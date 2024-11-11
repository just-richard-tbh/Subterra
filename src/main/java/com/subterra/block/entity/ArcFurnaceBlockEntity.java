//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.subterra.block.entity;

import com.google.common.collect.Lists;
import com.subterra.screen.ArcFurnaceScreenHandler;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import com.subterra.block.ArcFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

import static com.subterra.block.AlloyFurnaceBlock.LIT;

public class ArcFurnaceBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider {

//    INPUT_SLOT_INDEX = 0;
//    OUTPUT_SLOT_INDEX = 1;
//    COOK_TIME_PROPERTY_INDEX = 0;
//    COOK_TIME_TOTAL_PROPERTY_INDEX = 1;
//    POWERED_PROPERTY_INDEX = 2;
//    PROPERTY_COUNT = 3;
    private static final int[] BOTTOM_SLOTS = new int[]{1};
    private static final int[] SIDE_OR_TOP_SLOTS = new int[]{0};
    protected DefaultedList<ItemStack> inventory;
    int cookTime = 0;
    int cookTimeTotal = 100;
    int isPowered;
    protected final PropertyDelegate propertyDelegate;
    private final Object2IntOpenHashMap<Identifier> recipesUsed;
    private final RecipeManager.MatchGetter<SingleStackRecipeInput, ? extends BlastingRecipe> matchGetter;

