package io.github.ryuu.adventurecraft.gui;

import java.io.File;
import java.util.ArrayList;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityEffect;
import net.minecraft.client.Minecraft;

public class GuiEffect extends da {
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

    private int page;

    public GuiEffect(TileEntityEffect m) {
        this.page = 0;
        this.effect = m;
    }

    public void b() {
        int buttonWidth = (this.c - 16) / 4;
        this.e.add(new ke(-1, 4, 0, buttonWidth, 18, "Particles"));
        this.e.add(new ke(-2, 4 + 4 + buttonWidth, 0, buttonWidth, 18, "Fog"));
        this.e.add(new ke(-3, 4 + (4 + buttonWidth) * 2, 0, buttonWidth, 18, "Overlay"));
        this.e.add(new ke(-4, 4 + (4 + buttonWidth) * 3, 0, buttonWidth, 18, "Replacements"));
        buttonWidth = (this.c - 16) / 3;
        if (this.page == 0) {
            this.particlesPerSpawn = new GuiSlider2(200, 4, 20, 10, String.format("Particles Per Spawn: %d", Integer.valueOf(this.effect.particlesPerSpawn)), (this.effect.particlesPerSpawn - 1) / 49.0F);
            this.particlesPerSpawn.a = buttonWidth;
            this.e.add(this.particlesPerSpawn);
            this.ticksBetweenParticles = new GuiSlider2(200, 4 + 4 + buttonWidth, 20, 10, String.format("Ticks Between: %d", Integer.valueOf(this.effect.ticksBetweenParticles)), this.effect.ticksBetweenParticles / 100.0F);
            this.ticksBetweenParticles.a = buttonWidth;
            this.e.add(this.ticksBetweenParticles);
            this.offsetX = new GuiSlider2(201, 4, 40, 10, String.format("offset X: %.2f", Float.valueOf(this.effect.offsetX)), this.effect.offsetX / 8.0F);
            this.offsetX.a = buttonWidth;
            this.e.add(this.offsetX);
            this.offsetY = new GuiSlider2(202, 4 + 4 + buttonWidth, 40, 10, String.format("offset Y: %.2f", Float.valueOf(this.effect.offsetY)), this.effect.offsetY / 8.0F);
            this.offsetY.a = buttonWidth;
            this.e.add(this.offsetY);
            this.offsetZ = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 40, 10, String.format("offset Z: %.2f", Float.valueOf(this.effect.offsetZ)), this.effect.offsetZ / 8.0F);
            this.offsetZ.a = buttonWidth;
            this.e.add(this.offsetZ);
            this.randX = new GuiSlider2(201, 4, 60, 10, String.format("Rand X: %.2f", Float.valueOf(this.effect.randX)), this.effect.randX / 8.0F);
            this.randX.a = buttonWidth;
            this.e.add(this.randX);
            this.randY = new GuiSlider2(202, 4 + 4 + buttonWidth, 60, 10, String.format("Rand Y: %.2f", Float.valueOf(this.effect.randY)), this.effect.randY / 8.0F);
            this.randY.a = buttonWidth;
            this.e.add(this.randY);
            this.randZ = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 60, 10, String.format("Rand Z: %.2f", Float.valueOf(this.effect.randZ)), this.effect.randZ / 8.0F);
            this.randZ.a = buttonWidth;
            this.e.add(this.randZ);
            this.floatArg1 = new GuiSlider2(201, 4, 80, 10, String.format("Arg 1: %.2f", Float.valueOf(this.effect.floatArg1)), (this.effect.floatArg1 + 1.0F) / 2.0F);
            this.floatArg1.a = buttonWidth;
            this.e.add(this.floatArg1);
            this.floatArg2 = new GuiSlider2(202, 4 + 4 + buttonWidth, 80, 10, String.format("Arg 2: %.2f", Float.valueOf(this.effect.floatArg2)), (this.effect.floatArg2 + 1.0F) / 2.0F);
            this.floatArg2.a = buttonWidth;
            this.e.add(this.floatArg2);
            this.floatArg3 = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 80, 10, String.format("Arg 3: %.2f", Float.valueOf(this.effect.floatArg3)), (this.effect.floatArg3 + 1.0F) / 2.0F);
            this.floatArg3.a = buttonWidth;
            this.e.add(this.floatArg3);
            this.floatRand1 = new GuiSlider2(201, 4, 100, 10, String.format("Rand Arg 1: %.2f", Float.valueOf(this.effect.floatRand1)), this.effect.floatRand1);
            this.floatRand1.a = buttonWidth;
            this.e.add(this.floatRand1);
            this.floatRand2 = new GuiSlider2(202, 4 + 4 + buttonWidth, 100, 10, String.format("Rand Arg 2: %.2f", Float.valueOf(this.effect.floatRand2)), this.effect.floatRand2);
            this.floatRand2.a = buttonWidth;
            this.e.add(this.floatRand2);
            this.floatRand3 = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 100, 10, String.format("Rand Arg 3: %.2f", Float.valueOf(this.effect.floatRand3)), this.effect.floatRand3);
            this.floatRand3.a = buttonWidth;
            this.e.add(this.floatRand3);
            this.e.add(new ke(0, 4, 120, buttonWidth, 18, "No Particles"));
            int i = 1;
            for (String particleType : particleTypes) {
                this.e.add(new ke(i, 4 + i % 3 * (4 + buttonWidth), 120 + i / 3 * 20, buttonWidth, 18, particleType));
                i++;
            }
        } else if (this.page == 1) {
            ke b = new ke(0, 4, 20, buttonWidth, 18, "Don't Change Fog Color");
            if (this.effect.changeFogColor == 1) {
                b.e = "Change Fog Color";
            } else if (this.effect.changeFogColor == 2) {
                b.e = "Revert Fog Color To Normal";
            }
            this.e.add(b);
            this.fogR = new GuiSlider2(201, 4, 40, 10, String.format("Red: %.2f", Float.valueOf(this.effect.fogR)), this.effect.fogR);
            this.fogR.a = buttonWidth;
            this.e.add(this.fogR);
            this.fogG = new GuiSlider2(202, 4 + 4 + buttonWidth, 40, 10, String.format("Green: %.2f", Float.valueOf(this.effect.fogG)), this.effect.fogG);
            this.fogG.a = buttonWidth;
            this.e.add(this.fogG);
            this.fogB = new GuiSlider2(203, 4 + 2 * (4 + buttonWidth), 40, 10, String.format("Blue: %.2f", Float.valueOf(this.effect.fogB)), this.effect.fogB);
            this.fogB.a = buttonWidth;
            this.e.add(this.fogB);
            b = new ke(1, 4, 60, buttonWidth, 18, "Don't Change Fog Density");
            if (this.effect.changeFogDensity == 1) {
                b.e = "Change Fog Density";
            } else if (this.effect.changeFogDensity == 2) {
                b.e = "Revert Fog Density To Normal";
            }
            this.e.add(b);
            this.fogStart = new GuiSlider2(201, 4, 80, 10, String.format("Start: %.1f", Float.valueOf(this.effect.fogStart)), this.effect.fogStart / 512.0F);
            this.fogStart.a = buttonWidth;
            this.e.add(this.fogStart);
            this.fogEnd = new GuiSlider2(202, 4 + 4 + buttonWidth, 80, 10, String.format("End: %.1f", Float.valueOf(this.effect.fogEnd)), this.effect.fogEnd / 512.0F);
            this.fogEnd.a = buttonWidth;
            this.e.add(this.fogEnd);
        } else if (this.page == 2) {
            ke b = new ke(0, 4, 20, buttonWidth, 18, "Change Overlay");
            if (!this.effect.setOverlay)
                b.e = "Don't Change Overlay";
            this.e.add(b);
            this.e.add(new ke(1, 4, 40, buttonWidth, 18, "Remove Overlay"));
            int i = 1;
            File overlays = new File(this.effect.d.levelDir, "overlays");
            if (overlays.exists() && overlays.isDirectory())
                for (File overlayFile : overlays.listFiles()) {
                    this.e.add(new ke(1 + i, 4 + i % 3 * (4 + buttonWidth), 40 + i / 3 * 20, buttonWidth, 18, overlayFile.getName()));
                    i++;
                }
        } else if (this.page == 3) {
            ke b = new ke(0, 4, 20, buttonWidth, 18, "Replace Textures");
            if (!this.effect.replaceTextures && this.effect.revertTextures) {
                b.e = "Revert Replacements";
            } else if (!this.effect.replaceTextures) {
                b.e = "Do Nothing";
            }
            this.e.add(b);
            int i = 0;
            File replacements = new File(this.effect.d.levelDir, "textureReplacement");
            if (replacements.exists() && replacements.isDirectory())
                for (File replacementFile : replacements.listFiles()) {
                    this.e.add(new ke(1 + i, 4 + i % 3 * (4 + buttonWidth), 40 + i / 3 * 20, buttonWidth, 18, replacementFile.getName()));
                    i++;
                }
        }
    }

    protected void a(ke guibutton) {
        if (guibutton.f < 0) {
            this.page = -guibutton.f - 1;
            this.e.clear();
            b();
            return;
        }
        if (this.page == 0) {
            if (guibutton.f == 0) {
                this.effect.particleType = "";
            } else if (guibutton.f > 0 && guibutton.f < 100) {
                this.effect.particleType = particleTypes.get(guibutton.f - 1);
            }
        } else if (this.page == 1) {
            if (guibutton.f == 0) {
                this.effect.changeFogColor = (this.effect.changeFogColor + 1) % 3;
                if (this.effect.changeFogColor == 1) {
                    guibutton.e = "Change Fog Color";
                } else if (this.effect.changeFogColor == 2) {
                    guibutton.e = "Revert Fog Color To Normal";
                } else {
                    guibutton.e = "Don't Change Fog Color";
                }
            } else if (guibutton.f == 1) {
                this.effect.changeFogDensity = (this.effect.changeFogDensity + 1) % 3;
                if (this.effect.changeFogDensity == 1) {
                    guibutton.e = "Change Fog Density";
                } else if (this.effect.changeFogDensity == 2) {
                    guibutton.e = "Revert Fog Density To Normal";
                } else {
                    guibutton.e = "Don't Change Fog Density";
                }
            }
        } else if (this.page == 2) {
            if (guibutton.f == 0) {
                this.effect.setOverlay = !this.effect.setOverlay;
                if (this.effect.setOverlay) {
                    guibutton.e = "Change Overlay";
                } else {
                    guibutton.e = "Don't Change Overlay";
                }
            } else if (guibutton.f == 1) {
                this.effect.overlay = "";
            } else {
                this.effect.overlay = guibutton.e;
            }
        } else if (this.page == 3) {
            if (guibutton.f == 0) {
                if (this.effect.replaceTextures) {
                    this.effect.replaceTextures = false;
                    this.effect.revertTextures = true;
                    guibutton.e = "Revert Replacements";
                } else if (this.effect.revertTextures) {
                    this.effect.replaceTextures = false;
                    this.effect.revertTextures = false;
                    guibutton.e = "Do Nothing";
                } else {
                    this.effect.replaceTextures = true;
                    this.effect.revertTextures = false;
                    guibutton.e = "Replace Textures";
                }
            } else {
                this.effect.textureReplacement = guibutton.e;
            }
        }
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        if (this.page == 0) {
            this.effect.offsetX = this.offsetX.sliderValue * 8.0F;
            this.offsetX.e = String.format("Offset X: %.2f", new Object[]{Float.valueOf(this.effect.offsetX)});
            this.effect.offsetY = this.offsetY.sliderValue * 8.0F;
            this.offsetY.e = String.format("Offset Y: %.2f", new Object[]{Float.valueOf(this.effect.offsetY)});
            this.effect.offsetZ = this.offsetZ.sliderValue * 8.0F;
            this.offsetZ.e = String.format("Offset Z: %.2f", new Object[]{Float.valueOf(this.effect.offsetZ)});
            this.effect.randX = this.randX.sliderValue * 8.0F;
            this.randX.e = String.format("Rand X: %.2f", new Object[]{Float.valueOf(this.effect.randX)});
            this.effect.randY = this.randY.sliderValue * 8.0F;
            this.randY.e = String.format("Rand Y: %.2f", new Object[]{Float.valueOf(this.effect.randY)});
            this.effect.randZ = this.randZ.sliderValue * 8.0F;
            this.randZ.e = String.format("Rand Z: %.2f", new Object[]{Float.valueOf(this.effect.randZ)});
            this.effect.floatArg1 = this.floatArg1.sliderValue * 2.0F - 1.0F;
            this.floatArg1.e = String.format("Arg 1: %.2f", new Object[]{Float.valueOf(this.effect.floatArg1)});
            this.effect.floatArg2 = this.floatArg2.sliderValue * 2.0F - 1.0F;
            this.floatArg2.e = String.format("Arg 2: %.2f", new Object[]{Float.valueOf(this.effect.floatArg2)});
            this.effect.floatArg3 = this.floatArg3.sliderValue * 2.0F - 1.0F;
            this.floatArg3.e = String.format("Arg 3: %.2f", new Object[]{Float.valueOf(this.effect.floatArg3)});
            this.effect.floatRand1 = this.floatRand1.sliderValue;
            this.floatRand1.e = String.format("Rand 1: %.2f", new Object[]{Float.valueOf(this.effect.floatRand1)});
            this.effect.floatRand2 = this.floatRand2.sliderValue;
            this.floatRand2.e = String.format("Rand 2: %.2f", new Object[]{Float.valueOf(this.effect.floatRand2)});
            this.effect.floatRand3 = this.floatRand3.sliderValue;
            this.floatRand3.e = String.format("Rand 3: %.2f", new Object[]{Float.valueOf(this.effect.floatRand3)});
            this.effect.ticksBetweenParticles = (int) (this.ticksBetweenParticles.sliderValue * 100.0F + 0.5F);
            this.ticksBetweenParticles.e = String.format("Ticks Between: %d", new Object[]{Integer.valueOf(this.effect.ticksBetweenParticles)});
            this.effect.particlesPerSpawn = (int) (this.particlesPerSpawn.sliderValue * 49.0F + 1.5F);
            this.particlesPerSpawn.e = String.format("Particles Per Spawn: %d", new Object[]{Integer.valueOf(this.effect.particlesPerSpawn)});
            this.g.a("Particle Type: " + this.effect.particleType, 2 * this.c / 3, 25, -1);
        } else if (this.page == 1) {
            this.effect.fogR = this.fogR.sliderValue;
            this.fogR.e = String.format("Red: %.2f", new Object[]{Float.valueOf(this.effect.fogR)});
            this.effect.fogG = this.fogG.sliderValue;
            this.fogG.e = String.format("Green: %.2f", new Object[]{Float.valueOf(this.effect.fogG)});
            this.effect.fogB = this.fogB.sliderValue;
            this.fogB.e = String.format("Blue: %.2f", new Object[]{Float.valueOf(this.effect.fogB)});
            this.effect.fogStart = this.fogStart.sliderValue * 512.0F;
            this.fogStart.e = String.format("Start: %.1f", new Object[]{Float.valueOf(this.effect.fogStart)});
            this.effect.fogEnd = this.fogEnd.sliderValue * 512.0F;
            this.fogEnd.e = String.format("End: %.1f", new Object[]{Float.valueOf(this.effect.fogEnd)});
        } else if (this.page == 2) {
            this.g.a("Overlay: " + this.effect.overlay, this.c / 3, 25, -1);
        } else if (this.page == 3) {
            this.g.a("Replacement: " + this.effect.textureReplacement, this.c / 3, 25, -1);
        }
        super.a(i, j, f);
        this.effect.d.b(this.effect.e, this.effect.g).g();
    }

    public static void showUI(TileEntityEffect m) {
        Minecraft.minecraftInstance.a(new AC_GuiEffect(m));
    }

    public boolean c() {
        return false;
    }

    private static final ArrayList<String> particleTypes = new ArrayList<String>();

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
}
