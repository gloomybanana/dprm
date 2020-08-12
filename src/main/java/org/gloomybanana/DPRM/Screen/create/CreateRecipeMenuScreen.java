package org.gloomybanana.DPRM.Screen.create;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.create.CreateRecipeMenuContainer;
import org.gloomybanana.DPRM.container.vanilla.RecipeMenuContainer;
import org.gloomybanana.DPRM.dao.VanillaRecipeJson;
import org.gloomybanana.DPRM.network.Networking;
import org.gloomybanana.DPRM.network.ScreenToggle;
import org.gloomybanana.DPRM.widget.DPRMRecipeWidget;

import java.util.ArrayList;
import java.util.List;

public class CreateRecipeMenuScreen extends ContainerScreen<CreateRecipeMenuContainer> {
    protected final CreateRecipeMenuContainer container;
    protected final JSONObject jsonPacket;
    protected ResourceLocation SCREEN_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/recipe_list.png");
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(DPRM.MOD_ID,"textures/gui/buttons.png");
    protected ImageButton addMechanicalCraftingBtn;
    protected ImageButton addBlastingBtn;
    protected ImageButton addStoneCuttingBtn;
    protected ImageButton forwardPageBtn;
    protected ImageButton backPageBtn;

    Integer total_pages;
    Integer current_page;
    JSONArray current_page_recipe_list;
    JSONArray recipe_list;

    protected DPRMRecipeWidget[] dprmRecipeWidgets= new DPRMRecipeWidget[20];
    protected final Integer textureWidth = 300;
    protected final Integer textureHeight = 300;

    public CreateRecipeMenuScreen(CreateRecipeMenuContainer container, PlayerInventory inv, ITextComponent titleIn) {
        super(container, inv, titleIn);
        this.guiTop = (this.height - this.ySize) / 2;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.container = container;
        this.jsonPacket = JSON.parseObject(container.getPacketBuffer().readString());
        DPRM.LOGGER.info("Send From Server:"+this.jsonPacket);
        this.xSize = 148;
        this.ySize = 167;

        this.current_page = jsonPacket.getInteger("current_page");
        this.total_pages = jsonPacket.getJSONArray("recipe_list").size()/20+1;
        this.recipe_list = jsonPacket.getJSONArray("recipe_list");
        this.current_page_recipe_list = new JSONArray();
        for (int i = (current_page-1)*20; i <= (current_page-1)*20 + 19; i++) {
            try {
                current_page_recipe_list.add(recipe_list.get(i));
            } catch (Exception e) {}
        }
    }

    @Override
    protected void init() {
        super.init();
        addMechanicalCraftingBtn = new ImageButton(guiLeft + 16 -5, guiTop + 10, 26, 16, 0, 0, 17, BUTTON_TEXTURE, this::onAddCraftingShapedBtnPressed);
        addBlastingBtn = new ImageButton(guiLeft + 44 -5, guiTop + 10, 26, 16, 26, 0, 17, BUTTON_TEXTURE, this::onAddFurnanceBtnPressed);
        addStoneCuttingBtn = new ImageButton(guiLeft + 72 -5,guiTop + 10, 26, 16, 52, 0, 17, BUTTON_TEXTURE,this::onAddStonecuttingBtnPressed);

        forwardPageBtn = new ImageButton(guiLeft + 94,guiTop + 134,10,18,78,0,18, BUTTON_TEXTURE,this::forwardPage);
        backPageBtn = new ImageButton(guiLeft+ 43,guiTop + 134,10,18,92,0,18, BUTTON_TEXTURE,this::backwardPage);

        this.addButton(addMechanicalCraftingBtn);
        this.addButton(addStoneCuttingBtn);
        this.addButton(addBlastingBtn);

        if (current_page<total_pages) this.addButton(forwardPageBtn);
        if (current_page>1) this.addButton(backPageBtn);

        for (int i = 0; i < current_page_recipe_list.size(); i++) {
            dprmRecipeWidgets[i] = new DPRMRecipeWidget(guiLeft,guiTop,i,current_page_recipe_list.getJSONObject(i),jsonPacket);
            this.addButton(dprmRecipeWidgets[i]);
        }

    }
    //上一页按钮
    private void backwardPage(Button button) {
        jsonPacket.put("current_page",jsonPacket.getInteger("current_page")-1);
        jsonPacket.put("operate","open_recipe_list_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }
    //下一页按钮
    private void forwardPage(Button button) {
        jsonPacket.put("current_page",jsonPacket.getInteger("current_page")+1);
        jsonPacket.put("operate","open_recipe_list_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        super.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);
        //渲染按钮
        this.addMechanicalCraftingBtn.render(mouseX, mouseY, particleTick);
        this.addBlastingBtn.render(mouseX,mouseY,particleTick);
        this.addStoneCuttingBtn.render(mouseX,mouseY,particleTick);
        if (current_page<total_pages)this.forwardPageBtn.render(mouseX,mouseY,particleTick);
        if (current_page>1)this.backPageBtn.render(mouseX,mouseY,particleTick);
        renderBtnTooltips(mouseX, mouseY);
        renderDprmRecipeWidgetTooltip(mouseX,mouseY);
    }

    private void renderBtnTooltips(int mouseX, int mouseY) {
        if (addMechanicalCraftingBtn.isHovered()){
            List<String> addCraftingRecipe = new ArrayList<>();
            addCraftingRecipe.add(I18n.format("gui."+ DPRM.MOD_ID+".add_crafting_recipe"));
            this.renderTooltip(addCraftingRecipe,mouseX,mouseY);
        }
        if (addBlastingBtn.isHovered()){
            List<String> addFurnaceRcipe = new ArrayList<>();
            addFurnaceRcipe.add(I18n.format("gui."+DPRM.MOD_ID+".add_furnace_recipe"));
            this.renderTooltip(addFurnaceRcipe,mouseX,mouseY);
        }
        if (addStoneCuttingBtn.isHovered()){
            List<String> addStoneCuttingRecipe = new ArrayList<>();
            addStoneCuttingRecipe.add(I18n.format("gui."+DPRM.MOD_ID+".add_stonecutting_recipe"));
            this.renderTooltip(addStoneCuttingRecipe,mouseX,mouseY);
        }
    }

    private void renderDprmRecipeWidgetTooltip(int mouseX,int mouseY){
        List<String> recipeTooltips = new ArrayList<>();
        for (int i = 0; i < current_page_recipe_list.size(); i++) {
            if (dprmRecipeWidgets[i].isHovered()){
                JSONObject recipe = current_page_recipe_list.getJSONObject(i);
                String recipe_name = recipe.getString("recipe_name");
                String type = recipe.getJSONObject("content").getString("type");
                String itemRegistryName = VanillaRecipeJson.getResultName(recipe.getJSONObject("content"));
                recipeTooltips.add(VanillaRecipeJson.translateRegisryName(itemRegistryName));
                recipeTooltips.add(I18n.format("gui."+DPRM.MOD_ID+".tooltips.recipe_name",recipe_name));
                recipeTooltips.add(I18n.format("gui."+DPRM.MOD_ID+".tooltips.recipe_type", VanillaRecipeJson.translateRecipeType(type)));
                this.renderTooltip(recipeTooltips,mouseX,mouseY);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(SCREEN_TEXTURE);
        blit(guiLeft, guiTop, 0, 0,148, 167, textureWidth, textureHeight);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String page = current_page+"/"+total_pages;
        this.font.drawString(page, (float)(this.xSize / 2 - this.font.getStringWidth(page) / 2), 139.0F, 0XFFFFFF);
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
}
