package io.github.ryuu.adventurecraft.mixin.tile.entity;

import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityMobSpawner;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTrigger;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerInverter;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerMemory;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityRedstoneTrigger;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityWeather;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityMusic;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTimer;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityMessage;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityCamera;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerPushable;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityStorage;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityHealDamage;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTeleport;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTree;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityScript;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityStore;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityEffect;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityUrl;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;

@Mixin(TileEntity.class)
public class MixinTileEntity {

    @Shadow()
    private static Map ID_TO_CLASS = new HashMap();

    private static Map CLASS_TO_ID = new HashMap();

    public Level level;

    public int x;

    public int y;

    public int z;

    protected boolean invalid;

    public boolean killedFromSaving = false;

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private static void register(Class class1, String s) {
        if (CLASS_TO_ID.containsKey((Object) s)) {
            throw new IllegalArgumentException("Duplicate id: " + s);
        }
        ID_TO_CLASS.put((Object) s, (Object) class1);
        CLASS_TO_ID.put((Object) class1, (Object) s);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void readIdentifyingData(CompoundTag tag) {
        this.x = tag.getInt("x");
        this.y = tag.getInt("y");
        this.z = tag.getInt("z");
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void writeIdentifyingData(CompoundTag tag) {
        String s = (String) CLASS_TO_ID.get((Object) this.getClass());
        if (s == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        tag.put("id", s);
        tag.put("x", this.x);
        tag.put("y", this.y);
        tag.put("z", this.z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected String getClassName() {
        return (String) CLASS_TO_ID.get((Object) this.getClass());
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static TileEntity method_1068(CompoundTag nbttagcompound) {
        TileEntity tileentity = null;
        try {
            Class class1 = (Class) ID_TO_CLASS.get((Object) nbttagcompound.getString("id"));
            if (class1 != null) {
                tileentity = (TileEntity) class1.newInstance();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (tileentity != null) {
            tileentity.readIdentifyingData(nbttagcompound);
        } else {
            System.out.println("Skipping TileEntity with id " + nbttagcompound.getString("id"));
        }
        return tileentity;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public double squaredDistanceTo(double x, double y, double z) {
        double d3 = (double) this.x + 0.5 - x;
        double d4 = (double) this.y + 0.5 - y;
        double d5 = (double) this.z + 0.5 - z;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    static Class _mthclass$(String s) {
        try {
            return Class.forName((String) s);
        } catch (ClassNotFoundException classnotfoundexception) {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static {
        TileEntity.register(FurnaceEntity.class, "Furnace");
        TileEntity.register(Chest.class, "Chest");
        TileEntity.register(Jukebox.class, "RecordPlayer");
        TileEntity.register(Dispenser.class, "Trap");
        TileEntity.register(Sign.class, "Sign");
        TileEntity.register(MobSpawner.class, "MobSpawner");
        TileEntity.register(Noteblock.class, "Note");
        TileEntity.register(Piston.class, "Piston");
        TileEntity.register(TileEntityMobSpawner.class, "MobSpawnerNew");
        TileEntity.register(TileEntityTrigger.class, "Trigger");
        TileEntity.register(TileEntityTriggerInverter.class, "TriggerInverter");
        TileEntity.register(TileEntityTriggerMemory.class, "TriggerMemory");
        TileEntity.register(TileEntityRedstoneTrigger.class, "RedstoneTrigger");
        TileEntity.register(TileEntityWeather.class, "Weather");
        TileEntity.register(TileEntityMusic.class, "Music");
        TileEntity.register(TileEntityTimer.class, "Timer");
        TileEntity.register(TileEntityMessage.class, "Message");
        TileEntity.register(TileEntityCamera.class, "Camera");
        TileEntity.register(TileEntityTriggerPushable.class, "TriggerPushable");
        TileEntity.register(TileEntityStorage.class, "Storage");
        TileEntity.register(TileEntityHealDamage.class, "HealDamage");
        TileEntity.register(TileEntityTeleport.class, "Teleport");
        TileEntity.register(TileEntityTree.class, "Tree");
        TileEntity.register(TileEntityScript.class, "Script");
        TileEntity.register(TileEntityStore.class, "Store");
        TileEntity.register(TileEntityEffect.class, "Effect");
        TileEntity.register(TileEntityUrl.class, "Url");
        TileEntity.register(TileEntityNpcPath.class, "NpcPath");
    }
}
