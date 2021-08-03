package io.github.ryuu.adventurecraft.util;

class ProfileContext {
    public String contextName;

    long startTime;

    public ProfileContext(String n) {
        this.contextName = n;
        this.startTime = System.nanoTime();
    }

    long getTime() {
        return System.nanoTime() - this.startTime;
    }
}
