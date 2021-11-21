package io.github.ryuu.adventurecraft.items;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.script.ScriptItem;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class ItemCustom extends ItemType {

    String fileName;

    String onItemUsedScript;

    static ArrayList<Integer> loadedItemIDs = new ArrayList();

    private static ItemCustom loadScript(File descFile) {
        block7: {
            Properties p = new Properties();
            try {
                p.load((InputStream) new FileInputStream(descFile));
                int itemID = Integer.parseInt((String) p.getProperty("itemID", "-1"));
                if (itemID == -1) {
                    Minecraft.minecraftInstance.overlay.addChatMessage(String.format((String) "ItemID for %s is unspecified", (Object[]) new Object[] { descFile.getName() }));
                    break block7;
                }
                if (itemID <= 0) {
                    Minecraft.minecraftInstance.overlay.addChatMessage(String.format((String) "ItemID for %s specifies a negative itemID", (Object[]) new Object[] { descFile.getName() }));
                    break block7;
                }
                if (ItemType.byId[itemID] != null) {
                    Minecraft.minecraftInstance.overlay.addChatMessage(String.format((String) "ItemID (%d) for %s is already in use by %s", (Object[]) new Object[] { itemID, descFile.getName() }));
                    break block7;
                }
                return new ItemCustom(itemID, descFile.getName(), p);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                Minecraft.minecraftInstance.overlay.addChatMessage(String.format((String) "ItemID for %s is specified invalidly '%s'", (Object[]) new Object[] { descFile.getName(), p.getProperty("itemID") }));
            }
        }
        return null;
    }

    public ItemCustom(int itemID, String fName, Properties p) {
        super(itemID - 256);
        Integer maxStackSizeI;
        String maxStackSizeS;
        Integer maxItemDamageI;
        String maxItemDamageS;
        Integer iconIndexI;
        this.fileName = fName;
        String iconIndexS = p.getProperty("iconIndex");
        if (iconIndexS != null && (iconIndexI = this.loadPropertyInt("iconIndex", iconIndexS)) != null) {
            this.setTexturePosition(iconIndexI);
        }
        if ((maxItemDamageS = p.getProperty("maxItemDamage")) != null && (maxItemDamageI = this.loadPropertyInt("maxItemDamage", maxItemDamageS)) != null) {
            this.field_402 = maxItemDamageI;
        }
        if ((maxStackSizeS = p.getProperty("maxStackSize")) != null && (maxStackSizeI = this.loadPropertyInt("maxStackSize", maxStackSizeS)) != null) {
            this.maxStackSize = maxStackSizeI;
        }
        this.setName(p.getProperty("name", "Unnamed"));
        this.onItemUsedScript = p.getProperty("onItemUsedScript", "");
        this.itemUseDelay = 1;
    }

    private Integer loadPropertyInt(String pName, String intString) {
        try {
            Integer i = Integer.parseInt((String) intString);
            return i;
        } catch (NumberFormatException e) {
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format((String) "Item File '%s' Property '%s' is specified invalidly '%s'", (Object[]) new Object[] { this.fileName, pName, intString }));
            return null;
        }
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, Player player) {
        if (!this.onItemUsedScript.equals((Object) "")) {
            ScriptItem item2 = new ScriptItem(item);
            Object wrappedOut = Context.javaToJS((Object) item2, (Scriptable) level.scope);
            ScriptableObject.putProperty((Scriptable) level.scope, (String) "itemUsed", (Object) wrappedOut);
            level.scriptHandler.runScript(this.onItemUsedScript, level.scope);
        }
        return item;
    }

    static void loadItems(File itemFolder) {
        for (Integer i : loadedItemIDs) {
            ItemType.byId[i.intValue()] = null;
        }
        loadedItemIDs.clear();
        if (!itemFolder.exists()) {
            return;
        }
        for (File itemFile : itemFolder.listFiles()) {
            ItemCustom item;
            if (!itemFile.isFile() || (item = ItemCustom.loadScript(itemFile)) == null)
                continue;
            loadedItemIDs.add((Object) item.id);
        }
    }
}
