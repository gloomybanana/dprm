package org.gloomybanana.DPRM.command;

import com.alibaba.fastjson.JSONObject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkHooks;
import org.gloomybanana.DPRM.containerprovider.RecipeListContainerProvider;


public class RecipeListCommand implements Command<CommandSource> {
    public static RecipeListCommand instance = new RecipeListCommand();
    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity serverPlayer = context.getSource().asPlayer();

        String datapacksDirPath = serverPlayer.getServerWorld().getSaveHandler().getWorldDirectory().getPath() + "\\datapacks";
        JSONObject jsonPacket = new JSONObject();
        jsonPacket.put("player_name",serverPlayer.getName().getFormattedText());
        jsonPacket.put("datapacks_dir_path",datapacksDirPath);
        jsonPacket.put("recipe_list","");

        NetworkHooks.openGui(serverPlayer,new RecipeListContainerProvider(), (PacketBuffer packetBuffer) -> {
            packetBuffer.writeString(jsonPacket.toJSONString());
        });
        return 0;
    }


}
