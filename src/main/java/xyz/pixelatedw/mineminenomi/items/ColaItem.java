package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModCreativeTabs;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.wypi.network.WyNetwork;
import xyz.pixelatedw.wypi.schematic.WySchematic;

public class ColaItem extends Item
{

	public ColaItem()
	{
		super(new Properties().group(ModCreativeTabs.MISC).maxStackSize(16).food(Foods.APPLE));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	{
		System.out.println(WySchematic.load(("marineCamp")));
		//WySchematic.build(WySchematic.load("dojo"), world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), Blocks.BEDROCK);
		//player.setActiveHand(hand);
		//if(!world.isRemote)
		//	WyHelper.loadNBTStructure((ServerWorld) world, "small_ship/marine/mast", player.getPosition());
		return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack itemStack, World world, LivingEntity entity)
	{
		if(!world.isRemote && entity instanceof PlayerEntity)
		{		
			PlayerEntity player = (PlayerEntity) entity;
			IEntityStats props = EntityStatsCapability.get(player);
			
			if(props.isCyborg())
			{
				if(props.getCola() <= props.getMaxCola() - 15) 
					props.alterCola(15);
				else 
					props.setCola(props.getMaxCola());
			}

			WyNetwork.sendTo(new SSyncEntityStatsPacket(player.getEntityId(), props), (ServerPlayerEntity) player);
			if(!player.isCreative())
				itemStack.shrink(1);
		}
		
		return itemStack;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.DRINK;
	}
}