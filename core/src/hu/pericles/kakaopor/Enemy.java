package hu.pericles.kakaopor;

import com.badlogic.gdx.graphics.Texture;

public class Enemy extends Moving {
    private double healthPoint;
    //private static int NUMBER_OF_ENEMY = 0;
    private int deg;
    //private int armor;
    //private int enemyID = 0;

    public Enemy(Texture texture, float x, float y, int speed, double healthPoint, double armor) {
        super(texture, x, y, speed);
        this.healthPoint = healthPoint;
        //enemyID = NUMBER_OF_ENEMY;
        //this.armor = armor;
        this.deg = 0;
        //NUMBER_OF_ENEMY++;
    }

    public void setHealth(double healthChange) {
        healthPoint += healthChange;
    }

    public double getHealth() {
        return healthPoint;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getDeg() {
        return deg;
    }

}
