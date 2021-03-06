package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityWeather;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.gui.GuiWeather;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockWeather extends TileWithEntity implements AcTriggerTile, AcRenderConditionTile {

    protected BlockWeather(int i, int j) {
        super(i, j, Material.AIR);
        this.hardness(1.5f);
        this.blastResistance(10.0f);
        this.sounds(Tile.PISTON_SOUNDS);
        ((ExTile) this).setTextureNum(2);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityWeather();
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    @Override
    public boolean canBeTriggered() {
        return true;
    }

    @Override
    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityWeather obj = (TileEntityWeather) world.getTileEntity(i, j, k);
        if (obj.changePrecipitate) {
            world.getProperties().setRaining(obj.precipitate);
            ((ExLevel) world).resetCoordOrder();
        }
        if (obj.changeTempOffset) {
            ((ExLevelProperties) world.getProperties()).setTempOffset(obj.tempOffset);
            ((ExLevel) world).resetCoordOrder();
        }
        if (obj.changeTimeOfDay) {
            ((ExLevel) world).setTimeOfDay(obj.timeOfDay);
        }
        if (obj.changeTimeRate) {
            ((ExLevelProperties) world.getProperties()).setTimeRate(obj.timeRate);
        }
    }

    @Override
    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active && player.getHeldItem() != null && player.getHeldItem().itemId == Items.cursor.id) {
            TileEntityWeather obj = (TileEntityWeather) level.getTileEntity(x, y, z);
            GuiWeather.showUI(level, obj);
            return true;
        }
        return false;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }
}
