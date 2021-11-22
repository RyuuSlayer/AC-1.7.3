package io.github.ryuu.adventurecraft.mixin.entity;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Painting;
import net.minecraft.entity.PaintingMotif;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.material.Material;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Painting.class)
public class MixinPainting extends Entity {

    @Shadow()
    private int field_1389 = 0;

    public int dir = 0;

    public int tileX;

    public int tileY;

    public int tileZ;

    public PaintingMotif motive;

    public MixinPainting(Level world) {
        super(world);
        this.standingEyeHeight = 0.0f;
        this.setSize(0.5f, 0.5f);
    }

    public MixinPainting(Level world, int i, int j, int k, int l) {
        this(world);
        this.tileX = i;
        this.tileY = j;
        this.tileZ = k;
        ArrayList arraylist = new ArrayList();
        PaintingMotif[] aenumart = PaintingMotif.values();
        int i1 = aenumart.length;
        for (int j1 = 0; j1 < i1; ++j1) {
            PaintingMotif enumart;
            this.motive = enumart = aenumart[j1];
            this.setDir(l);
            if (!this.method_1193())
                continue;
            arraylist.add((Object) enumart);
        }
        if (arraylist.size() > 0) {
            this.motive = (PaintingMotif) ((Object) arraylist.get(this.rand.nextInt(arraylist.size())));
        }
        this.setDir(l);
    }

    public MixinPainting(Level world, int i, int j, int k, int l, String s) {
        this(world);
        this.tileX = i;
        this.tileY = j;
        this.tileZ = k;
        for (PaintingMotif enumart : PaintingMotif.values()) {
            if (!enumart.id.equals((Object) s))
                continue;
            this.motive = enumart;
            break;
        }
        this.setDir(l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setDir(int i) {
        this.dir = i;
        this.prevYaw = this.yaw = (float) (i * 90);
        float f = this.motive.width;
        float f1 = this.motive.height;
        float f2 = this.motive.width;
        if (i == 0 || i == 2) {
            f2 = 0.5f;
        } else {
            f = 0.5f;
        }
        f /= 32.0f;
        f1 /= 32.0f;
        f2 /= 32.0f;
        float f3 = (float) this.tileX + 0.5f;
        float f4 = (float) this.tileY + 0.5f;
        float f5 = (float) this.tileZ + 0.5f;
        float f6 = 0.5625f;
        if (i == 0) {
            f5 -= f6;
        }
        if (i == 1) {
            f3 -= f6;
        }
        if (i == 2) {
            f5 += f6;
        }
        if (i == 3) {
            f3 += f6;
        }
        if (i == 0) {
            f3 -= this.method_1192(this.motive.width);
        }
        if (i == 1) {
            f5 += this.method_1192(this.motive.width);
        }
        if (i == 2) {
            f3 += this.method_1192(this.motive.width);
        }
        if (i == 3) {
            f5 -= this.method_1192(this.motive.width);
        }
        this.setPosition(f3, f4 += this.method_1192(this.motive.height), f5);
        float f7 = -0.00625f;
        this.boundingBox.set(f3 - f - f7, f4 - f1 - f7, f5 - f2 - f7, f3 + f + f7, f4 + f1 + f7, f5 + f2 + f7);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private float method_1192(int i) {
        if (i == 32) {
            return 0.5f;
        }
        return i != 64 ? 0.0f : 0.5f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1193() {
        if (this.level.method_190(this, this.boundingBox).size() > 0) {
            return false;
        }
        int i = this.motive.width / 16;
        int j = this.motive.height / 16;
        int k = this.tileX;
        int l = this.tileY;
        int i1 = this.tileZ;
        if (this.dir == 0) {
            k = MathsHelper.floor(this.x - (double) ((float) this.motive.width / 32.0f));
        }
        if (this.dir == 1) {
            i1 = MathsHelper.floor(this.z - (double) ((float) this.motive.width / 32.0f));
        }
        if (this.dir == 2) {
            k = MathsHelper.floor(this.x - (double) ((float) this.motive.width / 32.0f));
        }
        if (this.dir == 3) {
            i1 = MathsHelper.floor(this.z - (double) ((float) this.motive.width / 32.0f));
        }
        l = MathsHelper.floor(this.y - (double) ((float) this.motive.height / 32.0f));
        for (int j1 = 0; j1 < i; ++j1) {
            for (int k1 = 0; k1 < j; ++k1) {
                Material material = this.dir == 0 || this.dir == 2 ? this.level.getMaterial(k + j1, l + k1, this.tileZ) : this.level.getMaterial(this.tileX, l + k1, i1 + j1);
                if (material.isSolid())
                    continue;
                return false;
            }
        }
        List list = this.level.getEntities(this, this.boundingBox);
        for (int l1 = 0; l1 < list.size(); ++l1) {
            if (!(list.get(l1) instanceof Painting))
                continue;
            return false;
        }
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeCustomDataToTag(CompoundTag tag) {
        tag.put("Dir", (byte) this.dir);
        tag.put("Motive", this.motive.id);
        tag.put("TileX", this.tileX);
        tag.put("TileY", this.tileY);
        tag.put("TileZ", this.tileZ);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readCustomDataFromTag(CompoundTag tag) {
        this.dir = tag.getByte("Dir");
        this.tileX = tag.getInt("TileX");
        this.tileY = tag.getInt("TileY");
        this.tileZ = tag.getInt("TileZ");
        String s = tag.getString("Motive");
        for (PaintingMotif enumart : PaintingMotif.values()) {
            if (!enumart.id.equals((Object) s))
                continue;
            this.motive = enumart;
        }
        if (this.motive == null) {
            this.motive = PaintingMotif.Kebab;
        }
        this.setDir(this.dir);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void move(double d, double d1, double d2) {
        if (!this.level.isClient && d * d + d1 * d1 + d2 * d2 > 0.0) {
            this.remove();
            this.level.spawnEntity(new ItemEntity(this.level, this.x, this.y, this.z, new ItemInstance(ItemType.painting)));
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1322(double d, double d1, double d2) {
        if (!this.level.isClient && d * d + d1 * d1 + d2 * d2 > 0.0) {
            this.remove();
            this.level.spawnEntity(new ItemEntity(this.level, this.x, this.y, this.z, new ItemInstance(ItemType.painting)));
        }
    }
}
