package org.gloomybanana.DPRM.Screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.CraftingShapedContainer;
import org.gloomybanana.DPRM.file.JsonManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ShapedCraftingSceen extends ContainerScreen<CraftingShapedContainer> implements IContainerListener {
    //Screen背景材质(宽：176 高：216)
    private final ResourceLocation CRAFTING_TABLE_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/crafting_table.png");

    //gui本地化
    TranslationTextComponent title = new TranslationTextComponent("gui."+DPRM.MOD_ID+".crafting_shaped.title");//Screen名称
    TranslationTextComponent recipeName = new TranslationTextComponent("gui."+DPRM.MOD_ID+".crafting_shaped.recipe_name");//配方名
    TranslationTextComponent groupName = new TranslationTextComponent("gui."+DPRM.MOD_ID+".crafting_shaped.group_name");//组名
    TranslationTextComponent addRecipe = new TranslationTextComponent("gui."+DPRM.MOD_ID+".crafting_shaped.add_recipe");//添加配方
    TranslationTextComponent addToDatapack = new TranslationTextComponent("gui."+DPRM.MOD_ID+".crafting_shaped.add_to_datapack");//添加至数据包
    TranslationTextComponent pleaseCompleteRecipeInfo = new TranslationTextComponent("gui."+DPRM.MOD_ID+".crafting_shaped.please_complete_recipe_info");//完善配方信息
    TranslationTextComponent recipeGenerateSuccessed = new TranslationTextComponent("gui."+DPRM.MOD_ID+".crafting_shaped.recipe_generate_successed");//数据包生成成功
    TextFieldWidget recipeNameInput;//配方名输入组件
    TextFieldWidget groupNameInput;//组名输入组件
    Button confirmBtn;//按钮

    Boolean isResultSlotEmpty = true;
    Boolean isCraftingSlotEmpty = true;
    Boolean isRecipeNameEmpty = true;
    Boolean isGroupNameEmpty = true;
    Boolean isRecipeJsonExist = false;

    private final CraftingShapedContainer craftingShapedContainer;

    //boolean
    boolean recipeNameIsEmpty = true;
    public ShapedCraftingSceen(CraftingShapedContainer craftingShapedContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(craftingShapedContainer, inv, titleIn);
        //设置ContainerScreen的尺寸
        this.xSize = 176;
        this.ySize = 166;
        this.craftingShapedContainer = craftingShapedContainer;

    }

    @Override
    protected void init() {
        super.init();

        //配方名输入框
        this.recipeNameInput = new TextFieldWidget(this.font, guiLeft - 82, guiTop + 12, 80, 12,recipeName.getString());//字体，位置，宽高，信息
        this.recipeNameInput.setTextColor(-1);
        this.recipeNameInput.setDisabledTextColour(-1);
        this.recipeNameInput.setEnableBackgroundDrawing(false);
        this.recipeNameInput.setMaxStringLength(35);//最大输入长度
        this.recipeNameInput.setResponder(this::recipeNameinputResponder);//每次输入后的回调函数
        this.children.add(this.recipeNameInput);
        //组名输入框
        this.groupNameInput = new TextFieldWidget(this.font, guiLeft - 82, guiTop + 25, 80, 12,groupName.getString());//字体，位置，宽高，信息
        this.groupNameInput.setTextColor(-1);
        this.groupNameInput.setDisabledTextColour(-1);
        this.groupNameInput.setEnableBackgroundDrawing(false);
        this.groupNameInput.setMaxStringLength(35);//最大输入长度
        this.groupNameInput.setResponder(this::groupNameinputResponder);//每次输入后的回调函数
        this.children.add(this.groupNameInput);

        //

        this.container.addListener(this);


        //添加按钮
        this.confirmBtn = new Button(this.guiLeft - 80, this.guiTop + 40, 70, 20, addRecipe.getString(), (button) -> {
            System.out.println("isCraftingSlotEmpty:"+isCraftingSlotEmpty);
            System.out.println("isGroupNameEmpty:"+ isGroupNameEmpty);
            System.out.println("isCraftingSlotEmpty:"+isCraftingSlotEmpty);
            System.out.println("isResultSlotEmpty:"+isResultSlotEmpty);



//            try {
//                String recipePath = JsonManager.createShapedCraftingRecipe(craftingShapedContainer.craftTableSlots, recipeNameInput.getText(), groupNameInput.getText(), recipeDirPath);
//                playerInventory.player.sendMessage(new StringTextComponent(recipeGenerateSuccessed.getFormattedText()));
//                playerInventory.player.sendMessage(new StringTextComponent(recipePath));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//            this.minecraft.player.closeScreen();
        });//位置，宽高，文字，按下后回调函数

        this.confirmBtn.active = true;//设置为禁用状态
        this.confirmBtn.changeFocus(true);//设置焦点
        this.addButton(confirmBtn);//添加到Screen

    }

    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);

        //渲染组件
        this.recipeNameInput.render(mouseX, mouseY, particleTick);
        this.confirmBtn.render(mouseX,mouseY,particleTick);
        //Tooltips
        this.renderHoveredToolTip(mouseX, mouseY);
        ArrayList<String> confirmBtnActiveToolTips = new ArrayList<>();
        ArrayList<String> confirmBtnDisableToolTips = new ArrayList<>();
        confirmBtnActiveToolTips.add(addToDatapack.getFormattedText());
        confirmBtnDisableToolTips.add(pleaseCompleteRecipeInfo.getFormattedText());
        if (this.confirmBtn.isHovered()){
            if(this.confirmBtn.active){
                this.renderTooltip(confirmBtnActiveToolTips,mouseX,mouseY);
            }else {
                this.renderTooltip(confirmBtnDisableToolTips,mouseX,mouseY);
            }
        }
        ArrayList<String> recipeNameInputTooltips = new ArrayList<>();
        recipeNameInputTooltips.add("配方名称");
        if (this.recipeNameInput.isHovered()&&!recipeNameInput.isFocused()){
            this.renderTooltip(recipeNameInputTooltips,mouseX,mouseY);
        }
        ArrayList<String> groupNameInputTooltips = new ArrayList<>();
        groupNameInputTooltips.add("配方名称");
        if (this.groupNameInput.isHovered()&&!groupNameInput.isFocused()){
            this.renderTooltip(groupNameInputTooltips,mouseX,mouseY);
        }
    }


    public void tick() {
        super.tick();
        Slot[] slots = craftingShapedContainer.craftTableSlots;
        isResultSlotEmpty = slots[0].getStack().isEmpty();//判断合成槽是否为空
        isCraftingSlotEmpty = true;//判断
        for (int i = 1; i <= 9; i++) {
            if(!slots[i].getStack().isEmpty())isCraftingSlotEmpty = false;
        }
        String recipeDirPath = this.craftingShapedContainer.getPacketBuffier().readString();
        File recipeJsonPath = new File(recipeDirPath + "//" + recipeNameInput.getText() + ".json");
        isRecipeJsonExist = recipeJsonPath.exists();

        this.confirmBtn.active = !isResultSlotEmpty && !isCraftingSlotEmpty && !isRecipeNameEmpty && !isGroupNameEmpty && !isRecipeJsonExist;
    }

    //移除监听器
    public void removed() {
        super.removed();
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.container.removeListener(this);
    }

    //关闭除Esc以及文字输入之外的其他键盘按键触发事件
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (p_keyPressed_1_ == 256) {
            this.minecraft.player.closeScreen();
        }

        return this.recipeNameInput.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_) || this.recipeNameInput.canWrite() || super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.renderBackground();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(CRAFTING_TABLE_TEXTURE);
        int textureWidth = 176;
        int textureHeight = 216;
        //背景渲染
        blit(guiLeft, guiTop, 0, 0, 176, 166, textureWidth, textureHeight);
        //配方名输入框背景渲染
        blit(guiLeft - 85, guiTop + 8, 0, 166 + (this.recipeNameInput.isFocused() ? 0 : 16), 80, 15, textureWidth, textureHeight);
        //组名输入框背景渲染
        blit(guiLeft - 85, guiTop + 24, 0, 166 + (this.groupNameInput.isFocused() ? 0 : 16), 80, 15, textureWidth, textureHeight);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font.drawString(this.title.getString(), 28.0F, 6.0F, 4210752);

    }

    private void recipeNameinputResponder(String inputText) {
        isRecipeNameEmpty = recipeNameInput.getText().isEmpty();
    }
    private void groupNameinputResponder(String inputText) {
        isGroupNameEmpty = groupNameInput.getText().isEmpty();
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

    public CraftingShapedContainer getCraftingShapedContainer() {
        return craftingShapedContainer;
    }
}
