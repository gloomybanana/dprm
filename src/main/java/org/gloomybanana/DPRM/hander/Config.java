package org.gloomybanana.DPRM.hander;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.BooleanValue ONLY_OP_CAN_USE;
    public static ForgeConfigSpec.BooleanValue ENABLE_CUSTOM_PATH;
    public static ForgeConfigSpec.ConfigValue<String> PATH;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("General settings").push("general");
        ONLY_OP_CAN_USE = COMMON_BUILDER.comment("Only operator permission can use this mod? if false, all player can use this mod").define("op_only",true);
        ENABLE_CUSTOM_PATH = COMMON_BUILDER.comment("Enable custom path.").define("enable_custom_path",false);
        PATH = COMMON_BUILDER.comment("please write the correct custom path").define("custom_path","folder_name/folder_name2/folder_name3");
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}