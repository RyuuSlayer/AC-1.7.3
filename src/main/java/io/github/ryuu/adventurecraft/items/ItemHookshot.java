package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemHookshot extends ItemType {

    public EntityHookshot mainHookshot = null;

    public EntityHookshot offHookshot = null;

    ItemInstance mainActiveHookshot = null;

    ItemInstance offActiveHookshot = null;

    Player player = null;

    public ItemHookshot(int id) {
        super(id);
        this.setTexturePosition(3, 10);
        this.maxStackSize = 1;
    }

    @Override
    public boolean shouldRotate180() {
        return true;
    }

    @Override
    public int getTexturePosition(int damage) {
        if (damage == 1) {
            return this.texturePosition + 1;
        }
        return this.texturePosition;
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, Player player) {
        EntityHookshot other;
        EntityHookshot hookshot;
        boolean main = true;
        if (!player.swappedItems) {
            hookshot = this.mainHookshot;
            other = this.offHookshot;
        } else {
            hookshot = this.offHookshot;
            other = this.mainHookshot;
            main = false;
        }
        if ((hookshot == null || hookshot.removed) && (other != null && other.attachedToSurface || player.onGround || player.method_1335() || player.method_1393())) {
            hookshot = new EntityHookshot(level, player, main, item);
            level.spawnEntity(hookshot);
            player.swingHand();
            if (main) {
                this.mainActiveHookshot = item;
                this.mainActiveHookshot.setDamage(1);
            } else {
                this.offActiveHookshot = item;
                this.offActiveHookshot.setDamage(1);
            }
            this.player = player;
        } else {
            this.releaseHookshot(hookshot);
        }
        if (main) {
            this.mainHookshot = hookshot;
        } else {
            this.offHookshot = hookshot;
        }
        return item;
    }

    public void releaseHookshot(EntityHookshot hookshot) {
        if (hookshot == null) {
            return;
        }
        hookshot.turningAround = true;
        hookshot.attachedToSurface = false;
        hookshot.entityGrabbed = null;
        if (hookshot == this.mainHookshot && this.mainActiveHookshot != null) {
            this.mainActiveHookshot.setDamage(0);
            this.mainActiveHookshot = null;
        } else if (this.offActiveHookshot != null) {
            this.offActiveHookshot.setDamage(0);
            this.offActiveHookshot = null;
        }
    }
}
