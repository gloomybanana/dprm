package org.gloomybanana.DPRM.dao;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class LootTableJson {

    public static JSONObject genLootTableJson(Slot[] slots, String groupName) {


        Item blockItem = slots[0].getStack().getItem();
        Block block = Block.getBlockFromItem(blockItem);
        if (block.equals(Blocks.AIR));
        String[] registryName = block.getRegistryName().toString().split(":");
        String nameSpace = registryName[0];
        String blockName = registryName[1];



        JSONObject loot_table_json = new JSONObject(true);
        loot_table_json.put("type","minecraft:block");
        loot_table_json.put("group", groupName);
        JSONObject key = new JSONObject();
        for (int i = 1; i <=9 ; i++) {
            if (!slots[i].getStack().isEmpty()){
                JSONObject itemStack = new JSONObject();
                itemStack.put("item",slots[i].getStack().getItem().getRegistryName().toString());
                key.put(""+i,itemStack);
            }
        }
        loot_table_json.put("key",key);
        JSONObject result = new JSONObject();
        result.put("item",slots[0].getStack().getItem().getRegistryName().toString());
        result.put("count",slots[0].getStack().getCount());
        loot_table_json.put("result",result);
        return loot_table_json;
    }
}
