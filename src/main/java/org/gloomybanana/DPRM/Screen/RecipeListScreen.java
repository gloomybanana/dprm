package org.gloomybanana.DPRM.Screen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.RecipeListContainer;
import org.gloomybanana.DPRM.file.JsonManager;
import org.gloomybanana.DPRM.network.Networking;
import org.gloomybanana.DPRM.network.ScreenToggle;
import org.gloomybanana.DPRM.widget.DPRMRecipeWidget;

import java.util.ArrayList;
import java.util.List;

public class RecipeListScreen extends ContainerScreen<RecipeListContainer> {
    protected final RecipeListContainer container;
    protected final JSONObject jsonPacket;
    protected ResourceLocation SCREEN_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/recipe_list.png");
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(DPRM.MOD_ID,"textures/gui/buttons.png");
    protected ImageButton addCraftingShapedBtn;
    protected ImageButton addFurnanceBtn;
    protected ImageButton addStoneCuttingBtn;
    protected ImageButton addSmithingBtn;
    protected ImageButton forwardPageBtn;
    protected ImageButton backPageBtn;
    Integer total_pages;
    Integer current_page;
    JSONArray current_page_recipe_list;
    JSONArray recipe_list;

    protected DPRMRecipeWidget[] dprmRecipeWidgets= new DPRMRecipeWidget[20];


    protected final Integer textureWidth = 300;
    protected final Integer textureHeight = 300;
    public RecipeListScreen instance = this;
//    protected final RecipeButtonPageScreen recipeButtonPageScreen;

