package org.gloomybanana.DPRM.hander;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.gloomybanana.DPRM.Screen.ShapedCraftingSceen;
import org.gloomybanana.DPRM.Screen.ShapelessCraftingSceen;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHander {
    @SubscribeEvent
    public static void onClineSetupEvent(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(Registry.shapedCraftingContainer.get(), ShapedCraftingSceen::new);
        ScreenManager.registerFactory(Registry.shapelessCraftingContainer.get(), ShapelessCraftingSceen::new);
    }

}
