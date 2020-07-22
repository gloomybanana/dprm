package org.gloomybanana.DPRM.Screen;

import com.alibaba.fastjson.JSONObject;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.TranslationTextComponent;
import org.gloomybanana.DPRM.DPRM;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.gloomybanana.DPRM.container.StonecuttingContainer;
import org.gloomybanana.DPRM.file.JsonManager;

public class StonecuttingScreen extends AbstractRecipeMakerScreen<StonecuttingContainer> {

    Boolean isResultSlotEmpty = true;
    Boolean isCraftingSlotEmpty = true;

    public StonecuttingScreen(StonecuttingContainer stonecuttingContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(stonecuttingContainer, inv, titleIn);
        SCREEN_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/stonecutting.png");
    }

    @Override
    protected void init() {
        super.init();
    }
    public void tick() {
        super.tick();
        Slot[] slots = container.stonecuttingSlots;
        isResultSlotEmpty = slots[0].getStack().isEmpty();//判断合成槽是否为空
        isCraftingSlotEmpty = slots[1].getStack().isEmpty();//判断

        this.confirmBtn.active = !isResultSlotEmpty && !isCraftingSlotEmpty && !isRecipeNameEmpty && !isGroupNameEmpty && !isRecipeJsonExist;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks,mouseX,mouseY);
        //切石机
        this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(Items.STONECUTTER),guiLeft+75,guiTop+33);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX,mouseY);
        this.font.drawString(this.title.getFormattedText(), 8.0F, 4.0F, 4210752);
    }

    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);
    }

    @Override
    public void onConfirmBtnPress(Button button) {
        JSONObject stonecuttingRecipe = JsonManager.genStonecuttingRecipe(container.stonecuttingSlots, groupNameInput.getText());
        JSONObject result = JsonManager.createJsonFile(jsonPacket,stonecuttingRecipe,recipeNameInput.getText());
        if (result.getBoolean("success")){
            System.out.println(result.getString("dir"));
            playerInventory.player.sendMessage(new TranslationTextComponent("gui."+DPRM.MOD_ID+".chat.recipe_generate_successed",result.getString("dir")));
        }else {
            playerInventory.player.sendMessage(new TranslationTextComponent("gui."+DPRM.MOD_ID+".chat.recipe_generate_failed",result.getString("dir")));
        }
//        this.minecraft.player.closeScreen();
    }
}
