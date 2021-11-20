package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

public interface IItemReload {

    public void reload(MixinItemInstance var1, MixinLevel var2, MixinPlayer var3);
}
