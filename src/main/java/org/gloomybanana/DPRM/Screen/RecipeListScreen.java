package org.gloomybanana.DPRM.Screen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.RecipeListContainer;
import org.gloomybanana.DPRM.network.Networking;
import org.gloomybanana.DPRM.network.ScreenToggle;

import java.util.ArrayList;
import java.util.List;

public class RecipeListScreen extends ContainerScreen<RecipeListContainer> {
    protected final RecipeListContainer container;
    protected final JSONObject jsonPacket;
    protected ResourceLocation SCREEN_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/recipe_list.png");
    private static final ResourceLocation ADD_CRAFTING_SHAPED_BTN = new ResourceLocation(DPRM.MOD_ID,"textures/gui/add_crafting_shaped_btn.png");
    private static final ResourceLocation ADD_FURNANCE_BTN = new ResourceLocation(DPRM.MOD_ID,"textures/gui/add_furnance_btn.png");
    private static final ResourceLocation ADD_STONECUTTING_BTN = new ResourceLocation(DPRM.MOD_ID,"textures/gui/add_stonecutting_btn.png");
    protected ImageButton addCraftingShapedBtn;
    protected ImageButton addFurnanceBtn;
    protected ImageButton addStoneCuttingBtn;

    protected final Integer textureWidth = 300;
    protected final Integer textureHeight = 300;
    public RecipeListScreen(RecipeListContainer container, PlayerInventory inv, ITextComponent titleIn) {
        super(container, inv, titleIn);
        this.container = container;
        this.jsonPacket = JSON.parseObject(container.getPacketBuffer().readString());
        DPRM.LOGGER.info("Send From Server:"+this.jsonPacket);
        this.xSize = 148;
        this.ySize = 167;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

    }

    @Override
    protected void init() {
        super.init();
        addCraftingShapedBtn = new ImageButton(guiLeft + 5, guiTop - 18, 26, 16, 0, 0, 16, ADD_CRAFTING_SHAPED_BTN, this::onAddCraftingShapedBtnPressed);
        addFurnanceBtn = new ImageButton(guiLeft + 33, guiTop - 18, 26, 16, 0, 0, 16, ADD_FURNANCE_BTN, this::onAddFurnanceBtnPressed);
        addStoneCuttingBtn = new ImageButton(guiLeft + 61,guiTop - 18, 26, 16, 0, 0, 16, ADD_STONECUTTING_BTN,this::onAddStonecuttingBtnPressed);
        this.addButton(addCraftingShapedBtn);
        this.addButton(addStoneCuttingBtn);
        this.addButton(addFurnanceBtn);
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        super.renderHoveredToolTip(mouseX, mouseY);

    }

    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);
        this.addCraftingShapedBtn.render(mouseX, mouseY, particleTick);
        this.addFurnanceBtn.render(mouseX,mouseY,particleTick);
        this.addStoneCuttingBtn.render(mouseX,mouseY,particleTick);
        if (addCraftingShapedBtn.isHovered()){
            List<String> addCraftingRecipe = new ArrayList<>();
            addCraftingRecipe.add(I18n.format("gui."+DPRM.MOD_ID+".add_crafting_recipe"));
            this.renderTooltip(addCraftingRecipe,mouseX,mouseY);
        }
        if (addFurnanceBtn.isHovered()){
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

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(SCREEN_TEXTURE);
        blit(guiLeft, guiTop, 0, 0,148, 167, textureWidth, textureHeight);
    }

    private void onAddCraftingShapedBtnPressed(Button button) {
        jsonPacket.put("operate","open_crafting_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }
    private void onAddFurnanceBtnPressed(Button button) {
        jsonPacket.put("operate","open_furnace_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
//        this.minecraft.player.closeScreen();
    }
    private void onAddStonecuttingBtnPressed(Button button) {
        jsonPacket.put("operate","open_stonecutting_screen");
        Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
    }
}
