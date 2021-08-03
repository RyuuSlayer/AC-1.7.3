package io.github.ryuu.adventurecraft.overrides;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.minecraft.src.NBTBase;

public class nu extends ij {
    private final Map<String, NBTBase> a = new HashMap<String, NBTBase>();

    void a(DataOutput dataoutput) throws IOException {
        for (Iterator<ij> iterator = this.a.values().iterator(); iterator.hasNext(); ij.a(nbtbase, dataoutput))
            ij nbtbase = iterator.next();
        dataoutput.writeByte(0);
    }

    void a(DataInput datainput) throws IOException {
        this.a.clear();
        ij nbtbase;
        for (; (nbtbase = ij.b(datainput)).a() != 0; this.a.put(nbtbase.b(), nbtbase)) ;
    }

    public Collection c() {
        return this.a.values();
    }

    public byte a() {
        return 10;
    }

    public void a(String s, ij nbtbase) {
        this.a.put(s, nbtbase.a(s));
    }

    public void a(String s, byte byte0) {
        this.a.put(s, (new qp(byte0)).a(s));
    }

    public void a(String s, short word0) {
        if ((byte) word0 == word0) {
            a(s, (byte) word0);
        } else {
            this.a.put(s, (new ul(word0)).a(s));
        }
    }

    public void a(String s, int i) {
        if ((short) i == i) {
            a(s, (short) i);
        } else {
            this.a.put(s, (new pp(i)).a(s));
        }
    }

    public void a(String s, long l) {
        if ((int) l == l) {
            a(s, (int) l);
        } else {
            this.a.put(s, (new mi(l)).a(s));
        }
    }

    public void a(String s, float f) {
        this.a.put(s, (new p(f)).a(s));
    }

    public void a(String s, double d) {
        this.a.put(s, (new sz(d)).a(s));
    }

    public void a(String s, String s1) {
        this.a.put(s, (new xb(s1)).a(s));
    }

    public void a(String s, byte[] abyte0) {
        this.a.put(s, (new hn(abyte0)).a(s));
    }

    public void a(String s, nu nbttagcompound) {
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
        return ((qp) this.a.get(s)).a;
    }

    public short d(String s) {
        if (!this.a.containsKey(s))
            return 0;
        try {
            return ((ul) this.a.get(s)).a;
        } catch (ClassCastException e) {
            return c(s);
        }
    }

    public int e(String s) {
        if (!this.a.containsKey(s))
            return 0;
        try {
            return ((pp) this.a.get(s)).a;
        } catch (ClassCastException e) {
            return d(s);
        }
    }

    public long f(String s) {
        if (!this.a.containsKey(s))
            return 0L;
        try {
            return ((mi) this.a.get(s)).a;
        } catch (ClassCastException e) {
            return e(s);
        }
    }

    public float g(String s) {
        if (!this.a.containsKey(s))
            return 0.0F;
        return ((p) this.a.get(s)).a;
    }

    public double h(String s) {
        if (!this.a.containsKey(s))
            return 0.0D;
        return ((sz) this.a.get(s)).a;
    }

    public String i(String s) {
        if (!this.a.containsKey(s))
            return "";
        return ((xb) this.a.get(s)).a;
    }

    public byte[] j(String s) {
        if (!this.a.containsKey(s))
            return new byte[0];
        return ((hn) this.a.get(s)).a;
    }

    public nu k(String s) {
        if (!this.a.containsKey(s))
            return new nu();
        return (nu) this.a.get(s);
    }

    public sp l(String s) {
        if (!this.a.containsKey(s))
            return new sp();
        return (sp) this.a.get(s);
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
