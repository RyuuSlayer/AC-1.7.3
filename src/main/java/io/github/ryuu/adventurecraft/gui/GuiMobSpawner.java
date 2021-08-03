package io.github.ryuu.adventurecraft.gui;

import java.io.File;
import java.util.ArrayList;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMobSpawner;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.scripting.EntityDescriptions;
import net.minecraft.client.Minecraft;

public class GuiMobSpawner extends da {
    private TileEntityMobSpawner mobSpawner;

    public GuiMobSpawner(TileEntityMobSpawner ms) {
        this.mobSpawner = ms;
    }

    public void a() {}

    public void b() {
        this.e.clear();
        this.spawnCountSlider = new GuiSlider2(50, 4, 44, 10, String.format("Spawn Count: %d", new Object[] { Integer.valueOf(this.mobSpawner.spawnNumber) }), this.mobSpawner.spawnNumber / 15.0F);
        this.respawnSlider = new GuiSlider2(51, this.c / 2, 44, 10, String.format("Respawn Delay: %.1f", new Object[] { Float.valueOf(this.mobSpawner.respawnDelay / 20.0F) }), this.mobSpawner.respawnDelay / 12000.0F);
        this.spawnCountSlider.a = 200;
        this.respawnSlider.a = 200;
        this.e.add(this.spawnCountSlider);
        this.e.add(this.respawnSlider);
        ke b = new ke(53, this.c / 2, 4, 200, 18, "Spawn On Trigger");
        if (!this.mobSpawner.spawnOnTrigger)
            if (this.mobSpawner.spawnOnDetrigger) {
                b.e = "Spawn on Detrigger";
            } else {
                b.e = "Spawn on Timer";
            }
        this.e.add(b);
        b = new ke(55, this.c / 2, 24, 200, 18, String.format("Spawn: (%d, %d, %d), (%d, %d, %d)", new Object[] { Integer.valueOf(this.mobSpawner.minSpawnVec.x), Integer.valueOf(this.mobSpawner.minSpawnVec.y), Integer.valueOf(this.mobSpawner.minSpawnVec.z), Integer.valueOf(this.mobSpawner.maxSpawnVec.x), Integer.valueOf(this.mobSpawner.maxSpawnVec.y), Integer.valueOf(this.mobSpawner.maxSpawnVec.z) }));
        this.e.add(b);
        int buttonWidth = (this.c - 16) / 4;
        this.e.add(new ke(57, 4, 64, buttonWidth, 18, "Select Spawn"));
        this.e.add(new ke(58, 4 + 4 + buttonWidth, 64, buttonWidth, 18, "Select Drops"));
        this.e.add(new ke(59, 4 + 2 * (4 + buttonWidth), 64, buttonWidth, 18, "Select Triggers"));
        this.e.add(new ke(60, 4 + 3 * (4 + buttonWidth), 64, buttonWidth, 18, "Select Scripts"));
        if (this.displayScreen == 0) {
            int i = 0;
            String itemToSpawn = "Spawn Item/Block: None";
            if (gm.c[this.mobSpawner.spawnID] != null)
                itemToSpawn = String.format("Spawn Item/Block: %s", new Object[] { gm.c[this.mobSpawner.spawnID].a() });
            this.e.add(new ke(56, 2, 84, 200, 14, itemToSpawn));
            for (String entityID : entityTypes) {
                this.e.add(new ke(i, 2 + (buttonWidth + 4) * i % 4, (i / 4 + 1) * 14 + 84, buttonWidth, 13, entityID));
                i++;
            }
            for (String descName : EntityDescriptions.getDescriptions()) {
                this.e.add(new ke(i, 2 + (buttonWidth + 4) * i % 4, (i / 4 + 1) * 14 + 84, buttonWidth, 13, descName + " (Scripted)"));
                i++;
            }
        } else if (this.displayScreen == 1) {
            b = new ke(52, 4, 84, 200, 18, "Drop Nothing");
            if (this.mobSpawner.dropItem > 0)
                if (this.mobSpawner.dropItem == Items.doorKey.bf) {
                    b.e = "Drop key";
                } else if (this.mobSpawner.dropItem == Items.heartContainer.bf) {
                    b.e = "Drop heart container";
                } else if (this.mobSpawner.dropItem == Items.bossKey.bf) {
                    b.e = "Drop boss key";
                }
            this.e.add(b);
        } else if (this.displayScreen == 2) {
            for (int trigger = 0; trigger < 8; trigger++) {
                String isSet = ": Not Set";
                if (this.mobSpawner.isTriggerSet(trigger))
                    isSet = ": Set";
                if (trigger < 4) {
                    b = new ke(70 + trigger, 4, 84 + trigger * 19, 200, 18, "Trigger ".concat(Integer.toString(trigger)).concat(isSet));
                } else {
                    b = new ke(70 + trigger, this.c / 2, 84 + (trigger - 4) * 19, 200, 18, "OnDeath Trigger ".concat(Integer.toString(trigger)).concat(isSet));
                }
                this.e.add(b);
            }
        } else if (this.displayScreen == 3) {
            this.selectedID = 0;
            this.setOnTrigger = new ke(61, 4, 84, "OnSpawn (selected): " + this.mobSpawner.onTriggerScriptFile);
            this.setOnDetrigger = new ke(62, this.c / 2, 84, "OnDeath: " + this.mobSpawner.onDetriggerScriptFile);
            this.setOnUpdate = new ke(63, 4, 104, "OnUpdate: " + this.mobSpawner.onUpdateScriptFile);
            this.e.add(this.setOnTrigger);
            this.e.add(this.setOnDetrigger);
            this.e.add(this.setOnUpdate);
            this.e.add(new ke(64, this.c / 2, 104, 200, 20, "Reload Scripts"));
            this.e.add(new ke(65, 4, 124, (this.c - 12) / 3, 18, "None"));
            String[] scripts = getScriptFiles();
            if (scripts != null) {
                int i = 1;
                for (String scriptFile : scripts) {
                    b = new ke(119 + i, 4 + i % 3 * (this.c - 8) / 3, 124 + i / 3 * 20, (this.c - 12) / 3, 18, scriptFile);
                    this.e.add(b);
                    i++;
                }
            }
        }
    }

