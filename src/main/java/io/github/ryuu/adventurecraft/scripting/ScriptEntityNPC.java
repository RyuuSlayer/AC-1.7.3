package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.entities.EntityNPC;

@SuppressWarnings("unused")
public class ScriptEntityNPC extends ScriptEntityLivingScript {

    EntityNPC npc;

    public ScriptEntityNPC(EntityNPC entity) {
        super(entity);
        this.npc = entity;
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

    public void setSpawnY(double y) {
        this.npc.spawnY = y;
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
