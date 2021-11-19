package io.github.ryuu.adventurecraft.gui;

import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.Textbox;
import net.minecraft.level.Level;

import java.io.File;

public class GuiWorldConfig extends Screen {
    private final Level world;
    private final Textbox[] lightLevels;
    int selectedID;
    private int page;
    private Textbox playerName;
    private Button setOnNewSave;
    private Button setOnLoad;
    private Button setOnUpdate;
    private boolean lightChanged;

    public GuiWorldConfig(Level w) {
        this.page = 0;
        this.selectedID = 0;
        this.lightChanged = false;
        this.world = w;
        this.lightLevels = new Textbox[16];
    }

    @Override
    public void tick() {
        if (this.page == 0) {
            this.playerName.tick();
        } else if (this.page == 2) {
            for (int i = 0; i < 16; i++)
                this.lightLevels[i].tick();
        }
    }

    @Override
    public void init() {
        int buttonWidth = (this.width - 16) / 4;
        this.buttons.add(new Button(-1, 4, 0, buttonWidth, 18, "Misc"));
        this.buttons.add(new Button(-2, 4 + 4 + buttonWidth, 0, buttonWidth, 18, "Script"));
        this.buttons.add(new Button(-3, 4 + 2 * (4 + buttonWidth), 0, buttonWidth, 18, "Light Levels"));
        buttonWidth = (this.width - 16) / 3;
        if (this.page == 0) {
            int w = this.textManager.getTextWidth("Player Name:") + 8;
            this.playerName = new Textbox(this, this.textManager, w, 24, this.width / 2 - w, 20, this.world.properties.playerName);
            this.playerName.field_2420 = true;
            this.playerName.method_1878(32);
            Button b = new Button(0, 4, 44, buttonWidth, 18, "Crafting: Enabled");
            this.buttons.add(b);
            if (!this.minecraft.level.properties.allowsInventoryCrafting)
                b.text = "Crafting: Disabled";
        } else if (this.page == 1) {
            this.selectedID = 0;
            this.setOnNewSave = new Button(0, 4, 24, "OnNewSave (selected): " + this.world.properties.onNewSaveScript);
            this.setOnLoad = new Button(1, 4, 46, "OnLoad: " + this.world.properties.onLoadScript);
            this.setOnUpdate = new Button(2, 4, 68, "OnUpdate: " + this.world.properties.onUpdateScript);
            this.buttons.add(this.setOnNewSave);
            this.buttons.add(this.setOnLoad);
            this.buttons.add(this.setOnUpdate);
            Button b = new Button(3, 4, 90, 200, 20, "Reload Scripts");
            this.buttons.add(b);
            b = new Button(4, 4, 112, 160, 18, "None");
            this.buttons.add(b);
            String[] scripts = getScriptFiles();
            if (scripts != null) {
                int i = 1;
                for (String scriptFile : scripts) {
                    b = new Button(4 + i, 4 + i % 3 * this.width / 3, 112 + i / 3 * 20, 160, 18, scriptFile);
                    this.buttons.add(b);
                    i++;
                }
            }
        } else if (this.page == 2) {
            for (int i = 0; i < 16; i++) {
                this.lightLevels[i] = new Textbox(this, this.textManager, 80, 22 + 14 * i, 80, 11, String.format("%.7f", Float.valueOf(this.minecraft.level.properties.brightness[i])));
            }
        } else if (this.page == 3) {

        }
    }

