package io.github.ryuu.adventurecraft.mixin.client.resource.language;

import net.minecraft.client.resource.language.TranslationStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Mixin(TranslationStorage.class)
public class MixinTranslationStorage {

    @Shadow private Properties translations;

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/Class;getResourceAsStream(Ljava/lang/String;)Ljava/io/InputStream;", ordinal = 0))
    private InputStream TranslationStorage(Class instance, String e) {
        return instance.getResourceAsStream("/assets/adventurecraft/lang/en_US.lang");
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/Class;getResourceAsStream(Ljava/lang/String;)Ljava/io/InputStream;", ordinal = 1))
    private InputStream TranslationStorage1(Class instance, String e) {
        return instance.getResourceAsStream("/assets/adventurecraft/lang/stats_US.lang");
    }


    public void adventurecraft$loadMapTranslation(File levelDir) {
        try {
            this.translations.load(TranslationStorage.class.getResourceAsStream("/assets/adventurecraft/lang/en_US.lang"));
        } catch (IOException ignored) {
        }
        try {
            File langFile = new File(levelDir, "/assets/adventurecraft/lang/en_US.lang");
            if (langFile.exists()) {
                FileInputStream is = new FileInputStream(langFile);
                this.translations.load(is);
            }
        } catch (IOException ignored) {
        }
    }


    @Redirect(method = "method_995", at = @At(value = "INVOKE", target = "Ljava/util/Properties;getProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;"))
    private String method_9951(Properties instance, String s, String defaultValue) {
        String t = this.translations.getProperty(s, defaultValue);
        if (t.equals("") && s != null) {
            String[] parts = s.split("\\.");
            t = parts[parts.length - 1];
            this.translations.setProperty(s, t);
        }
        return t;
    }
}
