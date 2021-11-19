package io.github.ryuu.adventurecraft.scripting;

public class ScriptEntityArrow extends ScriptEntity {

    MixinArrow entityArrow;

    ScriptEntityArrow(MixinArrow e) {
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
