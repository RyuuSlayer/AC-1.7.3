package io.github.ryuu.adventurecraft.mixin.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.SmokeRenderer;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.render.Tessellator;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;

@Mixin(Screen.class)
public class MixinScreen extends DrawableHelper {

    public int width;
    public int height;
    public boolean passEvents = false;
    public SmokeRenderer smokeRenderer;
    public boolean disableInputGrabbing = false;
    @Shadow()
    protected Minecraft minecraft;
    protected List buttons = new ArrayList();
    protected MixinTextRenderer textManager;
    protected Button lastClickedButton = null;

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static String getClipboardContents() {
        try {
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String s = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                return s;
            }
        } catch (Exception exception) {
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void render(int mouseX, int mouseY, float delta) {
        for (int k = 0; k < this.buttons.size(); ++k) {
            Button guibutton = (Button) this.buttons.get(k);
            guibutton.render(this.minecraft, mouseX, mouseY);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void keyPressed(char character, int key) {
        if (key == 1) {
            this.minecraft.openScreen(null);
            this.minecraft.lockCursor();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if (button == 0) {
            for (int l = 0; l < this.buttons.size(); ++l) {
                Button guibutton = (Button) this.buttons.get(l);
                if (!guibutton.isMouseOver(this.minecraft, mouseX, mouseY)) continue;
                this.lastClickedButton = guibutton;
                this.minecraft.soundHelper.playSound("random.click", 1.0f, 1.0f);
                this.buttonClicked(guibutton);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void mouseReleased(int mouseX, int mouseY, int button) {
        if (this.lastClickedButton != null && button == 0) {
            this.lastClickedButton.mouseReleased(mouseX, mouseY);
            this.lastClickedButton = null;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void buttonClicked(Button button) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void init(Minecraft minecraft, int width, int height) {
        this.smokeRenderer = new SmokeRenderer(minecraft);
        this.minecraft = minecraft;
        this.textManager = minecraft.textRenderer;
        this.width = width;
        this.height = height;
        this.buttons.clear();
        this.init();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void init() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_130() {
        while (Mouse.next()) {
            this.onMouseEvent();
        }
        while (Keyboard.next()) {
            this.onKeyboardEvent();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onMouseEvent() {
        if (Mouse.getEventButtonState()) {
            int i = Mouse.getEventX() * this.width / this.minecraft.actualWidth;
            int k = this.height - Mouse.getEventY() * this.height / this.minecraft.actualHeight - 1;
            this.mouseClicked(i, k, Mouse.getEventButton());
        } else {
            int j = Mouse.getEventX() * this.width / this.minecraft.actualWidth;
            int l = this.height - Mouse.getEventY() * this.height / this.minecraft.actualHeight - 1;
            this.mouseReleased(j, l, Mouse.getEventButton());
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onKeyboardEvent() {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == 87) {
                this.minecraft.toggleFullscreen();
                return;
            }
            this.keyPressed(Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void tick() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onClose() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void renderBackground() {
        this.renderBackground(0);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void renderBackground(int alpha) {
        if (this.minecraft.level != null) {
            this.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
        } else {
            this.renderDirtBackground(alpha);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void renderDirtBackground(int alpha) {
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/gui/background.png"));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f = 32.0f;
        tessellator.start();
        tessellator.colour(0x404040);
        tessellator.vertex(0.0, this.height, 0.0, 0.0, (float) this.height / f + (float) alpha);
        tessellator.vertex(this.width, this.height, 0.0, (float) this.width / f, (float) this.height / f + (float) alpha);
        tessellator.vertex(this.width, 0.0, 0.0, (float) this.width / f, 0 + alpha);
        tessellator.vertex(0.0, 0.0, 0.0, 0.0, 0 + alpha);
        tessellator.draw();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isPauseScreen() {
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void updateServer(boolean flag, int i) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_135() {
    }
}
