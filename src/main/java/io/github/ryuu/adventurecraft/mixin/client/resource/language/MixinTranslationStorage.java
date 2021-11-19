/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Properties
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin.client.resource.language;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.TranslationStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinTranslationStorage;

@Mixin(TranslationStorage.class)
public class MixinTranslationStorage {

    @Shadow()
    private static MixinTranslationStorage instance = new MixinTranslationStorage();

    private Properties translations = new Properties();

    private MixinTranslationStorage() {
        try {
            this.translations.load(MixinTranslationStorage.class.getResourceAsStream("/lang/en_US.lang"));
            this.translations.load(MixinTranslationStorage.class.getResourceAsStream("/lang/stats_US.lang"));
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
            this.translations.load(MixinTranslationStorage.class.getResourceAsStream("/lang/en_US.lang"));
        } catch (IOException ioexception) {
            // empty catch block
        }
        try {
            File langFile = new File(levelDir, "/lang/en_US.lang");
            if (langFile.exists()) {
                FileInputStream is = new FileInputStream(langFile);
                this.translations.load((InputStream) is);
            }
        } catch (IOException iOException) {
            // empty catch block
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static MixinTranslationStorage getInstance() {
        return instance;
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
        return String.format((String) s1, (Object[]) arg);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String method_995(String s) {
        String t = this.translations.getProperty(s + ".name", "");
        if (t.equals((Object) "") && s != null) {
            String[] parts = s.split("\\.");
            t = parts[parts.length - 1];
            this.translations.setProperty(s, t);
        }
        return t;
    }
}
