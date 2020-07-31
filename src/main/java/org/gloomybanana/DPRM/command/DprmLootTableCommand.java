package org.gloomybanana.DPRM.command;

import com.alibaba.fastjson.JSONObject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;

public class DprmLootTableCommand implements Command<CommandSource> {
    public static DprmLootTableCommand instance = new DprmLootTableCommand();

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

        return 0;
    }
}
