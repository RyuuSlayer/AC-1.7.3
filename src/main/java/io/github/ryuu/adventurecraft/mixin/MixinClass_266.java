package io.github.ryuu.adventurecraft.mixin;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.class_267;
import net.minecraft.src.SoundPoolEntry;

public class MixinClass_266 {
    private Random rand = new Random();
    private Map field_1089 = new HashMap();
    private List field_1090 = new ArrayList();
    public int field_1086 = 0;
    public boolean field_1087 = true;

    public class_267 method_959(String s, File file) {
        try {
            String s1 = s;
            String noFileExtension = s = s.substring(0, s.indexOf("."));
            if (this.field_1087) {
                while (Character.isDigit(s.charAt(s.length() - 1))) {
                    s = s.substring(0, s.length() - 1);
                }
            }
            if (!this.field_1089.containsKey(s = s.replaceAll("/", "."))) {
                this.field_1089.put(s, new ArrayList());
            }
            class_267 soundpoolentry = new class_267(s1, file.toURI().toURL());
            List sounds = (List)this.field_1089.get(s);
            for (class_267 sEntry : sounds) {
                if (!noFileExtension.equals(sEntry.field_2126.substring(0, sEntry.field_2126.indexOf(".")))) continue;
                sounds.remove(sEntry);
                break;
            }
            sounds.add(soundpoolentry);
            this.field_1090.add(soundpoolentry);
            ++this.field_1086;
            return soundpoolentry;
        }
        catch (MalformedURLException malformedurlexception) {
            malformedurlexception.printStackTrace();
            throw new RuntimeException(malformedurlexception);
        }
    }

    public class_267 method_958(String s) {
        List list = (List)this.field_1089.get(s);
        if (list == null) {
            return null;
        }
        return (class_267)list.get(this.rand.nextInt(list.size()));
    }

    public class_267 method_957() {
        if (this.field_1090.size() == 0) {
            return null;
        }
        return (class_267)this.field_1090.get(this.rand.nextInt(this.field_1090.size()));
    }
}