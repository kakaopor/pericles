package hu.pericles.kakaopor;

import com.badlogic.gdx.graphics.Texture;

import static hu.pericles.kakaopor.states.PlayState.MAX_LEVEL;

public class TurretBase extends Entity {
    private static final int PRICE = 1500;
    private static final int[] PRICE_UPGRADE = {750, 1500, 3000, 6000, 12000};

    private static int level;

    public TurretBase(Texture texture, float x, float y) {
        super(texture, x, y);
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
