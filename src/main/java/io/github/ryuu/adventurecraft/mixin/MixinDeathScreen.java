package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.widgets.Button;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DeathScreen.class)
public class MixinDeathScreen extends Screen {

    //TODO: This code was already commented out by Cryect in the AdventureCraft source.

    protected void a(Button guibutton) {
        // Byte code:
        //   0: aload_1
        //   1: getfield f : I
        //   4: ifeq -> 7
        //   7: aload_1
        //   8: getfield f : I
        //   11: iconst_1
        //   12: if_icmpne -> 33
        //   15: aload_0
        //   16: getfield b : Lnet/minecraft/client/Minecraft;
        //   19: getfield h : Ldc;
        //   22: invokevirtual p_ : ()V
        //   25: aload_0
        //   26: getfield b : Lnet/minecraft/client/Minecraft;
        //   29: aconst_null
        //   30: invokevirtual a : (Lda;)V
        //   33: aload_1
        //   34: getfield f : I
        //   37: iconst_2
        //   38: if_icmpne -> 63
        //   41: aload_0
        //   42: getfield b : Lnet/minecraft/client/Minecraft;
        //   45: aconst_null
        //   46: invokevirtual a : (Lfd;)V
        //   49: aload_0
        //   50: getfield b : Lnet/minecraft/client/Minecraft;
        //   53: new fu
        //   56: dup
        //   57: invokespecial <init> : ()V
        //   60: invokevirtual a : (Lda;)V
        //   63: return
        // Line number table:
        //   Java source line number -> byte code offset
        //   #38	-> 0
        //   #39	-> 7
        //   #41	-> 15
        //   #42	-> 25
        //   #44	-> 33
        //   #46	-> 41
        //   #47	-> 49
        //   #49	-> 63
        // Local variable table:
        //   start	length	slot	name	descriptor
        //   0	64	0	this	Lch;
        //   0	64	1	guibutton	Lke;
    }

    //TODO: Mixin here.

    public void render(int i, int j, float f) {
        this.fillGradient(0, 0, this.width, this.height, 1615855616, -1602211792);
        GL11.glPushMatrix();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        this.drawTextWithShadowCentred(this.textManager, "Game over!", this.width / 2 / 2, 30, 16777215);
        GL11.glPopMatrix();

        //TODO: Vanilla class has the following line: this.drawTextWithShadowCentred(this.textManager, "Score: &e" + this.minecraft.player.method_481(), this.width / 2, 100, 16777215);
        //TODO: We probably have to mixin that this line doesn't exist. Friendly Person on cursed-fabric discord said we have to use a redirect: https://fabricmc.net/wiki/tutorial:mixin_redirectors

        super.render(i, j, f);
    }
}
