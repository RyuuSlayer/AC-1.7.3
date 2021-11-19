package io.github.ryuu.adventurecraft.mixin.util.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import net.minecraft.src.NBTBase;
import net.minecraft.util.io.*;

public class MixinCompoundTag extends AbstractTag {
    private Map<String, NBTBase> data = new HashMap();

    void write(DataOutput out) throws IOException {
        for (AbstractTag nbtbase : this.data.values()) {
            AbstractTag.writeTag(nbtbase, out);
        }
        out.writeByte(0);
    }

    void read(DataInput in) throws IOException {
        AbstractTag nbtbase;
        this.data.clear();
        while ((nbtbase = AbstractTag.readTag(in)).getType() != 0) {
            this.data.put((Object)nbtbase.getKey(), (Object)nbtbase);
        }
    }

    public Collection values() {
        return this.data.values();
    }

    public byte getType() {
        return 10;
    }

    public void put(String key, AbstractTag item) {
        this.data.put((Object)key, (Object)item.setKey(key));
    }

    public void put(String key, byte item) {
        this.data.put((Object)key, (Object)new ByteTag(item).setKey(key));
    }

    public void put(String key, short item) {
        if ((byte)item == item) {
            this.put(key, (byte)item);
        } else {
            this.data.put((Object)key, (Object)new ShortTag(item).setKey(key));
        }
    }

    public void put(String key, int item) {
        if ((short)item == item) {
            this.put(key, (short)item);
        } else {
            this.data.put((Object)key, (Object)new IntTag(item).setKey(key));
        }
    }

    public void put(String key, long item) {
        if ((long)((int)item) == item) {
            this.put(key, (int)item);
        } else {
            this.data.put((Object)key, (Object)new LongTag(item).setKey(key));
        }
    }

    public void put(String key, float item) {
        this.data.put((Object)key, (Object)new FloatTag(item).setKey(key));
    }

    public void put(String key, double item) {
        this.data.put((Object)key, (Object)new DoubleTag(item).setKey(key));
    }

    public void put(String key, String item) {
        this.data.put((Object)key, (Object)new StringTag(item).setKey(key));
    }

    public void put(String key, byte[] item) {
        this.data.put((Object)key, (Object)new ByteArrayTag(item).setKey(key));
    }

    public void put(String key, CompoundTag item) {
        this.data.put((Object)key, (Object)item.setKey(key));
    }

    public void put(String key, boolean item) {
        this.put(key, (byte)(item ? 1 : 0));
    }

    public boolean containsKey(String key) {
        return this.data.containsKey((Object)key);
    }

    public byte getByte(String key) {
        if (!this.data.containsKey((Object)key)) {
            return 0;
        }
        return ((ByteTag)this.data.get((Object)key)).data;
    }

    public short getShort(String key) {
        if (!this.data.containsKey((Object)key)) {
            return 0;
        }
        try {
            return ((ShortTag)this.data.get((Object)key)).data;
        }
        catch (ClassCastException e) {
            return this.getByte(key);
        }
    }

    public int getInt(String key) {
        if (!this.data.containsKey((Object)key)) {
            return 0;
        }
        try {
            return ((IntTag)this.data.get((Object)key)).data;
        }
        catch (ClassCastException e) {
            return this.getShort(key);
        }
    }

    public long getLong(String key) {
        if (!this.data.containsKey((Object)key)) {
            return 0L;
        }
        try {
            return ((LongTag)this.data.get((Object)key)).data;
        }
        catch (ClassCastException e) {
            return this.getInt(key);
        }
    }

    public float getFloat(String key) {
        if (!this.data.containsKey((Object)key)) {
            return 0.0f;
        }
        return ((FloatTag)this.data.get((Object)key)).data;
    }

    public double getDouble(String key) {
        if (!this.data.containsKey((Object)key)) {
            return 0.0;
        }
        return ((DoubleTag)this.data.get((Object)key)).data;
    }

    public String getString(String key) {
        if (!this.data.containsKey((Object)key)) {
            return "";
        }
        return ((StringTag)this.data.get((Object)key)).data;
    }

    public byte[] getByteArray(String key) {
        if (!this.data.containsKey((Object)key)) {
            return new byte[0];
        }
        return ((ByteArrayTag)this.data.get((Object)key)).data;
    }

    public CompoundTag getCompoundTag(String key) {
        if (!this.data.containsKey((Object)key)) {
            return new CompoundTag();
        }
        return (CompoundTag)this.data.get((Object)key);
    }

    public ListTag getListTag(String key) {
        if (!this.data.containsKey((Object)key)) {
            return new ListTag();
        }
        return (ListTag)this.data.get((Object)key);
    }

    public boolean getBoolean(String s) {
        return this.getByte(s) != 0;
    }

    public String toString() {
        return "" + this.data.size() + " entries";
    }

    public Set<String> getKeys() {
        return this.data.keySet();
    }
}