    protected void a(ke guibutton) {
        if (guibutton.f < 50) {
            this.mobSpawner.entityID = guibutton.e;
        } else if (guibutton.f == 52) {
            if (this.mobSpawner.dropItem == Items.doorKey.bf) {
                guibutton.e = "Drop heart container";
                this.mobSpawner.dropItem = Items.heartContainer.bf;
            } else if (this.mobSpawner.dropItem == Items.heartContainer.bf) {
                guibutton.e = "Drop boss key";
                this.mobSpawner.dropItem = Items.bossKey.bf;
            } else if (this.mobSpawner.dropItem == Items.bossKey.bf) {
                guibutton.e = "Drop nothing";
                this.mobSpawner.dropItem = 0;
            } else {
                guibutton.e = "Drop key";
                this.mobSpawner.dropItem = Items.doorKey.bf;
            }
        } else if (guibutton.f == 53) {
            if (this.mobSpawner.spawnOnTrigger) {
                this.mobSpawner.spawnOnTrigger = false;
                this.mobSpawner.spawnOnDetrigger = true;
                guibutton.e = "Spawn On Detrigger";
            } else if (this.mobSpawner.spawnOnDetrigger) {
                this.mobSpawner.spawnOnDetrigger = false;
                guibutton.e = "Spawn On Timer";
            } else {
                this.mobSpawner.spawnOnTrigger = true;
                guibutton.e = "Spawn on Trigger";
            }
        } else if (guibutton.f == 55) {
            this.mobSpawner.setSpawnVec();
            guibutton.e = String.format("Spawn: (%d, %d, %d), (%d, %d, %d)", new Object[] { Integer.valueOf(this.mobSpawner.minSpawnVec.x), Integer.valueOf(this.mobSpawner.minSpawnVec.y), Integer.valueOf(this.mobSpawner.minSpawnVec.z), Integer.valueOf(this.mobSpawner.maxSpawnVec.x), Integer.valueOf(this.mobSpawner.maxSpawnVec.y), Integer.valueOf(this.mobSpawner.maxSpawnVec.z) });
        } else if (guibutton.f == 56) {
            if (this.b.h.c.b() != null) {
                this.mobSpawner.spawnID = (this.b.h.c.b()).c;
                this.mobSpawner.spawnMeta = this.b.h.c.b().i();
            } else {
                this.mobSpawner.spawnID = 0;
                this.mobSpawner.spawnMeta = 0;
            }
            String itemToSpawn = "Spawn Item/Block: None";
            if (gm.c[this.mobSpawner.spawnID] != null)
                itemToSpawn = String.format("Spawn Item/Block: %s", new Object[] { gm.c[this.mobSpawner.spawnID].a() });
            guibutton.e = itemToSpawn;
        } else if (guibutton.f >= 57 && guibutton.f <= 60) {
            this.displayScreen = guibutton.f - 57;
            b();
        } else if (guibutton.f >= 61 && guibutton.f <= 63) {
            this.selectedID = guibutton.f - 61;
            resetNames();
        } else if (guibutton.f == 64) {
            this.mobSpawner.d.scriptHandler.loadScripts();
            resetNames();
        } else if (guibutton.f == 65) {
            updateScriptFile("");
            resetNames();
        } else if (guibutton.f >= 120) {
            updateScriptFile(guibutton.e);
            resetNames();
        } else if (guibutton.f >= 70 && guibutton.f < 120) {
            int i = guibutton.f - 70;
            if (this.mobSpawner.isTriggerSet(i)) {
                this.mobSpawner.setCursor(i);
                this.mobSpawner.clearTrigger(i);
                guibutton.e = "Trigger ".concat(Integer.toString(i)).concat(": Not Set");
            } else {
                this.mobSpawner.setTrigger(i);
                guibutton.e = "Trigger ".concat(Integer.toString(i)).concat(": Set");
            }
        }
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        b(this.g, String.format("Entity Spawn: %s", new Object[] { this.mobSpawner.entityID }), 4, 4, 14737632);
        b(this.g, String.format("Entities Alive: %d", new Object[] { Integer.valueOf(this.mobSpawner.getNumAlive()) }), 4, 14, 14737632);
        b(this.g, String.format("Respawn In: %.1fs", new Object[] { Float.valueOf(this.mobSpawner.delay / 20.0F) }), 4, 24, 14737632);
        if (this.mobSpawner.hasDroppedItem) {
            b(this.g, "Has Dropped An Item", 4, 34, 14737632);
        } else {
            b(this.g, "Has Not Dropped An Item", 4, 34, 14737632);
        }
        this.mobSpawner.spawnNumber = (int)(this.spawnCountSlider.sliderValue * 15.0F + 0.5F);
        this.spawnCountSlider.e = String.format("Spawn Count: %d", new Object[] { Integer.valueOf(this.mobSpawner.spawnNumber) });
        this.mobSpawner.respawnDelay = (int)(this.respawnSlider.sliderValue * 12000.0F + 0.5F);
        this.respawnSlider.e = String.format("Respawn Delay: %.1fs", new Object[] { Float.valueOf(this.mobSpawner.respawnDelay / 20.0F) });
        super.a(i, j, f);
        this.mobSpawner.d.b(this.mobSpawner.e, this.mobSpawner.g).g();
    }

