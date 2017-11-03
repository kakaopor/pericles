package hu.pericles.kakaopor;

public class Enemy extends Moving {
    private double healthPoint;
    private double armor;
    private int enemyID = 0;

    public Enemy(float startPositionX, float startPositionY, int speed, double healthPoint, double armor) {
        super(startPositionX, startPositionY, speed);
        this.healthPoint = healthPoint;
        this.armor = armor;
        enemyID++;
    }

    protected void setHealth(double healthChange) {
        healthPoint += healthChange;
    }

    protected double getHealth() {
        return healthPoint;
    }

}
