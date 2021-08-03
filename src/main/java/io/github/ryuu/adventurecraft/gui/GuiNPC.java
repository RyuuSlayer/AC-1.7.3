package io.github.ryuu.adventurecraft.gui;

import java.io.File;

import io.github.ryuu.adventurecraft.entities.EntityNPC;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;

public class GuiNPC extends Screen {
    private final EntityNPC npc;

    private ro npcName;

    private ro chatMsg;

    int selectedID;

    private ke setOnCreated;

    private ke setOnUpdate;

    private ke setOnPathReached;

    private ke setOnAttacked;

    private ke setOnDeath;

    private ke setOnInteraction;

    private int page;

    public GuiNPC(EntityNPC n) {
        this.selectedID = 0;
        this.page = 0;
        this.npc = n;
    }

    public void a() {
        if (this.page == 0) {
            this.npcName.b();
            this.chatMsg.b();
        }
    }

    public void b() {
        this.e.clear();
        int buttonWidth = (this.c - 16) / 4;
        this.e.add(new ke(-20, 4, 0, buttonWidth, 18, "Misc"));
        this.e.add(new ke(-21, 4 + 4 + buttonWidth, 0, buttonWidth, 18, "Script"));
        if (this.page == 0) {
            this.npcName = new ro(this, this.g, 4, 40, 160, 20, this.npc.npcName);
            this.npcName.a = true;
            this.npcName.a(32);
            this.chatMsg = new ro(this, this.g, 4, 80, 160, 20, this.npc.chatMsg);
            this.chatMsg.a = false;
            this.chatMsg.a(32);
            ke b = new ke(-1, 4, 104, 160, 18, "Delete NPC");
            this.e.add(b);
            b = new ke(-2, 170, 24, 160, 18, "Path To Home");
            this.e.add(b);
            if (!this.npc.pathToHome)
                b.e = "Don't Path Home";
            b = new ke(-3, 170, 42, 160, 18, "Track Player");
            this.e.add(b);
            if (!this.npc.trackPlayer)
                b.e = "Don't Track Player";
            b = new ke(-4, 170, 64, 160, 18, "Can be attacked");
            this.e.add(b);
            if (!this.npc.isAttackable)
                b.e = "Can't be attacked";
            File npcSkins = new File(Minecraft.minecraftInstance.f.levelDir, "npc");
            int i = 1;
            buttonWidth = (this.c - 16) / 3;
            b = new ke(0, 4, 124, buttonWidth, 18, "Player Skin");
            this.e.add(b);
            if (npcSkins.isDirectory())
                for (File f : npcSkins.listFiles()) {
                    b = new ke(i, 4 + (buttonWidth + 4) * i % 3, 124 + i / 3 * 20, buttonWidth, 18, f.getName().split("\\.")[0]);
                    this.e.add(b);
                    i++;
                }
        } else if (this.page == 1) {
            this.selectedID = 0;
            this.setOnCreated = new ke(0, 4, 24, "OnCreated (selected): " + this.npc.onCreated);
            this.setOnUpdate = new ke(1, this.c / 2, 24, "OnUpdate: " + this.npc.onUpdate);
            this.setOnPathReached = new ke(2, 4, 46, "OnPathReached: " + this.npc.onPathReached);
            this.setOnAttacked = new ke(3, this.c / 2, 46, "OnAttacked: " + this.npc.onAttacked);
            this.setOnDeath = new ke(4, 4, 68, "OnDeath: " + this.npc.onDeath);
            this.setOnInteraction = new ke(5, this.c / 2, 68, "OnInteraction: " + this.npc.onInteraction);
            this.e.add(this.setOnCreated);
            this.e.add(this.setOnUpdate);
            this.e.add(this.setOnPathReached);
            this.e.add(this.setOnAttacked);
            this.e.add(this.setOnDeath);
            this.e.add(this.setOnInteraction);
            ke b = new ke(6, 4, 90, 200, 20, "Reload Scripts");
            this.e.add(b);
            b = new ke(7, 4, 112, 160, 18, "None");
            this.e.add(b);
            String[] scripts = getScriptFiles();
            if (scripts != null) {
                int i = 1;
                for (String scriptFile : scripts) {
                    b = new ke(7 + i, 6 + i % 3 * this.c / 3, 112 + i / 3 * 20, 160, 18, scriptFile);
                    this.e.add(b);
                    i++;
                }
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

    protected void a(char c, int i) {
        if (this.page == 0) {
            this.npcName.a(c, i);
            this.chatMsg.a(c, i);
        }
        super.a(c, i);
    }

    protected void a(ke guibutton) {
        if (guibutton.f <= -20) {
            this.page = Math.abs(guibutton.f + 20);
            b();
            return;
        }
        if (this.page == 0) {
            if (guibutton.f == -1) {
                this.npc.K();
                Minecraft.minecraftInstance.a(null);
            } else if (guibutton.f == -2) {
                this.npc.pathToHome = !this.npc.pathToHome;
                if (this.npc.pathToHome) {
                    guibutton.e = "Path To Home";
                } else {
                    guibutton.e = "Don't Path Home";
                }
            } else if (guibutton.f == -3) {
                this.npc.trackPlayer = !this.npc.trackPlayer;
                if (this.npc.trackPlayer) {
                    guibutton.e = "Track Player";
                } else {
                    guibutton.e = "Don't Track Player";
                }
            } else if (guibutton.f == -4) {
                this.npc.isAttackable = !this.npc.isAttackable;
                if (this.npc.isAttackable) {
                    guibutton.e = "Can be attacked";
                } else {
                    guibutton.e = "Can't be attacked";
                }
            } else if (guibutton.f == 0) {
                this.npc.O = "/mob/char.png";
            } else if (guibutton.f > 0) {
                File npcSkins = new File(Minecraft.minecraftInstance.f.levelDir, "npc");
                File[] skins = npcSkins.listFiles();
                if (guibutton.f - 1 < skins.length)
                    this.npc.O = "/npc/" + skins[guibutton.f - 1].getName();
            }
        } else if (this.page == 1) {
            if (guibutton.f < 6) {
                this.selectedID = guibutton.f;
            } else if (guibutton.f == 6) {
                this.b.f.scriptHandler.loadScripts();
            } else if (guibutton.f == 7) {
                updateScriptFile("");
            } else {
                updateScriptFile(guibutton.e);
            }
            resetScriptNames();
        }
        this.npc.aI.b((int) this.npc.aM, (int) this.npc.aO).g();
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
        this.setOnCreated.e = "OnNewSave: " + this.npc.onCreated;
        this.setOnUpdate.e = "OnUpdate: " + this.npc.onUpdate;
        this.setOnPathReached.e = "OnPathReached: " + this.npc.onPathReached;
        this.setOnAttacked.e = "OnAttacked: " + this.npc.onAttacked;
        this.setOnDeath.e = "OnDeath: " + this.npc.onDeath;
        this.setOnInteraction.e = "OnInteraction: " + this.npc.onInteraction;
        if (this.selectedID == 0) {
            this.setOnCreated.e = "OnNewSave (selected): " + this.npc.onCreated;
        } else if (this.selectedID == 1) {
            this.setOnUpdate.e = "OnUpdate (selected): " + this.npc.onUpdate;
        } else if (this.selectedID == 2) {
            this.setOnPathReached.e = "OnPathReached (selected): " + this.npc.onPathReached;
        } else if (this.selectedID == 3) {
            this.setOnAttacked.e = "OnAttacked (selected): " + this.npc.onAttacked;
        } else if (this.selectedID == 4) {
            this.setOnDeath.e = "OnDeath (selected): " + this.npc.onDeath;
        } else if (this.selectedID == 5) {
            this.setOnInteraction.e = "OnInteraction (selected): " + this.npc.onInteraction;
        }
    }

    protected void a(int i, int j, int k) {
        super.a(i, j, k);
        if (this.page == 0) {
            this.npcName.a(i, j, k);
            this.chatMsg.a(i, j, k);
        }
    }

    public void a(int i, int j, float f) {
        i();
        if (this.page == 0) {
            b(this.g, "NPC Name", 4, 28, 14737632);
            b(this.g, "Chat Message", 4, 68, 14737632);
            this.npcName.c();
            this.chatMsg.c();
            this.npc.npcName = this.npcName.a();
            this.npc.chatMsg = this.chatMsg.a();
        }
        super.a(i, j, f);
    }

    public static void showUI(EntityNPC n) {
        TileEntityNpcPath.lastEntity = n;
        Minecraft.minecraftInstance.a(new GuiNPC(n));
    }
}