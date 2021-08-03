package io.github.ryuu.adventurecraft.gui;

import org.lwjgl.opengl.GL11;

public class GuiStore extends da {
    iz buyItem = new iz(0, 0, 0);

    iz sellItem = new iz(0, 0, 0);

    int supplyLeft;

    public void a(int i, int j, float f) {
        nh t = nh.a();
        int yOffset = 64;
        if (this.supplyLeft < 0) {
            a(this.c / 2 - 38, this.d / 2 - 10 - 12 - yOffset, this.c / 2 + 38, this.d / 2 + 10 - yOffset, -2147483648);
        } else {
            a(this.c / 2 - 38, this.d / 2 - 10 - 12 - yOffset, this.c / 2 + 38, this.d / 2 + 10 + 12 - yOffset, -2147483648);
        }
        if (this.buyItem.c != 0 && this.sellItem.c != 0) {
            a(this.g, t.a("store.trade"), this.c / 2, this.d / 2 - 19 - yOffset, 16777215);
            a(this.g, t.a("store.for"), this.c / 2, this.d / 2 - 4 - yOffset, 16777215);
        } else if (this.buyItem.c != 0) {
            a(this.g, t.a("store.receive"), this.c / 2, this.d / 2 - 19 - yOffset, 16777215);
        } else if (this.sellItem.c != 0) {
            a(this.g, t.a("store.insert"), this.c / 2, this.d / 2 - 19 - yOffset, 16777215);
        }
        if (this.supplyLeft > 0)
            a(this.g, String.format("%s: %d", new Object[]{t.a("store.tradesLeft"), Integer.valueOf(this.supplyLeft)}), this.c / 2, this.d / 2 + 11 - yOffset, 16777215);
        if (this.buyItem.c != 0 && this.sellItem.c != 0) {
            a(this.c / 2 + 11, this.d / 2 - 9 - yOffset, this.c / 2 + 29, this.d / 2 + 9 - yOffset, -1433695349);
            a(this.c / 2 - 29, this.d / 2 - 9 - yOffset, this.c / 2 - 11, this.d / 2 + 9 - yOffset, -1433695349);
            GL11.glPushMatrix();
            GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
            u.b();
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(32826);
            if (gm.c[this.buyItem.c] != null) {
                itemRenderer.a(this.g, this.b.p, this.buyItem, this.c / 2 + 12, this.d / 2 - 8 - yOffset);
                itemRenderer.b(this.g, this.b.p, this.buyItem, this.c / 2 + 12, this.d / 2 - 8 - yOffset);
            }
            if (gm.c[this.sellItem.c] != null) {
                itemRenderer.a(this.g, this.b.p, this.sellItem, this.c / 2 - 28, this.d / 2 - 8 - yOffset);
                itemRenderer.b(this.g, this.b.p, this.sellItem, this.c / 2 - 28, this.d / 2 - 8 - yOffset);
            }
            GL11.glDisable(32826);
            u.a();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
        } else if (this.buyItem.c != 0) {
            a(this.c / 2 - 9, this.d / 2 - 9 - yOffset, this.c / 2 + 9, this.d / 2 + 9 - yOffset, -1433695349);
            if (gm.c[this.buyItem.c] != null) {
                GL11.glPushMatrix();
                GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
                u.b();
                GL11.glPopMatrix();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(32826);
                itemRenderer.a(this.g, this.b.p, this.buyItem, this.c / 2 - 8, this.d / 2 - 8 - yOffset);
                itemRenderer.b(this.g, this.b.p, this.buyItem, this.c / 2 - 8, this.d / 2 - 8 - yOffset);
                GL11.glDisable(32826);
                u.a();
                GL11.glDisable(2896);
                GL11.glDisable(2929);
            }
        } else if (this.sellItem.c != 0) {
            a(this.c / 2 - 9, this.d / 2 - 9 - yOffset, this.c / 2 + 9, this.d / 2 + 9 - yOffset, -1433695349);
            if (gm.c[this.sellItem.c] != null) {
                GL11.glPushMatrix();
                GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
                u.b();
                GL11.glPopMatrix();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(32826);
                itemRenderer.a(this.g, this.b.p, this.sellItem, this.c / 2 - 8, this.d / 2 - 8 - yOffset);
                itemRenderer.b(this.g, this.b.p, this.sellItem, this.c / 2 - 8, this.d / 2 - 8 - yOffset);
                GL11.glDisable(32826);
                u.a();
                GL11.glDisable(2896);
                GL11.glDisable(2929);
            }
        }
    }

    public void setBuyItem(int itemID, int amount, int damage) {
        this.buyItem.c = itemID;
        this.buyItem.a = amount;
        this.buyItem.b(damage);
    }

    public void setSellItem(int itemID, int amount, int damage) {
        this.sellItem.c = itemID;
        this.sellItem.a = amount;
        this.sellItem.b(damage);
    }

    public void setSupplyLeft(int s) {
        this.supplyLeft = s;
    }

    private static final bb itemRenderer = new bb();
}