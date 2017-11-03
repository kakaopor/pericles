package hu.pericles.kakaopor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Entity {
    private float positionX, positionY;
    private int id;
    protected Sprite sprite;

    public Entity(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void rotate(float rotateDeg) {
        this.sprite.rotate(rotateDeg);
    }

}
