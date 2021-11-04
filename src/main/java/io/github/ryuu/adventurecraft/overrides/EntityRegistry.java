package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.entities.*;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

import java.util.HashMap;
import java.util.Map;

public class EntityRegistry {
    private static void a(Class<?> class1, String s, int i) {
        a.put(s, class1);
        b.put(class1, s);
        c.put(Integer.valueOf(i), class1);
        d.put(class1, Integer.valueOf(i));
    }

    public static Entity a(String s, Level world) {
        Entity entity = null;
        try {
            Class<Entity> class1 = (Class) a.get(s);
            if (class1 != null)
                entity = class1.getConstructor(new Class[]{Level.class}).newInstance(new Object[]{world});
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return entity;
    }

    public static Entity a(CompoundTag nbttagcompound, Level world) {
        Entity entity = null;
        try {
            Class<Entity> class1 = (Class) a.get(nbttagcompound.i("id"));
            if (class1 != null)
                entity = class1.getConstructor(new Class[]{Level.class}).newInstance(new Object[]{world});
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (entity != null) {
            entity.e(nbttagcompound);
        } else {
            System.out.println("Skipping Entity with id " + nbttagcompound.i("id"));
        }
        return entity;
    }

    public static Entity a(int i, Level world) {
        Entity entity = null;
        try {
            Class<Entity> class1 = (Class) c.get(Integer.valueOf(i));
            if (class1 != null)
                entity = class1.getConstructor(new Class[]{Level.class}).newInstance(new Object[]{world});
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (entity == null)
            System.out.println("Skipping Entity with id " + i);
        return entity;
    }

    public static int a(Entity entity) {
        return ((Integer) d.get(entity.getClass())).intValue();
    }

    public static String b(Entity entity) {
        return (String) b.get(entity.getClass());
    }

    public static String getEntityStringClimbing(Entity entity) {
        String returning = null;
        Class<?> obj = entity.getClass();
        while (returning == null && obj != null) {
            returning = (String) b.get(obj);
            obj = obj.getSuperclass();
        }
        return returning;
    }

    private static final Map a = new HashMap<>();

    private static final Map b = new HashMap<>();

    private static final Map c = new HashMap<>();

    private static final Map d = new HashMap<>();

    static {
        a(Arrow.class, "Arrow", 10);
        a(by.class, "Snowball", 11);
        a(ItemEntity.class, "Item", 1);
        a(Painting.class, "Painting", 9);
        a(LivingEntity.class, "Mob", 48);
        a(gz.class, "Monster", 49);
        a(gb.class, "Creeper", 50);
        a(fr.class, "Skeleton", 51);
        a(cn.class, "Spider", 52);
        a(nt.class, "Giant", 53);
        a(Zombie.class, "Zombie", 54);
        a(Slime.class, "Slime", 55);
        a(Ghast.class, "Ghast", 56);
        a(ZombiePigman.class, "PigZombie", 57);
        a(wh.class, "Pig", 90);
        a(dl.class, "Sheep", 91);
        a(bx.class, "Cow", 92);
        a(ww.class, "Chicken", 93);
        a(xt.class, "Squid", 94);
        a(gi.class, "Wolf", 95);
        a(qw.class, "PrimedTnt", 20);
        a(FallingTile.class, "FallingSand", 21);
        a(yl.class, "Minecart", 40);
        a(fz.class, "Boat", 41);
        a(ThrownEgg.class, "Egg", 12);
        a(Snowball.class, "Fireball", 13);
        a(lx.class, "FishingRod", 14);
        a(EntitySkeletonSword.class, "SkeletonSword", 58);
        a(EntitySkeletonBoss.class, "SkeletonBoss", 59);
        a(EntityBat.class, "Bat", 60);
        a(EntityRat.class, "Rat", 61);
        a(EntityNPC.class, "NPC", 62);
        a(EntitySkeletonRifle.class, "SkeletonRifle", 63);
        a(EntitySkeletonShotgun.class, "SkeletonShotgun", 64);
        a(EntityZombiePistol.class, "ZombiePistol", 65);
        a(EntityBomb.class, "Bomb", 1000);
        a(EntityBoomerang.class, "Boomerang", 1001);
        a(EntityArrowBomb.class, "Bomb Arrow", 1002);
        a(EntityHookshot.class, "Hookshot", 1003);
        a(EntityLivingScript.class, "Script", 1004);
    }
}
