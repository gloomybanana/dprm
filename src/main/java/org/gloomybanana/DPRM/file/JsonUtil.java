package org.gloomybanana.DPRM.file;

import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public class JsonUtil {


    public static String getJsonKey(List<Slot> slots){
        StringBuilder key = new StringBuilder();
        for (Slot slot : slots) {
            if (slot.getSlotIndex()>=1&&slot.getSlotIndex()<=9&&!slot.getStack().isEmpty()){
                key.append("        \""+slot.getSlotIndex()+"\": {\n" +
                        "            \"item\": \""+slot.getStack().getItem().getRegistryName()+"\"\n" +
                        "            \"tag\": \""+slot.getStack().getItem().getTags()+"\"\n" +
                        "        },\n");
            }
        }
        return (key.substring(0, key.length() - 2) + "\n");
    }

    public static String getJsonPattern(List<Slot> slots){
        Slot slot1 = null,slot2 = null,slot3 = null;
        Slot slot4 = null,slot5 = null,slot6 = null;
        Slot slot7 = null,slot8 = null,slot9 = null;
        String p1 = " ",p2 = " ",p3 = " ",p4 = " ",p5 = " ",p6 = " ",p7 = " ",p8 = "",p9 = " ";
        for (Slot slot : slots) {
            if (slot.getSlotIndex()==1&&!slot.getStack().isEmpty())p1 = "1";
            if (slot.getSlotIndex()==2&&!slot.getStack().isEmpty())p2 = "2";
            if (slot.getSlotIndex()==3&&!slot.getStack().isEmpty())p3 = "3";
            if (slot.getSlotIndex()==4&&!slot.getStack().isEmpty())p4 = "4";
            if (slot.getSlotIndex()==5&&!slot.getStack().isEmpty())p5 = "5";
            if (slot.getSlotIndex()==6&&!slot.getStack().isEmpty())p6 = "6";
            if (slot.getSlotIndex()==7&&!slot.getStack().isEmpty())p7 = "7";
            if (slot.getSlotIndex()==8&&!slot.getStack().isEmpty())p8 = "8";
            if (slot.getSlotIndex()==9&&!slot.getStack().isEmpty())p9 = "9";
        }


        //3x3模式
        String pattern = "        \""+p1+p2+p3+"\",\n" +
                "        \""+p4+p5+p6+"\",\n" +
                "        \""+p7+p8+p9+"\"\n";

        //2x2模式
        if (                                                slot3.getStack().isEmpty() &&
                slot6.getStack().isEmpty() &&
                slot7.getStack().isEmpty() && slot8.getStack().isEmpty() && slot9.getStack().isEmpty())
        {
            pattern = "        \""+p1+p2+"\",\n" +
                    "        \""+p4+p5+"\",\n";
        }
        if (slot1.getStack().isEmpty() &&
                slot4.getStack().isEmpty() &&
                slot7.getStack().isEmpty() && slot8.getStack().isEmpty() && slot9.getStack().isEmpty())
        {
            pattern = "        \""+p2+p3+"\",\n" +
                    "        \""+p5+p6+"\",\n";
        }
        if (slot1.getStack().isEmpty() && slot2.getStack().isEmpty() && slot3.getStack().isEmpty() &&
                slot6.getStack().isEmpty() &&
                slot9.getStack().isEmpty())
        {
            pattern = "        \""+p4+p5+"\",\n" +
                    "        \""+p7+p8+"\",\n";
        }
        if (slot1.getStack().isEmpty() && slot2.getStack().isEmpty() && slot3.getStack().isEmpty() &&
                slot4.getStack().isEmpty() &&
                slot7.getStack().isEmpty()                                            )
        {
            pattern = "        \""+p5+p6+"\",\n" +
                    "        \""+p8+p9+"\",\n";
        }



        return pattern;
    }

    public static String getJsonCraftingResult(ItemStack itemStack){

        String result = "\"item\": \""+itemStack.getItem().getRegistryName()+"\",\n" +
                "        \"count\":"+itemStack.getCount()+"\n";

        return result;
    }
}
