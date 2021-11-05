package io.github.ryuu.adventurecraft.gui;

import java.io.File;

import io.github.ryuu.adventurecraft.entities.EntityNPC;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.Textbox;

public class GuiNPC extends Screen {
    private final EntityNPC npc;
    int selectedID;
    private Textbox npcName;
    private Textbox chatMsg;
    private Button setOnCreated;

    private Button setOnUpdate;

    private Button setOnPathReached;

    private Button setOnAttacked;

    private Button setOnDeath;

    private Button setOnInteraction;

    private int page;

    public GuiNPC(EntityNPC n) {
        this.selectedID = 0;
        this.page = 0;
        this.npc = n;
    }

    public static void showUI(EntityNPC n) {
        TileEntityNpcPath.lastEntity = n;
        Minecraft.minecraftInstance.a(new GuiNPC(n));
    }

    @Override
    public void tick() {
        if (this.page == 0) {
            this.npcName.tick();
            this.chatMsg.tick();
        }
    }

    @Override
    public void init() {
        this.buttons.clear();
        int buttonWidth = (this.width - 16) / 4;
        this.buttons.add(new Button(-20, 4, 0, buttonWidth, 18, "Misc"));
        this.buttons.add(new Button(-21, 4 + 4 + buttonWidth, 0, buttonWidth, 18, "Script"));
        if (this.page == 0) {
            this.npcName = new Textbox(this, this.textManager, 4, 40, 160, 20, this.npc.npcName);
            this.npcName.field_2420 = true;
            this.npcName.method_1878(32);
            this.chatMsg = new Textbox(this, this.textManager, 4, 80, 160, 20, this.npc.chatMsg);
            this.chatMsg.field_2420 = false;
            this.chatMsg.method_1878(32);
            Button b = new Button(-1, 4, 104, 160, 18, "Delete NPC");
            this.buttons.add(b);
            b = new Button(-2, 170, 24, 160, 18, "Path To Home");
            this.buttons.add(b);
            if (!this.npc.pathToHome)
                b.text = "Don't Path Home";
            b = new Button(-3, 170, 42, 160, 18, "Track Player");
            this.buttons.add(b);
            if (!this.npc.trackPlayer)
                b.text = "Don't Track Player";
            b = new Button(-4, 170, 64, 160, 18, "Can be attacked");
            this.buttons.add(b);
            if (!this.npc.isAttackable)
                b.text = "Can't be attacked";
            File npcSkins = new File(Minecraft.minecraftInstance.f.levelDir, "npc");
            int i = 1;
            buttonWidth = (this.width - 16) / 3;
            b = new Button(0, 4, 124, buttonWidth, 18, "Player Skin");
            this.buttons.add(b);
            if (npcSkins.isDirectory())
                for (File f : npcSkins.listFiles()) {
                    b = new Button(i, 4 + (buttonWidth + 4) * i % 3, 124 + i / 3 * 20, buttonWidth, 18, f.getName().split("\\.")[0]);
                    this.buttons.add(b);
                    i++;
                }
        } else if (this.page == 1) {
            this.selectedID = 0;
            this.setOnCreated = new Button(0, 4, 24, "OnCreated (selected): " + this.npc.onCreated);
            this.setOnUpdate = new Button(1, this.width / 2, 24, "OnUpdate: " + this.npc.onUpdate);
            this.setOnPathReached = new Button(2, 4, 46, "OnPathReached: " + this.npc.onPathReached);
            this.setOnAttacked = new Button(3, this.width / 2, 46, "OnAttacked: " + this.npc.onAttacked);
            this.setOnDeath = new Button(4, 4, 68, "OnDeath: " + this.npc.onDeath);
            this.setOnInteraction = new Button(5, this.width / 2, 68, "OnInteraction: " + this.npc.onInteraction);
            this.buttons.add(this.setOnCreated);
            this.buttons.add(this.setOnUpdate);
            this.buttons.add(this.setOnPathReached);
            this.buttons.add(this.setOnAttacked);
            this.buttons.add(this.setOnDeath);
            this.buttons.add(this.setOnInteraction);
            Button b = new Button(6, 4, 90, 200, 20, "Reload Scripts");
            this.buttons.add(b);
            b = new Button(7, 4, 112, 160, 18, "None");
            this.buttons.add(b);
            String[] scripts = getScriptFiles();
            if (scripts != null) {
                int i = 1;
                for (String scriptFile : scripts) {
                    b = new Button(7 + i, 6 + i % 3 * this.width / 3, 112 + i / 3 * 20, 160, 18, scriptFile);
                    this.buttons.add(b);
                    i++;
                }
            }
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
    protected void keyPressed(char c, int i) {
        if (this.page == 0) {
            this.npcName.method_1877(c, i);
            this.chatMsg.method_1877(c, i);
        }
        super.keyPressed(c, i);
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (guibutton.id <= -20) {
            this.page = Math.abs(guibutton.id + 20);
            init();
            return;
        }
        if (this.page == 0) {
            if (guibutton.id == -1) {
                this.npc.K();
                Minecraft.minecraftInstance.a(null);
            } else if (guibutton.id == -2) {
                this.npc.pathToHome = !this.npc.pathToHome;
                if (this.npc.pathToHome) {
                    guibutton.text = "Path To Home";
                } else {
                    guibutton.text = "Don't Path Home";
                }
            } else if (guibutton.id == -3) {
                this.npc.trackPlayer = !this.npc.trackPlayer;
                if (this.npc.trackPlayer) {
                    guibutton.text = "Track Player";
                } else {
                    guibutton.text = "Don't Track Player";
                }
            } else if (guibutton.id == -4) {
                this.npc.isAttackable = !this.npc.isAttackable;
                if (this.npc.isAttackable) {
                    guibutton.text = "Can be attacked";
                } else {
                    guibutton.text = "Can't be attacked";
                }
            } else if (guibutton.id == 0) {
                this.npc.texture = "/mob/char.png";
            } else if (guibutton.id > 0) {
                File npcSkins = new File(Minecraft.minecraftInstance.f.levelDir, "npc");
                File[] skins = npcSkins.listFiles();
                if (guibutton.id - 1 < skins.length)
                    this.npc.texture = "/npc/" + skins[guibutton.id - 1].getName();
            }
        } else if (this.page == 1) {
            if (guibutton.id < 6) {
                this.selectedID = guibutton.id;
            } else if (guibutton.id == 6) {
                this.minecraft.level.scriptHandler.loadScripts();
            } else if (guibutton.id == 7) {
                updateScriptFile("");
            } else {
                updateScriptFile(guibutton.text);
            }
            resetScriptNames();
        }
        this.npc.level.getChunk((int) this.npc.x, (int) this.npc.z).method_885();
    }

    private void updateScriptFile(String file) {
        if (this.selectedID == 0) {
            this.npc.onCreated = file;
            this.npc.runCreatedScript();
        } else if (this.selectedID == 1) {
            this.npc.onUpdate = file;
        } else if (this.selectedID == 2) {
            this.npc.onPathReached = file;
        } else if (this.selectedID == 3) {
            this.npc.onAttacked = file;
        } else if (this.selectedID == 4) {
            this.npc.onDeath = file;
        } else if (this.selectedID == 5) {
            this.npc.onInteraction = file;
        }
    }

    private void resetScriptNames() {
        this.setOnCreated.text = "OnNewSave: " + this.npc.onCreated;
        this.setOnUpdate.text = "OnUpdate: " + this.npc.onUpdate;
        this.setOnPathReached.text = "OnPathReached: " + this.npc.onPathReached;
        this.setOnAttacked.text = "OnAttacked: " + this.npc.onAttacked;
        this.setOnDeath.text = "OnDeath: " + this.npc.onDeath;
        this.setOnInteraction.text = "OnInteraction: " + this.npc.onInteraction;
        if (this.selectedID == 0) {
            this.setOnCreated.text = "OnNewSave (selected): " + this.npc.onCreated;
        } else if (this.selectedID == 1) {
            this.setOnUpdate.text = "OnUpdate (selected): " + this.npc.onUpdate;
        } else if (this.selectedID == 2) {
            this.setOnPathReached.text = "OnPathReached (selected): " + this.npc.onPathReached;
        } else if (this.selectedID == 3) {
            this.setOnAttacked.text = "OnAttacked (selected): " + this.npc.onAttacked;
        } else if (this.selectedID == 4) {
            this.setOnDeath.text = "OnDeath (selected): " + this.npc.onDeath;
        } else if (this.selectedID == 5) {
            this.setOnInteraction.text = "OnInteraction (selected): " + this.npc.onInteraction;
        }
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        if (this.page == 0) {
            this.npcName.method_1879(i, j, k);
            this.chatMsg.method_1879(i, j, k);
        }
    }

    @Override
    public void render(int i, int j, float f) {
        renderBackground();
        if (this.page == 0) {
            drawTextWithShadow(this.textManager, "NPC Name", 4, 28, 14737632);
            drawTextWithShadow(this.textManager, "Chat Message", 4, 68, 14737632);
            this.npcName.method_1883();
            this.chatMsg.method_1883();
            this.npc.npcName = this.npcName.method_1876();
            this.npc.chatMsg = this.chatMsg.method_1876();
        }
        super.render(i, j, f);
    }
}