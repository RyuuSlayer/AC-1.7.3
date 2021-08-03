package io.github.ryuu.adventurecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.level.Level;

class MusicPlayer {
    static void playNoteFromEntity(Level world, Entity ent, String instrument, char note, boolean sharp, float octave, float volume) {
        playNote(world, ent.aM, ent.aN, ent.aO, instrument, note, sharp, octave, volume);
    }

    static void playNote(Level world, double x, double y, double z, String instrument, char note, boolean sharp, float octave, float volume) {
        float pitch = 1.189207F;
        switch (note) {
            case 'A':
                break;
            case 'B':
                pitch *= 1.122462F;
                break;
            case 'C':
                pitch *= 1.189207F;
                break;
            case 'D':
                pitch *= 1.33484F;
                break;
            case 'E':
                pitch *= 1.498307F;
                break;
            case 'F':
                pitch *= 1.587401F;
                break;
            case 'G':
                pitch *= 1.781797F;
                break;
            default:
                return;
        }
        if (sharp)
            pitch = (float) (pitch * 1.059463D);
        world.a(x, y, z, instrument, volume, pitch * octave);
    }

    static void playNoteFromSong(Level world, double x, double y, double z, String instrument, String song, int noteNum, float volume) {
        int stringIndex = 0;
        int onNote = 0;
        boolean flat = false;
        boolean sharp = false;
        char note = 'A';
        float octave = 1.0F;
        while (onNote <= noteNum && stringIndex < song.length()) {
            char curChar = song.charAt(stringIndex);
            if (curChar == '+') {
                octave *= 2.0F;
            } else if (curChar == '-') {
                octave *= 0.5F;
            } else if (curChar != '#' && curChar != 'b') {
                note = curChar;
                onNote++;
            }
            stringIndex++;
        }
        if (stringIndex < song.length()) {
            char nextChar = song.charAt(stringIndex);
            if (nextChar == '#') {
                sharp = true;
            } else if (nextChar == 'b') {
                flat = true;
            }
        }
        if (flat) {
            if (note == 'A') {
                octave *= 0.5F;
                note = 'G';
            } else {
                note = (char) (note - 1);
            }
            sharp = true;
        }
        playNote(world, x, y, z, instrument, note, sharp, octave, volume);
    }

    static int countNotes(String song) {
        int stringIndex = 0;
        int onNote = 0;
        while (stringIndex < song.length()) {
            char curChar = song.charAt(stringIndex);
            if (curChar != '+' && curChar != '-' && curChar != '#' && curChar != 'b')
                onNote++;
            stringIndex++;
        }
        return onNote;
    }
}
