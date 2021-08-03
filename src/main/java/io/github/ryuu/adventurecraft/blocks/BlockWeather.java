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

    protected TileEntity a_() {
        return new TileEntityWeather();
    }

    public boolean c() {
        return false;
    }

    public Box e(Level world, int i, int j, int k) {
        return null;
    }

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityWeather obj = (TileEntityWeather) world.b(i, j, k);
        if (obj.changePrecipitate) {
            world.x.b(obj.precipitate);
            world.resetCoordOrder();
        }
        if (obj.changeTempOffset) {
            world.x.tempOffset = obj.tempOffset;
            world.resetCoordOrder();
        }
        if (obj.changeTimeOfDay)
            world.setTimeOfDay(obj.timeOfDay);
        if (obj.changeTimeRate)
            world.x.setTimeRate(obj.timeRate);
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }

    public boolean a(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active && entityplayer.G() != null && (entityplayer.G()).c == Items.cursor.bf) {
            TileEntityWeather obj = (TileEntityWeather) world.b(i, j, k);
            GuiWeather.showUI(world, obj);
            return true;
        }
        return false;
    }

    public boolean v_() {
        return DebugMode.active;
    }
}
