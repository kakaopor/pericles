package hu.pericles.kakaopor;

class Wall extends Entity {
    private double healthPoint;
    private double armor;

    public Wall(float startPositionX, float startPositionY, double healthPoint, double armor) {
        super(startPositionX, startPositionY);
        this.healthPoint = healthPoint;
        this.armor = armor;
    }

    protected void setHealth(double healthChange) {
        healthPoint += healthChange;
    }

    protected void setArmor(double armor) {
        this.armor = armor;
    }

    protected double getHealth() {
        return healthPoint;
    }

    protected double getArmor() {
        return armor;
    }


}