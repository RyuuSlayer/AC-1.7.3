package io.github.ryuu.adventurecraft.gui;

import net.minecraft.client.gui.Screen;
import net.minecraft.level.Level;

import java.io.File;

public class GuiWorldConfig extends Screen {
    private int page;

    private final Level world;

    private ro playerName;

    int selectedID;

    private ke setOnNewSave;

    private ke setOnLoad;

    private ke setOnUpdate;

    private final ro[] lightLevels;

    private boolean lightChanged;

    public GuiWorldConfig(Level w) {
        this.page = 0;
        this.selectedID = 0;
        this.lightChanged = false;
        this.world = w;
        this.lightLevels = new ro[16];
    }

    public void a() {
        if (this.page == 0) {
            this.playerName.b();
        } else if (this.page == 2) {
            for (int i = 0; i < 16; i++)
                this.lightLevels[i].b();
        }
    }

    public void b() {
        int buttonWidth = (this.c - 16) / 4;
        this.e.add(new ke(-1, 4, 0, buttonWidth, 18, "Misc"));
        this.e.add(new ke(-2, 4 + 4 + buttonWidth, 0, buttonWidth, 18, "Script"));
        this.e.add(new ke(-3, 4 + 2 * (4 + buttonWidth), 0, buttonWidth, 18, "Light Levels"));
        buttonWidth = (this.c - 16) / 3;
        if (this.page == 0) {
            int w = this.g.a("Player Name:") + 8;
            this.playerName = new ro(this, this.g, w, 24, this.c / 2 - w, 20, this.world.x.playerName);
            this.playerName.a = true;
            this.playerName.a(32);
            ke b = new ke(0, 4, 44, buttonWidth, 18, "Crafting: Enabled");
            this.e.add(b);
            if (!this.b.f.x.allowsInventoryCrafting)
                b.e = "Crafting: Disabled";
        } else if (this.page == 1) {
            this.selectedID = 0;
            this.setOnNewSave = new ke(0, 4, 24, "OnNewSave (selected): " + this.world.x.onNewSaveScript);
            this.setOnLoad = new ke(1, 4, 46, "OnLoad: " + this.world.x.onLoadScript);
            this.setOnUpdate = new ke(2, 4, 68, "OnUpdate: " + this.world.x.onUpdateScript);
            this.e.add(this.setOnNewSave);
            this.e.add(this.setOnLoad);
            this.e.add(this.setOnUpdate);
            ke b = new ke(3, 4, 90, 200, 20, "Reload Scripts");
            this.e.add(b);
            b = new ke(4, 4, 112, 160, 18, "None");
            this.e.add(b);
            String[] scripts = getScriptFiles();
            if (scripts != null) {
                int i = 1;
                for (String scriptFile : scripts) {
                    b = new ke(4 + i, 4 + i % 3 * this.c / 3, 112 + i / 3 * 20, 160, 18, scriptFile);
                    this.e.add(b);
                    i++;
                }
            }
        } else if (this.page == 2) {
            for (int i = 0; i < 16; i++) {
                this.lightLevels[i] = new ro(this, this.g, 80, 22 + 14 * i, 80, 11, String.format("%.7f", new Object[]{Float.valueOf(this.b.f.x.brightness[i])}));
            }
        } else if (this.page == 3) {

        }
    }

    private String[] getScriptFiles() {
        File scriptDir = new File(this.b.f.levelDir, "scripts");
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

    protected void a(ke guibutton) {
        if (guibutton.f < 0) {
            this.page = -guibutton.f - 1;
            this.e.clear();
            b();
            return;
        }
        if (this.page == 0) {
            if (guibutton.f == 0) {
                this.b.f.x.allowsInventoryCrafting = !this.b.f.x.allowsInventoryCrafting;
                guibutton.e = "Crafting: Enabled";
                if (!this.b.f.x.allowsInventoryCrafting)
                    guibutton.e = "Crafting: Disabled";
            }
        } else if (this.page == 1) {
            if (guibutton.f < 3) {
                this.selectedID = guibutton.f;
            } else if (guibutton.f == 3) {
                this.world.scriptHandler.loadScripts();
            } else if (guibutton.f == 4) {
                updateScriptFile("");
            } else {
                updateScriptFile(guibutton.e);
            }
            resetScriptNames();
        } else if (this.page != 2) {
            if (this.page == 3) ;
        }
    }

    private void updateScriptFile(String file) {
        if (this.selectedID == 0) {
            this.world.x.onNewSaveScript = file;
        } else if (this.selectedID == 1) {
            this.world.x.onLoadScript = file;
        } else if (this.selectedID == 2) {
            this.world.x.onUpdateScript = file;
        }
    }

    private void resetScriptNames() {
        this.setOnNewSave.e = "OnNewSave: " + this.world.x.onNewSaveScript;
        this.setOnLoad.e = "OnLoad: " + this.world.x.onLoadScript;
        this.setOnUpdate.e = "OnUpdate: " + this.world.x.onUpdateScript;
        if (this.selectedID == 0) {
            this.setOnNewSave.e = "OnNewSave (selected): " + this.world.x.onNewSaveScript;
        } else if (this.selectedID == 1) {
            this.setOnLoad.e = "OnLoad (selected): " + this.world.x.onLoadScript;
        } else if (this.selectedID == 2) {
            this.setOnUpdate.e = "OnUpdate (selected): " + this.world.x.onUpdateScript;
        }
    }

    protected void a(char c, int i) {
        if (this.page == 0) {
            if (this.playerName.a)
                this.playerName.a(c, i);
        } else if (this.page == 2) {
            for (int k = 0; k < 16; k++) {
                if ((this.lightLevels[k]).a && (i == 14 || (c >= '0' && c <= '9') || c == '.' || c == '\t'))
                    this.lightLevels[k].a(c, i);
            }
        }
        super.a(c, i);
        if (i == 1 && this.lightChanged)
            this.b.g.updateAllTheRenderers();
    }

    protected void a(int i, int j, int k) {
        super.a(i, j, k);
        if (this.page == 0) {
            this.playerName.a(i, j, k);
        } else if (this.page == 2) {
            for (int l = 0; l < 16; l++)
                this.lightLevels[l].a(i, j, k);
        }
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        if (this.page == 0) {
            b(this.g, "Player Name:", 4, 30, 10526880);
            this.playerName.c();
            this.world.x.playerName = this.playerName.a();
            this.b.h.l = this.playerName.a();
        } else if (this.page != 1) {
            if (this.page == 2) {
                for (int k = 0; k < 16; k++) {
                    b(this.g, String.format("Light Level %d", new Object[]{Integer.valueOf(k)}), 4, 24 + 14 * k, 10526880);
                    this.lightLevels[k].c();
                    try {
                        Float value = Float.valueOf(this.lightLevels[k].a());
                        if (value != null)
                            if (value.floatValue() != Math.floor((this.b.f.x.brightness[k] * 1.0E7F)) / 1.0E7D) {
                                this.b.f.x.brightness[k] = value.floatValue();
                                this.lightChanged = true;
                            }
                    } catch (NumberFormatException e) {
                    }
                }
                this.b.f.loadBrightness();
            } else if (this.page == 3) {

            }
        }
        super.a(i, j, f);
    }

    public boolean c() {
        return false;
    }
}