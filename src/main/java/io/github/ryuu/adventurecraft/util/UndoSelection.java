package io.github.ryuu.adventurecraft.util;

class UndoSelection {
    Selection before;

    Selection after;

    UndoSelection() {
        this.before = new Selection();
        this.after = new Selection();
        this.before.record();
    }
}
