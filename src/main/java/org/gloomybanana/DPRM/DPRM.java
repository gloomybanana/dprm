package org.gloomybanana.DPRM;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gloomybanana.DPRM.hander.Config;
import org.gloomybanana.DPRM.hander.Registry;

@Mod("dprm")
public class DPRM {
    public static final String MOD_ID = "dprm";
    public static final String MOD_NAME = "Datapack Recipe Maker";
    public static final String MOD_VERSION = "1.2";
    public static final Logger LOGGER = LogManager.getLogger();

    public DPRM(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        Registry.CONTAINERS_TYPE.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
    }
    public void setup(final FMLCommonSetupEvent event){

    }

}
