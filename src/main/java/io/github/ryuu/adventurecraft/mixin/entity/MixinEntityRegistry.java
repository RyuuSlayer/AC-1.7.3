package io.github.ryuu.adventurecraft.mixin.entity;

import io.github.ryuu.adventurecraft.entities.*;
import net.minecraft.entity.*;
import net.minecraft.entity.animal.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.entity.projectile.Snowball;
import net.minecraft.entity.projectile.ThrownEgg;
import net.minecraft.entity.projectile.ThrownSnowball;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;

@Mixin(EntityRegistry.class)
public class MixinEntityRegistry {

    @Shadow()
    private static final Map STRING_ID_TO_CLASS = new HashMap();

    private static final Map CLASS_TO_STRING_ID = new HashMap();

    private static final Map ID_TO_CLASS = new HashMap();

    private static final Map CLASS_TO_ID = new HashMap();

    static {
        EntityRegistry.register(Arrow.class, "Arrow", 10);
        EntityRegistry.register(ThrownSnowball.class, "Snowball", 11);
        EntityRegistry.register(ItemEntity.class, "Item", 1);
        EntityRegistry.register(Painting.class, "Painting", 9);
        EntityRegistry.register(LivingEntity.class, "Mob", 48);
        EntityRegistry.register(Monster.class, "Monster", 49);
        EntityRegistry.register(Creeper.class, "Creeper", 50);
        EntityRegistry.register(Skeleton.class, "Skeleton", 51);
        EntityRegistry.register(Spider.class, "Spider", 52);
        EntityRegistry.register(Giant.class, "Giant", 53);
        EntityRegistry.register(Zombie.class, "Zombie", 54);
        EntityRegistry.register(Slime.class, "Slime", 55);
        EntityRegistry.register(Ghast.class, "Ghast", 56);
        EntityRegistry.register(ZombiePigman.class, "PigZombie", 57);
        EntityRegistry.register(Pig.class, "Pig", 90);
        EntityRegistry.register(Sheep.class, "Sheep", 91);
        EntityRegistry.register(Cow.class, "Cow", 92);
        EntityRegistry.register(Chicken.class, "Chicken", 93);
        EntityRegistry.register(Squid.class, "Squid", 94);
        EntityRegistry.register(Wolf.class, "Wolf", 95);
        EntityRegistry.register(PrimedTnt.class, "PrimedTnt", 20);
        EntityRegistry.register(FallingTile.class, "FallingSand", 21);
        EntityRegistry.register(Minecart.class, "Minecart", 40);
        EntityRegistry.register(Boat.class, "Boat", 41);
        EntityRegistry.register(ThrownEgg.class, "Egg", 12);
        EntityRegistry.register(Snowball.class, "Fireball", 13);
        EntityRegistry.register(FishHook.class, "FishingRod", 14);
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private static void register(Class class1, String s, int i) {
        STRING_ID_TO_CLASS.put(s, class1);
        CLASS_TO_STRING_ID.put(class1, s);
        ID_TO_CLASS.put(i, class1);
        CLASS_TO_ID.put(class1, i);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static Entity create(String s, Level world) {
        Entity entity = null;
        try {
            Class class1 = (Class) STRING_ID_TO_CLASS.get(s);
            if (class1 != null) {
                entity = (Entity) class1.getConstructor(new Class[]{Level.class}).newInstance(new Object[]{world});
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
    public static Entity create(CompoundTag nbttagcompound, Level world) {
        Entity entity = null;
        try {
            Class class1 = (Class) STRING_ID_TO_CLASS.get(nbttagcompound.getString("id"));
            if (class1 != null) {
                entity = (Entity) class1.getConstructor(new Class[]{Level.class}).newInstance(new Object[]{world});
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
    public static Entity create(int i, Level world) {
        Entity entity = null;
        try {
            Class class1 = (Class) ID_TO_CLASS.get(i);
            if (class1 != null) {
                entity = (Entity) class1.getConstructor(new Class[]{Level.class}).newInstance(new Object[]{world});
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
    public static String getEntityStringClimbing(Entity entity) {
        String returning = null;
        for (Class obj = entity.getClass(); returning == null && obj != null; obj = obj.getSuperclass()) {
            returning = (String) CLASS_TO_STRING_ID.get(obj);
        }
        return returning;
    }
}
