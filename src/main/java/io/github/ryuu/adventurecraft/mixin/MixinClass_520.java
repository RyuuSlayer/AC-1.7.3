package io.github.ryuu.adventurecraft.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_520;
import net.minecraft.client.ClientInteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(class_520.class)
public class MixinClass_520 extends ClientInteractionManager {

    @Shadow()
    private int field_2181 = -1;

    private int field_2182 = -1;

    private int field_2183 = -1;

    private float field_2184 = 0.0f;

    private float field_2185 = 0.0f;

    private float field_2186 = 0.0f;

    private int field_2187 = 0;

    public MixinClass_520(Minecraft minecraft) {
        super(minecraft);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1711(Player entityplayer) {
        entityplayer.yaw = -180.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean method_1716(int i, int j, int k, int side) {
        this.minecraft.level.undoStack.startRecording();
        boolean flag = false;
        for (int x = -this.destroyExtraWidth; x <= this.destroyExtraWidth; ++x) {
            for (int y = -this.destroyExtraWidth; y <= this.destroyExtraWidth; ++y) {
                for (int z = 0; z <= this.destroyExtraDepth; ++z) {
                    if (side == 0) {
                        flag |= this._sendBlockRemoved(i + x, j + z, k + y, side);
                        continue;
                    }
                    if (side == 1) {
                        flag |= this._sendBlockRemoved(i + x, j - z, k + y, side);
                        continue;
                    }
                    if (side == 2) {
                        flag |= this._sendBlockRemoved(i + x, j + y, k + z, side);
                        continue;
                    }
                    if (side == 3) {
                        flag |= this._sendBlockRemoved(i + x, j + y, k - z, side);
                        continue;
                    }
                    if (side == 4) {
                        flag |= this._sendBlockRemoved(i + z, j + y, k + x, side);
                        continue;
                    }
                    if (side != 5)
                        continue;
                    flag |= this._sendBlockRemoved(i - z, j + y, k + x, side);
                }
            }
        }
        this.minecraft.level.undoStack.stopRecording();
        return flag;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private boolean _sendBlockRemoved(int i, int j, int k, int l) {
        int i1 = this.minecraft.level.getTileId(i, j, k);
        if (i1 == 0) {
            return false;
        }
        int j1 = this.minecraft.level.getTileMeta(i, j, k);
        boolean flag = super.method_1716(i, j, k, l);
        ItemInstance itemstack = this.minecraft.player.getHeldItem();
        boolean flag1 = this.minecraft.player.method_514(Tile.BY_ID[i1]);
        if (itemstack != null) {
            itemstack.postMine(i1, i, j, k, this.minecraft.player);
            if (itemstack.count == 0) {
                itemstack.method_700(this.minecraft.player);
                this.minecraft.player.method_503();
            }
        }
        if (flag && flag1) {
            Tile.BY_ID[i1].afterBreak(this.minecraft.level, this.minecraft.player, i, j, k, j1);
        }
        return flag;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1707(int i, int j, int k, int l) {
        this.minecraft.level.method_172(this.minecraft.player, i, j, k, l);
        int i1 = this.minecraft.level.getTileId(i, j, k);
        if (i1 > 0 && this.field_2184 == 0.0f) {
            Tile.BY_ID[i1].onPunched(this.minecraft.level, i, j, k, this.minecraft.player);
        }
        if (DebugMode.active && i1 > 0 && Tile.BY_ID[i1].method_1582(this.minecraft.player) >= 1.0f) {
            this.method_1716(i, j, k, l);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1721(int i, int j, int k, int l) {
        if (!DebugMode.active) {
            return;
        }
        if (this.field_2187 > 0) {
            --this.field_2187;
            return;
        }
        if (i == this.field_2181 && j == this.field_2182 && k == this.field_2183) {
            int i1 = this.minecraft.level.getTileId(i, j, k);
            if (i1 == 0) {
                return;
            }
            Tile block = Tile.BY_ID[i1];
            this.field_2184 += block.method_1582(this.minecraft.player);
            if (this.field_2186 % 4.0f == 0.0f && block != null) {
                this.minecraft.soundHelper.playSound(block.sounds.getWalkSound(), (float) i + 0.5f, (float) j + 0.5f, (float) k + 0.5f, (block.sounds.getVolume() + 1.0f) / 8.0f, block.sounds.getPitch() * 0.5f);
            }
            this.field_2186 += 1.0f;
            if (this.field_2184 >= 1.0f) {
                this.method_1716(i, j, k, l);
                this.field_2184 = 0.0f;
                this.field_2185 = 0.0f;
                this.field_2186 = 0.0f;
                this.field_2187 = 5;
            }
        } else {
            this.field_2184 = 0.0f;
            this.field_2185 = 0.0f;
            this.field_2186 = 0.0f;
            this.field_2181 = i;
            this.field_2182 = j;
            this.field_2183 = k;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1706(float f) {
        if (this.field_2184 <= 0.0f) {
            this.minecraft.overlay.field_2542 = 0.0f;
            this.minecraft.worldRenderer.field_1803 = 0.0f;
        } else {
            float f1;
            this.minecraft.overlay.field_2542 = f1 = this.field_2185 + (this.field_2184 - this.field_2185) * f;
            this.minecraft.worldRenderer.field_1803 = f1;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public float method_1715() {
        if (this.minecraft.player.getHeldItem() != null && this.minecraft.player.getHeldItem().itemId == Items.quill.id) {
            return 500.0f;
        }
        if (DebugMode.active) {
            return DebugMode.reachDistance;
        }
        return 4.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1710(Level world) {
        super.method_1710(world);
    }
}
