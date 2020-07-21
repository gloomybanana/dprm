package org.gloomybanana.DPRM.Screen;

import com.alibaba.fastjson.JSONObject;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.StonecutterContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.gloomybanana.DPRM.DPRM;

public class StonecuttingScreen extends ContainerScreen<StonecutterContainer> implements IContainerListener {
    private final ResourceLocation STONECUTTING_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/stonecutting.png");
    JSONObject jsonPacket = new JSONObject();
    public StonecuttingScreen(StonecutterContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.renderBackground();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(STONECUTTING_TEXTURE);
        int textureWidth = 176;
        int textureHeight = 216;
        //背景渲染
        blit(guiLeft, guiTop, 0, 0, 176, 166, textureWidth, textureHeight);
    }

    @Override
    public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) {

    }

    @Override
    public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {

    }

    @Override
    public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue) {

    }
}
