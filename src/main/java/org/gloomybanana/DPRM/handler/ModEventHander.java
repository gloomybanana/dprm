package org.gloomybanana.DPRM.handler;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.gloomybanana.DPRM.Screen.vanilla.CraftingScreen;
import org.gloomybanana.DPRM.Screen.vanilla.FurnaceScreen;
import org.gloomybanana.DPRM.Screen.vanilla.RecipeListScreen;
import org.gloomybanana.DPRM.Screen.vanilla.StonecuttingScreen;
import org.gloomybanana.DPRM.network.Networking;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHander {
    @SubscribeEvent
    public static void onClineSetupEvent(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(Registry.craftingContainer.get(), CraftingScreen::new);
        ScreenManager.registerFactory(Registry.furnaceContainer.get(), FurnaceScreen::new);
        ScreenManager.registerFactory(Registry.stonecuttingContainer.get(), StonecuttingScreen::new);
        ScreenManager.registerFactory(Registry.recipeListContainer.get(), RecipeListScreen::new);
    }
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        Networking.registerMessage();
    }

}
