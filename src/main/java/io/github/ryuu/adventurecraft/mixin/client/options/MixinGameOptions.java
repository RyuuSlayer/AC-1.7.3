package io.github.ryuu.adventurecraft.mixin.client.options;

import java.io.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.options.Option;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.resource.language.TranslationStorage;
import org.lwjgl.input.Keyboard;

public class MixinGameOptions {
    private static final String[] renderDistanceTranslationKeys = new String[]{"options.renderDistance.veryFar", "options.renderDistance.far", "options.renderDistance.normal", "options.renderDistance.short", "options.renderDistance.tiny"};
    private static final String[] difficultyTranslationKeys = new String[]{"options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard"};
    private static final String[] guiScaleTranslationKeys = new String[]{"options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"};
    private static final String[] performanceTranslationKeys = new String[]{"performance.max", "performance.balanced", "performance.powersaver"};
    public float music = 1.0f;
    public float sound = 1.0f;
    public float mouseSensitivity = 0.5f;
    public boolean invertYMouse = false;
    public int viewDistance;
    public boolean bobView;
    public boolean anaglyph3d;
    public boolean advancedOpengl;
    public int fpsLimit;
    public boolean fancyGraphics;
    public boolean ao;
    public String skin;
    public KeyBinding forwardKey;
    public KeyBinding leftKey;
    public KeyBinding backKey;
    public KeyBinding rightKey;
    public KeyBinding jumpKey;
    public KeyBinding inventoryKey;
    public KeyBinding dropKey;
    public KeyBinding chatKey;
    public KeyBinding fogKey;
    public KeyBinding sneakKey;
    public KeyBinding[] keyBindings;
    protected Minecraft minecraft;
    private File optionsFile;
    public int difficulty;
    public boolean hideHud;
    public boolean thirdPerson;
    public boolean debugHud;
    public String lastServer;
    public boolean field_1445;
    public boolean cinematicMode;
    public boolean field_1447;
    public float field_1448;
    public float field_1449;
    public int guiScale;
    public boolean autoFarClip;
    public boolean grass3d;

    public MixinGameOptions(Minecraft minecraft, File file) {
        this.viewDistance = 1;
        this.bobView = true;
        this.anaglyph3d = false;
        this.advancedOpengl = false;
        this.fpsLimit = 1;
        this.fancyGraphics = true;
        this.ao = true;
        this.skin = "Default";
        this.forwardKey = new KeyBinding("key.forward", 17);
        this.leftKey = new KeyBinding("key.left", 30);
        this.backKey = new KeyBinding("key.back", 31);
        this.rightKey = new KeyBinding("key.right", 32);
        this.jumpKey = new KeyBinding("key.jump", 57);
        this.inventoryKey = new KeyBinding("key.inventory", 18);
        this.dropKey = new KeyBinding("key.drop", 16);
        this.chatKey = new KeyBinding("key.chat", 20);
        this.fogKey = new KeyBinding("key.fog", 33);
        this.sneakKey = new KeyBinding("key.sneak", 42);
        this.keyBindings = new KeyBinding[]{this.forwardKey, this.leftKey, this.backKey, this.rightKey, this.jumpKey, this.sneakKey, this.dropKey, this.inventoryKey, this.chatKey, this.fogKey};
        this.difficulty = 2;
        this.hideHud = false;
        this.thirdPerson = false;
        this.debugHud = false;
        this.lastServer = "";
        this.field_1445 = false;
        this.cinematicMode = false;
        this.field_1447 = false;
        this.field_1448 = 1.0f;
        this.field_1449 = 1.0f;
        this.guiScale = 0;
        this.minecraft = minecraft;
        this.optionsFile = new File(file, "options.txt");
        this.autoFarClip = true;
        this.grass3d = true;
        this.load();
    }

    public MixinGameOptions() {
        this.viewDistance = 0;
        this.bobView = true;
        this.anaglyph3d = false;
        this.advancedOpengl = false;
        this.fpsLimit = 1;
        this.fancyGraphics = true;
        this.ao = true;
        this.skin = "Default";
        this.forwardKey = new KeyBinding("key.forward", 17);
        this.leftKey = new KeyBinding("key.left", 30);
        this.backKey = new KeyBinding("key.back", 31);
        this.rightKey = new KeyBinding("key.right", 32);
        this.jumpKey = new KeyBinding("key.jump", 57);
        this.inventoryKey = new KeyBinding("key.inventory", 18);
        this.dropKey = new KeyBinding("key.drop", 16);
        this.chatKey = new KeyBinding("key.chat", 20);
        this.fogKey = new KeyBinding("key.fog", 33);
        this.sneakKey = new KeyBinding("key.sneak", 42);
        this.keyBindings = new KeyBinding[]{this.forwardKey, this.leftKey, this.backKey, this.rightKey, this.jumpKey, this.sneakKey, this.dropKey, this.inventoryKey, this.chatKey, this.fogKey};
        this.difficulty = 2;
        this.hideHud = false;
        this.thirdPerson = false;
        this.debugHud = false;
        this.lastServer = "";
        this.field_1445 = false;
        this.cinematicMode = false;
        this.field_1447 = false;
        this.field_1448 = 1.0f;
        this.field_1449 = 1.0f;
        this.guiScale = 0;
    }

