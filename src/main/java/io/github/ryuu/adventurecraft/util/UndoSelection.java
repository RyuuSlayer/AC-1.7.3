package io.github.ryuu.adventurecraft.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

class UndoSelection {

    Selection before = new Selection();

    Selection after = new Selection();

    UndoSelection() {
        this.before.record();
    }
}
