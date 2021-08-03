package io.github.ryuu.adventurecraft.overrides;

public class qd extends gm {
    private final int a;

    public qd(int i, bu enumtoolmaterial) {
        super(i);
        this.bg = 1;
        e(enumtoolmaterial.a());
        this.a = 4 + enumtoolmaterial.c() * 2;
    }

    public float a(iz itemstack, Tile block) {
        return (block.bn != Tile.X.bn) ? 1.5F : 15.0F;
    }

    public boolean a(iz itemstack, ls entityliving, ls entityliving1) {
        return true;
    }

    public boolean a(iz itemstack, int i, int j, int k, int l, ls entityliving) {
        return true;
    }

    public int a(sn entity) {
        return this.a;
    }

    public boolean b() {
        return true;
    }

    public boolean a(Tile block) {
        return (block.bn == Tile.X.bn);
    }

    public boolean mainActionLeftClick() {
        return true;
    }
}
