package org.gloomybanana.DPRM.Screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.InvContainer;

public class InvScreen extends ContainerScreen<InvContainer> {
    //Screen背景材质
    private final ResourceLocation INVSCREEN_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/container.png");
    private final int textureWidth = 176;
    private final int textureHeight = 166;


    public InvScreen(InvContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        //设置Screen的尺寸
        this.xSize = textureWidth;
        this.ySize = textureHeight;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.renderBackground();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(INVSCREEN_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        blit(i, j, 0, 0, xSize, ySize, this.textureWidth, textureHeight);
    }
}
