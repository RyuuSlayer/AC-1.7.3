/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.lwjgl.opengl.GL11
 */
package io.github.ryuu.adventurecraft.mixin.client.render.entity.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderHelper;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.client.render.entity.tile.PistonRenderer;
import net.minecraft.client.render.entity.tile.TileEntityRenderer;
import net.minecraft.level.Level;
import net.minecraft.tile.PistonTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.Piston;
import net.minecraft.tile.entity.TileEntity;
import org.lwjgl.opengl.GL11;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinTile;
import io.github.ryuu.adventurecraft.mixin.item.MixinTileEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinTileRenderer;

@Mixin(PistonRenderer.class)
public class MixinPistonRenderer extends TileEntityRenderer {

    @Shadow()
    private MixinTileRenderer field_1131;

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_973(Piston tileentitypiston, double d, double d1, double d2, float f) {
        MixinTile block = Tile.BY_ID[tileentitypiston.method_1518()];
        if (block != null && tileentitypiston.method_1519(f) < 1.0f) {
            Tessellator tessellator = Tessellator.INSTANCE;
            int textureNum = block.getTextureNum();
            String textureName = textureNum == 0 ? "/terrain.png" : String.format((String) "/terrain%d.png", (Object[]) new Object[] { textureNum });
            this.bindTexture(textureName);
            RenderHelper.disableLighting();
            GL11.glBlendFunc((int) 770, (int) 771);
            GL11.glEnable((int) 3042);
            GL11.glDisable((int) 2884);
            if (Minecraft.isSmoothLightingEnabled()) {
                GL11.glShadeModel((int) 7425);
            } else {
                GL11.glShadeModel((int) 7424);
            }
            tessellator.start();
            tessellator.prevPos((float) d - (float) tileentitypiston.x + tileentitypiston.method_1524(f), (float) d1 - (float) tileentitypiston.y + tileentitypiston.method_1525(f), (float) d2 - (float) tileentitypiston.z + tileentitypiston.method_1526(f));
            tessellator.colour(1, 1, 1);
            if (block == Tile.PISTON_HEAD && tileentitypiston.method_1519(f) < 0.5f) {
                this.field_1131.method_52(block, tileentitypiston.x, tileentitypiston.y, tileentitypiston.z, false);
            } else if (tileentitypiston.method_1527() && !tileentitypiston.method_1521()) {
                Tile.PISTON_HEAD.method_729(((PistonTile) block).method_767());
                this.field_1131.method_52(Tile.PISTON_HEAD, tileentitypiston.x, tileentitypiston.y, tileentitypiston.z, tileentitypiston.method_1519(f) < 0.5f);
                Tile.PISTON_HEAD.method_728();
                tessellator.prevPos((float) d - (float) tileentitypiston.x, (float) d1 - (float) tileentitypiston.y, (float) d2 - (float) tileentitypiston.z);
                this.field_1131.method_66(block, tileentitypiston.x, tileentitypiston.y, tileentitypiston.z);
            } else {
                this.field_1131.method_49(block, tileentitypiston.x, tileentitypiston.y, tileentitypiston.z);
            }
            tessellator.prevPos(0.0, 0.0, 0.0);
            tessellator.draw();
            RenderHelper.enableLighting();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void refreshLevel(MixinLevel world) {
        this.field_1131 = new MixinTileRenderer(world);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void render(MixinTileEntity entity, double x, double y, double z, float f) {
        this.method_973((Piston) entity, x, y, z, f);
    }
}
