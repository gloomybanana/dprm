package org.gloomybanana.DPRM.container.create;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import org.gloomybanana.DPRM.handler.Registry;

public class CreateRecipeMenuContainer extends Container {
    private final PacketBuffer packetBuffer;

    public CreateRecipeMenuContainer(int sycID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(Registry.createRecipeContainer.get(),sycID);
        this.packetBuffer = packetBuffer;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public PacketBuffer getPacketBuffer() {
        return packetBuffer;
    }
}
