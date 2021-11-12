package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.entities.EntityArrowBomb;
import io.github.ryuu.adventurecraft.items.Items;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

public class MixinBowItem extends ItemType {
    public MixinBowItem(int i) {
        super(i);
        this.bg = 1;
    }

    public ItemInstance a(ItemInstance itemstack, Level world, Player entityplayer) {
        ItemInstance curItem = entityplayer.c.b();
        ItemInstance offItem = entityplayer.c.getOffhandItem();
        if ((curItem != null && curItem.c == Items.bombArow.bf) || (offItem != null && offItem.c == Items.bombArow.bf)) {
            if (entityplayer.c.c(Items.bombArow.bf)) {
                world.a((net.minecraft.entity.Entity) entityplayer, "random.bow", 1.0F, 1.0F / (b.nextFloat() * 0.4F + 0.8F));
                if (!world.B)
                    world.b((Entity) new EntityArrowBomb(world, entityplayer));
            }
        } else if (entityplayer.c.c(ItemType.j.bf)) {
            world.a((net.minecraft.entity.Entity) entityplayer, "random.bow", 1.0F, 1.0F / (b.nextFloat() * 0.4F + 0.8F));
            if (!world.B)
                world.b(new Arrow(world, entityplayer));
        }
        return itemstack;
    }
}
