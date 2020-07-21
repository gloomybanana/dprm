package org.gloomybanana.DPRM.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.minecraft.inventory.container.Slot;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonManager {
    /**
     * 创建数据包
     * @param jsonPacket jsonPacket
     * @return 数据包文件夹
     * @throws IOException
     */
    private static File createDatapack(JSONObject jsonPacket) throws IOException {
        String player_name = jsonPacket.getString("player_name");
        String datapacks_dir_path = jsonPacket.getString("datapacks_dir_path");
        File pack_mcmeta = new File(datapacks_dir_path+"\\add_by_"+player_name+"\\pack.mcmeta");
        if (!pack_mcmeta.exists()){
            boolean mkdirs = pack_mcmeta.getParentFile().mkdirs();
            OutputStream outputStream = new FileOutputStream(pack_mcmeta);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.append("{\n" + "    \"pack\": {\n" + "        \"description\": \"dprm resources\",\n" + "        \"pack_format\": 5,\n" + "        \"_comment\": \" " + "add by ").append(player_name).append(" recipe maker").append(".\"\n").append("    }\n").append("}\n");
            writer.close();
            outputStream.close();
            System.out.println("gen add_by_"+ player_name +" datapack successed!");
        }
        return pack_mcmeta.getParentFile();
    }

    /**
     * 创建配方json文件到数据包
     * @param jsonPacket "player_name"玩家名,"datapacks_dir_path"数据包文件夹路径
     * @param recipe 配方Json对象
     * @return
     */
    public static JSONObject createJsonFile(JSONObject jsonPacket,JSONObject recipe,String recipeName){
        JSONObject result = new JSONObject();
        try {
            File datapack = createDatapack(jsonPacket);
            File recipe_json = new File(datapack.getPath() + "//data//minecraft//recipes//" + recipeName + ".json");
            boolean mkdirs = recipe_json.getParentFile().mkdirs();
            OutputStream outputStream = new FileOutputStream(recipe_json);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.append(JSON.toJSONString(recipe, SerializerFeature.PrettyFormat));
            writer.close();
            outputStream.close();

            System.out.println("gen recipe \""+ recipeName +"\" successed!");
            result.put("success",true);
            result.put("dir",recipe_json.getPath());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result.put("success",false);
            result.put("dir","");
            return result;
        }
    }

    /**
     * 生成有序合成配方json
     * @param slots 合成界面插槽
     * @param groupName group名
     * @return 有序合成配方json
     */
    public static JSONObject genCraftingShapedRecipe(Slot[] slots,String groupName) {
        JSONObject craftingRecipe = new JSONObject(true);
        craftingRecipe.put("type","minecraft:crafting_shaped");
        craftingRecipe.put("group", groupName);
        craftingRecipe.put("pattern",craftingShapedPatten(slots));
         JSONObject key = new JSONObject();
         for (int i = 1; i <=9 ; i++) {
            if (!slots[i].getStack().isEmpty()){
                JSONObject itemStack = new JSONObject();
                itemStack.put("item",slots[i].getStack().getItem().getRegistryName().toString());
                key.put(""+i,itemStack);
            }
        }
        craftingRecipe.put("key",key);
         JSONObject result = new JSONObject();
         result.put("item",slots[0].getStack().getItem().getRegistryName().toString());
         result.put("count",slots[0].getStack().getCount());
        craftingRecipe.put("result",result);
        return craftingRecipe;
    }

    /**
     * 生成无序合成配方json
     * @param slots 合成界面插槽
     * @param groupName group名
     * @return 无序合成配方json
     */
    public static JSONObject genCraftingShapelessRecipe(Slot[] slots,String groupName){
        JSONObject craftingRecipe = new JSONObject(true);
        craftingRecipe.put("type","minecraft:crafting_shapeless");
        craftingRecipe.put("group", groupName);
         List<JSONObject> ingredients = new ArrayList<>();
         for (int i = 1; i <= 9; i++) {
            if (!slots[i].getStack().isEmpty()){
                JSONObject itemStack = new JSONObject();
                itemStack.put("item",slots[i].getStack().getItem().getRegistryName().toString());
                ingredients.add(itemStack);
            }
        }
        craftingRecipe.put("ingredients",ingredients);
         JSONObject result = new JSONObject();
         result.put("item",slots[0].getStack().getItem().getRegistryName().toString());
         result.put("count",slots[0].getStack().getCount());
        craftingRecipe.put("result",result);
        return craftingRecipe;
    }

    /**
     * 生成熔炉配方json
     * @param slots 制作面插槽
     * @param groupName group名
     * @return 熔炉配方json
     */
    public static JSONObject genSmeltingRecipe(Slot[] slots, String groupName){
        //TODO
        return null;
    }

    /**
     * 生成高炉配方json
     * @param slots 界面面插槽
     * @param groupName group名
     * @return 高炉配方json
     */
    public static JSONObject genBlastingRecipe(Slot[] slots,String groupName){
        //TODO
        return null;
    }

    /**
     * 生成营火配方json
     * @param slots 界面面插槽
     * @param groupName group名
     * @return 营火配方json
     */
    public static JSONObject genCampfireCookingRecipe(Slot[] slots,String groupName){
        //TODO
        return null;
    }

    /**
     * 生成烟熏配方json
     * @param slots 界面面插槽
     * @param groupName group名
     * @return 烟熏配方json
     */
    public static JSONObject genSmokingRecipe(Slot[] slots,String groupName){
        //TODO
        return null;
    }

    /**
     * 生成切石机配方json
     * @param slots 界面面插槽
     * @param groupName group名
     * @return 切石机配方json
     */
    public static JSONObject genStonecuttingRecipe(Slot[] slots,String groupName){
        //TODO
        return null;
    }

    /**
     * 获取有序合成排列模式
     * @param slots 合成插槽
     * @return 有序合成排列模式
     */
    public static List<String> craftingShapedPatten(Slot[] slots){
        List<String> pattern = new ArrayList<>();
        String p1 = " ",p2 = " ",p3 = " ",p4 = " ",p5 = " ",p6 = " ",p7 = " ",p8 = " ",p9 = " ";
        if (!slots[1].getStack().isEmpty())p1 = "1";
        if (!slots[2].getStack().isEmpty())p2 = "2";
        if (!slots[3].getStack().isEmpty())p3 = "3";
        if (!slots[4].getStack().isEmpty())p4 = "4";
        if (!slots[5].getStack().isEmpty())p5 = "5";
        if (!slots[6].getStack().isEmpty())p6 = "6";
        if (!slots[7].getStack().isEmpty())p7 = "7";
        if (!slots[8].getStack().isEmpty())p8 = "8";
        if (!slots[9].getStack().isEmpty())p9 = "9";

        if (                                                                    slots[3].getStack().isEmpty() &&
                                                                                slots[6].getStack().isEmpty() &&
                slots[7].getStack().isEmpty() && slots[8].getStack().isEmpty() && slots[9].getStack().isEmpty())
        {
            pattern.add(p1+p2);
            pattern.add(p4+p5);
            return pattern;
        }
        if (slots[1].getStack().isEmpty() &&
                slots[4].getStack().isEmpty() &&
                slots[7].getStack().isEmpty() && slots[8].getStack().isEmpty() && slots[9].getStack().isEmpty())
        {
            pattern.add(p2+p3);
            pattern.add(p5+p6);
            return pattern;
        }
        if (slots[1].getStack().isEmpty() && slots[2].getStack().isEmpty() && slots[3].getStack().isEmpty() &&
                slots[6].getStack().isEmpty() &&
                slots[9].getStack().isEmpty())
        {
            pattern.add(p4+p5);
            pattern.add(p7+p8);
            return pattern;
        }
        if (slots[1].getStack().isEmpty() && slots[2].getStack().isEmpty() && slots[3].getStack().isEmpty() &&
                slots[4].getStack().isEmpty() &&
                slots[7].getStack().isEmpty()                                            )
        {
            pattern.add(p5+p6);
            pattern.add(p8+p9);
            return pattern;
        }
        pattern.add(0,p1+p2+p3);
        pattern.add(1,p4+p5+p6);
        pattern.add(2,p7+p8+p9);
        return pattern;
    }
}