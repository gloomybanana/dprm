package org.gloomybanana.DPRM.command;

import com.alibaba.fastjson.JSONObject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkHooks;
import org.gloomybanana.DPRM.containerprovider.CraftingContainerProvider;
import org.gloomybanana.DPRM.file.JsonManager;

import java.io.IOException;

public class CraftingCommand implements Command<CommandSource> {
    public static CraftingCommand instance = new CraftingCommand();

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity serverPlayer = context.getSource().asPlayer();
        String datapacksDirPath = serverPlayer.getServerWorld().getSaveHandler().getWorldDirectory().getPath() + "\\datapacks";
        JSONObject jsonPacket = new JSONObject(true);
        jsonPacket.put("player_name",serverPlayer.getName().getFormattedText());
        jsonPacket.put("datapacks_dir_path",datapacksDirPath);
        jsonPacket.put("select_recipe_name","");
        try {
            jsonPacket.put("recipe_list", JsonManager.getAllRecipes(jsonPacket));
        } catch (IOException e) {
            e.printStackTrace();
        }


        NetworkHooks.openGui(serverPlayer,new CraftingContainerProvider(), (packetBuffer) -> {
            packetBuffer.writeString(jsonPacket.toJSONString());
        });
        return 0;

    }
}