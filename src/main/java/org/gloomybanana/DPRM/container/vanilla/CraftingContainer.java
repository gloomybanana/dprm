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

public class CraftingContainer extends ContainerWithPlayerInventory {

    public CraftingInventory craftMatrix = new SingleSlot(this, 3, 3);
    public IInventory craftResult = new CraftResultInventory();
    public Slot[] craftTableSlots = new Slot[10];
    public CraftingContainer(int id, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(Registry.craftingContainer.get(),id,playerInventory,packetBuffer);
        //产物插槽
        craftTableSlots[0] = this.addSlot(new Slot(this.craftResult, 0, 124, 35));
        //3x3合成栏插槽
        craftTableSlots[1] = this.addSlot(new Slot(this.craftMatrix, 0, 30 + 0 * 18, 17 + 0 * 18));
        craftTableSlots[2] = this.addSlot(new Slot(this.craftMatrix, 1, 30 + 1 * 18, 17 + 0 * 18));
        craftTableSlots[3] = this.addSlot(new Slot(this.craftMatrix, 2, 30 + 2 * 18, 17 + 0 * 18));
        craftTableSlots[4] = this.addSlot(new Slot(this.craftMatrix, 3, 30 + 0 * 18, 17 + 1 * 18));
        craftTableSlots[5] = this.addSlot(new Slot(this.craftMatrix, 4, 30 + 1 * 18, 17 + 1 * 18));
        craftTableSlots[6] = this.addSlot(new Slot(this.craftMatrix, 5, 30 + 2 * 18, 17 + 1 * 18));
        craftTableSlots[7] = this.addSlot(new Slot(this.craftMatrix, 6, 30 + 0 * 18, 17 + 2 * 18));
        craftTableSlots[8] = this.addSlot(new Slot(this.craftMatrix, 7, 30 + 1 * 18, 17 + 2 * 18));
        craftTableSlots[9] = this.addSlot(new Slot(this.craftMatrix, 8, 30 + 2 * 18, 17 + 2 * 18));
    }
}
