package io.github.ryuu.adventurecraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.Textbox;

import java.io.File;

public class GuiNPC extends MixinScreen {

    private final EntityNPC npc;
    int selectedID = 0;
    private Textbox npcName;
    private Textbox chatMsg;
    private Button setOnCreated;

    private Button setOnUpdate;

    private Button setOnPathReached;

    private Button setOnAttacked;

    private Button setOnDeath;

    private Button setOnInteraction;

    private int page = 0;

    public GuiNPC(EntityNPC n) {
        this.npc = n;
    }

    public static void showUI(EntityNPC n) {
        TileEntityNpcPath.lastEntity = n;
        Minecraft.minecraftInstance.openScreen(new GuiNPC(n));
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
        block8:
        {
            block7:
            {
                this.buttons.clear();
                int buttonWidth = (this.width - 16) / 4;
                this.buttons.add((Object) new Button(-20, 4, 0, buttonWidth, 18, "Misc"));
                this.buttons.add((Object) new Button(-21, 4 + (4 + buttonWidth), 0, buttonWidth, 18, "Script"));
                if (this.page != 0) break block7;
                this.npcName = new Textbox(this, this.textManager, 4, 40, 160, 20, this.npc.npcName);
                this.npcName.field_2420 = true;
                this.npcName.method_1878(32);
                this.chatMsg = new Textbox(this, this.textManager, 4, 80, 160, 20, this.npc.chatMsg);
                this.chatMsg.field_2420 = false;
                this.chatMsg.method_1878(32);
                Button b = new Button(-1, 4, 104, 160, 18, "Delete NPC");
                this.buttons.add((Object) b);
                b = new Button(-2, 170, 24, 160, 18, "Path To Home");
                this.buttons.add((Object) b);
                if (!this.npc.pathToHome) {
                    b.text = "Don't Path Home";
                }
                b = new Button(-3, 170, 42, 160, 18, "Track Player");
                this.buttons.add((Object) b);
                if (!this.npc.trackPlayer) {
                    b.text = "Don't Track Player";
                }
                b = new Button(-4, 170, 64, 160, 18, "Can be attacked");
                this.buttons.add((Object) b);
                if (!this.npc.isAttackable) {
                    b.text = "Can't be attacked";
                }
                File npcSkins = new File(Minecraft.minecraftInstance.level.levelDir, "npc");
                int i = 1;
                buttonWidth = (this.width - 16) / 3;
                b = new Button(0, 4, 124, buttonWidth, 18, "Player Skin");
                this.buttons.add((Object) b);
                if (!npcSkins.isDirectory()) break block8;
                for (File f : npcSkins.listFiles()) {
                    b = new Button(i, 4 + (buttonWidth + 4) * (i % 3), 124 + i / 3 * 20, buttonWidth, 18, f.getName().split("\\.")[0]);
                    this.buttons.add((Object) b);
                    ++i;
                }
                break block8;
            }
            if (this.page == 1) {
                this.selectedID = 0;
                this.setOnCreated = new Button(0, 4, 24, "OnCreated (selected): " + this.npc.onCreated);
                this.setOnUpdate = new Button(1, this.width / 2, 24, "OnUpdate: " + this.npc.onUpdate);
                this.setOnPathReached = new Button(2, 4, 46, "OnPathReached: " + this.npc.onPathReached);
                this.setOnAttacked = new Button(3, this.width / 2, 46, "OnAttacked: " + this.npc.onAttacked);
                this.setOnDeath = new Button(4, 4, 68, "OnDeath: " + this.npc.onDeath);
                this.setOnInteraction = new Button(5, this.width / 2, 68, "OnInteraction: " + this.npc.onInteraction);
                this.buttons.add((Object) this.setOnCreated);
                this.buttons.add((Object) this.setOnUpdate);
                this.buttons.add((Object) this.setOnPathReached);
                this.buttons.add((Object) this.setOnAttacked);
                this.buttons.add((Object) this.setOnDeath);
                this.buttons.add((Object) this.setOnInteraction);
                Button b = new Button(6, 4, 90, 200, 20, "Reload Scripts");
                this.buttons.add((Object) b);
                b = new Button(7, 4, 112, 160, 18, "None");
                this.buttons.add((Object) b);
                String[] scripts = this.getScriptFiles();
                if (scripts != null) {
                    int i = 1;
                    for (String scriptFile : scripts) {
                        b = new Button(7 + i, 6 + i % 3 * this.width / 3, 112 + i / 3 * 20, 160, 18, scriptFile);
                        this.buttons.add((Object) b);
                        ++i;
                    }
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
            for (File scriptFile : scriptFiles) {
                fileNames[i++] = scriptFile.getName();
            }
            return fileNames;
        }
        return null;
    }

    @Override
    protected void keyPressed(char character, int key) {
        if (this.page == 0) {
            this.npcName.method_1877(character, key);
            this.chatMsg.method_1877(character, key);
        }
        super.keyPressed(character, key);
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id <= -20) {
            this.page = Math.abs(button.id + 20);
            this.init();
            return;
        }
        if (this.page == 0) {
            File npcSkins;
            File[] skins;
            if (button.id == -1) {
                this.npc.remove();
                Minecraft.minecraftInstance.openScreen(null);
            } else if (button.id == -2) {
                boolean bl = this.npc.pathToHome = !this.npc.pathToHome;
                button.text = this.npc.pathToHome ? "Path To Home" : "Don't Path Home";
            } else if (button.id == -3) {
                boolean bl = this.npc.trackPlayer = !this.npc.trackPlayer;
                button.text = this.npc.trackPlayer ? "Track Player" : "Don't Track Player";
            } else if (button.id == -4) {
                boolean bl = this.npc.isAttackable = !this.npc.isAttackable;
                button.text = this.npc.isAttackable ? "Can be attacked" : "Can't be attacked";
            } else if (button.id == 0) {
                this.npc.texture = "/mob/char.png";
            } else if (button.id > 0 && button.id - 1 < (skins = (npcSkins = new File(Minecraft.minecraftInstance.level.levelDir, "npc")).listFiles()).length) {
                this.npc.texture = "/npc/" + skins[button.id - 1].getName();
            }
        } else if (this.page == 1) {
            if (button.id < 6) {
                this.selectedID = button.id;
            } else if (button.id == 6) {
                this.minecraft.level.scriptHandler.loadScripts();
            } else if (button.id == 7) {
                this.updateScriptFile("");
            } else {
                this.updateScriptFile(button.text);
            }
            this.resetScriptNames();
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
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        if (this.page == 0) {
            this.npcName.method_1879(mouseX, mouseY, button);
            this.chatMsg.method_1879(mouseX, mouseY, button);
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        if (this.page == 0) {
            this.drawTextWithShadow(this.textManager, "NPC Name", 4, 28, 0xE0E0E0);
            this.drawTextWithShadow(this.textManager, "Chat Message", 4, 68, 0xE0E0E0);
            this.npcName.method_1883();
            this.chatMsg.method_1883();
            this.npc.npcName = this.npcName.method_1876();
            this.npc.chatMsg = this.chatMsg.method_1876();
        }
        super.render(mouseX, mouseY, delta);
    }
}
