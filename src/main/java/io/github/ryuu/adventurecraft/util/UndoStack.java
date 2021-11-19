package io.github.ryuu.adventurecraft.util;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.LinkedList
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.src.EditAction
 *  net.minecraft.src.UndoSelection
 */
import java.util.LinkedList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.src.EditAction;
import net.minecraft.util.io.CompoundTag;

public class UndoStack {

    boolean isRecording = false;

    UndoSelection selection = null;

    EditAction firstAction = null;

    EditAction lastAction = null;

    LinkedList<EditAction> undoStack = new LinkedList();

    LinkedList<EditAction> redoStack = new LinkedList();

    LinkedList<net.minecraft.src.UndoSelection> undoSelectionStack = new LinkedList();

    LinkedList<net.minecraft.src.UndoSelection> redoSelectionStack = new LinkedList();

    static final int MAX_UNDO = 128;

    UndoStack() {
    }

    public void startRecording() {
        assert (!this.isRecording);
        this.isRecording = true;
        this.selection = new UndoSelection();
    }

    public void stopRecording() {
        assert (this.isRecording);
        if (this.firstAction != null) {
            this.redoStack.clear();
            this.undoStack.addLast((Object) this.firstAction);
            if (this.undoStack.size() > 128) {
                this.undoStack.removeFirst();
            }
            this.selection.after.record();
            this.undoSelectionStack.addLast((Object) this.selection);
            if (this.undoSelectionStack.size() > 128) {
                this.undoSelectionStack.removeFirst();
            }
            this.firstAction = null;
            this.lastAction = null;
            this.selection = null;
        }
        this.isRecording = false;
    }

    boolean isRecording() {
        return this.isRecording;
    }

    void recordChange(int x, int y, int z, int prevBlockID, int prevMeta, CompoundTag prevTag, int nextBlockID, int nextMeta, CompoundTag nextTag) {
        EditAction newAction = new EditAction(x, y, z, prevBlockID, prevMeta, prevTag, nextBlockID, nextMeta, nextTag);
        if (this.firstAction == null) {
            this.firstAction = newAction;
        } else {
            EditAction checking = this.firstAction;
            while (checking != null) {
                if (checking.x == x && checking.y == y && checking.z == z) {
                    checking.newBlockID = nextBlockID;
                    checking.newMetadata = nextMeta;
                    checking.newNBT = nextTag;
                    return;
                }
                checking = checking.nextAction;
            }
            this.lastAction.nextAction = newAction;
        }
        this.lastAction = newAction;
    }

    public void undo(Level w) {
        if (!this.undoStack.isEmpty()) {
            EditAction action = (EditAction) this.undoStack.removeLast();
            action.undo(w);
            this.redoStack.addLast((Object) action);
            if (this.redoStack.size() > 128) {
                this.redoStack.removeFirst();
            }
            UndoSelection sel = (UndoSelection) this.undoSelectionStack.removeLast();
            sel.before.load();
            this.redoSelectionStack.addLast((Object) sel);
            if (this.redoSelectionStack.size() > 128) {
                this.redoSelectionStack.removeFirst();
            }
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format((String) "Undo (Undo Actions Left: %d Redo Actions Left: %d)", (Object[]) new Object[] { this.undoStack.size(), this.redoStack.size() }));
        }
    }

    public void redo(Level w) {
        if (!this.redoStack.isEmpty()) {
            EditAction action = (EditAction) this.redoStack.removeLast();
            action.redo(w);
            this.undoStack.addLast((Object) action);
            if (this.undoStack.size() > 128) {
                this.undoStack.removeFirst();
            }
            UndoSelection sel = (UndoSelection) this.redoSelectionStack.removeLast();
            sel.after.load();
            this.undoSelectionStack.addLast((Object) sel);
            if (this.undoSelectionStack.size() > 128) {
                this.undoSelectionStack.removeFirst();
            }
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format((String) "Redo (Undo Actions Left: %d Redo Actions Left: %d)", (Object[]) new Object[] { this.undoStack.size(), this.redoStack.size() }));
        }
    }
}
