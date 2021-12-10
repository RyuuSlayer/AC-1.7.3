package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.extensions.client.render.ExTextRenderer;
import net.minecraft.client.GLAllocator;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.CharacterUtils;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;

@Mixin(TextRenderer.class)
public abstract class MixinTextRenderer implements ExTextRenderer {

    @Shadow
    private int[] widths;

    @Shadow
    private int glList;

    @Shadow
    private IntBuffer buffer;

    @Shadow
    public int imageId;

    @Override
    public void drawStringWithShadow(String s, float i, float j, int k) {
        this.renderString(s, i + 1.0f, j + 1.0f, k, true);
        this.drawString(s, i, j, k);
    }

    @Override
    public void drawString(String s, float i, float j, int k) {
        this.renderString(s, i, j, k, false);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void drawText(String text, int x, int y, int colour, boolean shadow) {
        this.renderString(text, x, y, colour, shadow);
    }

    public void renderString(String s, float x, float y, int k, boolean flag) {
        if (s != null) {
            if (flag) {
                int l = k & 0xFF000000;
                k = (k & 0xFCFCFC) >> 2;
                k += l;
            }

            GL11.glBindTexture(3553, this.imageId);
            float f = (k >> 16 & 0xFF) / 255.0f;
            float f1 = (k >> 8 & 0xFF) / 255.0f;
            float f2 = (k & 0xFF) / 255.0f;
            float f3 = (k >> 24 & 0xFF) / 255.0f;
            if (f3 == 0.0f) {
                f3 = 1.0f;
            }

            GL11.glColor4f(f, f1, f2, f3);
            this.buffer.clear();
            GL11.glPushMatrix();
            GL11.glTranslatef(x, y, 0.0f);

            for (int i1 = 0; i1 < s.length(); ++i1) {
                while (s.length() > i1 + 1 && s.charAt(i1) == '\u00a7') {
                    int j1 = "0123456789abcdef".indexOf(s.toLowerCase().charAt(i1 + 1));
                    if (j1 < 0 || j1 > 15) {
                        j1 = 15;
                    }

                    this.buffer.put(this.glList + 256 + j1 + (flag ? 16 : 0));
                    if (this.buffer.remaining() == 0) {
                        this.buffer.flip();
                        GL11.glCallLists(this.buffer);
                        this.buffer.clear();
                    }

                    i1 += 2;
                }

                if (i1 < s.length()) {
                    int k1 = CharacterUtils.SUPPORTED_CHARS.indexOf(s.charAt(i1));
                    char c = s.charAt(i1);
                    if (k1 >= 0 && c < '\u00b0') {
                        this.buffer.put(this.glList + k1 + 32);
                    } else if (c < '\u0100') {
                        this.buffer.put(this.glList + c);
                    }
                }
                if (this.buffer.remaining() != 0) continue;
                this.buffer.flip();
                GL11.glCallLists(this.buffer);
                this.buffer.clear();
            }
            this.buffer.flip();
            GL11.glCallLists(this.buffer);
            GL11.glPopMatrix();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public int getTextWidth(String text) {
        if (text == null) {
            return 0;
        }
        int i = 0;
        for (int j = 0; j < text.length(); ++j) {
            if (text.charAt(j) == '\u00a7') {
                ++j;
                continue;
            }
            int k = CharacterUtils.SUPPORTED_CHARS.indexOf(text.charAt(j));
            char c = text.charAt(j);
            if (k >= 0 && c < '\u00b0') {
                i += this.widths[k + 32];
                continue;
            }
            if (c >= '\u0100') continue;
            i += this.widths[c];
        }
        return i;
    }

    @Shadow
    public abstract void drawTextWithoutShadow(String string, int i, int j, int k);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void drawMultilineText(String text, int x, int y, int width, int colour) {
        String[] as = text.split("\n");
        if (as.length > 1) {
            for (String a : as) {
                this.drawMultilineText(a, x, y, width, colour);
                y += this.getLineWidth(a, width);
            }
        } else {
            String[] as1 = text.split(" ");
            int j1 = 0;

            while (j1 < as1.length) {
                StringBuilder s1 = new StringBuilder(as1[j1++] + " ");
                while (j1 < as1.length && this.getTextWidth(s1 + as1[j1]) < width) {
                    s1.append(as1[j1++]).append(" ");
                }

                while (this.getTextWidth(s1.toString()) > width) {
                    int k1 = 0;
                    while (this.getTextWidth(s1.substring(0, k1 + 1)) <= width) {
                        ++k1;
                    }
                    if (s1.substring(0, k1).trim().length() > 0) {
                        this.drawTextWithoutShadow(s1.substring(0, k1), x, y, colour);
                        y += 8;
                    }
                    s1 = new StringBuilder(s1.substring(k1));
                }
                if (s1.toString().trim().length() <= 0) continue;
                this.drawTextWithoutShadow(s1.toString(), x, y, colour);
                y += 8;
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public int getLineWidth(String text, int width) {
        String[] as = text.split("\n");
        if (as.length > 1) {
            int j = 0;
            for (String a : as) {
                j += this.getLineWidth(a, width);
            }
            return j;
        }
        String[] as1 = text.split(" ");
        int l = 0;
        int i1 = 0;
        while (l < as1.length) {
            StringBuilder s1 = new StringBuilder(as1[l++] + " ");
            while (l < as1.length && this.getTextWidth(s1 + as1[l]) < width) {
                s1.append(as1[l++]).append(" ");
            }

            while (this.getTextWidth(s1.toString()) > width) {
                int j1 = 0;
                while (this.getTextWidth(s1.substring(0, j1 + 1)) <= width) {
                    ++j1;
                }
                if (s1.substring(0, j1).trim().length() > 0) {
                    i1 += 8;
                }
                s1 = new StringBuilder(s1.substring(j1));
            }
            if (s1.toString().trim().length() <= 0) continue;
            i1 += 8;
        }
        if (i1 < 8) {
            i1 += 8;
        }
        return i1;
    }
}
