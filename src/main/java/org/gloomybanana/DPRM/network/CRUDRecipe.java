package org.gloomybanana.DPRM.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.file.JsonManager;
import java.util.function.Supplier;

public class CRUDRecipe {
    String jsonString;

    public CRUDRecipe(PacketBuffer buffer) {
        this.jsonString = buffer.readString();
    }

    //反序列化
    public CRUDRecipe(String jsonString) {
        this.jsonString = jsonString;
    }

    //序列化
    public void toBytes(PacketBuffer buf) {
        buf.writeString(this.jsonString);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DPRM.LOGGER.info("Send From Client:"+this.jsonString);
        });
        ServerPlayerEntity serverPlayer = null;
        try {
            serverPlayer = ctx.get().getSender().getCommandSource().asPlayer();
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        //还原接受包
        JSONObject jsonPacket = JSON.parseObject(jsonString, Feature.OrderedField);
        JSONObject json_recipe = jsonPacket.getJSONObject("json_recipe");
        String recipe_name = jsonPacket.getString("recipe_name");
        //创建json文件
        if (jsonPacket.getString("crud").equals("create")){
            JSONObject result = JsonManager.createJsonFile(jsonPacket,json_recipe,recipe_name);
            if (result.getBoolean("success")){
                serverPlayer.sendMessage(new TranslationTextComponent("gui."+DPRM.MOD_ID+".chat.recipe_generate_successed",result.getString("dir")));
            }
            else { serverPlayer.sendMessage(new TranslationTextComponent("gui."+ DPRM.MOD_ID+".chat.recipe_generate_failed",result.getString("dir"))); }
        }
        if (jsonPacket.getString("crud").equals("delete")) {
            String result = JsonManager.deleteJsonFile(jsonPacket);
            serverPlayer.sendMessage(new StringTextComponent(result));
        }

        //向玩家反馈创建信息

        ctx.get().setPacketHandled(true);
    }
}
