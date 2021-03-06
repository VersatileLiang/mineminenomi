package xyz.pixelatedw.mineminenomi.world.features.structures.largebase.marine;

import java.util.Random;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.ScatteredStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import xyz.pixelatedw.mineminenomi.config.CommonConfig;
import xyz.pixelatedw.mineminenomi.init.ModFeatures;
import xyz.pixelatedw.wypi.APIConfig;
import xyz.pixelatedw.wypi.WyHelper;
import xyz.pixelatedw.wypi.debug.WyDebug;

public class MarineLargeBaseStructure extends ScatteredStructure<NoFeatureConfig>
{
	public MarineLargeBaseStructure()
	{
		super(NoFeatureConfig::deserialize);
	}
	
	@Override
	public String getStructureName()
	{
		return new ResourceLocation(APIConfig.PROJECT_ID, "marine_large_base").toString();
	}

	@Override
	protected int getSeedModifier()
	{
		return 45071855;
	}

	@Override
	public int getSize()
	{
		return 6;
	}

	// Keep in mind Feature Distance - Feature Separation MUST BE > 0, otherwise the game will crash!
	@Override
	protected int getBiomeFeatureDistance(ChunkGenerator<?> chunkGenerator)
	{
		return 16;
	}

	@Override
	protected int getBiomeFeatureSeparation(ChunkGenerator<?> chunkGenerator)
	{
		return 5;
	}
	
	@Override
	public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ)
	{
		boolean isFlat = WyHelper.isSurfaceFlat(chunkGen, chunkPosX, chunkPosZ, 2);
		boolean isChance = MathHelper.clamp(WyHelper.randomWithRange(0, 100) + WyHelper.randomDouble(), 0, 100) < CommonConfig.instance.getChanceForLargeBasesSpawn();
		//boolean isEmptyAround = WyHelper.isEmptyAround(chunkGen, rand, chunkPosX, chunkPosZ, 10, ModFeatures.BANDIT_CAMP, ModFeatures.MARINE_CAMP, ModFeatures.BANDIT_SMALL_BASE, ModFeatures.DOJO);
		
		if (isFlat && isChance)
		{
			return super.hasStartAt(chunkGen, rand, chunkPosX, chunkPosZ);
		}

		return false;
	}

	@Override
	public IStartFactory getStartFactory()
	{
		return MarineLargeBaseStructure.Start::new;
	}
	
	public static void register(Biome biome)
	{
		if (!CommonConfig.instance.canSpawnLargeBases())
			return;

		if (biome.getCategory() == Category.PLAINS || biome.getCategory() == Category.BEACH || biome.getCategory() == Category.FOREST || biome.getCategory() == Category.MESA || biome.getCategory() == Category.SAVANNA)
		{
			biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(ModFeatures.MARINE_LARGE_BASE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
			biome.addStructure(ModFeatures.MARINE_LARGE_BASE, IFeatureConfig.NO_FEATURE_CONFIG);
		}
	}
	
	public static class Start extends StructureStart
	{
		private TemplateManager templateManager;
		
		public Start(Structure<?> structure, int chunkX, int chunkZ, Biome biome, MutableBoundingBox bb, int i3, long seed)
		{
			super(structure, chunkX, chunkZ, biome, bb, i3, seed);
		}

		@Override
		public void init(ChunkGenerator<?> generator, TemplateManager templateManager, int chunkX, int chunkZ, Biome biome)
		{
			this.templateManager = templateManager;
			int i = chunkX * 16;
			int j = chunkZ * 16;
			BlockPos blockpos = new BlockPos(i, 90, j);
			MarineLargeBasePieces.addComponents(this.templateManager, blockpos, this.components);
			this.recalculateStructureSize();
			
			WyDebug.debug("Marine Large Base spawned at: /tp " + blockpos.getX() + " ~ " + blockpos.getZ());
		}
	}
}
