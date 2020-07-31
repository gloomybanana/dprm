package org.gloomybanana.DPRM.containerprovider.loot_table;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.gloomybanana.DPRM.DPRM;

import javax.annotation.Nullable;

public class MenuContainerProvider implements INamedContainerProvider {
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui."+ DPRM.MOD_ID +".loot_table_menu.title");
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        return null;
    }
}
