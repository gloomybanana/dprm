package org.gloomybanana.DPRM.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.containerprovider.vanilla.CraftingContainerProvider;
import org.gloomybanana.DPRM.containerprovider.vanilla.FurnaceContainerProvider;
import org.gloomybanana.DPRM.containerprovider.vanilla.MenuContainerProvider;
import org.gloomybanana.DPRM.containerprovider.vanilla.StonecuttingContainerProvider;
import org.gloomybanana.DPRM.dao.VanillaRecipeJson;

import java.io.IOException;
import java.util.function.Supplier;

public class ScreenToggle {
    String jsonString;

    public ScreenToggle(PacketBuffer buffer) {
        this.jsonString = buffer.readString(3000000);
    }

    //反序列化
    public ScreenToggle(String jsonString) {
        this.jsonString = jsonString;
    }

    //序列化
    public void toBytes(PacketBuffer buf) {
        buf.writeString(this.jsonString,3000000);
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

        JSONObject jsonPacket = JSON.parseObject(jsonString);
        try {
            jsonPacket.put("recipe_list", VanillaRecipeJson.getAllRecipes(jsonPacket));
        } catch (IOException e) {
            e.printStackTrace();
        }


        //TODO
        //追加json信息

        if (jsonPacket.getString("operate").equals("open_crafting_screen")){
            NetworkHooks.openGui(serverPlayer,new CraftingContainerProvider(), (PacketBuffer packetBuffer) -> {
                packetBuffer.writeString(jsonPacket.toJSONString());
            });
        }
        if (jsonPacket.getString("operate").equals("open_furnace_screen")){
            NetworkHooks.openGui(serverPlayer,new FurnaceContainerProvider(), (PacketBuffer packetBuffer) -> {
                packetBuffer.writeString(jsonPacket.toJSONString());
            });
        }
        if (jsonPacket.getString("operate").equals("open_stonecutting_screen")){
            NetworkHooks.openGui(serverPlayer,new StonecuttingContainerProvider(), (PacketBuffer packetBuffer) -> {
                packetBuffer.writeString(jsonPacket.toJSONString());
            });
        }
        if (jsonPacket.getString("operate").equals("open_recipe_list_screen")){
            NetworkHooks.openGui(serverPlayer,new MenuContainerProvider(), (PacketBuffer packetBuffer) -> {
                packetBuffer.writeString(jsonPacket.toJSONString());
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
