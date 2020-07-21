package org.gloomybanana.DPRM.hander;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.CraftingShapedContainer;
import org.gloomybanana.DPRM.container.CraftingShapelessContainer;

public class Registry {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, DPRM.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS,DPRM.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS_TYPE = new DeferredRegister<>(ForgeRegistries.CONTAINERS, DPRM.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, DPRM.MOD_ID);

    //Item

    //Block

    //TileEntiy

    //Container
    public static RegistryObject<ContainerType<CraftingShapedContainer>> craftingShapedContainer = CONTAINERS_TYPE.register("crafting_shaped_container", () -> IForgeContainerType.create((windowId, playerInventory, packetBuffer) -> new CraftingShapedContainer(windowId,playerInventory,packetBuffer)));
    public static RegistryObject<ContainerType<CraftingShapelessContainer>> craftingShapelessContainer = CONTAINERS_TYPE.register("crafting_shapeless_container", () -> IForgeContainerType.create((windowId, playerInventory, packetBuffer) -> new CraftingShapelessContainer(windowId,playerInventory,packetBuffer)));


}
