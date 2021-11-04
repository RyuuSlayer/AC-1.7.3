package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.tile.TileEntityRenderer;
import net.minecraft.level.Level;
import org.lwjgl.opengl.GL11;

public class PistonRenderer extends TileEntityRenderer {
    private cv b;

    public void a(uk tileentitypiston, double d, double d1, double d2, float f) {
        Tile block = Tile.m[tileentitypiston.a()];
        if (block != null && tileentitypiston.a(f) < 1.0F) {
            String textureName;
            Tessellator tessellator = Tessellator.a;
            int textureNum = block.getTextureNum();
            if (textureNum == 0) {
                textureName = "/terrain.png";
            } else {
                textureName = String.format("/terrain%d.png", Integer.valueOf(textureNum));
            }
            a(textureName);
            u.a();
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glDisable(2884);
            if (Minecraft.v()) {
                GL11.glShadeModel(7425);
            } else {
                GL11.glShadeModel(7424);
            }
            tessellator.b();
            tessellator.b(((float) d - tileentitypiston.e + tileentitypiston.b(f)), ((float) d1 - tileentitypiston.f + tileentitypiston.c(f)), ((float) d2 - tileentitypiston.g + tileentitypiston.d(f)));
            tessellator.a(1, 1, 1);
            if (block == Tile.ab && tileentitypiston.a(f) < 0.5F) {
                this.b.a(block, tileentitypiston.e, tileentitypiston.f, tileentitypiston.g, false);
            } else if (tileentitypiston.k() && !tileentitypiston.b()) {
                Tile.ab.a_(((jq) block).i());
                this.b.a((Tile) Tile.ab, tileentitypiston.e, tileentitypiston.f, tileentitypiston.g, (tileentitypiston.a(f) < 0.5F));
                Tile.ab.a();
                tessellator.b(((float) d - tileentitypiston.e), ((float) d1 - tileentitypiston.f), ((float) d2 - tileentitypiston.g));
                this.b.d(block, tileentitypiston.e, tileentitypiston.f, tileentitypiston.g);
            } else {
                this.b.a(block, tileentitypiston.e, tileentitypiston.f, tileentitypiston.g);
            }
            tessellator.b(0.0D, 0.0D, 0.0D);
            tessellator.a();
            u.b();
        }
    }

    public void a(Level world) {
        this.b = new cv((xp) world);
    }

    public void a(TileEntity tileentity, double d, double d1, double d2, float f) {
        a((uk) tileentity, d, d1, d2, f);
    }
}
