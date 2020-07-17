package org.gloomybanana.DPRM.Screen;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.recipebook.IRecipeUpdateListener;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipePlacer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.Iterator;
import java.util.List;

public class MenuGui extends AbstractGui implements IRenderable, IGuiEventListener, IRecipeUpdateListener, IRecipePlacer<Ingredient> {
    public static ResourceLocation MENUGUI_TEXTURE;




    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {

    }

    @Override
    public void recipesShown(List<IRecipe<?>> recipes) {

    }

    @Override
    public void setSlotContents(Iterator<Ingredient> ingredients, int slotIn, int maxAmount, int y, int x) {

    }
}
