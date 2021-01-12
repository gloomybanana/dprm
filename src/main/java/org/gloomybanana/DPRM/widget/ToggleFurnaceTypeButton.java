package org.gloomybanana.DPRM.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import org.gloomybanana.DPRM.DPRM;

public class ToggleFurnaceTypeButton extends Button {

  public Type type;

  public ToggleFurnaceTypeButton(int widthIn, int heightIn, int width, int height, IPressable onPress, Type type) {
    super(widthIn, heightIn, width, height, new StringTextComponent(""), onPress);
    this.type = type;
  }

  public void toggle(){
    int ordinal = type.ordinal();
    ordinal++;
    if (ordinal > 3)ordinal = 0;
    type = Type.values()[ordinal];
  }

  public enum Type {
    smelting, blasting, smoking, campfire_cooking;

    public ItemStack getIcon(){
      switch (this){
        default:
        case smelting:return new ItemStack(Blocks.FURNACE);
        case smoking:return new ItemStack(Blocks.SMOKER);
        case blasting:return new ItemStack(Blocks.BLAST_FURNACE);
        case campfire_cooking:return new ItemStack(Blocks.CAMPFIRE);
      }
    }

    public String getTitle(){
      switch (this){
        default:
        case smelting:return I18n.format("gui."+ DPRM.MOD_ID+".smelting.title");
        case smoking:return I18n.format("gui."+ DPRM.MOD_ID+".smoking.title");
        case blasting:return I18n.format("gui."+ DPRM.MOD_ID+".blasting.title");
        case campfire_cooking:return I18n.format("gui."+ DPRM.MOD_ID+".campfire_cooking.title");
      }
    }
  }
}
