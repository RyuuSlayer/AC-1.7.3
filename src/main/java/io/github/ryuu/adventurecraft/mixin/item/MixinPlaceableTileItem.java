package io.github.ryuu.adventurecraft.mixin.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlaceableTileItem.class)
public class MixinPlaceableTileItem extends ItemType {

    @Shadow()
    private int tileId;

    public MixinPlaceableTileItem(int id) {
        super(id);
        this.tileId = id + 256;
        this.setTexturePosition(Tile.BY_ID[id + 256].getTextureForSide(2));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        if (!DebugMode.active) {
            return false;
        }
        if (level.getTileId(x, y, z) == Tile.SNOW.id) {
            facing = 0;
        } else {
            if (facing == 0) {
                --y;
            }
            if (facing == 1) {
                ++y;
            }
            if (facing == 2) {
                --z;
            }
            if (facing == 3) {
                ++z;
            }
            if (facing == 4) {
                --x;
            }
            if (facing == 5) {
                ++x;
            }
        }
        if (item.count == 0) {
            return false;
        }
        if (y == 127 && Tile.BY_ID[this.tileId].material.isSolid()) {
            return false;
        }
        if (level.canPlaceTile(this.tileId, x, y, z, false, facing)) {
            Tile block = Tile.BY_ID[this.tileId];
            if (level.method_201(x, y, z, this.tileId, this.method_470(item.getDamage()))) {
                Tile.BY_ID[this.tileId].onPlaced(level, x, y, z, facing);
                Tile.BY_ID[this.tileId].afterPlaced(level, x, y, z, player);
                level.playSound((float) x + 0.5f, (float) y + 0.5f, (float) z + 0.5f, block.sounds.getWalkSound(), (block.sounds.getVolume() + 1.0f) / 2.0f, block.sounds.getPitch() * 0.8f);
                --item.count;
            }
            return true;
        }
        return false;
    }
}
