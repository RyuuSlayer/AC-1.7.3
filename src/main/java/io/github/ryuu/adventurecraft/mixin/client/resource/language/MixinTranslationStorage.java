package io.github.ryuu.adventurecraft.mixin.client.resource.language;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Mixin(TranslationStorage.class)
public class MixinTranslationStorage {

    @Shadow()
    private static final TranslationStorage instance = new TranslationStorage();

    private final Properties translations = new Properties();

    private MixinTranslationStorage() {
        try {
            this.translations.load(TranslationStorage.class.getResourceAsStream("/lang/en_US.lang"));
            this.translations.load(TranslationStorage.class.getResourceAsStream("/lang/stats_US.lang"));
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void loadMapTranslation(File levelDir) {
        try {
            this.translations.load(TranslationStorage.class.getResourceAsStream("/lang/en_US.lang"));
        } catch (IOException ioexception) {
        }
        try {
            File langFile = new File(levelDir, "/lang/en_US.lang");
            if (langFile.exists()) {
                FileInputStream is = new FileInputStream(langFile);
                this.translations.load(is);
            }
        } catch (IOException iOException) {
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String translate(String key) {
        return this.translations.getProperty(key, key);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String translate(String key, Object[] arg) {
        String s1 = this.translations.getProperty(key, key);
        return String.format(s1, arg);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
