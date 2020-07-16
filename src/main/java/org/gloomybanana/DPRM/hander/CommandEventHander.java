package org.gloomybanana.DPRM.hander;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.gloomybanana.DPRM.command.ShapedCraftingCommand;
import org.gloomybanana.DPRM.command.ShapelessCraftingCommand;

@Mod.EventBusSubscriber
public class CommandEventHander {
    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event){
        //命令节点"dprm"
        LiteralArgumentBuilder<CommandSource> dprm = Commands.literal("dprm");//新建节点名
        dprm.requires((commandSource)-> commandSource.hasPermissionLevel(0));//设置为一般玩家权限
        //命令节点"shaped_crafting"
        LiteralArgumentBuilder<CommandSource> shaped_crafting = Commands.literal("shaped_crafting");//新建节点名
        shaped_crafting.requires((commandSource)-> commandSource.hasPermissionLevel(2));//设置为管理员权限
        shaped_crafting.executes(ShapedCraftingCommand.instance);//命令功能
        //将"shaped_crafting"节点绑定到"dprm"上;
        dprm.then(shaped_crafting);

        //命令节点"shapeless_crafting"
        LiteralArgumentBuilder<CommandSource> shapeless_crafting = Commands.literal("shapeless_crafting");//新建节点名
        shapeless_crafting.requires((commandSource)-> commandSource.hasPermissionLevel(2));//设置为管理员权限
        shapeless_crafting.executes(ShapelessCraftingCommand.instance);//命令功能
        //将"shapeless_crafting"节点绑定到"dprm"上;
        dprm.then(shaped_crafting);

        //注册命令
        CommandDispatcher<CommandSource> commandDispatcher = event.getCommandDispatcher();
        commandDispatcher.register(dprm);
    }
}
