package io.github.ryuu.adventurecraft.gui;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import java.io.File;
import java.util.ArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;

public class GuiEffect extends Screen {

    private TileEntityEffect effect;

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

    private static ArrayList<String> particleTypes = new ArrayList();

    public GuiEffect(TileEntityEffect m) {
        this.effect = m;
    }

    @Override
    public void init() {
        block16: {
            int buttonWidth;
            block18: {
                block17: {
                    block15: {
                        buttonWidth = (this.width - 16) / 4;
                        this.buttons.add((Object) new Button(-1, 4, 0, buttonWidth, 18, "Particles"));
                        this.buttons.add((Object) new Button(-2, 4 + (4 + buttonWidth), 0, buttonWidth, 18, "Fog"));
                        this.buttons.add((Object) new Button(-3, 4 + (4 + buttonWidth) * 2, 0, buttonWidth, 18, "Overlay"));
                        this.buttons.add((Object) new Button(-4, 4 + (4 + buttonWidth) * 3, 0, buttonWidth, 18, "Replacements"));
                        buttonWidth = (this.width - 16) / 3;
                        if (this.page != 0)
                            break block15;
                        this.particlesPerSpawn = new GuiSlider2(200, 4, 20, 10, String.format((String) "Particles Per Spawn: %d", (Object[]) new Object[] { this.effect.particlesPerSpawn }), (float) (this.effect.particlesPerSpawn - 1) / 49.0f);
                        this.particlesPerSpawn.width = buttonWidth;
                        this.buttons.add((Object) this.particlesPerSpawn);
                        this.ticksBetweenParticles = new GuiSlider2(200, 4 + (4 + buttonWidth), 20, 10, String.format((String) "Ticks Between: %d", (Object[]) new Object[] { this.effect.ticksBetweenParticles }), (float) this.effect.ticksBetweenParticles / 100.0f);
                        this.ticksBetweenParticles.width = buttonWidth;
                        this.buttons.add((Object) this.ticksBetweenParticles);
                        this.offsetX = new GuiSlider2(201, 4, 40, 10, String.format((String) "offset X: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.offsetX) }), this.effect.offsetX / 8.0f);
                        this.offsetX.width = buttonWidth;
                        this.buttons.add((Object) this.offsetX);
                        this.offsetY = new GuiSlider2(202, 4 + (4 + buttonWidth), 40, 10, String.format((String) "offset Y: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.offsetY) }), this.effect.offsetY / 8.0f);
                        this.offsetY.width = buttonWidth;
                        this.buttons.add((Object) this.offsetY);
                        this.offsetZ = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 40, 10, String.format((String) "offset Z: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.offsetZ) }), this.effect.offsetZ / 8.0f);
                        this.offsetZ.width = buttonWidth;
                        this.buttons.add((Object) this.offsetZ);
                        this.randX = new GuiSlider2(201, 4, 60, 10, String.format((String) "Rand X: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.randX) }), this.effect.randX / 8.0f);
                        this.randX.width = buttonWidth;
                        this.buttons.add((Object) this.randX);
                        this.randY = new GuiSlider2(202, 4 + (4 + buttonWidth), 60, 10, String.format((String) "Rand Y: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.randY) }), this.effect.randY / 8.0f);
                        this.randY.width = buttonWidth;
                        this.buttons.add((Object) this.randY);
                        this.randZ = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 60, 10, String.format((String) "Rand Z: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.randZ) }), this.effect.randZ / 8.0f);
                        this.randZ.width = buttonWidth;
                        this.buttons.add((Object) this.randZ);
                        this.floatArg1 = new GuiSlider2(201, 4, 80, 10, String.format((String) "Arg 1: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.floatArg1) }), (this.effect.floatArg1 + 1.0f) / 2.0f);
                        this.floatArg1.width = buttonWidth;
                        this.buttons.add((Object) this.floatArg1);
                        this.floatArg2 = new GuiSlider2(202, 4 + (4 + buttonWidth), 80, 10, String.format((String) "Arg 2: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.floatArg2) }), (this.effect.floatArg2 + 1.0f) / 2.0f);
                        this.floatArg2.width = buttonWidth;
                        this.buttons.add((Object) this.floatArg2);
                        this.floatArg3 = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 80, 10, String.format((String) "Arg 3: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.floatArg3) }), (this.effect.floatArg3 + 1.0f) / 2.0f);
                        this.floatArg3.width = buttonWidth;
                        this.buttons.add((Object) this.floatArg3);
                        this.floatRand1 = new GuiSlider2(201, 4, 100, 10, String.format((String) "Rand Arg 1: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.floatRand1) }), this.effect.floatRand1);
                        this.floatRand1.width = buttonWidth;
                        this.buttons.add((Object) this.floatRand1);
                        this.floatRand2 = new GuiSlider2(202, 4 + (4 + buttonWidth), 100, 10, String.format((String) "Rand Arg 2: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.floatRand2) }), this.effect.floatRand2);
                        this.floatRand2.width = buttonWidth;
                        this.buttons.add((Object) this.floatRand2);
                        this.floatRand3 = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 100, 10, String.format((String) "Rand Arg 3: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.floatRand3) }), this.effect.floatRand3);
                        this.floatRand3.width = buttonWidth;
                        this.buttons.add((Object) this.floatRand3);
                        this.buttons.add((Object) new Button(0, 4, 120, buttonWidth, 18, "No Particles"));
                        int i = 1;
                        for (String particleType : particleTypes) {
                            this.buttons.add((Object) new Button(i, 4 + i % 3 * (4 + buttonWidth), 120 + i / 3 * 20, buttonWidth, 18, particleType));
                            ++i;
                        }
                        break block16;
                    }
                    if (this.page != 1)
                        break block17;
                    Button b = new Button(0, 4, 20, buttonWidth, 18, "Don't Change Fog Color");
                    if (this.effect.changeFogColor == 1) {
                        b.text = "Change Fog Color";
                    } else if (this.effect.changeFogColor == 2) {
                        b.text = "Revert Fog Color To Normal";
                    }
                    this.buttons.add((Object) b);
                    this.fogR = new GuiSlider2(201, 4, 40, 10, String.format((String) "Red: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.fogR) }), this.effect.fogR);
                    this.fogR.width = buttonWidth;
                    this.buttons.add((Object) this.fogR);
                    this.fogG = new GuiSlider2(202, 4 + (4 + buttonWidth), 40, 10, String.format((String) "Green: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.fogG) }), this.effect.fogG);
                    this.fogG.width = buttonWidth;
                    this.buttons.add((Object) this.fogG);
                    this.fogB = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 40, 10, String.format((String) "Blue: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.fogB) }), this.effect.fogB);
                    this.fogB.width = buttonWidth;
                    this.buttons.add((Object) this.fogB);
                    b = new Button(1, 4, 60, buttonWidth, 18, "Don't Change Fog Density");
                    if (this.effect.changeFogDensity == 1) {
                        b.text = "Change Fog Density";
                    } else if (this.effect.changeFogDensity == 2) {
                        b.text = "Revert Fog Density To Normal";
                    }
                    this.buttons.add((Object) b);
                    this.fogStart = new GuiSlider2(201, 4, 80, 10, String.format((String) "Start: %.1f", (Object[]) new Object[] { Float.valueOf((float) this.effect.fogStart) }), this.effect.fogStart / 512.0f);
                    this.fogStart.width = buttonWidth;
                    this.buttons.add((Object) this.fogStart);
                    this.fogEnd = new GuiSlider2(202, 4 + (4 + buttonWidth), 80, 10, String.format((String) "End: %.1f", (Object[]) new Object[] { Float.valueOf((float) this.effect.fogEnd) }), this.effect.fogEnd / 512.0f);
                    this.fogEnd.width = buttonWidth;
                    this.buttons.add((Object) this.fogEnd);
                    break block16;
                }
                if (this.page != 2)
                    break block18;
                Button b = new Button(0, 4, 20, buttonWidth, 18, "Change Overlay");
                if (!this.effect.setOverlay) {
                    b.text = "Don't Change Overlay";
                }
                this.buttons.add((Object) b);
                this.buttons.add((Object) new Button(1, 4, 40, buttonWidth, 18, "Remove Overlay"));
                int i = 1;
                File overlays = new File(this.effect.level.levelDir, "overlays");
                if (!overlays.exists() || !overlays.isDirectory())
                    break block16;
                for (File overlayFile : overlays.listFiles()) {
                    this.buttons.add((Object) new Button(1 + i, 4 + i % 3 * (4 + buttonWidth), 40 + i / 3 * 20, buttonWidth, 18, overlayFile.getName()));
                    ++i;
                }
                break block16;
            }
            if (this.page == 3) {
                Button b = new Button(0, 4, 20, buttonWidth, 18, "Replace Textures");
                if (!this.effect.replaceTextures && this.effect.revertTextures) {
                    b.text = "Revert Replacements";
                } else if (!this.effect.replaceTextures) {
                    b.text = "Do Nothing";
                }
                this.buttons.add((Object) b);
                int i = 0;
                File replacements = new File(this.effect.level.levelDir, "textureReplacement");
                if (replacements.exists() && replacements.isDirectory()) {
                    for (File replacementFile : replacements.listFiles()) {
                        this.buttons.add((Object) new Button(1 + i, 4 + i % 3 * (4 + buttonWidth), 40 + i / 3 * 20, buttonWidth, 18, replacementFile.getName()));
                        ++i;
                    }
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
                this.effect.particleType = (String) particleTypes.get(button.id - 1);
            }
        } else if (this.page == 1) {
            if (button.id == 0) {
                this.effect.changeFogColor = (this.effect.changeFogColor + 1) % 3;
                button.text = this.effect.changeFogColor == 1 ? "Change Fog Color" : (this.effect.changeFogColor == 2 ? "Revert Fog Color To Normal" : "Don't Change Fog Color");
            } else if (button.id == 1) {
                this.effect.changeFogDensity = (this.effect.changeFogDensity + 1) % 3;
                button.text = this.effect.changeFogDensity == 1 ? "Change Fog Density" : (this.effect.changeFogDensity == 2 ? "Revert Fog Density To Normal" : "Don't Change Fog Density");
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
                    this.effect.replaceTextures = false;
                    this.effect.revertTextures = false;
                    button.text = "Do Nothing";
                } else {
                    this.effect.replaceTextures = true;
                    this.effect.revertTextures = false;
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
            this.offsetX.text = String.format((String) "Offset X: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.offsetX) });
            this.effect.offsetY = this.offsetY.sliderValue * 8.0f;
            this.offsetY.text = String.format((String) "Offset Y: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.offsetY) });
            this.effect.offsetZ = this.offsetZ.sliderValue * 8.0f;
            this.offsetZ.text = String.format((String) "Offset Z: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.offsetZ) });
            this.effect.randX = this.randX.sliderValue * 8.0f;
            this.randX.text = String.format((String) "Rand X: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.randX) });
            this.effect.randY = this.randY.sliderValue * 8.0f;
            this.randY.text = String.format((String) "Rand Y: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.randY) });
            this.effect.randZ = this.randZ.sliderValue * 8.0f;
            this.randZ.text = String.format((String) "Rand Z: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.randZ) });
            this.effect.floatArg1 = this.floatArg1.sliderValue * 2.0f - 1.0f;
            this.floatArg1.text = String.format((String) "Arg 1: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.floatArg1) });
            this.effect.floatArg2 = this.floatArg2.sliderValue * 2.0f - 1.0f;
            this.floatArg2.text = String.format((String) "Arg 2: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.floatArg2) });
            this.effect.floatArg3 = this.floatArg3.sliderValue * 2.0f - 1.0f;
            this.floatArg3.text = String.format((String) "Arg 3: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.floatArg3) });
            this.effect.floatRand1 = this.floatRand1.sliderValue;
            this.floatRand1.text = String.format((String) "Rand 1: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.floatRand1) });
            this.effect.floatRand2 = this.floatRand2.sliderValue;
            this.floatRand2.text = String.format((String) "Rand 2: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.floatRand2) });
            this.effect.floatRand3 = this.floatRand3.sliderValue;
            this.floatRand3.text = String.format((String) "Rand 3: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.floatRand3) });
            this.effect.ticksBetweenParticles = (int) (this.ticksBetweenParticles.sliderValue * 100.0f + 0.5f);
            this.ticksBetweenParticles.text = String.format((String) "Ticks Between: %d", (Object[]) new Object[] { this.effect.ticksBetweenParticles });
            this.effect.particlesPerSpawn = (int) (this.particlesPerSpawn.sliderValue * 49.0f + 1.5f);
            this.particlesPerSpawn.text = String.format((String) "Particles Per Spawn: %d", (Object[]) new Object[] { this.effect.particlesPerSpawn });
            this.textManager.drawTextWithShadow("Particle Type: " + this.effect.particleType, 2 * this.width / 3, 25, -1);
        } else if (this.page == 1) {
            this.effect.fogR = this.fogR.sliderValue;
            this.fogR.text = String.format((String) "Red: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.fogR) });
            this.effect.fogG = this.fogG.sliderValue;
            this.fogG.text = String.format((String) "Green: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.fogG) });
            this.effect.fogB = this.fogB.sliderValue;
            this.fogB.text = String.format((String) "Blue: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.effect.fogB) });
            this.effect.fogStart = this.fogStart.sliderValue * 512.0f;
            this.fogStart.text = String.format((String) "Start: %.1f", (Object[]) new Object[] { Float.valueOf((float) this.effect.fogStart) });
            this.effect.fogEnd = this.fogEnd.sliderValue * 512.0f;
            this.fogEnd.text = String.format((String) "End: %.1f", (Object[]) new Object[] { Float.valueOf((float) this.effect.fogEnd) });
        } else if (this.page == 2) {
            this.textManager.drawTextWithShadow("Overlay: " + this.effect.overlay, this.width / 3, 25, -1);
        } else if (this.page == 3) {
            this.textManager.drawTextWithShadow("Replacement: " + this.effect.textureReplacement, this.width / 3, 25, -1);
        }
        super.render(mouseX, mouseY, delta);
        this.effect.level.getChunk(this.effect.x, this.effect.z).method_885();
    }

    public static void showUI(TileEntityEffect m) {
        Minecraft.minecraftInstance.openScreen(new GuiEffect(m));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    static {
        particleTypes.add((Object) "bubble");
        particleTypes.add((Object) "explode");
        particleTypes.add((Object) "flame");
        particleTypes.add((Object) "heart");
        particleTypes.add((Object) "largesmoke");
        particleTypes.add((Object) "lava");
        particleTypes.add((Object) "note");
        particleTypes.add((Object) "portal");
        particleTypes.add((Object) "reddust");
        particleTypes.add((Object) "slime");
        particleTypes.add((Object) "smoke");
        particleTypes.add((Object) "snowballpoof");
        particleTypes.add((Object) "splash");
    }
}
