//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.subterra.block.entity;

import net.minecraft.block.BlockState;
import com.subterra.block.entity.AbstractFastFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.BlastFurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class SteelFurnaceBlockEntity extends AbstractFastFurnaceBlockEntity {

    public SteelFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STEEL_FURNACE_BLOCK_ENTITY, pos, state, RecipeType.SMELTING);
    }

    protected Text getContainerName() {
        return Text.translatable("container.steel_furnace");
    }

    protected int getFuelTime(ItemStack fuel) {
        return super.getFuelTime(fuel) / 2;
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new BlastFurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}
