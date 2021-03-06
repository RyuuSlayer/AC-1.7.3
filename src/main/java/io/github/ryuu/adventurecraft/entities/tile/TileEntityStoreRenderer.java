package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.extensions.client.render.entity.ExItemRenderer;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.render.entity.tile.TileEntityRenderer;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemInstance;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileEntityStoreRenderer extends TileEntityRenderer {

    static ItemInstance item = new ItemInstance(0, 0, 0);

    static ItemEntity eItem = new ItemEntity(null, 0.0, 0.0, 0.0, item);

    static ItemRenderer renderItem = new ItemRenderer();

    static {
        ((ExItemRenderer) TileEntityStoreRenderer.renderItem).setScale(1.5f);
    }

    public TileEntityStoreRenderer() {
        renderItem.setDispatcher(EntityRenderDispatcher.INSTANCE);
    }

    public void renderTileEntityStore(TileEntityStore store, double d, double d1, double d2, float f) {
        if (store.buySupplyLeft != 0 && store.buyItemID != 0) {
            TileEntityStoreRenderer.item.itemId = store.buyItemID;
            TileEntityStoreRenderer.item.count = store.buyItemAmount;
            item.setDamage(store.buyItemDamage);
            TileEntityStoreRenderer.eItem.level = store.level;
            eItem.setPosition(store.x, store.y, store.z);
            renderItem.render(eItem, d + 0.5, d1 + 0.125, d2 + 0.5, 0.0f, 0.0f);
        }
        if (DebugMode.active && store.tradeTrigger != null) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glTranslatef((float) d + 0.5f, (float) d1 + 0.5f, (float) d2 + 0.5f);
            GL11.glLineWidth(6.0f);
            GL11.glShadeModel(7425);
            GL11.glBegin(1);
            for (int i = store.tradeTrigger.minX; i <= store.tradeTrigger.maxX; ++i) {
                for (int j = store.tradeTrigger.minY; j <= store.tradeTrigger.maxY; ++j) {
                    for (int k = store.tradeTrigger.minZ; k <= store.tradeTrigger.maxZ; ++k) {
                        Tile block = Tile.BY_ID[store.level.getTileId(i, j, k)];
                        if (block == null || !((ExTile) block).canBeTriggered()) continue;
                        GL11.glColor3f(0.0f, 0.0f, 0.0f);
                        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
                        GL11.glColor3f(1.0f, 1.0f, 1.0f);
                        GL11.glVertex3f((float) (i - store.x), (float) (j - store.y), (float) (k - store.z));
                    }
                }
            }
            GL11.glEnd();
            GL11.glShadeModel(7424);
            GL11.glLineWidth(1.0f);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void render(TileEntity entity, double x, double y, double z, float f) {
        this.renderTileEntityStore((TileEntityStore) entity, x, y, z, f);
    }
}
