package hu.pericles.kakaopor;

import static hu.pericles.kakaopor.states.PlayState.MAX_LEVEL;

public class TurretBase extends Entity {
    private static final int PRICE = 1500;
    private static final int[] PRICE_UPGRADE = {750, 1500, 3000, 6000, 12000};

    private static int level;

    public TurretBase(float positionX, float positionY) {
        super(positionX, positionY);
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

}
