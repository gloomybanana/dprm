package org.gloomybanana.DPRM.Screen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.AbstractRecipeContainer;
import org.gloomybanana.DPRM.network.Networking;
import org.gloomybanana.DPRM.network.ScreenToggle;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class AbstractRecipeMakerScreen<T extends AbstractRecipeContainer> extends ContainerScreen<T> implements IContainerListener {
    protected final T container;
    protected final JSONObject jsonPacket;
    protected ResourceLocation SCREEN_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/empty_recipe_screen.png");
    protected final ResourceLocation BACK_TO_RECIPE_LIST_BTN = new ResourceLocation(DPRM.MOD_ID,"textures/gui/back_to_recipe_list_btn.png");
    protected final ResourceLocation DELETE_BTN = new ResourceLocation(DPRM.MOD_ID,"textures/gui/delete_btn.png");
    protected final Integer textureWidth = 300;
    protected final Integer textureHeight = 300;
    public JSONObject currentRecipe = new JSONObject();

    //组件
    TextFieldWidget recipeNameInput;//配方名输入组件
    TextFieldWidget groupNameInput;//组名输入组件
    Button confirmBtn;//按钮
    protected ImageButton backBtn;
    protected ImageButton deleteBtn;

    //提交按钮判断条件
    Boolean isRecipeNameEmpty = true;
    Boolean isGroupNameEmpty = true;
    Boolean isRecipeJsonExist = false;

    public AbstractRecipeMakerScreen(T container, PlayerInventory inv, ITextComponent titleIn) {
        super(container, inv, titleIn);
        this.container =container;
        this.jsonPacket = JSON.parseObject(container.getPacketBuffier().readString());
        DPRM.LOGGER.info("Send From Server:"+this.jsonPacket);
    }

    @Override
    protected void init() {
        super.init();
        //配方名输入框
        this.recipeNameInput = new TextFieldWidget(this.font, guiLeft - 82, guiTop + 15, 80, 12, I18n.format("gui."+ DPRM.MOD_ID+".recipe_info.recipe_name"));//字体，位置，宽高，信息
        this.recipeNameInput.setTextColor(-1);
        this.recipeNameInput.setDisabledTextColour(-1);
        this.recipeNameInput.setEnableBackgroundDrawing(false);
        this.recipeNameInput.setMaxStringLength(35);//最大输入长度
        this.recipeNameInput.setCanLoseFocus(true);
        this.recipeNameInput.setText(jsonPacket.getString("select_recipe_name"));
        this.recipeNameInput.setResponder(this::recipeNameinputResponder);//每次输入后的回调函数
        //组名输入框
        this.groupNameInput = new TextFieldWidget(this.font, guiLeft - 82, guiTop + 31, 80, 12, I18n.format("gui."+DPRM.MOD_ID+".recipe_info.group_name"));//字体，位置，宽高，信息
        this.groupNameInput.setTextColor(-1);
        this.groupNameInput.setDisabledTextColour(-1);
        this.groupNameInput.setEnableBackgroundDrawing(false);
        this.groupNameInput.setMaxStringLength(35);//最大输入长度
        this.groupNameInput.setCanLoseFocus(true);
        this.groupNameInput.setResponder(this::groupNameinputResponder);//每次输入后的回调函数
        //监听器
        this.container.addListener(this);
        //按钮初始化
        this.confirmBtn = new Button(this.guiLeft - 85, this.guiTop + 85, 80, 20, I18n.format("gui."+DPRM.MOD_ID+".recipe_info.add_recipe"), this::onConfirmBtnPress);//位置，宽高，文字，按下后回调函数
        this.backBtn = new ImageButton(guiLeft + 145, guiTop + 5, 26, 16, 0, 0, 16, BACK_TO_RECIPE_LIST_BTN, this::backToRecipeList);
        this.deleteBtn = new ImageButton(guiLeft + 5, guiTop + 5, 16, 16, 0, 0, 16, DELETE_BTN, this::deleteRecipe);


        this.children.add(this.recipeNameInput);
        this.children.add(this.groupNameInput);
        this.addButton(confirmBtn);
        this.addButton(backBtn);
        this.addButton(deleteBtn);
    }

    public void deleteRecipe(Button button) {

    }

    private void backToRecipeList(Button button) {
        jsonPacket.put("operate","open_recipe_list_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }

    public void tick() {
        super.tick();
        isRecipeJsonExist = false;
        JSONArray recipe_list = jsonPacket.getJSONArray("recipe_list");
        for (int i = 0; i < recipe_list.size(); i++) {
            JSONObject recipe = recipe_list.getJSONObject(i);
            String recipe_name = recipe.getString("recipe_name");
            if (recipe_name.equals(recipeNameInput.getText())){
                isRecipeJsonExist = true;
                currentRecipe = recipe.getJSONObject("content");
            }
        }
        if (isRecipeJsonExist) {
            this.confirmBtn.setMessage(I18n.format("gui."+DPRM.MOD_ID+".recipe_info.update_recipe"));

        } else {
            this.confirmBtn.setMessage(I18n.format("gui."+DPRM.MOD_ID+".recipe_info.add_recipe"));
        }
        deleteBtn.visible = isRecipeJsonExist;
        deleteBtn.active = isRecipeJsonExist;
    }
    private void recipeNameinputResponder(String inputText) {
        isRecipeNameEmpty = recipeNameInput.getText().isEmpty();
    }
    private void groupNameinputResponder(String inputText) {
        isGroupNameEmpty = groupNameInput.getText().isEmpty();
    }

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
    public boolean keyPressed(int keyCode, int scanCode, int modifier) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.minecraft.player.closeScreen();
        }
        return this.recipeNameInput.keyPressed(keyCode, scanCode, modifier) || this.recipeNameInput.canWrite()
            || this.groupNameInput.keyPressed(keyCode, scanCode, modifier) || this.groupNameInput.canWrite()
            || super.keyPressed(keyCode, scanCode, modifier);
    }

    //渲染背景
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.renderBackground();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(SCREEN_TEXTURE);
        //背景渲染
        blit(guiLeft, guiTop, 0, 0,176, 166, textureWidth, textureHeight);
        //侧边栏背景渲染
        blit(guiLeft - 98, guiTop + 4,202, 0, 98, 107, textureWidth, textureHeight);
        //配方名输入框背景渲染
        blit(guiLeft - 85, guiTop + 11, 0, 166 + (this.recipeNameInput.isFocused() ? 0 : 16), 80, 15, textureWidth, textureHeight);
        //组名输入框背景渲染
        blit(guiLeft - 85, guiTop + 27, 0, 166 + (this.groupNameInput.isFocused() ? 0 : 16), 80, 15, textureWidth, textureHeight);
        //输入框placeholder
        if (isRecipeNameEmpty)this.font.drawString(recipeNameInput.getMessage(),guiLeft - 82,guiTop + 14, 0xFF777777);
        if (isGroupNameEmpty)this.font.drawString(groupNameInput.getMessage(),guiLeft - 82,guiTop + 30,0xFF777777);
    }
    //渲染GUI内静态文字
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
    }

    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);
        //渲染组件
        this.recipeNameInput.render(mouseX, mouseY, particleTick);
        this.groupNameInput.render(mouseX, mouseY, particleTick);
        this.confirmBtn.render(mouseX,mouseY,particleTick);
        this.backBtn.render(mouseX,mouseY,particleTick);
        this.deleteBtn.render(mouseX,mouseY,particleTick);
        //渲染Tooltips
        this.renderHoveredToolTip(mouseX, mouseY);
        ArrayList<String> confirmBtnActiveToolTips = new ArrayList<>();
        ArrayList<String> confirmBtnDisableToolTips = new ArrayList<>();
        confirmBtnActiveToolTips.add(I18n.format("gui."+DPRM.MOD_ID+".recipe_info.add_to_datapack"));
        confirmBtnDisableToolTips.add(I18n.format("gui."+DPRM.MOD_ID+".recipe_info.please_complete_recipe_info"));
        if (this.confirmBtn.isHovered()&&!groupNameInput.isFocused()){
            if(this.confirmBtn.active){
                this.renderTooltip(confirmBtnActiveToolTips,mouseX,mouseY);
            }else this.renderTooltip(confirmBtnDisableToolTips,mouseX,mouseY);
        }
        ArrayList<String> recipeNameInputTooltips = new ArrayList<>();
        recipeNameInputTooltips.add(recipeNameInput.getMessage());
        if (this.recipeNameInput.isHovered()&&!recipeNameInput.isFocused()){
            this.renderTooltip(recipeNameInputTooltips,mouseX,mouseY);
        }else if (this.recipeNameInput.isHovered()&&recipeNameInput.isFocused()){
            if (isRecipeJsonExist){
                String currentType = currentRecipe.getString("type");
                this.renderTooltip(getCurrentTypeTooltips(currentType),mouseX,mouseY);
            }
        }
        ArrayList<String> groupNameInputTooltips = new ArrayList<>();
        groupNameInputTooltips.add(groupNameInput.getMessage());
        if (this.groupNameInput.isHovered()&&!groupNameInput.isFocused()){
            this.renderTooltip(groupNameInputTooltips,mouseX,mouseY);
        }

        ArrayList<String> backBtnTooltips = new ArrayList<>();
        backBtnTooltips.add(I18n.format("gui."+DPRM.MOD_ID+".recipe_info.back_to_recipe_list"));
        if (this.backBtn.isHovered()){
            this.renderTooltip(backBtnTooltips,mouseX,mouseY);
        }

        ArrayList<String> deleteBtnTooltips = new ArrayList<>();
        deleteBtnTooltips.add(I18n.format("gui."+DPRM.MOD_ID+".recipe_info.delete"));
        if (this.deleteBtn.isHovered()){
            this.renderTooltip(deleteBtnTooltips,mouseX,mouseY);
        }
    }

    private List<String> getCurrentTypeTooltips(String currentType) {
        List<String> tooltips = new ArrayList<>();
        switch (currentType) {
            case "minecraft:crafting_shaped":
            case "minecraft:crafting_shapeless":
                tooltips.add(I18n.format("gui." + DPRM.MOD_ID + ".this_is_a_crafting_recipe"));
                return tooltips;
            case "smelting":
            case "smoking":
            case "blasting":
            case "campfire_cooking":
                tooltips.add(I18n.format("gui." + DPRM.MOD_ID + ".this_is_a_furnace_recipe"));
                return tooltips;
            case "stonecutting":
                tooltips.add(I18n.format("gui." + DPRM.MOD_ID + ".this_is_a_stonecutting_recipe"));
                return tooltips;
            default:
                tooltips.add("other");
                break;
        }
        return tooltips;
    }


    //确认按钮
    public void onConfirmBtnPress(Button button){

    };

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
