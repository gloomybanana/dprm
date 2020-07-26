package org.gloomybanana.DPRM.Screen;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.CraftingContainer;
import org.gloomybanana.DPRM.file.JsonManager;
import org.gloomybanana.DPRM.network.Networking;
import org.gloomybanana.DPRM.network.ScreenToggle;
import org.gloomybanana.DPRM.network.CRUDRecipe;
import org.gloomybanana.DPRM.widget.ToggleCraftingTypeButton;

import java.util.ArrayList;
import java.util.List;

public class CraftingScreen extends AbstractRecipeMakerScreen<CraftingContainer> {
    //提交按钮判断条件
    Boolean isResultSlotEmpty = true;
    Boolean isCraftingSlotEmpty = true;
    ToggleCraftingTypeButton.Type currentType = ToggleCraftingTypeButton.Type.crafting_shaped;
    ToggleCraftingTypeButton toggleCraftingBtn;


    public CraftingScreen(CraftingContainer craftingContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(craftingContainer, inv, titleIn);
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

//        try {
//            String type = currentRecipe.getString("type");
//            if (type.equals("minecraft:crafting_shaped") || type.equals("minecraft:crafting_shapeless")) {
//            } else if (type.equals("smelting")||type.equals("smoking")||type.equals("blasting")||type.equals("campfire_cooking")||type.equals("stonecutting")){
//                this.font.drawString(I18n.format("gui."+DPRM.MOD_ID+".tooltips.this_is_xx_recipe_type",JsonManager.translateRecipeType(type)), guiLeft - 82, guiTop - 15, 0XFFFFFF);
//            }
//        } catch (Exception e) {}

        if (isRecipeJsonExist){
            renderFakeRecipe();
            if (recipeNameInput!=null){
                if (recipeNameInput.isFocused()){
                    if (currentRecipe.getString("type").equals("minecraft:crafting_shaped"))currentType = ToggleCraftingTypeButton.Type.crafting_shaped;
                    if (currentRecipe.getString("type").equals("minecraft:crafting_shapeless"))currentType = ToggleCraftingTypeButton.Type.crafting_shapeless;
                }
            }
        }
    }

