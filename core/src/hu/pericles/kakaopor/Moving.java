package hu.pericles.kakaopor;

import com.badlogic.gdx.Gdx;

public abstract class Moving extends Entity {
    private int speed;
    private boolean isRotated;
    private float currentDestinationX;
    private float currentDestionationY;

    protected Moving(float startPositionX, float startPositionY, int speed) {
        super(startPositionX, startPositionY);
        this.speed = speed;
        this.isRotated = false;
    }

    protected int getSpeed() {
        return speed;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    protected void move(float destinationX, float destinationY) {
        float deltaX = destinationX - getPositionX();
        float deltaY = destinationY - getPositionY();
        float absolute = Math.abs(deltaX) + Math.abs(deltaY);

        for (int i = 0; i < speed; i++) {
            sprite.translate(deltaX / absolute, deltaY /absolute );
            setPositionX(getPositionX() + deltaX / absolute);
            setPositionY(getPositionY() + deltaY / absolute);
        }

        /*If this is a new destination, then this Moving Entity is not rotated to the new destination*/
        if (destinationX != currentDestinationX || destinationY != currentDestionationY) {
            isRotated = false;
            currentDestinationX = destinationX;
            currentDestionationY = destinationY;
        }
        /*rotate only once per destination*/
        if (!isRotated) {
            float rotateDeg = (float) Math.atan2(deltaX, deltaY) * (float) (180 / Math.PI);
            rotate(rotateDeg);
            isRotated = true;
        }
    }

}
