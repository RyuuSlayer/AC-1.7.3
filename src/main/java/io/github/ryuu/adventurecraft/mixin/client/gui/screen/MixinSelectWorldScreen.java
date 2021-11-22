package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.gui.GuiMapSelect;

@Mixin(SelectWorldScreen.class)
public class MixinSelectWorldScreen extends Screen {

    @Shadow()
    private final DateFormat dateFormat = new SimpleDateFormat();

    protected Screen parent;

    protected String title = "Select world";

    private boolean field_2434 = false;

    private int field_2435;

    private List field_2436;

    private class_569 field_2437;

    private String field_2438;

    private String field_2439;

    private boolean field_2440;

    private Button renameButton;

    private Button selectButton;

    private Button deleteButton;

    public MixinSelectWorldScreen(Screen guiscreen) {
        this.parent = guiscreen;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void init() {
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        this.title = stringtranslate.translate("selectWorld.title");
        this.field_2438 = stringtranslate.translate("selectWorld.world");
        this.field_2439 = stringtranslate.translate("selectWorld.conversion");
        this.method_1897();
        this.field_2437 = new class_569(this);
        this.field_2437.method_1258(this.buttons, 4, 5);
        this.method_1896();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1897() {
        LevelStorage isaveformat = this.minecraft.method_2127();
        this.field_2436 = isaveformat.getMetadata();
        Collections.sort((List) this.field_2436);
        this.field_2435 = -1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected String method_1887(int i) {
        return ((LevelMetadata) this.field_2436.get(i)).getFileName();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected String method_1889(int i) {
        String s = ((LevelMetadata) this.field_2436.get(i)).getLevelName();
        if (s == null || MathsHelper.isStringEmpty(s)) {
            TranslationStorage stringtranslate = TranslationStorage.getInstance();
            s = stringtranslate.translate("selectWorld.world") + " " + (i + 1);
        }
        return s;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1896() {
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        this.selectButton = new Button(1, this.width / 2 - 152, this.height - 28, 100, 20, "Load Save");
        this.buttons.add((Object) this.selectButton);
        this.deleteButton = new Button(2, this.width / 2 - 50, this.height - 28, 100, 20, stringtranslate.translate("selectWorld.delete"));
        this.buttons.add((Object) this.deleteButton);
        this.buttons.add((Object) new Button(0, this.width / 2 + 52, this.height - 28, 100, 20, stringtranslate.translate("gui.cancel")));
        this.selectButton.active = false;
        this.deleteButton.active = false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void render(int mouseX, int mouseY, float delta) {
        this.field_2437.method_1256(mouseX, mouseY, delta);
        this.drawTextWithShadowCentred(this.textManager, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(mouseX, mouseY, delta);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    static List method_1884(SelectWorldScreen guiselectworld) {
        return guiselectworld.field_2436;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    static int method_1885(SelectWorldScreen guiselectworld, int i) {
        guiselectworld.field_2435 = i;
        return guiselectworld.field_2435;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    static int method_1886(SelectWorldScreen guiselectworld) {
        return guiselectworld.field_2435;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    static Button method_1888(SelectWorldScreen guiselectworld) {
        return guiselectworld.selectButton;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    static Button method_1890(SelectWorldScreen guiselectworld) {
        return guiselectworld.renameButton;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    static Button method_1892(SelectWorldScreen guiselectworld) {
        return guiselectworld.deleteButton;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    static String method_1893(SelectWorldScreen guiselectworld) {
        return guiselectworld.field_2438;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    static DateFormat method_1894(SelectWorldScreen guiselectworld) {
        return guiselectworld.dateFormat;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    static String method_1895(SelectWorldScreen guiselectworld) {
        return guiselectworld.field_2439;
    }

    class class_569 extends class_97 {

        final SelectWorldScreen field_2444;

        public MixinSelectWorldScreen(SelectWorldScreen guiselectworld) {
            super(guiselectworld.minecraft, guiselectworld.width, guiselectworld.height, 32, guiselectworld.height - 32, 36);
            this.field_2444 = guiselectworld;
        }

        /**
         * @author Ryuu, TechPizza, Phil
         */
        @Override
        @Overwrite()
        protected int method_1266() {
            return SelectWorldScreen.method_1884(this.field_2444).size();
        }

        /**
         * @author Ryuu, TechPizza, Phil
         */
        @Override
        @Overwrite()
        protected void method_1267(int i, boolean flag) {
            boolean flag1;
            SelectWorldScreen.method_1885(this.field_2444, i);
            SelectWorldScreen.method_1888((SelectWorldScreen) this.field_2444).active = flag1 = SelectWorldScreen.method_1886(this.field_2444) >= 0 && SelectWorldScreen.method_1886(this.field_2444) < this.method_1266();
            SelectWorldScreen.method_1892((SelectWorldScreen) this.field_2444).active = flag1;
            if (flag && flag1) {
                this.field_2444.method_1891(i);
            }
        }

        /**
         * @author Ryuu, TechPizza, Phil
         */
        @Override
        @Overwrite()
        protected boolean method_1270(int i) {
            return i == SelectWorldScreen.method_1886(this.field_2444);
        }

        /**
         * @author Ryuu, TechPizza, Phil
         */
        @Override
        @Overwrite()
        protected int method_1268() {
            return SelectWorldScreen.method_1884(this.field_2444).size() * 36;
        }

        /**
         * @author Ryuu, TechPizza, Phil
         */
        @Override
        @Overwrite()
        protected void method_1269() {
            this.field_2444.renderBackground();
        }

        /**
         * @author Ryuu, TechPizza, Phil
         */
        @Override
        @Overwrite()
        protected void method_1264(int i, int j, int k, int l, Tessellator tessellator) {
            LevelMetadata saveformatcomparator = (LevelMetadata) SelectWorldScreen.method_1884(this.field_2444).get(i);
            String s = saveformatcomparator.getLevelName();
            if (s == null || MathsHelper.isStringEmpty(s)) {
                s = SelectWorldScreen.method_1893(this.field_2444) + " " + (i + 1);
            }
            String s1 = saveformatcomparator.getFileName();
            s1 = s1 + " (" + SelectWorldScreen.method_1894(this.field_2444).format(new Date(saveformatcomparator.getLastPlayedTime()));
            long l1 = saveformatcomparator.getFileSize();
            s1 = s1 + ", " + (float) (l1 / 1024L * 100L / 1024L) / 100.0f + " MB)";
            String s2 = "";
            if (saveformatcomparator.getVersion()) {
                s2 = SelectWorldScreen.method_1895(this.field_2444) + " " + s2;
            }
            this.field_2444.drawTextWithShadow(this.field_2444.textManager, s, j + 2, k + 1, 0xFFFFFF);
            this.field_2444.drawTextWithShadow(this.field_2444.textManager, s1, j + 2, k + 12, 0x808080);
            this.field_2444.drawTextWithShadow(this.field_2444.textManager, s2, j + 2, k + 12 + 10, 0x808080);
        }
    }
}
