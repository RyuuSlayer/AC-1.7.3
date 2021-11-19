/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.System
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.mozilla.javascript.Scriptable
 */
package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.io.CompoundTag;
import org.mozilla.javascript.Scriptable;
import io.github.ryuu.adventurecraft.mixin.item.MixinCompoundTag;

public class ScopeTag {

    public static MixinCompoundTag getTagFromScope(Scriptable scope) {
        MixinCompoundTag tag = new MixinCompoundTag();
        for (Object id : scope.getIds()) {
            if (!(id instanceof String))
                continue;
            String key = (String) id;
            Object value = scope.get(key, scope);
            ScopeTag.saveProperty(tag, key, value);
        }
        return tag;
    }

    private static void saveProperty(MixinCompoundTag tag, String key, Object value) {
        if (value instanceof String) {
            String strValue = (String) value;
            tag.put("String_" + key, strValue);
        } else if (value instanceof Boolean) {
            boolean bValue = (Boolean) value;
            tag.put("Boolean_" + key, bValue);
        } else if (value instanceof Number) {
            Number nValue = (Number) value;
            double dValue = nValue.doubleValue();
            float fValue = nValue.floatValue();
            long lValue = nValue.longValue();
            int iValue = nValue.intValue();
            short sValue = nValue.shortValue();
            if (dValue != (double) fValue) {
                tag.put("Double_" + key, dValue);
            } else if (fValue != (float) lValue) {
                tag.put("Float_" + key, fValue);
            } else if (lValue != (long) iValue) {
                tag.put("Long_" + key, lValue);
            } else if (iValue != sValue) {
                tag.put("Integer_" + key, iValue);
            } else {
                tag.put("Short_" + key, sValue);
            }
        }
    }

    public static void loadScopeFromTag(Scriptable scope, MixinCompoundTag tag) {
        for (String varKey : tag.getKeys()) {
            String[] parts = varKey.split("_", 2);
            if (parts.length != 2) {
                System.out.printf("Unknown key in tag: %s %d\n", new Object[] { varKey, parts.length });
                continue;
            }
            String type = parts[0];
            String key = parts[1];
            if (type.equals((Object) "String")) {
                String value = tag.getString(varKey);
                scope.put(key, scope, (Object) value);
                continue;
            }
            if (type.equals((Object) "Boolean")) {
                boolean value = tag.getBoolean(varKey);
                scope.put(key, scope, (Object) new Boolean(value));
                continue;
            }
            if (type.equals((Object) "Double")) {
                double value = tag.getDouble(varKey);
                scope.put(key, scope, (Object) new Double(value));
                continue;
            }
            if (type.equals((Object) "Float")) {
                float value = tag.getFloat(varKey);
                scope.put(key, scope, (Object) new Float(value));
                continue;
            }
            if (type.equals((Object) "Long")) {
                long value = tag.getLong(varKey);
                scope.put(key, scope, (Object) new Long(value));
                continue;
            }
            if (type.equals((Object) "Integer")) {
                int value = tag.getInt(varKey);
                scope.put(key, scope, (Object) new Integer(value));
                continue;
            }
            if (type.equals((Object) "Short")) {
                short value = tag.getShort(varKey);
                scope.put(key, scope, (Object) new Short(value));
                continue;
            }
            System.out.printf("Unknown type: %s\n", new Object[] { type });
        }
    }
}
