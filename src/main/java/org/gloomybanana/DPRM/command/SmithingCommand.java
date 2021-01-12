package org.gloomybanana.DPRM.command;

import com.alibaba.fastjson.JSONObject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;
import org.gloomybanana.DPRM.containerprovider.CraftingContainerProvider;
import org.gloomybanana.DPRM.containerprovider.SmithingContainerProvider;
import org.gloomybanana.DPRM.file.JsonManager;

import java.io.IOException;

public class SmithingCommand implements Command<CommandSource> {
    public static SmithingCommand instance = new SmithingCommand();

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity serverPlayer = context.getSource().asPlayer();
        String datapacksDirPath = JsonManager.getWorldFolder(serverPlayer.getServerWorld()).getPath() + "\\datapacks";
        JSONObject jsonPacket = new JSONObject(true);
        jsonPacket.put("player_name",serverPlayer.getName().getString());
        jsonPacket.put("datapacks_dir_path",datapacksDirPath);
        jsonPacket.put("select_recipe_name","");
        jsonPacket.put("current_page",1);
        try {
            jsonPacket.put("recipe_list", JsonManager.getAllRecipes(jsonPacket));
        } catch (IOException e) {
            e.printStackTrace();
        }


        NetworkHooks.openGui(serverPlayer,new SmithingContainerProvider(), (packetBuffer) -> {
            packetBuffer.writeString(jsonPacket.toJSONString());
        });
        return 0;
    }
}