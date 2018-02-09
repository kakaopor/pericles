package hu.pericles.kakaopor;

import static hu.pericles.kakaopor.states.PlayState.MAX_LEVEL;

public class Wall extends Entity {
    private static final int PRICE = 250;
    private static final int[] PRICE_UPGRADE = {125, 250, 500, 1000, 2000};

    private static int level;

    private double healthPoint;

    public Wall(float startPositionX, float startPositionY, double healthPoint) {
        super(startPositionX, startPositionY);
        this.healthPoint = healthPoint;
        Wall.level = 0;
    }

    protected void setHealthPoint(double healthChange) {
        healthPoint += healthChange;
    }

    protected double getHealthPoint() {
        return healthPoint;
    }

    public static int getLevel() {
        return level;
    }

    public static void upLevel() {
        if (level < MAX_LEVEL) {
            level++;
        }
    }

    public static int getPRICE() {
        return PRICE;
    }

    public static int getPriceUpgrade(int level) {
        if (level < MAX_LEVEL - 2) {
            return PRICE_UPGRADE[level + 1];
        }
        return 0;
    }

}
