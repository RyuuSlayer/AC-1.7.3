package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

import java.util.Random;

public class TileEntityEffect extends TileEntity {
    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.particleType = nbttagcompound.getString("particleType");
        this.particlesPerSpawn = nbttagcompound.getInt("particlesPerSpawn");
        this.ticksBetweenParticles = nbttagcompound.getInt("ticksBetweenParticles");
        this.isActivated = nbttagcompound.getBoolean("isActivated");
        this.offsetX = nbttagcompound.getFloat("offsetX");
        this.offsetY = nbttagcompound.getFloat("offsetY");
        this.offsetZ = nbttagcompound.getFloat("offsetZ");
        this.randX = nbttagcompound.getFloat("randX");
        this.randY = nbttagcompound.getFloat("randY");
        this.randZ = nbttagcompound.getFloat("randZ");
        this.floatArg1 = nbttagcompound.getFloat("floatArg1");
        this.floatArg2 = nbttagcompound.getFloat("floatArg2");
        this.floatArg3 = nbttagcompound.getFloat("floatArg3");
        this.floatRand1 = nbttagcompound.getFloat("floatRand1");
        this.floatRand2 = nbttagcompound.getFloat("floatRand2");
        this.floatRand3 = nbttagcompound.getFloat("floatRand3");
        this.changeFogColor = nbttagcompound.getInt("changeFogColor");
        this.fogR = nbttagcompound.getFloat("fogR");
        this.fogG = nbttagcompound.getFloat("fogG");
        this.fogB = nbttagcompound.getFloat("fogB");
        this.changeFogDensity = nbttagcompound.getInt("changeFogDensity");
        this.fogStart = nbttagcompound.getFloat("fogStart");
        this.fogEnd = nbttagcompound.getFloat("fogEnd");
        this.setOverlay = nbttagcompound.getBoolean("setOverlay");
        this.overlay = nbttagcompound.getString("overlay");
        this.revertTextures = nbttagcompound.getBoolean("revertTextures");
        this.replaceTextures = nbttagcompound.getBoolean("replaceTextures");
        this.textureReplacement = nbttagcompound.getString("textureReplacement");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        if (!this.particleType.equals(""))
            nbttagcompound.put("particleType", this.particleType);
        nbttagcompound.put("particlesPerSpawn", this.particlesPerSpawn);
        nbttagcompound.put("ticksBetweenParticles", this.ticksBetweenParticles);
        nbttagcompound.put("isActivated", this.isActivated);
        nbttagcompound.put("offsetX", this.offsetX);
        nbttagcompound.put("offsetY", this.offsetY);
        nbttagcompound.put("offsetZ", this.offsetZ);
        nbttagcompound.put("randX", this.randX);
        nbttagcompound.put("randY", this.randY);
        nbttagcompound.put("randZ", this.randZ);
        nbttagcompound.put("floatArg1", this.floatArg1);
        nbttagcompound.put("floatArg2", this.floatArg2);
        nbttagcompound.put("floatArg3", this.floatArg3);
        nbttagcompound.put("floatRand1", this.floatRand1);
        nbttagcompound.put("floatRand2", this.floatRand2);
        nbttagcompound.put("floatRand3", this.floatRand3);
        nbttagcompound.put("changeFogColor", this.changeFogColor);
        nbttagcompound.put("fogR", this.fogR);
        nbttagcompound.put("fogG", this.fogG);
        nbttagcompound.put("fogB", this.fogB);
        nbttagcompound.put("changeFogDensity", this.changeFogDensity);
        nbttagcompound.put("fogStart", this.fogStart);
        nbttagcompound.put("fogEnd", this.fogEnd);
        nbttagcompound.put("setOverlay", this.setOverlay);
        nbttagcompound.put("overlay", this.overlay);
        nbttagcompound.put("revertTextures", this.revertTextures);
        nbttagcompound.put("replaceTextures", this.replaceTextures);
        nbttagcompound.put("textureReplacement", this.textureReplacement);
    }

    public void n_() {
        if (this.checkTrigger) {
            this.isActivated = this.d.triggerManager.isActivated(this.e, this.f, this.g);
            this.checkTrigger = false;
        }
        if (this.isActivated)
            if (this.ticksBeforeParticle > 0) {
                this.ticksBeforeParticle--;
            } else {
                for (int i = 0; i < this.particlesPerSpawn; i++) {
                    this.d.a(this.particleType, this.e + 0.5D + this.randX * (2.0D * rand.nextDouble() - 1.0D) + this.offsetX, this.f + 0.5D + this.randY * (2.0D * rand.nextDouble() - 1.0D) + this.offsetY, this.g + 0.5D + this.randZ * (2.0D * rand.nextDouble() - 1.0D) + this.offsetZ, this.floatArg1 + this.floatRand1 * (2.0D * rand.nextDouble() - 1.0D), this.floatArg2 + this.floatRand2 * (2.0D * rand.nextDouble() - 1.0D), this.floatArg3 + this.floatRand3 * (2.0D * rand.nextDouble() - 1.0D));
                    this.ticksBeforeParticle = this.ticksBetweenParticles;
                }
            }
    }

    public boolean checkTrigger = true;

    public String particleType = "heart";

    public int particlesPerSpawn = 1;

    public int ticksBetweenParticles = 1;

    public boolean isActivated = false;

    public int ticksBeforeParticle = 0;

    public float offsetX = 0.0F;

    public float offsetY = 0.0F;

    public float offsetZ = 0.0F;

    public float randX = 0.0F;

    public float randY = 0.0F;

    public float randZ = 0.0F;

    public float floatArg1 = 0.0F;

    public float floatArg2 = 0.0F;

    public float floatArg3 = 0.0F;

    public float floatRand1 = 0.0F;

    public float floatRand2 = 0.0F;

    public float floatRand3 = 0.0F;

    public int changeFogColor;

    public float fogR;

    public float fogG;

    public float fogB;

    public int changeFogDensity;

    public float fogStart;

    public float fogEnd;

    public boolean setOverlay = false;

    public String overlay = "";

    public boolean revertTextures = false;

    public boolean replaceTextures = false;

    public String textureReplacement = "";

    private static final Random rand = new Random();
}
