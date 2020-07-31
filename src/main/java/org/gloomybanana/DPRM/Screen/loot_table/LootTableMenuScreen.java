package org.gloomybanana.DPRM.Screen.loot_table;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import org.gloomybanana.DPRM.container.loot_table.LootTableMenuContainer;

public class LootTableMenuScreen extends ContainerScreen<LootTableMenuContainer> {
    protected final JSONObject jsonPacket;
    Integer total_pages;
    Integer current_page;
    JSONArray current_page_recipe_list;
    JSONArray recipe_list;

    public LootTableMenuScreen(LootTableMenuContainer container, PlayerInventory inv, ITextComponent titleIn) {
        super(container, inv, titleIn);
        this.xSize = 148;
        this.ySize = 167;
        this.guiTop = (this.height - this.ySize) / 2;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.jsonPacket = JSON.parseObject(container.getPacketBuffer().readString());
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
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {



    }
}
