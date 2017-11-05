package hu.pericles.kakaopor;

public class Base extends Entity {
    private double healthPoint;
    private int level;

    public Base(float startPositionX, float startPositionY, double healthPoint) {
        super(startPositionX, startPositionY);
        this.healthPoint = healthPoint;
        level = 0;
    }

    protected int getLevel() {
        return level;
    }

    protected void upLevel() {
        this.level++;
        //this.sprite = new Sprite("baseLevel" + getLevel() + "Texture");
    }

    protected double getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(double healthPoint) {
        this.healthPoint = healthPoint;
    }

}
