package org.gloomybanana.DPRM.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import org.gloomybanana.DPRM.hander.Registry;

public class BlastingContainer extends AbstractRecipeContainer {
    public CraftingInventory craftMatrix = new SingleSlot(this, 3, 3);
    public IInventory craftResult = new SingleSlot(this,3,3);
    public Slot[] furnaceSlots = new Slot[2];

    public BlastingContainer(int sycID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(Registry.blastingContainer.get(),sycID,playerInventory,packetBuffer);

        //烧制物品插槽
        furnaceSlots[1] = this.addSlot(new Slot(this.craftMatrix, 0, 56, 17));
        //产物插槽
        furnaceSlots[0] = this.addSlot(new Slot(this.craftResult, 0, 116, 35));
    }
}