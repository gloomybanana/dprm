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
        //命令节点"menu"
        LiteralArgumentBuilder<CommandSource> menu = Commands.literal("menu");//新建节点名
        menu.requires((commandSource)-> commandSource.hasPermissionLevel(0));//设置为一般玩家权限
        menu.executes(MenuScreenCommand.instance);
        dprm.then(menu);//绑定到"dprm"节点上
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
        //命令节点"smelting"
        LiteralArgumentBuilder<CommandSource> smelting = Commands.literal("smelting");//新建节点名
        smelting.requires((commandSource -> commandSource.hasPermissionLevel(2)));//设置管理员权限
        smelting.executes(SmeltingCommand.instance);//命令功能
        dprm.then(smelting);
        //命令节点"smoking"
        LiteralArgumentBuilder<CommandSource> smoking = Commands.literal("smoking");
        smoking.requires((commandSource -> commandSource.hasPermissionLevel(2)));//设置管理员权限
        smoking.executes(SmokingCommand.instance);//命令功能
        //命令节点"blasting"
        LiteralArgumentBuilder<CommandSource> blasting = Commands.literal("blasting");
        blasting.requires((commandSource -> commandSource.hasPermissionLevel(2)));//设置管理员权限
        blasting.executes(BlastingCommand.instance);//命令功能
        //命令节点"campfire_cooking"
        LiteralArgumentBuilder<CommandSource> campfire_cooking = Commands.literal("campfire_cooking");
        campfire_cooking.requires((commandSource -> commandSource.hasPermissionLevel(2)));//设置管理员权限
        campfire_cooking.executes(CampfireCookingCommand.instance);//命令功能
        //命令节点"stonecutting"
        LiteralArgumentBuilder<CommandSource> stonecutting = Commands.literal("stonecutting");
        stonecutting.requires((commandSource -> commandSource.hasPermissionLevel(2)));//设置管理员权限
        stonecutting.executes(StonecuttingCommand.instance);//命令功能
        //命令节点"smithing"
        //TODO

        //注册命令
        CommandDispatcher<CommandSource> commandDispatcher = event.getCommandDispatcher();
        commandDispatcher.register(dprm);
    }
}
