package com.subterra.block;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static net.minecraft.block.Blocks.createLightLevelFromLitBlockState;

public class ModBlocks {
    private static final float CRYSLATE_BRICK_STRENGTH = 4.5F;

    public static final AlloyFurnaceBlock ALLOY_FURNACE_BLOCK = new AlloyFurnaceBlock(AbstractBlock.Settings.create().
            mapColor(MapColor.DEEPSLATE_GRAY).
            requiresTool().
            strength(3.5F).
            luminance(createLightLevelFromLitBlockState(13)).
            sounds(BlockSoundGroup.DEEPSLATE_TILES)
    );
    public static final Block CRYSLATE_BLOCK = new PillarBlock(AbstractBlock.Settings.create().
            mapColor(MapColor.DARK_DULL_PINK).
            requiresTool().
            strength(4.0F).
            sounds(BlockSoundGroup.DEEPSLATE)
    );
    public static final Block COBBLED_CRYSLATE = new Block(AbstractBlock.Settings.create().
            mapColor(MapColor.DARK_DULL_PINK).
            requiresTool().
            strength(CRYSLATE_BRICK_STRENGTH).
            sounds(BlockSoundGroup.DEEPSLATE)
    );
    public static final Block COBBLED_CRYSLATE_STAIRS = new StairsBlock(ModBlocks.COBBLED_CRYSLATE.getDefaultState(),
            AbstractBlock.Settings.create().
            requiresTool().
            strength(CRYSLATE_BRICK_STRENGTH).
            sounds(BlockSoundGroup.DEEPSLATE)
    );

    public static final Block COBBLED_CRYSLATE_SLAB = new SlabBlock(AbstractBlock.Settings.create().
            mapColor(MapColor.DARK_DULL_PINK).
            requiresTool().
            strength(CRYSLATE_BRICK_STRENGTH).
            sounds(BlockSoundGroup.DEEPSLATE)
    );
    public static final Block CRYSLATE_BRICKS = new Block(AbstractBlock.Settings.create().
            mapColor(MapColor.DARK_DULL_PINK).
            requiresTool().
            strength(CRYSLATE_BRICK_STRENGTH).
            sounds(BlockSoundGroup.DEEPSLATE)
    );
    public static final Block CRYSLATE_TILES = new Block(AbstractBlock.Settings.create().
            mapColor(MapColor.DARK_DULL_PINK).
            requiresTool().
            strength(CRYSLATE_BRICK_STRENGTH).
            sounds(BlockSoundGroup.DEEPSLATE)
    );
    public static final Block CRACKED_CRYSLATE_BRICKS = new Block(AbstractBlock.Settings.create().
            mapColor(MapColor.DARK_DULL_PINK).
            requiresTool().
            strength(CRYSLATE_BRICK_STRENGTH).
            sounds(BlockSoundGroup.DEEPSLATE)
    );
    public static final Block CRACKED_CRYSLATE_TILES = new Block(AbstractBlock.Settings.create().
            mapColor(MapColor.DARK_DULL_PINK).
            requiresTool().
            strength(CRYSLATE_BRICK_STRENGTH).
            sounds(BlockSoundGroup.DEEPSLATE)
    );
    public static final Block CHISELED_CRYSLATE_BRICKS = new Block(AbstractBlock.Settings.create().
            mapColor(MapColor.DARK_DULL_PINK).
            requiresTool().
            strength(CRYSLATE_BRICK_STRENGTH).
            sounds(BlockSoundGroup.DEEPSLATE)
    );
    public static final Block CRACKED_CHISELED_CRYSLATE_BRICKS = new Block(AbstractBlock.Settings.create().
            mapColor(MapColor.DARK_DULL_PINK).
            requiresTool().
            strength(CRYSLATE_BRICK_STRENGTH).
            sounds(BlockSoundGroup.DEEPSLATE)
    );
    public static final Block CRYSLATE_PILLAR = new PillarBlock(AbstractBlock.Settings.create().
            mapColor(MapColor.DARK_DULL_PINK).
            requiresTool().
            strength(CRYSLATE_BRICK_STRENGTH).
            sounds(BlockSoundGroup.DEEPSLATE)
    );
    public static final Block CRACKED_CRYSLATE_PILLAR = new PillarBlock(AbstractBlock.Settings.create().
            mapColor(MapColor.DARK_DULL_PINK).
            requiresTool().
            strength(CRYSLATE_BRICK_STRENGTH).
            sounds(BlockSoundGroup.DEEPSLATE)
    );
    public static final Block MANTLE_BLOCK = new Block(AbstractBlock.Settings.create().
            mapColor(MapColor.TERRACOTTA_BROWN).
            requiresTool().
            strength(16.0F).
            resistance(100.0F).
            sounds(BlockSoundGroup.ANCIENT_DEBRIS)
    );
    public static final GrapeBushBlock GRAPE_BUSH = new GrapeBushBlock(AbstractBlock.Settings.create().
            mapColor(MapColor.LICHEN_GREEN).
            strength(0.5F).
            sounds(BlockSoundGroup.AZALEA).
            nonOpaque().
            noCollision()
    );

    private static void registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        Registry.register(Registries.BLOCK, Identifier.of("subterra", name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of("subterra", name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks(){
        registerBlock("alloy_furnace", ALLOY_FURNACE_BLOCK);
        registerBlock("cryslate", CRYSLATE_BLOCK);
        registerBlock("cobbled_cryslate", COBBLED_CRYSLATE);
        registerBlock("cobbled_cryslate_stairs", COBBLED_CRYSLATE_STAIRS);
        registerBlock("cobbled_cryslate_slab", COBBLED_CRYSLATE_SLAB);
        registerBlock("cryslate_tiles", CRYSLATE_TILES);
        registerBlock("cryslate_bricks", CRYSLATE_BRICKS);
        registerBlock("cracked_cryslate_tiles", CRACKED_CRYSLATE_TILES);
        registerBlock("cracked_cryslate_bricks", CRACKED_CRYSLATE_BRICKS);
        registerBlock("cryslate_pillar", CRYSLATE_PILLAR);
        registerBlock("cracked_cryslate_pillar", CRACKED_CRYSLATE_PILLAR);
        registerBlock("chiseled_cryslate_bricks", CHISELED_CRYSLATE_BRICKS);
        registerBlock("cracked_chiseled_cryslate_bricks", CRACKED_CHISELED_CRYSLATE_BRICKS);
        registerBlock("mantle_rock", MANTLE_BLOCK);
        registerBlock("grape_bush", GRAPE_BUSH);
    }
}
