package com.subterra.block.entity;

import com.subterra.block.ArcFurnaceBlock;
import com.subterra.block.CrucibleHeaterBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import static com.subterra.block.CrucibleHeaterBlock.LIT;
import static com.subterra.block.CrucibleHeaterBlock.POWERED;

import java.util.List;

import static net.minecraft.block.entity.AbstractFurnaceBlockEntity.createFuelTimeMap;

public class CrucibleHeaterBlockEntity extends LootableContainerBlockEntity implements ImplementedInventory, SidedInventory, NamedScreenHandlerFactory {
    int currentFuel;
    int maxFuel;
    private DefaultedList<ItemStack> inventory;

    public CrucibleHeaterBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.CRUCIBLE_HEATER_BLOCK_ENTITY, blockPos, blockState);
        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        if (!this.writeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory, registryLookup);
        }

    }

    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.readLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory, registryLookup);
        }

    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return null;
    }

    @Override
    protected Text getContainerName() {
        return null;
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return null;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {

    }

    protected int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }
        Item item = fuel.getItem();
        return (createFuelTimeMap().getOrDefault(item, 0));
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, CrucibleHeaterBlockEntity blockEntity) {
        boolean hasPower = blockEntity.getCachedState().get(CrucibleHeaterBlock.POWERED);
        if (!hasPower && blockEntity.currentFuel >= 0){
            blockEntity.currentFuel = blockEntity.maxFuel = blockEntity.getFuelTime(blockEntity.inventory.get(0));
            blockEntity.inventory.get(0).decrement(1);
        } if (blockEntity.currentFuel > 0){
            --blockEntity.currentFuel;
            world.setBlockState(blockPos, blockState.with(LIT, true));
        } else {
            world.setBlockState(blockPos, blockState.with(LIT, false));
        }
    }
}
