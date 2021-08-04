package io.github.ryuu.adventurecraft.gui;

import java.io.File;
import java.util.ArrayList;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMobSpawner;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.scripting.EntityDescriptions;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemType;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;

public class GuiMobSpawner extends Screen {
    private final TileEntityMobSpawner mobSpawner;

    public GuiMobSpawner(TileEntityMobSpawner ms) {
        this.mobSpawner = ms;
    }

    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
        this.buttons.clear();
        this.spawnCountSlider = new GuiSlider2(50, 4, 44, 10, String.format("Spawn Count: %d", this.mobSpawner.spawnNumber), this.mobSpawner.spawnNumber / 15.0F);
        this.respawnSlider = new GuiSlider2(51, this.width / 2, 44, 10, String.format("Respawn Delay: %.1f", this.mobSpawner.respawnDelay / 20.0F), this.mobSpawner.respawnDelay / 12000.0F);
        this.spawnCountSlider.width = 200;
        this.respawnSlider.width = 200;
        this.buttons.add(this.spawnCountSlider);
        this.buttons.add(this.respawnSlider);
        Button b = new Button(53, this.width / 2, 4, 200, 18, "Spawn On Trigger");
        if (!this.mobSpawner.spawnOnTrigger)
            if (this.mobSpawner.spawnOnDetrigger) {
                b.text = "Spawn on Detrigger";
            } else {
                b.text = "Spawn on Timer";
            }
        this.buttons.add(b);
        b = new Button(55, this.width / 2, 24, 200, 18, String.format("Spawn: (%d, %d, %d), (%d, %d, %d)", this.mobSpawner.minSpawnVec.x, this.mobSpawner.minSpawnVec.y, this.mobSpawner.minSpawnVec.z, this.mobSpawner.maxSpawnVec.x, this.mobSpawner.maxSpawnVec.y, this.mobSpawner.maxSpawnVec.z));
        this.buttons.add(b);
        int buttonWidth = (this.width - 16) / 4;
        this.buttons.add(new Button(57, 4, 64, buttonWidth, 18, "Select Spawn"));
        this.buttons.add(new Button(58, 4 + 4 + buttonWidth, 64, buttonWidth, 18, "Select Drops"));
        this.buttons.add(new Button(59, 4 + 2 * (4 + buttonWidth), 64, buttonWidth, 18, "Select Triggers"));
        this.buttons.add(new Button(60, 4 + 3 * (4 + buttonWidth), 64, buttonWidth, 18, "Select Scripts"));
        if (this.displayScreen == 0) {
            int i = 0;
            String itemToSpawn = "Spawn Item/Block: None";
            if (ItemType.byId[this.mobSpawner.spawnID] != null)
                itemToSpawn = String.format("Spawn Item/Block: %s", ItemType.byId[this.mobSpawner.spawnID].getTranslationKey());
            this.buttons.add(new Button(56, 2, 84, 200, 14, itemToSpawn));
            for (String entityID : entityTypes) {
                this.buttons.add(new Button(i, 2 + (buttonWidth + 4) * i % 4, (i / 4 + 1) * 14 + 84, buttonWidth, 13, entityID));
                i++;
            }
            for (String descName : EntityDescriptions.getDescriptions()) {
                this.buttons.add(new Button(i, 2 + (buttonWidth + 4) * i % 4, (i / 4 + 1) * 14 + 84, buttonWidth, 13, descName + " (Scripted)"));
                i++;
            }
        } else if (this.displayScreen == 1) {
            b = new Button(52, 4, 84, 200, 18, "Drop Nothing");
            if (this.mobSpawner.dropItem > 0)
                if (this.mobSpawner.dropItem == Items.doorKey.id) {
                    b.text = "Drop key";
                } else if (this.mobSpawner.dropItem == Items.heartContainer.id) {
                    b.text = "Drop heart container";
                } else if (this.mobSpawner.dropItem == Items.bossKey.id) {
                    b.text = "Drop boss key";
                }
            this.buttons.add(b);
        } else if (this.displayScreen == 2) {
            for (int trigger = 0; trigger < 8; trigger++) {
                String isSet = ": Not Set";
                if (this.mobSpawner.isTriggerSet(trigger))
                    isSet = ": Set";
                if (trigger < 4) {
                    b = new Button(70 + trigger, 4, 84 + trigger * 19, 200, 18, "Trigger ".concat(Integer.toString(trigger)).concat(isSet));
                } else {
                    b = new Button(70 + trigger, this.width / 2, 84 + (trigger - 4) * 19, 200, 18, "OnDeath Trigger ".concat(Integer.toString(trigger)).concat(isSet));
                }
                this.buttons.add(b);
            }
        } else if (this.displayScreen == 3) {
            this.selectedID = 0;
            this.setOnTrigger = new Button(61, 4, 84, "OnSpawn (selected): " + this.mobSpawner.onTriggerScriptFile);
            this.setOnDetrigger = new Button(62, this.width / 2, 84, "OnDeath: " + this.mobSpawner.onDetriggerScriptFile);
            this.setOnUpdate = new Button(63, 4, 104, "OnUpdate: " + this.mobSpawner.onUpdateScriptFile);
            this.buttons.add(this.setOnTrigger);
            this.buttons.add(this.setOnDetrigger);
            this.buttons.add(this.setOnUpdate);
            this.buttons.add(new Button(64, this.width / 2, 104, 200, 20, "Reload Scripts"));
            this.buttons.add(new Button(65, 4, 124, (this.width - 12) / 3, 18, "None"));
            String[] scripts = getScriptFiles();
            if (scripts != null) {
                int i = 1;
                for (String scriptFile : scripts) {
                    b = new Button(119 + i, 4 + i % 3 * (this.width - 8) / 3, 124 + i / 3 * 20, (this.width - 12) / 3, 18, scriptFile);
                    this.buttons.add(b);
                    i++;
                }
            }
        }
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (guibutton.id < 50) {
            this.mobSpawner.entityID = guibutton.text;
        } else if (guibutton.id == 52) {
            if (this.mobSpawner.dropItem == Items.doorKey.id) {
                guibutton.text = "Drop heart container";
                this.mobSpawner.dropItem = Items.heartContainer.id;
            } else if (this.mobSpawner.dropItem == Items.heartContainer.id) {
                guibutton.text = "Drop boss key";
                this.mobSpawner.dropItem = Items.bossKey.id;
            } else if (this.mobSpawner.dropItem == Items.bossKey.id) {
                guibutton.text = "Drop nothing";
                this.mobSpawner.dropItem = 0;
            } else {
                guibutton.text = "Drop key";
                this.mobSpawner.dropItem = Items.doorKey.id;
            }
        } else if (guibutton.id == 53) {
            if (this.mobSpawner.spawnOnTrigger) {
                this.mobSpawner.spawnOnTrigger = false;
                this.mobSpawner.spawnOnDetrigger = true;
                guibutton.text = "Spawn On Detrigger";
            } else if (this.mobSpawner.spawnOnDetrigger) {
                this.mobSpawner.spawnOnDetrigger = false;
                guibutton.text = "Spawn On Timer";
            } else {
                this.mobSpawner.spawnOnTrigger = true;
                guibutton.text = "Spawn on Trigger";
            }
        } else if (guibutton.id == 55) {
            this.mobSpawner.setSpawnVec();
            guibutton.text = String.format("Spawn: (%d, %d, %d), (%d, %d, %d)", this.mobSpawner.minSpawnVec.x, this.mobSpawner.minSpawnVec.y, this.mobSpawner.minSpawnVec.z, this.mobSpawner.maxSpawnVec.x, this.mobSpawner.maxSpawnVec.y, this.mobSpawner.maxSpawnVec.z);
        } else if (guibutton.id == 56) {
            if (this.minecraft.player.inventory.getHeldItem() != null) {
                this.mobSpawner.spawnID = (this.minecraft.player.inventory.getHeldItem()).itemId;
                this.mobSpawner.spawnMeta = this.minecraft.player.inventory.getHeldItem().getDamage();
            } else {
                this.mobSpawner.spawnID = 0;
                this.mobSpawner.spawnMeta = 0;
            }
            String itemToSpawn = "Spawn Item/Block: None";
            if (ItemType.byId[this.mobSpawner.spawnID] != null)
                itemToSpawn = String.format("Spawn Item/Block: %s", ItemType.byId[this.mobSpawner.spawnID].getTranslationKey());
            guibutton.text = itemToSpawn;
        } else if (guibutton.id >= 57 && guibutton.id <= 60) {
            this.displayScreen = guibutton.id - 57;
            init();
        } else if (guibutton.id >= 61 && guibutton.id <= 63) {
            this.selectedID = guibutton.id - 61;
            resetNames();
        } else if (guibutton.id == 64) {
            this.mobSpawner.level.scriptHandler.loadScripts();
            resetNames();
        } else if (guibutton.id == 65) {
            updateScriptFile("");
            resetNames();
        } else if (guibutton.id >= 120) {
            updateScriptFile(guibutton.text);
            resetNames();
        } else if (guibutton.id >= 70 && guibutton.id < 120) {
            int i = guibutton.id - 70;
            if (this.mobSpawner.isTriggerSet(i)) {
                this.mobSpawner.setCursor(i);
                this.mobSpawner.clearTrigger(i);
                guibutton.text = "Trigger ".concat(Integer.toString(i)).concat(": Not Set");
            } else {
                this.mobSpawner.setTrigger(i);
                guibutton.text = "Trigger ".concat(Integer.toString(i)).concat(": Set");
            }
        }
    }

    @Override
    public void render(int i, int j, float f) {
        fill(0, 0, this.width, this.height, -2147483648);
        drawTextWithShadow(this.textManager, String.format("Entity Spawn: %s", this.mobSpawner.entityID), 4, 4, 14737632);
        drawTextWithShadow(this.textManager, String.format("Entities Alive: %d", this.mobSpawner.getNumAlive()), 4, 14, 14737632);
        drawTextWithShadow(this.textManager, String.format("Respawn In: %.1fs", this.mobSpawner.delay / 20.0F), 4, 24, 14737632);
        if (this.mobSpawner.hasDroppedItem) {
            drawTextWithShadow(this.textManager, "Has Dropped An Item", 4, 34, 14737632);
        } else {
            drawTextWithShadow(this.textManager, "Has Not Dropped An Item", 4, 34, 14737632);
        }
        this.mobSpawner.spawnNumber = (int) (this.spawnCountSlider.sliderValue * 15.0F + 0.5F);
        this.spawnCountSlider.text = String.format("Spawn Count: %d", this.mobSpawner.spawnNumber);
        this.mobSpawner.respawnDelay = (int) (this.respawnSlider.sliderValue * 12000.0F + 0.5F);
        this.respawnSlider.text = String.format("Respawn Delay: %.1fs", this.mobSpawner.respawnDelay / 20.0F);
        super.render(i, j, f);
        this.mobSpawner.level.getChunk(this.mobSpawner.x, this.mobSpawner.z).method_885();
    }

    public static void showUI(TileEntityMobSpawner ms) {
        Minecraft.minecraftInstance.a(new GuiMobSpawner(ms));
    }

    public boolean c() {
        return false;
    }

    private void resetNames() {
        this.setOnTrigger.text = "OnSpawn: " + this.mobSpawner.onTriggerScriptFile;
        this.setOnDetrigger.text = "OnDeath: " + this.mobSpawner.onDetriggerScriptFile;
        this.setOnUpdate.text = "OnUpdate: " + this.mobSpawner.onUpdateScriptFile;
        if (this.selectedID == 0) {
            this.setOnTrigger.text = "OnSpawn (selected): " + this.mobSpawner.onTriggerScriptFile;
        } else if (this.selectedID == 1) {
            this.setOnDetrigger.text = "OnDeath (selected): " + this.mobSpawner.onDetriggerScriptFile;
        } else if (this.selectedID == 2) {
            this.setOnUpdate.text = "OnUpdate (selected): " + this.mobSpawner.onUpdateScriptFile;
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

    private static final ArrayList<String> entityTypes = new ArrayList<>();

    private GuiSlider2 spawnCountSlider;

    private GuiSlider2 respawnSlider;

    private String newSliderString;

    private int displayScreen;

    Button setOnTrigger;

    Button setOnDetrigger;

    Button setOnUpdate;

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