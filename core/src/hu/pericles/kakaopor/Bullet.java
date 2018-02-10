package hu.pericles.kakaopor;

import com.badlogic.gdx.graphics.Texture;

class Bullet extends Moving {
    private double damage;

    public Bullet(Texture texture, float x, float y, int speed, double damage) {
        super(texture, x, y, speed);
        this.damage = damage;
    }

    protected void setDamage(double damage) {
        this.damage = damage;
    }

    protected double getDamage() {
        return damage;
    }


}
