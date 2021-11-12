package io.github.ryuu.adventurecraft.mixin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.src.NBTBase;
import net.minecraft.util.io.*;

public class MixinCompoundTag extends AbstractTag {
    private final Map<String, NBTBase> a = new HashMap<String, NBTBase>();

    void a(DataOutput dataoutput) throws IOException {
        for (Iterator<AbstractTag> iterator = this.a.values().iterator(); iterator.hasNext(); AbstractTag.a(nbtbase, dataoutput))
            AbstractTag nbtbase = iterator.next();
        dataoutput.writeByte(0);
    }

    void a(DataInput datainput) throws IOException {
        this.a.clear();
        AbstractTag nbtbase;
        for (; (nbtbase = AbstractTag.b(datainput)).a() != 0; this.a.put(nbtbase.b(), nbtbase)) ;
    }

    public Collection c() {
        return this.a.values();
    }

    public byte a() {
        return 10;
    }

    public void a(String s, AbstractTag nbtbase) {
        this.a.put(s, nbtbase.a(s));
    }

    public void a(String s, byte byte0) {
        this.a.put(s, (new ByteTag(byte0)).a(s));
    }

    public void a(String s, short word0) {
        if ((byte) word0 == word0) {
            a(s, (byte) word0);
        } else {
            this.a.put(s, (new ShortTag(word0)).a(s));
        }
    }

    public void a(String s, int i) {
        if ((short) i == i) {
            a(s, (short) i);
        } else {
            this.a.put(s, (new IntTag(i)).a(s));
        }
    }

    public void a(String s, long l) {
        if ((int) l == l) {
            a(s, (int) l);
        } else {
            this.a.put(s, (new LongTag(l)).a(s));
        }
    }

    public void a(String s, float f) {
        this.a.put(s, (new FloatTag(f)).a(s));
    }

    public void a(String s, double d) {
        this.a.put(s, (new DoubleTag(d)).a(s));
    }

    public void a(String s, String s1) {
        this.a.put(s, (new StringTag(s1)).a(s));
    }

    public void a(String s, byte[] abyte0) {
        this.a.put(s, (new ByteArrayTag(abyte0)).a(s));
    }

    public void a(String s, CompoundTag nbttagcompound) {
        this.a.put(s, nbttagcompound.a(s));
    }

    public void a(String s, boolean flag) {
        a(s, (byte) (flag ? 1 : 0));
    }

    public boolean b(String s) {
        return this.a.containsKey(s);
    }

    public byte c(String s) {
        if (!this.a.containsKey(s))
            return 0;
        return ((ByteTag) this.a.get(s)).a;
    }

    public short d(String s) {
        if (!this.a.containsKey(s))
            return 0;
        try {
            return ((ShortTag) this.a.get(s)).a;
        } catch (ClassCastException e) {
            return c(s);
        }
    }

    public int e(String s) {
        if (!this.a.containsKey(s))
            return 0;
        try {
            return ((IntTag) this.a.get(s)).a;
        } catch (ClassCastException e) {
            return d(s);
        }
    }

    public long f(String s) {
        if (!this.a.containsKey(s))
            return 0L;
        try {
            return ((LongTag) this.a.get(s)).a;
        } catch (ClassCastException e) {
            return e(s);
        }
    }

    public float g(String s) {
        if (!this.a.containsKey(s))
            return 0.0F;
        return ((FloatTag) this.a.get(s)).a;
    }

    public double h(String s) {
        if (!this.a.containsKey(s))
            return 0.0D;
        return ((DoubleTag) this.a.get(s)).a;
    }

    public String i(String s) {
        if (!this.a.containsKey(s))
            return "";
        return ((StringTag) this.a.get(s)).a;
    }

    public byte[] j(String s) {
        if (!this.a.containsKey(s))
            return new byte[0];
        return ((ByteArrayTag) this.a.get(s)).a;
    }

    public CompoundTag k(String s) {
        if (!this.a.containsKey(s))
            return new CompoundTag();
        return (CompoundTag) this.a.get(s);
    }

    public ListTag l(String s) {
        if (!this.a.containsKey(s))
            return new ListTag();
        return (ListTag) this.a.get(s);
    }

    public boolean m(String s) {
        return (c(s) != 0);
    }

    public String toString() {
        return "" + this.a.size() + " entries";
    }

    public Set<String> getKeys() {
        return this.a.keySet();
    }
}
