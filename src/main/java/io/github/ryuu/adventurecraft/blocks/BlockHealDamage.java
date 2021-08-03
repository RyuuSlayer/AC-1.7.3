package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityHealDamage;
import io.github.ryuu.adventurecraft.gui.GuiHealDamage;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockHealDamage extends TileWithEntity {
    protected BlockHealDamage(int i, int j) {
        super(i, j, Material.AIR);
    }

    protected TileEntity a_() {
        return new TileEntityHealDamage();
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
        TileEntityHealDamage tileEnt = (TileEntityHealDamage) world.b(i, j, k);
        for (Object obj : world.d) {
            Player p = (Player) obj;
            if (tileEnt.healDamage > 0) {
                p.c(tileEnt.healDamage);
                continue;
            }
            p.b(-tileEnt.healDamage);
        }
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }

    public boolean v_() {
        return DebugMode.active;
    }

    public boolean a(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active && entityplayer.G() != null && (entityplayer.G()).c == Items.cursor.bf) {
            TileEntityHealDamage obj = (TileEntityHealDamage) world.b(i, j, k);
            GuiHealDamage.showUI(world, obj);
            return true;
        }
        return false;
    }
}