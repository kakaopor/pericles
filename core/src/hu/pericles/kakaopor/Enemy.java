package hu.pericles.kakaopor;

public class Enemy extends Moving {
    private double healthPoint;
    private double armor;
    private static int enemyID = 0;
    private boolean alive;

    public Enemy(float startPositionX, float startPositionY, int speed, double healthPoint, double armor) {
        super(startPositionX, startPositionY, speed);
        this.healthPoint = healthPoint;
        this.armor = armor;
        enemyID++;
        this.alive = true;
    }

    protected void setHealth(double healthChange) {
        healthPoint += healthChange;
    }

    public double getHealth() {
        return healthPoint;
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

}
