package hu.pericles.kakaopor;

import com.badlogic.gdx.graphics.Texture;

public class Enemy extends Moving {
    private double healthPoint;
    private static int NUMBER_OF_ENEMY = 0;
    private int enemyID = 0;
    private boolean alive;

    public Enemy(Texture texture, float x, float y, int speed, double healthPoint, double armor) {
        super(texture, x, y, speed);
        this.healthPoint = healthPoint;
        enemyID = NUMBER_OF_ENEMY;
        NUMBER_OF_ENEMY++;
        this.alive = true;
    }

    public void setHealth(double healthChange) {
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

    /*public boolean Move(int tileX, int tileY, int tileDestinationX, int tileDestinationY) {
        boolean move = false;
        if ( (tileX <= tileDestinationX - 1 || tileX >= tileDestinationX + 1)
                && (tileY <= tileDestinationY - 1 || tileY >= tileDestinationY + 1) ) {
            move = true;
        }
        return move;
    }*/

    /*public int nextX(int tileX, int tileY, OccupancyGrid grid) {
        return grid[tileX][tileY].desX;
    }

    public void move(int tileX, int tileY, OccupancyGrid grid) {
        int[] destination = new int[2];
        destination
    }*/

    /*public int move(int tileX, int tileY, OccupancyGrid occupancyGrid) {
        return occupancyGrid[tileX][tileY].desX;
    }*/

}
