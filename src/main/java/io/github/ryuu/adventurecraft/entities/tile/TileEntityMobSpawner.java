package io.github.ryuu.adventurecraft.entities.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.ryuu.adventurecraft.entities.EntityLivingScript;
import io.github.ryuu.adventurecraft.entities.EntitySkeletonSword;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.overrides.ItemType;
import io.github.ryuu.adventurecraft.overrides.Tile;
import io.github.ryuu.adventurecraft.scripting.ScopeTag;
import io.github.ryuu.adventurecraft.scripting.ScriptEntity;
import io.github.ryuu.adventurecraft.util.Coord;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.entity.monster.Slime;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.CompoundTag;
import org.mozilla.javascript.Scriptable;

public class TileEntityMobSpawner extends TileEntityScript {
    public int delay;

    public String entityID;

    public int spawnNumber;

    public int respawnDelay;

    public int dropItem;

    public boolean hasDroppedItem;

    public boolean spawnOnTrigger;

    public boolean spawnOnDetrigger;

    public List<Entity> spawnedEntities;

    public List<Entity> entitiesLeft;

    public int spawnID;

    public int spawnMeta;

    Random rand;

    public Coord[] minVec;

    public Coord[] maxVec;

    public Coord minSpawnVec;

    public Coord maxSpawnVec;

    public int ticksBeforeLoad;

    public CompoundTag delayLoadData;

    private boolean spawnStill;

    Scriptable scope;

    public TileEntityMobSpawner() {
        this.ticksBeforeLoad = 20;
        this.spawnStill = false;
        this.delay = -1;
        this.entityID = "Pig";
        this.delay = 20;
        this.spawnNumber = 3;
        this.respawnDelay = 1200;
        this.spawnedEntities = new ArrayList<>();
        this.entitiesLeft = new ArrayList<>();
        this.dropItem = 0;
        this.hasDroppedItem = false;
        this.spawnOnTrigger = true;
        this.spawnOnDetrigger = false;
        this.rand = new Random();
        this.minVec = new Coord[8];
        this.maxVec = new Coord[8];
        for (int i = 0; i < 8; i++) {
            this.minVec[i] = new Coord();
            this.maxVec[i] = new Coord();
        }
        this.minSpawnVec = new Coord();
        this.maxSpawnVec = new Coord();
        this.delayLoadData = null;
        this.scope = Minecraft.minecraftInstance.f.script.getNewScope();
    }

    public int getNumAlive() {
        int numAlive = 0;
        for (Entity ent : this.spawnedEntities) {
            if (!ent.removed) {
                numAlive++;
                continue;
            }
            if (this.entitiesLeft.contains(ent))
                this.entitiesLeft.remove(ent);
        }
        return numAlive;
    }

    public void resetMobs() {
        for (Entity ent : this.spawnedEntities) {
            if (!ent.removed)
                ent.K();
        }
        this.spawnedEntities.clear();
        this.entitiesLeft.clear();
        deactivateTriggers();
    }

    private boolean canSpawn(Entity entity) {
        return (this.d.a(entity.aW) && this.d.a(entity, entity.aW).size() == 0 && !this.d.b(entity.aW));
    }

