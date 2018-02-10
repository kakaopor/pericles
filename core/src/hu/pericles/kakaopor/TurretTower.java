package hu.pericles.kakaopor;

import com.badlogic.gdx.graphics.Texture;

public class TurretTower extends Entity {

    private double damage;

    public TurretTower(Texture texture, float x, float y, double damage) {
        super(texture, x, y);
        this.damage = damage;
    }

    protected void setDamage(double damage) {
        this.damage = damage;
    }

    protected double getDamage() {
        return damage;

    }

}
