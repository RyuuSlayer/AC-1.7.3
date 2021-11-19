/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.Random
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin.tile;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.level.Level;
import net.minecraft.tile.CactusTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import io.github.ryuu.adventurecraft.mixin.item.MixinTile;
import io.github.ryuu.adventurecraft.mixin.item.MixinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CactusTile.class)
public class MixinCactusTile extends MixinTile {

    protected MixinCactusTile(int id, int j) {
        super(id, j, Material.CACTUS);
        this.setTicksRandomly(true);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onScheduledTick(MixinLevel level, int x, int y, int z, Random rand) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
        float f = 0.0625f;
        return Box.getOrCreate((float) x + f, y, (float) z + f, (float) (x + 1) - f, (float) (y + 1) - f, (float) (z + 1) - f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Box getOutlineShape(MixinLevel level, int x, int y, int z) {
        float f = 0.0625f;
        return Box.getOrCreate((float) x + f, y, (float) z + f, (float) (x + 1) - f, y + 1, (float) (z + 1) - f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTextureForSide(int side) {
        if (side == 1) {
            return this.tex - 1;
        }
        if (side == 0) {
            return this.tex + 1;
        }
        return this.tex;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean isFullCube() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean isFullOpaque() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int method_1621() {
        return 13;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean canPlaceAt(MixinLevel level, int x, int y, int z) {
        if (!super.canPlaceAt(level, x, y, z)) {
            return false;
        }
        return this.isValidPosition(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1609(MixinLevel level, int x, int y, int z, int id) {
        if (!this.isValidPosition(level, x, y, z)) {
            this.drop(level, x, y, z, level.getTileMeta(x, y, z));
            level.setTile(x, y, z, 0);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean isValidPosition(MixinLevel world, int i, int j, int k) {
        if (world.getMaterial(i - 1, j, k).isSolid()) {
            return false;
        }
        if (world.getMaterial(i + 1, j, k).isSolid()) {
            return false;
        }
        if (world.getMaterial(i, j, k - 1).isSolid()) {
            return false;
        }
        if (world.getMaterial(i, j, k + 1).isSolid()) {
            return false;
        }
        int l = world.getTileId(i, j - 1, k);
        return l == Tile.CACTUS.id || l == Tile.SAND.id;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onEntityCollision(MixinLevel world, int i, int j, int k, MixinEntity entity) {
        entity.damage(null, 1);
    }
}
