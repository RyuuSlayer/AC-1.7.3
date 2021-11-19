package io.github.ryuu.adventurecraft.util;

class MusicPlayer {

    MusicPlayer() {
    }

    static void playNoteFromEntity(MixinLevel world, MixinEntity ent, String instrument, char note, boolean sharp, float octave, float volume) {
        MusicPlayer.playNote(world, ent.x, ent.y, ent.z, instrument, note, sharp, octave, volume);
    }

    static void playNote(MixinLevel world, double x, double y, double z, String instrument, char note, boolean sharp, float octave, float volume) {
        float pitch = 1.189207f;
        switch (note) {
            case 'A': {
                break;
            }
            case 'B': {
                pitch *= 1.122462f;
                break;
            }
            case 'C': {
                pitch *= 1.189207f;
                break;
            }
            case 'D': {
                pitch *= 1.33484f;
                break;
            }
            case 'E': {
                pitch *= 1.498307f;
                break;
            }
            case 'F': {
                pitch *= 1.587401f;
                break;
            }
            case 'G': {
                pitch *= 1.781797f;
                break;
            }
            default: {
                return;
            }
        }
        if (sharp) {
            pitch = (float) ((double) pitch * 1.059463);
        }
        world.playSound(x, y, z, instrument, volume, pitch * octave);
    }

    static void playNoteFromSong(MixinLevel world, double x, double y, double z, String instrument, String song, int noteNum, float volume) {
        int stringIndex;
        int onNote = 0;
        boolean flat = false;
        boolean sharp = false;
        char note = 'A';
        float octave = 1.0f;
        for (stringIndex = 0; onNote <= noteNum && stringIndex < song.length(); ++stringIndex) {
            char curChar = song.charAt(stringIndex);
            if (curChar == '+') {
                octave *= 2.0f;
                continue;
            }
            if (curChar == '-') {
                octave *= 0.5f;
                continue;
            }
            if (curChar == '#' || curChar == 'b') continue;
            note = curChar;
            ++onNote;
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
                octave *= 0.5f;
                note = 'G';
            } else {
                note = (char) (note - 1);
            }
            sharp = true;
        }
        MusicPlayer.playNote(world, x, y, z, instrument, note, sharp, octave, volume);
    }

    static int countNotes(String song) {
        int onNote = 0;
        for (int stringIndex = 0; stringIndex < song.length(); ++stringIndex) {
            char curChar = song.charAt(stringIndex);
            if (curChar == '+' || curChar == '-' || curChar == '#' || curChar == 'b') continue;
            ++onNote;
        }
        return onNote;
    }
}