    public void spawnMobs() {
        if (this.delay > 0 || getNumAlive() > 0 || this.delayLoadData != null)
            return;
        for (int i = 0; i < this.spawnNumber * 6; i++) {
            double posY;
            float rot;
            String spawnEntityID = this.entityID.replace(" ", "");
            if (spawnEntityID.equalsIgnoreCase("FallingBlock")) {
                spawnEntityID = "FallingSand";
            } else if (spawnEntityID.startsWith("Slime")) {
                spawnEntityID = "Slime";
            } else if (spawnEntityID.startsWith("Minecart")) {
                spawnEntityID = "Minecart";
            } else if (spawnEntityID.startsWith("Spider")) {
                spawnEntityID = "Spider";
            } else if (spawnEntityID.startsWith("Wolf")) {
                spawnEntityID = "Wolf";
            } else if (spawnEntityID.endsWith("(Scripted)")) {
                spawnEntityID = "Script";
            }
            Entity entity = EntityRegistry.getId(spawnEntityID, this.d);
            if (entity == null)
                return;
            if (spawnEntityID.equalsIgnoreCase("FallingSand")) {
                if (this.spawnID < 256 && Tile.m[this.spawnID] != null) {
                    ((FallingTile) entity).tile = this.spawnID;
                    ((FallingTile) entity).metadata = this.spawnMeta;
                } else {
                    return;
                }
            } else if (spawnEntityID.equalsIgnoreCase("Item")) {
                if (ItemType.c[this.spawnID] != null) {
                    ((ItemEntity) entity).item = new ItemInstance(this.spawnID, 1, this.spawnMeta);
                } else {
                    return;
                }
            } else if (this.entityID.startsWith("Slime") && this.entityID.length() > 6) {
                int size = Integer.parseInt(this.entityID.split(":")[1].trim());
                ((Slime) entity).setSize(size);
            } else if (this.entityID.equalsIgnoreCase("Minecart Chest")) {
                ((Minecart) entity).type = 1;
            } else if (this.entityID.equalsIgnoreCase("Minecart Furnace")) {
                ((Minecart) entity).type = 2;
            }
            if (this.maxSpawnVec.y == this.minSpawnVec.y) {
                posY = (this.f + this.maxSpawnVec.y);
            } else {
                posY = (this.f + this.minSpawnVec.y + this.d.r.nextInt(this.maxSpawnVec.y - this.minSpawnVec.y));
            }
            double posX = (this.e + this.minSpawnVec.x) + this.d.r.nextDouble() * (this.maxSpawnVec.x - this.minSpawnVec.x) + 0.5D;
            double posZ = (this.g + this.minSpawnVec.z) + this.d.r.nextDouble() * (this.maxSpawnVec.z - this.minSpawnVec.z) + 0.5D;
            if (!spawnEntityID.equalsIgnoreCase("FallingSand")) {
                rot = this.d.r.nextFloat() * 360.0F;
            } else {
                rot = 0.0F;
            }
            entity.c(posX, posY, posZ, rot, 0.0F);
            if (canSpawn(entity)) {
                this.d.b(entity);
                if (this.entityID.equalsIgnoreCase("Spider Skeleton")) {
                    Entity rider = new Skeleton(this.d);
                    rider.c(posX, posY, posZ, rot, 0.0F);
                    this.d.b(rider);
                    rider.i(entity);
                    this.spawnedEntities.add(rider);
                    this.entitiesLeft.add(rider);
                } else if (this.entityID.equalsIgnoreCase("Spider Skeleton Sword")) {
                    Entity rider = new EntitySkeletonSword(this.d);
                    rider.c(posX, posY, posZ, rot, 0.0F);
                    this.d.b(rider);
                    rider.i(entity);
                    this.spawnedEntities.add(rider);
                    this.entitiesLeft.add(rider);
                } else if (this.entityID.equalsIgnoreCase("Wolf (Angry)")) {
                    Wolf w = (Wolf) entity;
                    w.setAngry(true);
                } else if (this.entityID.equalsIgnoreCase("Wolf (Tame)")) {
                    Wolf w = (Wolf) entity;
                    w.setHasOwner(true);
                    w.a((dh) null);
                    w.Y = 20;
                    w.a(Minecraft.minecraftInstance.h.l);
                    w.a(true);
                    this.d.a((Entity) w, (byte) 7);
                }
                if (this.entityID.endsWith("(Scripted)")) {
                    EntityLivingScript els = (EntityLivingScript) entity;
                    els.setEntityDescription(this.entityID.replace(" (Scripted)", ""));
                }
                if (entity instanceof LivingEntity)
                    ((LivingEntity) entity).V();
                this.spawnedEntities.add(entity);
                this.entitiesLeft.add(entity);
                if (this.spawnedEntities.size() >= this.spawnNumber)
                    break;
            }
        }
        if (this.spawnNumber > 0 && this.spawnedEntities.size() == 0) {
            this.delay = 20;
            this.spawnStill = true;
        } else {
            activateTriggers();
            executeScript(this.onTriggerScriptFile);
            this.spawnStill = false;
        }
    }

