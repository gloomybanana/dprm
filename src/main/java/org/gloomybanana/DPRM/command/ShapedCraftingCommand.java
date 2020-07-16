package org.gloomybanana.DPRM.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;
import org.gloomybanana.DPRM.container.ShapedCraftingContainer;

import javax.annotation.Nullable;

public class ShapedCraftingCommand implements Command<CommandSource> {
    public static ShapedCraftingCommand instance = new ShapedCraftingCommand();

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().asPlayer();

        NetworkHooks.openGui(player, new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new StringTextComponent("inventory container");
            }

            @Nullable
            @Override
            public Container createMenu(int sycID, PlayerInventory playerInventory, PlayerEntity player) {
                return new ShapedCraftingContainer(sycID, playerInventory);
            }
        });
        return 0;
    }
}
