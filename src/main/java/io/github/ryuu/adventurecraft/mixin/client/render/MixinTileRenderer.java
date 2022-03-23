package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.blocks.BlockOverlay;
import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTree;
import io.github.ryuu.adventurecraft.extensions.client.render.ExTileRenderer;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.tile.ExGrassTile;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(TileRenderer.class)
public abstract class MixinTileRenderer implements AccessTileRenderer, ExTileRenderer {

    private final Random rand = new Random();

    @Shadow
    public static boolean field_67;

    @Shadow
    private int field_55;

    @Shadow
    private TileView field_82;

    @Shadow
    private int field_83;

    @Shadow
    private boolean field_85;

    @Shadow
    private boolean field_92;

    @Shadow
    private float field_93;

    @Shadow
    private float field_94;

    @Shadow
    private float field_95;

    @Shadow
    private float field_96;

    @Shadow
    private float field_97;

    @Shadow
    private float field_98;

    @Shadow
    private float field_99;

    @Shadow
    private float field_100;

    @Shadow
    private float field_101;

    @Shadow
    private float field_102;

    @Shadow
    private float field_103;

    @Shadow
    private float field_104;

    @Shadow
    private float field_105;

    @Shadow
    private float field_41;

    @Shadow
    private float field_42;

    @Shadow
    private float field_43;

    @Shadow
    private float field_44;

    @Shadow
    private float field_45;

    @Shadow
    private float field_46;

    @Shadow
    private float field_47;

    @Shadow
    private float field_48;

    @Shadow
    private float field_49;

    @Shadow
    private float field_50;

    @Shadow
    private float field_51;

    @Shadow
    private float field_52;

    @Shadow
    private float field_53;

    @Shadow
    private float field_54;

    @Shadow
    private float field_56;

    @Shadow
    private float field_57;

    @Shadow
    private float field_58;

    @Shadow
    private float field_59;

    @Shadow
    private float field_60;

    @Shadow
    private float field_61;

    @Shadow
    private float field_62;

    @Shadow
    private float field_63;

    @Shadow
    private float field_64;

    @Shadow
    private float field_65;

    @Shadow
    private float field_66;

    @Shadow
    private float field_68;

    @Shadow
    private boolean field_69;

    @Shadow
    private boolean field_70;

    @Shadow
    private boolean field_71;

    @Shadow
    private boolean field_72;

    @Shadow
    private boolean field_73;

    @Shadow
    private boolean field_74;

    @Shadow
    private boolean field_75;

    @Shadow
    private boolean field_76;

    @Shadow
    private boolean field_77;

    @Shadow
    private boolean field_78;

    @Shadow
    private boolean field_79;

    @Shadow
    private boolean field_80;

    @Shadow
    public abstract void method_46(Tile arg, double d, double d1, double d2, int i);

    @Shadow
    public abstract void method_55(Tile arg, double d, double d1, double d2, int i);

    @Shadow
    public abstract boolean method_57(Tile arg, int i, int j, int k);

    @Shadow
    public abstract void method_61(Tile arg, double d, double d1, double d2, int i);

    @Shadow
    public abstract void method_65(Tile arg, double d, double d1, double d2, int i);

    @Shadow
    public abstract void method_67(Tile arg, double d, double d1, double d2, int i);

    @Shadow
    public abstract void method_69(Tile arg, double d, double d1, double d2, int i);

    @Shadow
    public abstract boolean method_76(Tile arg, int i, int j, int k);

