package xyz.pixelatedw.mineminenomi.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.wypi.WyHelper;

public class CommonConfig
{
	
	public static CommonConfig instance;
	
	// General
	private EnumValue keepStatsAfterDeath;
	private Map<String, ForgeConfigSpec.BooleanValue> statsToKeep;
	private Map<String, ForgeConfigSpec.BooleanValue> cooldownVisual;
	private List<String> bannedAbilities;
	private EnumValue haoshokuUnlockLogic;

	private BooleanValue logiaInvulnerability;
	private BooleanValue logiaHurtsLogias;
	private BooleanValue devilFruitDropsFromLeaves;
	private BooleanValue extraHearts;
	private BooleanValue mobRewards;
	private BooleanValue griefing;
	private BooleanValue animeScreaming;
	private BooleanValue specialFlying;
	private BooleanValue oneFruitPerWorld;
	private BooleanValue yamiPower;
	private DoubleValue dorikiRewardMultiplier;
	private BooleanValue minimumDorikiPerKill;
	private BooleanValue abilityFraudChecks;
	//private DoubleValue devilFruitDropsFromLeaves
		
	// Structures
	private BooleanValue spawnDojos;
	private DoubleValue spawnChanceDojo;
	private BooleanValue spawnShips;
	private DoubleValue spawnRateSmallShip;
	private DoubleValue spawnRateLargeShip;
	
	// Quests
	private BooleanValue quests;
	private BooleanValue questProgression;
	
	// Bounty
	private BooleanValue wantedPosterPackages;
	private IntValue timeBetweenPackageDrops;
	
	// World Events
	// Traders
	private BooleanValue canSpawnTraders;
	private IntValue timeBetweenTraderSpawns;
	private IntValue chanceForTraderSpawn;
	// Trainers
	private BooleanValue canSpawnTrainers;
	private IntValue timeBetweenTrainerSpawns;
	private IntValue chanceForTrainerSpawn;
	// Ambushes
	private BooleanValue canSpawnAmbushes;
	private IntValue timeBetweenAmbushSpawns;
	private IntValue chanceForAmbushSpawn;
	
	// Crews
	private IntValue bountyRequirement;
	private BooleanValue worldMessageOnCrewCreate;
	
	// Ores
	private IntValue kairosekiSpawnCount;
	private IntValue kairosekiSpawnHeight;

	// System
	private BooleanValue telemetry;
	private BooleanValue fovRemover;
	private BooleanValue updateMessage;


	public enum KeepStatsLogic
	{
		NONE, AUTO, FULL, CUSTOM
	}
	
	public enum HaoshokuUnlockLogic
	{
		NONE, RANDOM, EXPERIENCE
	}
	
