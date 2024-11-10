package com.subterra.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;

public class SteelFurnaceScreenHandler extends AbstractFurnaceScreenHandler {
    public SteelFurnaceScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandlers.STEEL_FURNACE_SCREEN_HANDLER, RecipeType.SMELTING, RecipeBookCategory.FURNACE, syncId, playerInventory);
    }

    public SteelFurnaceScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.STEEL_FURNACE_SCREEN_HANDLER, RecipeType.SMELTING, RecipeBookCategory.FURNACE, syncId, playerInventory, inventory, propertyDelegate);
    }
}
