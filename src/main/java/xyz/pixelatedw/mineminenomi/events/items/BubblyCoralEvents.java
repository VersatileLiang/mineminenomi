package xyz.pixelatedw.mineminenomi.events.items;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.wypi.APIConfig;

@Mod.EventBusSubscriber(modid = APIConfig.PROJECT_ID, value = Dist.CLIENT)
public class BubblyCoralEvents
{

	@SubscribeEvent
	public static void onBubblyCoralCheck(LivingUpdateEvent event)
	{
		if(event.getEntityLiving().isPotionActive(ModEffects.BUBBLY_CORAL))
		{
			event.getEntityLiving().setAir(event.getEntityLiving().getMaxAir());
		}
	}
	
}