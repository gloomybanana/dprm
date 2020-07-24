package org.gloomybanana.DPRM.Screen;

import com.alibaba.fastjson.JSONObject;
import net.minecraft.client.gui.widget.ToggleWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.CraftingShapedContainer;
import org.gloomybanana.DPRM.file.JsonManager;
import org.gloomybanana.DPRM.widget.ToggleCraftingTypeButton;
import org.gloomybanana.DPRM.widget.ToggleFurnaceTypeButton;

import java.util.ArrayList;
import java.util.List;

public class CraftingShapedScreen extends AbstractRecipeMakerScreen<CraftingShapedContainer> {
    //提交按钮判断条件
    Boolean isResultSlotEmpty = true;
    Boolean isCraftingSlotEmpty = true;
    ToggleCraftingTypeButton.Type currentType = ToggleCraftingTypeButton.Type.crafting_shaped;
    ToggleCraftingTypeButton toggleCraftingBtn;

    public CraftingShapedScreen(CraftingShapedContainer craftingShapedContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(craftingShapedContainer, inv, titleIn);
        SCREEN_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/crafting_table.png");
    }

    @Override
    protected void init() {
        super.init();
        this.toggleCraftingBtn = new ToggleCraftingTypeButton(guiLeft+88,guiTop+28,28,28,this::changeCraftingType,currentType);
        this.children.add(toggleCraftingBtn);
    }

    private void changeCraftingType(Button button) {
        ((ToggleCraftingTypeButton) button).toggle();
        int ordinal = currentType.ordinal();
        ordinal++;
        if (ordinal > 1) ordinal = 0;
        currentType = ToggleCraftingTypeButton.Type.values()[ordinal];
    }

    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);
        renderToolTip(mouseX,mouseX,particleTick);

    }

    public void renderToolTip(int mouseX,int mouseY, float particleTick){

        List<String> toogleCraftType = new ArrayList<>();
        toogleCraftType.add("切换");
        if (mouseX>=guiLeft+88&&mouseX<=guiLeft+116&&mouseY>=guiTop+28&&mouseY<=guiTop+56){
            this.renderTooltip(toogleCraftType,mouseX,mouseY);
        }
    }

    @Override
    public void onConfirmBtnPress(Button button) {

        JSONObject craftingShapedRecipe = JsonManager.genCraftingShapedRecipe(container.craftTableSlots, groupNameInput.getText(),currentType.name());
        JSONObject result = JsonManager.createJsonFile(jsonPacket,craftingShapedRecipe,recipeNameInput.getText());
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
        this.minecraft.getTextureManager().bindTexture(this.SCREEN_TEXTURE);
        if (currentType == ToggleCraftingTypeButton.Type.crafting_shaped) blit(guiLeft+88, guiTop+28, 0, 226,28, 28, textureWidth, textureHeight);
        if (currentType == ToggleCraftingTypeButton.Type.crafting_shapeless) blit(guiLeft+88, guiTop+28, 0, 198,28, 28, textureWidth, textureHeight);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX,mouseY);
        this.font.drawString(currentType.getTitle(), 28.0F, 6.0F, 4210752);
    }

}
