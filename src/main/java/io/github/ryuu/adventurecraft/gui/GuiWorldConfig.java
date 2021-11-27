package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.extensions.client.render.ExWorldRenderer;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.Textbox;
import net.minecraft.level.Level;

import java.io.File;

public class GuiWorldConfig extends Screen {

    private final Level world;
    private final Textbox[] lightLevels;
    int selectedID = 0;
    private int page = 0;
    private Textbox playerName;
    private Button setOnNewSave;
    private Button setOnLoad;
    private Button setOnUpdate;
    private boolean lightChanged = false;

    public GuiWorldConfig(Level w) {
        this.world = w;
        this.lightLevels = new Textbox[16];
    }

    @Override
    public void tick() {
        if (this.page == 0) {
            this.playerName.tick();
        } else if (this.page == 2) {
            for (int i = 0; i < 16; ++i) {
                this.lightLevels[i].tick();
            }
        }
    }

    @Override
    public void init() {
        int buttonWidth = (this.width - 16) / 4;
        this.buttons.add(new Button(-1, 4, 0, buttonWidth, 18, "Misc"));
        this.buttons.add(new Button(-2, 4 + (4 + buttonWidth), 0, buttonWidth, 18, "Script"));
        this.buttons.add(new Button(-3, 4 + 2 * (4 + buttonWidth), 0, buttonWidth, 18, "Light Levels"));
        buttonWidth = (this.width - 16) / 3;
        if (this.page == 0) {
            int w = this.textManager.getTextWidth("Player Name:") + 8;
            this.playerName = new Textbox(this, this.textManager, w, 24, this.width / 2 - w, 20, this.world.properties.playerName);
            this.playerName.field_2420 = true;
            this.playerName.method_1878(32);
            Button b = new Button(0, 4, 44, buttonWidth, 18, "Crafting: Enabled");
            this.buttons.add(b);
            if (!this.minecraft.level.properties.allowsInventoryCrafting) {
                b.text = "Crafting: Disabled";
            }
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
            String[] scripts = this.getScriptFiles();
            if (scripts != null) {
                int i = 1;
                for (String scriptFile : scripts) {
                    b = new Button(4 + i, 4 + i % 3 * this.width / 3, 112 + i / 3 * 20, 160, 18, scriptFile);
                    this.buttons.add(b);
                    ++i;
                }
            }
        } else if (this.page == 2) {
            for (int i = 0; i < 16; ++i) {
                this.lightLevels[i] = new Textbox(this, this.textManager, 80, 22 + 14 * i, 80, 11, String.format("%.7f", ((ExLevelProperties)this.minecraft.level.getProperties()).getBrightness()[i]));
            }
        } else if (this.page == 3) {
        }
    }

    private String[] getScriptFiles() {
        File scriptDir = new File(((ExLevel)this.minecraft.level).getLevelDir(), "scripts");
        if (scriptDir.exists() && scriptDir.isDirectory()) {
            File[] scriptFiles = scriptDir.listFiles();
            String[] fileNames = new String[scriptFiles.length];
            int i = 0;
            for (File scriptFile : scriptFiles) {
                fileNames[i++] = scriptFile.getName();
            }
            return fileNames;
        }
        return null;
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
                this.minecraft.level.getProperties().allowsInventoryCrafting = !this.minecraft.level.getProperties().allowsInventoryCrafting;
                button.text = "Crafting: Enabled";
                if (!this.minecraft.level.getProperties().allowsInventoryCrafting) {
                    button.text = "Crafting: Disabled";
                }
            }
        } else if (this.page == 1) {
            if (button.id < 3) {
                this.selectedID = button.id;
            } else if (button.id == 3) {
                ((ExLevel)this.world).getScriptHandler().loadScripts();
            } else if (button.id == 4) {
                this.updateScriptFile("");
            } else {
                this.updateScriptFile(button.text);
            }
            this.resetScriptNames();
        } else if (this.page == 2 || this.page == 3) {
        }
    }

    private void updateScriptFile(String file) {
        if (this.selectedID == 0) {
            this.world.getProperties().onNewSaveScript = file;
        } else if (this.selectedID == 1) {
            this.world.getProperties().onLoadScript = file;
        } else if (this.selectedID == 2) {
            this.world.getProperties().onUpdateScript = file;
        }
    }

    private void resetScriptNames() {
        this.setOnNewSave.text = "OnNewSave: " + this.world.getProperties().onNewSaveScript;
        this.setOnLoad.text = "OnLoad: " + this.world.getProperties().onLoadScript;
        this.setOnUpdate.text = "OnUpdate: " + this.world.getProperties().onUpdateScript;
        if (this.selectedID == 0) {
            this.setOnNewSave.text = "OnNewSave (selected): " + this.world.getProperties().onNewSaveScript;
        } else if (this.selectedID == 1) {
            this.setOnLoad.text = "OnLoad (selected): " + this.world.getProperties().onLoadScript;
        } else if (this.selectedID == 2) {
            this.setOnUpdate.text = "OnUpdate (selected): " + this.world.getProperties().onUpdateScript;
        }
    }

    @Override
    protected void keyPressed(char character, int key) {
        if (this.page == 0) {
            if (this.playerName.field_2420) {
                this.playerName.method_1877(character, key);
            }
        } else if (this.page == 2) {
            for (int k = 0; k < 16; ++k) {
                if (!this.lightLevels[k].field_2420 || key != 14 && (character < '0' || character > '9') && character != '.' && character != '\t')
                    continue;
                this.lightLevels[k].method_1877(character, key);
            }
        }
        super.keyPressed(character, key);
        if (key == 1 && this.lightChanged) {
            ((ExWorldRenderer)this.minecraft.worldRenderer).updateAllTheRenderers();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        if (this.page == 0) {
            this.playerName.method_1879(mouseX, mouseY, button);
        } else if (this.page == 2) {
            for (int l = 0; l < 16; ++l) {
                this.lightLevels[l].method_1879(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        if (this.page == 0) {
            this.drawTextWithShadow(this.textManager, "Player Name:", 4, 30, 0xA0A0A0);
            this.playerName.method_1883();
            ((ExLevelProperties)this.world.getProperties()).setPlayerName(this.playerName.method_1876());
            this.minecraft.player.name = this.playerName.method_1876();
        } else if (this.page != 1) {
            if (this.page == 2) {
                for (int k = 0; k < 16; ++k) {
                    this.drawTextWithShadow(this.textManager, String.format("Light Level %d", k), 4, 24 + 14 * k, 0xA0A0A0);
                    this.lightLevels[k].method_1883();
                    try {
                        float value = Float.parseFloat(this.lightLevels[k].method_1876());
                        if ((double) value == Math.floor(((ExLevelProperties)this.minecraft.level.getProperties()).getBrightness()[k] * 1.0E7f) / 1.0E7)
                            continue;
                        ((ExLevelProperties)this.minecraft.level.getProperties()).getBrightness()[k] = value;
                        this.lightChanged = true;
                        continue;
                    } catch (NumberFormatException e) {
                    }
                }
                ((ExLevel)this.minecraft.level).loadBrightness();
            } else if (this.page == 3) {
            }
        }
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
