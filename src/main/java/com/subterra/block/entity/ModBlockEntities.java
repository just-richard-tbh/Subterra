package com.subterra.block.entity;

import com.subterra.Subterra;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import static com.subterra.block.ModBlocks.*;

public class ModBlockEntities {
    public static BlockEntityType<AlloyFurnaceBlockEntity> ALLOY_FURNACE_BLOCK_ENTITY;
    public static BlockEntityType<SteelFurnaceBlockEntity> STEEL_FURNACE_BLOCK_ENTITY;
    public static BlockEntityType<ArcFurnaceBlockEntity> ARC_FURNACE_BLOCK_ENTITY;
    public static BlockEntityType<CrucibleHeaterBlockEntity> CRUCIBLE_HEATER_BLOCK_ENTITY;
    public static void registerBlockEntityTypes() {
        ALLOY_FURNACE_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE, Identifier.of(Subterra.MOD_ID, "alloy_furnace_be"),
                BlockEntityType.Builder.create(AlloyFurnaceBlockEntity::new, ALLOY_FURNACE_BLOCK).build()
        );
        STEEL_FURNACE_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE, Identifier.of(Subterra.MOD_ID, "steel_furnace_be"),
                BlockEntityType.Builder.create(SteelFurnaceBlockEntity::new, STEEL_FURNACE).build()
        );
        ARC_FURNACE_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE, Identifier.of(Subterra.MOD_ID, "arc_furnace_be"),
                BlockEntityType.Builder.create(ArcFurnaceBlockEntity::new, ARC_FURNACE).build()
        );
        CRUCIBLE_HEATER_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE, Identifier.of(Subterra.MOD_ID, "crucible_heater_be"),
                BlockEntityType.Builder.create((BlockPos blockPos, BlockState blockState) -> new CrucibleHeaterBlockEntity(blockPos, blockState), CRUCIBLE_HEATER).build()
        );
    }
}
