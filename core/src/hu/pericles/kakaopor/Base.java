package hu.pericles.kakaopor;

import com.badlogic.gdx.graphics.Texture;

import static hu.pericles.kakaopor.states.PlayState.MAX_LEVEL;

public class Base extends Entity {

    private static final int[] PRICE_UPGRADE = {10000, 25000, 50000, 100000, 250000};

    private static double healthPoint;
    private static int level;
    private static boolean rotate;
    private static int rotateCounter;

    public Base(Texture texture, float x, float y, double healthPoint) {
        super(texture, x, y);
        Base.healthPoint = healthPoint;
        Base.level = 0;
        Base.rotate = true;
        Base.rotateCounter = 0;
    }

    public static int getLevel() {
        return level;
    }

    public static void upLevel() {
        if (level < MAX_LEVEL) {
            level++;
        }
    }

    public static double getHealthPoint() {
        return healthPoint;
    }

    public static void setHealthPoint(double healthPoint) {
        Base.healthPoint = healthPoint;
    }

    public static int getPriceUpgrade(int level) {
        if (level < MAX_LEVEL - 2) {
            return PRICE_UPGRADE[level + 1];
        }
        return 0;
    }

    /*Rotates the base 180 degress left, then right and repeat.*/
    public void rotator() {
        if (rotateCounter >= 180) {
            rotate = !rotate;
            rotateCounter = 0;
        }

        if (rotate) {
            this.rotate(1);
        } else {
            this.rotate(-1);
        }
        rotateCounter++;
    }

}
