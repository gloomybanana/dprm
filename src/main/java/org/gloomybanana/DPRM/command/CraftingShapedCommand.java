package org.gloomybanana.DPRM.command;

import com.alibaba.fastjson.JSONObject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkHooks;
import org.gloomybanana.DPRM.containerprovider.CraftingShapedContainerProvider;

public class CraftingShapedCommand implements Command<CommandSource> {
    public static CraftingShapedCommand instance = new CraftingShapedCommand();

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity serverPlayer = context.getSource().asPlayer();
        String datapacksDirPath = serverPlayer.getServerWorld().getSaveHandler().getWorldDirectory().getPath() + "\\datapacks";
        JSONObject jsonPacket = new JSONObject();
        jsonPacket.put("player_name",serverPlayer.getName().getFormattedText());
        jsonPacket.put("datapacks_dir_path",datapacksDirPath);

        NetworkHooks.openGui(serverPlayer,new CraftingShapedContainerProvider(), (packetBuffer) -> {
            packetBuffer.writeString(jsonPacket.toJSONString());
        });
        return 0;

    }
}
