package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityWeather;
import io.github.ryuu.adventurecraft.gui.GuiWeather;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
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
    public Box getCollisionShape(Level world, int i, int j, int k) {
        return null;
    }

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

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
        if (obj.changeTimeOfDay)
            world.setTimeOfDay(obj.timeOfDay);
        if (obj.changeTimeRate)
            world.properties.setTimeRate(obj.timeRate);
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }

    @Override
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active && entityplayer.getHeldItem() != null && (entityplayer.getHeldItem()).itemId == Items.cursor.id) {
            TileEntityWeather obj = (TileEntityWeather) world.getTileEntity(i, j, k);
            GuiWeather.showUI(world, obj);
            return true;
        }
        return false;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }
}
