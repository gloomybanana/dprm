package org.gloomybanana.DPRM.hander;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.*;

public class Registry {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS_TYPE = DeferredRegister.create(ForgeRegistries.CONTAINERS, DPRM.MOD_ID);

    //Container
    public static RegistryObject<ContainerType<CraftingContainer>> craftingContainer = CONTAINERS_TYPE.register("crafting_container", () -> IForgeContainerType.create(CraftingContainer::new));
    public static RegistryObject<ContainerType<FurnaceContainer>> furnaceContainer = CONTAINERS_TYPE.register("furnace_container",() -> IForgeContainerType.create(FurnaceContainer::new));
    public static RegistryObject<ContainerType<StonecuttingContainer>> stonecuttingContainer = CONTAINERS_TYPE.register("stonecutting_container",() -> IForgeContainerType.create(StonecuttingContainer::new));
    public static RegistryObject<ContainerType<SmithingContainer>> smithingContainer = CONTAINERS_TYPE.register("smithing_container",() -> IForgeContainerType.create(SmithingContainer::new));
    public static RegistryObject<ContainerType<RecipeListContainer>> recipeListContainer = CONTAINERS_TYPE.register("recipe_list_container",() -> IForgeContainerType.create(RecipeListContainer::new));

}
