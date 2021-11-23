package io.github.ryuu.adventurecraft.mixin.item.tool;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.item.tool.ToolMaterial;
import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SwordItem.class)
public class MixinSwordItem extends ItemType {

    @Shadow()
    private final int field_1314;

    public MixinSwordItem(int i, ToolMaterial enumtoolmaterial) {
        super(i);
        this.maxStackSize = 1;
        this.setDurability(enumtoolmaterial.getDurability());
        this.field_1314 = 4 + enumtoolmaterial.getAttackDamage() * 2;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public float method_438(ItemInstance item, Tile tile) {
        return tile.id != Tile.WEB.id ? 1.5f : 15.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean postHit(ItemInstance itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean postMine(ItemInstance itemstack, int i, int j, int k, int l, LivingEntity entityliving) {
        return true;
    }
}
