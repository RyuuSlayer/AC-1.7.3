package io.github.ryuu.adventurecraft.util;

import net.minecraft.client.Minecraft;

import java.util.LinkedList;

public class UndoStack {

    static final int MAX_UNDO = 128;
    boolean isRecording = false;
    UndoSelection selection = null;
    EditAction firstAction = null;
    EditAction lastAction = null;
    LinkedList<EditAction> undoStack = new LinkedList();
    LinkedList<EditAction> redoStack = new LinkedList();
    LinkedList<net.minecraft.src.UndoSelection> undoSelectionStack = new LinkedList();
    LinkedList<net.minecraft.src.UndoSelection> redoSelectionStack = new LinkedList();

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
            this.undoStack.addLast(this.firstAction);
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

    void recordChange(int x, int y, int z, int prevBlockID, int prevMeta, MixinCompoundTag prevTag, int nextBlockID, int nextMeta, MixinCompoundTag nextTag) {
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

    public void undo(MixinLevel w) {
        if (!this.undoStack.isEmpty()) {
            EditAction action = this.undoStack.removeLast();
            action.undo(w);
            this.redoStack.addLast(action);
            if (this.redoStack.size() > 128) {
                this.redoStack.removeFirst();
            }
            UndoSelection sel = (UndoSelection) this.undoSelectionStack.removeLast();
            sel.before.load();
            this.redoSelectionStack.addLast((Object) sel);
            if (this.redoSelectionStack.size() > 128) {
                this.redoSelectionStack.removeFirst();
            }
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Undo (Undo Actions Left: %d Redo Actions Left: %d)", new Object[]{this.undoStack.size(), this.redoStack.size()}));
        }
    }

    public void redo(MixinLevel w) {
        if (!this.redoStack.isEmpty()) {
            EditAction action = this.redoStack.removeLast();
            action.redo(w);
            this.undoStack.addLast(action);
            if (this.undoStack.size() > 128) {
                this.undoStack.removeFirst();
            }
            UndoSelection sel = (UndoSelection) this.redoSelectionStack.removeLast();
            sel.after.load();
            this.undoSelectionStack.addLast((Object) sel);
            if (this.undoSelectionStack.size() > 128) {
                this.undoSelectionStack.removeFirst();
            }
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Redo (Undo Actions Left: %d Redo Actions Left: %d)", new Object[]{this.undoStack.size(), this.redoStack.size()}));
        }
    }
}
