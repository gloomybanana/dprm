package org.gloomybanana.DPRM.handler;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.gloomybanana.DPRM.Config;
import org.gloomybanana.DPRM.command.*;

@Mod.EventBusSubscriber
public class CommandEventHander {
    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event){

        Boolean onlyOperatorCanUse = Config.VALUE.get();
        int permissionLevel = onlyOperatorCanUse?2:0;

        //命令节点"dprm"
        LiteralArgumentBuilder<CommandSource> dprm = Commands.literal("dprm");
        dprm.requires((commandSource) -> commandSource.hasPermissionLevel(permissionLevel));
        dprm.executes(DprmCommand.instance);

        //命令节点"loot_table"
        LiteralArgumentBuilder<CommandSource> loot_table = Commands.literal("loot_table");
        loot_table.requires((commandSource -> commandSource.hasPermissionLevel(permissionLevel)));
        loot_table.executes(DprmLootTableCommand.instance);
        //dprm.then(loot_table);

        //命令节点"create"
        LiteralArgumentBuilder<CommandSource> create = Commands.literal("create");
        create.requires((commandSource) -> commandSource.hasPermissionLevel(permissionLevel));
        create.executes(CreateCommand.instance);
        //dprm.then(create);

        //注册命令
        CommandDispatcher<CommandSource> commandDispatcher = event.getCommandDispatcher();
        commandDispatcher.register(dprm);

    }
}
