package hu.pericles.kakaopor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Entity {
    private float positionX, positionY;
    private int id;
    Sprite sprite;

    Entity(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    void setPosition(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    float getPositionX() {
        return positionX;
    }

    float getPositionY() {
        return positionY;
    }

    void rotate(float rotateDeg) {
        this.sprite.rotate(rotateDeg);
    }

}
