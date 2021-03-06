package xyz.pixelatedw.wypi.abilities.projectiles;

import java.io.Serializable;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.Tag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.wypi.abilities.events.AbilityProjectileEvents;

public class AbilityProjectileEntity extends ThrowableEntity
{
	private int life = 64;
	private int maxLife = 64;
	private int knockbackStrength = 0;
	private double collisionSize = 1;
	private float damage = 1;
	private float gravity = 0.0001F;
	private boolean isPhysical = false;
	private boolean canPassThroughBlocks = false;
	private boolean canPassThroughEntities = false;
	private boolean canGetStuckInGround = false;
	protected boolean stuckInGround = false;
	private boolean changeHurtTime = false;
	// For reference default hurt time is 20
	private int hurtTime = 10;
	boolean entityDamaged = false;
	// Setting the defaults so that no crash occurs and so they will be null safe.
	public IOnEntityImpact onEntityImpactEvent = (hitEntity) -> { this.onBlockImpactEvent.onImpact(hitEntity.getPosition()); };
	public IOnBlockImpact onBlockImpactEvent = (hit) -> {};
	public IOnTick onTickEvent = () -> {};
	public IWithEffects withEffects = () -> { return new EffectInstance[0]; };
	public DamageSource source = new IndirectEntityDamageSource("ability_projectile", this, this.getThrower()).setProjectile();

	private static final Block[] NON_SOLID_BLOCKS = new Block[] {Blocks.GRASS, Blocks.TALL_GRASS, Blocks.SEAGRASS, Blocks.TALL_SEAGRASS, Blocks.VINE, Blocks.REDSTONE_WIRE, Blocks.DEAD_BUSH};

	public AbilityProjectileEntity(EntityType type, World world)
	{
		super(type, world);
	}

	public AbilityProjectileEntity(EntityType type, World world, double x, double y, double z)
	{
		super(type, x, y, z, world);
	}

	public AbilityProjectileEntity(EntityType type, World world, LivingEntity player)
	{
		super(type, player, world);
		this.maxLife = life;
	}

	@Override
	public void tick()
	{
		super.tick();

		if (!this.world.isRemote)
		{
			if (this.life <= 0)
			{
				this.life = this.maxLife;
				this.onBlockImpactEvent.onImpact(this.getPosition());
				this.remove();
			}
			else
				this.life--;
		}

		Vec3d vec31 = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d vec3 = new Vec3d(this.posX + this.getMotion().x, this.posY + this.getMotion().y, this.posZ + this.getMotion().z);
		RayTraceResult hit = this.world.rayTraceBlocks(new RayTraceContext(vec3, vec31, BlockMode.OUTLINE, FluidMode.ANY, this));

		double sizeX = this.collisionSize;
		double sizeY = this.collisionSize;
		double sizeZ = this.collisionSize;

		AxisAlignedBB aabb = new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).grow(sizeX, sizeY, sizeZ);
		List list = world.getEntitiesWithinAABB(LivingEntity.class, aabb);

		Entity entity = null;

		for (int i = 0; i < list.size(); ++i)
		{
			Entity target = (Entity) list.get(i);

			if (target.canBeCollidedWith() && (target != this.getThrower() || this.ticksExisted >= 5))
			{
				entity = target;
			}
		}

		if (entity == this.getThrower())
			return;

		if (entity != null)
			hit = new EntityRayTraceResult(entity);

		if (hit.getType() == RayTraceResult.Type.ENTITY && ((EntityRayTraceResult) hit).getEntity() instanceof LivingEntity)
			this.onImpact(hit);

