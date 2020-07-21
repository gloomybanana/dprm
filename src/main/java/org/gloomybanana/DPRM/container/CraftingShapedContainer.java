package org.gloomybanana.DPRM.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import org.gloomybanana.DPRM.hander.Registry;

public class CraftingShapedContainer extends Container {

    private final PacketBuffer packetBuffier;
    public CraftingInventory craftMatrix = new CraftingInventory(this, 3, 3);
    public IInventory craftResult = new CraftResultInventory();
    public Slot[] craftTableSlots = new Slot[10];
    public CraftingShapedContainer(int id, PlayerInventory playerInventory,PacketBuffer packetBuffer) {
        super(Registry.craftingShapedContainer.get(),id);
        this.packetBuffier = packetBuffer;
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
        //玩家背包插槽
        layoutPlayerInventorySlots(playerInventory, 8, 84);
    }

    //玩家是否能交互
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    //玩家摁住Shift点击物品槽的行为
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }

    //玩家物品栏
    private int addSlotRange(IInventory inventory, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(inventory, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IInventory inventory, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(inventory, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(IInventory inventory, int leftCol, int topRow) {
        // Player inventory
        addSlotBox(inventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(inventory, 0, leftCol, topRow, 9, 18);
    }

    public PacketBuffer getPacketBuffier() {
        return packetBuffier;
    }
}
