package io.github.ryuu.adventurecraft.mixin.client.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TileParticle;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ParticleManager.class)
public class MixinParticleManager {

    @Shadow()
    protected Level level;

    private List[] particles = new List[6];

    private TextureManager textureManager;

    private Random rand = new Random();

    public MixinParticleManager(Level world, TextureManager textureManager) {
        if (world != null) {
            this.level = world;
        }
        this.textureManager = textureManager;
        for (int i = 0; i < 6; ++i) {
            this.particles[i] = new ArrayList();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void addParticle(Particle particle) {
        int i = particle.method_2003();
        if (this.particles[i].size() >= 4000) {
            this.particles[i].remove(0);
        }
        this.particles[i].add((Object) particle);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_320() {
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < this.particles[i].size(); ++j) {
                Particle entityfx = (Particle) this.particles[i].get(j);
                entityfx.tick();
                if (!entityfx.removed)
                    continue;
                this.particles[i].remove(j--);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public List getEffectsWithinAABB(Box axisalignedbb) {
        ArrayList arraylist = new ArrayList();
        for (int i = 0; i < 6; ++i) {
            for (Object obj : this.particles[i]) {
                Entity p = (Entity) obj;
                if (!(axisalignedbb.minX <= p.x) || !(axisalignedbb.maxX >= p.x) || !(axisalignedbb.minY <= p.y) || !(axisalignedbb.maxY >= p.y) || !(axisalignedbb.minZ <= p.z) || !(axisalignedbb.maxZ >= p.z))
                    continue;
                arraylist.add((Object) p);
            }
        }
        return arraylist;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_324(Entity entity, float f) {
        float f1 = MathsHelper.cos(entity.yaw * 3.141593f / 180.0f);
        float f2 = MathsHelper.sin(entity.yaw * 3.141593f / 180.0f);
        float f3 = -f2 * MathsHelper.sin(entity.pitch * 3.141593f / 180.0f);
        float f4 = f1 * MathsHelper.sin(entity.pitch * 3.141593f / 180.0f);
        float f5 = MathsHelper.cos(entity.pitch * 3.141593f / 180.0f);
        Particle.field_2645 = entity.prevRenderX + (entity.x - entity.prevRenderX) * (double) f;
        Particle.field_2646 = entity.prevRenderY + (entity.y - entity.prevRenderY) * (double) f;
        Particle.field_2647 = entity.prevRenderZ + (entity.z - entity.prevRenderZ) * (double) f;
        for (int i = 0; i < 5; ++i) {
            if (this.particles[i].size() == 0)
                continue;
            int j = 0;
            if (i == 0) {
                j = this.textureManager.getTextureId("/particles.png");
            }
            if (i == 1) {
                j = this.textureManager.getTextureId("/terrain.png");
            }
            if (i == 2) {
                j = this.textureManager.getTextureId("/gui/items.png");
            }
            if (i == 3) {
                j = this.textureManager.getTextureId("/terrain2.png");
            }
            if (i == 4) {
                j = this.textureManager.getTextureId("/terrain3.png");
            }
            GL11.glBindTexture((int) 3553, (int) j);
            Tessellator tessellator = Tessellator.INSTANCE;
            tessellator.start();
            for (int k = 0; k < this.particles[i].size(); ++k) {
                Particle entityfx = (Particle) this.particles[i].get(k);
                entityfx.method_2002(tessellator, f, f1, f5, f2, f3, f4);
            }
            tessellator.draw();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_327(Entity entity, float f) {
        int byte0 = 5;
        if (this.particles[byte0].size() == 0) {
            return;
        }
        Tessellator tessellator = Tessellator.INSTANCE;
        for (int i = 0; i < this.particles[byte0].size(); ++i) {
            Particle entityfx = (Particle) this.particles[byte0].get(i);
            entityfx.method_2002(tessellator, f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_323(Level world) {
        this.level = world;
        for (int i = 0; i < 6; ++i) {
            this.particles[i].clear();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void addTileBreakParticles(int i, int j, int k, int l, int i1) {
        if (l == 0) {
            return;
        }
        Tile block = Tile.BY_ID[l];
        int j1 = 4;
        for (int k1 = 0; k1 < j1; ++k1) {
            for (int l1 = 0; l1 < j1; ++l1) {
                for (int i2 = 0; i2 < j1; ++i2) {
                    double d = (double) i + ((double) k1 + 0.5) / (double) j1;
                    double d1 = (double) j + ((double) l1 + 0.5) / (double) j1;
                    double d2 = (double) k + ((double) i2 + 0.5) / (double) j1;
                    int j2 = this.rand.nextInt(6);
                    this.addParticle(new TileParticle(this.level, d, d1, d2, d - (double) i - 0.5, d1 - (double) j - 0.5, d2 - (double) k - 0.5, block, j2, i1).method_1856(i, j, k));
                }
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void addTileClickParticle(int x, int y, int z, int facing) {
        int i1 = this.level.getTileId(x, y, z);
        if (i1 == 0) {
            return;
        }
        Tile block = Tile.BY_ID[i1];
        float f = 0.1f;
        double d = (double) x + this.rand.nextDouble() * (block.maxX - block.minX - (double) (f * 2.0f)) + (double) f + block.minX;
        double d1 = (double) y + this.rand.nextDouble() * (block.maxY - block.minY - (double) (f * 2.0f)) + (double) f + block.minY;
        double d2 = (double) z + this.rand.nextDouble() * (block.maxZ - block.minZ - (double) (f * 2.0f)) + (double) f + block.minZ;
        if (facing == 0) {
            d1 = (double) y + block.minY - (double) f;
        }
        if (facing == 1) {
            d1 = (double) y + block.maxY + (double) f;
        }
        if (facing == 2) {
            d2 = (double) z + block.minZ - (double) f;
        }
        if (facing == 3) {
            d2 = (double) z + block.maxZ + (double) f;
        }
        if (facing == 4) {
            d = (double) x + block.minX - (double) f;
        }
        if (facing == 5) {
            d = (double) x + block.maxX + (double) f;
        }
        this.addParticle(new TileParticle(this.level, d, d1, d2, 0.0, 0.0, 0.0, block, facing, this.level.getTileMeta(x, y, z)).method_1856(x, y, z).calculateVelocity(0.2f).method_2001(0.6f));
    }
}
