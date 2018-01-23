package hu.pericles.kakaopor.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State implements InputProcessor {

    private Texture buttonNewGame;
    private Texture buttonLoadGame;
    private Texture buttonExitGame;

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        buttonNewGame = new Texture("menu/new_game.png");
        buttonLoadGame = new Texture("menu/load_game.png");
        buttonExitGame = new Texture("menu/exit_game.png");

        Gdx.input.setInputProcessor(this);
    }

    private void textureRestore() {
        buttonNewGame = new Texture("menu/new_game.png");
        buttonLoadGame = new Texture("menu/load_game.png");
        buttonExitGame = new Texture("menu/exit_game.png");
    }

    public void handleInput() {}

    @Override
    public void update(float timeDelta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(buttonNewGame, Gdx.graphics.getWidth() / 2 - buttonNewGame.getWidth() / 2, Gdx.graphics.getHeight() / 2 + buttonNewGame.getHeight() );
        batch.draw(buttonLoadGame,Gdx.graphics.getWidth() / 2 - buttonLoadGame.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        batch.draw(buttonExitGame, Gdx.graphics.getWidth() / 2 - buttonExitGame.getWidth() / 2, Gdx.graphics.getHeight() / 2 - buttonExitGame.getHeight() );
        batch.end();
    }


    @Override
    public void dispose() {
        buttonNewGame.dispose();
        buttonLoadGame.dispose();
        buttonExitGame.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int WIDTH_HALF = Gdx.graphics.getWidth() / 2;
        int HEIGHT_HALF = Gdx.graphics.getHeight() / 2;
        int WIDTH_HALF_BUTTON = buttonNewGame.getWidth() / 2;
        int HEIGHT__BUTTON = buttonNewGame.getHeight();
        int y = Gdx.graphics.getHeight() - screenY;

        if ( (screenX >= WIDTH_HALF - WIDTH_HALF_BUTTON) && (screenX <= WIDTH_HALF + WIDTH_HALF_BUTTON) ) {
            if ( (y > HEIGHT_HALF + HEIGHT__BUTTON * 0.5) && (y < HEIGHT_HALF + HEIGHT__BUTTON * 1.5 ) ) {
                gameStateManager.set(new PlayState(gameStateManager) );
                dispose();
            } else if (y > HEIGHT_HALF - HEIGHT__BUTTON * 0.5) {
                gameStateManager.set(new PlayState(gameStateManager) );
                dispose();
            } else if (y > HEIGHT_HALF - HEIGHT__BUTTON * 1.5) {
                System.exit(0);
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        int WIDTH_HALF = Gdx.graphics.getWidth() / 2;
        int HEIGHT_HALF = Gdx.graphics.getHeight() / 2;
        int WIDTH_HALF_BUTTON = buttonNewGame.getWidth() / 2;
        int HEIGHT__BUTTON = buttonNewGame.getHeight();
        int y = Gdx.graphics.getHeight() - screenY;

        if ( (screenX >= WIDTH_HALF - WIDTH_HALF_BUTTON) && (screenX <= WIDTH_HALF + WIDTH_HALF_BUTTON) ) {
            if ( (y > HEIGHT_HALF + HEIGHT__BUTTON * 0.5) && (y < HEIGHT_HALF + HEIGHT__BUTTON * 1.5 ) ) {
                textureRestore();
                buttonNewGame = new Texture("menu/new_game_hover.png");
            } else if (y > HEIGHT_HALF - HEIGHT__BUTTON * 0.5) {
                textureRestore();
                buttonLoadGame = new Texture("menu/load_game_hover.png");
            } else if (y > HEIGHT_HALF - HEIGHT__BUTTON * 1.5) {
                textureRestore();
                buttonExitGame = new Texture("menu/exit_game_hover.png");
            }
        } else {
            textureRestore();
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
