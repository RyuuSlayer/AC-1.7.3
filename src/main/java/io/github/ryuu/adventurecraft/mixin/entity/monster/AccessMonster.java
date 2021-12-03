package io.github.ryuu.adventurecraft.mixin.entity.monster;

import net.minecraft.entity.monster.Monster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Monster.class)
public interface AccessMonster {

    @Accessor
    int getAttackDamage();

    @Accessor
    void setAttackDamage(int attackDamage);
}
