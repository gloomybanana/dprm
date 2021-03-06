package org.gloomybanana.DPRM.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import org.gloomybanana.DPRM.DPRM;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VanillaRecipeJson {
    /**
     * 创建数据包
     * @param jsonPacket jsonPacket
     * @return 数据包文件夹
     * @throws IOException
     */
    private static File createDatapack(JSONObject jsonPacket) throws IOException {
        String player_name = jsonPacket.getJSONObject("player").getString("name");
        String datapacks_dir_path = jsonPacket.getJSONObject("player").getString("datapack_path");
        File pack_mcmeta = new File(datapacks_dir_path+"\\add_by_"+player_name+"\\pack.mcmeta");
        if (!pack_mcmeta.exists()){
            boolean mkdirs = pack_mcmeta.getParentFile().mkdirs();
            OutputStream outputStream = new FileOutputStream(pack_mcmeta);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.append("{\n" + "    \"pack\": {\n" + "        \"description\": \"A custom datapack generated by ”Datapack Recipe Maker“ mod, You can download it from https://www.curseforge.com/minecraft/mc-mods/datapackrecipemaker\",\n" + "        \"pack_format\": 5,\n" + "        \"_comment\": \" " + "Create by ").append(player_name).append(".\"\n").append("    }\n").append("}\n");
            writer.close();
            outputStream.close();
        }
        return pack_mcmeta.getParentFile();
    }

    /**
     * 查询所有已存在配方
     * @param jsonPacket
     * @return 配方json数组
     * @throws IOException
     */
    public static JSONArray getAllRecipes(JSONObject jsonPacket) throws IOException {
        JSONArray jsonArray = new JSONArray();
        String player_name = jsonPacket.getJSONObject("player").getString("name");
        String datapacks_dir_path = jsonPacket.getJSONObject("player").getString("datapack_path");
        File datapack_path = new File(datapacks_dir_path+"//add_by_"+player_name);
        File minecraftRecipesDir = new File(datapack_path + "//data//minecraft//recipes");
        if (!minecraftRecipesDir.exists())return jsonArray;
        for (File file : minecraftRecipesDir.listFiles()) {
            if (file.getName().contains(".json")){
                JSONObject recipe = new JSONObject(true);
                String content = readFileContent(file.getPath());
                String recipe_name_json = file.getName();
                String recipe_name = recipe_name_json.substring(0, recipe_name_json.lastIndexOf("."));
                recipe.put("recipe_name",recipe_name);
                recipe.put("content",JSONObject.parseObject(content));
                jsonArray.add(recipe);
            }
        }
        return jsonArray;
    }
    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
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

    public static String deleteJsonFile(JSONObject jsonPacket){

        String player_name = jsonPacket.getJSONObject("player").getString("name");
        String select_recipe_name = jsonPacket.getString("select_recipe_name");
        String datapacks_dir_path = jsonPacket.getJSONObject("player").getString("datapack_path");
        File datapack_path = new File(datapacks_dir_path+"\\add_by_"+player_name);
        File select_recipe_json = new File(datapack_path + "//data//minecraft//recipes//"+select_recipe_name+".json");
        if (select_recipe_json.exists()) {
            boolean delete = select_recipe_json.delete();
            return I18n.format("gui."+ DPRM.MOD_ID+".chat.delete_success");
        }
        return I18n.format("gui."+ DPRM.MOD_ID+".chat.delete_failed");
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
     * @param slots 界面面插槽
     * @param groupName group名
     * @return 切石机配方json
     */
    public static JSONObject genFurnaceRecipe(Slot[] slots, String groupName,Double experience,Integer cookingtime,String furnaceType){
        JSONObject furnaceRecipe = new JSONObject(true);
        furnaceRecipe.put("type",furnaceType);
        furnaceRecipe.put("group",groupName);
        JSONObject ingredient = new JSONObject(true);
        ingredient.put("item",slots[1].getStack().getItem().getRegistryName().toString());
        furnaceRecipe.put("ingredient",ingredient);
        furnaceRecipe.put("result",slots[0].getStack().getItem().getRegistryName().toString());
        furnaceRecipe.put("experience",experience);
        furnaceRecipe.put("cookingtime",cookingtime);
        return furnaceRecipe;
    }
    /**
     * 生成切石机配方json
     * @param slots 界面面插槽
     * @param groupName group名
     * @return 切石机配方json
     */
    public static JSONObject genStonecuttingRecipe(Slot[] slots,String groupName){
        JSONObject stonecuttingRecipe = new JSONObject(true);
        stonecuttingRecipe.put("type","stonecutting");
        stonecuttingRecipe.put("group",groupName);
        JSONObject ingredient = new JSONObject(true);
        ingredient.put("item",slots[1].getStack().getItem().getRegistryName().toString());
        stonecuttingRecipe.put("ingredient",ingredient);
        stonecuttingRecipe.put("result",slots[0].getStack().getItem().getRegistryName().toString());
        stonecuttingRecipe.put("count",slots[0].getStack().getCount());
        return stonecuttingRecipe;
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

    public static String getResultName(JSONObject content){
        String type = content.getString("type");
        if (type.equals("blasting")||type.equals("campfire_cooking")||type.equals("smelting")||type.equals("smoking")||type.equals("stonecutting")){
            return content.getString("result");
        }
        if (type.equals("minecraft:crafting_shaped")||type.equals("minecraft:crafting_shapeless")){
            return content.getJSONObject("result").getString("item");
        }else return "minecraft:air";
    }

    public static String translateRegisryName(String regisryName){
        ITextComponent name = ForgeRegistries.ITEMS.getValue(new ResourceLocation(regisryName)).getName();
        return name.getString();
    }

    public static String translateRecipeType(String typeName){
        switch (typeName) {
            case "minecraft:crafting_shaped":
                return I18n.format("gui." + DPRM.MOD_ID + ".type.crafting_shaped");
            case "minecraft:crafting_shapeless":
                return I18n.format("gui." + DPRM.MOD_ID + ".type.crafting_shapeless");
            case "smelting":
                return I18n.format("gui." + DPRM.MOD_ID + ".type.smelting");
            case "smoking":
                return I18n.format("gui." + DPRM.MOD_ID + ".type.smoking");
            case "blasting":
                return I18n.format("gui." + DPRM.MOD_ID + ".type.blasting");
            case "campfire_cooking":
                return I18n.format("gui." + DPRM.MOD_ID + ".type.campfire_cooking");
            case "stonecutting":
                return I18n.format("gui." + DPRM.MOD_ID + ".type.stonecutting");
        }
        return "other";
    }
}
