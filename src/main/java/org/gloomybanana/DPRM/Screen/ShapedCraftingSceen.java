package org.gloomybanana.DPRM.Screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.ShapedCraftingContainer;

import java.util.ArrayList;

public class ShapedCraftingSceen extends ContainerScreen<ShapedCraftingContainer> implements IContainerListener {
    //Screen背景材质
    private final ResourceLocation CRAFTING_TABLE_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/crafting_table.png");
    private final int textureWidth = 176;
    private final int textureHeight = 216;
    private boolean widthTooNarrow;

    //gui本地化
    TranslationTextComponent title = new TranslationTextComponent("gui."+DPRM.MOD_ID+".shaped_crafting");
    TranslationTextComponent recipeName = new TranslationTextComponent("gui."+DPRM.MOD_ID+".recipe_name");
    TranslationTextComponent addRecipe = new TranslationTextComponent("gui."+DPRM.MOD_ID+".add_recipe");
    TextFieldWidget recipeNameInput;//文本域
    Button confirmBtn;//按钮

    //boolean
    boolean recipeNameIsEmpty = true;
    public ShapedCraftingSceen(ShapedCraftingContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        //设置ContainerScreen的尺寸
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void init() {

        //添加文本输入框
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.recipeNameInput = new TextFieldWidget(this.font, i + 92, j + 12, 80, 12, recipeName.getString());
        this.recipeNameInput.setCanLoseFocus(false);
        this.recipeNameInput.changeFocus(true);
        this.recipeNameInput.setTextColor(-1);
        this.recipeNameInput.setDisabledTextColour(-1);
        this.recipeNameInput.setEnableBackgroundDrawing(false);
        this.recipeNameInput.setMaxStringLength(35);
        this.recipeNameInput.setResponder(this::inputResponder);
        this.children.add(this.recipeNameInput);
        this.container.addListener(this);
        this.setFocusedDefault(this.recipeNameInput);

        //添加按钮
        this.confirmBtn = new Button(this.guiLeft + 90, this.height / 2 - 23, 70, 20, addRecipe.getString(), (button) -> {

        });
        this.confirmBtn.active = false;
        this.addButton(confirmBtn);

        super.init();
    }

    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);

        //渲染组件
        this.recipeNameInput.render(mouseX, mouseY, particleTick);



        //按钮Tooltip
        ArrayList<String> confirmBtnToolTips = new ArrayList<>();
        confirmBtnToolTips.add("请输入正确的配方");
        if (this.confirmBtn.isHovered()){
            if(this.confirmBtn.active){
                renderTooltip(confirmBtnToolTips,mouseX,mouseY);
            }
        }


    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.renderBackground();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(CRAFTING_TABLE_TEXTURE);
        this.blit(guiLeft, guiTop, 0, 0, 176, 166, textureWidth, textureHeight);

        this.blit(guiLeft + 88, guiTop + 8, 0, 166 + (this.recipeNameInput.isFocused() ? 0 : 16), 80, 16, textureWidth, textureHeight);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font.drawString(this.title.getString(), 28.0F, 6.0F, 4210752);
    }

    private void inputResponder(String inputText) {
        if (inputText.isEmpty()) {
            this.recipeNameIsEmpty = true;
        }else {
            this.recipeNameIsEmpty = false;
        }
    }

    public void resize(Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
        String s = this.recipeNameInput.getText();
        this.init(p_resize_1_, p_resize_2_, p_resize_3_);
        this.recipeNameInput.setText(s);
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
