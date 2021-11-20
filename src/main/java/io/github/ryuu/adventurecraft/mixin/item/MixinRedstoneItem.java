package io.github.ryuu.adventurecraft.mixin.item;

import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RedstoneItem.class)
public class MixinRedstoneItem extends ItemType {

    public MixinRedstoneItem(int id) {
        super(id);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        if (level.getTileId(x, y, z) != Tile.SNOW.id) {
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
            if (!level.isAir(x, y, z)) {
                return false;
            }
        }
        if (DebugMode.active && Tile.REDSTONE_DUST.canPlaceAt(level, x, y, z)) {
            --item.count;
            level.setTile(x, y, z, Tile.REDSTONE_DUST.id);
        }
        return true;
    }
}
