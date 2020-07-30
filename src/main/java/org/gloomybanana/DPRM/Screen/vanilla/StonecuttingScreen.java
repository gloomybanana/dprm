package org.gloomybanana.DPRM.Screen.vanilla;

import com.alibaba.fastjson.JSONObject;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import org.gloomybanana.DPRM.DPRM;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.gloomybanana.DPRM.container.vanilla.StonecuttingContainer;
import org.gloomybanana.DPRM.file.VanillaRecipeJson;
import org.gloomybanana.DPRM.network.ScreenToggle;
import org.gloomybanana.DPRM.network.Networking;
import org.gloomybanana.DPRM.network.CRUDRecipe;

public class StonecuttingScreen extends ScreenWithRecipeInfo<StonecuttingContainer> {

    Boolean isResultSlotEmpty = true;
    Boolean isCraftingSlotEmpty = true;

    public StonecuttingScreen(StonecuttingContainer stonecuttingContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(stonecuttingContainer, inv, titleIn);
        SIDE_INFO_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/stonecutting.png");
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

        this.confirmBtn.active = !isResultSlotEmpty && !isCraftingSlotEmpty && !isRecipeNameEmpty && !isGroupNameEmpty;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks,mouseX,mouseY);
        //切石机
        this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(Items.STONECUTTER),guiLeft+79,guiTop+33);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX,mouseY);
        String s = this.title.getFormattedText();
        this.font.drawString(s, (float)(this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 4210752);
    }

    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);

        if (isRecipeJsonExist){
            renderFakeRecipe();
        }

    }

    private void renderFakeRecipe() {
        if (currentRecipe==null) return;
        String type = currentRecipe.getString("type");
        if (type.equals("stonecutting")){
            String ingredientItemRegistryName = currentRecipe.getJSONObject("ingredient").getString("item");
            String resultRegistryName = currentRecipe.getString("result");
            ItemStack ingredientItemStack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ingredientItemRegistryName)));
            Integer resultcount = currentRecipe.getInteger("count");
            ItemStack resultItemStack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(resultRegistryName)),resultcount);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(ingredientItemStack,guiLeft+20,guiTop+33);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(resultItemStack,guiLeft+143,guiTop+33);
        }
    }

    @Override
    public void onConfirmBtnPress(Button button) {
        JSONObject stonecuttingRecipe = VanillaRecipeJson.genStonecuttingRecipe(container.stonecuttingSlots, groupNameInput.getText());
        jsonPacket.put("json_recipe",stonecuttingRecipe);
        jsonPacket.put("recipe_name",recipeNameInput.getText());
        jsonPacket.put("crud","create");
        Networking.INSTANCE.sendToServer(new CRUDRecipe(jsonPacket.toJSONString()));

        jsonPacket.put("operate","open_stonecutting_screen");
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
        jsonPacket.put("operate","open_stonecutting_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }
}
