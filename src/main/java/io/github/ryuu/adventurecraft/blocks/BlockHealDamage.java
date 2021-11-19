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

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityHealDamage();
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
        TileEntityHealDamage tileEnt = (TileEntityHealDamage) world.getTileEntity(i, j, k);
        for (Object obj : world.players) {
            Player p = (Player) obj;
            if (tileEnt.healDamage > 0) {
                p.addHealth(tileEnt.healDamage);
                continue;
            }
            p.addHealth(-tileEnt.healDamage);
        }
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active && entityplayer.getHeldItem() != null && (entityplayer.getHeldItem()).itemId == Items.cursor.id) {
            TileEntityHealDamage obj = (TileEntityHealDamage) world.getTileEntity(i, j, k);
            GuiHealDamage.showUI(world, obj);
            return true;
        }
        return false;
    }
}