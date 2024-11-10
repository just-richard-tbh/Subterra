//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.subterra.block;

import com.mojang.serialization.MapCodec;
import com.subterra.block.entity.AbstractFastFurnaceBlockEntity;
import com.subterra.block.entity.SteelFurnaceBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import com.subterra.block.entity.AbstractFastFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import com.subterra.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class SteelFurnaceBlock extends AbstractFastFurnaceBlock {
    public static final MapCodec<SteelFurnaceBlock> CODEC = createCodec(SteelFurnaceBlock::new);

    public MapCodec<SteelFurnaceBlock> getCodec() {
        return CODEC;
    }

    public SteelFurnaceBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SteelFurnaceBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(world, type, ModBlockEntities.STEEL_FURNACE_BLOCK_ENTITY);
    }

    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SteelFurnaceBlockEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory)blockEntity);
        }

    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if ((Boolean)state.get(LIT)) {
            double d = (double)pos.getX() + 0.5;
            double e = (double)pos.getY();
            double f = (double)pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1) {
                world.playSound(d, e, f, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = (Direction)state.get(FACING);
            Direction.Axis axis = direction.getAxis();
            double g = 0.52;
            double h = random.nextDouble() * 0.6 - 0.3;
            double i = axis == Axis.X ? (double)direction.getOffsetX() * 0.52 : h;
            double j = random.nextDouble() * 9.0 / 16.0;
            double k = axis == Axis.Z ? (double)direction.getOffsetZ() * 0.52 : h;
            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
        }
    }
}