		this.onTickEvent.onTick();

	}

	@Override
	public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy)
	{
		if (entityThrower instanceof PlayerEntity)
			inaccuracy = ItemsHelper.getSniperInaccuracy(inaccuracy, (PlayerEntity) entityThrower);
		super.shoot(entityThrower, rotationPitchIn, rotationYawIn, pitchOffset, velocity, inaccuracy);
	}

	@Override
	public boolean handleFluidAcceleration(Tag<Fluid> fluidTag)
	{
		if (this.inWater)
			this.doWaterSplashEffect();
		return false;
	}

	@Override
	protected void onImpact(RayTraceResult hit)
	{
		if (!this.world.isRemote)
		{
			if (hit.getType() == RayTraceResult.Type.ENTITY)
			{
				EntityRayTraceResult entityHit = (EntityRayTraceResult) hit;

				if (entityHit.getEntity() instanceof LivingEntity && this.getThrower() != null)
				{
					LivingEntity hitEntity = (LivingEntity) entityHit.getEntity();

					if (hitEntity == this.getThrower())
						return;

					AbilityProjectileEvents.Hit event = new AbilityProjectileEvents.Hit(this, hit);
					if (MinecraftForge.EVENT_BUS.post(event))
						return;

					if(!this.entityDamaged) {
/*						float reduction = getAbsoluteDamage();
						this.entityDamaged = hitEntity.attackEntityFrom(this.source, this.damage - reduction);
						hitEntity.hurtTime = 0;
						hitEntity.hurtResistantTime = 0;
						hitEntity.attackEntityFrom(this.source, reduction);*/
						this.entityDamaged = hitEntity.attackEntityFrom(this.source, this.damage);
					}

					this.triggerEffects(hitEntity);

					if(this.entityDamaged)
					{			
						this.onEntityImpactEvent.onImpact(hitEntity);

						if (this.knockbackStrength > 0)
						{
							Vec3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale(this.knockbackStrength * 0.6D);
							if (vec3d.lengthSquared() > 0.0D)
							{
								hitEntity.addVelocity(vec3d.x, 0.1D, vec3d.z);
							}
						}
			            
						if (!this.canPassThroughEntities)
							this.remove();
						else
							this.entityDamaged = false;
					}

					if (this.changeHurtTime)
						hitEntity.hurtResistantTime = this.hurtTime;
				}
			}
			else if (hit.getType() == RayTraceResult.Type.BLOCK)
			{
				BlockRayTraceResult blockHit = (BlockRayTraceResult) hit;

				AbilityProjectileEvents.Hit event = new AbilityProjectileEvents.Hit(this, hit);
				if (MinecraftForge.EVENT_BUS.post(event))
					return;

				if(!this.passedThroughNonSolidBlock(blockHit.getPos()))
				{
					if (!this.canPassThroughBlocks)
					{
						this.onBlockImpactEvent.onImpact(blockHit.getPos());
						if (!this.canGetStuckInGround)
							this.remove();
					}
				}
			}
		}
	}

	public float getAbsoluteDamage() {
		float reduction = (float) (this.damage * (0.5 + this.damage / 250));
		if(reduction > this.damage)
			reduction = this.damage;
		return reduction;
	}

	public void triggerEffects(LivingEntity hitEntity)
	{
		if (this.withEffects.getEffects().length > 0)
		{
			for (EffectInstance instance : this.withEffects.getEffects())
			{
				hitEntity.addPotionEffect(instance);
				if (this.getThrower() instanceof ServerPlayerEntity)
					((ServerPlayerEntity) this.getThrower()).connection.sendPacket(new SPlayEntityEffectPacket(hitEntity.getEntityId(), instance));
			}
		}
	}

	private boolean passedThroughNonSolidBlock(BlockPos pos)
	{
		// #TODO Remove this from API code and create a predicate or smth, also ugly hardcoded shit
		if(this.world.getBlockState(pos).getBlock() == ModBlocks.BARRIER || this.world.getBlockState(pos).getBlock() == Blocks.BEDROCK)
		{
			this.remove();
			return false;
		}
		
		for(Block block : NON_SOLID_BLOCKS)
		{
			if(this.world.getBlockState(pos).getBlock() == block)
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void remove()
	{
		super.remove();
	}

	public void setKnockbackStrength(int knockbackStrengthIn)
	{
		this.knockbackStrength = knockbackStrengthIn;
	}

	@Override
	protected float getGravityVelocity()
	{
		return this.gravity;
	}

	@Override
	public boolean isImmuneToExplosions()
	{
		return true;
	}

	@Override
	protected void registerData()
	{

	}

	@Override
	public IPacket<?> createSpawnPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	/*
	 * Setters/Getters
	 */
	public double getCollisionSize()
	{
		return this.collisionSize;
	}

	public void setCollisionSize(double val)
	{
		this.collisionSize = val;
	}

	public int getLife()
	{
		return this.life;
	}

	public int getMaxLife()
	{
		return this.maxLife;
	}

	public void setMaxLife(int life)
	{
		this.maxLife = life;
		this.life = this.maxLife;
	}

	public void setPhysical()
	{
		this.isPhysical = true;
	}

	public boolean getPhysical()
	{
		return this.isPhysical;
	}

	public void setPassThroughBlocks()
	{
		this.canPassThroughBlocks = true;
	}

	public void setPassThroughEntities()
	{
		this.canPassThroughEntities = true;
	}

	public void setCanGetStuckInGround()
	{
		this.canGetStuckInGround = true;
	}

	public void setDamage(float damage)
	{
		this.damage = damage;
	}

	public float getDamage()
	{
		return this.damage;
	}

	public void setGravity(float gravity)
	{
		this.gravity = gravity;
	}

	public boolean isStuckInGround()
	{
		return this.stuckInGround;
	}

	public void setChangeHurtTime(boolean flag)
	{
		this.changeHurtTime = flag;
	}

	public void setHurtTime(int time)
	{
		this.hurtTime = time;
	}

	public void setThrower(LivingEntity e)
	{
		this.owner = e;
	}

	/*
	 * Interfaces
	 */
	public interface IOnEntityImpact extends Serializable
	{
		void onImpact(LivingEntity hitEntity);
	}

	public interface IOnBlockImpact extends Serializable
	{
		void onImpact(BlockPos hitPos);
	}

	public interface IOnTick extends Serializable
	{
		void onTick();
	}

	public interface IWithEffects extends Serializable
	{
		EffectInstance[] getEffects();
	}
}