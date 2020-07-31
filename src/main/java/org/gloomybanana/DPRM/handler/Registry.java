package org.gloomybanana.DPRM.handler;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.gloomybanana.DPRM.DPRM;
import org.gloomybanana.DPRM.container.loot_table.LootTableMenuContainer;
import org.gloomybanana.DPRM.container.vanilla.CraftingContainer;
import org.gloomybanana.DPRM.container.vanilla.FurnaceContainer;
import org.gloomybanana.DPRM.container.vanilla.RecipeMenuContainer;
import org.gloomybanana.DPRM.container.vanilla.StonecuttingContainer;

public class  Registry {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS_TYPE = new DeferredRegister<>(ForgeRegistries.CONTAINERS, DPRM.MOD_ID);

    //vanilla_recipe
    public static RegistryObject<ContainerType<CraftingContainer>> craftingContainer = CONTAINERS_TYPE.register("crafting_container", () -> IForgeContainerType.create(CraftingContainer::new));
    public static RegistryObject<ContainerType<FurnaceContainer>> furnaceContainer = CONTAINERS_TYPE.register("furnace_container",() -> IForgeContainerType.create(FurnaceContainer::new));
    public static RegistryObject<ContainerType<StonecuttingContainer>> stonecuttingContainer = CONTAINERS_TYPE.register("stonecutting_container",() -> IForgeContainerType.create(StonecuttingContainer::new));
    public static RegistryObject<ContainerType<RecipeMenuContainer>> recipeListContainer = CONTAINERS_TYPE.register("recipe_list_container",() -> IForgeContainerType.create(RecipeMenuContainer::new));

    //loot_table
    public static RegistryObject<ContainerType<LootTableMenuContainer>> lootTableMenuContainer = CONTAINERS_TYPE.register("loot_table_menu_container",()->IForgeContainerType.create(LootTableMenuContainer::new));
}
