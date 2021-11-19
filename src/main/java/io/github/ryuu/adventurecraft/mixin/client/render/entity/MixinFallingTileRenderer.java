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
package io.github.ryuu.adventurecraft.mixin.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FallingTileRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingTile;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import io.github.ryuu.adventurecraft.mixin.item.MixinTile;
import io.github.ryuu.adventurecraft.mixin.item.MixinFallingTile;
import io.github.ryuu.adventurecraft.mixin.item.MixinEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinTileRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FallingTileRenderer.class)
public class MixinFallingTileRenderer extends EntityRenderer {

    @Shadow()
    private MixinTileRenderer field_857 = new MixinTileRenderer();

    public MixinFallingTileRenderer() {
        this.field_2678 = 0.5f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_770(MixinFallingTile entityfallingsand, double d, double d1, double d2, float f, float f1) {
        int x = MathsHelper.floor(entityfallingsand.x);
        int y = MathsHelper.floor(entityfallingsand.y);
        int z = MathsHelper.floor(entityfallingsand.z);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) ((float) d), (float) ((float) d1), (float) ((float) d2));
        MixinTile block = Tile.BY_ID[entityfallingsand.tile];
        if (block.getTextureNum() == 0) {
            this.bindTexture("/terrain.png");
        } else {
            this.bindTexture(String.format((String) "/terrain%d.png", (Object[]) new Object[] { block.getTextureNum() }));
        }
        MixinLevel world = entityfallingsand.getFallingLevel();
        GL11.glDisable((int) 2896);
        int b = world.getTileId(x, y, z);
        int m = world.getTileMeta(x, y, z);
        world.setBlockAndMetadataTemp(x, y, z, entityfallingsand.tile, entityfallingsand.metadata);
        this.field_857.method_53(block, world, x, y, z);
        world.setBlockAndMetadataTemp(x, y, z, b, m);
        GL11.glEnable((int) 2896);
        GL11.glPopMatrix();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void render(MixinEntity entity, double x, double y, double z, float f, float f1) {
        this.method_770((MixinFallingTile) entity, x, y, z, f, f1);
    }
}