	public static void init()
	{
		Pair<CommonConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        ForgeConfigSpec configSpec = pair.getRight();
        CommonConfig.instance = pair.getLeft();
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, configSpec);
	}
	
	public CommonConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("General");
		{
			this.logiaInvulnerability = builder.comment("Allows logia users to avoid physical attacks; true by default").define("Logia Invulnerability", true);
			this.logiaHurtsLogias = builder.comment("Allows logia users to hurt other logia users with projectiles; true by default").define("Logias hurt Logias", true);
			this.devilFruitDropsFromLeaves = builder.comment("Allows Devil Fruits to drop from leaves; false by default").define("Devil Fruit drops from leaves", false);
			this.extraHearts = builder.comment("Allows players to receive extra hearts based on their doriki; true by default").define("Extra Hearts", true);
			this.mobRewards = builder.comment("Allows mobs to reward doriki, bounty or items; true by default").define("Mob Rewards", true);
			this.griefing = builder.comment("Allows abilities to break or replace blocks; if turned OFF it will make some abilities completly useless; true by default").define("Griefing", true);
			this.animeScreaming = builder.comment("Will send a chat message to nearby players with the used ability's name; false by default").define("Anime Scream", false);
			this.specialFlying = builder.comment("Allows Gasu Gasu no Mi, Moku Moku no Mi and Suna Suna no Mi users to fly, this option does not affect flying Zoans which will be able to fly regardless; false by default").define("Special Flying", false);
			this.oneFruitPerWorld = builder.comment("Restricts the Devil Fruit spawns to only 1 of each type per world; false by default").define("One Devil Fruit per World", false);
			this.yamiPower = builder.comment("Allows Yami Yami no Mi users to eat an additional fruit; true by default").define("Yami Yami no Mi additional fruit", true);
			this.dorikiRewardMultiplier = builder.comment("Multiplies any doriki gained by this amount; 1 by default, min: 0, max: 10").defineInRange("Doriki Reward Multiplier", 1.0, 0.0, 10.0);
			this.minimumDorikiPerKill = builder.comment("Guarantees a minimum of 1 doriki per kill; false by default").define("Minimum Doriki per Kill", false);
			this.abilityFraudChecks = builder.comment("Runs a check for all abilities on a player to remove dupes or suspicious abilities when the player joins the world; true by default").define("Ability Fraud Checks", true);
			this.haoshokuUnlockLogic = builder.comment("Responsible for how player unlock Haoshoku Haki; \n NONE - Haoshoku Haki cannot be unlocked naturally \n RANDOM - Only a few chosen ones receive it when they spawn \n EXPERIENCE - Will unlock based on the total amount of Haki experience a player has").defineEnum("Haoshoku Haki Unlock Logic", HaoshokuUnlockLogic.EXPERIENCE, HaoshokuUnlockLogic.values());

			this.bannedAbilities = new ArrayList<String>();
			Predicate<Object> bannedAbilitiesTest = new Predicate<Object>() 
			{
				@Override
				public boolean test(Object t)
				{
					if(!(t instanceof String))
						return false;
					
					String str = (String)t;
					return !WyHelper.isNullOrEmpty(str);
				}
			};
			this.bannedAbilities.add("Example1");
			this.bannedAbilities.add("Example2");
			builder.comment("List with ability names that are banned, the names can be written in any case with or without spaces").defineList("Banned Abilities", this.bannedAbilities, bannedAbilitiesTest);
			
			this.keepStatsAfterDeath = builder.comment("Defines which logic to apply after a player's death \n NONE - nothing is kept \n AUTO (default) - only the faction/race/fighting style stats are kept \n FULL - everything is kept \n CUSTOM - will use the 'Stats to Keep' section to determine which stats to keep").defineEnum("Keep Stats after Death", KeepStatsLogic.AUTO, KeepStatsLogic.values());
			
			builder.push("Stats to Keep");
			{
				String[] statsToKeepNames = new String[] {"Doriki", "Bounty", "Belly", "Race", "Faction", "Fighting Style", "Devil Fruit", "Haki Exp"};
				this.statsToKeep = new HashMap<String, ForgeConfigSpec.BooleanValue>();
				
				for(String stat : statsToKeepNames) 
					this.statsToKeep.put(stat, builder.define(stat, true));
			}
			builder.pop();
			
			builder.push("Cooldown Visuals");
			{
				String[] cooldownVisuals = new String[] {"Text", "Color"};
				this.cooldownVisual = new HashMap<String, ForgeConfigSpec.BooleanValue>();
				
				for(String mode : cooldownVisuals) 
					this.cooldownVisual.put(mode, builder.define(mode, true));
			}
			builder.pop();

		}
		builder.pop();
		
		builder.push("Structures");
		
		this.spawnDojos = builder.comment("Allows dojos to spawn in the world; true by default").define("Spawn Dojos", true);
		this.spawnChanceDojo = builder.comment("Sets the % chance for a dojo to spawn; 0.01 by default, min is 0 and max is 100").defineInRange("Dojo Spawn Chance", 0.01, 0, 100);		
		this.spawnShips = builder.comment("Allows ships to spawn in the world; true by default").define("Spawn Ships", true);
		this.spawnRateSmallShip = builder.comment("Spawn Rate for Small Ships, min is 0 and max is 100").defineInRange("Spawn Rate for Small Ships", 1.5, 0.0, 100.0);
		this.spawnRateLargeShip = builder.comment("Spawn Rate for Large Ships, min is 0 and max is 100").defineInRange("Spawn Rate for Large Ships", 1.0, 0.0, 100.0);
		
		builder.pop();
		
		builder.push("Quests");
		
		this.quests = builder.comment("Allows quests to be accepted / completed; true by default").define("Quests", true);
		this.questProgression = builder.comment("Allows quests to reward players with abilities, otherwise all abilities will be unlocked from the beginning; true by default").define("Quest Progression", true);
		
		builder.pop();

		builder.push("World Events");
		{
			builder.push("Traders");
			this.canSpawnTraders = builder.comment("Allows Traders to spawn in the world; true by default").define("Trader Spawns", true);
			this.timeBetweenTraderSpawns = builder.comment("Determines the time (in seconds) between two spawn attempts; 1800 by default").defineInRange("Time Between Trader Spawns", 1800, 1, 99999);
			this.chanceForTraderSpawn = builder.comment("Determines the % chance for a trader to spawn; 1 by default").defineInRange("Chance for Trader Spawns", 1, 1, 100);
			builder.pop();

			builder.push("Trainers");
			this.canSpawnTrainers = builder.comment("Allows Trainers to spawn in the world; true by default").define("Trainer Spawns", true);
			this.timeBetweenTrainerSpawns = builder.comment("Determines the time (in seconds) between two spawn attempts; 1800 by default").defineInRange("Time Between Trainer Spawns", 1800, 1, 99999);
			this.chanceForTrainerSpawn = builder.comment("Determines the % chance for a trainer to spawn; 15 by default").defineInRange("Chance for Trainer Spawns", 15, 1, 100);
			builder.pop();

			builder.push("Ambushes");
			this.canSpawnAmbushes = builder.comment("Allows Ambushes to spawn in the world; true by default").define("Ambushe Spawns", true);
			this.timeBetweenAmbushSpawns = builder.comment("Determines the time (in seconds) between two spawn attempts; 3600 by default").defineInRange("Time Between Ambushes Spawns", 3600, 1, 99999);
			this.chanceForAmbushSpawn = builder.comment("Determines the % chance for a ambush to spawn; 15 by default").defineInRange("Chance for Ambush Spawns", 15, 1, 100);
			builder.pop();
		}
		builder.pop();


		builder.push("Ores");
		this.kairosekiSpawnCount = builder.comment("Kairoseki vein spawn count; 3 by default").defineInRange("Chance for vein to spawn", 3, 0, 100);
		this.kairosekiSpawnHeight = builder.comment("Kairoseki spawn height; 128 by default").defineInRange("Kairoseki max spawn size", 128, 0, 256);
		builder.pop();

		builder.push("Crews");
		{
			this.bountyRequirement = builder.comment("Bounty Requirement for creating a crew; 0 means no requirement; 0 by default").defineInRange("Bounty Requirement", 0, 0, ModValues.MAX_DORIKI);
			this.worldMessageOnCrewCreate = builder.comment("Sends a message to all players when a new crew gets formed; false by default").define("World Message", false);
		}
		builder.pop();
		
		builder.push("Bounty");
		
		this.wantedPosterPackages = builder.comment("Allows wanted poster packages to drop from the sky; true by default").define("Wanted Poster Package Drops", true);
		this.timeBetweenPackageDrops = builder.comment("Time it takes for another package to drop; 18000 (15 minutes) by default").defineInRange("Time Between Package Drops", 18000, 0, Integer.MAX_VALUE);
		
		builder.pop();

		builder.push("System");	
		
		this.telemetry = builder.comment("Allows the game to send data to our server for statistics, no personal information is sent only minor things like which fruit the player ate, what ability was used, which mobs killed etc; true by default").define("Telemtry", true);
		this.updateMessage = builder.comment("Allows the game to show a text message when the installed mod is outdated; true by default").define("Update Message", true);	
		this.fovRemover = builder.comment("Keeps the FOV fixed when the player has speed effects active").define("FOV Remover", true);
		
		builder.pop();
	}

	public double getDojoSpawnChance()
	{
		return this.spawnChanceDojo.get();
	}
	
	public boolean canSpawnDojos()
	{
		return this.spawnDojos.get();
	}
	
	public boolean isCrewWorldMessageEnabled()
	{
		return this.worldMessageOnCrewCreate.get();
	}
	
	public int getBountyRequirementForCrews()
	{
		return this.bountyRequirement.get();
	}
	
	public HaoshokuUnlockLogic getHaoshokuUnlockLogic()
	{
		return (HaoshokuUnlockLogic) this.haoshokuUnlockLogic.get();
	}
	
	public int getChanceForAmbushSpawn()
	{
		return this.chanceForAmbushSpawn.get();
	}
	
	public int getTimeBetweenAmbushSpawns()
	{
		return this.timeBetweenAmbushSpawns.get();
	}
	
	public boolean canSpawnAmbushes()
	{
		return this.canSpawnAmbushes.get();
	}
	
	public int getChanceForTrainerSpawn()
	{
		return this.chanceForTrainerSpawn.get();
	}
	
	public int getTimeBetweenTrainerSpawns()
	{
		return this.timeBetweenTrainerSpawns.get();
	}
	
	public boolean canSpawnTrainers()
	{
		return this.canSpawnTrainers.get();
	}
	
	public int getChanceForTraderSpawn()
	{
		return this.chanceForTraderSpawn.get();
	}
	
	public int getTimeBetweenTraderSpawns()
	{
		return this.timeBetweenTraderSpawns.get();
	}
	
	public boolean canSpawnTraders()
	{
		return this.canSpawnTraders.get();
	}
	
	public String[] getCooldownVisuals()
	{
		String[] newArray = new String[] {};
		return this.cooldownVisual.keySet().toArray(newArray);
	}
	
	public String[] getStatsToKeep()
	{
		String[] newArray = new String[] {};
		return this.statsToKeep.keySet().toArray(newArray);
	}
	
	public int getTimeBetweenPackages()
	{
		return this.timeBetweenPackageDrops.get();
	}
	
	public boolean isWantedPosterPackagesEnabled()
	{
		return this.wantedPosterPackages.get();
	}
	
	public double getLargeShipSpawnRate()
	{
		return this.spawnRateLargeShip.get();
	}
	
	public double getSmallShipSpawnRate()
	{
		return this.spawnRateSmallShip.get();
	}
	
	public boolean isSpawnShipsEnabled()
	{
		return this.spawnShips.get();
	}
	
	public double getDorikiRewardMultiplier()
	{
		return this.dorikiRewardMultiplier.get();
	}
	
	public boolean isUpdateMessageEnabled()
	{
		return this.updateMessage.get();
	}

	public boolean isQuestProgressionEnabled()
	{
		return this.questProgression.get();
	}
	
	public boolean isFOVRemoved()
	{
		return this.fovRemover.get();
	}
	
	public boolean isQuestsEnabled()
	{
		return this.quests.get();
	}
	
	public String[] getBannedAbilities()
	{
		return Arrays.copyOf(this.bannedAbilities.toArray(), this.bannedAbilities.toArray().length, String[].class);
	}
	
	public KeepStatsLogic getAfterDeathLogic()
	{
		return (KeepStatsLogic) this.keepStatsAfterDeath.get();
	}
	
	public boolean isLogiaInvulnerabilityEnabled()
	{
		return this.logiaInvulnerability.get();
	}

	public boolean isLogiaHurtsLogiasEnabled()
	{
		return this.logiaHurtsLogias.get();
	}

	public boolean isDevilFruitDropsFromLeavesEnabled()
	{
		return this.devilFruitDropsFromLeaves.get();
	}
	
	public boolean isExtraHeartsEnabled()
	{
		return this.extraHearts.get();
	}
	
	public boolean isMobRewardsEnabled()
	{
		return this.mobRewards.get();
	}
	
	public boolean isGriefingEnabled()
	{
		return this.griefing.get();
	}
	
	public boolean isAnimeScreamingEnabled()
	{
		return this.animeScreaming.get();
	}
	
	public boolean isSpecialFlyingEnabled()
	{
		return this.specialFlying.get();
	}
	
	public boolean isOneFruitPerWorldEnabled()
	{
		return this.oneFruitPerWorld.get();
	}
	
	public boolean isYamiPowerEnabled()
	{
		return this.yamiPower.get();
	}

	public boolean isTelemetryEnabled()
	{
		return this.telemetry.get();
	}

	public boolean isAbilityFraudChecksEnabled()
	{
		return this.abilityFraudChecks.get();
	}

	public boolean isMinimumDorikiPerKillEnabled() { return this.minimumDorikiPerKill.get(); }

	public int getkairosekiSpawnCount() { return this.kairosekiSpawnCount.get(); }

	public int getKairosekiSpawnHeight() { return this.kairosekiSpawnCount.get(); }
}
