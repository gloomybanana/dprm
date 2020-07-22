package org.gloomybanana.DPRM.Screen;

import com.alibaba.fastjson.JSONObject;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.CraftingShapelessContainer;
import org.gloomybanana.DPRM.file.JsonManager;


public class CraftingShapelessScreen extends AbstractRecipeMakerScreen<CraftingShapelessContainer> {
    //提交按钮判断条件
    Boolean isResultSlotEmpty = true;
    Boolean isCraftingSlotEmpty = true;

    public CraftingShapelessScreen(CraftingShapelessContainer craftingShapelessContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(craftingShapelessContainer, inv, titleIn);
        SCREEN_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/crafting_table.png");
    }
    @Override
    protected void init() {
        super.init();
    }
    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);
    }
    @Override
    public void onConfirmBtnPress(Button button) {
        JSONObject craftingShapelessRecipe = JsonManager.genCraftingShapelessRecipe(container.craftTableSlots, groupNameInput.getText());
        JSONObject result = JsonManager.createJsonFile(jsonPacket,craftingShapelessRecipe,recipeNameInput.getText());
        if (result.getBoolean("success")){
            System.out.println(result.getString("dir"));
            playerInventory.player.sendMessage(new TranslationTextComponent("gui."+DPRM.MOD_ID+".chat.recipe_generate_successed",result.getString("dir")));
        }else {
            playerInventory.player.sendMessage(new TranslationTextComponent("gui."+DPRM.MOD_ID+".chat.recipe_generate_failed",result.getString("dir")));
        }
//        this.minecraft.player.closeScreen();
    }
    public void tick() {
        super.tick();
        Slot[] slots = container.craftTableSlots;
        isResultSlotEmpty = slots[0].getStack().isEmpty();//判断合成槽是否为空
        isCraftingSlotEmpty = true;//判断
        for (int i = 1; i <= 9; i++) {
            if(!slots[i].getStack().isEmpty())isCraftingSlotEmpty = false;
        }
        this.confirmBtn.active = !isResultSlotEmpty && !isCraftingSlotEmpty && !isRecipeNameEmpty && !isGroupNameEmpty && !isRecipeJsonExist;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks,mouseX,mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX,mouseY);
        this.font.drawString(this.title.getString(), 28.0F, 6.0F, 4210752);
    }

}
