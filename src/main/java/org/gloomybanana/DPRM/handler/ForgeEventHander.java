package org.gloomybanana.DPRM.handler;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHander {
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event){

    }
}
