package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.EntityCamera;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.Textbox;

public class GuiCamera extends Screen {

    private EntityCamera cam;

    private Textbox timerText;

    public GuiCamera(EntityCamera c) {
        this.cam = c;
    }

    public static void showUI(EntityCamera c) {
        AccessMinecraft.getInstance().openScreen(new GuiCamera(c));
    }

    @Override
    public void init() {
        Button b = new Button(0, 4, 4, 160, 18, "Delete Camera Point");
        this.buttons.add(b);
        b = new Button(1, 4, 24, 160, 18, "No Interpolation");
        if (this.cam.type == 1) {
            b.text = "Linear Interpolation";
        } else if (this.cam.type == 2) {
            b.text = "Quadratic Interpolation";
        }
        this.buttons.add(b);
        this.timerText = new Textbox(this, this.textManager, 80, 46, 70, 16, String.format("%.2f", this.cam.time));
    }

    @Override
    public void tick() {
        this.timerText.tick();
    }

    @Override
    protected void keyPressed(char character, int key) {
        if (this.timerText.field_2420 && (key == 14 || character >= '0' && character <= '9' || character == '.' || character == '\t')) {
            this.timerText.method_1877(character, key);
        }
        super.keyPressed(character, key);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        this.timerText.method_1879(mouseX, mouseY, button);
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0) {
            this.cam.deleteCameraPoint();
            AccessMinecraft.getInstance().openScreen(null);
        } else if (button.id == 1) {
            this.cam.type = (this.cam.type + 1) % 3;
            this.minecraft.activeCutsceneCamera.setPointType(this.cam.cameraID, this.cam.type);
            for (Object obj : this.minecraft.level.entities) {
                if (obj instanceof EntityCamera) {
                    EntityCamera c = (EntityCamera) obj;
                    if (c.isAlive() && c.cameraID == this.cam.cameraID) {
                        this.cam = c;
                        break;
                    }
                }
            }
            button.text = this.cam.type == 1 ? "Linear Interpolation" : (this.cam.type == 2 ? "Quadratic Interpolation" : "No Interpolation");
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        try {
            float value = Float.parseFloat(this.timerText.method_1876());
            this.cam.time = value;
            this.minecraft.activeCutsceneCamera.setTime(this.cam.cameraID, (float) value);
        } catch (NumberFormatException e) {
        }
        this.drawTextWithShadow(this.textManager, "Active At:", 4, 49, 0xE0E0E0);
        this.timerText.method_1883();
        super.render(mouseX, mouseY, delta);
    }
}
