package io.github.ryuu.adventurecraft.blocks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityEffect;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TerrainImage;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.maths.Box;

public class BlockEffect extends TileWithEntity {
    protected BlockEffect(int i, int j) {
        super(i, j, ln.a);
    }

    protected TileEntity a_() {
        return new TileEntityEffect();
    }

    public boolean c() {
        return false;
    }

    public Box e(Level world, int i, int j, int k) {
        return null;
    }

    public boolean shouldRender(xp blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    public boolean v_() {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityEffect obj = (TileEntityEffect)world.b(i, j, k);
        obj.isActivated = true;
        obj.ticksBeforeParticle = 0;
        if (obj.changeFogColor == 1) {
            world.x.overrideFogColor = true;
            world.x.fogR = obj.fogR;
            world.x.fogG = obj.fogG;
            world.x.fogB = obj.fogB;
        } else if (obj.changeFogColor == 2) {
            world.x.overrideFogColor = false;
        }
        if (obj.changeFogDensity == 1) {
            world.x.overrideFogDensity = true;
            world.x.fogStart = obj.fogStart;
            world.x.fogEnd = obj.fogEnd;
        } else if (obj.changeFogDensity == 2) {
            world.x.overrideFogDensity = false;
        }
        if (obj.setOverlay)
            world.x.overlay = obj.overlay;
        if (obj.replaceTextures) {
            replaceTextures(world, obj.textureReplacement);
        } else if (obj.revertTextures) {
            revertTextures(world);
        }
    }

    public static void revertTextures(Level world) {
        Minecraft.minecraftInstance.p.revertTextures();
        if (needsReloadForRevert) {
            ia.loadGrass("/misc/grasscolor.png");
            jh.loadFoliage("/misc/foliagecolor.png");
            TerrainImage.loadWaterMap(new File(world.levelDir, "watermap.png"));
            TerrainImage.loadBiomeMap(new File(world.levelDir, "biomemap.png"));
            Minecraft.minecraftInstance.g.e();
            needsReloadForRevert = false;
        }
        world.x.revertTextures();
    }

    public void replaceTextures(Level world, String replacement) {
        boolean needsReload = false;
        File replacementFile = new File(world.levelDir, "textureReplacement/" + replacement);
        if (replacementFile.exists())
            try {
                BufferedReader reader = new BufferedReader(new FileReader(replacementFile));
                try {
                    while (reader.ready()) {
                        String line = reader.readLine();
                        String[] parts = line.split(",", 2);
                        if (parts.length == 2)
                            needsReload |= replaceTexture(world, parts[0], parts[1]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        if (needsReload)
            Minecraft.minecraftInstance.g.e();
    }

    public static boolean replaceTexture(Level world, String textureToReplace, String replacementTexture) {
        String lTextureToReplace = textureToReplace.toLowerCase();
        if (!world.x.addReplacementTexture(textureToReplace, replacementTexture))
            return false;
        if (lTextureToReplace.equals("/watermap.png")) {
            TerrainImage.loadWaterMap(new File(world.levelDir, replacementTexture));
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals("/biomemap.png")) {
            TerrainImage.loadBiomeMap(new File(world.levelDir, replacementTexture));
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals("/misc/grasscolor.png")) {
            ia.loadGrass(replacementTexture);
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals("/misc/foliagecolor.png")) {
            jh.loadFoliage(replacementTexture);
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals("/custom_fire.png")) {
            sd.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_lava_flowing.png")) {
            if.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_lava_still.png")) {
            cg.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_portal.png")) {
            hs.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_water_flowing.png")) {
            oh.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_water_still.png")) {
            vs.loadImage(replacementTexture);
            return true;
        }
        Minecraft.minecraftInstance.p.replaceTexture(textureToReplace, replacementTexture);
        return false;
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        TileEntityEffect obj = (TileEntityEffect)world.b(i, j, k);
        obj.isActivated = false;
    }

    public boolean a(Level world, int i, int j, int k, gs entityplayer) {
        if (DebugMode.active && entityplayer.G() != null && (entityplayer.G()).c == Items.cursor.bf) {
            TileEntityEffect obj = (TileEntityEffect)world.b(i, j, k);
            GuiEffect.showUI(obj);
            return true;
        }
        return false;
    }

    static boolean needsReloadForRevert = true;
}