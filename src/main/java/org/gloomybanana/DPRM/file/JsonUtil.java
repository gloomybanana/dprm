package org.gloomybanana.DPRM.file;

import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

import java.util.List;

public class JsonUtil {


    public static String getJsonKey(Slot[] slots){
        StringBuilder key = new StringBuilder();
        for (int i = 1;i <= 9; i++) {
            if (!slots[i].getStack().isEmpty()){
                key.append("        \""+i+"\": {\n" +
                        "            \"item\": \""+slots[i].getStack().getItem().getRegistryName()+"\"\n" +
//                        "            \"tag\": \""+slots[i].getStack().getItem().getTags()+"\"\n" +
                        "        },\n");
            }
        }

        return (key.substring(0, key.length() - 2) + "\n");
    }

    public static String getJsonPattern(Slot[] slots){


        String p1 = " ",p2 = " ",p3 = " ",p4 = " ",p5 = " ",p6 = " ",p7 = " ",p8 = "",p9 = " ";

        if (!slots[1].getStack().isEmpty())p1 = "1";
        if (!slots[2].getStack().isEmpty())p2 = "2";
        if (!slots[3].getStack().isEmpty())p3 = "3";
        if (!slots[4].getStack().isEmpty())p4 = "4";
        if (!slots[5].getStack().isEmpty())p5 = "5";
        if (!slots[6].getStack().isEmpty())p6 = "6";
        if (!slots[7].getStack().isEmpty())p7 = "7";
        if (!slots[8].getStack().isEmpty())p8 = "8";
        if (!slots[9].getStack().isEmpty())p9 = "9";



        //3x3模式
        String pattern = "        \""+p1+p2+p3+"\",\n" +
                "        \""+p4+p5+p6+"\",\n" +
                "        \""+p7+p8+p9+"\"\n";

        //2x2模式
        if (                                                slots[3].getStack().isEmpty() &&
                slots[6].getStack().isEmpty() &&
                slots[7].getStack().isEmpty() && slots[8].getStack().isEmpty() && slots[9].getStack().isEmpty())
        {
            pattern = "        \""+p1+p2+"\",\n" +
                    "        \""+p4+p5+"\"\n";
        }
        if (slots[1].getStack().isEmpty() &&
                slots[4].getStack().isEmpty() &&
                slots[7].getStack().isEmpty() && slots[8].getStack().isEmpty() && slots[9].getStack().isEmpty())
        {
            pattern = "        \""+p2+p3+"\",\n" +
                    "        \""+p5+p6+"\"\n";
        }
        if (slots[1].getStack().isEmpty() && slots[2].getStack().isEmpty() && slots[3].getStack().isEmpty() &&
                slots[6].getStack().isEmpty() &&
                slots[9].getStack().isEmpty())
        {
            pattern = "        \""+p4+p5+"\",\n" +
                    "        \""+p7+p8+"\"\n";
        }
        if (slots[1].getStack().isEmpty() && slots[2].getStack().isEmpty() && slots[3].getStack().isEmpty() &&
                slots[4].getStack().isEmpty() &&
                slots[7].getStack().isEmpty()                                            )
        {
            pattern = "        \""+p5+p6+"\",\n" +
                    "        \""+p8+p9+"\"\n";
        }



        return pattern;
    }

    public static String getJsonCraftingResult(ItemStack itemStack){

        String result = "        \"item\": \""+itemStack.getItem().getRegistryName()+"\",\n" +
                "        \"count\":"+itemStack.getCount()+"\n";

        return result;
    }
}
