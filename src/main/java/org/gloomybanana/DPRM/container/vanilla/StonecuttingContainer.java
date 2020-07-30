package org.gloomybanana.DPRM.container.vanilla;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import org.gloomybanana.DPRM.container.ContainerWithPlayerInventory;
import org.gloomybanana.DPRM.container.SingleSlot;
import org.gloomybanana.DPRM.handler.Registry;

public class StonecuttingContainer extends ContainerWithPlayerInventory {

    public CraftingInventory craftMatrix = new SingleSlot(this, 3, 3);
    public IInventory craftResult = new CraftResultInventory();
    public Slot[] stonecuttingSlots = new Slot[2];


    public StonecuttingContainer(int sycID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(Registry.stonecuttingContainer.get(),sycID,playerInventory,packetBuffer);
        //烧制物品插槽
        stonecuttingSlots[1] = this.addSlot(new Slot(this.craftMatrix, 0, 20, 33));
        //产物插槽
        stonecuttingSlots[0] = this.addSlot(new Slot(this.craftResult, 0, 143, 33));

    }
}