package io.github.ryuu.adventurecraft.mixin.entity;

import io.github.ryuu.adventurecraft.entities.*;
import net.minecraft.entity.*;
import net.minecraft.entity.animal.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.entity.projectile.Snowball;
import net.minecraft.entity.projectile.ThrownEgg;
import net.minecraft.entity.projectile.ThrownSnowball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(EntityRegistry.class)
public abstract class MixinEntityRegistry implements AccessEntityRegistry {

    static {
        register(Arrow.class, "Arrow", 10);
        register(ThrownSnowball.class, "Snowball", 11);
        register(ItemEntity.class, "Item", 1);
        register(Painting.class, "Painting", 9);
        register(LivingEntity.class, "Mob", 48);
        register(Monster.class, "Monster", 49);
        register(Creeper.class, "Creeper", 50);
        register(Skeleton.class, "Skeleton", 51);
        register(Spider.class, "Spider", 52);
        register(Giant.class, "Giant", 53);
        register(Zombie.class, "Zombie", 54);
        register(Slime.class, "Slime", 55);
        register(Ghast.class, "Ghast", 56);
        register(ZombiePigman.class, "PigZombie", 57);
        register(Pig.class, "Pig", 90);
        register(Sheep.class, "Sheep", 91);
        register(Cow.class, "Cow", 92);
        register(Chicken.class, "Chicken", 93);
        register(Squid.class, "Squid", 94);
        register(Wolf.class, "Wolf", 95);
        register(PrimedTnt.class, "PrimedTnt", 20);
        register(FallingTile.class, "FallingSand", 21);
        register(Minecart.class, "Minecart", 40);
        register(Boat.class, "Boat", 41);
        register(ThrownEgg.class, "Egg", 12);
        register(Snowball.class, "Fireball", 13);
        register(FishHook.class, "FishingRod", 14);
        register(EntitySkeletonSword.class, "SkeletonSword", 58);
        register(EntitySkeletonBoss.class, "SkeletonBoss", 59);
        register(EntityBat.class, "Bat", 60);
        register(EntityRat.class, "Rat", 61);
        register(EntityNPC.class, "NPC", 62);
        register(EntitySkeletonRifle.class, "SkeletonRifle", 63);
        register(EntitySkeletonShotgun.class, "SkeletonShotgun", 64);
        register(EntityZombiePistol.class, "ZombiePistol", 65);
        register(EntityBomb.class, "Bomb", 1000);
        register(EntityBoomerang.class, "Boomerang", 1001);
        register(EntityArrowBomb.class, "Bomb Arrow", 1002);
        register(EntityHookshot.class, "Hookshot", 1003);
        register(EntityLivingScript.class, "Script", 1004);
    }

    @Shadow
    private static void register(Class class1, String s, int i) {
        throw new AssertionError();
    }
}