    public String getKeybindName(int index) {
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        return stringtranslate.translate(this.keyBindings[index].name);
    }

    public String getKeybindKey(int index) {
        return Keyboard.getKeyName(this.keyBindings[index].key);
    }

    public void method_1226(int i, int j) {
        this.keyBindings[i].key = j;
        this.saveOptions();
    }

    public void method_1228(Option enumoptions, float f) {
        if (enumoptions == Option.MUSIC) {
            this.music = f;
            this.minecraft.soundHelper.method_2008();
        }
        if (enumoptions == Option.SOUND) {
            this.sound = f;
            this.minecraft.soundHelper.method_2008();
        }
        if (enumoptions == Option.SENSITIVITY) {
            this.mouseSensitivity = f;
        }
    }

    public void changeOption(Option option, int i) {
        if (option == Option.INVERT_MOUSE) {
            boolean bl = this.invertYMouse = !this.invertYMouse;
        }
        if (option == Option.RENDER_DISTANCE) {
            this.viewDistance = (this.viewDistance + i) % 5;
            if (this.viewDistance < 0) {
                this.viewDistance = 4;
            }
        }
        if (option == Option.GUI_SCALE) {
            this.guiScale = this.guiScale + i & 3;
        }
        if (option == Option.VIEW_BOBBING) {
            boolean bl = this.bobView = !this.bobView;
        }
        if (option == Option.ADVANCED_OPENGL) {
            this.advancedOpengl = !this.advancedOpengl;
            this.minecraft.worldRenderer.method_1537();
        }
        if (option == Option.ANAGLYPH) {
            this.anaglyph3d = !this.anaglyph3d;
            this.minecraft.textureManager.reload();
        }
        if (option == Option.FRAMERATE_LIMIT) {
            this.fpsLimit = (this.fpsLimit + i + 3) % 3;
        }
        if (option == Option.DIFFICULTY) {
            this.difficulty = this.difficulty + i & 3;
        }
        if (option == Option.GRAPHICS) {
            this.fancyGraphics = !this.fancyGraphics;
            this.minecraft.worldRenderer.method_1537();
        }
        if (option == Option.AMBIENT_OCCLUSION) {
            this.ao = !this.ao;
            this.minecraft.worldRenderer.method_1537();
        }
        if (option == Option.AUTO_FAR_CLIP) {
            boolean bl = this.autoFarClip = !this.autoFarClip;
        }
        if (option == Option.GRASS_3D) {
            this.grass3d = !this.grass3d;
            this.minecraft.worldRenderer.method_1537();
        }
        this.saveOptions();
    }

    public float getFloatValue(Option enumoptions) {
        if (enumoptions == Option.MUSIC) {
            return this.music;
        }
        if (enumoptions == Option.SOUND) {
            return this.sound;
        }
        if (enumoptions == Option.SENSITIVITY) {
            return this.mouseSensitivity;
        }
        return 0.0f;
    }

    public boolean getBooleanValue(Option enumoptions) {
        switch (enumoptions) {
            case INVERT_MOUSE: {
                return this.invertYMouse;
            }
            case VIEW_BOBBING: {
                return this.bobView;
            }
            case ANAGLYPH: {
                return this.anaglyph3d;
            }
            case ADVANCED_OPENGL: {
                return this.advancedOpengl;
            }
            case AMBIENT_OCCLUSION: {
                return this.ao;
            }
            case AUTO_FAR_CLIP: {
                return this.autoFarClip;
            }
            case GRASS_3D: {
                return this.grass3d;
            }
        }
        return false;
    }

    public String getTranslatedValue(Option enumoptions) {
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        String s = stringtranslate.translate(enumoptions.getTranslationKey()) + ": ";
        if (enumoptions.isSlider()) {
            float f = this.getFloatValue(enumoptions);
            if (enumoptions == Option.SENSITIVITY) {
                if (f == 0.0f) {
                    return s + stringtranslate.translate("options.sensitivity.min");
                }
                if (f == 1.0f) {
                    return s + stringtranslate.translate("options.sensitivity.max");
                }
                return s + (int)(f * 200.0f) + "%";
            }
            if (f == 0.0f) {
                return s + stringtranslate.translate("options.off");
            }
            return s + (int)(f * 100.0f) + "%";
        }
        if (enumoptions.isToggle()) {
            boolean flag = this.getBooleanValue(enumoptions);
            if (flag) {
                return s + stringtranslate.translate("options.on");
            }
            return s + stringtranslate.translate("options.off");
        }
        if (enumoptions == Option.RENDER_DISTANCE) {
            return s + stringtranslate.translate(renderDistanceTranslationKeys[this.viewDistance]);
        }
        if (enumoptions == Option.DIFFICULTY) {
            return s + stringtranslate.translate(difficultyTranslationKeys[this.difficulty]);
        }
        if (enumoptions == Option.GUI_SCALE) {
            return s + stringtranslate.translate(guiScaleTranslationKeys[this.guiScale]);
        }
        if (enumoptions == Option.FRAMERATE_LIMIT) {
            return s + I18n.translate(performanceTranslationKeys[this.fpsLimit]);
        }
        if (enumoptions == Option.GRAPHICS) {
            if (this.fancyGraphics) {
                return s + stringtranslate.translate("options.graphics.fancy");
            }
            return s + stringtranslate.translate("options.graphics.fast");
        }
        if (enumoptions == Option.AUTO_FAR_CLIP) {
            return "Auto Far Clip: " + (this.autoFarClip ? "ON" : "OFF");
        }
        if (enumoptions == Option.GRASS_3D) {
            return "Grass 3D: " + (this.grass3d ? "ON" : "OFF");
        }
        return s;
    }

