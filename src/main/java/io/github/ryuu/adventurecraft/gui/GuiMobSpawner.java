package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMobSpawner;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.scripting.EntityDescriptions;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.item.ItemType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GuiMobSpawner extends Screen {

    private static final ArrayList<String> entityTypes = new ArrayList<>();

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

    private final TileEntityMobSpawner mobSpawner;
    Button setOnTrigger;
    Button setOnDetrigger;
    Button setOnUpdate;
    int selectedID;
    private GuiSlider2 spawnCountSlider;
    private GuiSlider2 respawnSlider;
    private int displayScreen;

    public GuiMobSpawner(TileEntityMobSpawner ms) {
        this.mobSpawner = ms;
    }

    public static void showUI(TileEntityMobSpawner ms) {
        AccessMinecraft.getInstance().openScreen(new GuiMobSpawner(ms));
    }

    @Override
    public void init() {
        List<Button> buttons = (List<Button>) this.buttons;

        buttons.clear();
        this.spawnCountSlider = new GuiSlider2(50, 4, 44, 10, String.format("Spawn Count: %d", this.mobSpawner.spawnNumber), (float) this.mobSpawner.spawnNumber / 15.0f);
        this.respawnSlider = new GuiSlider2(51, this.width / 2, 44, 10, String.format("Respawn Delay: %.1f", (float) this.mobSpawner.respawnDelay / 20.0f), (float) this.mobSpawner.respawnDelay / 12000.0f);
        this.spawnCountSlider.setWidth(200);
        this.respawnSlider.setWidth(200);
        buttons.add(this.spawnCountSlider);
        buttons.add(this.respawnSlider);
        Button b = new Button(53, this.width / 2, 4, 200, 18, "Spawn On Trigger");
        if (!this.mobSpawner.spawnOnTrigger) {
            b.text = this.mobSpawner.spawnOnDetrigger ? "Spawn on Detrigger" : "Spawn on Timer";
        }
        buttons.add(b);
        b = new Button(55, this.width / 2, 24, 200, 18, String.format("Spawn: (%d, %d, %d), (%d, %d, %d)", this.mobSpawner.minSpawnVec.x, this.mobSpawner.minSpawnVec.y, this.mobSpawner.minSpawnVec.z, this.mobSpawner.maxSpawnVec.x, this.mobSpawner.maxSpawnVec.y, this.mobSpawner.maxSpawnVec.z));
        buttons.add(b);
        int buttonWidth = (this.width - 16) / 4;
        buttons.add(new Button(57, 4, 64, buttonWidth, 18, "Select Spawn"));
        buttons.add(new Button(58, 4 + (4 + buttonWidth), 64, buttonWidth, 18, "Select Drops"));
        buttons.add(new Button(59, 4 + 2 * (4 + buttonWidth), 64, buttonWidth, 18, "Select Triggers"));
        buttons.add(new Button(60, 4 + 3 * (4 + buttonWidth), 64, buttonWidth, 18, "Select Scripts"));

        if (this.displayScreen == 0) {
            int i = 0;
            String itemToSpawn = "Spawn Item/Block: None";
            if (ItemType.byId[this.mobSpawner.spawnID] != null) {
                itemToSpawn = String.format("Spawn Item/Block: %s", ItemType.byId[this.mobSpawner.spawnID].getTranslationKey());
            }
            buttons.add(new Button(56, 2, 84, 200, 14, itemToSpawn));
            for (String entityID : entityTypes) {
                buttons.add(new Button(i, 2 + (buttonWidth + 4) * (i % 4), (i / 4 + 1) * 14 + 84, buttonWidth, 13, entityID));
                ++i;
            }
            for (String descName : EntityDescriptions.getDescriptions()) {
                buttons.add(new Button(i, 2 + (buttonWidth + 4) * (i % 4), (i / 4 + 1) * 14 + 84, buttonWidth, 13, descName + " (Scripted)"));
                ++i;
            }
        } else if (this.displayScreen == 1) {
            b = new Button(52, 4, 84, 200, 18, "Drop Nothing");
            if (this.mobSpawner.dropItem > 0) {
                if (this.mobSpawner.dropItem == Items.doorKey.id) {
                    b.text = "Drop key";
                } else if (this.mobSpawner.dropItem == Items.heartContainer.id) {
                    b.text = "Drop heart container";
                } else if (this.mobSpawner.dropItem == Items.bossKey.id) {
                    b.text = "Drop boss key";
                }
            }
            buttons.add(b);
        } else if (this.displayScreen == 2) {
            for (int trigger = 0; trigger < 8; ++trigger) {
                String isSet = ": Not Set";
                if (this.mobSpawner.isTriggerSet(trigger)) {
                    isSet = ": Set";
                }
                b = trigger < 4 ? new Button(70 + trigger, 4, 84 + trigger * 19, 200, 18, "Trigger ".concat(Integer.toString(trigger)).concat(isSet)) : new Button(70 + trigger, this.width / 2, 84 + (trigger - 4) * 19, 200, 18, "OnDeath Trigger ".concat(Integer.toString(trigger)).concat(isSet));
                buttons.add(b);
            }
        } else if (this.displayScreen == 3) {
            this.selectedID = 0;
            this.setOnTrigger = new Button(61, 4, 84, "OnSpawn (selected): " + this.mobSpawner.onTriggerScriptFile);
            this.setOnDetrigger = new Button(62, this.width / 2, 84, "OnDeath: " + this.mobSpawner.onDetriggerScriptFile);
            this.setOnUpdate = new Button(63, 4, 104, "OnUpdate: " + this.mobSpawner.onUpdateScriptFile);
            buttons.add(this.setOnTrigger);
            buttons.add(this.setOnDetrigger);
            buttons.add(this.setOnUpdate);
            buttons.add(new Button(64, this.width / 2, 104, 200, 20, "Reload Scripts"));
            buttons.add(new Button(65, 4, 124, (this.width - 12) / 3, 18, "None"));
            String[] scripts = this.getScriptFiles();
            if (scripts != null) {
                int i = 1;
                for (String scriptFile : scripts) {
                    b = new Button(119 + i, 4 + i % 3 * (this.width - 8) / 3, 124 + i / 3 * 20, (this.width - 12) / 3, 18, scriptFile);
                    buttons.add(b);
                    ++i;
                }
            }
        }
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id < 50) {
            this.mobSpawner.entityID = button.text;
        } else if (button.id == 52) {
            if (this.mobSpawner.dropItem == Items.doorKey.id) {
                button.text = "Drop heart container";
                this.mobSpawner.dropItem = Items.heartContainer.id;
            } else if (this.mobSpawner.dropItem == Items.heartContainer.id) {
                button.text = "Drop boss key";
                this.mobSpawner.dropItem = Items.bossKey.id;
            } else if (this.mobSpawner.dropItem == Items.bossKey.id) {
                button.text = "Drop nothing";
                this.mobSpawner.dropItem = 0;
            } else {
                button.text = "Drop key";
                this.mobSpawner.dropItem = Items.doorKey.id;
            }
        } else if (button.id == 53) {
            if (this.mobSpawner.spawnOnTrigger) {
                this.mobSpawner.spawnOnTrigger = false;
                this.mobSpawner.spawnOnDetrigger = true;
                button.text = "Spawn On Detrigger";
            } else if (this.mobSpawner.spawnOnDetrigger) {
                this.mobSpawner.spawnOnDetrigger = false;
                button.text = "Spawn On Timer";
            } else {
                this.mobSpawner.spawnOnTrigger = true;
                button.text = "Spawn on Trigger";
            }
        } else if (button.id == 55) {
            this.mobSpawner.setSpawnVec();
            button.text = String.format("Spawn: (%d, %d, %d), (%d, %d, %d)", this.mobSpawner.minSpawnVec.x, this.mobSpawner.minSpawnVec.y, this.mobSpawner.minSpawnVec.z, this.mobSpawner.maxSpawnVec.x, this.mobSpawner.maxSpawnVec.y, this.mobSpawner.maxSpawnVec.z);
        } else if (button.id == 56) {
            if (this.minecraft.player.inventory.getHeldItem() != null) {
                this.mobSpawner.spawnID = this.minecraft.player.inventory.getHeldItem().itemId;
                this.mobSpawner.spawnMeta = this.minecraft.player.inventory.getHeldItem().getDamage();
            } else {
                this.mobSpawner.spawnID = 0;
                this.mobSpawner.spawnMeta = 0;
            }
            String itemToSpawn = "Spawn Item/Block: None";
            if (ItemType.byId[this.mobSpawner.spawnID] != null) {
                itemToSpawn = String.format("Spawn Item/Block: %s", ItemType.byId[this.mobSpawner.spawnID].getTranslationKey());
            }
            button.text = itemToSpawn;
        } else if (button.id >= 57 && button.id <= 60) {
            this.displayScreen = button.id - 57;
            this.init();
        } else if (button.id >= 61 && button.id <= 63) {
            this.selectedID = button.id - 61;
            this.resetNames();
        } else if (button.id == 64) {
            ((ExLevel) this.mobSpawner.level).getScriptHandler().loadScripts();
            this.resetNames();
        } else if (button.id == 65) {
            this.updateScriptFile("");
            this.resetNames();
        } else if (button.id >= 120) {
            this.updateScriptFile(button.text);
            this.resetNames();
        } else if (button.id >= 70 && button.id < 120) {
            int i = button.id - 70;
            if (this.mobSpawner.isTriggerSet(i)) {
                this.mobSpawner.setCursor(i);
                this.mobSpawner.clearTrigger(i);
                button.text = "Trigger ".concat(Integer.toString(i)).concat(": Not Set");
            } else {
                this.mobSpawner.setTrigger(i);
                button.text = "Trigger ".concat(Integer.toString(i)).concat(": Set");
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        this.drawTextWithShadow(this.textManager, String.format("Entity Spawn: %s", this.mobSpawner.entityID), 4, 4, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, String.format("Entities Alive: %d", this.mobSpawner.getNumAlive()), 4, 14, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, String.format("Respawn In: %.1fs", (float) this.mobSpawner.delay / 20.0f), 4, 24, 0xE0E0E0);
        if (this.mobSpawner.hasDroppedItem) {
            this.drawTextWithShadow(this.textManager, "Has Dropped An Item", 4, 34, 0xE0E0E0);
        } else {
            this.drawTextWithShadow(this.textManager, "Has Not Dropped An Item", 4, 34, 0xE0E0E0);
        }
        this.mobSpawner.spawnNumber = (int) (this.spawnCountSlider.sliderValue * 15.0f + 0.5f);
        this.spawnCountSlider.text = String.format("Spawn Count: %d", this.mobSpawner.spawnNumber);
        this.mobSpawner.respawnDelay = (int) (this.respawnSlider.sliderValue * 12000.0f + 0.5f);
        this.respawnSlider.text = String.format("Respawn Delay: %.1fs", (float) this.mobSpawner.respawnDelay / 20.0f);
        super.render(mouseX, mouseY, delta);
        this.mobSpawner.level.getChunk(this.mobSpawner.x, this.mobSpawner.z).markDirty();
    }

    @Override
    public boolean isPauseScreen() {
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
        File scriptDir = new File(((ExLevel) this.minecraft.level).getLevelDir(), "scripts");
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
}
