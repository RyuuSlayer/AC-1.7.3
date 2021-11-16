package io.github.ryuu.adventurecraft.mixin;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.src.SoundPoolEntry;

public class MixinClass_266 {
    private final Random c = new Random();

    private final Map d = new HashMap<>();

    private final List e = new ArrayList();

    public int a = 0;

    public boolean b = true;

    public bh a(String s, File file) {
        try {
            String s1 = s;
            s = s.substring(0, s.indexOf("."));
            String noFileExtension = s;
            if (this.b)
                for (; Character.isDigit(s.charAt(s.length() - 1)); s = s.substring(0, s.length() - 1)) ;
            s = s.replaceAll("/", ".");
            if (!this.d.containsKey(s))
                this.d.put(s, new ArrayList());
            bh soundpoolentry = new bh(s1, file.toURI().toURL());
            List<SoundPoolEntry> sounds = (List<SoundPoolEntry>) this.d.get(s);
            for (bh sEntry : sounds) {
                if (noFileExtension.equals(sEntry.a.substring(0, sEntry.a.indexOf(".")))) {
                    sounds.remove(sEntry);
                    break;
                }
            }
            sounds.add(soundpoolentry);
            this.e.add(soundpoolentry);
            this.a++;
            return soundpoolentry;
        } catch (MalformedURLException malformedurlexception) {
            malformedurlexception.printStackTrace();
            throw new RuntimeException(malformedurlexception);
        }
    }

    public bh a(String s) {
        List<bh> list = (List) this.d.get(s);
        if (list == null)
            return null;
        return list.get(this.c.nextInt(list.size()));
    }

    public bh a() {
        if (this.e.size() == 0)
            return null;
        return this.e.get(this.c.nextInt(this.e.size()));
    }
}
