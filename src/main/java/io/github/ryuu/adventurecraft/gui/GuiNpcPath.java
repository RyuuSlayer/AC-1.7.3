package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.EntityNPC;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;

public class GuiNpcPath extends Screen {
    private TileEntityNpcPath path;

    public GuiNpcPath(TileEntityNpcPath npcPath) {
        this.path = npcPath;
    }

    public void a() {}

    public void b() {
        String entityName = "<Unselected>";
        EntityNPC e = TileEntityNpcPath.lastEntity;
        if (e != null)
            entityName = e.npcName;
        this.e.add(new ab(0, 4, 20, String.format("Set Path NPC: %s", new Object[] { entityName })));
        this.e.add(new ab(1, 4, 80, "Use Current Selection"));
        this.e.add(new ab(2, 4, 100, "Reset Target"));
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0) {
            this.path.setEntityToLastSelected();
        } else if (guibutton.f == 1) {
            this.path.minX = ItemCursor.minX;
            this.path.minY = ItemCursor.minY;
            this.path.minZ = ItemCursor.minZ;
            this.path.maxX = ItemCursor.maxX;
            this.path.maxY = ItemCursor.maxY;
            this.path.maxZ = ItemCursor.maxZ;
        } else if (guibutton.f == 2) {
            this.path.minX = 0;
            this.path.minY = 0;
            this.path.minZ = 0;
            this.path.maxX = 0;
            this.path.maxY = 0;
            this.path.maxZ = 0;
        }
        this.b.f.b(this.path.e, this.path.f).g();
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        String entityName = "<Not Set>";
        EntityNPC e = this.path.getNPC();
        if (e != null)
            entityName = e.npcName;
        b(this.g, String.format("NPC: %s", new Object[] { entityName }), 4, 4, 14737632);
        b(this.g, String.format("Min: (%d, %d, %d)", new Object[] { Integer.valueOf(this.path.minX), Integer.valueOf(this.path.minY), Integer.valueOf(this.path.minZ) }), 4, 44, 14737632);
        b(this.g, String.format("Max: (%d, %d, %d)", new Object[] { Integer.valueOf(this.path.maxX), Integer.valueOf(this.path.maxY), Integer.valueOf(this.path.maxZ) }), 4, 64, 14737632);
        super.a(i, j, f);
    }

    public static void showUI(TileEntityNpcPath npcPath) {
        Minecraft.minecraftInstance.a(new GuiNpcPath(npcPath));
    }

    public boolean c() {
        return false;
    }
}