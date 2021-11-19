package io.github.ryuu.adventurecraft.mixin.client.resource.language;

import net.minecraft.client.resource.language.TranslationStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MixinTranslationStorage {
    private static TranslationStorage instance = new TranslationStorage();
    private Properties translations = new Properties();

    private MixinTranslationStorage() {
        try {
            this.translations.load(TranslationStorage.class.getResourceAsStream("/lang/en_US.lang"));
            this.translations.load(TranslationStorage.class.getResourceAsStream("/lang/stats_US.lang"));
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    public void loadMapTranslation(File levelDir) {
        try {
            this.translations.load(TranslationStorage.class.getResourceAsStream("/lang/en_US.lang"));
        }
        catch (IOException ioexception) {
            // empty catch block
        }
        try {
            File langFile = new File(levelDir, "/lang/en_US.lang");
            if (langFile.exists()) {
                FileInputStream is = new FileInputStream(langFile);
                this.translations.load(is);
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public static TranslationStorage getInstance() {
        return instance;
    }

    public String translate(String key) {
        return this.translations.getProperty(key, key);
    }

    public String translate(String key, Object[] arg) {
        String s1 = this.translations.getProperty(key, key);
        return String.format(s1, arg);
    }

    public String method_995(String s) {
        String t = this.translations.getProperty(s + ".name", "");
        if (t.equals("") && s != null) {
            String[] parts = s.split("\\.");
            t = parts[parts.length - 1];
            this.translations.setProperty(s, t);
        }
        return t;
    }
}