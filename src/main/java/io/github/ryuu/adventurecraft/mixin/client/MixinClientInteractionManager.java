package io.github.ryuu.adventurecraft.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ClientPlayer;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class MixinClientInteractionManager {
    protected final Minecraft minecraft;
    public boolean field_2105 = false;
    public int destroyExtraWidth = 0;
    public int destroyExtraDepth = 0;

    public MixinClientInteractionManager(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    public void method_1710(Level world) {
    }

    public void method_1707(int i, int j, int k, int l) {
        this.minecraft.level.method_172(this.minecraft.player, i, j, k, l);
        this.method_1716(i, j, k, l);
    }

    public boolean method_1716(int i, int j, int k, int l) {
        Level world = this.minecraft.level;
        Tile block = Tile.BY_ID[world.getTileId(i, j, k)];
        world.playLevelEvent(2001, i, j, k, block.id + world.getTileMeta(i, j, k) * 256);
        int i1 = world.getTileMeta(i, j, k);
        boolean flag = world.setTile(i, j, k, 0);
        if (block != null && flag) {
            block.method_1612(world, i, j, k, i1);
        }
        return flag;
    }

    public void method_1721(int i, int j, int k, int l) {
    }

    public void method_1705() {
    }

    public void method_1706(float f) {
    }

    public float method_1715() {
        return 5.0f;
    }

    public boolean useItem(Player player, Level level, ItemInstance item) {
        int i = item.count;
        ItemInstance itemstack1 = item.use(level, player);
        if (itemstack1 != item || itemstack1 != null && itemstack1.count != i) {
            player.inventory.main[player.inventory.selectedHotbarSlot] = itemstack1;
            if (itemstack1.count == 0) {
                player.inventory.main[player.inventory.selectedHotbarSlot] = null;
            }
            return true;
        }
        return false;
    }

    public void method_1711(Player entityplayer) {
    }

    public void tick() {
    }

    public boolean method_1722() {
        return true;
    }

    public void method_1718(Player player) {
    }

    public boolean activateTile(Player player, Level level, ItemInstance item, int x, int y, int z, int l) {
        int i1 = level.getTileId(x, y, z);
        if (i1 > 0 && Tile.BY_ID[i1].activate(level, x, y, z, player)) {
            return true;
        }
        if (item == null) {
            return false;
        }
        return item.useOnTile(player, level, x, y, z, l);
    }

    public Player createPlayer(Level level) {
        return new ClientPlayer(this.minecraft, level, this.minecraft.session, level.dimension.id);
    }

    public void interactWith(Player player, Entity target) {
        player.interactWith(target);
    }

    public void attack(Player player, Entity target) {
        player.attack(target);
    }

    public ItemInstance method_1708(int i, int j, int k, boolean flag, Player entityplayer) {
        return entityplayer.container.method_2078(j, k, flag, entityplayer);
    }

    public void closeContainer(int i, Player player) {
        player.container.onClosed(player);
        player.container = player.playerContainer;
    }
}