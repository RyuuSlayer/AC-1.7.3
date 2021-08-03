package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.entities.EntityArrowBomb;
import io.github.ryuu.adventurecraft.items.Items;

public class qz extends gm {
    public qz(int i) {
        super(i);
        this.bg = 1;
    }

    public iz a(iz itemstack, fd world, gs entityplayer) {
        iz curItem = entityplayer.c.b();
        iz offItem = entityplayer.c.getOffhandItem();
        if ((curItem != null && curItem.c == Items.bombArow.bf) || (offItem != null && offItem.c == Items.bombArow.bf)) {
            if (entityplayer.c.c(Items.bombArow.bf)) {
                world.a((sn)entityplayer, "random.bow", 1.0F, 1.0F / (b.nextFloat() * 0.4F + 0.8F));
                if (!world.B)
                    world.b((sn)new EntityArrowBomb(world, (ls)entityplayer));
            }
        } else if (entityplayer.c.c(gm.j.bf)) {
            world.a((sn)entityplayer, "random.bow", 1.0F, 1.0F / (b.nextFloat() * 0.4F + 0.8F));
            if (!world.B)
                world.b(new sl(world, (ls)entityplayer));
        }
        return itemstack;
    }
}
