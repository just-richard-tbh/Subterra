package com.subterra.client.screen;

import com.subterra.screen.AlloyFurnaceScreenHandler;
import com.subterra.screen.ArcFurnaceScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.texture.Sprite;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ArcFurnaceScreen extends HandledScreen<ArcFurnaceScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of("subterra", "textures/gui/container/arc_furnace.png");
    private final Identifier POWERED_TEXTURE = Identifier.of("subterra", "container/arc_redstone_indicator");
    private final Identifier BURN_PROGRESS_TEXTURE = Identifier.ofVanilla("container/blast_furnace/burn_progress");

    public ArcFurnaceScreen(ArcFurnaceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    public void handledScreenTick(){
        super.handledScreenTick();
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        super.render(drawContext, mouseX, mouseY, delta);
        drawMouseoverTooltip(drawContext, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext drawContext, float delta, int mouseX, int mouseY) {
        int i = this.x;
        int j = this.y;
        int l;
        drawContext.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        l = MathHelper.ceil(((ArcFurnaceScreenHandler)this.handler).getCookProgress() * 24.0F);
        drawContext.drawGuiTexture(this.BURN_PROGRESS_TEXTURE, 24, 16, 0, 0, i + 79, j + 34, l, 16);
        if (handler.isPowered()){
            drawContext.drawGuiTexture(this.POWERED_TEXTURE, i+59, j+55, 1, 9, 12);
        };
    }

    @Override
    protected void init() {
        super.init();
    }
}
