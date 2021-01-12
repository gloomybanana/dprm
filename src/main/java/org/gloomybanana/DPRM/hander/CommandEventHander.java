package org.gloomybanana.DPRM.hander;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.gloomybanana.DPRM.command.*;

@Mod.EventBusSubscriber
public class CommandEventHander {
    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event){

        Boolean onlyOperatorCanUse = Config.ONLY_OP_CAN_USE.get();
        Integer permissionLevel = onlyOperatorCanUse?2:0;

        //命令节点"dprm"
        LiteralArgumentBuilder<CommandSource> dprm = Commands.literal("dprm");
        dprm.requires((commandSource)-> commandSource.hasPermissionLevel(permissionLevel));
        dprm.executes(RecipeListCommand.instance);
        //命令节点"crafting"
        LiteralArgumentBuilder<CommandSource> crafting = Commands.literal("crafting");
        crafting.requires((commandSource)-> commandSource.hasPermissionLevel(permissionLevel));
        crafting.executes(CraftingCommand.instance);
        dprm.then(crafting);
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
        LiteralArgumentBuilder<CommandSource> smithing = Commands.literal("smithing");
        smithing.requires((commandSource -> commandSource.hasPermissionLevel(permissionLevel)));
        smithing.executes(SmithingCommand.instance);
        dprm.then(smithing);
        //TODO



        //注册命令
        CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();
        commandDispatcher.register(dprm);
    }
}
