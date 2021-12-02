package io.github.ryuu.adventurecraft.mixin.tile.entity;

import io.github.ryuu.adventurecraft.entities.tile.*;
import io.github.ryuu.adventurecraft.extensions.tile.entity.ExTileEntity;
import net.minecraft.entity.FurnaceEntity;
import net.minecraft.tile.entity.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(TileEntity.class)
public abstract class MixinTileEntity implements ExTileEntity {

    @Shadow
    private static Map<Class<?>, String> CLASS_TO_ID;

    static {
        register(FurnaceEntity.class, "Furnace");
        register(Chest.class, "Chest");
        register(Jukebox.class, "RecordPlayer");
        register(Dispenser.class, "Trap");
        register(Sign.class, "Sign");
        register(MobSpawner.class, "MobSpawner");
        register(Noteblock.class, "Note");
        register(Piston.class, "Piston");
        register(TileEntityMobSpawner.class, "MobSpawnerNew");
        register(TileEntityTrigger.class, "Trigger");
        register(TileEntityTriggerInverter.class, "TriggerInverter");
        register(TileEntityTriggerMemory.class, "TriggerMemory");
        register(TileEntityRedstoneTrigger.class, "RedstoneTrigger");
        register(TileEntityWeather.class, "Weather");
        register(TileEntityMusic.class, "Music");
        register(TileEntityTimer.class, "Timer");
        register(TileEntityMessage.class, "Message");
        register(TileEntityCamera.class, "Camera");
        register(TileEntityTriggerPushable.class, "TriggerPushable");
        register(TileEntityStorage.class, "Storage");
        register(TileEntityHealDamage.class, "HealDamage");
        register(TileEntityTeleport.class, "Teleport");
        register(TileEntityTree.class, "Tree");
        register(TileEntityScript.class, "Script");
        register(TileEntityStore.class, "Store");
        register(TileEntityEffect.class, "Effect");
        register(TileEntityUrl.class, "Url");
        register(TileEntityNpcPath.class, "NpcPath");
    }

    public boolean killedFromSaving;

    @Shadow
    private static void register(Class<?> class1, String s) {
        throw new AssertionError();
    }

    @Override
    public String getClassName() {
        return CLASS_TO_ID.get(this.getClass());
    }

    @Override
    public boolean isKilledFromSaving() {
        return this.killedFromSaving;
    }

    @Override
    public void setKilledFromSaving(boolean killedFromSaving) {
        this.killedFromSaving = killedFromSaving;
    }
}
