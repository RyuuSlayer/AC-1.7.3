package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.entity.projectile.Arrow;

public class ScriptEntityArrow extends ScriptEntity {
    Arrow entityArrow;

    ScriptEntityArrow(Arrow e) {
        super(e);
        this.entityArrow = e;
    }

    public int getInBlockID() {
        return this.entityArrow.g;
    }

    public ScriptVec3 getInBlockCoords() {
        if (this.entityArrow.g == 0)
            return null;
        return new ScriptVec3(this.entityArrow.d, this.entityArrow.e, this.entityArrow.f);
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
