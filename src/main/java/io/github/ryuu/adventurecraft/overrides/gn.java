package io.github.ryuu.adventurecraft.overrides;

import org.lwjgl.opengl.GL11;

public class gn extends bw {
    private cv a = new cv();

    public void a(ju entityfallingsand, double d, double d1, double d2, float f, float f1) {
        int x = in.b(entityfallingsand.aM);
        int y = in.b(entityfallingsand.aN);
        int z = in.b(entityfallingsand.aO);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        Tile block = Tile.m[entityfallingsand.a];
        if (block.getTextureNum() == 0) {
            a("/terrain.png");
        } else {
            a(String.format("/terrain%d.png", new Object[] { Integer.valueOf(block.getTextureNum()) }));
        }
        fd world = entityfallingsand.k();
        GL11.glDisable(2896);
        int b = world.a(x, y, z);
        int m = world.e(x, y, z);
        world.setBlockAndMetadataTemp(x, y, z, entityfallingsand.a, entityfallingsand.metadata);
        this.a.a(block, world, x, y, z);
        world.setBlockAndMetadataTemp(x, y, z, b, m);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    }

    public void a(sn entity, double d, double d1, double d2, float f, float f1) {
        a((ju)entity, d, d1, d2, f, f1);
    }
}
