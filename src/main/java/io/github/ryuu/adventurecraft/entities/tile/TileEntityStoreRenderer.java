package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.render.entity.tile.TileEntityRenderer;
import net.minecraft.tile.Tile;
import org.lwjgl.opengl.GL11;

public class TileEntityStoreRenderer extends TileEntityRenderer {
    public TileEntityStoreRenderer() {
        renderItem.a(th.a);
    }

    public void renderTileEntityStore(TileEntityStore store, double d, double d1, double d2, float f) {
        if (store.buySupplyLeft != 0 && store.buyItemID != 0) {
            item.c = store.buyItemID;
            item.a = store.buyItemAmount;
            item.b(store.buyItemDamage);
            eItem.aI = store.d;
            eItem.e(store.e, store.f, store.g);
            renderItem.a(eItem, d + 0.5D, d1 + 0.125D, d2 + 0.5D, 0.0F, 0.0F);
        }
        if (DebugMode.active && store.tradeTrigger != null) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glTranslatef((float) d + 0.5F, (float) d1 + 0.5F, (float) d2 + 0.5F);
            GL11.glLineWidth(6.0F);
            GL11.glShadeModel(7425);
            GL11.glBegin(1);
            for (int i = store.tradeTrigger.minX; i <= store.tradeTrigger.maxX; i++) {
                for (int j = store.tradeTrigger.minY; j <= store.tradeTrigger.maxY; j++) {
                    for (int k = store.tradeTrigger.minZ; k <= store.tradeTrigger.maxZ; k++) {
                        Tile block = Tile.m[store.d.a(i, j, k)];
                        if (block != null && block.canBeTriggered()) {
                            GL11.glColor3f(0.0F, 0.0F, 0.0F);
                            GL11.glVertex3f(0.0F, 0.0F, 0.0F);
                            GL11.glColor3f(1.0F, 1.0F, 1.0F);
                            GL11.glVertex3f((i - store.e), (j - store.f), (k - store.g));
                        }
                    }
                }
            }
            GL11.glEnd();
            GL11.glShadeModel(7424);
            GL11.glLineWidth(1.0F);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }

    public void a(ow tileentity, double d, double d1, double d2, float f) {
        renderTileEntityStore((TileEntityStore) tileentity, d, d1, d2, f);
    }

    static iz item = new iz(0, 0, 0);

    static hl eItem = new hl(null, 0.0D, 0.0D, 0.0D, item);

    static bb renderItem = new bb();

    static {
        renderItem.scale = 1.5F;
    }
}
