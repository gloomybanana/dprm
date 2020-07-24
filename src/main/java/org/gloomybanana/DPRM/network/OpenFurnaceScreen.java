package org.gloomybanana.DPRM.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gloomybanana.DPRM.containerprovider.FurnaceContainerProvider;

import java.util.function.Supplier;

public class OpenFurnaceScreen {
    private String jsonString;
    private static final Logger LOGGER = LogManager.getLogger();

    public OpenFurnaceScreen(PacketBuffer buffer) {
        jsonString = buffer.readString();
    }

    //反序列化
    public OpenFurnaceScreen(String jsonString) {
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

        NetworkHooks.openGui(serverPlayer,new FurnaceContainerProvider(), (PacketBuffer packetBuffer) -> {
            packetBuffer.writeString(jsonPacket.toJSONString());
        });
        ctx.get().setPacketHandled(true);
    }
}
