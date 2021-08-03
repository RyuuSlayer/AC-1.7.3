package io.github.ryuu.adventurecraft.overrides;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class nh {
    private Properties b = new Properties();

    private nh() {
        try {
            this.b.load(nh.class.getResourceAsStream("/lang/en_US.lang"));
            this.b.load(nh.class.getResourceAsStream("/lang/stats_US.lang"));
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    public void loadMapTranslation(File levelDir) {
        try {
            this.b.load(nh.class.getResourceAsStream("/lang/en_US.lang"));
        } catch (IOException ioexception) {}
        try {
            File langFile = new File(levelDir, "/lang/en_US.lang");
            if (langFile.exists()) {
                InputStream is = new FileInputStream(langFile);
                this.b.load(is);
            }
        } catch (IOException ioexception) {}
    }

    public static nh a() {
        return a;
    }

    public String a(String s) {
        return this.b.getProperty(s, s);
    }

    public String a(String s, Object[] aobj) {
        String s1 = this.b.getProperty(s, s);
        return String.format(s1, aobj);
    }

    public String b(String s) {
        String t = this.b.getProperty(s + ".name", "");
        if (t.equals("") && s != null) {
            String[] parts = s.split("\\.");
            t = parts[parts.length - 1];
            this.b.setProperty(s, t);
        }
        return t;
    }

    private static nh a = new nh();
}