    public void load() {
        try {
            if (!this.optionsFile.exists()) {
                return;
            }
            BufferedReader bufferedreader = new BufferedReader(new FileReader(this.optionsFile));
            String s = "";
            while ((s = bufferedreader.readLine()) != null) {
                try {
                    String[] as = s.split(":");
                    if (as[0].equals("music")) {
                        this.music = this.parseFloat(as[1]);
                    }
                    if (as[0].equals("sound")) {
                        this.sound = this.parseFloat(as[1]);
                    }
                    if (as[0].equals("mouseSensitivity")) {
                        this.mouseSensitivity = this.parseFloat(as[1]);
                    }
                    if (as[0].equals("invertYMouse")) {
                        this.invertYMouse = as[1].equals("true");
                    }
                    if (as[0].equals("viewDistance")) {
                        this.viewDistance = Integer.parseInt(as[1]);
                    }
                    if (as[0].equals("guiScale")) {
                        this.guiScale = Integer.parseInt(as[1]);
                    }
                    if (as[0].equals("bobView")) {
                        this.bobView = as[1].equals("true");
                    }
                    if (as[0].equals("anaglyph3d")) {
                        this.anaglyph3d = as[1].equals("true");
                    }
                    if (as[0].equals("advancedOpengl")) {
                        this.advancedOpengl = as[1].equals("true");
                    }
                    if (as[0].equals("fpsLimit")) {
                        this.fpsLimit = Integer.parseInt(as[1]);
                    }
                    if (as[0].equals("difficulty")) {
                        this.difficulty = Integer.parseInt(as[1]);
                    }
                    if (as[0].equals("fancyGraphics")) {
                        this.fancyGraphics = as[1].equals("true");
                    }
                    if (as[0].equals("ao")) {
                        this.ao = as[1].equals("true");
                    }
                    if (as[0].equals("skin")) {
                        this.skin = as[1];
                    }
                    if (as[0].equals("lastServer") && as.length >= 2) {
                        this.lastServer = as[1];
                    }
                    if (as[0].equals("autoFarClip")) {
                        this.autoFarClip = as[1].equals("true");
                    }
                    if (as[0].equals("grass3d")) {
                        this.grass3d = as[1].equals("true");
                    }
                    for (int i = 0; i < this.keyBindings.length; ++i) {
                        if (!as[0].equals("key_" + this.keyBindings[i].name)) continue;
                        this.keyBindings[i].key = Integer.parseInt(as[1]);
                    }
                }
                catch (Exception exception1) {
                    System.out.println("Skipping bad option: " + s);
                }
            }
            bufferedreader.close();
        }
        catch (Exception exception) {
            System.out.println("Failed to load options");
            exception.printStackTrace();
        }
    }

    private float parseFloat(String str) {
        if (str.equals("true")) {
            return 1.0f;
        }
        if (str.equals("false")) {
            return 0.0f;
        }
        return Float.parseFloat(str);
    }

    public void saveOptions() {
        try {
            PrintWriter printwriter = new PrintWriter(new FileWriter(this.optionsFile));
            printwriter.println("music:" + this.music);
            printwriter.println("sound:" + this.sound);
            printwriter.println("invertYMouse:" + this.invertYMouse);
            printwriter.println("mouseSensitivity:" + this.mouseSensitivity);
            printwriter.println("viewDistance:" + this.viewDistance);
            printwriter.println("guiScale:" + this.guiScale);
            printwriter.println("bobView:" + this.bobView);
            printwriter.println("anaglyph3d:" + this.anaglyph3d);
            printwriter.println("advancedOpengl:" + this.advancedOpengl);
            printwriter.println("fpsLimit:" + this.fpsLimit);
            printwriter.println("difficulty:" + this.difficulty);
            printwriter.println("fancyGraphics:" + this.fancyGraphics);
            printwriter.println("ao:" + this.ao);
            printwriter.println("skin:" + this.skin);
            printwriter.println("lastServer:" + this.lastServer);
            printwriter.println("autoFarClip:" + this.autoFarClip);
            printwriter.println("grass3d:" + this.grass3d);
            for (int i = 0; i < this.keyBindings.length; ++i) {
                printwriter.println("key_" + this.keyBindings[i].name + ":" + this.keyBindings[i].key);
            }
            printwriter.close();
        }
        catch (Exception exception) {
            System.out.println("Failed to save options");
            exception.printStackTrace();
        }
    }
}