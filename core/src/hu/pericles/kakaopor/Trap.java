package hu.pericles.kakaopor;

public class Trap extends Entity {
    private double damage;
    private static int level;

    Trap(float startPositionX, float startPositionY, double damage) {
        super(startPositionX, startPositionY);
        this.damage = damage;
        this.level = 0;
    }

    static int getLevel() {
        return level;
    }

    static void upLevel() {
        level++;
    }

    protected double getDamage() {
        return damage;
    }

    protected void setDamage(double newDamage) {
        damage = newDamage;
    }

}