    public void n_() {
        if (this.delayLoadData != null) {
            if (this.ticksBeforeLoad == 0) {
                int num = this.delayLoadData.d("numEntities");
                for (int i = 0; i < num; i++) {
                    int entID = this.delayLoadData.e(String.format("entID_%d", new Object[]{Integer.valueOf(i)}));
                    for (Entity obj : this.d.b) {
                        Entity e = obj;
                        if (e.aD == entID) {
                            this.spawnedEntities.add(e);
                            if (e.W())
                                this.entitiesLeft.add(e);
                            break;
                        }
                    }
                }
                this.delayLoadData = null;
                if (num > 0)
                    executeScript(this.onTriggerScriptFile);
            }
            this.ticksBeforeLoad--;
            return;
        }
        if (this.delay > 0) {
            this.delay--;
            return;
        }
        if (!this.spawnedEntities.isEmpty()) {
            if (getNumAlive() == 0) {
                this.spawnedEntities.clear();
                this.entitiesLeft.clear();
                this.delay = this.respawnDelay;
                if (this.dropItem > 0 && !this.hasDroppedItem) {
                    ItemEntity entityitem = new ItemEntity(this.d, this.e + 0.5D, this.f + 0.5D, this.g + 0.5D, new ItemInstance(this.dropItem, 1, 0));
                    entityitem.pickupDelay = 10;
                    this.d.b(entityitem);
                    for (int j = 0; j < 20; j++) {
                        double d = this.rand.nextGaussian() * 0.02D;
                        double d1 = this.rand.nextGaussian() * 0.02D;
                        double d2 = this.rand.nextGaussian() * 0.02D;
                        double d3 = 10.0D;
                        this.d.a("explode", entityitem.aM + (this.rand.nextFloat() * 2.0F) - 1.0D - d * d3, entityitem.aN + this.rand.nextFloat() - d1 * d3, entityitem.aO + (this.rand.nextFloat() * 2.0F) - 1.0D - d2 * d3, d, d1, d2);
                    }
                    this.hasDroppedItem = true;
                }
                executeScript(this.onDetriggerScriptFile);
                for (int i = 4; i < 8; i++) {
                    if (isTriggerSet(i))
                        activateTrigger(i, this.minVec[i], this.maxVec[i]);
                }
                deactivateTriggers();
            } else {
                executeScript(this.onUpdateScriptFile);
            }
            return;
        }
        if (this.spawnStill || (!this.spawnOnTrigger && !this.spawnOnDetrigger))
            spawnMobs();
        super.n_();
    }

    public void setSpawnVec() {
        this.minSpawnVec.set(ItemCursor.minX - this.e, ItemCursor.minY - this.f, ItemCursor.minZ - this.g);
        this.maxSpawnVec.set(ItemCursor.maxX - this.e, ItemCursor.maxY - this.f, ItemCursor.maxZ - this.g);
    }

    public boolean isTriggerSet(int i) {
        boolean r = ((this.minVec[i]).x != 0);
        r = (r || (this.minVec[i]).y != 0);
        r = (r || (this.minVec[i]).z != 0);
        r = (r || (this.maxVec[i]).x != 0);
        r = (r || (this.maxVec[i]).y != 0);
        r = (r || (this.maxVec[i]).z != 0);
        return r;
    }

    public void setTrigger(int i) {
        this.minVec[i].set(ItemCursor.minX, ItemCursor.minY, ItemCursor.minZ);
        this.maxVec[i].set(ItemCursor.maxX, ItemCursor.maxY, ItemCursor.maxZ);
    }

    public void setCursor(int i) {
        ItemCursor.oneX = ItemCursor.minX = (this.minVec[i]).x;
        ItemCursor.oneY = ItemCursor.minY = (this.minVec[i]).y;
        ItemCursor.oneZ = ItemCursor.minZ = (this.minVec[i]).z;
        ItemCursor.twoX = ItemCursor.maxX = (this.maxVec[i]).x;
        ItemCursor.twoY = ItemCursor.maxY = (this.maxVec[i]).y;
        ItemCursor.twoZ = ItemCursor.maxZ = (this.maxVec[i]).z;
    }

    public void clearTrigger(int i) {
        (this.minVec[i]).x = (this.minVec[i]).y = (this.minVec[i]).z = 0;
        (this.maxVec[i]).x = (this.maxVec[i]).y = (this.maxVec[i]).z = 0;
    }

    private void activateTriggers() {
        for (int i = 0; i < 4; i++) {
            if (isTriggerSet(i))
                activateTrigger(i, this.minVec[i], this.maxVec[i]);
        }
    }

    private void activateTrigger(int i, Coord minVec, Coord maxVec) {
        if (minVec.x == 0 && minVec.y == 0 && minVec.z == 0 && maxVec.x == 0 && maxVec.y == 0 && maxVec.z == 0)
            return;
        this.d.triggerManager.addArea(this.e, this.f, this.g, i, new TriggerArea(minVec.x, minVec.y, minVec.z, maxVec.x, maxVec.y, maxVec.z));
    }

