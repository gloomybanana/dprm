package org.gloomybanana.DPRM.Screen;

import com.alibaba.fastjson.JSONObject;
import net.minecraft.client.gui.widget.button.Button;
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

    public BlastingScreen(BlastingContainer blastingContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(blastingContainer, inv, titleIn);
        this.SCREEN_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/blasting.png");
    }
    @Override
    protected void init() {
        //侧边栏组件初始化
        super.init();
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
        this.confirmBtn.active = !isResultSlotEmpty && !isCraftingSlotEmpty && !isRecipeNameEmpty && !isGroupNameEmpty && !isRecipeJsonExist;
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        //绘制Container背景,侧边栏组件
        super.drawGuiContainerBackgroundLayer(partialTicks,mouseX,mouseY);
        //高炉图标
        this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(Items.BLAST_FURNACE),guiLeft+56,guiTop+54);
    }
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        //绘制背包名称
        super.drawGuiContainerForegroundLayer(mouseX,mouseY);
        String s = this.title.getFormattedText();
        this.font.drawString(s, (float)(this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 4210752);
    }
    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);
    }
    @Override
    public void onConfirmBtnPress(Button button) {
        JSONObject blastingRecipe = JsonManager.genBlastingRecipe(container.furnaceSlots, groupNameInput.getText(),0.35d,200);
        JSONObject result = JsonManager.createJsonFile(jsonPacket,blastingRecipe,recipeNameInput.getText());
        if (result.getBoolean("success")){
            System.out.println(result.getString("dir"));
            playerInventory.player.sendMessage(new TranslationTextComponent("gui."+DPRM.MOD_ID+".chat.recipe_generate_successed",result.getString("dir")));
        }else {
            playerInventory.player.sendMessage(new TranslationTextComponent("gui."+DPRM.MOD_ID+".chat.recipe_generate_failed",result.getString("dir")));
        }
        this.minecraft.player.closeScreen();
    }
}
