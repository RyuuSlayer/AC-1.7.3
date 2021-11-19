package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import io.github.ryuu.adventurecraft.gui.GuiMapSelect;
import net.minecraft.class_520;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.DeleteConfirmationScreen;
import net.minecraft.client.gui.screen.EditLevelScreen;
import net.minecraft.client.gui.screen.SelectWorldScreen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.level.storage.LevelMetadata;
import net.minecraft.level.storage.LevelStorage;
import net.minecraft.util.maths.MathsHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class MixinSelectWorldScreen extends Screen {
    private final DateFormat dateFormat = new SimpleDateFormat();
    protected Screen parent;
    protected String title = "Select world";
    private boolean field_2434 = false;
    private int field_2435;
    private List field_2436;
    private SelectWorldScreen$class_569 field_2437;
    private String field_2438;
    private String field_2439;
    private boolean field_2440;
    private Button renameButton;
    private Button selectButton;
    private Button deleteButton;

    public MixinSelectWorldScreen(Screen guiscreen) {
        this.parent = guiscreen;
    }

    public void init() {
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        this.title = stringtranslate.translate("selectWorld.title");
        this.field_2438 = stringtranslate.translate("selectWorld.world");
        this.field_2439 = stringtranslate.translate("selectWorld.conversion");
        this.method_1897();
        this.field_2437 = new SelectWorldScreen$class_569(this);
        this.field_2437.method_1258(this.buttons, 4, 5);
        this.method_1896();
    }

    private void method_1897() {
        LevelStorage isaveformat = this.minecraft.method_2127();
        this.field_2436 = isaveformat.getMetadata();
        Collections.sort(this.field_2436);
        this.field_2435 = -1;
    }

    protected String method_1887(int i) {
        return ((LevelMetadata)this.field_2436.get(i)).getFileName();
    }

    protected String method_1889(int i) {
        String s = ((LevelMetadata)this.field_2436.get(i)).getLevelName();
        if (s == null || MathsHelper.isStringEmpty(s)) {
            TranslationStorage stringtranslate = TranslationStorage.getInstance();
            s = stringtranslate.translate("selectWorld.world") + " " + (i + 1);
        }
        return s;
    }

    public void method_1896() {
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        this.selectButton = new Button(1, this.width / 2 - 152, this.height - 28, 100, 20, "Load Save");
        this.buttons.add(this.selectButton);
        this.deleteButton = new Button(2, this.width / 2 - 50, this.height - 28, 100, 20, stringtranslate.translate("selectWorld.delete"));
        this.buttons.add(this.deleteButton);
        this.buttons.add(new Button(0, this.width / 2 + 52, this.height - 28, 100, 20, stringtranslate.translate("gui.cancel")));
        this.selectButton.active = false;
        this.deleteButton.active = false;
    }

    protected void buttonClicked(Button button) {
        if (!button.active) {
            return;
        }
        if (button.id == 2) {
            String s = this.method_1889(this.field_2435);
            if (s != null) {
                this.field_2440 = true;
                TranslationStorage stringtranslate = TranslationStorage.getInstance();
                String s1 = stringtranslate.translate("selectWorld.deleteQuestion");
                String s2 = "'" + s + "' " + stringtranslate.translate("selectWorld.deleteWarning");
                String s3 = stringtranslate.translate("selectWorld.deleteButton");
                String s4 = stringtranslate.translate("gui.cancel");
                DeleteConfirmationScreen guiyesno = new DeleteConfirmationScreen(this, s1, s2, s3, s4, this.field_2435);
                this.minecraft.openScreen(guiyesno);
            }
        } else if (button.id == 1) {
            this.method_1891(this.field_2435);
        } else if (button.id == 3) {
            this.minecraft.openScreen(new GuiMapSelect(this, ""));
        } else if (button.id == 6) {
            this.minecraft.openScreen(new EditLevelScreen(this, this.method_1887(this.field_2435)));
        } else if (button.id == 0) {
            this.minecraft.openScreen(this.parent);
        } else {
            this.field_2437.method_1259(button);
        }
    }

    public void method_1891(int i) {
        this.minecraft.openScreen(null);
        if (this.field_2434) {
            return;
        }
        this.field_2434 = true;
        this.minecraft.interactionManager = new class_520(this.minecraft);
        String s = this.method_1887(i);
        if (s == null) {
            s = "World" + i;
        }
        this.minecraft.createOrLoadWorld(s, this.method_1889(i), 0L);
    }

    public void updateServer(boolean flag, int i) {
        if (this.field_2440) {
            this.field_2440 = false;
            if (flag) {
                LevelStorage isaveformat = this.minecraft.method_2127();
                isaveformat.method_1003();
                isaveformat.method_1006(this.method_1887(i));
                this.method_1897();
            }
            this.minecraft.openScreen(this);
        }
    }

    public void render(int mouseX, int mouseY, float delta) {
        this.field_2437.method_1256(mouseX, mouseY, delta);
        this.drawTextWithShadowCentred(this.textManager, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(mouseX, mouseY, delta);
    }

    static List method_1884(SelectWorldScreen guiselectworld) {
        return guiselectworld.field_2436;
    }

    static int method_1885(SelectWorldScreen guiselectworld, int i) {
        guiselectworld.field_2435 = i;
        return guiselectworld.field_2435;
    }

    static int method_1886(SelectWorldScreen guiselectworld) {
        return guiselectworld.field_2435;
    }

    static Button method_1888(SelectWorldScreen guiselectworld) {
        return guiselectworld.selectButton;
    }

    static Button method_1890(SelectWorldScreen guiselectworld) {
        return guiselectworld.renameButton;
    }

    static Button method_1892(SelectWorldScreen guiselectworld) {
        return guiselectworld.deleteButton;
    }

    static String method_1893(SelectWorldScreen guiselectworld) {
        return guiselectworld.field_2438;
    }

    static DateFormat method_1894(SelectWorldScreen guiselectworld) {
        return guiselectworld.dateFormat;
    }

    static String method_1895(SelectWorldScreen guiselectworld) {
        return guiselectworld.field_2439;
    }
}