    private void deactivateTriggers() {
        this.d.triggerManager.removeArea(this.e, this.f, this.g);
    }

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.entityID = nbttagcompound.getString("EntityId");
        this.delay = nbttagcompound.getShort("Delay");
        this.respawnDelay = nbttagcompound.getInt("RespawnDelay");
        this.spawnNumber = nbttagcompound.getInt("SpawnNumber");
        this.spawnOnTrigger = nbttagcompound.getBoolean("SpawnOnTrigger");
        this.spawnOnDetrigger = nbttagcompound.getBoolean("SpawnOnDetrigger");
        this.dropItem = nbttagcompound.getInt("DropItem");
        this.hasDroppedItem = nbttagcompound.getBoolean("HasDroppedItem");
        this.spawnID = nbttagcompound.getInt("SpawnID");
        for (int i = 0; i < 8; i++) {
            (this.minVec[i]).x = nbttagcompound.getInt("minX".concat(Integer.toString(i)));
            (this.minVec[i]).y = nbttagcompound.getInt("minY".concat(Integer.toString(i)));
            (this.minVec[i]).z = nbttagcompound.getInt("minZ".concat(Integer.toString(i)));
            (this.maxVec[i]).x = nbttagcompound.getInt("maxX".concat(Integer.toString(i)));
            (this.maxVec[i]).y = nbttagcompound.getInt("maxY".concat(Integer.toString(i)));
            (this.maxVec[i]).z = nbttagcompound.getInt("maxZ".concat(Integer.toString(i)));
        }
        this.minSpawnVec.x = nbttagcompound.getInt("minSpawnX");
        this.minSpawnVec.y = nbttagcompound.getInt("minSpawnY");
        this.minSpawnVec.z = nbttagcompound.getInt("minSpawnZ");
        this.maxSpawnVec.x = nbttagcompound.getInt("maxSpawnX");
        this.maxSpawnVec.y = nbttagcompound.getInt("maxSpawnY");
        this.maxSpawnVec.z = nbttagcompound.getInt("maxSpawnZ");
        if (nbttagcompound.containsKey("numEntities") && nbttagcompound.getShort("numEntities") > 0) {
            this.ticksBeforeLoad = 20;
            this.delayLoadData = nbttagcompound;
        }
        if (nbttagcompound.containsKey("scope"))
            ScopeTag.loadScopeFromTag(this.scope, nbttagcompound.getCompoundTag("scope"));
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.put("EntityId", this.entityID);
        nbttagcompound.put("Delay", (short) this.delay);
        nbttagcompound.put("RespawnDelay", this.respawnDelay);
        nbttagcompound.put("SpawnNumber", this.spawnNumber);
        nbttagcompound.put("SpawnOnTrigger", this.spawnOnTrigger);
        nbttagcompound.put("SpawnOnDetrigger", this.spawnOnDetrigger);
        nbttagcompound.put("SpawnID", this.spawnID);
        nbttagcompound.put("DropItem", this.dropItem);
        nbttagcompound.put("HasDroppedItem", this.hasDroppedItem);
        int i;
        for (i = 0; i < 8; i++) {
            nbttagcompound.put("minX".concat(Integer.toString(i)), (this.minVec[i]).x);
            nbttagcompound.put("minY".concat(Integer.toString(i)), (this.minVec[i]).y);
            nbttagcompound.put("minZ".concat(Integer.toString(i)), (this.minVec[i]).z);
            nbttagcompound.put("maxX".concat(Integer.toString(i)), (this.maxVec[i]).x);
            nbttagcompound.put("maxY".concat(Integer.toString(i)), (this.maxVec[i]).y);
            nbttagcompound.put("maxZ".concat(Integer.toString(i)), (this.maxVec[i]).z);
        }
        nbttagcompound.put("minSpawnX", this.minSpawnVec.x);
        nbttagcompound.put("minSpawnY", this.minSpawnVec.y);
        nbttagcompound.put("minSpawnZ", this.minSpawnVec.z);
        nbttagcompound.put("maxSpawnX", this.maxSpawnVec.x);
        nbttagcompound.put("maxSpawnY", this.maxSpawnVec.y);
        nbttagcompound.put("maxSpawnZ", this.maxSpawnVec.z);
        nbttagcompound.put("numEntities", (short) this.spawnedEntities.size());
        i = 0;
        for (Entity e : this.spawnedEntities) {
            nbttagcompound.put(String.format("entID_%d", new Object[]{Integer.valueOf(i)}), e.aD);
            i++;
        }
        nbttagcompound.put("scope", ScopeTag.getTagFromScope(this.scope));
    }

    private void executeScript(String scriptName) {
        if (!scriptName.equals("")) {
            int i = 0;
            ScriptEntity[] scriptSpawnedEntities = new ScriptEntity[this.entitiesLeft.size()];
            for (Entity e : this.entitiesLeft)
                scriptSpawnedEntities[i++] = ScriptEntity.getEntityClass(e);
            this.d.script.addObject("spawnedEntities", scriptSpawnedEntities);
            this.d.scriptHandler.runScript(scriptName, this.scope);
        }
    }
}
