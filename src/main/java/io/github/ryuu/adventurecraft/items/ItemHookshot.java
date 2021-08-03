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
        a(3, 10);
        this.bg = 1;
    }

    public boolean c() {
        return true;
    }

    public int a(int i) {
        if (i == 1)
            return this.bh + 1;
        return this.bh;
    }

    public ItemInstance a(ItemInstance itemstack, Level world, Player entityplayer) {
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
        if ((hookshot == null || hookshot.be) && ((other != null && other.attachedToSurface) || entityplayer.aX || entityplayer.ah() || entityplayer.k_())) {
            hookshot = new EntityHookshot(world, entityplayer, main, itemstack);
            world.b(hookshot);
            entityplayer.J();
            if (main) {
                this.mainActiveHookshot = itemstack;
                this.mainActiveHookshot.b(1);
            } else {
                this.offActiveHookshot = itemstack;
                this.offActiveHookshot.b(1);
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
            this.mainActiveHookshot.b(0);
            this.mainActiveHookshot = null;
        } else if (this.offActiveHookshot != null) {
            this.offActiveHookshot.b(0);
            this.offActiveHookshot = null;
        }
    }
}
