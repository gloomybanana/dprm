package org.gloomybanana.DPRM.file;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import java.io.*;
import java.util.List;

public class FileManager {
    /**
     * 创建数据包
     * @param player 创建者的玩家ID
     * @return 原版数据包recipes路径
     * @throws IOException 异常
     */
    public static File createMinecraftDatapackRecipesDir(ServerPlayerEntity player) throws IOException {
        String datapackName = "add_by_" + player.getName().getFormattedText();
        File worldDir = player.getServerWorld().getSaveHandler().getWorldDirectory();
        File datapackDir = new File(worldDir.getPath() + "\\datapacks\\" + datapackName + "\\data\\minecraft\\recipes");
        File pack_mcmeta = new File(worldDir.getPath() + "\\datapacks\\" + datapackName + "\\pack.mcmeta");


        if (!datapackDir.exists()) {
            boolean mkdir = datapackDir.mkdirs();
            if (mkdir) {
                OutputStream outputStream = new FileOutputStream(pack_mcmeta);
                OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
                writer.append("{\n" +
                        "    \"pack\": {\n" +
                        "        \"description\": \"dprm resources\",\n" +
                        "        \"pack_format\": 5,\n" +
                        "        \"_comment\": \"add by " + player.getName().getFormattedText() + ".\"\n" +
                        "    }\n" +
                        "}\n");
                writer.close();
                outputStream.close();
                System.out.println("datapack \""+datapackName+"\" generate success!");
                return datapackDir;
            }
        }
        return null;
    }

    /**
     * 创建有序合成json配方文件
     * @param slots 有序合成Container插槽
     * @param recipeName recipe名称
     * @param groupName group名称
     * @param player 玩家
     * @return 数据包名称
     * @throws IOException 异常
     */
    public static String createShapedCraftingRecipe(List<Slot> slots,String recipeName,String groupName,ServerPlayerEntity player) throws IOException {

        File datapackRecipesDir = createMinecraftDatapackRecipesDir(player);
        Slot resultSlot = null;
        for (Slot slot : slots) {
            if (slot.getSlotIndex()==0) resultSlot = slot;
        }


        File recipeJson = new File(datapackRecipesDir + "//" + recipeName + ".json");
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
                        JsonUtil.getJsonCraftingResult(resultSlot.getStack()) +
                "    }\n" +
                "}");
        writer.close();
        outputStream.close();
        System.out.println("datapack \""+recipeName+"\" generate success!");

        return recipeName;
    }

    public static void createShapelessCraftingRecipe(){

    }

    public static void createFurnanceRecipe(){

    }
}
