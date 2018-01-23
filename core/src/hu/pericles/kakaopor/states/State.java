package hu.pericles.kakaopor.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    private OrthographicCamera cam;
    private Vector3 mouse;
    GameStateManager gameStateManager;

    State(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    public abstract void update(float timeDelta);
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();
}
