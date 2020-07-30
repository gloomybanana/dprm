package org.gloomybanana.DPRM.container.vanilla;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import org.gloomybanana.DPRM.container.ContainerWithPlayerInventory;
import org.gloomybanana.DPRM.container.SingleSlot;
import org.gloomybanana.DPRM.handler.Registry;

public class FurnaceContainer extends ContainerWithPlayerInventory {
    public CraftingInventory craftMatrix = new SingleSlot(this, 3, 3);
    public IInventory craftResult = new SingleSlot(this,3,3);
    public Slot[] furnaceSlots = new Slot[2];

    public FurnaceContainer(int sycID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(Registry.furnaceContainer.get(),sycID,playerInventory,packetBuffer);

        //烧制物品插槽
        furnaceSlots[1] = this.addSlot(new Slot(this.craftMatrix, 0, 56, 17));
        //产物插槽
        furnaceSlots[0] = this.addSlot(new Slot(this.craftResult, 0, 116, 35));
    }
}