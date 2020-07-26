package org.gloomybanana.DPRM.hander;

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
        Integer permissionLevel = onlyOperatorCanUse?2:0;

        //命令节点"dprm"
        LiteralArgumentBuilder<CommandSource> dprm = Commands.literal("dprm");
        dprm.requires((commandSource)-> commandSource.hasPermissionLevel(permissionLevel));
        dprm.executes(RecipeListCommand.instance);
        //命令节点"crafting"
        LiteralArgumentBuilder<CommandSource> crafting = Commands.literal("crafting");
        crafting.requires((commandSource)-> commandSource.hasPermissionLevel(permissionLevel));
        crafting.executes(CraftingCommand.instance);
        dprm.then(crafting);//绑定到"dprm"节点上
        //命令节点"furnace"
        LiteralArgumentBuilder<CommandSource> blasting = Commands.literal("furnace");
        blasting.requires((commandSource -> commandSource.hasPermissionLevel(permissionLevel)));
        blasting.executes(FurnaceCommand.instance);//命令功能
        dprm.then(blasting);
        //命令节点"stonecutting"
        LiteralArgumentBuilder<CommandSource> stonecutting = Commands.literal("stonecutting");
        stonecutting.requires((commandSource -> commandSource.hasPermissionLevel(permissionLevel)));
        stonecutting.executes(StonecuttingCommand.instance);
        dprm.then(stonecutting);
        //命令节点"smithing"
        //TODO

        //注册命令
        CommandDispatcher<CommandSource> commandDispatcher = event.getCommandDispatcher();
        commandDispatcher.register(dprm);
    }
}
