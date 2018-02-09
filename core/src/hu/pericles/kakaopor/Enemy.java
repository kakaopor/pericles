package hu.pericles.kakaopor;

public class Enemy extends Moving {
    private double healthPoint;
    private static int NUMBER_OF_ENEMY = 0;
    private int enemyID = 0;
    private boolean alive;

    public Enemy(float startPositionX, float startPositionY, int speed, double healthPoint, double armor) {
        super(startPositionX, startPositionY, speed);
        this.healthPoint = healthPoint;
        enemyID = NUMBER_OF_ENEMY;
        NUMBER_OF_ENEMY++;
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

    public boolean Move(int tileX, int tileY, int tileDestinationX, int tileDestinationY) {
        boolean move = false;
        if ( (tileX <= tileDestinationX - 1 || tileX >= tileDestinationX + 1)
                && (tileY <= tileDestinationY - 1 || tileY >= tileDestinationY + 1) ) {
            move = true;
        }
        return move;
    }

}
