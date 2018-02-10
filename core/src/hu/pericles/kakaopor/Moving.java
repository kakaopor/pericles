package hu.pericles.kakaopor;

import com.badlogic.gdx.graphics.Texture;

public abstract class Moving extends Entity {
    private int speed;
    private boolean isRotated;
    //private float currentDestinationX;
   // private float currentDestinationY;

    Moving(Texture texture, float x, float y, int speed) {
        super(texture, x, y);
        //super(startPositionX, startPositionY);
        this.speed = speed;
        this.isRotated = false;
    }

    protected int getSpeed() {
        return speed;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    /*public void move(float destinationX, float destinationY) {
        float deltaX = destinationX - getPositionX();
        float deltaY = destinationY - getPositionY();
        float absolute = Math.abs(deltaX) + Math.abs(deltaY);

        for (int i = 0; i < speed; i++) {
            sprite.translate(deltaX / absolute, deltaY /absolute );
            setPosition(
                    getPositionX() + deltaX / absolute,
                    getPositionY() + deltaY / absolute
            );
        }

        If this is a new destination, then this Moving Entity is not rotated to the new destination
        if (destinationX != currentDestinationX || destinationY != currentDestinationY) {
            isRotated = false;
            currentDestinationX = destinationX;
            currentDestinationY = destinationY;
        }
        rotate only once per destination
        if (!isRotated) {
            float rotateDeg = (float) Math.atan2(deltaX, deltaY) * (float) (180 / Math.PI);
            rotate(rotateDeg);
            isRotated = true;
        }
    }*/

}
