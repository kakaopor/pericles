package hu.pericles.kakaopor;

import static hu.pericles.kakaopor.states.PlayState.MAX_LEVEL;

public class Trap extends Entity {
    private static final int PRICE = 500;
    private static final int[] PRICE_UPGRADE = {250, 500, 1000, 2000, 4000};

    private static int level;

    private double damage;

    public Trap(float startPositionX, float startPositionY, double damage) {
        super(startPositionX, startPositionY);
        this.damage = damage;
        level = 0;
    }

    public static int getLevel() {
        return level;
    }

    public static void upLevel() {
        if (level < MAX_LEVEL) {
            level++;
        }
    }

    protected double getDamage() {
        return damage;
    }

    protected void setDamage(double damage) {
        this.damage = damage;
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