    public RecipeListScreen(RecipeListContainer container, PlayerInventory inv, ITextComponent titleIn) {
        super(container, inv, titleIn);
        this.guiTop = (this.height - this.ySize) / 2;
        this.container = container;
        this.jsonPacket = JSON.parseObject(container.getPacketBuffer().readString());
        DPRM.LOGGER.info("Send From Server:"+this.jsonPacket);
        this.xSize = 148;
        this.ySize = 167;
        this.guiLeft = (this.width - this.xSize) / 2;

        this.current_page = jsonPacket.getInteger("current_page");
        this.total_pages = jsonPacket.getJSONArray("recipe_list").size()/20+1;
        this.current_page_recipe_list = new JSONArray();
        this.recipe_list = jsonPacket.getJSONArray("recipe_list");
        for (int i = (current_page-1)*20; i <= (current_page-1)*20 + 19; i++) {
            try {
                current_page_recipe_list.add(recipe_list.get(i));
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
        
    }

    @Override
    protected void init() {
        super.init();
        addCraftingShapedBtn = new ImageButton(guiLeft + 16 -5, guiTop + 10, 26, 16, 0, 0, 17, BUTTON_TEXTURE, this::onAddCraftingShapedBtnPressed);
        addFurnanceBtn = new ImageButton(guiLeft + 44 -5, guiTop + 10, 26, 16, 26, 0, 17, BUTTON_TEXTURE, this::onAddFurnanceBtnPressed);
        addStoneCuttingBtn = new ImageButton(guiLeft + 72 -5,guiTop + 10, 26, 16, 52, 0, 17, BUTTON_TEXTURE,this::onAddStonecuttingBtnPressed);
        addSmithingBtn = new ImageButton(guiLeft + 100 -5,guiTop + 10, 26, 16, 0, 32, 17, BUTTON_TEXTURE,this::onAddSmithingBtnPressed);
        forwardPageBtn = new ImageButton(guiLeft + 94,guiTop + 134,10,18,78,0,18, BUTTON_TEXTURE,this::forwardPage);
        backPageBtn = new ImageButton(guiLeft+ 43,guiTop + 134,10,18,92,0,18, BUTTON_TEXTURE,this::backwardPage);

        this.addButton(addCraftingShapedBtn);
        this.addButton(addStoneCuttingBtn);
        this.addButton(addFurnanceBtn);
        this.addButton(addSmithingBtn);
        if (current_page<total_pages) this.addButton(forwardPageBtn);
        if (current_page>1) this.addButton(backPageBtn);


        for (int i = 0; i < current_page_recipe_list.size(); i++) {
            dprmRecipeWidgets[i] = new DPRMRecipeWidget(guiLeft,guiTop,i,current_page_recipe_list.getJSONObject(i),jsonPacket);
            this.addButton(dprmRecipeWidgets[i]);
        }

    }

    private void backwardPage(Button button) {
        jsonPacket.put("current_page",jsonPacket.getInteger("current_page")-1);
        jsonPacket.put("operate","open_recipe_list_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }

    private void forwardPage(Button button) {
        jsonPacket.put("current_page",jsonPacket.getInteger("current_page")+1);
        jsonPacket.put("operate","open_recipe_list_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }


    @Override
    protected void renderHoveredTooltip(MatrixStack matrixStack,int mouseX, int mouseY) {
        super.renderHoveredTooltip(matrixStack,mouseX, mouseY);

    }

    @Override
    public void render(MatrixStack matrixStack,int mouseX, int mouseY, float particleTick) {
        super.render(matrixStack,mouseX, mouseY, particleTick);
        this.addCraftingShapedBtn.render(matrixStack,mouseX, mouseY, particleTick);
        this.addFurnanceBtn.render(matrixStack,mouseX,mouseY,particleTick);
        this.addStoneCuttingBtn.render(matrixStack,mouseX,mouseY,particleTick);
        this.addSmithingBtn.render(matrixStack,mouseX,mouseY,particleTick);
        if (current_page<total_pages)this.forwardPageBtn.render(matrixStack,mouseX,mouseY,particleTick);
        if (current_page>1)this.backPageBtn.render(matrixStack,mouseX,mouseY,particleTick);
        if (addCraftingShapedBtn.isHovered()){
            this.renderTooltip(matrixStack,new TranslationTextComponent("gui."+DPRM.MOD_ID+".add_crafting_recipe"),mouseX,mouseY);
        }
        if (addFurnanceBtn.isHovered()){
            this.renderTooltip(matrixStack,new TranslationTextComponent("gui."+DPRM.MOD_ID+".add_furnace_recipe"),mouseX,mouseY);
        }
        if (addStoneCuttingBtn.isHovered()){
            this.renderTooltip(matrixStack,new TranslationTextComponent("gui."+DPRM.MOD_ID+".add_stonecutting_recipe"),mouseX,mouseY);
        }
        if (addSmithingBtn.isHovered()){
            this.renderTooltip(matrixStack,new TranslationTextComponent("gui."+DPRM.MOD_ID+".add_smithing_recipe"),mouseX,mouseY);
        }
        renderDprmRecipeWidgetTooltip(matrixStack,mouseX,mouseY);
    }

    public void renderDprmRecipeWidgetTooltip(MatrixStack matrixStack,int mouseX,int mouseY){
        for (int i = 0; i < current_page_recipe_list.size(); i++) {
            if (dprmRecipeWidgets[i].isHovered()){
                JSONObject recipe = current_page_recipe_list.getJSONObject(i);
                String recipe_name = recipe.getString("recipe_name");
                String type = recipe.getJSONObject("content").getString("type");
                String itemRegistryName = JsonManager.getResultName(recipe.getJSONObject("content"));

                ArrayList<ITextComponent> tooltips = new ArrayList<>();
                tooltips.add(JsonManager.translateRegisryName(itemRegistryName));
                tooltips.add(new TranslationTextComponent("gui."+DPRM.MOD_ID+".tooltips.recipe_name",recipe_name));
                tooltips.add(new TranslationTextComponent("gui."+DPRM.MOD_ID+".tooltips.recipe_type",JsonManager.translateRecipeType(type)));

//                this.renderTooltip(matrixStack,JsonManager.translateRegisryName(itemRegistryName),mouseX,mouseY);
                this.renderWrappedToolTip(matrixStack,tooltips,mouseX,mouseY,this.font);
//                this.renderTooltip(matrixStack,new TranslationTextComponent("gui."+DPRM.MOD_ID+".tooltips.recipe_name",recipe_name),mouseX,mouseY);
//                this.renderTooltip(matrixStack,new TranslationTextComponent("gui."+DPRM.MOD_ID+".tooltips.recipe_type",JsonManager.translateRecipeType(type)),mouseX,mouseY);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack,float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(SCREEN_TEXTURE);
        blit(matrixStack,guiLeft, guiTop, 0, 0,148, 167, textureWidth, textureHeight);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack,int mouseX, int mouseY) {
//        super.drawGuiContainerForegroundLayer(matrixStack,mouseX, mouseY);
        String page = current_page+"/"+total_pages;
        this.font.drawString(matrixStack,page, (float)(this.xSize / 2 - this.font.getStringWidth(page) / 2), 139.0F, 0XFFFFFF);
    }

    private void onAddCraftingShapedBtnPressed(Button button) {
        jsonPacket.put("select_recipe_name","");
        jsonPacket.put("operate","open_crafting_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }
    private void onAddFurnanceBtnPressed(Button button) {
        jsonPacket.put("select_recipe_name","");
        jsonPacket.put("operate","open_furnace_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
//        this.minecraft.player.closeScreen();
    }
    private void onAddStonecuttingBtnPressed(Button button) {
        jsonPacket.put("select_recipe_name","");
        jsonPacket.put("operate","open_stonecutting_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }
    private void onAddSmithingBtnPressed(Button button) {
        jsonPacket.put("select_recipe_name","");
        jsonPacket.put("operate","open_smithing_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }
}
