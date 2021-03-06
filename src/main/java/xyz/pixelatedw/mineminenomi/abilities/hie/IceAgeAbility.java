package xyz.pixelatedw.mineminenomi.abilities.hie;

import java.util.List;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.potion.EffectInstance;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.block.CoreBlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.block.FoliageBlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.block.LiquidBlockProtectionRule;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.hie.IceAgeParticleEffect;
import xyz.pixelatedw.wypi.APIConfig.AbilityCategory;
import xyz.pixelatedw.wypi.WyHelper;
import xyz.pixelatedw.wypi.abilities.Ability;
import xyz.pixelatedw.wypi.abilities.ChargeableAbility;

public class IceAgeAbility extends ChargeableAbility
{
	public static final Ability INSTANCE = new IceAgeAbility();

	public static final ParticleEffect PARTICLES = new IceAgeParticleEffect();
	private static final BlockProtectionRule GRIEF_RULE = new BlockProtectionRule(CoreBlockProtectionRule.INSTANCE, FoliageBlockProtectionRule.INSTANCE, LiquidBlockProtectionRule.INSTANCE); 

	public IceAgeAbility()
	{
		super("Ice Age", AbilityCategory.DEVIL_FRUIT);
		this.setMaxCooldown(15);
		this.setMaxChargeTime(5);
		this.setDescription("Freezes a large area around the user and everyone inside of it.");

		this.onEndChargingEvent = this::endChargingEvent;
	}

	private boolean endChargingEvent(PlayerEntity player)
	{
		for (int i = -15; i < 15; i++)
		{
			for (int j = -10; j < 10; j++)
			{
				for (int k = -15; k < 15; k++)
				{
					double posX = player.posX + i + (i < -WyHelper.randomWithRange(8, 12) || i > WyHelper.randomWithRange(8, 12) ? WyHelper.randomWithRange(-5, 5) : 0);
					double posY = player.posY + j;
					double posZ = player.posZ + k + (k < -WyHelper.randomWithRange(8, 12) || k > WyHelper.randomWithRange(8, 12) ? WyHelper.randomWithRange(-5, 5) : 0);

					AbilityHelper.placeBlockIfAllowed(player.world, posX, posY, posZ, Blocks.PACKED_ICE, GRIEF_RULE);
				}
			}
		}

		List<LivingEntity> list = WyHelper.getEntitiesNear(player.getPosition(), player.world, 15, FactionHelper.getOutsideGroupPredicate(player), LivingEntity.class);
		list.remove(player);

		for (LivingEntity target : list)
		{
			EffectInstance instance = new EffectInstance(ModEffects.FROZEN, 200, 1);
			target.addPotionEffect(instance);
			((ServerPlayerEntity) player).connection.sendPacket(new SPlayEntityEffectPacket(target.getEntityId(), instance));
		}

		PARTICLES.spawn(player.world, player.posX, player.posY, player.posZ, 0, 0, 0);

		return true;
	}
}
