package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockWeather extends TileWithEntity {

    protected BlockWeather(int i, int j) {
        super(i, j, Material.AIR);
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
            world.properties.setRaining(obj.precipitate);
            world.resetCoordOrder();
        }
        if (obj.changeTempOffset) {
            world.properties.tempOffset = obj.tempOffset;
            world.resetCoordOrder();
        }
        if (obj.changeTimeOfDay) {
            world.setTimeOfDay(obj.timeOfDay);
        }
        if (obj.changeTimeRate) {
            world.properties.setTimeRate(obj.timeRate);
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
