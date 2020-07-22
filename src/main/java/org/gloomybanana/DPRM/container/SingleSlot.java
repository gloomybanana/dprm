package org.gloomybanana.DPRM.container;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;

public class SingleSlot extends CraftingInventory {
    public SingleSlot(Container eventHandlerIn, int width, int height) {
        super(eventHandlerIn, width, height);
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
}
