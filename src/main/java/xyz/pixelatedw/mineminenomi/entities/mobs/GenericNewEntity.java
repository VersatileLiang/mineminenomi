package xyz.pixelatedw.mineminenomi.entities.mobs;

import javax.annotation.Nullable;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class GenericNewEntity extends CreatureEntity implements IDynamicRenderer
{
	protected String[] textures;
	private static final DataParameter<Integer> TEXTURE_ID = EntityDataManager.createKey(GenericNewEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> HAS_BUSOSHOKU_HAKI_ACTIVE = EntityDataManager.createKey(GenericNewEntity.class, DataSerializers.BOOLEAN);
	private int doriki, belly;
	protected int threat = 2;
	private Goal currentGoal, previousGoal;
	
	public GenericNewEntity(EntityType type, World worldIn, String[] textures)
	{
		super(type, worldIn);
		this.textures = textures;
		this.experienceValue = this.threat;
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();
		this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
	}
	
	@Override
	protected void registerData()
	{
		super.registerData();
		this.getDataManager().register(TEXTURE_ID, 0);
		this.getDataManager().register(HAS_BUSOSHOKU_HAKI_ACTIVE, false);
	}

	@Override
	public void writeAdditional(CompoundNBT nbt)
	{
		super.writeAdditional(nbt);
		nbt.putInt("texture", this.getTextureId());
		nbt.putInt("doriki", this.doriki);
		nbt.putInt("belly", this.belly);

		nbt.putBoolean("hasBusoHaki", this.hasBusoHaki());
	}

	@Override
	public void readAdditional(CompoundNBT nbt)
	{
		super.readAdditional(nbt);
		this.setTexture(nbt.getInt("texture"));
		this.doriki = nbt.getInt("doriki");
		this.belly = nbt.getInt("belly");

		this.setBusoHaki(nbt.getBoolean("hasBusoHaki"));
	}

	@Override
	@Nullable
	public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) 
	{
		spawnData = super.onInitialSpawn(world, difficulty, reason, spawnData, dataTag);
		
		if(this.textures != null && this.textures.length > 0)
			this.setTexture(this.rand.nextInt(this.textures.length));
		
		return spawnData;
	}
	
	@Override
	public String getMobTexture()
	{
		return this.textures[this.getTextureId()];
	}

	public int getTextureId()
	{
		return this.getDataManager().get(TEXTURE_ID);
	}

	protected void setTexture(int texture)
	{
		this.getDataManager().set(TEXTURE_ID, texture);
	}

	public int getDoriki()
	{
		return this.doriki;
	}

	public void setDoriki(double value)
	{
		this.doriki = (int) Math.floor(value);
	}

	public int getBelly()
	{
		return this.belly;
	}

	public void setBelly(double value)
	{
		this.belly = (int) Math.floor(value);
	}

	public boolean hasBusoHaki()
	{
		return this.dataManager.get(HAS_BUSOSHOKU_HAKI_ACTIVE);
	}

	public void setBusoHaki(boolean value)
	{
		this.dataManager.set(HAS_BUSOSHOKU_HAKI_ACTIVE, value);
	}

	public Goal getCurrentGoal()
	{
		return this.currentGoal;
	}
	
	public Goal getPreviousGoal()
	{
		return this.previousGoal;
	}
	
	public void setCurrentGoal(Goal goal)
	{
		this.currentGoal = goal;
	}
	
	public void setPreviousGoal(Goal goal)
	{
		this.previousGoal = goal;
	}
	
	public void addThreat(int threat)
	{
		this.threat += threat;
	}
	
	public int getThreat()
	{
		return this.threat;
	}
	
	public void setThreat(int threat)
	{
		this.threat = threat;
	}
}
