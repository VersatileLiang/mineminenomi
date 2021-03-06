package xyz.pixelatedw.mineminenomi.abilities.zou;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.ZoanAbility;
import xyz.pixelatedw.mineminenomi.entities.zoan.ZouHeavyZoanInfo;
import xyz.pixelatedw.wypi.APIConfig.AbilityCategory;

import java.util.UUID;

public class ZouHeavyPointAbility extends ZoanAbility
{
	public static final ZouHeavyPointAbility INSTANCE = new ZouHeavyPointAbility();

	private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(UUID.fromString("d141ef28-e77a-418d-927b-ca9a09417189"), "Guard Point Multiplier", -0.1f, AttributeModifier.Operation.MULTIPLY_BASE).setSaved(false);
	private static final AttributeModifier ARMOR_MODIFIER = new AttributeModifier(UUID.fromString("0847f786-0a5a-4e83-9ea6-f924c259a798"), "Guard Point Multiplier", 12, AttributeModifier.Operation.ADDITION).setSaved(false);
	private static final AttributeModifier STRENGTH_MODIFIER = new AttributeModifier(UUID.fromString("4b03a4b4-1eb5-464a-8312-0f9079044462"), "Guard Point Multiplier", 6, AttributeModifier.Operation.ADDITION).setSaved(false);
	private static final AttributeModifier ATTACK_SPEED_MODIFIER = new AttributeModifier(UUID.fromString("1d78a133-8a0e-4b8f-8790-1360007d4741"), "Guard Point Multiplier", -0.3f, AttributeModifier.Operation.ADDITION).setSaved(false);

	public ZouHeavyPointAbility()
	{
		super("Zou Heavy Point", AbilityCategory.DEVIL_FRUIT, ZouHeavyZoanInfo.FORM);
		this.setDescription("Allows the user to transforms into an elephant hybrid, which focuses on strength. Allows the user to use 'Trunk Shot' and 'Ivory Stomp'");
		this.addZoanModifier(SharedMonsterAttributes.MOVEMENT_SPEED, SPEED_MODIFIER);
		this.addZoanModifier(SharedMonsterAttributes.ARMOR, ARMOR_MODIFIER);
		this.addZoanModifier(SharedMonsterAttributes.ATTACK_DAMAGE, STRENGTH_MODIFIER);
		this.addZoanModifier(SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER);
	}
}
