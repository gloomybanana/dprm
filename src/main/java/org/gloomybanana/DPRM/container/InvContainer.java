package org.gloomybanana.DPRM.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import org.gloomybanana.DPRM.hander.Registry;

import javax.annotation.Nullable;

public class InvContainer extends Container {

    public InvContainer(int id, PlayerInventory playerInventory) {
        super(Registry.invContainer.get(),id);
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

}
