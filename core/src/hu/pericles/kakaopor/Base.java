package hu.pericles.kakaopor;

class Base extends Entity {

    private static double healthPoint;
    private static int level;
    private static boolean alive;

    private static boolean rotate;
    private static int rotateCounter;

    Base(float startPositionX, float startPositionY, double healthPoint) {
        super(startPositionX, startPositionY);
        Base.healthPoint = healthPoint;
        Base.level = 0;
        Base.alive = true;
        Base.rotate = true;
        Base.rotateCounter = 0;
    }

    static int getLevel() {
        return level;
    }

    static void upLevel() {
        level++;
    }

    static double getHealthPoint() {
        return healthPoint;
    }

    static void setHealthPoint(double healthPoint) {
        Base.healthPoint = healthPoint;
    }

    static boolean isAlive() {
        return alive;
    }

    static void kill() {
        Base.alive = false;
    }

    /*Rotates the base 180 degress left, then right and repeat.*/
    void rotator() {
        if (rotateCounter >= 180) {
            if (rotate) {
                rotate = false;
            } else {
                rotate = true;
            }
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
