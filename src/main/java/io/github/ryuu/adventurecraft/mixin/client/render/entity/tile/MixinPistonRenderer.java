package io.github.ryuu.adventurecraft.mixin.client.render.entity.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderHelper;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.client.render.entity.tile.TileEntityRenderer;
import net.minecraft.level.Level;
import net.minecraft.tile.PistonTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.Piston;
import net.minecraft.tile.entity.TileEntity;
import org.lwjgl.opengl.GL11;

public class MixinPistonRenderer extends TileEntityRenderer {
    private TileRenderer field_1131;

    public void method_973(Piston tileentitypiston, double d, double d1, double d2, float f) {
        Tile block = Tile.BY_ID[tileentitypiston.method_1518()];
        if (block != null && tileentitypiston.method_1519(f) < 1.0f) {
            Tessellator tessellator = Tessellator.INSTANCE;
            int textureNum = block.getTextureNum();
            String textureName = textureNum == 0 ? "/terrain.png" : String.format("/terrain%d.png", new Object[]{textureNum});
            this.bindTexture(textureName);
            RenderHelper.disableLighting();
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glDisable(2884);
            if (Minecraft.isSmoothLightingEnabled()) {
                GL11.glShadeModel(7425);
            } else {
                GL11.glShadeModel(7424);
            }
            tessellator.start();
            tessellator.prevPos((float)d - (float)tileentitypiston.x + tileentitypiston.method_1524(f), (float)d1 - (float)tileentitypiston.y + tileentitypiston.method_1525(f), (float)d2 - (float)tileentitypiston.z + tileentitypiston.method_1526(f));
            tessellator.colour(1, 1, 1);
            if (block == Tile.PISTON_HEAD && tileentitypiston.method_1519(f) < 0.5f) {
                this.field_1131.method_52(block, tileentitypiston.x, tileentitypiston.y, tileentitypiston.z, false);
            } else if (tileentitypiston.method_1527() && !tileentitypiston.method_1521()) {
                Tile.PISTON_HEAD.method_729(((PistonTile)block).method_767());
                this.field_1131.method_52(Tile.PISTON_HEAD, tileentitypiston.x, tileentitypiston.y, tileentitypiston.z, tileentitypiston.method_1519(f) < 0.5f);
                Tile.PISTON_HEAD.method_728();
                tessellator.prevPos((float)d - (float)tileentitypiston.x, (float)d1 - (float)tileentitypiston.y, (float)d2 - (float)tileentitypiston.z);
                this.field_1131.method_66(block, tileentitypiston.x, tileentitypiston.y, tileentitypiston.z);
            } else {
                this.field_1131.method_49(block, tileentitypiston.x, tileentitypiston.y, tileentitypiston.z);
            }
            tessellator.prevPos(0.0, 0.0, 0.0);
            tessellator.draw();
            RenderHelper.enableLighting();
        }
    }

    public void refreshLevel(Level world) {
        this.field_1131 = new TileRenderer(world);
    }

    public void render(TileEntity entity, double x, double y, double z, float f) {
        this.method_973((Piston)entity, x, y, z, f);
    }
}