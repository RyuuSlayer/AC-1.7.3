package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.entities.EntityNPC;

public class ScriptEntityNPC extends ScriptEntityLivingScript {

    EntityNPC npc;

    ScriptEntityNPC(EntityNPC e) {
        super(e);
        this.npc = e;
    }

    public String getName() {
        return this.npc.npcName;
    }

    public void setName(String name) {
        this.npc.npcName = name;
    }

    public String getChatMsg() {
        return this.npc.chatMsg;
    }

    public void setChatMsg(String msg) {
        this.npc.chatMsg = msg;
    }

    public double getSpawnX() {
        return this.npc.spawnX;
    }

    public void setSpawnX(double x) {
        this.npc.spawnX = x;
    }

    public double getSpawnY() {
        return this.npc.spawnY;
    }

    public void setSpawnY(double x) {
        this.npc.spawnY = x;
    }

    public double getSpawnZ() {
        return this.npc.spawnZ;
    }

    public void setSpawnZ(double x) {
        this.npc.spawnZ = x;
    }

    public boolean getPathToHome() {
        return this.npc.pathToHome;
    }

    public void setPathToHome(boolean b) {
        this.npc.pathToHome = b;
    }

    public boolean getTrackPlayer() {
        return this.npc.trackPlayer;
    }

    public void setTrackPlayer(boolean b) {
        this.npc.trackPlayer = b;
    }
}
