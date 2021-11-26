package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.entities.EntityLivingScript;
import io.github.ryuu.adventurecraft.entities.EntitySkeletonSword;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.scripting.ScopeTag;
import io.github.ryuu.adventurecraft.scripting.ScriptEntity;
import io.github.ryuu.adventurecraft.util.Coord;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.entity.*;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.entity.monster.Slime;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.AbstractTag;
import net.minecraft.util.io.CompoundTag;
import org.mozilla.javascript.Scriptable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileEntityMobSpawner extends TileEntityScript {

    public int delay = 20;

    public String entityID = "Pig";

    public int spawnNumber = 3;

    public int respawnDelay = 1200;

    public int dropItem = 0;

    public boolean hasDroppedItem = false;

    public boolean spawnOnTrigger = true;

    public boolean spawnOnDetrigger = false;

    public List<Object> spawnedEntities = new ArrayList();

    public List<Object> entitiesLeft = new ArrayList();

    public int spawnID;

    public int spawnMeta;
    public Coord[] minVec = new Coord[8];
    public Coord[] maxVec = new Coord[8];
    public Coord minSpawnVec;
    public Coord maxSpawnVec;
    public int ticksBeforeLoad = 20;
    public CompoundTag delayLoadData;
    Random rand = new Random();
    Scriptable scope;
    private boolean spawnStill = false;

    public TileEntityMobSpawner() {
        for (int i = 0; i < 8; ++i) {
            this.minVec[i] = new Coord();
            this.maxVec[i] = new Coord();
        }
        this.minSpawnVec = new Coord();
        this.maxSpawnVec = new Coord();
        this.delayLoadData = null;
        this.scope = AccessMinecraft.getInstance().level.script.getNewScope();
    }

    public int getNumAlive() {
        int numAlive = 0;
        for (Object ent : this.spawnedEntities) {
            if (!ent.removed) {
                ++numAlive;
                continue;
            }
            if (!this.entitiesLeft.contains(ent)) continue;
            this.entitiesLeft.remove(ent);
        }
        return numAlive;
    }

    public void resetMobs() {
        for (Object ent : this.spawnedEntities) {
            if (ent.removed) continue;
            ent.remove();
        }
        this.spawnedEntities.clear();
        this.entitiesLeft.clear();
        this.deactivateTriggers();
    }

    private boolean canSpawn(Entity entity) {
        return this.level.canSpawnEntity(entity.boundingBox) && this.level.method_190(entity, entity.boundingBox).size() == 0 && !this.level.method_218(entity.boundingBox);
    }

    public void spawnMobs() {
        if (this.delay > 0 || this.getNumAlive() > 0 || this.delayLoadData != null) {
            return;
        }
        for (int i = 0; i < this.spawnNumber * 6; ++i) {
            Wolf w;
            Skeleton rider;
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
            Entity entity = EntityRegistry.create(spawnEntityID, this.level);
            if (entity == null) {
                return;
            }
            if (spawnEntityID.equalsIgnoreCase("FallingSand")) {
                if (this.spawnID >= 256 || Tile.BY_ID[this.spawnID] == null) return;
                ((FallingTile) entity).tile = this.spawnID;
                ((FallingTile) entity).metadata = this.spawnMeta;
            } else if (spawnEntityID.equalsIgnoreCase("Item")) {
                if (ItemType.byId[this.spawnID] == null) return;
                ((ItemEntity) entity).item = new ItemInstance(this.spawnID, 1, this.spawnMeta);
            } else if (this.entityID.startsWith("Slime") && this.entityID.length() > 6) {
                int size = Integer.parseInt(this.entityID.split(":")[1].trim());
                ((Slime) entity).setSize(size);
            } else if (this.entityID.equalsIgnoreCase("Minecart Chest")) {
                ((Minecart) entity).type = 1;
            } else if (this.entityID.equalsIgnoreCase("Minecart Furnace")) {
                ((Minecart) entity).type = 2;
            }
            double posY = this.maxSpawnVec.y == this.minSpawnVec.y ? (double) (this.y + this.maxSpawnVec.y) : (double) (this.y + this.minSpawnVec.y + this.level.rand.nextInt(this.maxSpawnVec.y - this.minSpawnVec.y));
            double posX = (double) (this.x + this.minSpawnVec.x) + this.level.rand.nextDouble() * (double) (this.maxSpawnVec.x - this.minSpawnVec.x) + 0.5;
            double posZ = (double) (this.z + this.minSpawnVec.z) + this.level.rand.nextDouble() * (double) (this.maxSpawnVec.z - this.minSpawnVec.z) + 0.5;
            float rot = !spawnEntityID.equalsIgnoreCase("FallingSand") ? this.level.rand.nextFloat() * 360.0f : 0.0f;
            entity.setPositionAndAngles(posX, posY, posZ, rot, 0.0f);
            if (!this.canSpawn(entity)) continue;
            this.level.spawnEntity(entity);
            if (this.entityID.equalsIgnoreCase("Spider Skeleton")) {
                rider = new Skeleton(this.level);
                rider.setPositionAndAngles(posX, posY, posZ, rot, 0.0f);
                this.level.spawnEntity(rider);
                rider.startRiding(entity);
                this.spawnedEntities.add(rider);
                this.entitiesLeft.add(rider);
            } else if (this.entityID.equalsIgnoreCase("Spider Skeleton Sword")) {
                rider = new EntitySkeletonSword(this.level);
                rider.setPositionAndAngles(posX, posY, posZ, rot, 0.0f);
                this.level.spawnEntity(rider);
                rider.startRiding(entity);
                this.spawnedEntities.add(rider);
                this.entitiesLeft.add(rider);
            } else if (this.entityID.equalsIgnoreCase("Wolf (Angry)")) {
                w = (Wolf) entity;
                w.setAngry(true);
            } else if (this.entityID.equalsIgnoreCase("Wolf (Tame)")) {
                w = (Wolf) entity;
                w.setHasOwner(true);
                w.setTarget(null);
                w.health = 20;
                w.setOwner(AccessMinecraft.getInstance().player.name);
                w.spawnBoneParticles(true);
                this.level.method_185(w, (byte) 7);
            }
            if (this.entityID.endsWith("(Scripted)")) {
                EntityLivingScript els = (EntityLivingScript) entity;
                els.setEntityDescription(this.entityID.replace(" (Scripted)", ""));
            }
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).onSpawnedFromSpawner();
            }
            this.spawnedEntities.add(entity);
            this.entitiesLeft.add(entity);
            if (this.spawnedEntities.size() >= this.spawnNumber) break;
        }
        if (this.spawnNumber > 0 && this.spawnedEntities.size() == 0) {
            this.delay = 20;
            this.spawnStill = true;
        } else {
            this.activateTriggers();
            this.executeScript(this.onTriggerScriptFile);
            this.spawnStill = false;
        }
    }

    @Override
    public void tick() {
        if (this.delayLoadData != null) {
            if (this.ticksBeforeLoad == 0) {
                int num = this.delayLoadData.getShort("numEntities");
                block0:
                for (int i = 0; i < num; ++i) {
                    int entID = this.delayLoadData.getInt(String.format("entID_%d", i));
                    for (Object o : this.level.entities) {
                        Entity obj;
                        Entity e = obj = (Entity) o;
                        if (e.id != entID) continue;
                        this.spawnedEntities.add(e);
                        if (!e.isAlive()) continue block0;
                        this.entitiesLeft.add(e);
                        continue block0;
                    }
                }
                this.delayLoadData = null;
                if (num > 0) {
                    this.executeScript(this.onTriggerScriptFile);
                }
            }
            --this.ticksBeforeLoad;
            return;
        }
        if (this.delay > 0) {
            --this.delay;
            return;
        }
        if (!this.spawnedEntities.isEmpty()) {
            if (this.getNumAlive() == 0) {
                this.spawnedEntities.clear();
                this.entitiesLeft.clear();
                this.delay = this.respawnDelay;
                if (this.dropItem > 0 && !this.hasDroppedItem) {
                    ItemEntity entityitem = new ItemEntity(this.level, (double) this.x + 0.5, (double) this.y + 0.5, (double) this.z + 0.5, new ItemInstance(this.dropItem, 1, 0));
                    entityitem.pickupDelay = 10;
                    this.level.spawnEntity(entityitem);
                    for (int i = 0; i < 20; ++i) {
                        double d = this.rand.nextGaussian() * 0.02;
                        double d1 = this.rand.nextGaussian() * 0.02;
                        double d2 = this.rand.nextGaussian() * 0.02;
                        double d3 = 10.0;
                        this.level.addParticle("explode", entityitem.x + (double) (this.rand.nextFloat() * 2.0f) - 1.0 - d * d3, entityitem.y + (double) this.rand.nextFloat() - d1 * d3, entityitem.z + (double) (this.rand.nextFloat() * 2.0f) - 1.0 - d2 * d3, d, d1, d2);
                    }
                    this.hasDroppedItem = true;
                }
                this.executeScript(this.onDetriggerScriptFile);
                for (int i = 4; i < 8; ++i) {
                    if (!this.isTriggerSet(i)) continue;
                    this.activateTrigger(i, this.minVec[i], this.maxVec[i]);
                }
                this.deactivateTriggers();
            } else {
                this.executeScript(this.onUpdateScriptFile);
            }
            return;
        }
        if (this.spawnStill || !this.spawnOnTrigger && !this.spawnOnDetrigger) {
            this.spawnMobs();
        }
        super.tick();
    }

    public void setSpawnVec() {
        this.minSpawnVec.set(ItemCursor.minX - this.x, ItemCursor.minY - this.y, ItemCursor.minZ - this.z);
        this.maxSpawnVec.set(ItemCursor.maxX - this.x, ItemCursor.maxY - this.y, ItemCursor.maxZ - this.z);
    }

    public boolean isTriggerSet(int i) {
        boolean r = this.minVec[i].x != 0;
        r = r || this.minVec[i].y != 0;
        r = r || this.minVec[i].z != 0;
        r = r || this.maxVec[i].x != 0;
        r = r || this.maxVec[i].y != 0;
        r = r || this.maxVec[i].z != 0;
        return r;
    }

    public void setTrigger(int i) {
        this.minVec[i].set(ItemCursor.minX, ItemCursor.minY, ItemCursor.minZ);
        this.maxVec[i].set(ItemCursor.maxX, ItemCursor.maxY, ItemCursor.maxZ);
    }

    public void setCursor(int i) {
        ItemCursor.oneX = ItemCursor.minX = this.minVec[i].x;
        ItemCursor.oneY = ItemCursor.minY = this.minVec[i].y;
        ItemCursor.oneZ = ItemCursor.minZ = this.minVec[i].z;
        ItemCursor.twoX = ItemCursor.maxX = this.maxVec[i].x;
        ItemCursor.twoY = ItemCursor.maxY = this.maxVec[i].y;
        ItemCursor.twoZ = ItemCursor.maxZ = this.maxVec[i].z;
    }

    public void clearTrigger(int i) {
        this.minVec[i].z = 0;
        this.minVec[i].y = 0;
        this.minVec[i].x = 0;
        this.maxVec[i].z = 0;
        this.maxVec[i].y = 0;
        this.maxVec[i].x = 0;
    }

    private void activateTriggers() {
        for (int i = 0; i < 4; ++i) {
            if (!this.isTriggerSet(i)) continue;
            this.activateTrigger(i, this.minVec[i], this.maxVec[i]);
        }
    }

    private void activateTrigger(int i, Coord minVec, Coord maxVec) {
        if (minVec.x == 0 && minVec.y == 0 && minVec.z == 0 && maxVec.x == 0 && maxVec.y == 0 && maxVec.z == 0) {
            return;
        }
        this.level.triggerManager.addArea(this.x, this.y, this.z, i, new TriggerArea(minVec.x, minVec.y, minVec.z, maxVec.x, maxVec.y, maxVec.z));
    }

    private void deactivateTriggers() {
        this.level.triggerManager.removeArea(this.x, this.y, this.z);
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        this.entityID = tag.getString("EntityId");
        this.delay = tag.getShort("Delay");
        this.respawnDelay = tag.getInt("RespawnDelay");
        this.spawnNumber = tag.getInt("SpawnNumber");
        this.spawnOnTrigger = tag.getBoolean("SpawnOnTrigger");
        this.spawnOnDetrigger = tag.getBoolean("SpawnOnDetrigger");
        this.dropItem = tag.getInt("DropItem");
        this.hasDroppedItem = tag.getBoolean("HasDroppedItem");
        this.spawnID = tag.getInt("SpawnID");
        for (int i = 0; i < 8; ++i) {
            this.minVec[i].x = tag.getInt("minX".concat(Integer.toString(i)));
            this.minVec[i].y = tag.getInt("minY".concat(Integer.toString(i)));
            this.minVec[i].z = tag.getInt("minZ".concat(Integer.toString(i)));
            this.maxVec[i].x = tag.getInt("maxX".concat(Integer.toString(i)));
            this.maxVec[i].y = tag.getInt("maxY".concat(Integer.toString(i)));
            this.maxVec[i].z = tag.getInt("maxZ".concat(Integer.toString(i)));
        }
        this.minSpawnVec.x = tag.getInt("minSpawnX");
        this.minSpawnVec.y = tag.getInt("minSpawnY");
        this.minSpawnVec.z = tag.getInt("minSpawnZ");
        this.maxSpawnVec.x = tag.getInt("maxSpawnX");
        this.maxSpawnVec.y = tag.getInt("maxSpawnY");
        this.maxSpawnVec.z = tag.getInt("maxSpawnZ");
        if (tag.containsKey("numEntities") && tag.getShort("numEntities") > 0) {
            this.ticksBeforeLoad = 20;
            this.delayLoadData = tag;
        }
        if (tag.containsKey("scope")) {
            ScopeTag.loadScopeFromTag(this.scope, tag.getCompoundTag("scope"));
        }
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        int i;
        super.writeIdentifyingData(tag);
        tag.put("EntityId", this.entityID);
        tag.put("Delay", (short) this.delay);
        tag.put("RespawnDelay", this.respawnDelay);
        tag.put("SpawnNumber", this.spawnNumber);
        tag.put("SpawnOnTrigger", this.spawnOnTrigger);
        tag.put("SpawnOnDetrigger", this.spawnOnDetrigger);
        tag.put("SpawnID", this.spawnID);
        tag.put("DropItem", this.dropItem);
        tag.put("HasDroppedItem", this.hasDroppedItem);
        for (i = 0; i < 8; ++i) {
            tag.put("minX".concat(Integer.toString(i)), this.minVec[i].x);
            tag.put("minY".concat(Integer.toString(i)), this.minVec[i].y);
            tag.put("minZ".concat(Integer.toString(i)), this.minVec[i].z);
            tag.put("maxX".concat(Integer.toString(i)), this.maxVec[i].x);
            tag.put("maxY".concat(Integer.toString(i)), this.maxVec[i].y);
            tag.put("maxZ".concat(Integer.toString(i)), this.maxVec[i].z);
        }
        tag.put("minSpawnX", this.minSpawnVec.x);
        tag.put("minSpawnY", this.minSpawnVec.y);
        tag.put("minSpawnZ", this.minSpawnVec.z);
        tag.put("maxSpawnX", this.maxSpawnVec.x);
        tag.put("maxSpawnY", this.maxSpawnVec.y);
        tag.put("maxSpawnZ", this.maxSpawnVec.z);
        tag.put("numEntities", (short) this.spawnedEntities.size());
        i = 0;
        for (Object e : this.spawnedEntities) {
            tag.put(String.format("entID_%d", i), e.id);
            ++i;
        }
        tag.put("scope", (AbstractTag) ScopeTag.getTagFromScope(this.scope));
    }

    private void executeScript(String scriptName) {
        if (!scriptName.equals("")) {
            int i = 0;
            ScriptEntity[] scriptSpawnedEntities = new ScriptEntity[this.entitiesLeft.size()];
            for (Object e : this.entitiesLeft) {
                scriptSpawnedEntities[i++] = ScriptEntity.getEntityClass(e);
            }
            this.level.script.addObject("spawnedEntities", scriptSpawnedEntities);
            this.level.scriptHandler.runScript(scriptName, this.scope);
        }
    }
}
