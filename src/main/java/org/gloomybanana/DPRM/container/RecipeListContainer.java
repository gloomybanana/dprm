package org.gloomybanana.DPRM.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import org.gloomybanana.DPRM.hander.Registry;


public class RecipeListContainer extends Container {
    private final PacketBuffer packetBuffer;

    public RecipeListContainer(int sycID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(Registry.recipeListContainer.get(),sycID);
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
