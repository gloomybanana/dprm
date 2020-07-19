package org.gloomybanana.DPRM.hander;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.gloomybanana.DPRM.command.MenuScreenCommand;
import org.gloomybanana.DPRM.command.CraftingShapedCommand;
import org.gloomybanana.DPRM.command.CraftingShapelessCommand;

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
        LiteralArgumentBuilder<CommandSource> shaped_crafting = Commands.literal("crafting_shaped");//新建节点名
        shaped_crafting.requires((commandSource)-> commandSource.hasPermissionLevel(2));//设置为管理员权限
        shaped_crafting.executes(CraftingShapedCommand.instance);//命令功能
        dprm.then(shaped_crafting);//绑定到"dprm"节点上
        //命令节点"crafting_shapeless"
        LiteralArgumentBuilder<CommandSource> shapeless_crafting = Commands.literal("crafting_shapeless");//新建节点名
        shapeless_crafting.requires((commandSource)-> commandSource.hasPermissionLevel(2));//设置为管理员权限
        shapeless_crafting.executes(CraftingShapelessCommand.instance);//命令功能
        dprm.then(shapeless_crafting);//绑定到"dprm"节点上

        //注册命令
        CommandDispatcher<CommandSource> commandDispatcher = event.getCommandDispatcher();
        commandDispatcher.register(dprm);
    }
}
