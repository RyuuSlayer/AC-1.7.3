/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.HashMap
 *  java.util.Map
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin.entity;

import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.animal.Chicken;
import net.minecraft.entity.animal.Cow;
import net.minecraft.entity.animal.Pig;
import net.minecraft.entity.animal.Sheep;
import net.minecraft.entity.animal.Squid;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.entity.monster.Creeper;
import net.minecraft.entity.monster.Ghast;
import net.minecraft.entity.monster.Giant;
import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.entity.monster.Slime;
import net.minecraft.entity.monster.Spider;
import net.minecraft.entity.monster.Zombie;
import net.minecraft.entity.monster.ZombiePigman;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.entity.projectile.Snowball;
import net.minecraft.entity.projectile.ThrownEgg;
import net.minecraft.entity.projectile.ThrownSnowball;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import io.github.ryuu.adventurecraft.mixin.item.MixinSnowball;
import io.github.ryuu.adventurecraft.mixin.item.MixinWolf;
import io.github.ryuu.adventurecraft.mixin.item.MixinCreeper;
import io.github.ryuu.adventurecraft.mixin.item.MixinMonster;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinLivingEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinSlime;
import io.github.ryuu.adventurecraft.mixin.item.MixinFallingTile;
import io.github.ryuu.adventurecraft.mixin.item.MixinCompoundTag;
import io.github.ryuu.adventurecraft.mixin.item.MixinEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinArrow;
import io.github.ryuu.adventurecraft.mixin.item.MixinZombie;
import io.github.ryuu.adventurecraft.mixin.item.MixinSkeleton;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinThrownEgg;
import io.github.ryuu.adventurecraft.mixin.item.MixinPainting;
import io.github.ryuu.adventurecraft.mixin.item.MixinThrownSnowball;
import io.github.ryuu.adventurecraft.mixin.item.MixinGhast;
import io.github.ryuu.adventurecraft.mixin.item.MixinFishHook;
import io.github.ryuu.adventurecraft.mixin.item.MixinZombiePigman;

@Mixin(EntityRegistry.class)
public class MixinEntityRegistry {

    @Shadow()
    private static Map STRING_ID_TO_CLASS = new HashMap();

    private static Map CLASS_TO_STRING_ID = new HashMap();

    private static Map ID_TO_CLASS = new HashMap();

