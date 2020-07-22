package org.gloomybanana.DPRM.hander;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.gloomybanana.DPRM.Screen.*;
import org.gloomybanana.DPRM.container.*;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHander {
    @SubscribeEvent
    public static void onClineSetupEvent(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(Registry.craftingShapedContainer.get(), CraftingShapedScreen::new);
        ScreenManager.registerFactory(Registry.craftingShapelessContainer.get(), CraftingShapelessScreen::new);
        ScreenManager.registerFactory(Registry.smeltingContainer.get(), SmeltingScreen::new);
        ScreenManager.registerFactory(Registry.smokingContainer.get(), SmokingScreen::new);
        ScreenManager.registerFactory(Registry.blastingContainer.get(), BlastingScreen::new);
        ScreenManager.registerFactory(Registry.campfireCookingContainer.get(), CampfireCookingScreen::new);
        ScreenManager.registerFactory(Registry.stonecuttingContainer.get(), StonecuttingScreen::new);

    }

}
