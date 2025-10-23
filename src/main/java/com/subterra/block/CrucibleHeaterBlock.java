package com.subterra.block;

import com.mojang.serialization.MapCodec;
import com.subterra.block.entity.AlloyFurnaceBlockEntity;
import com.subterra.block.entity.CrucibleHeaterBlockEntity;
import com.subterra.block.entity.ModBlockEntities;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrucibleHeaterBlock extends BlockWithEntity implements BlockEntityProvider {
    protected CrucibleHeaterBlock(Settings settings) {
        super(settings);
    }
    public static final BooleanProperty LIT = Properties.LIT;
    public static final BooleanProperty POWERED = Properties.POWERED;

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CrucibleHeaterBlock.createCodec(CrucibleHeaterBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrucibleHeaterBlockEntity(pos, state);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(World world, BlockEntityType<T> givenType, BlockEntityType<? extends CrucibleHeaterBlockEntity> expectedType) {
        return world.isClient ? null : CrucibleHeaterBlock.validateTicker(givenType, expectedType, CrucibleHeaterBlockEntity::tick);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return CrucibleHeaterBlock.validateTicker(world, type, ModBlockEntities.CRUCIBLE_HEATER_BLOCK_ENTITY);
    }
}
