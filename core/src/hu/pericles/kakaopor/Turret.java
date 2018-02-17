package hu.pericles.kakaopor;

import com.badlogic.gdx.graphics.Texture;

import static hu.pericles.kakaopor.states.PlayState.MAX_LEVEL;

public class Turret extends Entity {
    private static final int PRICE = 1500;
    private static final int[] PRICE_UPGRADE = {750, 1500, 3000, 6000, 12000};
    private static int level;
    private int damage;

    public Turret(Texture texture, float x, float y, int damage) {
        super(texture, x, y);
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

    public static int getPRICE() {
        return PRICE;
    }

    public static int getPriceUpgrade(int level) {
        if (level < MAX_LEVEL - 2) {
            return PRICE_UPGRADE[level + 1];
        }
        return 0;
    }

    protected void setDamage(int damage) {
        this.damage = damage;
    }

    protected double getDamage() {
        return damage;

    }
}
