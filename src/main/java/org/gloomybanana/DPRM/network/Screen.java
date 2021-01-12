package org.gloomybanana.DPRM.network;

import com.alibaba.fastjson.JSONObject;

public enum Screen {
    recipeListScreen,craftingScreen,furnaceScreen,stonecuttingScreen,smithingScreen;
    public void jumpTo(JSONObject jsonPacket){
        switch (this){
            default:
            case recipeListScreen: {
                jsonPacket.put("operate","open_recipe_list_screen");
                Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
            };
            case craftingScreen: {
                jsonPacket.put("operate","open_crafting_screen");
                Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
            };
            case furnaceScreen: {
                jsonPacket.put("operate","open_furnace_screen");
                Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
            };
            case stonecuttingScreen: {
                jsonPacket.put("operate","open_stonecutting_screen");
                Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
            };
            case smithingScreen: {
                jsonPacket.put("operate","open_smithing_screen");
                Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
            };
        }
    }
}
