package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.script.ScriptEntity;
import net.minecraft.script.ScriptVec3;

public class ScriptEntityArrow extends ScriptEntity {

    Arrow entityArrow;

    ScriptEntityArrow(Arrow e) {
        super(e);
        this.entityArrow = e;
    }

    public int getInBlockID() {
        return this.entityArrow.inTile;
    }

    public ScriptVec3 getInBlockCoords() {
        if (this.entityArrow.inTile == 0) {
            return null;
        }
        return new ScriptVec3(this.entityArrow.xTile, this.entityArrow.yTile, this.entityArrow.zTile);
    }

    public boolean getIsPlayersArrow() {
        return this.entityArrow.player;
    }

    public void setIsPlayersArrow(boolean b) {
        this.entityArrow.player = b;
    }

    public ScriptEntity getOwner() {
        return ScriptEntity.getEntityClass(this.entityArrow);
    }
}
