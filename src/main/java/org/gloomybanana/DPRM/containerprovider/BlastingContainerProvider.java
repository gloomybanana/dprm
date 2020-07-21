package org.gloomybanana.DPRM.containerprovider;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.gloomybanana.DPRM.command.BlastingCommand;
import org.gloomybanana.DPRM.container.BlastingContainer;
import org.gloomybanana.DPRM.container.CraftingShapedContainer;

import javax.annotation.Nullable;

public class BlastingContainerProvider implements INamedContainerProvider {
    private final ServerPlayerEntity serverPlayer;

    public BlastingContainerProvider(ServerPlayerEntity serverPlayer) {
        this.serverPlayer = serverPlayer;
    }

    @Nullable
    @Override
    public Container createMenu(int sycID, PlayerInventory playerInventory, PlayerEntity player) {
        String playerName = serverPlayer.getName().getFormattedText();
        String recipePath = serverPlayer.getServerWorld().getSaveHandler().getWorldDirectory().getPath() + "\\datapacks\\add_by_" + playerName + "\\data\\minecraft\\recipes";
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer()).writeString(recipePath);

        return new BlastingContainer(sycID,playerInventory,packetBuffer);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("DPRM Container Provider");
    }

    public ServerPlayerEntity getServerPlayer() {
        return serverPlayer;
    }
}