    private static Map CLASS_TO_ID = new HashMap();

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private static void register(Class class1, String s, int i) {
        STRING_ID_TO_CLASS.put((Object) s, (Object) class1);
        CLASS_TO_STRING_ID.put((Object) class1, (Object) s);
        ID_TO_CLASS.put((Object) i, (Object) class1);
        CLASS_TO_ID.put((Object) class1, (Object) i);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static MixinEntity create(String s, MixinLevel world) {
        MixinEntity entity = null;
        try {
            Class class1 = (Class) STRING_ID_TO_CLASS.get((Object) s);
            if (class1 != null) {
                entity = (MixinEntity) class1.getConstructor(new Class[] { MixinLevel.class }).newInstance(new Object[] { world });
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return entity;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static MixinEntity create(MixinCompoundTag nbttagcompound, MixinLevel world) {
        MixinEntity entity = null;
        try {
            Class class1 = (Class) STRING_ID_TO_CLASS.get((Object) nbttagcompound.getString("id"));
            if (class1 != null) {
                entity = (MixinEntity) class1.getConstructor(new Class[] { MixinLevel.class }).newInstance(new Object[] { world });
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (entity != null) {
            entity.fromTag(nbttagcompound);
        } else {
            System.out.println("Skipping Entity with id " + nbttagcompound.getString("id"));
        }
        return entity;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static MixinEntity create(int i, MixinLevel world) {
        MixinEntity entity = null;
        try {
            Class class1 = (Class) ID_TO_CLASS.get((Object) i);
            if (class1 != null) {
                entity = (MixinEntity) class1.getConstructor(new Class[] { MixinLevel.class }).newInstance(new Object[] { world });
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (entity == null) {
            System.out.println("Skipping Entity with id " + i);
        }
        return entity;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static int getId(MixinEntity entity) {
        return (Integer) CLASS_TO_ID.get((Object) entity.getClass());
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static String getStringId(MixinEntity entity) {
        return (String) CLASS_TO_STRING_ID.get((Object) entity.getClass());
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static String getEntityStringClimbing(MixinEntity entity) {
        String returning = null;
        for (Class obj = entity.getClass(); returning == null && obj != null; obj = obj.getSuperclass()) {
            returning = (String) CLASS_TO_STRING_ID.get((Object) obj);
        }
        return returning;
    }

    static {
        EntityRegistry.register(MixinArrow.class, "Arrow", 10);
        EntityRegistry.register(MixinThrownSnowball.class, "Snowball", 11);
        EntityRegistry.register(MixinItemEntity.class, "Item", 1);
        EntityRegistry.register(MixinPainting.class, "Painting", 9);
        EntityRegistry.register(MixinLivingEntity.class, "Mob", 48);
        EntityRegistry.register(MixinMonster.class, "Monster", 49);
        EntityRegistry.register(MixinCreeper.class, "Creeper", 50);
        EntityRegistry.register(MixinSkeleton.class, "Skeleton", 51);
        EntityRegistry.register(Spider.class, "Spider", 52);
        EntityRegistry.register(Giant.class, "Giant", 53);
        EntityRegistry.register(MixinZombie.class, "Zombie", 54);
        EntityRegistry.register(MixinSlime.class, "Slime", 55);
        EntityRegistry.register(MixinGhast.class, "Ghast", 56);
        EntityRegistry.register(MixinZombiePigman.class, "PigZombie", 57);
        EntityRegistry.register(Pig.class, "Pig", 90);
        EntityRegistry.register(Sheep.class, "Sheep", 91);
        EntityRegistry.register(Cow.class, "Cow", 92);
        EntityRegistry.register(Chicken.class, "Chicken", 93);
        EntityRegistry.register(Squid.class, "Squid", 94);
        EntityRegistry.register(MixinWolf.class, "Wolf", 95);
        EntityRegistry.register(PrimedTnt.class, "PrimedTnt", 20);
        EntityRegistry.register(MixinFallingTile.class, "FallingSand", 21);
        EntityRegistry.register(Minecart.class, "Minecart", 40);
        EntityRegistry.register(Boat.class, "Boat", 41);
        EntityRegistry.register(MixinThrownEgg.class, "Egg", 12);
        EntityRegistry.register(MixinSnowball.class, "Fireball", 13);
        EntityRegistry.register(MixinFishHook.class, "FishingRod", 14);
        EntityRegistry.register(EntitySkeletonSword.class, "SkeletonSword", 58);
        EntityRegistry.register(EntitySkeletonBoss.class, "SkeletonBoss", 59);
        EntityRegistry.register(EntityBat.class, "Bat", 60);
        EntityRegistry.register(EntityRat.class, "Rat", 61);
        EntityRegistry.register(EntityNPC.class, "NPC", 62);
        EntityRegistry.register(EntitySkeletonRifle.class, "SkeletonRifle", 63);
        EntityRegistry.register(EntitySkeletonShotgun.class, "SkeletonShotgun", 64);
        EntityRegistry.register(EntityZombiePistol.class, "ZombiePistol", 65);
        EntityRegistry.register(EntityBomb.class, "Bomb", 1000);
        EntityRegistry.register(EntityBoomerang.class, "Boomerang", 1001);
        EntityRegistry.register(EntityArrowBomb.class, "Bomb Arrow", 1002);
        EntityRegistry.register(EntityHookshot.class, "Hookshot", 1003);
        EntityRegistry.register(EntityLivingScript.class, "Script", 1004);
    }
}
