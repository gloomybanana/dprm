package org.gloomybanana.DPRM.Screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.ShapedCraftingContainer;
import org.gloomybanana.DPRM.container.ShapelessCraftingContainer;

public class ShapelessCraftingSceen extends ContainerScreen<ShapelessCraftingContainer> {
    //Screen背景材质
    private final ResourceLocation INVSCREEN_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/crafting_table.png");
    private final int textureWidth = 176;
    private final int textureHeight = 166;
    //Container位置坐标
    private int containerX;
    private int containerY;

    //gui本地化
    TranslationTextComponent title = new TranslationTextComponent("gui."+DPRM.MOD_ID+".shapeless_crafting");
    TranslationTextComponent recipeName = new TranslationTextComponent("gui."+DPRM.MOD_ID+".recipe_name");
    TranslationTextComponent addRecipe = new TranslationTextComponent("gui."+DPRM.MOD_ID+".add_to_datapack");
    TextFieldWidget textFieldWidget;
    Button button;

    public ShapelessCraftingSceen(ShapelessCraftingContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        //设置Screen的尺寸
        this.xSize = textureWidth;
        this.ySize = textureHeight;
    }

    @Override
    protected void init() {
        //添加文本输入框
        this.textFieldWidget = new TextFieldWidget(this.font, this.width / 2 - 100, containerY-22, 200, 20, recipeName.getString());
        this.children.add(this.textFieldWidget);
        //添加按钮
        this.button = new Button(containerX+100, containerY+61, 64, 10, addRecipe.getString(), (button) -> {

        });
        this.addButton(button);

        super.init();
    }

    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);

        //渲染组件
        this.textFieldWidget.render(mouseX, mouseY, particleTick);
        this.button.render(mouseX, mouseY, particleTick);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.renderBackground();
        //渲染背景材质
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(INVSCREEN_TEXTURE);

        containerX = (this.width - xSize) / 2;
        containerY = (this.height - ySize) / 2;
        blit(containerX, containerY, 0, 0, xSize, ySize, this.textureWidth, textureHeight);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.drawString(this.font, title.getString(), 30, 4, 0x000000);
    }
}