    public static void showUI(TileEntityMobSpawner ms) {
        Minecraft.minecraftInstance.a(new AC_GuiMobSpawner(ms));
    }

    public boolean c() {
        return false;
    }

    private void resetNames() {
        this.setOnTrigger.e = "OnSpawn: " + this.mobSpawner.onTriggerScriptFile;
        this.setOnDetrigger.e = "OnDeath: " + this.mobSpawner.onDetriggerScriptFile;
        this.setOnUpdate.e = "OnUpdate: " + this.mobSpawner.onUpdateScriptFile;
        if (this.selectedID == 0) {
            this.setOnTrigger.e = "OnSpawn (selected): " + this.mobSpawner.onTriggerScriptFile;
        } else if (this.selectedID == 1) {
            this.setOnDetrigger.e = "OnDeath (selected): " + this.mobSpawner.onDetriggerScriptFile;
        } else if (this.selectedID == 2) {
            this.setOnUpdate.e = "OnUpdate (selected): " + this.mobSpawner.onUpdateScriptFile;
        }
    }

    private void updateScriptFile(String file) {
        if (this.selectedID == 0) {
            this.mobSpawner.onTriggerScriptFile = file;
        } else if (this.selectedID == 1) {
            this.mobSpawner.onDetriggerScriptFile = file;
        } else if (this.selectedID == 2) {
            this.mobSpawner.onUpdateScriptFile = file;
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

    private static ArrayList<String> entityTypes = new ArrayList<String>();

    private GuiSlider2 spawnCountSlider;

    private GuiSlider2 respawnSlider;

    private String newSliderString;

    private int displayScreen;

    ke setOnTrigger;

    ke setOnDetrigger;

    ke setOnUpdate;

    int selectedID;

    static {
        entityTypes.add("Bat");
        entityTypes.add("Boat");
        entityTypes.add("Chicken");
        entityTypes.add("Cow");
        entityTypes.add("Creeper");
        entityTypes.add("Falling Block");
        entityTypes.add("Ghast");
        entityTypes.add("Giant");
        entityTypes.add("Item");
        entityTypes.add("Minecart");
        entityTypes.add("Minecart Chest");
        entityTypes.add("Minecart Furnace");
        entityTypes.add("Pig Zombie");
        entityTypes.add("Pig");
        entityTypes.add("Primed Tnt");
        entityTypes.add("Rat");
        entityTypes.add("Sheep");
        entityTypes.add("Skeleton");
        entityTypes.add("Skeleton Boss");
        entityTypes.add("Skeleton Rifle");
        entityTypes.add("Skeleton Shotgun");
        entityTypes.add("Skeleton Sword");
        entityTypes.add("Slime");
        entityTypes.add("Slime Size: 1");
        entityTypes.add("Slime Size: 2");
        entityTypes.add("Slime Size: 4");
        entityTypes.add("Slime Size: 8");
        entityTypes.add("Slime Size: 16");
        entityTypes.add("Squid");
        entityTypes.add("Spider");
        entityTypes.add("Spider Skeleton");
        entityTypes.add("Spider Skeleton Sword");
        entityTypes.add("Wolf");
        entityTypes.add("Wolf (Angry)");
        entityTypes.add("Wolf (Tame)");
        entityTypes.add("Zombie");
        entityTypes.add("ZombiePistol");
    }
}