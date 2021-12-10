package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.extensions.tile.ExLadderTile;
import net.minecraft.level.Level;
import net.minecraft.tile.LadderTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LadderTile.class)
public class MixinLadderTile extends Tile implements ExLadderTile {

    protected MixinLadderTile(int id, int texUVStart) {
        super(id, texUVStart, Material.DOODADS);
    }

    private static int repeatGetTileMeta(int tileMeta) {
        return tileMeta % 4 + 2;
    }

    @Redirect(method = "getCollisionShape", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/level/Level;getTileMeta(III)I"))
    private int fixGetCollisionShapeTileMeta(Level instance, int i, int j, int k) {
        return repeatGetTileMeta(instance.getTileMeta(i, j, k));
    }

    @Redirect(method = "getOutlineShape", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/level/Level;getTileMeta(III)I"))
    private int fixGetOutlineShapeTileMeta(Level instance, int i, int j, int k) {
        return repeatGetTileMeta(instance.getTileMeta(i, j, k));
    }

    @Inject(method = "canPlaceAt", at = @At(
            value = "RETURN",
            ordinal = 3),
            cancellable = true)
    private void ladderCheckToCanPlaceAt(Level level, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        int bID = level.getTileId(x, y - 1, z);
        if (ExLadderTile.isLadderID(bID)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public void onPlaced(Level level, int x, int y, int z, int facing) {
        int i1 = level.getTileMeta(x, y, z);
        if (i1 == 0 && ExLadderTile.isLadderID(level.getTileId(x, y - 1, z))) {
            i1 = repeatGetTileMeta(level.getTileMeta(x, y - 1, z));
        }
        if (i1 == 0 && ExLadderTile.isLadderID(level.getTileId(x, y + 1, z))) {
            i1 = repeatGetTileMeta(level.getTileMeta(x, y + 1, z));
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
