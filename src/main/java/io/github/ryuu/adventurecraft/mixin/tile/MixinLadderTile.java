package io.github.ryuu.adventurecraft.mixin.tile;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.blocks.Blocks;

@Mixin(LadderTile.class)
public class MixinLadderTile extends Tile {

    protected MixinLadderTile(int id, int texUVStart) {
        super(id, texUVStart, Material.DOODADS);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Box getCollisionShape(Level level, int x, int y, int z) {
        int l = level.getTileMeta(x, y, z) % 4 + 2;
        float f = 0.125f;
        if (l == 2) {
            this.setBoundingBox(0.0f, 0.0f, 1.0f - f, 1.0f, 1.0f, 1.0f);
        }
        if (l == 3) {
            this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f);
        }
        if (l == 4) {
            this.setBoundingBox(1.0f - f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (l == 5) {
            this.setBoundingBox(0.0f, 0.0f, 0.0f, f, 1.0f, 1.0f);
        }
        return super.getCollisionShape(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Box getOutlineShape(Level level, int x, int y, int z) {
        int l = level.getTileMeta(x, y, z) % 4 + 2;
        float f = 0.125f;
        if (l == 2) {
            this.setBoundingBox(0.0f, 0.0f, 1.0f - f, 1.0f, 1.0f, 1.0f);
        }
        if (l == 3) {
            this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f);
        }
        if (l == 4) {
            this.setBoundingBox(1.0f - f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (l == 5) {
            this.setBoundingBox(0.0f, 0.0f, 0.0f, f, 1.0f, 1.0f);
        }
        return super.getOutlineShape(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean canPlaceAt(Level level, int x, int y, int z) {
        if (level.canSuffocate(x - 1, y, z)) {
            return true;
        }
        if (level.canSuffocate(x + 1, y, z)) {
            return true;
        }
        if (level.canSuffocate(x, y, z - 1)) {
            return true;
        }
        int bID = level.getTileId(x, y - 1, z);
        if (LadderTile.isLadderID(bID)) {
            return true;
        }
        return level.canSuffocate(x, y, z + 1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static boolean isLadderID(int bID) {
        return bID == Tile.LADDER.id || bID == Blocks.ladders1.id || bID == Blocks.ladders2.id || bID == Blocks.ladders3.id || bID == Blocks.ladders4.id;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onPlaced(Level level, int x, int y, int z, int facing) {
        int i1 = level.getTileMeta(x, y, z);
        if (i1 == 0 && LadderTile.isLadderID(level.getTileId(x, y - 1, z))) {
            i1 = level.getTileMeta(x, y - 1, z) % 4 + 2;
        }
        if (i1 == 0 && LadderTile.isLadderID(level.getTileId(x, y + 1, z))) {
            i1 = level.getTileMeta(x, y + 1, z) % 4 + 2;
        }
        if ((i1 == 0 || facing == 2) && level.isFullOpaque(x, y, z + 1)) {
            i1 = 2;
        }
        if ((i1 == 0 || facing == 3) && level.isFullOpaque(x, y, z - 1)) {
            i1 = 3;
        }
        if ((i1 == 0 || facing == 4) && level.isFullOpaque(x + 1, y, z)) {
            i1 = 4;
        }
        if ((i1 == 0 || facing == 5) && level.isFullOpaque(x - 1, y, z)) {
            i1 = 5;
        }
        level.setTileMeta(x, y, z, i1 - 2);
    }
}
