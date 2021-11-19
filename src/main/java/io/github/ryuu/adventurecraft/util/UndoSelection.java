package io.github.ryuu.adventurecraft.util;

class UndoSelection {

    Selection before = new Selection();

    Selection after = new Selection();

    UndoSelection() {
        this.before.record();
    }
}
