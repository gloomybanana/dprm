package org.gloomybanana.DPRM.hander;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.gloomybanana.DPRM.command.InvContainerCommand;
import org.gloomybanana.DPRM.command.MenuScreenCommand;

@Mod.EventBusSubscriber
public class CommandEventHander {
    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event){
        //命令节点"compressedblock"
        LiteralArgumentBuilder<CommandSource> dprm = Commands.literal("dprm");//新建节点名
        dprm.requires((commandSource)-> commandSource.hasPermissionLevel(0));//设置为一般玩家权限
        //命令节点"menuscreen"
        LiteralArgumentBuilder<CommandSource> menuscreen = Commands.literal("invcontainer");//新建节点名
        menuscreen.requires((commandSource)-> commandSource.hasPermissionLevel(2));//设置为管理员权限
        menuscreen.executes(InvContainerCommand.instance);//命令功能
        //将"menuscreen"节点绑定到"dprm"上;
        dprm.then(menuscreen);
        //注册命令
        CommandDispatcher<CommandSource> commandDispatcher = event.getCommandDispatcher();
        commandDispatcher.register(menuscreen);
    }
}
