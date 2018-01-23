package hu.pericles.kakaopor;

public class TurretBase extends Entity {
    private static int level;

    public TurretBase(float positionX, float positionY) {
        super(positionX, positionY);
        level = 0;
    }

    public static int getLevel() {
        return level;
    }

    public static void upLevel() {
        level++;
    }
}
