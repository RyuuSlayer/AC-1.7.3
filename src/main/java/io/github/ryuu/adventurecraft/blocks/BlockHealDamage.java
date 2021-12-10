package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityHealDamage;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.gui.GuiHealDamage;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.mixin.entity.AccessLivingEntity;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockHealDamage extends TileWithEntity implements AcTriggerTile, AcRenderConditionTile {

    protected BlockHealDamage(int i, int j) {
        super(i, j, Material.AIR);
        this.hardness(5.0f);
        this.sounds(Tile.METAL_SOUNDS);
        ((ExTile) this).setTextureNum(2);
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
        TileEntityHealDamage tileEnt = (TileEntityHealDamage) world.getTileEntity(i, j, k);
        for (Object obj : world.players) {
            Player p = (Player) obj;
            if (tileEnt.healDamage > 0) {
                p.addHealth(tileEnt.healDamage);
                continue;
            }
            ((AccessLivingEntity) p).invokeApplyDamage(-tileEnt.healDamage);
        }
    }

    @Override
    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active && player.getHeldItem() != null && player.getHeldItem().itemId == Items.cursor.id) {
            TileEntityHealDamage obj = (TileEntityHealDamage) level.getTileEntity(x, y, z);
            GuiHealDamage.showUI(level, obj);
            return true;
        }
        return false;
    }
}
