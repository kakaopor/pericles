package hu.pericles.kakaopor;

class TurretBase extends Entity {
    private static int level;

    TurretBase(float positionX, float positionY) {
        super(positionX, positionY);
        level = 0;
    }

    static int getLevel() {
        return level;
    }

    static void upLevel() {
        level++;
    }
}
