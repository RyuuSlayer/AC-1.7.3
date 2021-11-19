package io.github.ryuu.adventurecraft.mixin.client.render.entity;

import net.minecraft.client.render.TileRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FallingTileRenderer;
import net.minecraft.entity.FallingTile;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FallingTileRenderer.class)
public abstract class MixinFallingTileRenderer extends EntityRenderer {

    @Shadow
    private TileRenderer field_857;

    /**
     * @author TechPizza
     */
    @Overwrite
    public void render(FallingTile entityfallingsand, double d, double d1, double d2, float f, float f1) {
        int x = MathsHelper.floor(entityfallingsand.x);
        int y = MathsHelper.floor(entityfallingsand.y);
        int z = MathsHelper.floor(entityfallingsand.z);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        Tile block = Tile.BY_ID[entityfallingsand.tile];
        if (block.getTextureNum() == 0) {
            bindTexture("/terrain.png");
        } else {
            bindTexture(String.format("/terrain%d.png", block.getTextureNum()));
        }
        Level world = entityfallingsand.getFallingLevel();
        GL11.glDisable(2896);
        int b = world.getTileId(x, y, z);
        int m = world.getTileMeta(x, y, z);
        world.setBlockAndMetadataTemp(x, y, z, entityfallingsand.tile, entityfallingsand.metadata);
        this.field_857.method_53(block, world, x, y, z);
        world.setBlockAndMetadataTemp(x, y, z, b, m);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    }
}