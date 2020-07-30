package org.gloomybanana.DPRM.Screen.vanilla;

import com.alibaba.fastjson.JSONObject;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.vanilla.FurnaceContainer;
import org.gloomybanana.DPRM.file.VanillaRecipeJson;
import org.gloomybanana.DPRM.network.ScreenToggle;
import org.gloomybanana.DPRM.network.Networking;
import org.gloomybanana.DPRM.network.CRUDRecipe;
import org.gloomybanana.DPRM.widget.ToggleFurnaceTypeButton;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class FurnaceScreen extends ScreenWithRecipeInfo<FurnaceContainer> {
    Boolean isResultSlotEmpty = true;
    Boolean isCraftingSlotEmpty = true;
    Boolean isExperienceInputValid = false;
    Boolean isCookingTimeInputValid = false;

    TextFieldWidget experienceInput;
    TextFieldWidget cookingTimeInput;
    ToggleFurnaceTypeButton.Type currentType = ToggleFurnaceTypeButton.Type.smelting;
    ToggleFurnaceTypeButton toggleFurnaceBtn;

    public FurnaceScreen(FurnaceContainer furnaceContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(furnaceContainer, inv, titleIn);
        this.SIDE_INFO_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/furnance.png");
    }
    @Override
    protected void init() {
        //侧边栏组件初始化
        super.init();
        //经验
        this.experienceInput = new TextFieldWidget(this.font, guiLeft - 82, guiTop + 47, 24, 12, I18n.format("gui."+DPRM.MOD_ID+".furnance.experience"));
        this.experienceInput.setCanLoseFocus(true);
        this.experienceInput.setTextColor(-1);
        this.experienceInput.setDisabledTextColour(0x808080);
        this.experienceInput.setEnableBackgroundDrawing(false);
        this.experienceInput.setMaxStringLength(10);
        this.experienceInput.setResponder(this::experienceInputOnWrite);
        experienceInput.setText("0.35");
        this.children.add(this.experienceInput);
        //时间
        this.cookingTimeInput = new TextFieldWidget(this.font, guiLeft - 82, guiTop + 63, 24, 12,I18n.format("gui."+DPRM.MOD_ID+".furnance.cooking_time"));
        this.cookingTimeInput.setCanLoseFocus(true);
        this.cookingTimeInput.setTextColor(-1);
        this.cookingTimeInput.setDisabledTextColour(0x808080);
        this.cookingTimeInput.setEnableBackgroundDrawing(false);
        this.cookingTimeInput.setMaxStringLength(8);
        this.cookingTimeInput.setResponder(this::cookingTimeInputOnWrite);
        cookingTimeInput.setText("200");
        this.children.add(this.cookingTimeInput);

        this.toggleFurnaceBtn = new ToggleFurnaceTypeButton(guiLeft+56,guiTop+54,20,20,this::changeFurnaceType, currentType);
        this.children.add(toggleFurnaceBtn);
    }

    private void changeFurnaceType(Button button) {
        ((ToggleFurnaceTypeButton) button).toggle();
        int ordinal = currentType.ordinal();
        ordinal++;
        if (ordinal > 3) ordinal = 0;
        currentType = ToggleFurnaceTypeButton.Type.values()[ordinal];
    }

    private void cookingTimeInputOnWrite(String s) {
        isCookingTimeInputValid = Ints.tryParse(s) != null;
        if (isCookingTimeInputValid) {
            cookingTimeInput.setTextColor(-1);
        } else {
            cookingTimeInput.setTextColor(0xff0000);
        }
    }

    private void experienceInputOnWrite(String s) {
        isExperienceInputValid = Doubles.tryParse(s) != null;
        if (isExperienceInputValid) {
            experienceInput.setTextColor(-1);
        } else {
            experienceInput.setTextColor(0xff0000);
        }
    }

    public void tick() {
        //侧边栏isRecipeJsonExist,isRecipeNameEmpty,isGroupNameEmpty判断
        super.tick();
        //Container插槽判断
        Slot[] slots = container.furnaceSlots;
        isResultSlotEmpty = slots[0].getStack().isEmpty();//判断合成槽是否为空
        isCraftingSlotEmpty = true;//判断
        for (int i = 1; i <= 1; i++) {
            if(!slots[i].getStack().isEmpty())isCraftingSlotEmpty = false;
        }
        //按钮是否激活
        this.confirmBtn.active = !isResultSlotEmpty && !isCraftingSlotEmpty && !isRecipeNameEmpty && !isGroupNameEmpty &&isCookingTimeInputValid&&isExperienceInputValid;
    }

    Boolean isToggleBtnHovered = false;
    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        super.renderHoveredToolTip(mouseX, mouseY);
        //判断是否悬停在切换按钮
        List<String> toggleFurnaceToolTips = new ArrayList<>();
        toggleFurnaceToolTips.add(I18n.format("gui."+ DPRM.MOD_ID+".toggle_furnace_type"));
        if ((mouseX>=guiLeft+55)&&(mouseX<=guiLeft+55+18)&&(mouseY>=guiTop+53)&&(mouseY<=guiTop+53+18)){
            this.renderTooltip(toggleFurnaceToolTips,mouseX,mouseY);
            isToggleBtnHovered = true;
        }else isToggleBtnHovered =false;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        //绘制Container背景,侧边栏组件
        super.drawGuiContainerBackgroundLayer(partialTicks,mouseX,mouseY);
        this.minecraft.getTextureManager().bindTexture(this.SIDE_INFO_TEXTURE);
        //烧制时间、经验背景框
        blit( guiLeft - 85,  guiTop + 43, 0, 198, 24, 15, textureWidth, textureHeight);
        blit( guiLeft - 85,  guiTop + 59, 0, 198, 24, 15, textureWidth, textureHeight);
        //经验,时间文字
        if (isToggleBtnHovered)blit(guiLeft+54,guiTop+52,176,0,20,20,textureWidth,textureHeight);
        this.font.drawString(I18n.format("gui."+DPRM.MOD_ID+".furnance.experience"), guiLeft-59, guiTop+46, 0xFF222222);
        this.font.drawString(I18n.format("gui."+DPRM.MOD_ID+".furnance.cooking_time"), guiLeft-59, guiTop+62,0xFF222222);
        //图标
        this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(currentType.getIcon(),guiLeft+56,guiTop+54);
    }
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        //绘制背包名称
        super.drawGuiContainerForegroundLayer(mouseX,mouseY);
        //标题文字
        String s = currentType.getTitle();
        this.font.drawString(s, (float)(this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 4210752);
    }
    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);
        this.experienceInput.render(mouseX,mouseY,particleTick);
        this.cookingTimeInput.render(mouseX,mouseY,particleTick);
        if (isRecipeJsonExist){
            renderFakeRecipe();
            if (recipeNameInput!=null){
                if (recipeNameInput.isFocused()){
                    if (currentRecipe.getString("type").equals("smelting")) currentType = ToggleFurnaceTypeButton.Type.smelting;
                    if (currentRecipe.getString("type").equals("blasting")) currentType = ToggleFurnaceTypeButton.Type.blasting;
                    if (currentRecipe.getString("type").equals("smoking")) currentType = ToggleFurnaceTypeButton.Type.smoking;
                    if (currentRecipe.getString("type").equals("campfire_cooking")) currentType = ToggleFurnaceTypeButton.Type.campfire_cooking;
                }
            }
        }

    }

    private void renderFakeRecipe() {
        if (currentRecipe==null) return;
        String type = currentRecipe.getString("type");
        if (type.equals("smelting")||type.equals("blasting")||type.equals("smoking")||type.equals("campfire_cooking")){
            String ingredientItemRegistryName = currentRecipe.getJSONObject("ingredient").getString("item");
            String resultRegistryName = currentRecipe.getString("result");
            ItemStack ingredientItemStack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ingredientItemRegistryName)));
            ItemStack resultItemStack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(resultRegistryName)));
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(ingredientItemStack,guiLeft+56,guiTop+17);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(resultItemStack,guiLeft+116,guiTop+35);
        }
    }

    @Override
    public void onConfirmBtnPress(Button button) {
        String furnaceType = currentType.name();
        JSONObject blastingRecipe = VanillaRecipeJson.genFurnaceRecipe(container.furnaceSlots, groupNameInput.getText(),Doubles.tryParse(experienceInput.getText()),Ints.tryParse(cookingTimeInput.getText()),furnaceType);
        jsonPacket.put("json_recipe",blastingRecipe);
        jsonPacket.put("recipe_name",recipeNameInput.getText());
        jsonPacket.put("crud","create");
        Networking.INSTANCE.sendToServer(new CRUDRecipe(jsonPacket.toJSONString()));

        jsonPacket.put("operate","open_furnace_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
//        this.minecraft.player.closeScreen();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifier) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.minecraft.player.closeScreen();
        }
        return this.recipeNameInput.keyPressed(keyCode, scanCode, modifier) || this.recipeNameInput.canWrite()
                || this.groupNameInput.keyPressed(keyCode, scanCode, modifier) || this.groupNameInput.canWrite()
                || this.experienceInput.keyPressed(keyCode, scanCode, modifier) || this.experienceInput.canWrite()
                || this.cookingTimeInput.keyPressed(keyCode, scanCode, modifier) || this.cookingTimeInput.canWrite()
                || super.keyPressed(keyCode, scanCode, modifier);
    }


    @Override
    public void removed() {
        super.removed();
    }
    public void deleteRecipe(Button button) {
        jsonPacket.put("crud","delete");
        jsonPacket.put("select_recipe_name",recipeNameInput.getText());
        Networking.INSTANCE.sendToServer(new CRUDRecipe(jsonPacket.toJSONString()));
        jsonPacket.put("select_recipe_name","");
        jsonPacket.put("operate","open_furnace_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }
}
