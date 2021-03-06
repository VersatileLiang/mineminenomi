package xyz.pixelatedw.mineminenomi.entities.projectiles.bomu;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.api.abilities.ExplosionAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.common.CommonExplosionParticleEffect;
import xyz.pixelatedw.wypi.abilities.projectiles.AbilityProjectileEntity;

public class BreezeBreathBombProjectile extends AbilityProjectileEntity{

	private int tick = 0;
	public BreezeBreathBombProjectile(World world)
	{
		super(BomuProjectiles.BREEZE_BREATH_BOMB, world);
	}

	public BreezeBreathBombProjectile(EntityType type, World world)
	{
		super(type, world);
	}

	public BreezeBreathBombProjectile(World world, double x, double y, double z)
	{
		super(BomuProjectiles.BREEZE_BREATH_BOMB, world, x, y, z);
	}

	public BreezeBreathBombProjectile(World world, LivingEntity player)
	{
		super(BomuProjectiles.BREEZE_BREATH_BOMB, world, player);

		this.setDamage(6);
		this.onTickEvent = this::onTickEvent;
		this.setMaxLife(32);
		this.setPassThroughEntities();
		
	}
	private void onTickEvent() {
		if(tick > 5) {
		BlockPos pos = this.getPosition();
		ExplosionAbility explosion = AbilityHelper.newExplosion(this.getThrower(), pos.getX(), pos.getY(), pos.getZ(), 3);
		explosion.setExplosionSound(true);
		explosion.setDamageOwner(false);
		explosion.setDestroyBlocks(true);
		explosion.setFireAfterExplosion(false);
		explosion.setSmokeParticles(new CommonExplosionParticleEffect(3));
		explosion.setDamageEntities(true);
		explosion.doExplosion();
		}
		tick++;
	}
}
