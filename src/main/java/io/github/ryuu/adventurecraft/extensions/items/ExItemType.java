package io.github.ryuu.adventurecraft.extensions.items;

import io.github.ryuu.adventurecraft.items.DamageableItemType;
import io.github.ryuu.adventurecraft.items.DelayableUseItemType;
import io.github.ryuu.adventurecraft.items.LeftClickItemType;
import io.github.ryuu.adventurecraft.items.SlotChangeCallbackItemType;

public interface ExItemType extends DelayableUseItemType, LightingItemType, MuzzleFlashItemType, SlotChangeCallbackItemType, DamageableItemType, LeftClickUseItemType, LeftClickItemType {

    boolean mainActionLeftClick();
}
