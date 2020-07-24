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
    public static final DeferredRegister<ContainerType<?>> CONTAINERS_TYPE = new DeferredRegister<>(ForgeRegistries.CONTAINERS, DPRM.MOD_ID);

    //Container
    public static RegistryObject<ContainerType<CraftingShapedContainer>> craftingShapedContainer = CONTAINERS_TYPE.register("crafting_shaped_container", () -> IForgeContainerType.create(CraftingShapedContainer::new));
    public static RegistryObject<ContainerType<CraftingShapelessContainer>> craftingShapelessContainer = CONTAINERS_TYPE.register("crafting_shapeless_container", () -> IForgeContainerType.create(CraftingShapelessContainer::new));
    public static RegistryObject<ContainerType<FurnaceContainer>> furnaceContainer = CONTAINERS_TYPE.register("furnace_container",() -> IForgeContainerType.create(FurnaceContainer::new));
    public static RegistryObject<ContainerType<StonecuttingContainer>> stonecuttingContainer = CONTAINERS_TYPE.register("stonecutting_container",() -> IForgeContainerType.create(StonecuttingContainer::new));
    public static RegistryObject<ContainerType<RecipeListContainer>> recipeListContainer = CONTAINERS_TYPE.register("recipe_list_container",() -> IForgeContainerType.create(RecipeListContainer::new));

}
