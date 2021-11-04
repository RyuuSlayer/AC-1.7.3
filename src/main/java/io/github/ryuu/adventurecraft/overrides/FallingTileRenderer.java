package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingTile;
import net.minecraft.level.Level;
import org.lwjgl.opengl.GL11;

public class FallingTileRenderer extends EntityRenderer {
    private final TileRenderer a = new TileRenderer);

    public void a(FallingTile entityfallingsand, double d, double d1, double d2, float f, float f1) {
        int x = MathsHelper.b(entityfallingsand.aM);
        int y = MathsHelper.b(entityfallingsand.aN);
        int z = MathsHelper.b(entityfallingsand.aO);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        Tile block = Tile.m[entityfallingsand.a];
        if (block.getTextureNum() == 0) {
            a("/terrain.png");
        } else {
            a(String.format("/terrain%d.png", new Object[]{Integer.valueOf(block.getTextureNum())}));
        }
        Level world = entityfallingsand.k();
        GL11.glDisable(2896);
        int b = world.a(x, y, z);
        int m = world.e(x, y, z);
        world.setBlockAndMetadataTemp(x, y, z, entityfallingsand.a, entityfallingsand.metadata);
        this.a.a(block, world, x, y, z);
        world.setBlockAndMetadataTemp(x, y, z, b, m);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    }

    public void a(Entity entity, double d, double d1, double d2, float f, float f1) {
        a((FallingTile) entity, d, d1, d2, f, f1);
    }
}
