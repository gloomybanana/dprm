package org.gloomybanana.DPRM.command;

import com.alibaba.fastjson.JSONObject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkHooks;
import org.gloomybanana.DPRM.containerprovider.BlastingContainerProvider;
import org.gloomybanana.DPRM.containerprovider.SmeltingContainerProvider;

import java.io.File;

public class BlastingCommand implements Command<CommandSource> {
    public static BlastingCommand instance = new BlastingCommand();

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity serverPlayer = context.getSource().asPlayer();
        String datapacksDirPath = serverPlayer.getServerWorld().getSaveHandler().getWorldDirectory().getPath() + "\\datapacks";
        JSONObject jsonPacket = new JSONObject();
        jsonPacket.put("player_name",serverPlayer.getName().getFormattedText());
        jsonPacket.put("datapacks_dir_path",datapacksDirPath);

        File file = new File(datapacksDirPath+"\\add_by_"+serverPlayer.getName().getFormattedText()+"\\data\\minecraft\\recipes");
        if (file.exists()){

        }


        NetworkHooks.openGui(serverPlayer,new BlastingContainerProvider(serverPlayer), (PacketBuffer packetBuffer) -> {
            packetBuffer.writeString(jsonPacket.toJSONString());
        });
        return 0;
    }
}
