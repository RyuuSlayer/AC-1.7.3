/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.io.DataInput
 *  java.io.DataOutput
 *  java.io.IOException
 *  java.lang.ClassCastException
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.Map
 *  java.util.Set
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.src.NBTBase
 */
package io.github.ryuu.adventurecraft.mixin.util.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.NBTBase;
import net.minecraft.util.io.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinCompoundTag;

@Mixin(CompoundTag.class)
public class MixinCompoundTag extends AbstractTag {

    @Shadow()
    private Map<String, NBTBase> data = new HashMap();

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    void write(DataOutput out) throws IOException {
        for (AbstractTag nbtbase : this.data.values()) {
            AbstractTag.writeTag(nbtbase, out);
        }
        out.writeByte(0);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    void read(DataInput in) throws IOException {
        AbstractTag nbtbase;
        this.data.clear();
        while ((nbtbase = AbstractTag.readTag(in)).getType() != 0) {
            this.data.put((Object) nbtbase.getKey(), (Object) nbtbase);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Collection values() {
        return this.data.values();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public byte getType() {
        return 10;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void put(String key, AbstractTag item) {
        this.data.put((Object) key, (Object) item.setKey(key));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void put(String key, byte item) {
        this.data.put((Object) key, (Object) new ByteTag(item).setKey(key));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void put(String key, short item) {
        if ((byte) item == item) {
            this.put(key, (byte) item);
        } else {
            this.data.put((Object) key, (Object) new ShortTag(item).setKey(key));
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void put(String key, int item) {
        if ((short) item == item) {
            this.put(key, (short) item);
        } else {
            this.data.put((Object) key, (Object) new IntTag(item).setKey(key));
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void put(String key, long item) {
        if ((long) ((int) item) == item) {
            this.put(key, (int) item);
        } else {
            this.data.put((Object) key, (Object) new LongTag(item).setKey(key));
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void put(String key, float item) {
        this.data.put((Object) key, (Object) new FloatTag(item).setKey(key));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void put(String key, double item) {
        this.data.put((Object) key, (Object) new DoubleTag(item).setKey(key));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void put(String key, String item) {
        this.data.put((Object) key, (Object) new StringTag(item).setKey(key));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void put(String key, byte[] item) {
        this.data.put((Object) key, (Object) new ByteArrayTag(item).setKey(key));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void put(String key, MixinCompoundTag item) {
        this.data.put((Object) key, (Object) item.setKey(key));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void put(String key, boolean item) {
        this.put(key, (byte) (item ? 1 : 0));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean containsKey(String key) {
        return this.data.containsKey((Object) key);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public byte getByte(String key) {
        if (!this.data.containsKey((Object) key)) {
            return 0;
        }
        return ((ByteTag) this.data.get((Object) key)).data;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public short getShort(String key) {
        if (!this.data.containsKey((Object) key)) {
            return 0;
        }
        try {
            return ((ShortTag) this.data.get((Object) key)).data;
        } catch (ClassCastException e) {
            return this.getByte(key);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getInt(String key) {
        if (!this.data.containsKey((Object) key)) {
            return 0;
        }
        try {
            return ((IntTag) this.data.get((Object) key)).data;
        } catch (ClassCastException e) {
            return this.getShort(key);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public long getLong(String key) {
        if (!this.data.containsKey((Object) key)) {
            return 0L;
        }
        try {
            return ((LongTag) this.data.get((Object) key)).data;
        } catch (ClassCastException e) {
            return this.getInt(key);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getFloat(String key) {
        if (!this.data.containsKey((Object) key)) {
            return 0.0f;
        }
        return ((FloatTag) this.data.get((Object) key)).data;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public double getDouble(String key) {
        if (!this.data.containsKey((Object) key)) {
            return 0.0;
        }
        return ((DoubleTag) this.data.get((Object) key)).data;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String getString(String key) {
        if (!this.data.containsKey((Object) key)) {
            return "";
        }
        return ((StringTag) this.data.get((Object) key)).data;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public byte[] getByteArray(String key) {
        if (!this.data.containsKey((Object) key)) {
            return new byte[0];
        }
        return ((ByteArrayTag) this.data.get((Object) key)).data;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinCompoundTag getCompoundTag(String key) {
        if (!this.data.containsKey((Object) key)) {
            return new MixinCompoundTag();
        }
        return (MixinCompoundTag) this.data.get((Object) key);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public ListTag getListTag(String key) {
        if (!this.data.containsKey((Object) key)) {
            return new ListTag();
        }
        return (ListTag) this.data.get((Object) key);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean getBoolean(String s) {
        return this.getByte(s) != 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String toString() {
        return "" + this.data.size() + " entries";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Set<String> getKeys() {
        return this.data.keySet();
    }
}
