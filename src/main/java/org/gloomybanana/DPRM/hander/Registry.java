package org.gloomybanana.DPRM.hander;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.block.RockBlock;
import org.gloomybanana.DPRM.container.ShapedCraftingContainer;
import org.gloomybanana.DPRM.container.ShapelessCraftingContainer;
import org.gloomybanana.DPRM.item.ItemInMaterials;

public class Registry {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, DPRM.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS,DPRM.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, DPRM.MOD_ID);


    //Item
    public static RegistryObject<Item> demoItem1 = ITEMS.register("demo_item_1", ItemInMaterials::new);

    //Block
    public static RegistryObject<Block> demoBlock1 = BLOCKS.register("demo_block_1", RockBlock::new);
    public static RegistryObject<Item> demoBlock1Item = ITEMS.register("obsidian_block", () -> {
        return new BlockItem(Registry.demoBlock1.get(), new Item.Properties().group(ItemGroup.MATERIALS));});

    //Container
    public static RegistryObject<ContainerType<ShapedCraftingContainer>> shapedCraftingContainer = CONTAINERS.register("crafting_container", () -> IForgeContainerType.create((windowId, playerInventory, data) -> new ShapedCraftingContainer(windowId,playerInventory)));
    public static RegistryObject<ContainerType<ShapelessCraftingContainer>> shapelessCraftingContainer = CONTAINERS.register("shapeless_crafting_container", () -> IForgeContainerType.create((windowId, playerInventory, data) -> new ShapelessCraftingContainer(windowId,playerInventory)));


}
