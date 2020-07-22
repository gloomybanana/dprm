package org.gloomybanana.DPRM.Screen;

import com.alibaba.fastjson.JSONObject;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.BlastingContainer;
import org.gloomybanana.DPRM.file.JsonManager;

public class BlastingScreen extends AbstractRecipeMakerScreen<BlastingContainer>{
    Boolean isResultSlotEmpty = true;
    Boolean isCraftingSlotEmpty = true;
    Boolean isExperienceInputValid = false;
    Boolean isCookingTimeInputValid = false;

    TextFieldWidget experienceInput;
    TextFieldWidget cookingTimeInput;
    public BlastingScreen(BlastingContainer blastingContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(blastingContainer, inv, titleIn);
        this.SCREEN_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/furnance.png");
    }
    @Override
    protected void init() {
        //侧边栏组件初始化
        super.init();
        //经验
        this.experienceInput = new TextFieldWidget(this.font, guiLeft - 82, guiTop + 47, 24, 12, EXPERIENCE.getString());
        this.experienceInput.setCanLoseFocus(true);
        this.experienceInput.setTextColor(-1);
        this.experienceInput.setDisabledTextColour(0x808080);
        this.experienceInput.setEnableBackgroundDrawing(false);
        this.experienceInput.setMaxStringLength(10);
        this.experienceInput.setResponder(this::experienceInputOnWrite);
        experienceInput.setText("0.35");
        this.children.add(this.experienceInput);
        //时间
        this.cookingTimeInput = new TextFieldWidget(this.font, guiLeft - 82, guiTop + 63, 24, 12,COOKING_TIME.getString() );
        this.cookingTimeInput.setCanLoseFocus(true);
        this.cookingTimeInput.setTextColor(-1);
        this.cookingTimeInput.setDisabledTextColour(0x808080);
        this.cookingTimeInput.setEnableBackgroundDrawing(false);
        this.cookingTimeInput.setMaxStringLength(10);
        this.cookingTimeInput.setResponder(this::cookingTimeInputOnWrite);
        cookingTimeInput.setText("200");
        this.children.add(this.cookingTimeInput);
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
        this.confirmBtn.active = !isResultSlotEmpty && !isCraftingSlotEmpty && !isRecipeNameEmpty && !isGroupNameEmpty && !isRecipeJsonExist &&isCookingTimeInputValid&&isExperienceInputValid;
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        //绘制Container背景,侧边栏组件
        super.drawGuiContainerBackgroundLayer(partialTicks,mouseX,mouseY);
        this.minecraft.getTextureManager().bindTexture(this.SCREEN_TEXTURE);
        //烧制时间、经验背景框
        blit( guiLeft - 85,  guiTop + 43, 0, 198, 24, 15, textureWidth, textureHeight);
        blit( guiLeft - 85,  guiTop + 59, 0, 198, 24, 15, textureWidth, textureHeight);
        //经验,时间文字
        this.font.drawString(I18n.format("gui.dprm.furnance.experience"), guiLeft-59, guiTop+46, 0xFF222222);
        this.font.drawString(I18n.format("gui.dprm.furnance.cooking_time"), guiLeft-59, guiTop+62,0xFF222222);
        //高炉图标
        this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(Items.BLAST_FURNACE),guiLeft+56,guiTop+54);

    }
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        //绘制背包名称
        super.drawGuiContainerForegroundLayer(mouseX,mouseY);
        //标题文字
        String s = this.title.getFormattedText();
        this.font.drawString(s, (float)(this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 4210752);
    }
    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);
        this.experienceInput.render(mouseX,mouseY,particleTick);
        this.cookingTimeInput.render(mouseX,mouseY,particleTick);

    }
    @Override
    public void onConfirmBtnPress(Button button) {
        Ints.tryParse(cookingTimeInput.getText());
        Doubles.tryParse(experienceInput.getText());
        JSONObject blastingRecipe = JsonManager.genBlastingRecipe(container.furnaceSlots, groupNameInput.getText(),Doubles.tryParse(experienceInput.getText()),Ints.tryParse(cookingTimeInput.getText()));
        JSONObject result = JsonManager.createJsonFile(jsonPacket,blastingRecipe,recipeNameInput.getText());
        if (result.getBoolean("success")){
            playerInventory.player.sendMessage(new TranslationTextComponent("gui."+DPRM.MOD_ID+".chat.recipe_generate_successed",result.getString("dir")));
        }else {
            playerInventory.player.sendMessage(new TranslationTextComponent("gui."+DPRM.MOD_ID+".chat.recipe_generate_failed",result.getString("dir")));
        }
//        this.minecraft.player.closeScreen();
    }
}
