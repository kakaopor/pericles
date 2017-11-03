package hu.pericles.kakaopor;

public class Trap extends Entity {
    double damage;

    protected Trap(float startPositionX, float startPositionY, double damage) {
        super(startPositionX, startPositionY);
        this.damage = damage;
    }

    protected double getDamage() {
        return damage;
    }

    protected void setDamage(double newDamage) {
        damage = newDamage;
    }

}
