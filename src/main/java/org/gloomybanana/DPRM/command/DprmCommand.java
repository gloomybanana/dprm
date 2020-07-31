package org.gloomybanana.DPRM.command;

import com.alibaba.fastjson.JSONObject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkHooks;
import org.gloomybanana.DPRM.containerprovider.vanilla.MenuContainerProvider;
import org.gloomybanana.DPRM.dao.VanillaRecipeJson;

import java.io.IOException;


public class DprmCommand implements Command<CommandSource> {
    public static DprmCommand instance = new DprmCommand();
    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity serverPlayer = context.getSource().asPlayer();
        String datapacksDirPath = serverPlayer.getServerWorld().getSaveHandler().getWorldDirectory().getPath() + "\\datapacks";
        JSONObject jsonPacket = new JSONObject(true);
         JSONObject player = new JSONObject(true);
         player.put("name",serverPlayer.getName().getString());
         player.put("is_op",serverPlayer.hasPermissionLevel(2));
         player.put("datapack_path",datapacksDirPath);
        jsonPacket.put("player",player);
        jsonPacket.put("select_recipe_name","");
        jsonPacket.put("current_page",1);
        try {
            jsonPacket.put("recipe_list", VanillaRecipeJson.getAllRecipes(jsonPacket));
        } catch (IOException e) {
            e.printStackTrace();
        }

        NetworkHooks.openGui(serverPlayer,new MenuContainerProvider(), (PacketBuffer packetBuffer) -> {
            packetBuffer.writeString(jsonPacket.toJSONString());
        });
        return 0;
    }


}
