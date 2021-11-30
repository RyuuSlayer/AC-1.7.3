package io.github.ryuu.adventurecraft.mixin.util.io;

import net.minecraft.util.io.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Set;

@Mixin(CompoundTag.class)
public abstract class MixinCompoundTag extends AbstractTag implements ExCompoundTag {

    @Shadow
    private Map<String, AbstractTag> data;

    @Shadow
    public abstract byte getByte(String string);

    // TODO: remove because it's cursed?
    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void put(String key, short item) {
        if ((byte) item == item) {
            this.put(key, (byte) item);
        } else {
            this.data.put(key, new ShortTag(item).setKey(key));
        }
    }

    // TODO: remove because it's cursed?
    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void put(String key, int item) {
        if ((short) item == item) {
            this.put(key, (short) item);
        } else {
            this.data.put(key, new IntTag(item).setKey(key));
        }
    }

    // TODO: remove because it's cursed?
    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void put(String key, long item) {
        if ((long) ((int) item) == item) {
            this.put(key, (int) item);
        } else {
            this.data.put(key, new LongTag(item).setKey(key));
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public short getShort(String key) {
        if (!this.data.containsKey(key)) {
            return 0;
        }
        try {
            return ((ShortTag) this.data.get(key)).data;
        } catch (ClassCastException e) {
            return this.getByte(key);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public int getInt(String key) {
        if (!this.data.containsKey(key)) {
            return 0;
        }
        try {
            return ((IntTag) this.data.get(key)).data;
        } catch (ClassCastException e) {
            return this.getShort(key);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public long getLong(String key) {
        if (!this.data.containsKey(key)) {
            return 0L;
        }
        try {
            return ((LongTag) this.data.get(key)).data;
        } catch (ClassCastException e) {
            return this.getInt(key);
        }
    }

    @Override
    public Set<String> getKeys() {
        return this.data.keySet();
    }
}
