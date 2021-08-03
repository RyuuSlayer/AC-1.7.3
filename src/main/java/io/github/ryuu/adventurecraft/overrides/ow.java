package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.entities.tile.*;

import java.util.HashMap;
import java.util.Map;

public class ow {
    private static void a(Class<?> class1, String s) {
        if (b.containsKey(s))
            throw new IllegalArgumentException("Duplicate id: " + s);
        a.put(s, class1);
        b.put(class1, s);
    }

    public void a(nu nbttagcompound) {
        this.e = nbttagcompound.e("x");
        this.f = nbttagcompound.e("y");
        this.g = nbttagcompound.e("z");
    }

    public void b(nu nbttagcompound) {
        String s = (String) b.get(getClass());
        if (s == null)
            throw new RuntimeException(getClass() + " is missing a mapping! This is a bug!");
        nbttagcompound.a("id", s);
        nbttagcompound.a("x", this.e);
        nbttagcompound.a("y", this.f);
        nbttagcompound.a("z", this.g);
    }

    protected String getClassName() {
        return (String) b.get(getClass());
    }

    public void n_() {
    }

    public static ow c(nu nbttagcompound) {
        ow tileentity = null;
        try {
            Class<ow> class1 = (Class) a.get(nbttagcompound.i("id"));
            if (class1 != null)
                tileentity = class1.newInstance();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (tileentity != null) {
            tileentity.a(nbttagcompound);
        } else {
            System.out.println("Skipping TileEntity with id " + nbttagcompound.i("id"));
        }
        return tileentity;
    }

    public int e() {
        return this.d.e(this.e, this.f, this.g);
    }

    public void y_() {
        if (this.d != null)
            this.d.b(this.e, this.f, this.g, this);
    }

    public double a(double d, double d1, double d2) {
        double d3 = this.e + 0.5D - d;
        double d4 = this.f + 0.5D - d1;
        double d5 = this.g + 0.5D - d2;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public Tile f() {
        return Tile.m[this.d.a(this.e, this.f, this.g)];
    }

    public boolean g() {
        return this.h;
    }

    public void i() {
        this.h = true;
    }

    public void j() {
        this.h = false;
    }

    static Class _mthclass$(String s) {
        try {
            return Class.forName(s);
        } catch (ClassNotFoundException classnotfoundexception) {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private static final Map a = new HashMap<>();

    private static final Map b = new HashMap<>();

    public fd d;

    public int e;

    public int f;

    public int g;

    protected boolean h;

    public boolean killedFromSaving = false;

    static {
        a(sk.class, "Furnace");
        a(js.class, "Chest");
        a(eg.class, "RecordPlayer");
        a(Dispenser.class, "Trap");
        a(yk.class, "Sign");
        a(cy.class, "MobSpawner");
        a(tn.class, "Note");
        a(uk.class, "Piston");
        a(TileEntityMobSpawner.class, "MobSpawnerNew");
        a(TileEntityTrigger.class, "Trigger");
        a(TileEntityTriggerInverter.class, "TriggerInverter");
        a(TileEntityTriggerMemory.class, "TriggerMemory");
        a(TileEntityRedstoneTrigger.class, "RedstoneTrigger");
        a(TileEntityWeather.class, "Weather");
        a(TileEntityMusic.class, "Music");
        a(TileEntityTimer.class, "Timer");
        a(TileEntityMessage.class, "Message");
        a(TileEntityCamera.class, "Camera");
        a(TileEntityTriggerPushable.class, "TriggerPushable");
        a(TileEntityStorage.class, "Storage");
        a(TileEntityHealDamage.class, "HealDamage");
        a(TileEntityTeleport.class, "Teleport");
        a(TileEntityTree.class, "Tree");
        a(TileEntityScript.class, "Script");
        a(TileEntityStore.class, "Store");
        a(TileEntityEffect.class, "Effect");
        a(TileEntityUrl.class, "Url");
        a(TileEntityNpcPath.class, "NpcPath");
    }
}
