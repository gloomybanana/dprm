package org.gloomybanana.DPRM.widget;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import org.gloomybanana.DPRM.file.JsonManager;
import org.gloomybanana.DPRM.network.Networking;
import org.gloomybanana.DPRM.network.ScreenToggle;

import java.util.List;

public class DPRMRecipeWidget extends Widget {
    private static final ResourceLocation RECIPE_BOOK = new ResourceLocation("textures/gui/recipe_book.png");
    private final JSONObject recipe;
    private final JSONObject jsonPacket;
    private ItemStack resultItemStack;
    private FontRenderer font = Minecraft.getInstance().fontRenderer;
    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

    public DPRMRecipeWidget(int guiLeft, int guiTop, int recipeIndex, JSONObject recipe, JSONObject jsonPacket) {
        super(guiLeft + 11 + 25 * (recipeIndex % 5), guiTop + 31 + 25 * (recipeIndex / 5), 24, 24, "");
        this.recipe = recipe;
        this.jsonPacket = jsonPacket;
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partTick) {
        super.renderButton(mouseX, mouseY, partTick);
        Minecraft.getInstance().getTextureManager().bindTexture(RECIPE_BOOK);
        this.blit(this.x, this.y, 29, 206, 25, 25);
        String result_name = JsonManager.getResultName(recipe.getJSONObject("content"));
        resultItemStack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(result_name)));
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(resultItemStack,this.x+4,this.y+4);
    }

    @Override
    public void onClick(double p_onClick_1_, double p_onClick_3_) {
        String recipe_name = recipe.getString("recipe_name");
        String type = recipe.getJSONObject("content").getString("type");

        if (type.equals("minecraft:crafting_shaped")||type.equals("minecraft:crafting_shapeless")){
            jsonPacket.put("select_recipe_name",recipe_name);
            jsonPacket.put("operate","open_crafting_screen");
            Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
        }
        if (type.equals("smelting")||type.equals("blasting")||type.equals("smoking")||type.equals("campfire_cooking")){
            jsonPacket.put("select_recipe_name",recipe_name);
            jsonPacket.put("operate","open_furnace_screen");
            Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
        }
        if (type.equals("stonecutting")){
            jsonPacket.put("select_recipe_name",recipe_name);
            jsonPacket.put("operate","open_stonecutting_screen");
            Networking.INSTANCE.sendToServer(new ScreenToggle(jsonPacket.toJSONString()));
        }


        super.onClick(p_onClick_1_, p_onClick_3_);
    }

    @Override
    public void renderToolTip(int mouseX, int mouseY) {
        super.renderToolTip(mouseX, mouseY);
        renderTooltip( mouseX,  mouseY);
    }


    public void renderTooltip(int mouseX, int mouseY) {
        FontRenderer font = resultItemStack.getItem().getFontRenderer(resultItemStack);
        net.minecraftforge.fml.client.gui.GuiUtils.preItemToolTip(resultItemStack);
        this.renderTooltip(this.getTooltipFromItem(resultItemStack), mouseX, mouseY, (font == null ? this.font : font));
        net.minecraftforge.fml.client.gui.GuiUtils.postItemToolTip();
    }

    public List<String> getTooltipFromItem(ItemStack itemStack) {
        List<ITextComponent> list = itemStack.getTooltip(Minecraft.getInstance().player, Minecraft.getInstance().gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
        List<String> list1 = Lists.newArrayList();

        for(ITextComponent itextcomponent : list) {
            list1.add(itextcomponent.getFormattedText());
        }

        return list1;
    }

    public void renderTooltip(List<String> toolTips, int mouseX, int mouseY, FontRenderer font) {
        net.minecraftforge.fml.client.gui.GuiUtils.drawHoveringText(toolTips, mouseX, mouseY, width, height, -1, font);
        if (false && !toolTips.isEmpty()) {
            RenderSystem.disableRescaleNormal();
            RenderSystem.disableDepthTest();
            int i = 0;

            for(String s : toolTips) {
                int j = this.font.getStringWidth(s);
                if (j > i) {
                    i = j;
                }
            }

            int l1 = mouseX + 12;
            int i2 = mouseY - 12;
            int k = 8;
            if (toolTips.size() > 1) {
                k += 2 + (toolTips.size() - 1) * 10;
            }

            if (l1 + i > this.width) {
                l1 -= 28 + i;
            }

            if (i2 + k + 6 > this.height) {
                i2 = this.height - k - 6;
            }

            this.setBlitOffset(300);
            this.itemRenderer.zLevel = 300.0F;
            int l = -267386864;
            this.fillGradient(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, -267386864, -267386864);
            this.fillGradient(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, -267386864, -267386864);
            this.fillGradient(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, -267386864, -267386864);
            this.fillGradient(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, -267386864, -267386864);
            this.fillGradient(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, -267386864, -267386864);
            int i1 = 1347420415;
            int j1 = 1344798847;
            this.fillGradient(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, 1347420415, 1344798847);
            this.fillGradient(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, 1347420415, 1344798847);
            this.fillGradient(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, 1347420415, 1347420415);
            this.fillGradient(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, 1344798847, 1344798847);
            MatrixStack matrixstack = new MatrixStack();
            IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
            matrixstack.translate(0.0D, 0.0D, (double)this.itemRenderer.zLevel);
            Matrix4f matrix4f = matrixstack.getLast().getMatrix();

            for(int k1 = 0; k1 < toolTips.size(); ++k1) {
                String s1 = toolTips.get(k1);
                if (s1 != null) {
                    this.font.renderString(s1, (float)l1, (float)i2, -1, true, matrix4f, irendertypebuffer$impl, false, 0, 15728880);
                }

                if (k1 == 0) {
                    i2 += 2;
                }

                i2 += 10;
            }

            irendertypebuffer$impl.finish();
            this.setBlitOffset(0);
            this.itemRenderer.zLevel = 0.0F;
            RenderSystem.enableDepthTest();
            RenderSystem.enableRescaleNormal();
        }
    }
}
