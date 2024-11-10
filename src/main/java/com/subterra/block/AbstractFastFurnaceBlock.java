//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.subterra.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import com.subterra.block.entity.AbstractFastFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractFastFurnaceBlock extends BlockWithEntity {
    public static final DirectionProperty FACING;
    public static final BooleanProperty LIT;

    protected AbstractFastFurnaceBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(LIT, false));
    }

    protected abstract MapCodec<? extends AbstractFastFurnaceBlock> getCodec();

    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            this.openScreen(world, pos, player);
            return ActionResult.CONSUME;
        }
    }

    protected abstract void openScreen(World world, BlockPos pos, PlayerEntity player);

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AbstractFastFurnaceBlockEntity) {
                if (world instanceof ServerWorld) {
                    ItemScatterer.spawn(world, pos, (AbstractFastFurnaceBlockEntity)blockEntity);
                    ((AbstractFastFurnaceBlockEntity)blockEntity).getRecipesUsedAndDropExperience((ServerWorld)world, Vec3d.ofCenter(pos));
                }

                super.onStateReplaced(state, world, pos, newState, moved);
                world.updateComparators(pos, this);
            } else {
                super.onStateReplaced(state, world, pos, newState, moved);
            }

        }
    }

    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, LIT});
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(World world, BlockEntityType<T> givenType, BlockEntityType<? extends AbstractFastFurnaceBlockEntity> expectedType) {
        return world.isClient ? null : validateTicker(givenType, expectedType, AbstractFastFurnaceBlockEntity::tick);
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        LIT = Properties.LIT;
    }
}