    private String[] getScriptFiles() {
        File scriptDir = new File(this.minecraft.level.levelDir, "scripts");
        if (scriptDir.exists() && scriptDir.isDirectory()) {
            File[] scriptFiles = scriptDir.listFiles();
            String[] fileNames = new String[scriptFiles.length];
            int i = 0;
            for (File scriptFile : scriptFiles)
                fileNames[i++] = scriptFile.getName();
            return fileNames;
        }
        return null;
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (guibutton.id < 0) {
            this.page = -guibutton.id - 1;
            this.buttons.clear();
            init();
            return;
        }
        if (this.page == 0) {
            if (guibutton.id == 0) {
                this.minecraft.level.properties.allowsInventoryCrafting = !this.minecraft.level.properties.allowsInventoryCrafting;
                guibutton.text = "Crafting: Enabled";
                if (!this.minecraft.level.properties.allowsInventoryCrafting)
                    guibutton.text = "Crafting: Disabled";
            }
        } else if (this.page == 1) {
            if (guibutton.id < 3) {
                this.selectedID = guibutton.id;
            } else if (guibutton.id == 3) {
                this.world.scriptHandler.loadScripts();
            } else if (guibutton.id == 4) {
                updateScriptFile("");
            } else {
                updateScriptFile(guibutton.text);
            }
            resetScriptNames();
        } else if (this.page != 2) {
            if (this.page == 3) ;
        }
    }

    private void updateScriptFile(String file) {
        if (this.selectedID == 0) {
            this.world.properties.onNewSaveScript = file;
        } else if (this.selectedID == 1) {
            this.world.properties.onLoadScript = file;
        } else if (this.selectedID == 2) {
            this.world.properties.onUpdateScript = file;
        }
    }

    private void resetScriptNames() {
        this.setOnNewSave.text = "OnNewSave: " + this.world.properties.onNewSaveScript;
        this.setOnLoad.text = "OnLoad: " + this.world.properties.onLoadScript;
        this.setOnUpdate.text = "OnUpdate: " + this.world.properties.onUpdateScript;
        if (this.selectedID == 0) {
            this.setOnNewSave.text = "OnNewSave (selected): " + this.world.properties.onNewSaveScript;
        } else if (this.selectedID == 1) {
            this.setOnLoad.text = "OnLoad (selected): " + this.world.properties.onLoadScript;
        } else if (this.selectedID == 2) {
            this.setOnUpdate.text = "OnUpdate (selected): " + this.world.properties.onUpdateScript;
        }
    }

    @Override
    protected void keyPressed(char c, int i) {
        if (this.page == 0) {
            if (this.playerName.field_2420)
                this.playerName.method_1877(c, i);
        } else if (this.page == 2) {
            for (int k = 0; k < 16; k++) {
                if ((this.lightLevels[k]).field_2420 && (i == 14 || (c >= '0' && c <= '9') || c == '.' || c == '\t'))
                    this.lightLevels[k].method_1877(c, i);
            }
        }
        super.keyPressed(c, i);
        if (i == 1 && this.lightChanged)
            this.minecraft.worldRenderer.updateAllTheRenderers();
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        if (this.page == 0) {
            this.playerName.method_1879(i, j, k);
        } else if (this.page == 2) {
            for (int l = 0; l < 16; l++)
                this.lightLevels[l].method_1879(i, j, k);
        }
    }

    @Override
    public void render(int i, int j, float f) {
        fill(0, 0, this.width, this.height, -2147483648);
        if (this.page == 0) {
            drawTextWithShadow(this.textManager, "Player Name:", 4, 30, 10526880);
            this.playerName.method_1883();
            this.world.properties.playerName = this.playerName.method_1876();
            this.minecraft.player.name = this.playerName.method_1876();
        } else if (this.page != 1) {
            if (this.page == 2) {
                for (int k = 0; k < 16; k++) {
                    drawTextWithShadow(this.textManager, String.format("Light Level %d", k), 4, 24 + 14 * k, 10526880);
                    this.lightLevels[k].method_1883();
                    try {
                        Float value = Float.valueOf(this.lightLevels[k].method_1876());
                        if (value != null)
                            if (value != Math.floor((this.minecraft.level.properties.brightness[k] * 1.0E7F)) / 1.0E7D) {
                                this.minecraft.level.properties.brightness[k] = value;
                                this.lightChanged = true;
                            }
                    } catch (NumberFormatException e) {
                    }
                }
                this.minecraft.level.loadBrightness();
            } else if (this.page == 3) {

            }
        }
        super.render(i, j, f);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}