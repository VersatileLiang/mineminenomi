package xyz.pixelatedw.mineminenomi.quests.artofweather;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.quests.objectives.ObtainItemObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.artofweather.UniqueTemopAbilityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.artofweather.ThunderTempoKillObjective;
import xyz.pixelatedw.wypi.WyHelper;
import xyz.pixelatedw.wypi.WyRegistry;
import xyz.pixelatedw.wypi.quests.Quest;
import xyz.pixelatedw.wypi.quests.objectives.Objective;

public class ArtOfWeatherTrial03Quest extends Quest
{
	private Objective objective01 = new ObtainItemObjective("Obtain a Perfect Clima Tact", ModWeapons.PERFECT_CLIMA_TACT, 1);
	private Objective objective02 = new UniqueTemopAbilityObjective("Perform %s unique Tempos", 3).addRequirement(this.objective01);
	private Objective objective03 = new ThunderTempoKillObjective("Kill %s enemies using Thunderbolt Tempo or Thunderstorm Tempo", 15).addRequirement(this.objective02).markHidden();
	
	private static final String TIP = WyRegistry.registerName("quest.art_of_weather_trial_03.tip", "<Weather Wizard> Sneaking will charge your Clima Tact with a ball instead of releasing it, try different combinations for different tempos");

	public ArtOfWeatherTrial03Quest()
	{
		super("art_of_weather_trial_03", "Trial: Tempo Training");
		this.addObjectives(this.objective01, this.objective02, this.objective03);
		this.addRequirement(ModQuests.ART_OF_WEATHER_TRIAL_02);
		
		this.onStartEvent = this::startQuest;
	}

	public void startQuest(PlayerEntity player)
	{
		WyHelper.sendMsgToPlayer(player, new TranslationTextComponent(TIP).getFormattedText());
	}
}