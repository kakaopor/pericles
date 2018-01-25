package hu.pericles.kakaopor;

public class TurretTower extends Entity {

    private double damage;

    public TurretTower(float positionX, float positionY, double damage) {
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
