package org.gloomybanana.DPRM.Screen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.TranslationTextComponent;
import org.gloomybanana.DPRM.DPRM;
import net.minecraft.util.NonNullList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.gloomybanana.DPRM.container.StonecuttingContainer;
import org.gloomybanana.DPRM.file.JsonManager;

import java.io.File;
import java.util.ArrayList;

public class StonecuttingScreen extends ContainerScreen<StonecuttingContainer> implements IContainerListener {
    private final ResourceLocation STONECUTTING_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/stonecutting.png");
    private final StonecuttingContainer stonecuttingContainer;
    JSONObject jsonPacket = new JSONObject();

    //gui本地化
    TranslationTextComponent RECIPE_NAME = new TranslationTextComponent("gui."+DPRM.MOD_ID+".stonecutting.recipe_name");//配方名
    TranslationTextComponent GROUP_NAME = new TranslationTextComponent("gui."+DPRM.MOD_ID+".stonecutting.group_name");//组名
    TranslationTextComponent ADD_RECIPE = new TranslationTextComponent("gui."+DPRM.MOD_ID+".stonecutting.add_recipe");//添加配方
    TranslationTextComponent ADD_TO_DATAPACK = new TranslationTextComponent("gui."+DPRM.MOD_ID+".stonecutting.add_to_datapack");//添加至数据包
    TranslationTextComponent PLEASE_COMPLETE_RECIPE_INFO = new TranslationTextComponent("gui."+DPRM.MOD_ID+".stonecutting.please_complete_recipe_info");//完善配方信息

    //组件
    TextFieldWidget recipeNameInput;//配方名输入组件
    TextFieldWidget groupNameInput;//组名输入组件
    Button confirmBtn;//按钮

    //提交按钮判断条件
    Boolean isResultSlotEmpty = true;
    Boolean isCraftingSlotEmpty = true;
    Boolean isRecipeNameEmpty = true;
    Boolean isGroupNameEmpty = true;
    Boolean isRecipeJsonExist = false;

    public StonecuttingScreen(StonecuttingContainer stonecuttingContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(stonecuttingContainer, inv, titleIn);
        this.stonecuttingContainer = stonecuttingContainer;
        this.jsonPacket = JSON.parseObject(stonecuttingContainer.getPacketBuffier().readString());
    }

