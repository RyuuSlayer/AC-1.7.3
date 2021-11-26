package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import io.github.ryuu.adventurecraft.gui.GuiMapSelect;
import net.minecraft.class_520;
import net.minecraft.class_97;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.DeleteConfirmationScreen;
import net.minecraft.client.gui.screen.EditLevelScreen;
import net.minecraft.client.gui.screen.SelectWorldScreen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.resource.language.TranslationStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SelectWorldScreen.class)
public abstract class MixinSelectWorldScreen extends Screen implements AccessSelectWorldScreen {

    @Shadow
    protected Screen parent;

    @Shadow
    protected String title;

    @Shadow
    private boolean field_2434;

    @Shadow
    private int field_2435;

    @Shadow
    private SelectWorldScreen.class_569 field_2437;

    @Shadow
    private boolean field_2440;

    @Shadow
    private Button selectButton;

    @Shadow
    private Button deleteButton;

    public MixinSelectWorldScreen(Screen guiscreen) {
        this.parent = guiscreen;
    }

    @Shadow
    protected abstract String method_1889(int i);

    @Shadow
    protected abstract String method_1887(int i);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
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
    @Overwrite
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

    @Mixin(SelectWorldScreen.class_569.class)
    static abstract class class_569 extends class_97 {

        @Final
        @Shadow
        SelectWorldScreen field_2444;

        public class_569(Minecraft minecraft, int i, int j, int k, int i1, int i2) {
            super(minecraft, i, j, k, i1, i2);
        }

        @ModifyConstant(method = "<init>", constant = @Constant(intValue = 64))
        private int initHeightOffset(int value) {
            return 32;
        }

        /**
         * @author Ryuu, TechPizza, Phil
         */
        @Override
        @Overwrite
        protected void method_1267(int i, boolean flag) {
            boolean flag1;
            ((AccessSelectWorldScreen) this.field_2444).setField_2435(i);
            ((AccessSelectWorldScreen) this.field_2444).getSelectButton().active = flag1 = ((AccessSelectWorldScreen) this.field_2444).getField_2435() >= 0 && ((AccessSelectWorldScreen) this.field_2444).getField_2435() < this.method_1266();
            ((AccessSelectWorldScreen) this.field_2444).getDeleteButton().active = flag1;
            if (flag && flag1) {
                this.field_2444.method_1891(i);
            }
        }
    }
}
