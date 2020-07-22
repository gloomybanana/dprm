package org.gloomybanana.DPRM.Screen;

import com.alibaba.fastjson.JSONObject;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Items;
import net.minecraft.util.text.TranslationTextComponent;
import org.gloomybanana.DPRM.DPRM;
import net.minecraft.util.NonNullList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.gloomybanana.DPRM.container.CampfireCookingContainer;
import org.gloomybanana.DPRM.file.JsonManager;

public class CampfireCookingScreen extends AbstractRecipeMakerScreen<CampfireCookingContainer> {

    Boolean isResultSlotEmpty;
    Boolean isCraftingSlotEmpty;

    public CampfireCookingScreen(CampfireCookingContainer campfireCookingContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(campfireCookingContainer, inv, titleIn);
        SCREEN_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/campfire_cooking.png");
    }
    @Override
    protected void init() {
        //侧边栏组件初始化
        super.init();

    }
    public void tick() {
        super.tick();
        Slot[] slots = container.furnaceSlots;
        isResultSlotEmpty = slots[0].getStack().isEmpty();//判断合成槽是否为空
        isCraftingSlotEmpty = true;//判断
        for (int i = 1; i <= 1; i++) {
            if(!slots[i].getStack().isEmpty())isCraftingSlotEmpty = false;
        }
        this.confirmBtn.active = !isResultSlotEmpty && !isCraftingSlotEmpty && !isRecipeNameEmpty && !isGroupNameEmpty && !isRecipeJsonExist;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks,mouseX,mouseY);
        //萤火图标
        assert this.minecraft != null;
        this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(Items.CAMPFIRE),guiLeft+56,guiTop+54);
    }
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
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
        JSONObject campfireCookingRecipe = JsonManager.genCampfireCookingRecipe(container.furnaceSlots, groupNameInput.getText(),0.35d,200);
        JSONObject result = JsonManager.createJsonFile(jsonPacket,campfireCookingRecipe,recipeNameInput.getText());
        if (result.getBoolean("success")){
            System.out.println(result.getString("dir"));
            playerInventory.player.sendMessage(new TranslationTextComponent("gui."+DPRM.MOD_ID+".chat.recipe_generate_successed",result.getString("dir")));
        }else {
            playerInventory.player.sendMessage(new TranslationTextComponent("gui."+DPRM.MOD_ID+".chat.recipe_generate_failed",result.getString("dir")));
        }
        this.minecraft.player.closeScreen();
    }
}
