package org.gloomybanana.DPRM.Screen;

import com.alibaba.fastjson.JSONObject;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.SmithingContainer;
import org.gloomybanana.DPRM.file.JsonManager;
import org.gloomybanana.DPRM.network.CRUDRecipe;
import org.gloomybanana.DPRM.network.Networking;
import org.gloomybanana.DPRM.network.ScreenToggle;

public class SmithingScreen extends AbstractRecipeMakerScreen<SmithingContainer>{

    Boolean isResultSlotEmpty = true;
    Boolean isSlot1Empty = true;
    Boolean isSlot2Empty = true;
    public SmithingScreen(SmithingContainer smithingContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(smithingContainer, inv, titleIn);
        SCREEN_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/smithing.png");
    }

    @Override
    protected void init() {
        super.init();
    }
    public void tick() {
        super.tick();
        Slot[] slots = container.smithingSlots;
        isResultSlotEmpty = slots[0].getStack().isEmpty();//判断合成槽是否为空
        isSlot1Empty = slots[1].getStack().isEmpty();//判断插槽1是否为空
        isSlot2Empty = slots[2].getStack().isEmpty();//判断插槽2是否为空
        this.confirmBtn.active = !isResultSlotEmpty && !isSlot1Empty && !isSlot2Empty&& !isRecipeNameEmpty && !isGroupNameEmpty;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(matrixStack,partialTicks,mouseX,mouseY);
        //锻造台图标
//        this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(Items.SMITHING_TABLE),guiLeft+100,guiTop+33);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack,int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(matrixStack,mouseX,mouseY);
        String s = this.title.getString();
        this.font.drawString(matrixStack,s, (float)(this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 4210752);
    }

    @Override
    public void render(MatrixStack matrixStack,int mouseX, int mouseY, float particleTick) {
        super.render(matrixStack,mouseX, mouseY, particleTick);

        if (isRecipeJsonExist){
            renderFakeRecipe();
        }

    }

    private void renderFakeRecipe() {
        if (currentRecipe==null) return;
        String type = currentRecipe.getString("type");
        if (type.equals("smithing")){
            String slot1ItemRegistryName = currentRecipe.getJSONObject("base").getString("item");
            String slot2ItemRegistryName = currentRecipe.getJSONObject("addition").getString("item");
            String resultRegistryName = currentRecipe.getJSONObject("result").getString("item");
            ItemStack slot1ItemStack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(slot1ItemRegistryName)));
            ItemStack slot2ItemStack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(slot2ItemRegistryName)));

            ItemStack resultItemStack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(resultRegistryName)));
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(slot1ItemStack,guiLeft+27,guiTop+47);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(slot2ItemStack,guiLeft+76,guiTop+47);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(resultItemStack,guiLeft+134,guiTop+47);
        }
    }

    @Override
    public void onConfirmBtnPress(Button button) {
        JSONObject smithingRecipe = JsonManager.genSmithingRecipe(container.smithingSlots);
        jsonPacket.put("json_recipe",smithingRecipe);
        jsonPacket.put("recipe_name",recipeNameInput.getText());
        jsonPacket.put("crud","create");
        Networking.INSTANCE.sendToServer(new CRUDRecipe(jsonPacket.toJSONString()));

        jsonPacket.put("operate","open_smithing_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
//        this.minecraft.player.closeScreen();
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
        jsonPacket.put("operate","open_smithing_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }
}
