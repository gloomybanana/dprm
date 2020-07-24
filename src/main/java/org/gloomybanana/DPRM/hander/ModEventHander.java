package org.gloomybanana.DPRM.hander;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.gloomybanana.DPRM.Screen.*;
import org.gloomybanana.DPRM.network.Networking;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHander {
    @SubscribeEvent
    public static void onClineSetupEvent(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(Registry.craftingShapedContainer.get(), CraftingShapedScreen::new);
        ScreenManager.registerFactory(Registry.craftingShapelessContainer.get(), CraftingShapelessScreen::new);
        ScreenManager.registerFactory(Registry.furnaceContainer.get(), FurnaceScreen::new);
        ScreenManager.registerFactory(Registry.stonecuttingContainer.get(), StonecuttingScreen::new);
        ScreenManager.registerFactory(Registry.recipeListContainer.get(), RecipeListScreen::new);
    }
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        Networking.registerMessage();
    }

}
