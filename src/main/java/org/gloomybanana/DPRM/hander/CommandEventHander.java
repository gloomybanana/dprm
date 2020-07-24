package org.gloomybanana.DPRM.hander;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.gloomybanana.DPRM.command.*;

@Mod.EventBusSubscriber
public class CommandEventHander {
    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event){
        //命令节点"dprm"
        LiteralArgumentBuilder<CommandSource> dprm = Commands.literal("dprm");//新建节点名
        dprm.requires((commandSource)-> commandSource.hasPermissionLevel(0));//设置为一般玩家权限
        dprm.executes(RecipeListCommand.instance);
        //命令节点"crafting_shaped"
        LiteralArgumentBuilder<CommandSource> crafting_shaped = Commands.literal("crafting_shaped");//新建节点名
        crafting_shaped.requires((commandSource)-> commandSource.hasPermissionLevel(2));//设置为管理员权限
        crafting_shaped.executes(CraftingShapedCommand.instance);//命令功能
        dprm.then(crafting_shaped);//绑定到"dprm"节点上
        //命令节点"crafting_shapeless"
        LiteralArgumentBuilder<CommandSource> crafting_shapeless = Commands.literal("crafting_shapeless");//新建节点名
        crafting_shapeless.requires((commandSource)-> commandSource.hasPermissionLevel(2));//设置为管理员权限
        crafting_shapeless.executes(CraftingShapelessCommand.instance);//命令功能
        dprm.then(crafting_shapeless);//绑定到"dprm"节点上
        //命令节点"furnace"
        LiteralArgumentBuilder<CommandSource> blasting = Commands.literal("furnace");
        blasting.requires((commandSource -> commandSource.hasPermissionLevel(2)));//设置管理员权限
        blasting.executes(FurnaceCommand.instance);//命令功能
        dprm.then(blasting);
        //命令节点"stonecutting"
        LiteralArgumentBuilder<CommandSource> stonecutting = Commands.literal("stonecutting");
        stonecutting.requires((commandSource -> commandSource.hasPermissionLevel(2)));//设置管理员权限
        stonecutting.executes(StonecuttingCommand.instance);//命令功能
        dprm.then(stonecutting);
        //命令节点"smithing"
        //TODO

        //注册命令
        CommandDispatcher<CommandSource> commandDispatcher = event.getCommandDispatcher();
        commandDispatcher.register(dprm);
    }
}
