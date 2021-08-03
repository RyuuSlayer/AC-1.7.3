package io.github.ryuu.adventurecraft.gui;

import java.io.File;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityScript;
import net.minecraft.client.Minecraft;

public class GuiScript extends da {
    TileEntityScript script;

    ke setOnTrigger;

    ke setOnDetrigger;

    ke setOnUpdate;

    int selectedID;

    public GuiScript(TileEntityScript s) {
        this.script = s;
    }

    public void b() {
        this.selectedID = 0;
        this.setOnTrigger = new ke(0, 4, 4, "OnTrigger (selected): " + this.script.onTriggerScriptFile);
        this.setOnDetrigger = new ke(1, 4, 26, "OnDetrigger: " + this.script.onDetriggerScriptFile);
        this.setOnUpdate = new ke(2, 4, 48, "OnUpdate: " + this.script.onUpdateScriptFile);
        this.e.add(this.setOnTrigger);
        this.e.add(this.setOnDetrigger);
        this.e.add(this.setOnUpdate);
        ke b = new ke(3, 4, 70, 200, 20, "Reload Scripts");
        this.e.add(b);
        b = new ke(4, 4, 92, 160, 18, "None");
        this.e.add(b);
        String[] scripts = getScriptFiles();
        if (scripts != null) {
            int i = 1;
            for (String scriptFile : scripts) {
                b = new ke(4 + i, 4 + i % 3 * this.c / 3, 92 + i / 3 * 20, 160, 18, scriptFile);
                this.e.add(b);
                i++;
            }
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

    private void resetNames() {
        this.setOnTrigger.e = "OnTrigger: " + this.script.onTriggerScriptFile;
        this.setOnDetrigger.e = "OnDetrigger: " + this.script.onDetriggerScriptFile;
        this.setOnUpdate.e = "OnUpdate: " + this.script.onUpdateScriptFile;
        if (this.selectedID == 0) {
            this.setOnTrigger.e = "OnTrigger (selected): " + this.script.onTriggerScriptFile;
        } else if (this.selectedID == 1) {
            this.setOnDetrigger.e = "OnDetrigger (selected): " + this.script.onDetriggerScriptFile;
        } else if (this.selectedID == 2) {
            this.setOnUpdate.e = "OnUpdate (selected): " + this.script.onUpdateScriptFile;
        }
    }

    protected void a(ke guibutton) {
        if (guibutton.f < 3) {
            this.selectedID = guibutton.f;
        } else if (guibutton.f == 3) {
            this.script.d.scriptHandler.loadScripts();
        } else if (guibutton.f == 4) {
            updateScriptFile("");
        } else {
            updateScriptFile(guibutton.e);
        }
        resetNames();
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

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        super.a(i, j, f);
    }

    public static void showUI(TileEntityScript s) {
        Minecraft.minecraftInstance.a(new AC_GuiScript(s));
    }

    public boolean c() {
        return false;
    }
}