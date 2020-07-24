package org.gloomybanana.DPRM.Screen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.RecipeListContainer;
import org.gloomybanana.DPRM.file.JsonManager;
import org.gloomybanana.DPRM.network.Networking;
import org.gloomybanana.DPRM.network.OpenCraftingShapedScreen;
import org.gloomybanana.DPRM.network.SendRecipePack;

public class RecipeListScreen extends ContainerScreen<RecipeListContainer> {
    protected final RecipeListContainer container;
    protected final JSONObject jsonPacket;
    protected ResourceLocation SCREEN_TEXTURE = new ResourceLocation(DPRM.MOD_ID, "textures/gui/recipe_list.png");
    private static final ResourceLocation ADD_CRAFTING_SHAPED_BTN = new ResourceLocation(DPRM.MOD_ID,"textures/gui/add_crafting_shaped_btn.png");
    private static final ResourceLocation ADD_FURNANCE_BTN = new ResourceLocation(DPRM.MOD_ID,"textures/gui/add_furnance_btn.png");

    protected ImageButton addCraftingShapedBtn;
    protected ImageButton addFurnanceBtn;


    protected final Integer textureWidth = 300;
    protected final Integer textureHeight = 300;
    public RecipeListScreen(RecipeListContainer container, PlayerInventory inv, ITextComponent titleIn) {
        super(container, inv, titleIn);
        this.container = container;
        this.jsonPacket = JSON.parseObject(container.getPacketBuffer().readString());
        this.xSize = 148;
        this.ySize = 167;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

    }

    @Override
    protected void init() {
        super.init();
        addCraftingShapedBtn = new ImageButton(this.guiLeft + 5, this.guiTop - 18, 26, 16, 0, 0, 16, ADD_CRAFTING_SHAPED_BTN, this::onAddCraftingShapedBtnPressed);
        addFurnanceBtn = new ImageButton(this.guiLeft + 33, this.guiTop - 18, 26, 16, 0, 0, 16, ADD_FURNANCE_BTN, this::onAddFurnanceBtnPressed);
        this.addButton(addCraftingShapedBtn);
        this.addButton(addFurnanceBtn);
    }


    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);
        this.addCraftingShapedBtn.render(mouseX, mouseY, particleTick);
        this.addFurnanceBtn.render(mouseX,mouseX,particleTick);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(SCREEN_TEXTURE);
        blit(guiLeft, guiTop, 0, 0,148, 167, textureWidth, textureHeight);
    }

    private void onAddCraftingShapedBtnPressed(Button button) {
        JSONObject recipeJsonData = new JSONObject(true);
        recipeJsonData.put("jsonPacket",jsonPacket);
        Networking.INSTANCE.sendToServer(new OpenCraftingShapedScreen(recipeJsonData.toJSONString()));
        this.minecraft.player.closeScreen();
    }
    private void onAddFurnanceBtnPressed(Button button) {
        JSONObject recipeJsonData = new JSONObject(true);
        recipeJsonData.put("jsonPacket",jsonPacket);
        Networking.INSTANCE.sendToServer(new OpenCraftingShapedScreen(recipeJsonData.toJSONString()));
        this.minecraft.player.closeScreen();
    }
}