    Boolean isToggleCraftingBtnHovered = false;
    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        super.renderHoveredToolTip(mouseX, mouseY);
        //判断是否悬停在切换按钮
        List<String> toogleCraftType = new ArrayList<>();
        toogleCraftType.add(I18n.format( "gui."+DPRM.MOD_ID+".toggle_crafting_type"));
        if ((mouseX>=guiLeft+88)&&(mouseX<=guiLeft+116)&&(mouseY>=guiTop+28)&&(mouseY<=guiTop+56)){
            this.renderTooltip(toogleCraftType,mouseX,mouseY);
            isToggleCraftingBtnHovered = true;
        }else isToggleCraftingBtnHovered = false;
    }

    @Override
    public void onConfirmBtnPress(Button button) {
        JSONObject craftingRecipe = null;
        if (currentType == ToggleCraftingTypeButton.Type.crafting_shaped)craftingRecipe = JsonManager.genCraftingShapedRecipe(container.craftTableSlots, groupNameInput.getText());
        if (currentType == ToggleCraftingTypeButton.Type.crafting_shapeless)craftingRecipe = JsonManager.genCraftingShapelessRecipe(container.craftTableSlots,groupNameInput.getText());
        jsonPacket.put("json_recipe",craftingRecipe);
        jsonPacket.put("recipe_name",recipeNameInput.getText());
        jsonPacket.put("crud","create");
        Networking.INSTANCE.sendToServer(new CRUDRecipe(jsonPacket.toJSONString()));

        jsonPacket.put("operate","open_crafting_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }

    public void tick() {
        super.tick();
        Slot[] slots = container.craftTableSlots;
        isResultSlotEmpty = slots[0].getStack().isEmpty();//判断合成槽是否为空
        isCraftingSlotEmpty = true;//判断
        for (int i = 1; i <= 9; i++) {
            if(!slots[i].getStack().isEmpty())isCraftingSlotEmpty = false;
        }
        this.confirmBtn.active = !isResultSlotEmpty && !isCraftingSlotEmpty && !isRecipeNameEmpty && !isGroupNameEmpty;
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks,mouseX,mouseY);
        this.minecraft.getTextureManager().bindTexture(this.SCREEN_TEXTURE);
        if (isToggleCraftingBtnHovered) blit(guiLeft+88, guiTop+28, 28, 198,28, 28, textureWidth, textureHeight);
        if (currentType == ToggleCraftingTypeButton.Type.crafting_shaped) blit(guiLeft+88, guiTop+28, 0, 226,28, 28, textureWidth, textureHeight);
        if (currentType == ToggleCraftingTypeButton.Type.crafting_shapeless) blit(guiLeft+88, guiTop+28, 0, 198,28, 28, textureWidth, textureHeight);
    }
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX,mouseY);
        this.font.drawString(currentType.getTitle(), 28.0F, 6.0F, 4210752);
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
        jsonPacket.put("operate","open_crafting_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }

    public void renderFakeRecipe(){
        if (currentRecipe==null) return;
        String type = currentRecipe.getString("type");
        if (type.equals("minecraft:crafting_shaped")){
            JSONObject key = currentRecipe.getJSONObject("key");
            ItemStack[] fakeItemStacks = new ItemStack[10];
            for (int i = 1; i <= 9; i++) {
                String itemRegistryName = (key.getJSONObject(i + "") == null) ? "minecraft:air" : key.getJSONObject(i + "").getString("item");
                fakeItemStacks[i] = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemRegistryName)));
            }
            if (currentRecipe.getJSONObject("result")!=null){
                JSONObject result = currentRecipe.getJSONObject("result");
                if (result.getString("item")!=null){
                    String resultItemRegisryName = result.getString("item");
                    Integer resultItemCount = 0;
                    if (result.getInteger("count")!=null){
                        resultItemCount = result.getInteger("count");
                    }
                    fakeItemStacks[0] = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(resultItemRegisryName)),resultItemCount);
                }
            }
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[0],guiLeft+124,guiTop+35);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[1],guiLeft+30 + 0 * 18,guiTop+17 + 0 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[2],guiLeft+30 + 1 * 18,guiTop+17 + 0 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[3],guiLeft+30 + 2 * 18,guiTop+17 + 0 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[4],guiLeft+30 + 0 * 18,guiTop+17 + 1 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[5],guiLeft+30 + 1 * 18,guiTop+17 + 1 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[6],guiLeft+30 + 2 * 18,guiTop+17 + 1 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[7],guiLeft+30 + 0 * 18,guiTop+17 + 2 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[8],guiLeft+30 + 1 * 18,guiTop+17 + 2 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[9],guiLeft+30 + 2 * 18,guiTop+17 + 2 * 18);

//                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:clay_ball"));
//            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(,guiLeft+56,guiTop+54);
        }
        if (type.equals("minecraft:crafting_shapeless")){
            JSONArray ingredients = currentRecipe.getJSONArray("ingredients");
            ItemStack[] fakeItemStacks = new ItemStack[10];

            for (int i = 1; i <= 9; i++) {
                if (i<=ingredients.size()){
                    String itemRegistryName = ingredients.getJSONObject(i-1).getString("item");
                    fakeItemStacks[i] = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemRegistryName)));
                }else {
                    fakeItemStacks[i] = new ItemStack(Items.AIR);
                }
            }
            if (currentRecipe.getJSONObject("result")!=null){
                JSONObject result = currentRecipe.getJSONObject("result");
                if (result.getString("item")!=null){
                    String resultItemRegisryName = result.getString("item");
                    Integer resultItemCount = 0;
                    if (result.getInteger("count")!=null){
                        resultItemCount = result.getInteger("count");
                    }
                    fakeItemStacks[0] = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(resultItemRegisryName)),resultItemCount);
                }
            }
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[0],guiLeft+124,guiTop+35);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[1],guiLeft+30 + 0 * 18,guiTop+17 + 0 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[2],guiLeft+30 + 1 * 18,guiTop+17 + 0 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[3],guiLeft+30 + 2 * 18,guiTop+17 + 0 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[4],guiLeft+30 + 0 * 18,guiTop+17 + 1 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[5],guiLeft+30 + 1 * 18,guiTop+17 + 1 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[6],guiLeft+30 + 2 * 18,guiTop+17 + 1 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[7],guiLeft+30 + 0 * 18,guiTop+17 + 2 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[8],guiLeft+30 + 1 * 18,guiTop+17 + 2 * 18);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(fakeItemStacks[9],guiLeft+30 + 2 * 18,guiTop+17 + 2 * 18);
        }
    }
}
