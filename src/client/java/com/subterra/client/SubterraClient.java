package com.subterra.client;


import com.subterra.block.ModBlocks;
import com.subterra.client.screen.AlloyFurnaceScreen;
import com.subterra.client.screen.SteelFurnaceScreen;
import com.subterra.client.screen.ArcFurnaceScreen;
import com.subterra.screen.ModScreenHandlers;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.RenderLayer;

public class SubterraClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HandledScreens.register(ModScreenHandlers.ALLOY_FURNACE_SCREEN_HANDLER, AlloyFurnaceScreen::new);
		HandledScreens.register(ModScreenHandlers.STEEL_FURNACE_SCREEN_HANDLER, SteelFurnaceScreen::new);
		HandledScreens.register(ModScreenHandlers.ARC_FURNACE_SCREEN_HANDLER, ArcFurnaceScreen::new);
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRAPE_BUSH, RenderLayer.getCutout());
	}
}
