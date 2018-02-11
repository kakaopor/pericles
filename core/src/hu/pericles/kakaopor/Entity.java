package hu.pericles.kakaopor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

abstract class Entity extends Sprite {
    private boolean alive;

    Entity(Texture texture, float x, float y) {
        super(texture);
        this.setX(x);
        this.setY(y);
        this.alive = true;
    }

    public void kill() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

}