    @Override
    protected void init() {
        super.init();
        //配方名输入框
        this.recipeNameInput = new TextFieldWidget(this.font, guiLeft - 82, guiTop + 15, 80, 12, RECIPE_NAME.getString());//字体，位置，宽高，信息
        this.recipeNameInput.setTextColor(-1);
        this.recipeNameInput.setDisabledTextColour(-1);
        this.recipeNameInput.setEnableBackgroundDrawing(false);
        this.recipeNameInput.setMaxStringLength(35);//最大输入长度
        this.recipeNameInput.setCanLoseFocus(true);
        this.recipeNameInput.setResponder(this::recipeNameinputResponder);//每次输入后的回调函数
        this.children.add(this.recipeNameInput);
        //组名输入框
        this.groupNameInput = new TextFieldWidget(this.font, guiLeft - 82, guiTop + 31, 80, 12, GROUP_NAME.getString());//字体，位置，宽高，信息
        this.groupNameInput.setTextColor(-1);
        this.groupNameInput.setDisabledTextColour(-1);
        this.groupNameInput.setEnableBackgroundDrawing(false);
        this.groupNameInput.setMaxStringLength(35);//最大输入长度
        this.groupNameInput.setCanLoseFocus(true);
        this.groupNameInput.setResponder(this::groupNameinputResponder);//每次输入后的回调函数
        this.children.add(this.groupNameInput);
        //监听器
        this.container.addListener(this);

        //按钮初始化
//        this.confirmBtn = new Button(this.guiLeft - 85, this.guiTop + 85, 80, 20, ADD_RECIPE.getString(), (button) -> {
//            System.out.println("isCraftingSlotEmpty:"+isCraftingSlotEmpty);
//            System.out.println("isGroupNameEmpty:"+ isGroupNameEmpty);
//            System.out.println("isCraftingSlotEmpty:"+isCraftingSlotEmpty);
//            System.out.println("isResultSlotEmpty:"+isResultSlotEmpty);
//            System.out.println("isRecipeJsonExist"+isRecipeJsonExist);
//
//            JSONObject stonecuttingRecipe = JsonManager.genStonecuttingRecipe(stonecuttingContainer., groupNameInput.getText());
//            JSONObject result = JsonManager.createJsonFile(jsonPacket,stonecuttingRecipe,recipeNameInput.getText());
//            if (result.getBoolean("success")){
//                System.out.println(result.getString("dir"));
//                playerInventory.player.sendMessage(new TranslationTextComponent("gui."+DPRM.MOD_ID+".crafting_shaped.recipe_generate_successed",result.getString("dir")));
//            }else {
//                playerInventory.player.sendMessage(new TranslationTextComponent("gui."+DPRM.MOD_ID+".crafting_shaped.recipe_generate_failed",result.getString("dir")));
//            }
//            this.minecraft.player.closeScreen();
//
//        });//位置，宽高，文字，按下后回调函数
        this.confirmBtn.active = true;//设置为禁用状态
        this.addButton(confirmBtn);//添加到Screen
    }
//    public void tick() {
//        super.tick();
//        Slot[] slots = stonecuttingContainer.craftTableSlots;
//        isResultSlotEmpty = slots[0].getStack().isEmpty();//判断合成槽是否为空
//        isCraftingSlotEmpty = true;//判断
//        for (int i = 1; i <= 9; i++) {
//            if(!slots[i].getStack().isEmpty())isCraftingSlotEmpty = false;
//        }
//        String datapacks_dir_path = jsonPacket.getString("datapacks_dir_path");
//        String player_name = jsonPacket.getString("player_name");
//        File recipeJsonPath = new File(datapacks_dir_path + "//add_by_"+player_name+"//data//minecraft//recipes//" + recipeNameInput.getText() + ".json");
//        isRecipeJsonExist = recipeJsonPath.exists();
//        this.confirmBtn.active = !isResultSlotEmpty && !isCraftingSlotEmpty && !isRecipeNameEmpty && !isGroupNameEmpty && !isRecipeJsonExist;
//    }
    private void recipeNameinputResponder(String inputText) {
        isRecipeNameEmpty = recipeNameInput.getText().isEmpty();
    }
    private void groupNameinputResponder(String inputText) {
        isGroupNameEmpty = groupNameInput.getText().isEmpty();
    }
    //移除监听器
    public void removed() {
        super.removed();
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.container.removeListener(this);
    }
    //调整游戏界面大小时重新初始化界面
    public void resize(Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
        String s = this.recipeNameInput.getText();
        String g = this.groupNameInput.getText();
        this.init(p_resize_1_, p_resize_2_, p_resize_3_);
        this.recipeNameInput.setText(s);
        this.groupNameInput.setText(g);
    }
    //关闭除Esc以及文字输入之外的其他键盘按键触发事件
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (p_keyPressed_1_ == 256) {
            this.minecraft.player.closeScreen();
        }
        return this.recipeNameInput.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_) || this.recipeNameInput.canWrite() || super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)||this.groupNameInput.canWrite()||this.groupNameInput.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.renderBackground();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(STONECUTTING_TEXTURE);
        int textureWidth = 300;
        int textureHeight = 300;
        //背景渲染
        blit(guiLeft, guiTop, 0, 0, 176, 166, textureWidth, textureHeight);
        //侧边栏背景渲染
        blit(guiLeft - 98, guiTop + 4, 202, 0, 98, 107, textureWidth, textureHeight);
        //配方名输入框背景渲染
        blit(guiLeft - 85, guiTop + 11, 0, 166 + (this.recipeNameInput.isFocused() ? 0 : 16), 80, 15, textureWidth, textureHeight);
        //组名输入框背景渲染
        blit(guiLeft - 85, guiTop + 27, 0, 166 + (this.groupNameInput.isFocused() ? 0 : 16), 80, 15, textureWidth, textureHeight);
        //输入框placeholder
        if (isRecipeNameEmpty)this.font.drawString(recipeNameInput.getMessage(),guiLeft - 82,guiTop + 14, 0xFFEEEEEE);
        if (isGroupNameEmpty)this.font.drawString(recipeNameInput.getMessage(),guiLeft - 82,guiTop + 30,0xFFEEEEEE);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 4.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 94), 4210752);
    }

    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);
        //渲染组件
        this.recipeNameInput.render(mouseX, mouseY, particleTick);
        this.groupNameInput.render(mouseX, mouseY, particleTick);
        this.confirmBtn.render(mouseX,mouseY,particleTick);
        //渲染Tooltips
        this.renderHoveredToolTip(mouseX, mouseY);
        ArrayList<String> confirmBtnActiveToolTips = new ArrayList<>();
        ArrayList<String> confirmBtnDisableToolTips = new ArrayList<>();
        confirmBtnActiveToolTips.add(ADD_TO_DATAPACK.getFormattedText());
        confirmBtnDisableToolTips.add(PLEASE_COMPLETE_RECIPE_INFO.getFormattedText());
        if (this.confirmBtn.isHovered()&&!groupNameInput.isFocused()){
            if(this.confirmBtn.active){
                this.renderTooltip(confirmBtnActiveToolTips,mouseX,mouseY);
            }
        }
        ArrayList<String> recipeNameInputTooltips = new ArrayList<>();
        recipeNameInputTooltips.add(recipeNameInput.getMessage());
        if (this.recipeNameInput.isHovered()&&!recipeNameInput.isFocused()){
            this.renderTooltip(recipeNameInputTooltips,mouseX,mouseY);
        }
        ArrayList<String> groupNameInputTooltips = new ArrayList<>();
        groupNameInputTooltips.add(groupNameInput.getMessage());
        if (this.groupNameInput.isHovered()&&!groupNameInput.isFocused()){
            this.renderTooltip(groupNameInputTooltips,mouseX,mouseY);
        }
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
