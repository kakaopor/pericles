package hu.pericles.kakaopor;

public class Enemy extends Moving {
    private double healthPoint;
    private double armor;
    private static int enemyID = 0;
    private boolean alive;

    Enemy(float startPositionX, float startPositionY, int speed, double healthPoint, double armor) {
        super(startPositionX, startPositionY, speed);
        this.healthPoint = healthPoint;
        this.armor = armor;
        enemyID++;
        this.alive = true;
    }

    protected void setHealth(double healthChange) {
        healthPoint += healthChange;
    }

    double getHealth() {
        return healthPoint;
    }

    boolean isAlive() {
        return alive;
    }

    void kill() {
        alive = false;
    }

}