    public ArcFurnaceBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ARC_FURNACE_BLOCK_ENTITY, blockPos, blockState);
        this.inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0 -> {
                        return  ArcFurnaceBlockEntity.this.cookTime;
                    }
                    case 1 -> {
                        return  ArcFurnaceBlockEntity.this.cookTimeTotal;
                    }
                    case 2 -> {
                        return  ArcFurnaceBlockEntity.this.isPowered;
                    }
                    default -> {
                        return 0;
                    }
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ArcFurnaceBlockEntity.this.cookTime = value;
                    case 1 -> ArcFurnaceBlockEntity.this.cookTimeTotal = value;
                    case 2 -> ArcFurnaceBlockEntity.this.isPowered = value;
                }
            }

            @Override
            public int size() { return 3; }
        };

        this.recipesUsed = new Object2IntOpenHashMap();
        this.matchGetter = RecipeManager.createCachedMatchGetter(RecipeType.BLASTING);
        }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.arc_furnace");
    }

    public static void tick(World world, BlockPos pos, BlockState state, ArcFurnaceBlockEntity blockEntity) {
        boolean hasPower = blockEntity.getCachedState().get(ArcFurnaceBlock.POWERED);
        ItemStack itemStack = (ItemStack)blockEntity.inventory.get(0);
        boolean isCrafting = false;
        if (isCrafting) {
            markDirty(world, pos, state);
        }
        if (hasPower){
            blockEntity.isPowered = 1;
            blockEntity.cookTimeTotal = getCookTime(world, blockEntity); //This is very bad and will probably cause tons of lag. Please rewrite this.
        } else {
            blockEntity.isPowered = 0;
        }


        RecipeEntry recipeEntry;

        if (!itemStack.isEmpty()){
            recipeEntry = (RecipeEntry)blockEntity.matchGetter.getFirstMatch(new SingleStackRecipeInput(itemStack), world).orElse(null);
        } else {
            recipeEntry = null;
        }

        int i = blockEntity.getMaxCountPerStack();
        if (hasPower && !itemStack.isEmpty() && canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, blockEntity.inventory, i)){
            state = (BlockState) state.with(ArcFurnaceBlock.LIT, blockEntity.getCachedState().get(ArcFurnaceBlock.POWERED));
            world.setBlockState(pos, state, 3);
            ++blockEntity.cookTime;
            isCrafting = true;
            if (blockEntity.cookTime >= blockEntity.cookTimeTotal){
                blockEntity.cookTime = 0;
                itemStack.decrement(1);
                if (craftRecipe(world.getRegistryManager(), recipeEntry, blockEntity.inventory, i)){
                    blockEntity.setLastRecipe(recipeEntry);
                }


            }
        } else {
            blockEntity.cookTime = 0;
            world.setBlockState(pos, state.with(LIT, false));
            isCrafting = false;
        }
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new ArcFurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return BOTTOM_SLOTS;
        } else {
            return SIDE_OR_TOP_SLOTS;
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return dir != Direction.DOWN;
    }
    public boolean isValid(int slot, ItemStack stack) {
        return stack == (ItemStack)this.inventory.get(0);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return dir == Direction.DOWN;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {
        Iterator var2 = this.inventory.iterator();

        while(var2.hasNext()) {
            ItemStack itemStack = (ItemStack)var2.next();
            finder.addInput(itemStack);
        }
    }

    @Override
    public void setLastRecipe(@Nullable RecipeEntry<?> recipe) {
        if (recipe != null) {
            Identifier identifier = recipe.id();
            this.recipesUsed.addTo(identifier, 1);
        }
    }

    public void unlockLastRecipe(PlayerEntity player, List<ItemStack> ingredients) {
    }

    public void dropExperienceForRecipesUsed(ServerPlayerEntity player) {
        List<RecipeEntry<?>> list = this.getRecipesUsedAndDropExperience(player.getServerWorld(), player.getPos());
        player.unlockRecipes(list);
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            RecipeEntry<?> recipeEntry = (RecipeEntry)var3.next();
            if (recipeEntry != null) {
                player.onRecipeCrafted(recipeEntry, this.inventory);
            }
        }

        this.recipesUsed.clear();
    }

    public List<RecipeEntry<?>> getRecipesUsedAndDropExperience(ServerWorld world, Vec3d pos) {
        List<RecipeEntry<?>> list = Lists.newArrayList();
        ObjectIterator var4 = this.recipesUsed.object2IntEntrySet().iterator();

        while(var4.hasNext()) {
            Object2IntMap.Entry<Identifier> entry = (Object2IntMap.Entry)var4.next();
            world.getRecipeManager().get((Identifier)entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                dropExperience(world, pos, entry.getIntValue(), ((BlastingRecipe)recipe.value()).getExperience());
            });
        }

        return list;
    }

    private static boolean canAcceptRecipeOutput(DynamicRegistryManager registryManager, @Nullable RecipeEntry<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (!((ItemStack)slots.get(0)).isEmpty() && recipe != null) {
            ItemStack itemStack = recipe.value().getResult(registryManager);
            if (itemStack.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack2 = (ItemStack)slots.get(1);
                if (itemStack2.isEmpty()) {
                    return true;
                } else if (!ItemStack.areItemsAndComponentsEqual(itemStack2, itemStack)) {
                    return false;
                } else if (itemStack2.getCount() < count && itemStack2.getCount() < itemStack2.getMaxCount()) {
                    return true;
                } else {
                    return itemStack2.getCount() < itemStack.getMaxCount();
                }
            }
        } else {
            return false;
        }
    }

    private static int getCookTime(World world, ArcFurnaceBlockEntity furnace) {
        SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(furnace.getStack(0));
        return (Integer)furnace.matchGetter.getFirstMatch(singleStackRecipeInput, world).map((recipe) -> {
            return ((AbstractCookingRecipe)recipe.value()).getCookingTime();
        }).orElse(200);
    }

    private static boolean craftRecipe(DynamicRegistryManager registryManager, @Nullable RecipeEntry<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (recipe != null && canAcceptRecipeOutput(registryManager, recipe, slots, count)) {
            ItemStack itemStack = (ItemStack)slots.get(0);
            ItemStack itemStack2 = recipe.value().getResult(registryManager);
            ItemStack itemStack3 = (ItemStack)slots.get(1);
            if (itemStack3.isEmpty()) {
                slots.set(1, itemStack2.copy());
            } else if (ItemStack.areItemsAndComponentsEqual(itemStack3, itemStack2)) {
                itemStack3.increment(1);
            }

            itemStack.decrement(0);
            return true;
        } else {
            return false;
        }
    }

    private static void dropExperience(ServerWorld world, Vec3d pos, int multiplier, float experience) {
        int i = MathHelper.floor((float)multiplier * experience);
        float f = MathHelper.fractionalPart((float)multiplier * experience);
        if (f != 0.0F && Math.random() < (double)f) {
            ++i;
        }

        ExperienceOrbEntity.spawn(world, pos, i);
    }

    @Override
    public @Nullable RecipeEntry<?> getLastRecipe() {
        return null;
    }
}
