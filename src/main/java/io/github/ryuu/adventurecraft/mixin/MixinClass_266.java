package io.github.ryuu.adventurecraft.mixin;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_266;
import net.minecraft.class_267;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(class_266.class)
public class MixinClass_266 {

    @Shadow()
    private Random rand = new Random();

    private Map field_1089 = new HashMap();

    private List field_1090 = new ArrayList();

    public int field_1086 = 0;

    public boolean field_1087 = true;

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public class_267 method_959(String s, File file) {
        try {
            String s1 = s;
            String noFileExtension = s = s.substring(0, s.indexOf("."));
            if (this.field_1087) {
                while (Character.isDigit((char) s.charAt(s.length() - 1))) {
                    s = s.substring(0, s.length() - 1);
                }
            }
            if (!this.field_1089.containsKey((Object) (s = s.replaceAll("/", ".")))) {
                this.field_1089.put((Object) s, (Object) new ArrayList());
            }
            class_267 soundpoolentry = new class_267(s1, file.toURI().toURL());
            List sounds = (List) this.field_1089.get((Object) s);
            for (class_267 sEntry : sounds) {
                if (!noFileExtension.equals((Object) sEntry.field_2126.substring(0, sEntry.field_2126.indexOf("."))))
                    continue;
                sounds.remove((Object) sEntry);
                break;
            }
            sounds.add((Object) soundpoolentry);
            this.field_1090.add((Object) soundpoolentry);
            ++this.field_1086;
            return soundpoolentry;
        } catch (MalformedURLException malformedurlexception) {
            malformedurlexception.printStackTrace();
            throw new RuntimeException((Throwable) malformedurlexception);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public class_267 method_958(String s) {
        List list = (List) this.field_1089.get((Object) s);
        if (list == null) {
            return null;
        }
        return (class_267) list.get(this.rand.nextInt(list.size()));
    }
}
