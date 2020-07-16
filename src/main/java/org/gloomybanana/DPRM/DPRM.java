package org.gloomybanana.DPRM;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gloomybanana.DPRM.hander.Registry;

@Mod("dprm")
public class DPRM {

    public static final String MOD_ID = "dprm";
    public static final String MOD_NAME = "Datapack Recipe Maker";
    public static final String MOD_VERSION = "1.0";
    public static final Logger LOGGER = LogManager.getLogger();

    public DPRM(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        Registry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        Registry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        Registry.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    public void setup(final FMLCommonSetupEvent event){

    }

}
