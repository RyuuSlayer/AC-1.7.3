package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityEffect;
import io.github.ryuu.adventurecraft.extensions.client.colour.ExFoliageColour;
import io.github.ryuu.adventurecraft.extensions.client.colour.ExGrassColour;
import io.github.ryuu.adventurecraft.extensions.client.render.*;
import io.github.ryuu.adventurecraft.extensions.client.texture.ExTextureManager;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.gui.GuiEffect;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TerrainImage;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

import java.io.*;

public class BlockEffect extends TileWithEntity {

    static boolean needsReloadForRevert = true;

    protected BlockEffect(int i, int j) {
        super(i, j, Material.AIR);
        this.hardness(5.0f);
        this.sounds(Tile.METAL_SOUNDS);
        ((ExTile) this).setTextureNum(3);
    }

    public static void revertTextures(Level world) {
        ((ExTextureManager) AccessMinecraft.getInstance().textureManager).revertTextures();
        if (needsReloadForRevert) {
            ExGrassColour.loadGrass("/misc/grasscolor.png");
            ExFoliageColour.loadFoliage("/misc/foliagecolor.png");
            TerrainImage.loadWaterMap(new File(((ExLevel) world).getLevelDir(), "watermap.png"));
            TerrainImage.loadBiomeMap(new File(((ExLevel) world).getLevelDir(), "biomemap.png"));
            AccessMinecraft.getInstance().worldRenderer.method_1148();
            needsReloadForRevert = false;
        }
        ((ExLevelProperties) world.getProperties()).revertTextures();
    }

    public static boolean replaceTexture(Level world, String textureToReplace, String replacementTexture) {
        String lTextureToReplace = textureToReplace.toLowerCase();
        if (!((ExLevelProperties) world.getProperties()).addReplacementTexture(textureToReplace, replacementTexture)) {
            return false;
        }
        if (lTextureToReplace.equals("/watermap.png")) {
            TerrainImage.loadWaterMap(new File(((ExLevel) world).getLevelDir(), replacementTexture));
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals("/biomemap.png")) {
            TerrainImage.loadBiomeMap(new File(((ExLevel) world).getLevelDir(), replacementTexture));
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals("/misc/grasscolor.png")) {
            ExGrassColour.loadGrass(replacementTexture);
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals("/misc/foliagecolor.png")) {
            ExFoliageColour.loadFoliage(replacementTexture);
            needsReloadForRevert = true;
            return true;
        }
        if (lTextureToReplace.equals("/custom_fire.png")) {
            ExFireTextureBinder.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_lava_flowing.png")) {
            ExFlowingLavaTextureBinder2.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_lava_still.png")) {
            ExFlowingLavaTextureBinder.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_portal.png")) {
            ExPortalTextureBinder.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_water_flowing.png")) {
            ExFlowingWaterTextureBinder.loadImage(replacementTexture);
            return true;
        }
        if (lTextureToReplace.equals("/custom_water_still.png")) {
            ExFlowingWaterTextureBinder2.loadImage(replacementTexture);
            return true;
        }
        ((ExTextureManager) AccessMinecraft.getInstance().textureManager).replaceTexture(textureToReplace, replacementTexture);
        return false;
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityEffect();
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public boolean canBeTriggered() {
        return true;
    }

    @Override
    public void onTriggerActivated(Level world, int i, int j, int k) {
        ExLevelProperties properties = (ExLevelProperties) world.getProperties();
        TileEntityEffect obj = (TileEntityEffect) world.getTileEntity(i, j, k);
        obj.isActivated = true;
        obj.ticksBeforeParticle = 0;
        if (obj.changeFogColor == 1) {
            properties.setOverrideFogColor(true);
            properties.setFogR(obj.fogR);
            properties.setFogG(obj.fogG);
            properties.setFogB(obj.fogB);
        } else if (obj.changeFogColor == 2) {
            properties.setOverrideFogColor(false);
        }
        if (obj.changeFogDensity == 1) {
            properties.setOverrideFogDensity(true);
            properties.setFogStart(obj.fogStart);
            properties.setFogEnd(obj.fogEnd);
        } else if (obj.changeFogDensity == 2) {
            properties.setOverrideFogDensity(false);
        }
        if (obj.setOverlay) {
            properties.setOverlay(obj.overlay);
        }
        if (obj.replaceTextures) {
            this.replaceTextures(world, obj.textureReplacement);
        } else if (obj.revertTextures) {
            BlockEffect.revertTextures(world);
        }
    }

    public void replaceTextures(Level world, String replacement) {
        boolean needsReload = false;
        File replacementFile = new File(((ExLevel) world).getLevelDir(), "textureReplacement/" + replacement);
        if (replacementFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(replacementFile));
                try {
                    while (reader.ready()) {
                        String line = reader.readLine();
                        String[] parts = line.split(",", 2);
                        if (parts.length != 2) continue;
                        needsReload |= BlockEffect.replaceTexture(world, parts[0], parts[1]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        if (needsReload) {
            AccessMinecraft.getInstance().worldRenderer.method_1148();
        }
    }

    @Override
    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        TileEntityEffect obj = (TileEntityEffect) world.getTileEntity(i, j, k);
        obj.isActivated = false;
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active && player.getHeldItem() != null && player.getHeldItem().itemId == Items.cursor.id) {
            TileEntityEffect obj = (TileEntityEffect) level.getTileEntity(x, y, z);
            GuiEffect.showUI(obj);
            return true;
        }
        return false;
    }
}
