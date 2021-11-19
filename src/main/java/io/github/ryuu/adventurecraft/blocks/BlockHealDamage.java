package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockHealDamage extends TileWithEntity {

    protected BlockHealDamage(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected MixinTileEntity createTileEntity() {
        return new TileEntityHealDamage();
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
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
    public void onTriggerActivated(MixinLevel world, int i, int j, int k) {
        TileEntityHealDamage tileEnt = (TileEntityHealDamage) world.getTileEntity(i, j, k);
        for (Object obj : world.players) {
            MixinPlayer p = (MixinPlayer) obj;
            if (tileEnt.healDamage > 0) {
                p.addHealth(tileEnt.healDamage);
                continue;
            }
            p.applyDamage(-tileEnt.healDamage);
        }
    }

    @Override
    public void onTriggerDeactivated(MixinLevel world, int i, int j, int k) {
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        if (DebugMode.active && player.getHeldItem() != null && player.getHeldItem().itemId == Items.cursor.id) {
            TileEntityHealDamage obj = (TileEntityHealDamage) level.getTileEntity(x, y, z);
            GuiHealDamage.showUI(level, obj);
            return true;
        }
        return false;
    }
}
