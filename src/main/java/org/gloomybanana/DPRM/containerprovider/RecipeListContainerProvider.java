package org.gloomybanana.DPRM.containerprovider;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.RecipeListContainer;

import javax.annotation.Nullable;

public class RecipeListContainerProvider implements INamedContainerProvider {

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui."+ DPRM.MOD_ID +".recipe_list.title");
    }

    @Nullable
    @Override
    public Container createMenu(int sycID, PlayerInventory playerInventory, PlayerEntity player) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        return new RecipeListContainer(sycID,playerInventory,packetBuffer);
    }
}
