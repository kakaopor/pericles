package hu.pericles.kakaopor;

public class Bullet extends Moving {
    private double damage;

    public Bullet(float startPositionX, float startPositionY, int speed, double damage) {
        super(startPositionX, startPositionY, speed);
        this.damage = damage;
    }

    protected void setDamage(double damage) {
        this.damage = damage;
    }

    protected double getDamage() {
        return damage;
    }


}
