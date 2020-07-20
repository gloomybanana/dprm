package org.gloomybanana.DPRM.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkHooks;
import org.gloomybanana.DPRM.containerprovider.CraftingShapedContainerProvider;

public class CraftingShapedCommand implements Command<CommandSource> {
    public static CraftingShapedCommand instance = new CraftingShapedCommand();


    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity serverPlayer = context.getSource().asPlayer();
        String playerName = serverPlayer.getName().getFormattedText();
        String recipePath = serverPlayer.getServerWorld().getSaveHandler().getWorldDirectory().getPath() + "\\datapacks\\add_by_" + playerName + "\\data\\minecraft\\recipes";


        NetworkHooks.openGui(serverPlayer,new CraftingShapedContainerProvider(serverPlayer), (packerBuffer) -> {
            packerBuffer.writeString(recipePath);
        });
        return 0;

    }
}
