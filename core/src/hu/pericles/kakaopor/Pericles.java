package hu.pericles.kakaopor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hu.pericles.kakaopor.states.GameStateManager;
import hu.pericles.kakaopor.states.MenuState;

public class Pericles extends ApplicationAdapter {

    private GameStateManager gameStateManager;
    private SpriteBatch spriteBatch;

    @Override
    public void create () {
        spriteBatch = new SpriteBatch();
        gameStateManager = new GameStateManager();
        Gdx.gl.glClearColor(0,0,0,1);
        gameStateManager.push(new MenuState(gameStateManager) );
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(64/255f, 128/255f, 35/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStateManager.update(Gdx.graphics.getDeltaTime() );
        gameStateManager.render(spriteBatch);

    }

    @Override
    public void dispose () {


    }

}