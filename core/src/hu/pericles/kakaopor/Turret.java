package hu.pericles.kakaopor;

public abstract class Turret extends Entity {
    double damage;

    public Turret(float positionX, float positionY, double damage) {
        super(positionX, positionY);
        this.damage = damage;
    }

    protected void setDamage(double damage) {
        this.damage = damage;
    }

    protected double getDamage() {
        return damage;

    }
}
