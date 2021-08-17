package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.entities.tile.*;
import net.minecraft.entity.FurnaceEntity;
import net.minecraft.level.Level;
import net.minecraft.tile.entity.*;
import net.minecraft.util.io.CompoundTag;

import java.util.HashMap;
import java.util.Map;

public class TileEntity {
    private static void a(Class<?> class1, String s) {
        if (b.containsKey(s))
            throw new IllegalArgumentException("Duplicate id: " + s);
        a.put(s, class1);
        b.put(class1, s);
    }

    public void a(CompoundTag nbttagcompound) {
        this.e = nbttagcompound.getInt("x");
        this.f = nbttagcompound.getInt("y");
        this.g = nbttagcompound.getInt("z");
    }

    public void b(CompoundTag nbttagcompound) {
        String s = (String) b.get(getClass());
        if (s == null)
            throw new RuntimeException(getClass() + " is missing a mapping! This is a bug!");
        nbttagcompound.put("id", s);
        nbttagcompound.put("x", this.e);
        nbttagcompound.put("y", this.f);
        nbttagcompound.put("z", this.g);
    }

    protected String getClassName() {
        return (String) b.get(getClass());
    }

    public void n_() {
    }

    public static TileEntity c(CompoundTag nbttagcompound) {
        TileEntity tileentity = null;
        try {
            Class<TileEntity> class1 = (Class) a.get(nbttagcompound.i("id"));
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

    public Level d;

    public int e;

    public int f;

    public int g;

    protected boolean h;

    public boolean killedFromSaving = false;

    static {
        a(FurnaceEntity.class, "Furnace");
        a(Chest.class, "Chest");
        a(Jukebox.class, "RecordPlayer");
        a(Dispenser.class, "Trap");
        a(Sign.class, "Sign");
        a(MobSpawner.class, "MobSpawner");
        a(Noteblock.class, "Note");
        a(Piston.class, "Piston");
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