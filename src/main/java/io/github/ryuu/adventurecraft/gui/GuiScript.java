package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityScript;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;

import java.io.File;

public class GuiScript extends Screen {

    TileEntityScript script;

    Button setOnTrigger;

    Button setOnDetrigger;

    Button setOnUpdate;

    int selectedID;

    public GuiScript(TileEntityScript s) {
        this.script = s;
    }

    public static void showUI(TileEntityScript s) {
        AccessMinecraft.getInstance().openScreen(new GuiScript(s));
    }

    @Override
    public void init() {
        this.selectedID = 0;
        this.setOnTrigger = new Button(0, 4, 4, "OnTrigger (selected): " + this.script.onTriggerScriptFile);
        this.setOnDetrigger = new Button(1, 4, 26, "OnDetrigger: " + this.script.onDetriggerScriptFile);
        this.setOnUpdate = new Button(2, 4, 48, "OnUpdate: " + this.script.onUpdateScriptFile);
        this.buttons.add(this.setOnTrigger);
        this.buttons.add(this.setOnDetrigger);
        this.buttons.add(this.setOnUpdate);
        Button b = new Button(3, 4, 70, 200, 20, "Reload Scripts");
        this.buttons.add(b);
        b = new Button(4, 4, 92, 160, 18, "None");
        this.buttons.add(b);
        String[] scripts = this.getScriptFiles();
        if (scripts != null) {
            int i = 1;
            for (String scriptFile : scripts) {
                b = new Button(4 + i, 4 + i % 3 * this.width / 3, 92 + i / 3 * 20, 160, 18, scriptFile);
                this.buttons.add(b);
                ++i;
            }
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

    private void resetNames() {
        this.setOnTrigger.text = "OnTrigger: " + this.script.onTriggerScriptFile;
        this.setOnDetrigger.text = "OnDetrigger: " + this.script.onDetriggerScriptFile;
        this.setOnUpdate.text = "OnUpdate: " + this.script.onUpdateScriptFile;
        if (this.selectedID == 0) {
            this.setOnTrigger.text = "OnTrigger (selected): " + this.script.onTriggerScriptFile;
        } else if (this.selectedID == 1) {
            this.setOnDetrigger.text = "OnDetrigger (selected): " + this.script.onDetriggerScriptFile;
        } else if (this.selectedID == 2) {
            this.setOnUpdate.text = "OnUpdate (selected): " + this.script.onUpdateScriptFile;
        }
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id < 3) {
            this.selectedID = button.id;
        } else if (button.id == 3) {
            this.script.level.scriptHandler.loadScripts();
        } else if (button.id == 4) {
            this.updateScriptFile("");
        } else {
            this.updateScriptFile(button.text);
        }
        this.resetNames();
    }

    private void updateScriptFile(String file) {
        if (this.selectedID == 0) {
            this.script.onTriggerScriptFile = file;
        } else if (this.selectedID == 1) {
            this.script.onDetriggerScriptFile = file;
        } else if (this.selectedID == 2) {
            this.script.onUpdateScriptFile = file;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
