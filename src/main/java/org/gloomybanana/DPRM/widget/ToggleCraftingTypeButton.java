package org.gloomybanana.DPRM.widget;

import net.minecraft.block.Blocks;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import org.gloomybanana.DPRM.DPRM;

public class ToggleCraftingTypeButton extends Button {

  public Type type;

  public ToggleCraftingTypeButton(int widthIn, int heightIn, int width, int height, IPressable onPress, Type type) {
    super(widthIn, heightIn, width, height, new StringTextComponent(""), onPress);
    this.type = type;
  }

  public void toggle(){
    int ordinal = type.ordinal();
    ordinal++;
    if (ordinal > 1)ordinal = 0;
    type = Type.values()[ordinal];
  }

  public enum Type {
    crafting_shaped, crafting_shapeless;


    public String getTitle(){
      switch (this){
        default:
        case crafting_shaped:return I18n.format("gui."+ DPRM.MOD_ID+".crafting_shaped.title");
        case crafting_shapeless:return I18n.format("gui."+ DPRM.MOD_ID+".crafting_shapeless.title");
      }
    }
  }
}
