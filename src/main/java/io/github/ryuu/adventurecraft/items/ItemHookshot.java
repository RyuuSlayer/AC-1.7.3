package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.entities.EntityHookshot;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemHookshot extends ItemType {
    public EntityHookshot mainHookshot;

    public EntityHookshot offHookshot;

    ItemInstance mainActiveHookshot;

    ItemInstance offActiveHookshot;

    Player player;

    public ItemHookshot(int itemIndex) {
        super(itemIndex);
        this.mainHookshot = null;
        this.offHookshot = null;
        this.mainActiveHookshot = null;
        this.offActiveHookshot = null;
        this.player = null;
        setTexturePosition(3, 10);
        this.maxStackSize = 1;
    }

    public boolean c() {
        return true;
    }

    @Override
    public int getTexturePosition(int i) {
        if (i == 1)
            return this.texturePosition + 1;
        return this.texturePosition;
    }

    @Override
    public ItemInstance use(ItemInstance itemstack, Level world, Player entityplayer) {
        EntityHookshot hookshot, other;
        boolean main = true;
        if (!entityplayer.swappedItems) {
            hookshot = this.mainHookshot;
            other = this.offHookshot;
        } else {
            hookshot = this.offHookshot;
            other = this.mainHookshot;
            main = false;
        }
        if ((hookshot == null || hookshot.removed) && ((other != null && other.attachedToSurface) || entityplayer.onGround || entityplayer.method_1335() || entityplayer.method_1393())) {
            hookshot = new EntityHookshot(world, entityplayer, main, itemstack);
            world.spawnEntity(hookshot);
            entityplayer.swingHand();
            if (main) {
                this.mainActiveHookshot = itemstack;
                this.mainActiveHookshot.setDamage(1);
            } else {
                this.offActiveHookshot = itemstack;
                this.offActiveHookshot.setDamage(1);
            }
            this.player = entityplayer;
        } else {
            releaseHookshot(hookshot);
        }
        if (main) {
            this.mainHookshot = hookshot;
        } else {
            this.offHookshot = hookshot;
        }
        return itemstack;
    }

    public void releaseHookshot(EntityHookshot hookshot) {
        if (hookshot == null)
            return;
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
