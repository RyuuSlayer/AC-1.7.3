package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.ChunkSubData;

public class class_520 extends ChunkSubData {
    private int c;

    private int d;

    private int e;

    private float f;

    private float g;

    private float h;

    private int i;

    public class_520(Minecraft minecraft) {
        super(minecraft);
        this.c = -1;
        this.d = -1;
        this.e = -1;
        this.f = 0.0F;
        this.g = 0.0F;
        this.h = 0.0F;
        this.i = 0;
    }

    public void a(Player entityplayer) {
        entityplayer.aS = -180.0F;
    }

    public boolean b(int i, int j, int k, int side) {
        this.a.f.undoStack.startRecording();
        boolean flag = false;
        for (int x = -this.destroyExtraWidth; x <= this.destroyExtraWidth; x++) {
            for (int y = -this.destroyExtraWidth; y <= this.destroyExtraWidth; y++) {
                for (int z = 0; z <= this.destroyExtraDepth; z++) {
                    if (side == 0) {
                        flag |= _sendBlockRemoved(i + x, j + z, k + y, side);
                    } else if (side == 1) {
                        flag |= _sendBlockRemoved(i + x, j - z, k + y, side);
                    } else if (side == 2) {
                        flag |= _sendBlockRemoved(i + x, j + y, k + z, side);
                    } else if (side == 3) {
                        flag |= _sendBlockRemoved(i + x, j + y, k - z, side);
                    } else if (side == 4) {
                        flag |= _sendBlockRemoved(i + z, j + y, k + x, side);
                    } else if (side == 5) {
                        flag |= _sendBlockRemoved(i - z, j + y, k + x, side);
                    }
                }
            }
        }
        this.a.f.undoStack.stopRecording();
        return flag;
    }

    private boolean _sendBlockRemoved(int i, int j, int k, int l) {
        int i1 = this.a.f.a(i, j, k);
        if (i1 == 0)
            return false;
        int j1 = this.a.f.e(i, j, k);
        boolean flag = super.b(i, j, k, l);
        ItemInstance itemstack = this.a.h.G();
        boolean flag1 = this.a.h.b(Tile.m[i1]);
        if (itemstack != null) {
            itemstack.a(i1, i, j, k, (Player) this.a.h);
            if (itemstack.a == 0) {
                itemstack.a((Player) this.a.h);
                this.a.h.H();
            }
        }
        if (flag && flag1)
            Tile.m[i1].a(this.a.f, (Player) this.a.h, i, j, k, j1);
        return flag;
    }

    public void a(int i, int j, int k, int l) {
        this.a.f.a((Player) this.a.h, i, j, k, l);
        int i1 = this.a.f.a(i, j, k);
        if (i1 > 0 && this.f == 0.0F)
            Tile.m[i1].b(this.a.f, i, j, k, (Player) this.a.h);
        if (DebugMode.active && i1 > 0 && Tile.m[i1].a((Player) this.a.h) >= 1.0F)
            b(i, j, k, l);
    }

    public void a() {
        this.f = 0.0F;
        this.i = 0;
    }

    public void c(int i, int j, int k, int l) {
        if (!DebugMode.active)
            return;
        if (this.i > 0) {
            this.i--;
            return;
        }
        if (i == this.c && j == this.d && k == this.e) {
            int i1 = this.a.f.a(i, j, k);
            if (i1 == 0)
                return;
            Tile block = Tile.m[i1];
            this.f += block.a((Player) this.a.h);
            if (this.h % 4.0F == 0.0F && block != null)
                this.a.B.b(block.by.d(), i + 0.5F, j + 0.5F, k + 0.5F, (block.by.b() + 1.0F) / 8.0F, block.by.c() * 0.5F);
            this.h++;
            if (this.f >= 1.0F) {
                b(i, j, k, l);
                this.f = 0.0F;
                this.g = 0.0F;
                this.h = 0.0F;
                this.i = 5;
            }
        } else {
            this.f = 0.0F;
            this.g = 0.0F;
            this.h = 0.0F;
            this.c = i;
            this.d = j;
            this.e = k;
        }
    }

    public void a(float f) {
        if (this.f <= 0.0F) {
            this.a.v.b = 0.0F;
            this.a.g.i = 0.0F;
        } else {
            float f1 = this.g + (this.f - this.g) * f;
            this.a.v.b = f1;
            this.a.g.i = f1;
        }
    }

    public float b() {
        if (this.a.h.G() != null && (this.a.h.G()).c == Items.quill.bf)
            return 500.0F;
        if (DebugMode.active)
            return DebugMode.reachDistance;
        return 4.0F;
    }

    public void a(Level world) {
        super.a(world);
    }

    public void c() {
        this.g = this.f;
        this.a.B.c();
    }
}
