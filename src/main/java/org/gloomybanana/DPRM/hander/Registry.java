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
import org.gloomybanana.DPRM.container.*;

public class Registry {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, DPRM.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS,DPRM.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS_TYPE = new DeferredRegister<>(ForgeRegistries.CONTAINERS, DPRM.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, DPRM.MOD_ID);

    //Item

    //Block

    //TileEntiy

    //Container
    public static RegistryObject<ContainerType<CraftingShapedContainer>> craftingShapedContainer = CONTAINERS_TYPE.register("crafting_shaped_container", () -> IForgeContainerType.create(CraftingShapedContainer::new));
    public static RegistryObject<ContainerType<CraftingShapelessContainer>> craftingShapelessContainer = CONTAINERS_TYPE.register("crafting_shapeless_container", () -> IForgeContainerType.create(CraftingShapelessContainer::new));
    public static RegistryObject<ContainerType<SmeltingContainer>> smeltingContainer = CONTAINERS_TYPE.register("smelting_container",() -> IForgeContainerType.create(SmeltingContainer::new));
    public static RegistryObject<ContainerType<SmokingContainer>> smokingContainer = CONTAINERS_TYPE.register("smoking_container",() -> IForgeContainerType.create(SmokingContainer::new));
    public static RegistryObject<ContainerType<BlastingContainer>> blastingContainer = CONTAINERS_TYPE.register("blasting_container",() -> IForgeContainerType.create(BlastingContainer::new));
    public static RegistryObject<ContainerType<CampfireCookingContainer>> campfireCookingContainer = CONTAINERS_TYPE.register("campfire_cooking_container",() -> IForgeContainerType.create(CampfireCookingContainer::new));
    public static RegistryObject<ContainerType<StonecuttingContainer>> stonecuttingContainer = CONTAINERS_TYPE.register("stonecutting_container",() -> IForgeContainerType.create(StonecuttingContainer::new));

}
