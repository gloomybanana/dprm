package org.gloomybanana.DPRM.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import org.gloomybanana.DPRM.hander.Registry;

public class SmithingContainer extends AbstractRecipeContainer {

    public CraftingInventory craftMatrix = new SingleSlot(this, 3, 3);
    public IInventory craftResult = new SingleSlot(this,3,3);
    public Slot[] smithingSlots = new Slot[3];


    public SmithingContainer(int sycID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(Registry.smithingContainer.get(),sycID,playerInventory,packetBuffer);
        //被锻造物品插槽
        smithingSlots[1] = this.addSlot(new Slot(this.craftMatrix,0,27,47));
        //锻造材料插槽
        smithingSlots[2] = this.addSlot(new Slot(this.craftMatrix,1,76,47));
        //产物插槽
        smithingSlots[0] = this.addSlot(new Slot(this.craftResult,0,134,47));
    }
}
