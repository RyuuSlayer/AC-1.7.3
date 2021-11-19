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
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.DoorTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinPlayer;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import io.github.ryuu.adventurecraft.mixin.item.MixinTile;

@Mixin(DoorTile.class)
public class MixinDoorTile extends MixinTile {

    protected MixinDoorTile(int id, Material material) {
        super(id, material);
        this.tex = 97;
        if (material == Material.METAL) {
            ++this.tex;
        }
        float f = 0.5f;
        float f1 = 1.0f;
        this.setBoundingBox(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f1, 0.5f + f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTextureForSide(int side, int meta) {
        if (side == 0 || side == 1) {
            return this.tex;
        }
        int k = this.method_839(meta);
        if ((k == 0 || k == 2) ^ side <= 3) {
            return this.tex;
        }
        int l = k / 2 + (side & 1 ^ k);
        int i1 = this.tex - (meta & 8) * 2;
        if (((l += (meta & 4) / 4) & 1) != 0) {
            i1 = -i1;
        }
        return i1;
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
    public boolean isFullCube() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int method_1621() {
        return 7;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Box getOutlineShape(MixinLevel level, int x, int y, int z) {
        this.method_1616(level, x, y, z);
        return super.getOutlineShape(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
        this.method_1616(level, x, y, z);
        return super.getCollisionShape(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1616(TileView iblockaccess, int i, int j, int k) {
        this.setBoundingBox(this.method_839(iblockaccess.getTileMeta(i, j, k)));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setBoundingBox(int meta) {
        float f = 0.1875f;
        this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        if (meta == 0) {
            this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f);
        }
        if (meta == 1) {
            this.setBoundingBox(1.0f - f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (meta == 2) {
            this.setBoundingBox(0.0f, 0.0f, 1.0f - f, 1.0f, 1.0f, 1.0f);
        }
        if (meta == 3) {
            this.setBoundingBox(0.0f, 0.0f, 0.0f, f, 1.0f, 1.0f);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onPunched(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        this.activate(level, x, y, z, player);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        if (this.material == Material.METAL) {
            return true;
        }
        int l = level.getTileMeta(x, y, z);
        if ((l & 8) != 0) {
            if (level.getTileId(x, y - 1, z) == this.id) {
                this.activate(level, x, y - 1, z, player);
            }
            return true;
        }
        if (level.getTileId(x, y + 1, z) == this.id) {
            level.setTileMeta(x, y + 1, z, (l ^ 4) + 8);
        }
        level.setTileMeta(x, y, z, l ^ 4);
        level.updateRedstone(x, y - 1, z, x, y, z);
        level.playLevelEvent(player, 1003, x, y, z, 0);
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_837(MixinLevel world, int i, int j, int k, boolean flag) {
        boolean flag1;
        int l = world.getTileMeta(i, j, k);
        if ((l & 8) != 0) {
            if (world.getTileId(i, j - 1, k) == this.id) {
                this.method_837(world, i, j - 1, k, flag);
            }
            return;
        }
        boolean bl = flag1 = (world.getTileMeta(i, j, k) & 4) > 0;
        if (flag1 == flag) {
            return;
        }
        if (world.getTileId(i, j + 1, k) == this.id) {
            world.setTileMeta(i, j + 1, k, (l ^ 4) + 8);
        }
        world.setTileMeta(i, j, k, l ^ 4);
        world.updateRedstone(i, j - 1, k, i, j, k);
        world.playLevelEvent(null, 1003, i, j, k, 0);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1609(MixinLevel level, int x, int y, int z, int id) {
        int i1 = level.getTileMeta(x, y, z);
        if ((i1 & 8) != 0) {
            if (level.getTileId(x, y - 1, z) != this.id) {
                level.setTile(x, y, z, 0);
            }
            if (id > 0 && Tile.BY_ID[id].emitsRedstonePower()) {
                this.method_1609(level, x, y - 1, z, id);
            }
        } else {
            boolean flag = false;
            if (level.getTileId(x, y + 1, z) != this.id) {
                level.setTile(x, y, z, 0);
                flag = true;
            }
            if (!level.canSuffocate(x, y - 1, z)) {
                level.setTile(x, y, z, 0);
                flag = true;
                if (level.getTileId(x, y + 1, z) == this.id) {
                    level.setTile(x, y + 1, z, 0);
                }
            }
            if (flag) {
                if (!level.isClient) {
                    this.drop(level, x, y, z, i1);
                }
            } else if (id > 0 && Tile.BY_ID[id].emitsRedstonePower()) {
                boolean flag1 = level.hasRedstonePower(x, y, z) || level.hasRedstonePower(x, y + 1, z);
                this.method_837(level, x, y, z, flag1);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getDropId(int meta, Random rand) {
        if ((meta & 8) != 0) {
            return 0;
        }
        if (this.material == Material.METAL) {
            return ItemType.doorIron.id;
        }
        return ItemType.doorWood.id;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public HitResult raycast(MixinLevel world, int x, int y, int z, Vec3f vec3d, Vec3f vec3d1) {
        this.method_1616(world, x, y, z);
        int m = world.getTileMeta(x, y, z);
        if (this.material == Material.METAL && (m & 8) == 8) {
            this.minY = 0.8125;
        }
        vec3d = vec3d.method_1301(-x, -y, -z);
        vec3d1 = vec3d1.method_1301(-x, -y, -z);
        Vec3f vec3d2 = vec3d.method_1295(vec3d1, this.minX);
        Vec3f vec3d3 = vec3d.method_1295(vec3d1, this.maxX);
        Vec3f vec3d4 = vec3d.method_1299(vec3d1, this.minY);
        Vec3f vec3d5 = vec3d.method_1299(vec3d1, this.maxY);
        Vec3f vec3d6 = vec3d.method_1302(vec3d1, this.minZ);
        Vec3f vec3d7 = vec3d.method_1302(vec3d1, this.maxZ);
        if (!this.method_1579(vec3d2)) {
            vec3d2 = null;
        }
        if (!this.method_1579(vec3d3)) {
            vec3d3 = null;
        }
        if (!this.method_1586(vec3d4)) {
            vec3d4 = null;
        }
        if (!this.method_1586(vec3d5)) {
            vec3d5 = null;
        }
        if (!this.method_1588(vec3d6)) {
            vec3d6 = null;
        }
        if (!this.method_1588(vec3d7)) {
            vec3d7 = null;
        }
        Vec3f vec3d8 = null;
        if (vec3d2 != null && (vec3d8 == null || vec3d.method_1294(vec3d2) < vec3d.method_1294(vec3d8))) {
            vec3d8 = vec3d2;
        }
        if (vec3d3 != null && (vec3d8 == null || vec3d.method_1294(vec3d3) < vec3d.method_1294(vec3d8))) {
            vec3d8 = vec3d3;
        }
        if (vec3d4 != null && (vec3d8 == null || vec3d.method_1294(vec3d4) < vec3d.method_1294(vec3d8))) {
            vec3d8 = vec3d4;
        }
        if (vec3d5 != null && (vec3d8 == null || vec3d.method_1294(vec3d5) < vec3d.method_1294(vec3d8))) {
            vec3d8 = vec3d5;
        }
        if (vec3d6 != null && (vec3d8 == null || vec3d.method_1294(vec3d6) < vec3d.method_1294(vec3d8))) {
            vec3d8 = vec3d6;
        }
        if (vec3d7 != null && (vec3d8 == null || vec3d.method_1294(vec3d7) < vec3d.method_1294(vec3d8))) {
            vec3d8 = vec3d7;
        }
        if (vec3d8 == null) {
            return null;
        }
        int byte0 = -1;
        if (vec3d8 == vec3d2) {
            byte0 = 4;
        }
        if (vec3d8 == vec3d3) {
            byte0 = 5;
        }
        if (vec3d8 == vec3d4) {
            byte0 = 0;
        }
        if (vec3d8 == vec3d5) {
            byte0 = 1;
        }
        if (vec3d8 == vec3d6) {
            byte0 = 2;
        }
        if (vec3d8 == vec3d7) {
            byte0 = 3;
        }
        return new HitResult(x, y, z, byte0, vec3d8.method_1301(x, y, z));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_839(int i) {
        if ((i & 4) == 0) {
            return i - 1 & 3;
        }
        return i & 3;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean canPlaceAt(MixinLevel level, int x, int y, int z) {
        if (y >= 127) {
            return false;
        }
        return level.canSuffocate(x, y - 1, z) && super.canPlaceAt(level, x, y, z) && super.canPlaceAt(level, x, y + 1, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static boolean method_840(int i) {
        return (i & 4) != 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getPistonPushMode() {
        return 1;
    }
}
