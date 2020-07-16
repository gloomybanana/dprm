package org.gloomybanana.DPRM.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import org.gloomybanana.DPRM.hander.Registry;

public class ShapelessCraftingContainer extends Container {

    public CraftingInventory craftMatrix = new CraftingInventory(this, 3, 3);
    public IInventory craftResult = new CraftResultInventory();

    public ShapelessCraftingContainer(int id, PlayerInventory playerInventory) {
        super(Registry.shapedCraftingContainer.get(),id);

        //产物槽
        this.addSlot(new Slot(this.craftResult, 0, 124, 35));
        //3x3合成台插槽
        for (int y = 0; y < 3; ++y) for (int x = 0; x < 3; ++x) this.addSlot(new Slot(this.craftMatrix, x + y * 3, 30 + x * 18, 17 + y * 18));
        //玩家背包
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

}
