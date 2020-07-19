package org.gloomybanana.DPRM.file;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonManager {
    /**
     * 创建数据包
     * @param recipeDirPath 配方路径
     * @return 原版数据包recipes路径
     * @throws IOException 异常
     */
    public static File createMinecraftDatapackRecipesDir(String recipeDirPath) throws IOException {

        System.out.println("recipeDirPath:" + recipeDirPath);
        File recipeDir = new File(recipeDirPath);

        //  .\saves\New World\datapacks\add_by_Dev\data\minecraft\recipes
//        Pattern p = Pattern.compile("datapacks\\\\(.*?)\\\\data");
//        String datapackName = p.matcher(recipeDirPath).group(1);
//        System.out.println(datapackName);

        if (!recipeDir.exists()) {
            boolean mkdir = recipeDir.mkdirs();

            String pack_mcmetaPath = recipeDir.getParentFile().getParentFile().getParentFile().getPath();
            File pack_mcmeta = new File(pack_mcmetaPath + "\\pack.mcmeta");
            System.out.println("pack_mcmeta:"+pack_mcmetaPath);

            if (mkdir) {
                OutputStream outputStream = new FileOutputStream(pack_mcmeta);
                OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
                writer.append("{\n" +
                        "    \"pack\": {\n" +
                        "        \"description\": \"dprm resources\",\n" +
                        "        \"pack_format\": 5,\n" +
                        "        \"_comment\": \" " + "add by datapack recipe maker" + ".\"\n" +
                        "    }\n" +
                        "}\n");
                writer.close();
                outputStream.close();
                System.out.println("datapack \""+"datapackName"+"\" generate success!");
                return recipeDir.getParentFile().getParentFile().getParentFile();
            }
        }
        return null;
    }

    /**
     * 创建有序合成json配方文件
     * @param slots 有序合成Container插槽
     * @param recipeName recipe名称
     * @param groupName group名称
     * @param recipeDirPath 配方路径
     * @return 数据包名称
     * @throws IOException 异常
     */
    public static String createShapedCraftingRecipe(Slot[] slots,String recipeName,String groupName,String recipeDirPath) throws IOException {

        File datapackRecipesDir = createMinecraftDatapackRecipesDir(recipeDirPath);


        File recipeJson = new File(recipeDirPath + "//" + recipeName + ".json");
        OutputStream outputStream = new FileOutputStream(recipeJson);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.append("{\n" +
                "    \"type\": \"minecraft:crafting_shaped\",\n" +
                "    \"group\": \"" + groupName +"\",\n" +
                "    \"pattern\": [\n" +
                        JsonUtil.getJsonPattern(slots) +
                "    ],\n" +
                "    \"key\": {\n" +
                        JsonUtil.getJsonKey(slots) +
                "    },\n" +
                "    \"result\": {\n" +
                        JsonUtil.getJsonCraftingResult(slots[0].getStack()) +
                "    }\n" +
                "}");
        writer.close();
        outputStream.close();
        System.out.println("recipe \""+recipeJson.getPath()+"\" generate success!");

        return recipeJson.getPath();
    }

    public static void createShapelessCraftingRecipe(){

    }

    public static void createFurnanceRecipe(){

    }
}