    @Inject(method = "method_57", at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/tile/Tile;method_1621()I",
            shift = At.Shift.AFTER),
            cancellable = true)
    private void shouldRenderMethod_57(Tile block, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        if (!((ExTile) block).shouldRender(this.field_82, i, j, k)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "method_57", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
            value = "RETURN",
            ordinal = 17,
            shift = At.Shift.BEFORE),
            cancellable = true)
    private void renderACTilesInMethod_57(Tile block, int i, int j, int k, CallbackInfoReturnable<Boolean> cir, int var5) {
        if (var5 == 30) {
            int blockID;
            if (!(this.field_82 == null || this.field_83 != -1 || (blockID = this.field_82.getTileId(i, j + 1, k)) != 0 && ((ExTile) Tile.BY_ID[blockID]).shouldRender(this.field_82, i, j + 1, k))) {
                this.renderGrass(block, i, j, k);
            }
            cir.setReturnValue(this.method_76(block, i, j, k));
        } else if (var5 == 31) {
            boolean rendered = this.method_76(block, i, j, k);
            if (((ExLevel) AccessMinecraft.getInstance().level).getTriggerManager().isActivated(i, j, k)) {
                Tessellator.INSTANCE.colour(1.0f, 1.0f, 1.0f);
                this.field_83 = 99;
            } else {
                this.field_83 = 115;
            }
            this.method_45(block, i, (double) j + 0.25, k, 0.0, 0.0);
            this.field_83 = -1;
            cir.setReturnValue(rendered);
        } else if (var5 == 32) {
            cir.setReturnValue(this.renderSpikes(block, i, j, k));
        } else if (var5 == 33) {
            cir.setReturnValue(this.renderTable(block, i, j, k));
        } else if (var5 == 34) {
            cir.setReturnValue(this.renderChair(block, i, j, k));
        } else if (var5 == 35) {
            cir.setReturnValue(this.renderRope(block, i, j, k));
        } else if (var5 == 36) {
            cir.setReturnValue(this.renderBlockTree(block, i, j, k));
        } else if (var5 == 37) {
            cir.setReturnValue(this.renderBlockOverlay(block, i, j, k));
        } else if (var5 == 38) {
            cir.setReturnValue(this.renderBlockSlope(block, i, j, k));
        }
    }

    @Redirect(method = "method_62", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/tile/Tile;LUMINANCES:[I",
            opcode = Opcodes.GETSTATIC,
            args = "array=get"))
    private int redirectLuminanceInMethod_62(int[] array, int index) {
        return 0;
    }

    @ModifyVariable(method = "method_62", name = "var7", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/tile/Tile;LUMINANCES:[I",
            shift = At.Shift.BEFORE))
    private float modifyLuminanceAtMethod_62(float var7, Tile arg, int i, int j, int k) {
        if (((ExTile) arg).getBlockLightValue(this.field_82, i, j, k) > 0) {
            var7 = 1.0f;
        }
        return var7;
    }

    @Redirect(method = "method_82", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/tile/Tile;LUMINANCES:[I",
            opcode = Opcodes.GETSTATIC,
            args = "array=get"))
    private int redirectLuminanceInMethod_82(int[] array, int index) {
        return 0;
    }

    @ModifyVariable(method = "method_82", name = "var9", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/tile/Tile;LUMINANCES:[I",
            shift = At.Shift.BEFORE))
    private float modifyLuminanceAtMethod_82(float var9, Tile arg, int i, int j, int k) {
        if (((ExTile) arg).getBlockLightValue(this.field_82, i, j, k) > 0) {
            var9 = 1.0f;
        }
        return var9;
    }

    @Redirect(method = "method_68", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/tile/Tile;LUMINANCES:[I",
            opcode = Opcodes.GETSTATIC,
            args = "array=get"))
    private int redirectLuminanceInMethod_68(int[] array, int index) {
        return 0;
    }

    @ModifyVariable(method = "method_68", name = "var13", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/tile/Tile;LUMINANCES:[I",
            shift = At.Shift.BEFORE))
    private float modifyLuminanceAtMethod_68(float var9, Tile arg, int i, int j, int k) {
        if (((ExTile) arg).getBlockLightValue(this.field_82, i, j, k) > 0) {
            var9 = 1.0f;
        }
        return var9;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void method_45(Tile block, double d, double d1, double d2, double d3, double d4) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int i = block.getTextureForSide(0);
        if (this.field_83 >= 0) {
            i = this.field_83;
        }
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        float f = (float) j / 256.0f;
        float f1 = ((float) j + 15.99f) / 256.0f;
        float f2 = (float) k / 256.0f;
        float f3 = ((float) k + 15.99f) / 256.0f;
        double d5 = (double) f + 0.02734375;
        double d6 = (double) f2 + 0.0234375;
        double d7 = (double) f + 0.03515625;
        double d8 = (double) f2 + 0.03125;
        double d9 = (d += 0.5) - 0.5;
        double d10 = d + 0.5;
        double d11 = (d2 += 0.5) - 0.5;
        double d12 = d2 + 0.5;
        double d13 = 0.0625;
        double d14 = 0.625;
        double d15 = d + d3 * (1.0 - d14) - d13;
        double d21 = d2 + d4 * (1.0 - d14) - d13;
        tessellator.vertex(d15, d1 + d14, d21, d5, d6);
        double d22 = d2 + d4 * (1.0 - d14) + d13;
        tessellator.vertex(d15, d1 + d14, d22, d5, d8);
        double d16 = d + d3 * (1.0 - d14) + d13;
        tessellator.vertex(d16, d1 + d14, d22, d7, d8);
        tessellator.vertex(d16, d1 + d14, d21, d7, d6);
        tessellator.vertex(d - d13, d1 + 1.0, d11, f, f2);
        tessellator.vertex(d - d13 + d3, d1 + 0.0, d11 + d4, f, f3);
        tessellator.vertex(d - d13 + d3, d1 + 0.0, d12 + d4, f1, f3);
        tessellator.vertex(d - d13, d1 + 1.0, d12, f1, f2);
        tessellator.vertex(d + d13, d1 + 1.0, d12, f, f2);
        tessellator.vertex(d + d3 + d13, d1 + 0.0, d12 + d4, f, f3);
        tessellator.vertex(d + d3 + d13, d1 + 0.0, d11 + d4, f1, f3);
        tessellator.vertex(d + d13, d1 + 1.0, d11, f1, f2);
        tessellator.vertex(d9, d1 + 1.0, d2 + d13, f, f2);
        tessellator.vertex(d9 + d3, d1 + 0.0, d2 + d13 + d4, f, f3);
        tessellator.vertex(d10 + d3, d1 + 0.0, d2 + d13 + d4, f1, f3);
        tessellator.vertex(d10, d1 + 1.0, d2 + d13, f1, f2);
        tessellator.vertex(d10, d1 + 1.0, d2 - d13, f, f2);
        tessellator.vertex(d10 + d3, d1 + 0.0, d2 - d13 + d4, f, f3);
        tessellator.vertex(d9 + d3, d1 + 0.0, d2 - d13 + d4, f1, f3);
        tessellator.vertex(d9, d1 + 1.0, d2 - d13, f1, f2);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void method_47(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int j = block.getTextureForSide(0, i);
        if (this.field_83 >= 0) {
            j = this.field_83;
        }
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = k / 256.0f;
        double d4 = (k + 15.99f) / 256.0f;
        double d5 = l / 256.0f;
        double d6 = (l + 15.99f) / 256.0f;
        double d7 = d + 0.5 - 0.45f;
        double d8 = d + 0.5 + 0.45f;
        double d9 = d2 + 0.5 - 0.45f;
        double d10 = d2 + 0.5 + 0.45f;
        tessellator.vertex(d7, d1 + 1.0, d9, d3, d5);
        tessellator.vertex(d7, d1 + 0.0, d9, d3, d6);
        tessellator.vertex(d8, d1 + 0.0, d10, d4, d6);
        tessellator.vertex(d8, d1 + 1.0, d10, d4, d5);
        tessellator.vertex(d8, d1 + 1.0, d10, d3, d5);
        tessellator.vertex(d8, d1 + 0.0, d10, d3, d6);
        tessellator.vertex(d7, d1 + 0.0, d9, d4, d6);
        tessellator.vertex(d7, d1 + 1.0, d9, d4, d5);
        if (this.field_83 < 0) {
            j = block.getTextureForSide(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = k / 256.0f;
            d4 = (k + 15.99f) / 256.0f;
            d5 = l / 256.0f;
            d6 = (l + 15.99f) / 256.0f;
        }
        tessellator.vertex(d7, d1 + 1.0, d10, d3, d5);
        tessellator.vertex(d7, d1 + 0.0, d10, d3, d6);
        tessellator.vertex(d8, d1 + 0.0, d9, d4, d6);
        tessellator.vertex(d8, d1 + 1.0, d9, d4, d5);
        tessellator.vertex(d8, d1 + 1.0, d9, d3, d5);
        tessellator.vertex(d8, d1 + 0.0, d9, d3, d6);
        tessellator.vertex(d7, d1 + 0.0, d10, d4, d6);
        tessellator.vertex(d7, d1 + 1.0, d10, d4, d5);
    }

    public void renderCrossedSquaresUpsideDown(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int j = block.getTextureForSide(0, i);
        if (this.field_83 >= 0) {
            j = this.field_83;
        }
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = k / 256.0f;
        double d4 = (k + 15.99f) / 256.0f;
        double d5 = l / 256.0f;
        double d6 = (l + 15.99f) / 256.0f;
        double d7 = d + 0.5 - 0.45f;
        double d8 = d + 0.5 + 0.45f;
        double d9 = d2 + 0.5 - 0.45f;
        double d10 = d2 + 0.5 + 0.45f;
        tessellator.vertex(d7, d1 + 0.0, d9, d3, d5);
        tessellator.vertex(d7, d1 + 1.0, d9, d3, d6);
        tessellator.vertex(d8, d1 + 1.0, d10, d4, d6);
        tessellator.vertex(d8, d1 + 0.0, d10, d4, d5);
        tessellator.vertex(d8, d1 + 0.0, d10, d3, d5);
        tessellator.vertex(d8, d1 + 1.0, d10, d3, d6);
        tessellator.vertex(d7, d1 + 1.0, d9, d4, d6);
        tessellator.vertex(d7, d1 + 0.0, d9, d4, d5);
        if (this.field_83 < 0) {
            j = block.getTextureForSide(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = k / 256.0f;
            d4 = (k + 15.99f) / 256.0f;
            d5 = l / 256.0f;
            d6 = (l + 15.99f) / 256.0f;
        }
        tessellator.vertex(d7, d1 + 0.0, d10, d3, d5);
        tessellator.vertex(d7, d1 + 1.0, d10, d3, d6);
        tessellator.vertex(d8, d1 + 1.0, d9, d4, d6);
        tessellator.vertex(d8, d1 + 0.0, d9, d4, d5);
        tessellator.vertex(d8, d1 + 0.0, d9, d3, d5);
        tessellator.vertex(d8, d1 + 1.0, d9, d3, d6);
        tessellator.vertex(d7, d1 + 1.0, d10, d4, d6);
        tessellator.vertex(d7, d1 + 0.0, d10, d4, d5);
    }

    public void renderCrossedSquaresWest(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int j = block.getTextureForSide(0, i);
        if (this.field_83 >= 0) {
            j = this.field_83;
        }
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = k / 256.0f;
        double d4 = (k + 15.99f) / 256.0f;
        double d5 = l / 256.0f;
        double d6 = (l + 15.99f) / 256.0f;
        double d7 = d1 + 0.5 - 0.45f;
        double d8 = d1 + 0.5 + 0.45f;
        double d9 = d2 + 0.5 - 0.45f;
        double d10 = d2 + 0.5 + 0.45f;
        tessellator.vertex(d + 0.0, d7, d9, d3, d5);
        tessellator.vertex(d + 1.0, d7, d9, d3, d6);
        tessellator.vertex(d + 1.0, d8, d10, d4, d6);
        tessellator.vertex(d + 0.0, d8, d10, d4, d5);
        tessellator.vertex(d + 0.0, d8, d10, d3, d5);
        tessellator.vertex(d + 1.0, d8, d10, d3, d6);
        tessellator.vertex(d + 1.0, d7, d9, d4, d6);
        tessellator.vertex(d + 0.0, d7, d9, d4, d5);
        if (this.field_83 < 0) {
            j = block.getTextureForSide(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = k / 256.0f;
            d4 = (k + 15.99f) / 256.0f;
            d5 = l / 256.0f;
            d6 = (l + 15.99f) / 256.0f;
        }
        tessellator.vertex(d + 0.0, d7, d10, d3, d5);
        tessellator.vertex(d + 1.0, d7, d10, d3, d6);
        tessellator.vertex(d + 1.0, d8, d9, d4, d6);
        tessellator.vertex(d + 0.0, d8, d9, d4, d5);
        tessellator.vertex(d + 0.0, d8, d9, d3, d5);
        tessellator.vertex(d + 1.0, d8, d9, d3, d6);
        tessellator.vertex(d + 1.0, d7, d10, d4, d6);
        tessellator.vertex(d + 0.0, d7, d10, d4, d5);
    }

    public void renderCrossedSquaresEast(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int j = block.getTextureForSide(0, i);
        if (this.field_83 >= 0) {
            j = this.field_83;
        }
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = k / 256.0f;
        double d4 = (k + 15.99f) / 256.0f;
        double d5 = l / 256.0f;
        double d6 = (l + 15.99f) / 256.0f;
        double d7 = d1 + 0.5 - 0.45f;
        double d8 = d1 + 0.5 + 0.45f;
        double d9 = d2 + 0.5 - 0.45f;
        double d10 = d2 + 0.5 + 0.45f;
        tessellator.vertex(d + 1.0, d7, d9, d3, d5);
        tessellator.vertex(d + 0.0, d7, d9, d3, d6);
        tessellator.vertex(d + 0.0, d8, d10, d4, d6);
        tessellator.vertex(d + 1.0, d8, d10, d4, d5);
        tessellator.vertex(d + 1.0, d8, d10, d3, d5);
        tessellator.vertex(d + 0.0, d8, d10, d3, d6);
        tessellator.vertex(d + 0.0, d7, d9, d4, d6);
        if (this.field_83 < 0) {
            j = block.getTextureForSide(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = k / 256.0f;
            d4 = (k + 15.99f) / 256.0f;
            d5 = l / 256.0f;
            d6 = (l + 15.99f) / 256.0f;
        }
        tessellator.vertex(d + 1.0, d7, d9, d4, d5);
        tessellator.vertex(d + 1.0, d7, d10, d3, d5);
        tessellator.vertex(d + 0.0, d7, d10, d3, d6);
        tessellator.vertex(d + 0.0, d8, d9, d4, d6);
        tessellator.vertex(d + 1.0, d8, d9, d4, d5);
        tessellator.vertex(d + 1.0, d8, d9, d3, d5);
        tessellator.vertex(d + 0.0, d8, d9, d3, d6);
        tessellator.vertex(d + 0.0, d7, d10, d4, d6);
        tessellator.vertex(d + 1.0, d7, d10, d4, d5);
    }

    public void renderCrossedSquaresNorth(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int j = block.getTextureForSide(0, i);
        if (this.field_83 >= 0) {
            j = this.field_83;
        }
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = k / 256.0f;
        double d4 = (k + 15.99f) / 256.0f;
        double d5 = l / 256.0f;
        double d6 = (l + 15.99f) / 256.0f;
        double d7 = d1 + 0.5 - 0.45f;
        double d8 = d1 + 0.5 + 0.45f;
        double d9 = d + 0.5 - 0.45f;
        double d10 = d + 0.5 + 0.45f;
        tessellator.vertex(d9, d7, d2 + 1.0, d3, d5);
        tessellator.vertex(d9, d7, d2 + 0.0, d3, d6);
        tessellator.vertex(d10, d8, d2 + 0.0, d4, d6);
        tessellator.vertex(d10, d8, d2 + 1.0, d4, d5);
        tessellator.vertex(d10, d8, d2 + 1.0, d3, d5);
        tessellator.vertex(d10, d8, d2 + 0.0, d3, d6);
        tessellator.vertex(d9, d7, d2 + 0.0, d4, d6);
        tessellator.vertex(d9, d7, d2 + 1.0, d4, d5);
        if (this.field_83 < 0) {
            j = block.getTextureForSide(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = k / 256.0f;
            d4 = (k + 15.99f) / 256.0f;
            d5 = l / 256.0f;
            d6 = (l + 15.99f) / 256.0f;
        }
        tessellator.vertex(d10, d7, d2 + 1.0, d3, d5);
        tessellator.vertex(d10, d7, d2 + 0.0, d3, d6);
        tessellator.vertex(d9, d8, d2 + 0.0, d4, d6);
        tessellator.vertex(d9, d8, d2 + 1.0, d4, d5);
        tessellator.vertex(d9, d8, d2 + 1.0, d3, d5);
        tessellator.vertex(d9, d8, d2 + 0.0, d3, d6);
        tessellator.vertex(d10, d7, d2 + 0.0, d4, d6);
        tessellator.vertex(d10, d7, d2 + 1.0, d4, d5);
    }

    public void renderCrossedSquaresSouth(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int j = block.getTextureForSide(0, i);
        if (this.field_83 >= 0) {
            j = this.field_83;
        }
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = k / 256.0f;
        double d4 = (k + 15.99f) / 256.0f;
        double d5 = l / 256.0f;
        double d6 = (l + 15.99f) / 256.0f;
        double d7 = d1 + 0.5 - 0.45f;
        double d8 = d1 + 0.5 + 0.45f;
        double d9 = d + 0.5 - 0.45f;
        double d10 = d + 0.5 + 0.45f;
        tessellator.vertex(d9, d7, d2 + 0.0, d3, d5);
        tessellator.vertex(d9, d7, d2 + 1.0, d3, d6);
        tessellator.vertex(d10, d8, d2 + 1.0, d4, d6);
        tessellator.vertex(d10, d8, d2 + 0.0, d4, d5);
        tessellator.vertex(d10, d8, d2 + 0.0, d3, d5);
        tessellator.vertex(d10, d8, d2 + 1.0, d3, d6);
        tessellator.vertex(d9, d7, d2 + 1.0, d4, d6);
        tessellator.vertex(d9, d7, d2 + 0.0, d4, d5);
        if (this.field_83 < 0) {
            j = block.getTextureForSide(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = k / 256.0f;
            d4 = (k + 15.99f) / 256.0f;
            d5 = l / 256.0f;
            d6 = (l + 15.99f) / 256.0f;
        }
        tessellator.vertex(d10, d7, d2 + 0.0, d3, d5);
        tessellator.vertex(d10, d7, d2 + 1.0, d3, d6);
        tessellator.vertex(d9, d8, d2 + 1.0, d4, d6);
        tessellator.vertex(d9, d8, d2 + 0.0, d4, d5);
        tessellator.vertex(d9, d8, d2 + 0.0, d3, d5);
        tessellator.vertex(d9, d8, d2 + 1.0, d3, d6);
        tessellator.vertex(d10, d7, d2 + 1.0, d4, d6);
        tessellator.vertex(d10, d7, d2 + 0.0, d4, d5);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void method_53(Tile block, Level world, int i, int j, int k) {
        GL11.glTranslatef(-i, -j, -k);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        this.startRenderingBlocks(world);
        this.method_57(block, i, j, k);
        this.stopRenderingBlocks();
    }

    @Override
    public void startRenderingBlocks(Level world) {
        this.field_82 = world;
        if (Minecraft.isSmoothLightingEnabled()) {
            GL11.glShadeModel(7425);
        }
        Tessellator.INSTANCE.start();
        this.field_85 = true;
    }

    @Override
    public void stopRenderingBlocks() {
        this.field_85 = false;
        Tessellator.INSTANCE.draw();
        if (Minecraft.isSmoothLightingEnabled()) {
            GL11.glShadeModel(7424);
        }
        this.field_82 = null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public boolean method_50(Tile block, int i, int j, int k, float f, float f1, float f2) {
        float bottomRight;
        float topRight;
        float bottomLeft;
        float topLeft;
        float lerpBottom;
        float lerpTop;
        float lerpRight;
        float lerpLeft;
        this.field_92 = true;
        boolean flag = false;
        boolean flag1 = true;
        boolean flag3 = true;
        boolean flag4 = true;
        boolean flag5 = true;
        boolean flag6 = true;
        this.field_93 = block.method_1604(this.field_82, i, j, k);
        this.field_94 = block.method_1604(this.field_82, i - 1, j, k);
        this.field_95 = block.method_1604(this.field_82, i, j - 1, k);
        this.field_96 = block.method_1604(this.field_82, i, j, k - 1);
        this.field_97 = block.method_1604(this.field_82, i + 1, j, k);
        this.field_98 = block.method_1604(this.field_82, i, j + 1, k);
        this.field_99 = block.method_1604(this.field_82, i, j, k + 1);
        this.field_70 = Tile.IS_AIR[this.field_82.getTileId(i + 1, j + 1, k)];
        this.field_78 = Tile.IS_AIR[this.field_82.getTileId(i + 1, j - 1, k)];
        this.field_74 = Tile.IS_AIR[this.field_82.getTileId(i + 1, j, k + 1)];
        this.field_76 = Tile.IS_AIR[this.field_82.getTileId(i + 1, j, k - 1)];
        this.field_71 = Tile.IS_AIR[this.field_82.getTileId(i - 1, j + 1, k)];
        this.field_79 = Tile.IS_AIR[this.field_82.getTileId(i - 1, j - 1, k)];
        this.field_73 = Tile.IS_AIR[this.field_82.getTileId(i - 1, j, k - 1)];
        this.field_75 = Tile.IS_AIR[this.field_82.getTileId(i - 1, j, k + 1)];
        this.field_72 = Tile.IS_AIR[this.field_82.getTileId(i, j + 1, k + 1)];
        this.field_69 = Tile.IS_AIR[this.field_82.getTileId(i, j + 1, k - 1)];
        this.field_80 = Tile.IS_AIR[this.field_82.getTileId(i, j - 1, k + 1)];
        this.field_77 = Tile.IS_AIR[this.field_82.getTileId(i, j - 1, k - 1)];
        boolean isGrass = block.id == Tile.GRASS.id;
        if (isGrass || this.field_83 >= 0) {
            flag6 = false;
            flag5 = false;
            flag4 = false;
            flag3 = false;
            flag1 = false;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j - 1, k, 0)) {
            float f11;
            float f18;
            float f25;
            float f4;
            if (this.field_55 > 0) {
                this.field_101 = block.method_1604(this.field_82, i - 1, --j, k);
                this.field_103 = block.method_1604(this.field_82, i, j, k - 1);
                this.field_104 = block.method_1604(this.field_82, i, j, k + 1);
                this.field_41 = block.method_1604(this.field_82, i + 1, j, k);
                this.field_100 = this.field_77 || this.field_79 ? block.method_1604(this.field_82, i - 1, j, k - 1) : this.field_101;
                this.field_102 = this.field_80 || this.field_79 ? block.method_1604(this.field_82, i - 1, j, k + 1) : this.field_101;
                this.field_105 = this.field_77 || this.field_78 ? block.method_1604(this.field_82, i + 1, j, k - 1) : this.field_41;
                this.field_42 = this.field_80 || this.field_78 ? block.method_1604(this.field_82, i + 1, j, k + 1) : this.field_41;
                ++j;
                f4 = (this.field_102 + this.field_101 + this.field_104 + this.field_95) / 4.0f;
                f25 = (this.field_104 + this.field_95 + this.field_42 + this.field_41) / 4.0f;
                f18 = (this.field_95 + this.field_103 + this.field_41 + this.field_105) / 4.0f;
                f11 = (this.field_101 + this.field_100 + this.field_95 + this.field_103) / 4.0f;
            } else {
                f18 = f25 = this.field_95;
                f11 = f25;
                f4 = f25;
            }
            this.field_58 = this.field_59 = (flag1 ? f : 1.0f) * 0.5f;
            this.field_57 = this.field_59;
            this.field_56 = this.field_59;
            this.field_62 = this.field_63 = (flag1 ? f1 : 1.0f) * 0.5f;
            this.field_61 = this.field_63;
            this.field_60 = this.field_63;
            this.field_66 = this.field_68 = (flag1 ? f2 : 1.0f) * 0.5f;
            this.field_65 = this.field_68;
            this.field_64 = this.field_68;
            lerpLeft = 1.0f - (float) Math.max(block.minX, 0.0);
            lerpRight = 1.0f - (float) Math.min(block.maxX, 1.0);
            lerpTop = (float) Math.min(block.maxZ, 1.0);
            lerpBottom = (float) Math.max(block.minZ, 0.0);
            float v = lerpLeft * f4 + (1.0f - lerpLeft) * f25;
            float v1 = lerpLeft * f11 + (1.0f - lerpLeft) * f18;
            topLeft = lerpTop * v + (1.0f - lerpTop) * v1;
            bottomLeft = lerpBottom * v + (1.0f - lerpBottom) * v1;
            float v2 = lerpRight * f4 + (1.0f - lerpRight) * f25;
            float v3 = lerpRight * f11 + (1.0f - lerpRight) * f18;
            topRight = lerpTop * v2 + (1.0f - lerpTop) * v3;
            bottomRight = lerpBottom * v2 + (1.0f - lerpBottom) * v3;
            f4 = topLeft;
            f25 = topRight;
            f11 = bottomLeft;
            f18 = bottomRight;
            this.field_56 *= f4;
            this.field_60 *= f4;
            this.field_64 *= f4;
            this.field_57 *= f11;
            this.field_61 *= f11;
            this.field_65 *= f11;
            this.field_58 *= f18;
            this.field_62 *= f18;
            this.field_66 *= f18;
            this.field_59 *= f25;
            this.field_63 *= f25;
            this.field_68 *= f25;
            this.method_46(block, i, j, k, block.method_1626(this.field_82, i, j, k, 0));
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j + 1, k, 1)) {
            float f19;
            float f12;
            float f5;
            float f26;
            if (this.field_55 > 0) {
                this.field_44 = block.method_1604(this.field_82, i - 1, ++j, k);
                this.field_48 = block.method_1604(this.field_82, i + 1, j, k);
                this.field_46 = block.method_1604(this.field_82, i, j, k - 1);
                this.field_49 = block.method_1604(this.field_82, i, j, k + 1);
                this.field_43 = this.field_69 || this.field_71 ? block.method_1604(this.field_82, i - 1, j, k - 1) : this.field_44;
                this.field_47 = this.field_69 || this.field_70 ? block.method_1604(this.field_82, i + 1, j, k - 1) : this.field_48;
                this.field_45 = this.field_72 || this.field_71 ? block.method_1604(this.field_82, i - 1, j, k + 1) : this.field_44;
                this.field_50 = this.field_72 || this.field_70 ? block.method_1604(this.field_82, i + 1, j, k + 1) : this.field_48;
                --j;
                f26 = (this.field_45 + this.field_44 + this.field_49 + this.field_98) / 4.0f;
                f5 = (this.field_49 + this.field_98 + this.field_50 + this.field_48) / 4.0f;
                f12 = (this.field_98 + this.field_46 + this.field_48 + this.field_47) / 4.0f;
                f19 = (this.field_44 + this.field_43 + this.field_98 + this.field_46) / 4.0f;
            } else {
                f19 = f26 = this.field_98;
                f12 = f26;
                f5 = f26;
            }
            this.field_59 = f;
            this.field_58 = this.field_59;
            this.field_57 = this.field_59;
            this.field_56 = this.field_59;
            this.field_63 = f1;
            this.field_62 = this.field_63;
            this.field_61 = this.field_63;
            this.field_60 = this.field_63;
            this.field_68 = f2;
            this.field_66 = this.field_68;
            this.field_65 = this.field_68;
            this.field_64 = this.field_68;
            lerpLeft = 1.0f - (float) Math.max(block.minX, 0.0);
            lerpRight = 1.0f - (float) Math.min(block.maxX, 1.0);
            lerpTop = (float) Math.max(block.minZ, 0.0);
            lerpBottom = (float) Math.min(block.maxZ, 1.0);
            float v = lerpLeft * f26 + (1.0f - lerpLeft) * f5;
            float v1 = lerpLeft * f19 + (1.0f - lerpLeft) * f12;
            topLeft = lerpTop * v + (1.0f - lerpTop) * v1;
            float v2 = lerpRight * f26 + (1.0f - lerpRight) * f5;
            float v3 = lerpRight * f19 + (1.0f - lerpRight) * f12;
            float topRight2 = lerpTop * v2 + (1.0f - lerpTop) * v3;
            float bottomLeft2 = lerpBottom * v + (1.0f - lerpBottom) * v1;
            bottomRight = lerpBottom * v2 + (1.0f - lerpBottom) * v3;
            f19 = topLeft;
            f26 = bottomLeft2;
            f5 = bottomRight;
            f12 = topRight2;
            this.field_56 *= f5;
            this.field_60 *= f5;
            this.field_64 *= f5;
            this.field_57 *= f12;
            this.field_61 *= f12;
            this.field_65 *= f12;
            this.field_58 *= f19;
            this.field_62 *= f19;
            this.field_66 *= f19;
            this.field_59 *= f26;
            this.field_63 *= f26;
            this.field_68 *= f26;
            this.method_55(block, i, j, k, block.method_1626(this.field_82, i, j, k, 1));
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j, k - 1, 2)) {
            float f27;
            float f20;
            float f13;
            float f6;
            if (this.field_55 > 0) {
                this.field_51 = block.method_1604(this.field_82, i - 1, j, --k);
                this.field_103 = block.method_1604(this.field_82, i, j - 1, k);
                this.field_46 = block.method_1604(this.field_82, i, j + 1, k);
                this.field_52 = block.method_1604(this.field_82, i + 1, j, k);
                this.field_100 = this.field_73 || this.field_77 ? block.method_1604(this.field_82, i - 1, j - 1, k) : this.field_51;
                this.field_43 = this.field_73 || this.field_69 ? block.method_1604(this.field_82, i - 1, j + 1, k) : this.field_51;
                this.field_105 = this.field_76 || this.field_77 ? block.method_1604(this.field_82, i + 1, j - 1, k) : this.field_52;
                this.field_47 = this.field_76 || this.field_69 ? block.method_1604(this.field_82, i + 1, j + 1, k) : this.field_52;
                ++k;
                f6 = (this.field_51 + this.field_43 + this.field_96 + this.field_46) / 4.0f;
                f13 = (this.field_96 + this.field_46 + this.field_52 + this.field_47) / 4.0f;
                f20 = (this.field_103 + this.field_96 + this.field_105 + this.field_52) / 4.0f;
                f27 = (this.field_100 + this.field_51 + this.field_103 + this.field_96) / 4.0f;
            } else {
                f20 = f27 = this.field_96;
                f13 = f27;
                f6 = f27;
            }
            this.field_58 = this.field_59 = (flag3 ? f : 1.0f) * 0.8f;
            this.field_57 = this.field_59;
            this.field_56 = this.field_59;
            this.field_62 = this.field_63 = (flag3 ? f1 : 1.0f) * 0.8f;
            this.field_61 = this.field_63;
            this.field_60 = this.field_63;
            this.field_66 = this.field_68 = (flag3 ? f2 : 1.0f) * 0.8f;
            this.field_65 = this.field_68;
            this.field_64 = this.field_68;
            lerpLeft = (float) Math.min(block.maxX, 1.0);
            lerpRight = (float) Math.max(block.minX, 0.0);
            lerpTop = (float) Math.min(block.maxY, 1.0);
            lerpBottom = (float) Math.max(block.minY, 0.0);
            float v = lerpLeft * f13 + (1.0f - lerpLeft) * f6;
            float v1 = lerpLeft * f20 + (1.0f - lerpLeft) * f27;
            topLeft = lerpTop * v + (1.0f - lerpTop) * v1;
            bottomLeft = lerpBottom * v + (1.0f - lerpBottom) * v1;
            float v2 = lerpRight * f13 + (1.0f - lerpRight) * f6;
            float v3 = lerpRight * f20 + (1.0f - lerpRight) * f27;
            topRight = lerpTop * v2 + (1.0f - lerpTop) * v3;
            bottomRight = lerpBottom * v2 + (1.0f - lerpBottom) * v3;
            f13 = topLeft;
            f20 = bottomLeft;
            f27 = bottomRight;
            f6 = topRight;
            this.field_56 *= f6;
            this.field_60 *= f6;
            this.field_64 *= f6;
            this.field_57 *= f13;
            this.field_61 *= f13;
            this.field_65 *= f13;
            this.field_58 *= f20;
            this.field_62 *= f20;
            this.field_66 *= f20;
            this.field_59 *= f27;
            this.field_63 *= f27;
            this.field_68 *= f27;
            int l = block.method_1626(this.field_82, i, j, k, 2);
            this.method_61(block, i, j, k, l);
            if (field_67 && isGrass && this.field_83 < 0) {
                this.field_56 *= f;
                this.field_57 *= f;
                this.field_58 *= f;
                this.field_59 *= f;
                this.field_60 *= f1;
                this.field_61 *= f1;
                this.field_62 *= f1;
                this.field_63 *= f1;
                this.field_64 *= f2;
                this.field_65 *= f2;
                this.field_66 *= f2;
                this.field_68 *= f2;
                this.method_61(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j, k + 1, 3)) {
            float f14;
            float f21;
            float f28;
            float f7;
            if (this.field_55 > 0) {
                this.field_53 = block.method_1604(this.field_82, i - 1, j, ++k);
                this.field_54 = block.method_1604(this.field_82, i + 1, j, k);
                this.field_104 = block.method_1604(this.field_82, i, j - 1, k);
                this.field_49 = block.method_1604(this.field_82, i, j + 1, k);
                this.field_102 = this.field_75 || this.field_80 ? block.method_1604(this.field_82, i - 1, j - 1, k) : this.field_53;
                this.field_45 = this.field_75 || this.field_72 ? block.method_1604(this.field_82, i - 1, j + 1, k) : this.field_53;
                this.field_42 = this.field_74 || this.field_80 ? block.method_1604(this.field_82, i + 1, j - 1, k) : this.field_54;
                this.field_50 = this.field_74 || this.field_72 ? block.method_1604(this.field_82, i + 1, j + 1, k) : this.field_54;
                --k;
                f7 = (this.field_53 + this.field_45 + this.field_99 + this.field_49) / 4.0f;
                f28 = (this.field_99 + this.field_49 + this.field_54 + this.field_50) / 4.0f;
                f21 = (this.field_104 + this.field_99 + this.field_42 + this.field_54) / 4.0f;
                f14 = (this.field_102 + this.field_53 + this.field_104 + this.field_99) / 4.0f;
            } else {
                f21 = f28 = this.field_99;
                f14 = f28;
                f7 = f28;
            }
            this.field_58 = this.field_59 = (flag4 ? f : 1.0f) * 0.8f;
            this.field_57 = this.field_59;
            this.field_56 = this.field_59;
            this.field_62 = this.field_63 = (flag4 ? f1 : 1.0f) * 0.8f;
            this.field_61 = this.field_63;
            this.field_60 = this.field_63;
            this.field_66 = this.field_68 = (flag4 ? f2 : 1.0f) * 0.8f;
            this.field_65 = this.field_68;
            this.field_64 = this.field_68;
            lerpLeft = (float) Math.min(1.0 - block.minX, 1.0);
            lerpRight = (float) Math.max(1.0 - block.maxX, 0.0);
            lerpTop = (float) Math.min(block.maxY, 1.0);
            lerpBottom = (float) Math.max(block.minY, 0.0);
            float v = lerpLeft * f7 + (1.0f - lerpLeft) * f28;
            float v1 = lerpLeft * f14 + (1.0f - lerpLeft) * f21;
            topLeft = lerpTop * v + (1.0f - lerpTop) * v1;
            bottomLeft = lerpBottom * v + (1.0f - lerpBottom) * v1;
            float v2 = lerpRight * f7 + (1.0f - lerpRight) * f28;
            float v3 = lerpRight * f14 + (1.0f - lerpRight) * f21;
            topRight = lerpTop * v2 + (1.0f - lerpTop) * v3;
            bottomRight = lerpBottom * v2 + (1.0f - lerpBottom) * v3;
            f7 = topLeft;
            f14 = bottomLeft;
            f21 = bottomRight;
            f28 = topRight;
            this.field_56 *= f7;
            this.field_60 *= f7;
            this.field_64 *= f7;
            this.field_57 *= f14;
            this.field_61 *= f14;
            this.field_65 *= f14;
            this.field_58 *= f21;
            this.field_62 *= f21;
            this.field_66 *= f21;
            this.field_59 *= f28;
            this.field_63 *= f28;
            this.field_68 *= f28;
            block.method_1626(this.field_82, i, j, k, 3);
            this.method_65(block, i, j, k, block.method_1626(this.field_82, i, j, k, 3));
            if (field_67 && isGrass && this.field_83 < 0) {
                this.field_56 *= f;
                this.field_57 *= f;
                this.field_58 *= f;
                this.field_59 *= f;
                this.field_60 *= f1;
                this.field_61 *= f1;
                this.field_62 *= f1;
                this.field_63 *= f1;
                this.field_64 *= f2;
                this.field_65 *= f2;
                this.field_66 *= f2;
                this.field_68 *= f2;
                this.method_65(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i - 1, j, k, 4)) {
            float f22;
            float f15;
            float f8;
            float f29;
            if (this.field_55 > 0) {
                this.field_101 = block.method_1604(this.field_82, --i, j - 1, k);
                this.field_51 = block.method_1604(this.field_82, i, j, k - 1);
                this.field_53 = block.method_1604(this.field_82, i, j, k + 1);
                this.field_44 = block.method_1604(this.field_82, i, j + 1, k);
                this.field_100 = this.field_73 || this.field_79 ? block.method_1604(this.field_82, i, j - 1, k - 1) : this.field_51;
                this.field_102 = this.field_75 || this.field_79 ? block.method_1604(this.field_82, i, j - 1, k + 1) : this.field_53;
                this.field_43 = this.field_73 || this.field_71 ? block.method_1604(this.field_82, i, j + 1, k - 1) : this.field_51;
                this.field_45 = this.field_75 || this.field_71 ? block.method_1604(this.field_82, i, j + 1, k + 1) : this.field_53;
                ++i;
                f29 = (this.field_101 + this.field_102 + this.field_94 + this.field_53) / 4.0f;
                f8 = (this.field_94 + this.field_53 + this.field_44 + this.field_45) / 4.0f;
                f15 = (this.field_51 + this.field_94 + this.field_43 + this.field_44) / 4.0f;
                f22 = (this.field_100 + this.field_101 + this.field_51 + this.field_94) / 4.0f;
            } else {
                f22 = f29 = this.field_94;
                f15 = f29;
                f8 = f29;
            }
            this.field_58 = this.field_59 = (flag5 ? f : 1.0f) * 0.6f;
            this.field_57 = this.field_59;
            this.field_56 = this.field_59;
            this.field_62 = this.field_63 = (flag5 ? f1 : 1.0f) * 0.6f;
            this.field_61 = this.field_63;
            this.field_60 = this.field_63;
            this.field_66 = this.field_68 = (flag5 ? f2 : 1.0f) * 0.6f;
            this.field_65 = this.field_68;
            this.field_64 = this.field_68;
            lerpLeft = (float) Math.min(1.0 - block.minZ, 1.0);
            lerpRight = (float) Math.max(1.0 - block.maxZ, 0.0);
            lerpTop = (float) Math.min(block.maxY, 1.0);
            lerpBottom = (float) Math.max(block.minY, 0.0);
            float v = lerpLeft * f15 + (1.0f - lerpLeft) * f8;
            float v1 = lerpLeft * f22 + (1.0f - lerpLeft) * f29;
            topLeft = lerpTop * v + (1.0f - lerpTop) * v1;
            bottomLeft = lerpBottom * v + (1.0f - lerpBottom) * v1;
            float v2 = lerpRight * f15 + (1.0f - lerpRight) * f8;
            float v3 = lerpRight * f22 + (1.0f - lerpRight) * f29;
            topRight = lerpTop * v2 + (1.0f - lerpTop) * v3;
            bottomRight = lerpBottom * v2 + (1.0f - lerpBottom) * v3;
            f15 = topLeft;
            f22 = bottomLeft;
            f29 = bottomRight;
            f8 = topRight;
            this.field_56 *= f8;
            this.field_60 *= f8;
            this.field_64 *= f8;
            this.field_57 *= f15;
            this.field_61 *= f15;
            this.field_65 *= f15;
            this.field_58 *= f22;
            this.field_62 *= f22;
            this.field_66 *= f22;
            this.field_59 *= f29;
            this.field_63 *= f29;
            this.field_68 *= f29;
            int j1 = block.method_1626(this.field_82, i, j, k, 4);
            this.method_67(block, i, j, k, j1);
            if (field_67 && isGrass && this.field_83 < 0) {
                this.field_56 *= f;
                this.field_57 *= f;
                this.field_58 *= f;
                this.field_59 *= f;
                this.field_60 *= f1;
                this.field_61 *= f1;
                this.field_62 *= f1;
                this.field_63 *= f1;
                this.field_64 *= f2;
                this.field_65 *= f2;
                this.field_66 *= f2;
                this.field_68 *= f2;
                this.method_67(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i + 1, j, k, 5)) {
            float f16;
            float f23;
            float f30;
            float f9;
            if (this.field_55 > 0) {
                this.field_41 = block.method_1604(this.field_82, ++i, j - 1, k);
                this.field_52 = block.method_1604(this.field_82, i, j, k - 1);
                this.field_54 = block.method_1604(this.field_82, i, j, k + 1);
                this.field_48 = block.method_1604(this.field_82, i, j + 1, k);
                this.field_105 = this.field_78 || this.field_76 ? block.method_1604(this.field_82, i, j - 1, k - 1) : this.field_52;
                this.field_42 = this.field_78 || this.field_74 ? block.method_1604(this.field_82, i, j - 1, k + 1) : this.field_54;
                this.field_47 = this.field_70 || this.field_76 ? block.method_1604(this.field_82, i, j + 1, k - 1) : this.field_52;
                this.field_50 = this.field_70 || this.field_74 ? block.method_1604(this.field_82, i, j + 1, k + 1) : this.field_54;
                --i;
                f9 = (this.field_41 + this.field_42 + this.field_97 + this.field_54) / 4.0f;
                f30 = (this.field_97 + this.field_54 + this.field_48 + this.field_50) / 4.0f;
                f23 = (this.field_52 + this.field_97 + this.field_47 + this.field_48) / 4.0f;
                f16 = (this.field_105 + this.field_41 + this.field_52 + this.field_97) / 4.0f;
            } else {
                f23 = f30 = this.field_97;
                f16 = f30;
                f9 = f30;
            }
            this.field_58 = this.field_59 = (flag6 ? f : 1.0f) * 0.6f;
            this.field_57 = this.field_59;
            this.field_56 = this.field_59;
            this.field_62 = this.field_63 = (flag6 ? f1 : 1.0f) * 0.6f;
            this.field_61 = this.field_63;
            this.field_60 = this.field_63;
            this.field_66 = this.field_68 = (flag6 ? f2 : 1.0f) * 0.6f;
            this.field_65 = this.field_68;
            this.field_64 = this.field_68;
            lerpLeft = (float) Math.min(1.0 - block.minZ, 1.0);
            lerpRight = (float) Math.max(1.0 - block.maxZ, 0.0);
            lerpTop = (float) Math.min(block.maxY, 1.0);
            lerpBottom = (float) Math.max(block.minY, 0.0);
            float v = lerpLeft * f23 + (1.0f - lerpLeft) * f30;
            float v1 = lerpLeft * f16 + (1.0f - lerpLeft) * f9;
            topLeft = lerpTop * v + (1.0f - lerpTop) * v1;
            bottomLeft = lerpBottom * v + (1.0f - lerpBottom) * v1;
            float v2 = lerpRight * f23 + (1.0f - lerpRight) * f30;
            float v3 = lerpRight * f16 + (1.0f - lerpRight) * f9;
            topRight = lerpTop * v2 + (1.0f - lerpTop) * v3;
            f9 = lerpBottom * v2 + (1.0f - lerpBottom) * v3;
            f16 = bottomLeft;
            f23 = topLeft;
            f30 = topRight;
            this.field_56 *= f9;
            this.field_60 *= f9;
            this.field_64 *= f9;
            this.field_57 *= f16;
            this.field_61 *= f16;
            this.field_65 *= f16;
            this.field_58 *= f23;
            this.field_62 *= f23;
            this.field_66 *= f23;
            this.field_59 *= f30;
            this.field_63 *= f30;
            this.field_68 *= f30;
            int k1 = block.method_1626(this.field_82, i, j, k, 5);
            this.method_69(block, i, j, k, k1);
            if (field_67 && isGrass && this.field_83 < 0) {
                this.field_56 *= f;
                this.field_57 *= f;
                this.field_58 *= f;
                this.field_59 *= f;
                this.field_60 *= f1;
                this.field_61 *= f1;
                this.field_62 *= f1;
                this.field_63 *= f1;
                this.field_64 *= f2;
                this.field_65 *= f2;
                this.field_66 *= f2;
                this.field_68 *= f2;
                this.method_69(block, i, j, k, 38);
            }
            flag = true;
        }
        this.field_92 = false;
        return flag;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public boolean method_58(Tile block, int i, int j, int k, float f, float f1, float f2) {
        this.field_92 = false;
        Tessellator tessellator = Tessellator.INSTANCE;
        boolean flag = false;
        float f3 = 0.5f;
        float f4 = 1.0f;
        float f5 = 0.8f;
        float f6 = 0.6f;
        float f7 = f4 * f;
        float f8 = f4 * f1;
        float f9 = f4 * f2;
        float f10 = f3;
        float f11 = f5;
        float f12 = f6;
        float f13 = f3;
        float f14 = f5;
        float f15 = f6;
        float f16 = f3;
        float f17 = f5;
        float f18 = f6;
        boolean isGrass = block == Tile.GRASS;
        if (!isGrass) {
            f10 *= f;
            f11 *= f;
            f12 *= f;
            f13 *= f1;
            f14 *= f1;
            f15 *= f1;
            f16 *= f2;
            f17 *= f2;
            f18 *= f2;
        }
        float f19 = block.method_1604(this.field_82, i, j, k);
        if (this.field_85 || block.method_1618(this.field_82, i, j - 1, k, 0)) {
            float f20 = block.method_1604(this.field_82, i, j - 1, k);
            tessellator.colour(f10 * f20, f13 * f20, f16 * f20);
            this.method_46(block, i, j, k, block.method_1626(this.field_82, i, j, k, 0));
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j + 1, k, 1)) {
            float f21 = block.method_1604(this.field_82, i, j + 1, k);
            if (block.maxY != 1.0 && !block.material.isLiquid()) {
                f21 = f19;
            }
            tessellator.colour(f7 * f21, f8 * f21, f9 * f21);
            this.method_55(block, i, j, k, block.method_1626(this.field_82, i, j, k, 1));
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j, k - 1, 2)) {
            float f22 = block.method_1604(this.field_82, i, j, k - 1);
            if (block.minZ > 0.0) {
                f22 = f19;
            }
            tessellator.colour(f11 * f22, f14 * f22, f17 * f22);
            int l = block.method_1626(this.field_82, i, j, k, 2);
            this.method_61(block, i, j, k, l);
            if (field_67 && isGrass && this.field_83 < 0) {
                tessellator.colour(f11 * f22 * f, f14 * f22 * f1, f17 * f22 * f2);
                this.method_61(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j, k + 1, 3)) {
            float f23 = block.method_1604(this.field_82, i, j, k + 1);
            if (block.maxZ < 1.0) {
                f23 = f19;
            }
            tessellator.colour(f11 * f23, f14 * f23, f17 * f23);
            int i1 = block.method_1626(this.field_82, i, j, k, 3);
            this.method_65(block, i, j, k, i1);
            if (field_67 && isGrass && this.field_83 < 0) {
                tessellator.colour(f11 * f23 * f, f14 * f23 * f1, f17 * f23 * f2);
                this.method_65(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i - 1, j, k, 4)) {
            float f24 = block.method_1604(this.field_82, i - 1, j, k);
            if (block.minX > 0.0) {
                f24 = f19;
            }
            tessellator.colour(f12 * f24, f15 * f24, f18 * f24);
            int j1 = block.method_1626(this.field_82, i, j, k, 4);
            this.method_67(block, i, j, k, j1);
            if (field_67 && isGrass && this.field_83 < 0) {
                tessellator.colour(f12 * f24 * f, f15 * f24 * f1, f18 * f24 * f2);
                this.method_67(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i + 1, j, k, 5)) {
            float f25 = block.method_1604(this.field_82, i + 1, j, k);
            if (block.maxX < 1.0) {
                f25 = f19;
            }
            tessellator.colour(f12 * f25, f15 * f25, f18 * f25);
            int k1 = block.method_1626(this.field_82, i, j, k, 5);
            this.method_69(block, i, j, k, k1);
            if (field_67 && isGrass && this.field_83 < 0) {
                tessellator.colour(f12 * f25 * f, f15 * f25 * f1, f18 * f25 * f2);
                this.method_69(block, i, j, k, 38);
            }
            flag = true;
        }
        return flag;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public boolean method_77(Tile block, int i, int j, int k) {
        int l = block.getTint(this.field_82, i, j, k);
        float f = (float) (l >> 16 & 0xFF) / 255.0f;
        float f1 = (float) (l >> 8 & 0xFF) / 255.0f;
        float f2 = (float) (l & 0xFF) / 255.0f;
        if (GameRenderer.field_2340) {
            float f3 = (f * 30.0f + f1 * 59.0f + f2 * 11.0f) / 100.0f;
            float f4 = (f * 30.0f + f1 * 70.0f) / 100.0f;
            float f5 = (f * 30.0f + f2 * 70.0f) / 100.0f;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        return this.method_63(block, i, j, k, f, f1, f2);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public boolean method_63(Tile block, int i, int j, int k, float f, float f1, float f2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        boolean flag = false;
        float f3 = 0.5f;
        float f4 = 1.0f;
        float f5 = 0.8f;
        float f6 = 0.6f;
        float f7 = f3 * f;
        float f8 = f4 * f;
        float f9 = f5 * f;
        float f10 = f6 * f;
        float f11 = f3 * f1;
        float f12 = f4 * f1;
        float f13 = f5 * f1;
        float f14 = f6 * f1;
        float f15 = f3 * f2;
        float f16 = f4 * f2;
        float f17 = f5 * f2;
        float f18 = f6 * f2;
        float f19 = 0.0625f;
        float f20 = block.method_1604(this.field_82, i, j, k);
        if (this.field_85 || block.method_1618(this.field_82, i, j - 1, k, 0)) {
            float f21 = block.method_1604(this.field_82, i, j - 1, k);
            tessellator.colour(f7 * f21, f11 * f21, f15 * f21);
            this.method_46(block, i, j, k, block.method_1626(this.field_82, i, j, k, 0));
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j + 1, k, 1)) {
            float f22 = block.method_1604(this.field_82, i, j + 1, k);
            if (block.maxY != 1.0 && !block.material.isLiquid()) {
                f22 = f20;
            }
            tessellator.colour(f8 * f22, f12 * f22, f16 * f22);
            this.method_55(block, i, j, k, block.method_1626(this.field_82, i, j, k, 1));
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j, k - 1, 2)) {
            float f23 = block.method_1604(this.field_82, i, j, k - 1);
            if (block.minZ > 0.0) {
                f23 = f20;
            }
            tessellator.colour(f9 * f23, f13 * f23, f17 * f23);
            tessellator.changePrevPos(0.0f, 0.0f, f19);
            this.method_61(block, i, j, k, block.method_1626(this.field_82, i, j, k, 2));
            tessellator.changePrevPos(0.0f, 0.0f, -f19);
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j, k + 1, 3)) {
            float f24 = block.method_1604(this.field_82, i, j, k + 1);
            if (block.maxZ < 1.0) {
                f24 = f20;
            }
            tessellator.colour(f9 * f24, f13 * f24, f17 * f24);
            tessellator.changePrevPos(0.0f, 0.0f, -f19);
            this.method_65(block, i, j, k, block.method_1626(this.field_82, i, j, k, 3));
            tessellator.changePrevPos(0.0f, 0.0f, f19);
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i - 1, j, k, 4)) {
            float f25 = block.method_1604(this.field_82, i - 1, j, k);
            if (block.minX > 0.0) {
                f25 = f20;
            }
            tessellator.colour(f10 * f25, f14 * f25, f18 * f25);
            tessellator.changePrevPos(f19, 0.0f, 0.0f);
            this.method_67(block, i, j, k, block.method_1626(this.field_82, i, j, k, 4));
            tessellator.changePrevPos(-f19, 0.0f, 0.0f);
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i + 1, j, k, 5)) {
            float f26 = block.method_1604(this.field_82, i + 1, j, k);
            if (block.maxX < 1.0) {
                f26 = f20;
            }
            tessellator.colour(f10 * f26, f14 * f26, f18 * f26);
            tessellator.changePrevPos(-f19, 0.0f, 0.0f);
            this.method_69(block, i, j, k, block.method_1626(this.field_82, i, j, k, 5));
            tessellator.changePrevPos(f19, 0.0f, 0.0f);
            flag = true;
        }
        return flag;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public boolean method_78(Tile block, int i, int j, int k) {
        float b2;
        float b1;
        double v2;
        double v1;
        double u2;
        double u1;
        int v;
        int u;
        int texture;
        Tessellator tessellator;
        float f = 0.375f;
        float f1 = 0.625f;
        block.setBoundingBox(f, 0.0f, f, f1, 1.0f, f1);
        this.method_76(block, i, j, k);
        boolean flag = true;
        boolean flag1 = false;
        boolean flag2 = false;
        if (this.field_82.getTileId(i - 1, j, k) == block.id || this.field_82.getTileId(i + 1, j, k) == block.id) {
            flag1 = true;
        }
        if (this.field_82.getTileId(i, j, k - 1) == block.id || this.field_82.getTileId(i, j, k + 1) == block.id) {
            flag2 = true;
        }
        boolean flag3 = this.field_82.getTileId(i - 1, j, k) == block.id;
        boolean flag4 = this.field_82.getTileId(i + 1, j, k) == block.id;
        boolean flag5 = this.field_82.getTileId(i, j, k - 1) == block.id;
        boolean flag6 = this.field_82.getTileId(i, j, k + 1) == block.id;
        if (!flag1 && !flag2) {
            flag1 = true;
        }
        f = 0.4375f;
        f1 = 0.5625f;
        float f2 = 0.75f;
        float f3 = 0.9375f;
        float f4 = flag3 ? 0.0f : f;
        float f5 = flag4 ? 1.0f : f1;
        float f6 = flag5 ? 0.0f : f;
        float f7 = flag6 ? 1.0f : f1;
        if (flag1) {
            block.setBoundingBox(f4, f2, f, f5, f3, f1);
            this.method_76(block, i, j, k);
            flag = true;
        }
        if (flag2) {
            block.setBoundingBox(f, f2, f6, f1, f3, f7);
            this.method_76(block, i, j, k);
            flag = true;
        }
        f2 = 0.375f;
        f3 = 0.5625f;
        if (flag1) {
            block.setBoundingBox(f4, f2, f, f5, f3, f1);
            this.method_76(block, i, j, k);
            flag = true;
        }
        if (flag2) {
            block.setBoundingBox(f, f2, f6, f1, f3, f7);
            this.method_76(block, i, j, k);
            flag = true;
        }
        f = (f - 0.5f) * 0.707f + 0.5f;
        f1 = (f1 - 0.5f) * 0.707f + 0.5f;
        if (this.field_82.getTileId(i - 1, j, k + 1) == block.id && !flag6 && !flag3) {
            tessellator = Tessellator.INSTANCE;
            texture = block.method_1626(this.field_82, i, j, k, 0);
            u = (texture & 0xF) << 4;
            v = texture & 0xF0;
            u1 = u / 256.0;
            u2 = (u + 16.0 - 0.01) / 256.0;
            v1 = (v + 16.0 * f3 - 1.0) / 256.0;
            v2 = (v + 16.0 * f2 - 1.0 - 0.01) / 256.0;
            b1 = this.field_82.getBrightness(i, j, k);
            b2 = this.field_82.getBrightness(i - 1, j, k + 1);
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f1 + i, f2 + j, f1 + k, u1, v2);
            tessellator.vertex(f1 + i, f3 + j, f1 + k, u1, v1);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f1 + i - 1.0f, f3 + j, f1 + k + 1.0f, u2, v1);
            tessellator.vertex(f1 + i - 1.0f, f2 + j, f1 + k + 1.0f, u2, v2);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f + i - 1.0f, f2 + j, f + k + 1.0f, u2, v2);
            tessellator.vertex(f + i - 1.0f, f3 + j, f + k + 1.0f, u2, v1);
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f + i, f3 + j, f + k, u1, v1);
            tessellator.vertex(f + i, f2 + j, f + k, u1, v2);
            v1 = ((double) v + 16.0 * (double) f3) / 256.0;
            v2 = ((double) v + 16.0 * (double) f3 + 2.0 - 0.01) / 256.0;
            tessellator.colour(b2 * 0.5f, b2 * 0.5f, b2 * 0.5f);
            tessellator.vertex(f1 + i - 1.0f, f2 + j, f1 + k + 1.0f, u2, v1);
            tessellator.vertex(f + i - 1.0f, f2 + j, f + k + 1.0f, u2, v2);
            tessellator.colour(b1 * 0.5f, b1 * 0.5f, b1 * 0.5f);
            tessellator.vertex(f + i, f2 + j, f + k, u1, v2);
            tessellator.vertex(f1 + i, f2 + j, f1 + k, u1, v1);
            tessellator.colour(b2, b2, b2);
            tessellator.vertex(f + i - 1.0f, f3 + j, f + k + 1.0f, u2, v1);
            tessellator.vertex(f1 + i - 1.0f, f3 + j, f1 + k + 1.0f, u2, v2);
            tessellator.colour(b1, b1, b1);
            tessellator.vertex(f1 + i, f3 + j, f1 + k, u1, v2);
            tessellator.vertex(f + i, f3 + j, f + k, u1, v1);
            f2 = 0.75f;
            f3 = 0.9375f;
            v1 = ((double) v + 16.0 * (double) f3 - 1.0) / 256.0;
            v2 = ((double) v + 16.0 * (double) f2 - 1.0 - 0.01) / 256.0;
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f1 + i, f2 + j, f1 + k, u1, v2);
            tessellator.vertex(f1 + i, f3 + j, f1 + k, u1, v1);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f1 + i - 1.0f, f3 + j, f1 + k + 1.0f, u2, v1);
            tessellator.vertex(f1 + i - 1.0f, f2 + j, f1 + k + 1.0f, u2, v2);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f + i - 1.0f, f2 + j, f + k + 1.0f, u2, v2);
            tessellator.vertex(f + i - 1.0f, f3 + j, f + k + 1.0f, u2, v1);
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f + i, f3 + j, f + k, u1, v1);
            tessellator.vertex(f + i, f2 + j, f + k, u1, v2);
            v1 = ((double) v + 16.0 * (double) f3) / 256.0;
            v2 = ((double) v + 16.0 * (double) f3 - 2.0 - 0.01) / 256.0;
            tessellator.colour(b2 * 0.5f, b2 * 0.5f, b2 * 0.5f);
            tessellator.vertex(f1 + i - 1.0f, f2 + j, f1 + k + 1.0f, u2, v1);
            tessellator.vertex(f + i - 1.0f, f2 + j, f + k + 1.0f, u2, v2);
            tessellator.colour(b1 * 0.5f, b1 * 0.5f, b1 * 0.5f);
            tessellator.vertex(f + i, f2 + j, f + k, u1, v2);
            tessellator.vertex(f1 + i, f2 + j, f1 + k, u1, v1);
            tessellator.colour(b2, b2, b2);
            tessellator.vertex(f + i - 1.0f, f3 + j, f + k + 1.0f, u2, v1);
            tessellator.vertex(f1 + i - 1.0f, f3 + j, f1 + k + 1.0f, u2, v2);
            tessellator.colour(b1, b1, b1);
            tessellator.vertex(f1 + i, f3 + j, f1 + k, u1, v2);
            tessellator.vertex(f + i, f3 + j, f + k, u1, v1);
        }
        if (this.field_82.getTileId(i + 1, j, k + 1) == block.id && !flag6 && !flag4) {
            f2 = 0.375f;
            f3 = 0.5625f;
            tessellator = Tessellator.INSTANCE;
            texture = block.method_1626(this.field_82, i, j, k, 0);
            u = (texture & 0xF) << 4;
            v = texture & 0xF0;
            u1 = (double) u / 256.0;
            u2 = ((double) u + 16.0 - 0.01) / 256.0;
            v1 = ((double) v + 16.0 * (double) f3 - 1.0) / 256.0;
            v2 = ((double) v + 16.0 * (double) f2 - 1.0 - 0.01) / 256.0;
            b1 = this.field_82.getBrightness(i, j, k);
            b2 = this.field_82.getBrightness(i - 1, j, k + 1);
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f1 + i, f2 + j, f + k, u1, v2);
            tessellator.vertex(f1 + i, f3 + j, f + k, u1, v1);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f1 + i + 1.0f, f3 + j, f + k + 1.0f, u2, v1);
            tessellator.vertex(f1 + i + 1.0f, f2 + j, f + k + 1.0f, u2, v2);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f + i + 1.0f, f2 + j, f1 + k + 1.0f, u2, v2);
            tessellator.vertex(f + i + 1.0f, f3 + j, f1 + k + 1.0f, u2, v1);
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f + i, f3 + j, f1 + k, u1, v1);
            tessellator.vertex(f + i, f2 + j, f1 + k, u1, v2);
            v1 = ((double) v + 16.0 * (double) f3) / 256.0;
            v2 = ((double) v + 16.0 * (double) f3 + 2.0 - 0.01) / 256.0;
            tessellator.colour(b2 * 0.5f, b2 * 0.5f, b2 * 0.5f);
            tessellator.vertex(f1 + i + 1.0f, f2 + j, f + k + 1.0f, u2, v1);
            tessellator.vertex(f + i + 1.0f, f2 + j, f1 + k + 1.0f, u2, v2);
            tessellator.colour(b1 * 0.5f, b1 * 0.5f, b1 * 0.5f);
            tessellator.vertex(f + i, f2 + j, f1 + k, u1, v2);
            tessellator.vertex(f1 + i, f2 + j, f + k, u1, v1);
            tessellator.colour(b2, b2, b2);
            tessellator.vertex(f + i + 1.0f, f3 + j, f1 + k + 1.0f, u2, v1);
            tessellator.vertex(f1 + i + 1.0f, f3 + j, f + k + 1.0f, u2, v2);
            tessellator.colour(b1, b1, b1);
            tessellator.vertex(f1 + i, f3 + j, f + k, u1, v2);
            tessellator.vertex(f + i, f3 + j, f1 + k, u1, v1);
            f2 = 0.75f;
            f3 = 0.9375f;
            v1 = ((double) v + 16.0 * (double) f3 - 1.0) / 256.0;
            v2 = ((double) v + 16.0 * (double) f2 - 1.0 - 0.01) / 256.0;
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f1 + i, f2 + j, f + k, u1, v2);
            tessellator.vertex(f1 + i, f3 + j, f + k, u1, v1);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f1 + i + 1.0f, f3 + j, f + k + 1.0f, u2, v1);
            tessellator.vertex(f1 + i + 1.0f, f2 + j, f + k + 1.0f, u2, v2);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f + i + 1.0f, f2 + j, f1 + k + 1.0f, u2, v2);
            tessellator.vertex(f + i + 1.0f, f3 + j, f1 + k + 1.0f, u2, v1);
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f + i, f3 + j, f1 + k, u1, v1);
            tessellator.vertex(f + i, f2 + j, f1 + k, u1, v2);
            v1 = ((double) v + 16.0 * (double) f3) / 256.0;
            v2 = ((double) v + 16.0 * (double) f3 - 2.0 - 0.01) / 256.0;
            tessellator.colour(b2 * 0.5f, b2 * 0.5f, b2 * 0.5f);
            tessellator.vertex(f1 + i + 1.0f, f2 + j, f + k + 1.0f, u2, v1);
            tessellator.vertex(f + i + 1.0f, f2 + j, f1 + k + 1.0f, u2, v2);
            tessellator.colour(b1 * 0.5f, b1 * 0.5f, b1 * 0.5f);
            tessellator.vertex(f + i, f2 + j, f1 + k, u1, v2);
            tessellator.vertex(f1 + i, f2 + j, f + k, u1, v1);
            tessellator.colour(b2, b2, b2);
            tessellator.vertex(f + i + 1.0f, f3 + j, f1 + k + 1.0f, u2, v1);
            tessellator.vertex(f1 + i + 1.0f, f3 + j, f + k + 1.0f, u2, v2);
            tessellator.colour(b1, b1, b1);
            tessellator.vertex(f1 + i, f3 + j, f + k, u1, v2);
            tessellator.vertex(f + i, f3 + j, f1 + k, u1, v1);
        }
        block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        return flag;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public boolean method_79(Tile block, int i, int j, int k) {
        boolean flag;
        int l = this.field_82.getTileMeta(i, j, k) & 3;
        block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        this.method_76(block, i, j, k);
        if (l == 0) {
            Tile b = Tile.BY_ID[this.field_82.getTileId(i - 1, j, k)];
            if (b != null && b.method_1621() == 10) {
                int m = this.field_82.getTileMeta(i - 1, j, k) & 3;
                if (m == 2) {
                    block.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                    this.method_76(block, i, j, k);
                } else if (m == 3) {
                    block.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                    this.method_76(block, i, j, k);
                }
            }
            int m = this.field_82.getTileMeta(i + 1, j, k) & 3;
            b = Tile.BY_ID[this.field_82.getTileId(i + 1, j, k)];
            if (b != null && b.method_1621() == 10 && (m == 2 || m == 3)) {
                if (m == 2) {
                    block.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                } else {
                    block.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                }
            } else {
                block.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
            this.method_76(block, i, j, k);
            flag = true;
        } else if (l == 1) {
            int m = this.field_82.getTileMeta(i - 1, j, k) & 3;
            Tile b = Tile.BY_ID[this.field_82.getTileId(i - 1, j, k)];
            if (b != null && b.method_1621() == 10 && (m == 2 || m == 3)) {
                if (m == 3) {
                    block.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                } else {
                    block.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                }
            } else {
                block.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 1.0f);
            }
            this.method_76(block, i, j, k);
            b = Tile.BY_ID[this.field_82.getTileId(i + 1, j, k)];
            if (b != null && b.method_1621() == 10) {
                m = this.field_82.getTileMeta(i + 1, j, k) & 3;
                if (m == 2) {
                    block.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                    this.method_76(block, i, j, k);
                } else if (m == 3) {
                    block.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                    this.method_76(block, i, j, k);
                }
            }
            flag = true;
        } else {
            Tile b;
            if (l == 2) {
                b = Tile.BY_ID[this.field_82.getTileId(i, j, k - 1)];
                if (b != null && b.method_1621() == 10) {
                    int m = this.field_82.getTileMeta(i, j, k - 1) & 3;
                    if (m == 1) {
                        block.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                        this.method_76(block, i, j, k);
                    } else if (m == 0) {
                        block.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                        this.method_76(block, i, j, k);
                    }
                }
                int m = this.field_82.getTileMeta(i, j, k + 1) & 3;
                b = Tile.BY_ID[this.field_82.getTileId(i, j, k + 1)];
                if (b != null && b.method_1621() == 10 && (m == 0 || m == 1)) {
                    if (m == 0) {
                        block.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                    } else {
                        block.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                    }
                } else {
                    block.setBoundingBox(0.0f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                }
            } else {
                b = Tile.BY_ID[this.field_82.getTileId(i, j, k + 1)];
                if (b != null && b.method_1621() == 10) {
                    int m = this.field_82.getTileMeta(i, j, k + 1) & 3;
                    if (m == 1) {
                        block.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                        this.method_76(block, i, j, k);
                    } else if (m == 0) {
                        block.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                        this.method_76(block, i, j, k);
                    }
                }
                int m = this.field_82.getTileMeta(i, j, k - 1) & 3;
                b = Tile.BY_ID[this.field_82.getTileId(i, j, k - 1)];
                if (b != null && b.method_1621() == 10 && (m == 0 || m == 1)) {
                    if (m == 0) {
                        block.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                    } else {
                        block.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                    }
                } else {
                    block.setBoundingBox(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                }
            }
            this.method_76(block, i, j, k);
            flag = true;
        }
        block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        return flag;
    }

    public boolean renderBlockSlope(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int l = this.field_82.getTileMeta(i, j, k) & 3;
        int texture = block.method_1626(this.field_82, i, j, k, 0);
        int u = (texture & 0xF) << 4;
        int v = texture & 0xF0;
        double u1 = (double) u / 256.0;
        double u2 = ((double) u + 16.0 - 0.01) / 256.0;
        double v1 = (double) v / 256.0;
        double v2 = ((double) v + 16.0 - 0.01) / 256.0;
        float b = block.method_1604(this.field_82, i, j, k);
        tessellator.colour(0.5f * b, 0.5f * b, 0.5f * b);
        block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        tessellator.vertex(i, j, k, u1, v1);
        tessellator.vertex(i + 1, j, k, u2, v1);
        tessellator.vertex(i + 1, j, k + 1, u2, v2);
        tessellator.vertex(i, j, k + 1, u1, v2);
        if (l == 0) {
            Tile nB = Tile.BY_ID[this.field_82.getTileId(i - 1, j, k)];
            int m = this.field_82.getTileMeta(i - 1, j, k) & 3;
            if (nB != null && nB.method_1621() == 38 && (m == 2 || m == 3)) {
                tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                if (m == 2) {
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                } else {
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                }
                tessellator.vertex(i, j, k, u1, v2);
                tessellator.vertex(i, j, k + 1, u2, v2);
                tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                tessellator.vertex(i + 1, j + 1, k, u1, v1);
                tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                tessellator.vertex(i + 1, j, k, u1, v2);
                tessellator.vertex(i + 1, j + 1, k, u1, v1);
                tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                tessellator.vertex(i + 1, j, k + 1, u2, v2);
            } else {
                m = this.field_82.getTileMeta(i + 1, j, k) & 3;
                nB = Tile.BY_ID[this.field_82.getTileId(i + 1, j, k)];
                if (nB != null && nB.method_1621() == 38 && (m == 2 || m == 3)) {
                    tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                    if (m == 2) {
                        tessellator.vertex(i + 1, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                        tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                        tessellator.vertex(i, j, k, u1, v2);
                        tessellator.vertex(i, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    } else {
                        tessellator.vertex(i, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k, u2, v1);
                        tessellator.vertex(i + 1, j + 1, k, u2, v1);
                        tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                        tessellator.vertex(i, j, k, u1, v2);
                        tessellator.vertex(i, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k, u1, v1);
                        tessellator.vertex(i + 1, j + 1, k, u1, v1);
                        tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                        tessellator.vertex(i + 1, j + 1, k, u1, v1);
                        tessellator.vertex(i + 1, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                    }
                } else {
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                }
            }
        } else if (l == 1) {
            Tile nB = Tile.BY_ID[this.field_82.getTileId(i + 1, j, k)];
            int m = this.field_82.getTileMeta(i + 1, j, k) & 3;
            if (nB != null && nB.method_1621() == 38 && (m == 2 || m == 3)) {
                tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                if (m == 2) {
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j + 1, k, u2, v1);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                } else {
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                }
                tessellator.vertex(i, j + 1, k, u2, v1);
                tessellator.vertex(i, j + 1, k + 1, u1, v1);
                tessellator.vertex(i + 1, j, k + 1, u1, v2);
                tessellator.vertex(i + 1, j, k, u2, v2);
                tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                tessellator.vertex(i, j, k, u1, v2);
                tessellator.vertex(i, j, k + 1, u2, v2);
                tessellator.vertex(i, j + 1, k + 1, u2, v1);
                tessellator.vertex(i, j + 1, k, u1, v1);
            } else {
                m = this.field_82.getTileMeta(i - 1, j, k) & 3;
                nB = Tile.BY_ID[this.field_82.getTileId(i - 1, j, k)];
                if (nB != null && nB.method_1621() == 38 && (m == 2 || m == 3)) {
                    tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                    if (m == 3) {
                        tessellator.vertex(i, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                        tessellator.vertex(i, j + 1, k, u1, v1);
                        tessellator.vertex(i, j + 1, k, u1, v1);
                        tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                        tessellator.vertex(i, j + 1, k, u2, v1);
                        tessellator.vertex(i, j + 1, k, u2, v1);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k, u2, v2);
                        tessellator.vertex(i, j + 1, k, u2, v1);
                        tessellator.vertex(i + 1, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                    } else {
                        tessellator.vertex(i, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i + 1, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                        tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                        tessellator.vertex(i, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k, u2, v2);
                        tessellator.vertex(i, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    }
                } else {
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j + 1, k, u2, v1);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k, u2, v1);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                }
            }
        } else if (l == 2) {
            int m = this.field_82.getTileMeta(i, j, k - 1) & 3;
            Tile nB = Tile.BY_ID[this.field_82.getTileId(i, j, k - 1)];
            if (nB != null && nB.method_1621() == 38 && (m == 0 || m == 1)) {
                tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                if (m == 1) {
                    tessellator.vertex(i, j + 1, k, u2, v1);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                } else {
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u1, v2);
                }
                tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                tessellator.vertex(i + 1, j, k + 1, u2, v2);
                tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                tessellator.vertex(i, j + 1, k + 1, u1, v1);
                tessellator.vertex(i, j, k + 1, u1, v2);
                tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                tessellator.vertex(i, j + 1, k + 1, u2, v1);
                tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                tessellator.vertex(i + 1, j, k, u1, v2);
                tessellator.vertex(i, j, k, u2, v2);
            } else {
                m = this.field_82.getTileMeta(i, j, k + 1) & 3;
                nB = Tile.BY_ID[this.field_82.getTileId(i, j, k + 1)];
                if (nB != null && nB.method_1621() == 38 && (m == 0 || m == 1)) {
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    if (m == 0) {
                        tessellator.vertex(i, j, k, u1, v2);
                        tessellator.vertex(i, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                        tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                        tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i + 1, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                        tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                        tessellator.vertex(i + 1, j, k, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    } else {
                        tessellator.vertex(i, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k, u2, v2);
                        tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                        tessellator.vertex(i, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i + 1, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                        tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                        tessellator.vertex(i, j, k + 1, u2, v2);
                        tessellator.vertex(i, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u1, v2);
                    }
                } else {
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                }
            }
        } else {
            int m = this.field_82.getTileMeta(i, j, k + 1) & 3;
            Tile nB = Tile.BY_ID[this.field_82.getTileId(i, j, k + 1)];
            if (nB != null && nB.method_1621() == 38 && (m == 0 || m == 1)) {
                tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                if (m == 1) {
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j + 1, k, u2, v1);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                } else {
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u1, v2);
                }
                tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                tessellator.vertex(i, j + 1, k, u1, v1);
                tessellator.vertex(i + 1, j + 1, k, u2, v1);
                tessellator.vertex(i + 1, j, k, u2, v2);
                tessellator.vertex(i, j, k, u1, v2);
                tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                tessellator.vertex(i, j, k + 1, u1, v2);
                tessellator.vertex(i + 1, j, k + 1, u2, v2);
                tessellator.vertex(i + 1, j + 1, k, u2, v1);
                tessellator.vertex(i, j + 1, k, u1, v1);
            } else {
                m = this.field_82.getTileMeta(i, j, k - 1) & 3;
                nB = Tile.BY_ID[this.field_82.getTileId(i, j, k - 1)];
                if (nB != null && nB.method_1621() == 38 && (m == 0 || m == 1)) {
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    if (m == 0) {
                        tessellator.vertex(i, j, k, u1, v2);
                        tessellator.vertex(i, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k, u1, v1);
                        tessellator.vertex(i + 1, j + 1, k, u1, v1);
                        tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                        tessellator.vertex(i, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k, u2, v1);
                        tessellator.vertex(i + 1, j + 1, k, u2, v1);
                        tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                        tessellator.vertex(i + 1, j, k, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k, u2, v1);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    } else {
                        tessellator.vertex(i, j + 1, k, u2, v1);
                        tessellator.vertex(i, j + 1, k, u2, v1);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k, u2, v2);
                        tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                        tessellator.vertex(i, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                        tessellator.vertex(i, j + 1, k, u1, v1);
                        tessellator.vertex(i, j + 1, k, u1, v1);
                        tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                        tessellator.vertex(i, j, k + 1, u2, v2);
                        tessellator.vertex(i, j + 1, k, u1, v1);
                        tessellator.vertex(i, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u1, v2);
                    }
                } else {
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u1, v2);
                }
            }
        }
        return true;
    }

    @Redirect(method = "method_80", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/tile/Tile;LUMINANCES:[I",
            opcode = Opcodes.GETSTATIC,
            args = "array=get"))
    private int redirectLuminanceInMethod_80(int[] array, int index) {
        return 0;
    }

    @ModifyVariable(method = "method_80", name = "var13", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/tile/Tile;LUMINANCES:[I",
            shift = At.Shift.BEFORE))
    private float modifyLuminanceAtMethod_80(float var13, Tile arg, int i, int j, int k) {
        if (((ExTile) arg).getBlockLightValue(this.field_82, i, j, k) > 0) {
            var13 = 1.0f;
        }
        return var13;
    }

    public boolean renderGrass(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        float f = block.method_1604(this.field_82, i, j + 1, k);
        int l = block.getTint(this.field_82, i, j, k);
        float r = (l >> 16 & 0xFF) / 255.0f;
        float g = (l >> 8 & 0xFF) / 255.0f;
        float b = (l & 0xFF) / 255.0f;
        int metadata = this.field_82.getTileMeta(i, j, k);
        float multiplier = ((ExGrassTile) Tile.GRASS).grassMultiplier(metadata);
        if (multiplier < 0.0f) {
            return false;
        }
        tessellator.colour(f * r * multiplier, f * g * multiplier, f * b * multiplier);
        double d1 = j - 0.0625f + 1.0f;
        this.rand.setSeed(i * i * 3121L + i * 45238971L + k * k * 418711L + k * 13761L + j);
        j = 168;
        int u = (j & 0xF) << 4;
        int v = j & 0xF0;
        double d3 = (u += this.rand.nextInt(32)) / 256.0f;
        double d4 = (u + 15.99f) / 256.0f;
        double d5 = v / 256.0f;
        double d6 = (v + 15.99f) / 256.0f;
        double d7 = i + 0.5 - 0.45f;
        double d8 = i + 0.5 + 0.45f;
        double d9 = k + 0.5 - 0.45f;
        double d10 = k + 0.5 + 0.45f;
        tessellator.vertex(d7, d1 + 1.0, d9, d3, d5);
        tessellator.vertex(d7, d1 + 0.0, d9, d3, d6);
        tessellator.vertex(d8, d1 + 0.0, d10, d4, d6);
        tessellator.vertex(d8, d1 + 1.0, d10, d4, d5);
        tessellator.vertex(d8, d1 + 1.0, d10, d3, d5);
        tessellator.vertex(d8, d1 + 0.0, d10, d3, d6);
        tessellator.vertex(d7, d1 + 0.0, d9, d4, d6);
        tessellator.vertex(d7, d1 + 1.0, d9, d4, d5);
        u = (j & 0xF) << 4;
        d3 = (u += this.rand.nextInt(32)) / 256.0f;
        d4 = (u + 15.99f) / 256.0f;
        d5 = v / 256.0f;
        d6 = (v + 15.99f) / 256.0f;
        tessellator.vertex(d7, d1 + 1.0, d10, d3, d5);
        tessellator.vertex(d7, d1 + 0.0, d10, d3, d6);
        tessellator.vertex(d8, d1 + 0.0, d9, d4, d6);
        tessellator.vertex(d8, d1 + 1.0, d9, d4, d5);
        tessellator.vertex(d8, d1 + 1.0, d9, d3, d5);
        tessellator.vertex(d8, d1 + 0.0, d9, d3, d6);
        tessellator.vertex(d7, d1 + 0.0, d10, d4, d6);
        tessellator.vertex(d7, d1 + 1.0, d10, d4, d5);
        return true;
    }

    public boolean renderSpikes(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        float f = block.method_1604(this.field_82, i, j, k);
        tessellator.colour(f, f, f);
        if (this.field_82.isFullOpaque(i, j - 1, k)) {
            this.method_47(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else if (this.field_82.isFullOpaque(i, j + 1, k)) {
            this.renderCrossedSquaresUpsideDown(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else if (this.field_82.isFullOpaque(i - 1, j, k)) {
            this.renderCrossedSquaresEast(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else if (this.field_82.isFullOpaque(i + 1, j, k)) {
            this.renderCrossedSquaresWest(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else if (this.field_82.isFullOpaque(i, j, k - 1)) {
            this.renderCrossedSquaresNorth(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else if (this.field_82.isFullOpaque(i, j, k + 1)) {
            this.renderCrossedSquaresSouth(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else {
            this.method_47(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        }
        return true;
    }

    public boolean renderTable(Tile block, int i, int j, int k) {
        boolean rendered = this.method_76(block, i, j, k);
        boolean north = this.field_82.getTileId(i, j, k + 1) != Blocks.tableBlocks.id;
        boolean south = this.field_82.getTileId(i, j, k - 1) != Blocks.tableBlocks.id;
        boolean west = this.field_82.getTileId(i - 1, j, k) != Blocks.tableBlocks.id;
        boolean east = this.field_82.getTileId(i + 1, j, k) != Blocks.tableBlocks.id;
        if (west && south) {
            block.setBoundingBox(0.0f, 0.0f, 0.0f, 0.1875f, 0.875f, 0.1875f);
            rendered |= this.method_76(block, i, j, k);
        }
        if (east && south) {
            block.setBoundingBox(0.8125f, 0.0f, 0.0f, 1.0f, 0.875f, 0.1875f);
            rendered |= this.method_76(block, i, j, k);
        }
        if (east && north) {
            block.setBoundingBox(0.8125f, 0.0f, 0.8125f, 1.0f, 0.875f, 1.0f);
            rendered |= this.method_76(block, i, j, k);
        }
        if (west && north) {
            block.setBoundingBox(0.0f, 0.0f, 0.8125f, 0.1875f, 0.875f, 1.0f);
            rendered |= this.method_76(block, i, j, k);
        }
        block.setBoundingBox(0.0f, 0.875f, 0.0f, 1.0f, 1.0f, 1.0f);
        return rendered;
    }

    public boolean renderChair(Tile block, int i, int j, int k) {
        boolean rendered = this.method_76(block, i, j, k);
        int side = this.field_82.getTileMeta(i, j, k) % 4;
        switch (side) {
            case 0: {
                block.setBoundingBox(0.125f, 0.625f, 0.125f, 0.25f, 1.25f, 0.875f);
                rendered |= this.method_76(block, i, j, k);
                break;
            }
            case 1: {
                block.setBoundingBox(0.125f, 0.625f, 0.125f, 0.875f, 1.25f, 0.25f);
                rendered |= this.method_76(block, i, j, k);
                break;
            }
            case 2: {
                block.setBoundingBox(0.75f, 0.625f, 0.125f, 0.875f, 1.25f, 0.875f);
                rendered |= this.method_76(block, i, j, k);
                break;
            }
            case 3: {
                block.setBoundingBox(0.125f, 0.625f, 0.75f, 0.875f, 1.25f, 0.875f);
                rendered |= this.method_76(block, i, j, k);
            }
        }
        block.setBoundingBox(0.125f, 0.0f, 0.125f, 0.25f, 0.5f, 0.25f);
        rendered |= this.method_76(block, i, j, k);
        block.setBoundingBox(0.75f, 0.0f, 0.125f, 0.875f, 0.5f, 0.25f);
        rendered |= this.method_76(block, i, j, k);
        block.setBoundingBox(0.75f, 0.0f, 0.75f, 0.875f, 0.5f, 0.875f);
        rendered |= this.method_76(block, i, j, k);
        block.setBoundingBox(0.125f, 0.0f, 0.75f, 0.25f, 0.5f, 0.875f);
        block.setBoundingBox(0.125f, 0.5f, 0.125f, 0.875f, 0.625f, 0.875f);
        return rendered | this.method_76(block, i, j, k);
    }

    public boolean renderRope(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        float f = block.method_1604(this.field_82, i, j, k);
        tessellator.colour(f, f, f);
        int m = this.field_82.getTileMeta(i, j, k) % 3;
        if (m == 0) {
            this.method_47(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else if (m == 1) {
            this.renderCrossedSquaresEast(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else {
            this.renderCrossedSquaresNorth(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        }
        return true;
    }

    public boolean renderBlockTree(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        float f = block.method_1604(this.field_82, i, j, k);
        tessellator.colour(f, f, f);
        TileEntity o = this.field_82.getTileEntity(i, j, k);
        TileEntityTree obj = null;
        if (o instanceof TileEntityTree) {
            obj = (TileEntityTree) o;
        }
        int m = this.field_82.getTileMeta(i, j, k);
        int t = block.getTextureForSide(0, m);
        if (this.field_83 >= 0) {
            t = this.field_83;
        }
        int u = (t & 0xF) << 4;
        int v = t & 0xF0;
        double d3 = u / 256.0f;
        double d4 = (u + 15.99f) / 256.0f;
        double d5 = v / 256.0f;
        double d6 = (v + 15.99f) / 256.0f;
        double size = 1.0;
        if (obj != null) {
            size = obj.size;
        }
        double d7 = i + 0.5 - 0.45f * size;
        double d8 = i + 0.5 + 0.45f * size;
        double d9 = k + 0.5 - 0.45f * size;
        double d10 = k + 0.5 + 0.45f * size;
        tessellator.vertex(d7, j + size, d9, d3, d5);
        tessellator.vertex(d7, j + 0.0, d9, d3, d6);
        tessellator.vertex(d8, j + 0.0, d10, d4, d6);
        tessellator.vertex(d8, j + size, d10, d4, d5);
        tessellator.vertex(d8, j + size, d10, d3, d5);
        tessellator.vertex(d8, j + 0.0, d10, d3, d6);
        tessellator.vertex(d7, j + 0.0, d9, d4, d6);
        tessellator.vertex(d7, j + size, d9, d4, d5);
        if (this.field_83 < 0) {
            t = block.getTextureForSide(1, m);
            u = (t & 0xF) << 4;
            v = t & 0xF0;
            d3 = u / 256.0f;
            d4 = (u + 15.99f) / 256.0f;
            d5 = v / 256.0f;
            d6 = (v + 15.99f) / 256.0f;
        }
        tessellator.vertex(d7, j + size, d10, d3, d5);
        tessellator.vertex(d7, j + 0.0, d10, d3, d6);
        tessellator.vertex(d8, j + 0.0, d9, d4, d6);
        tessellator.vertex(d8, j + size, d9, d4, d5);
        tessellator.vertex(d8, j + size, d9, d3, d5);
        tessellator.vertex(d8, j + 0.0, d9, d3, d6);
        tessellator.vertex(d7, j + 0.0, d10, d4, d6);
        tessellator.vertex(d7, j + size, d10, d4, d5);
        return true;
    }

    public boolean renderBlockOverlay(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        float f = block.method_1604(this.field_82, i, j, k);
        tessellator.colour(f, f, f);
        int m = this.field_82.getTileMeta(i, j, k);
        int t = block.getTextureForSide(0, m);
        ((BlockOverlay) block).updateBounds(this.field_82, i, j, k);
        if (this.field_82.isFullOpaque(i, j - 1, k)) {
            this.method_55(block, i, j, k, t);
        } else if (this.field_82.isFullOpaque(i, j + 1, k)) {
            this.method_46(block, i, j, k, t);
        } else if (this.field_82.isFullOpaque(i - 1, j, k)) {
            this.method_69(block, i, j, k, t);
        } else if (this.field_82.isFullOpaque(i + 1, j, k)) {
            this.method_67(block, i, j, k, t);
        } else if (this.field_82.isFullOpaque(i, j, k - 1)) {
            this.method_65(block, i, j, k, t);
        } else if (this.field_82.isFullOpaque(i, j, k + 1)) {
            this.method_61(block, i, j, k, t);
        } else {
            this.method_55(block, i, j, k, t);
        }
        return true;
    }
}
