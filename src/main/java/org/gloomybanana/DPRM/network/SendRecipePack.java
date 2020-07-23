package org.gloomybanana.DPRM.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.command.MenuScreenCommand;
import org.gloomybanana.DPRM.file.JsonManager;

import java.util.function.Supplier;

public class SendRecipePack {
    private String jsonString;
    private static final Logger LOGGER = LogManager.getLogger();

    public SendRecipePack(PacketBuffer buffer) {
        jsonString = buffer.readString();
    }

    //反序列化
    public SendRecipePack(String jsonString) {
        this.jsonString = jsonString;
    }

    //序列化
    public void toBytes(PacketBuffer buf) {
        buf.writeString(this.jsonString);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LOGGER.info(this.jsonString);
        });
        ServerPlayerEntity serverPlayer = null;
        try {
            serverPlayer = ctx.get().getSender().getCommandSource().asPlayer();
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }

        JSONObject recipeJson = JSON.parseObject(jsonString);
        JSONObject jsonPacket = recipeJson.getJSONObject("jsonPacket");
        JSONObject json_recipe = recipeJson.getJSONObject("json_recipe");
        String recipe_name = recipeJson.getString("recipe_name");

        JSONObject result = JsonManager.createJsonFile(jsonPacket,json_recipe,recipe_name);
        if (result.getBoolean("success")){
            serverPlayer.sendMessage(new TranslationTextComponent("gui."+DPRM.MOD_ID+".chat.recipe_generate_successed",result.getString("dir")));
        }else {
            serverPlayer.sendMessage(new TranslationTextComponent("gui."+ DPRM.MOD_ID+".chat.recipe_generate_failed",result.getString("dir")));
        }


        ctx.get().setPacketHandled(true);
    }

}
