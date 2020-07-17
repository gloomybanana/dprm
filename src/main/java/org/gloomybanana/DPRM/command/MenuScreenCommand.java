package org.gloomybanana.DPRM.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import org.gloomybanana.DPRM.file.FileManager;

import java.io.IOException;


public class MenuScreenCommand implements Command<CommandSource> {
    public static MenuScreenCommand instance = new MenuScreenCommand();
    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().asPlayer();

        try {
            FileManager.createMinecraftDatapackRecipesDir(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
