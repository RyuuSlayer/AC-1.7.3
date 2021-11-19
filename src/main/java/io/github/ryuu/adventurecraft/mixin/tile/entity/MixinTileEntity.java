package io.github.ryuu.adventurecraft.mixin.tile.entity;

import net.minecraft.entity.FurnaceEntity;
import net.minecraft.packet.AbstractPacket;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;

@Mixin(TileEntity.class)
public class MixinTileEntity {

    private static final Map CLASS_TO_ID = new HashMap();
    @Shadow()
    private static Map ID_TO_CLASS = new HashMap();

    static {
        TileEntity.register(FurnaceEntity.class, "Furnace");
        TileEntity.register(Chest.class, "Chest");
        TileEntity.register(Jukebox.class, "RecordPlayer");
        TileEntity.register(MixinDispenser.class, "Trap");
        TileEntity.register(MixinSign.class, "Sign");
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

    public MixinLevel level;
    public int x;
    public int y;
    public int z;
    public boolean killedFromSaving = false;
    protected boolean invalid;

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private static void register(Class class1, String s) {
        if (CLASS_TO_ID.containsKey(s)) {
            throw new IllegalArgumentException("Duplicate id: " + s);
        }
        ID_TO_CLASS.put(s, class1);
        CLASS_TO_ID.put(class1, s);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static MixinTileEntity method_1068(MixinCompoundTag nbttagcompound) {
        MixinTileEntity tileentity = null;
        try {
            Class class1 = (Class) ID_TO_CLASS.get((Object) nbttagcompound.getString("id"));
            if (class1 != null) {
                tileentity = (MixinTileEntity) class1.newInstance();
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
    static Class _mthclass$(String s) {
        try {
            return Class.forName(s);
        } catch (ClassNotFoundException classnotfoundexception) {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void readIdentifyingData(MixinCompoundTag tag) {
        this.x = tag.getInt("x");
        this.y = tag.getInt("y");
        this.z = tag.getInt("z");
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void writeIdentifyingData(MixinCompoundTag tag) {
        String s = (String) CLASS_TO_ID.get(this.getClass());
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
        return (String) CLASS_TO_ID.get(this.getClass());
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void tick() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getCachedId() {
        return this.level.getTileMeta(this.x, this.y, this.z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void markDirty() {
        if (this.level != null) {
            this.level.method_203(this.x, this.y, this.z, this);
        }
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
    public MixinTile getTile() {
        return Tile.BY_ID[this.level.getTileId(this.x, this.y, this.z)];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public AbstractPacket createInitialChunkDataPacket() {
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isInvalid() {
        return this.invalid;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void invalidate() {
        this.invalid = true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void validate() {
        this.invalid = false;
    }
}
