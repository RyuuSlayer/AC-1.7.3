package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockTriggeredDoor extends Tile implements AcTriggerTile, AcRenderConditionTile {

    protected BlockTriggeredDoor(int i) {
        super(i, Material.WOOD);
        this.tex = 208;
        this.hardness(5.0f);
        this.sounds(Tile.WOOD_SOUNDS);
        ((ExTile) this).setTextureNum(3);
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active || ((ExLevel) AccessMinecraft.getInstance().level).getTriggerManager().isActivated(i, j, k);
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        if (!((ExLevel) level).getTriggerManager().isActivated(x, y, z) || DebugMode.active) {
            return null;
        }
        return super.getCollisionShape(level, x, y, z);
    }

    @Override
    public boolean canBeTriggered() {
        return true;
    }

    @Override
    public void onTriggerActivated(Level level, int i, int j, int k) {
        level.playSound((double) i + 0.5, (double) j + 0.5, (double) k + 0.5, "random.door_open", 1.0f, level.rand.nextFloat() * 0.1f + 0.9f);
        level.method_243(i, j, k);
    }

    @Override
    public void onTriggerDeactivated(Level level, int i, int j, int k) {
        level.playSound((double) i + 0.5, (double) j + 0.5, (double) k + 0.5, "random.door_close", 1.0f, level.rand.nextFloat() * 0.1f + 0.9f);
        level.method_243(i, j, k);
    }
}
