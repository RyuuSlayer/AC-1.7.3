package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityEffect;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GuiEffect extends Screen {

    private static final ArrayList<String> particleTypes = new ArrayList<>();

    static {
        particleTypes.add("bubble");
        particleTypes.add("explode");
        particleTypes.add("flame");
        particleTypes.add("heart");
        particleTypes.add("largesmoke");
        particleTypes.add("lava");
        particleTypes.add("note");
        particleTypes.add("portal");
        particleTypes.add("reddust");
        particleTypes.add("slime");
        particleTypes.add("smoke");
        particleTypes.add("snowballpoof");
        particleTypes.add("splash");
    }

    private final TileEntityEffect effect;
    private GuiSlider2 ticksBetweenParticles;
    private GuiSlider2 particlesPerSpawn;
    private GuiSlider2 offsetX;
    private GuiSlider2 offsetY;
    private GuiSlider2 offsetZ;
    private GuiSlider2 randX;
    private GuiSlider2 randY;
    private GuiSlider2 randZ;
    private GuiSlider2 floatArg1;
    private GuiSlider2 floatArg2;
    private GuiSlider2 floatArg3;
    private GuiSlider2 floatRand1;
    private GuiSlider2 floatRand2;
    private GuiSlider2 floatRand3;
    private GuiSlider2 fogR;
    private GuiSlider2 fogG;
    private GuiSlider2 fogB;
    private GuiSlider2 fogStart;
    private GuiSlider2 fogEnd;
    private int page = 0;

    public GuiEffect(TileEntityEffect m) {
        this.effect = m;
    }

    public static void showUI(TileEntityEffect m) {
        AccessMinecraft.getInstance().openScreen(new GuiEffect(m));
    }

    @Override
    public void init() {
        List<Button> buttons = (List<Button>) this.buttons;

        int buttonWidth = (this.width - 16) / 4;
        buttons.add(new Button(-1, 4, 0, buttonWidth, 18, "Particles"));
        buttons.add(new Button(-2, 4 + 4 + buttonWidth, 0, buttonWidth, 18, "Fog"));
        buttons.add(new Button(-3, 4 + (4 + buttonWidth) * 2, 0, buttonWidth, 18, "Overlay"));
        buttons.add(new Button(-4, 4 + (4 + buttonWidth) * 3, 0, buttonWidth, 18, "Replacements"));
        buttonWidth = (this.width - 16) / 3;

        if (this.page == 0) {
            this.particlesPerSpawn = new GuiSlider2(200, 4, 20, 10, String.format("Particles Per Spawn: %d", this.effect.particlesPerSpawn), (float) (this.effect.particlesPerSpawn - 1) / 49.0f);
            this.particlesPerSpawn.setWidth(buttonWidth);
            buttons.add(this.particlesPerSpawn);
            this.ticksBetweenParticles = new GuiSlider2(200, 4 + 4 + buttonWidth, 20, 10, String.format("Ticks Between: %d", this.effect.ticksBetweenParticles), (float) this.effect.ticksBetweenParticles / 100.0f);
            this.ticksBetweenParticles.setWidth(buttonWidth);
            buttons.add(this.ticksBetweenParticles);
            this.offsetX = new GuiSlider2(201, 4, 40, 10, String.format("offset X: %.2f", this.effect.offsetX), this.effect.offsetX / 8.0f);
            this.offsetX.setWidth(buttonWidth);
            buttons.add(this.offsetX);
            this.offsetY = new GuiSlider2(202, 4 + 4 + buttonWidth, 40, 10, String.format("offset Y: %.2f", this.effect.offsetY), this.effect.offsetY / 8.0f);
            this.offsetY.setWidth(buttonWidth);
            buttons.add(this.offsetY);
            this.offsetZ = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 40, 10, String.format("offset Z: %.2f", this.effect.offsetZ), this.effect.offsetZ / 8.0f);
            this.offsetZ.setWidth(buttonWidth);
            buttons.add(this.offsetZ);
            this.randX = new GuiSlider2(201, 4, 60, 10, String.format("Rand X: %.2f", this.effect.randX), this.effect.randX / 8.0f);
            this.randX.setWidth(buttonWidth);
            buttons.add(this.randX);
            this.randY = new GuiSlider2(202, 4 + 4 + buttonWidth, 60, 10, String.format("Rand Y: %.2f", this.effect.randY), this.effect.randY / 8.0f);
            this.randY.setWidth(buttonWidth);
            buttons.add(this.randY);
            this.randZ = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 60, 10, String.format("Rand Z: %.2f", this.effect.randZ), this.effect.randZ / 8.0f);
            this.randZ.setWidth(buttonWidth);
            buttons.add(this.randZ);
            this.floatArg1 = new GuiSlider2(201, 4, 80, 10, String.format("Arg 1: %.2f", this.effect.floatArg1), (this.effect.floatArg1 + 1.0f) / 2.0f);
            this.floatArg1.setWidth(buttonWidth);
            buttons.add(this.floatArg1);
            this.floatArg2 = new GuiSlider2(202, 4 + 4 + buttonWidth, 80, 10, String.format("Arg 2: %.2f", this.effect.floatArg2), (this.effect.floatArg2 + 1.0f) / 2.0f);
            this.floatArg2.setWidth(buttonWidth);
            buttons.add(this.floatArg2);
            this.floatArg3 = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 80, 10, String.format("Arg 3: %.2f", this.effect.floatArg3), (this.effect.floatArg3 + 1.0f) / 2.0f);
            this.floatArg3.setWidth(buttonWidth);
            buttons.add(this.floatArg3);
            this.floatRand1 = new GuiSlider2(201, 4, 100, 10, String.format("Rand Arg 1: %.2f", this.effect.floatRand1), this.effect.floatRand1);
            this.floatRand1.setWidth(buttonWidth);
            buttons.add(this.floatRand1);
            this.floatRand2 = new GuiSlider2(202, 4 + 4 + buttonWidth, 100, 10, String.format("Rand Arg 2: %.2f", this.effect.floatRand2), this.effect.floatRand2);
            this.floatRand2.setWidth(buttonWidth);
            buttons.add(this.floatRand2);
            this.floatRand3 = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 100, 10, String.format("Rand Arg 3: %.2f", this.effect.floatRand3), this.effect.floatRand3);
            this.floatRand3.setWidth(buttonWidth);
            buttons.add(this.floatRand3);
            buttons.add(new Button(0, 4, 120, buttonWidth, 18, "No Particles"));
            int i = 1;
            for (String particleType : particleTypes) {
                buttons.add(new Button(i, 4 + i % 3 * (4 + buttonWidth), 120 + i / 3 * 20, buttonWidth, 18, particleType));
                ++i;
            }
        } else if (this.page == 1) {
            Button b = new Button(0, 4, 20, buttonWidth, 18, "Don't Change Fog Color");
            if (this.effect.changeFogColor == 1) {
                b.text = "Change Fog Color";
            } else if (this.effect.changeFogColor == 2) {
                b.text = "Revert Fog Color To Normal";
            }
            buttons.add(b);
            this.fogR = new GuiSlider2(201, 4, 40, 10, String.format("Red: %.2f", this.effect.fogR), this.effect.fogR);
            this.fogR.setWidth(buttonWidth);
            buttons.add(this.fogR);
            this.fogG = new GuiSlider2(202, 4 + 4 + buttonWidth, 40, 10, String.format("Green: %.2f", this.effect.fogG), this.effect.fogG);
            this.fogG.setWidth(buttonWidth);
            buttons.add(this.fogG);
            this.fogB = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 40, 10, String.format("Blue: %.2f", this.effect.fogB), this.effect.fogB);
            this.fogB.setWidth(buttonWidth);
            buttons.add(this.fogB);
            b = new Button(1, 4, 60, buttonWidth, 18, "Don't Change Fog Density");
            if (this.effect.changeFogDensity == 1) {
                b.text = "Change Fog Density";
            } else if (this.effect.changeFogDensity == 2) {
                b.text = "Revert Fog Density To Normal";
            }
            buttons.add(b);
            this.fogStart = new GuiSlider2(201, 4, 80, 10, String.format("Start: %.1f", this.effect.fogStart), this.effect.fogStart / 512.0f);
            this.fogStart.setWidth(buttonWidth);
            buttons.add(this.fogStart);
            this.fogEnd = new GuiSlider2(202, 4 + 4 + buttonWidth, 80, 10, String.format("End: %.1f", this.effect.fogEnd), this.effect.fogEnd / 512.0f);
            this.fogEnd.setWidth(buttonWidth);
            buttons.add(this.fogEnd);
        } else if (this.page == 2) {
            Button b = new Button(0, 4, 20, buttonWidth, 18, "Change Overlay");
            if (!this.effect.setOverlay) {
                b.text = "Don't Change Overlay";
            }
            buttons.add(b);
            buttons.add(new Button(1, 4, 40, buttonWidth, 18, "Remove Overlay"));
            int i = 1;
            File overlays = new File(((ExLevel) this.effect.level).getLevelDir(), "overlays");
            if (overlays.exists() && overlays.isDirectory()) {
                for (File overlayFile : overlays.listFiles()) {
                    buttons.add(new Button(1 + i, 4 + i % 3 * (4 + buttonWidth), 40 + i / 3 * 20, buttonWidth, 18, overlayFile.getName()));
                    ++i;
                }
            }
        } else if (this.page == 3) {
            Button b = new Button(0, 4, 20, buttonWidth, 18, "Replace Textures");
            if (!this.effect.replaceTextures && this.effect.revertTextures) {
                b.text = "Revert Replacements";
            } else if (!this.effect.replaceTextures) {
                b.text = "Do Nothing";
            }
            buttons.add(b);
            int i = 0;
            File replacements = new File(((ExLevel) this.effect.level).getLevelDir(), "textureReplacement");
            if (replacements.exists() && replacements.isDirectory()) {
                for (File replacementFile : replacements.listFiles()) {
                    buttons.add(new Button(1 + i, 4 + i % 3 * (4 + buttonWidth), 40 + i / 3 * 20, buttonWidth, 18, replacementFile.getName()));
                    ++i;
                }
            }
        }
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id < 0) {
            this.page = -button.id - 1;
            this.buttons.clear();
            this.init();
            return;
        }
        if (this.page == 0) {
            if (button.id == 0) {
                this.effect.particleType = "";
            } else if (button.id > 0 && button.id < 100) {
                this.effect.particleType = particleTypes.get(button.id - 1);
            }
        } else if (this.page == 1) {
            if (button.id == 0) {
                this.effect.changeFogColor = (this.effect.changeFogColor + 1) % 3;
                button.text = this.effect.changeFogColor == 1 ? "Change Fog Color" : this.effect.changeFogColor == 2 ? "Revert Fog Color To Normal" : "Don't Change Fog Color";
            } else if (button.id == 1) {
                this.effect.changeFogDensity = (this.effect.changeFogDensity + 1) % 3;
                button.text = this.effect.changeFogDensity == 1 ? "Change Fog Density" : this.effect.changeFogDensity == 2 ? "Revert Fog Density To Normal" : "Don't Change Fog Density";
            }
        } else if (this.page == 2) {
            if (button.id == 0) {
                boolean bl = this.effect.setOverlay = !this.effect.setOverlay;
                button.text = this.effect.setOverlay ? "Change Overlay" : "Don't Change Overlay";
            } else {
                this.effect.overlay = button.id == 1 ? "" : button.text;
            }
        } else if (this.page == 3) {
            if (button.id == 0) {
                if (this.effect.replaceTextures) {
                    this.effect.replaceTextures = false;
                    this.effect.revertTextures = true;
                    button.text = "Revert Replacements";
                } else if (this.effect.revertTextures) {
                    this.effect.revertTextures = false;
                    button.text = "Do Nothing";
                } else {
                    this.effect.replaceTextures = true;
                    button.text = "Replace Textures";
                }
            } else {
                this.effect.textureReplacement = button.text;
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        if (this.page == 0) {
            this.effect.offsetX = this.offsetX.sliderValue * 8.0f;
            this.offsetX.text = String.format("Offset X: %.2f", this.effect.offsetX);
            this.effect.offsetY = this.offsetY.sliderValue * 8.0f;
            this.offsetY.text = String.format("Offset Y: %.2f", this.effect.offsetY);
            this.effect.offsetZ = this.offsetZ.sliderValue * 8.0f;
            this.offsetZ.text = String.format("Offset Z: %.2f", this.effect.offsetZ);
            this.effect.randX = this.randX.sliderValue * 8.0f;
            this.randX.text = String.format("Rand X: %.2f", this.effect.randX);
            this.effect.randY = this.randY.sliderValue * 8.0f;
            this.randY.text = String.format("Rand Y: %.2f", this.effect.randY);
            this.effect.randZ = this.randZ.sliderValue * 8.0f;
            this.randZ.text = String.format("Rand Z: %.2f", this.effect.randZ);
            this.effect.floatArg1 = this.floatArg1.sliderValue * 2.0f - 1.0f;
            this.floatArg1.text = String.format("Arg 1: %.2f", this.effect.floatArg1);
            this.effect.floatArg2 = this.floatArg2.sliderValue * 2.0f - 1.0f;
            this.floatArg2.text = String.format("Arg 2: %.2f", this.effect.floatArg2);
            this.effect.floatArg3 = this.floatArg3.sliderValue * 2.0f - 1.0f;
            this.floatArg3.text = String.format("Arg 3: %.2f", this.effect.floatArg3);
            this.effect.floatRand1 = this.floatRand1.sliderValue;
            this.floatRand1.text = String.format("Rand 1: %.2f", this.effect.floatRand1);
            this.effect.floatRand2 = this.floatRand2.sliderValue;
            this.floatRand2.text = String.format("Rand 2: %.2f", this.effect.floatRand2);
            this.effect.floatRand3 = this.floatRand3.sliderValue;
            this.floatRand3.text = String.format("Rand 3: %.2f", this.effect.floatRand3);
            this.effect.ticksBetweenParticles = (int) (this.ticksBetweenParticles.sliderValue * 100.0f + 0.5f);
            this.ticksBetweenParticles.text = String.format("Ticks Between: %d", this.effect.ticksBetweenParticles);
            this.effect.particlesPerSpawn = (int) (this.particlesPerSpawn.sliderValue * 49.0f + 1.5f);
            this.particlesPerSpawn.text = String.format("Particles Per Spawn: %d", this.effect.particlesPerSpawn);
            this.textManager.drawTextWithShadow("Particle Type: " + this.effect.particleType, 2 * this.width / 3, 25, -1);
        } else if (this.page == 1) {
            this.effect.fogR = this.fogR.sliderValue;
            this.fogR.text = String.format("Red: %.2f", this.effect.fogR);
            this.effect.fogG = this.fogG.sliderValue;
            this.fogG.text = String.format("Green: %.2f", this.effect.fogG);
            this.effect.fogB = this.fogB.sliderValue;
            this.fogB.text = String.format("Blue: %.2f", this.effect.fogB);
            this.effect.fogStart = this.fogStart.sliderValue * 512.0f;
            this.fogStart.text = String.format("Start: %.1f", this.effect.fogStart);
            this.effect.fogEnd = this.fogEnd.sliderValue * 512.0f;
            this.fogEnd.text = String.format("End: %.1f", this.effect.fogEnd);
        } else if (this.page == 2) {
            this.textManager.drawTextWithShadow("Overlay: " + this.effect.overlay, this.width / 3, 25, -1);
        } else if (this.page == 3) {
            this.textManager.drawTextWithShadow("Replacement: " + this.effect.textureReplacement, this.width / 3, 25, -1);
        }
        super.render(mouseX, mouseY, delta);
        this.effect.level.getChunk(this.effect.x, this.effect.z).markDirty();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
