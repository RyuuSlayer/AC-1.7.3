package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.mixin.entity.projectile.AccessArrow;
import net.minecraft.entity.projectile.Arrow;

@SuppressWarnings("unused")
public class ScriptEntityArrow extends ScriptEntity {

    Arrow entityArrow;

    ScriptEntityArrow(Arrow entity) {
        super(entity);
        this.entityArrow = entity;
    }

    public int getInBlockID() {
        return ((AccessArrow)this.entityArrow).getInTile();
    }

    public ScriptVec3 getInBlockCoords() {
        AccessArrow arrow = (AccessArrow) this.entityArrow;
        if (arrow.getInTile() == 0) {
            return null;
        }
        return new ScriptVec3(arrow.getXTile(), arrow.getYTile(), arrow.getZTile());
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
