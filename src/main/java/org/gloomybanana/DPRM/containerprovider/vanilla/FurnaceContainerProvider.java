package org.gloomybanana.DPRM.containerprovider.vanilla;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.vanilla.FurnaceContainer;

import javax.annotation.Nullable;

public class FurnaceContainerProvider implements INamedContainerProvider {

    @Nullable
    @Override
    public Container createMenu(int sycID, PlayerInventory playerInventory, PlayerEntity player) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        return new FurnaceContainer(sycID,playerInventory,packetBuffer);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui."+ DPRM.MOD_ID +".blasting.title");
    }
}
