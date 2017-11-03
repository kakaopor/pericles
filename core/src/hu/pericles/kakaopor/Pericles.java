package hu.pericles.kakaopor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Pericles extends ApplicationAdapter implements InputProcessor {
    // we will use 32px/unit in world
    public final static float SCALE = 32f;
    public final static float INV_SCALE = 1.f/SCALE;
    // this is our "target" resolution, not that the window can be any size, it is not bound to this one
    public final static float VP_WIDTH = 800 * INV_SCALE;
    public final static float VP_HEIGHT = 600 * INV_SCALE;

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private ShapeRenderer shapes;


	private SpriteBatch batch;
    /*TEXTURES*/
    private Texture baseLevel0Texture, baseLevel1Texture, baseLevel2Texture, baseLevel3Texture, baseLevel4Texture, baseLevel5Texture;
	private Texture enemyTexture, trapTexture;
    private Texture turretTowerTexture, turretBaseTexture;
    /*GAME CONSTANTS*/
    private static final int NUMBER_OF_ENEMY = 5;
    private static final int NUMBER_OF_TRAP = 10;

    private Enemy[] enemy = new Enemy[NUMBER_OF_ENEMY];
    private Trap[] trap = new Trap[NUMBER_OF_TRAP];

    private Base base;
    private Base turretTower, turretBase;

    private Background bg;
    private Texture bgTexture;

    private float[] destinationX = new float[NUMBER_OF_ENEMY];
    private float[] destinationY = new float[NUMBER_OF_ENEMY];

	@Override
	public void create () {
        camera = new OrthographicCamera();
        // pick a viewport that suits your thing, ExtendViewport is a good start
        viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT, camera);
        // ShapeRenderer so we can see our touch point
        shapes = new ShapeRenderer();
        Gdx.input.setInputProcessor(this);

		batch = new SpriteBatch();
        /*BACKGROUND*/
        bg = new Background(0, 0);
        bgTexture = new Texture(Gdx.files.internal("background/background.png") );
        bg.sprite = new Sprite(bgTexture);
        bg.sprite.setPosition(bg.getPositionX(), bg.getPositionY());
        /*TEXTURES*/
        /*Enemy textures*/
		enemyTexture = new Texture(Gdx.files.internal("enemy/enemy.png") );
        /*Base textures by level*/
        baseLevel0Texture = new Texture(Gdx.files.internal("base/base_level0.png") );
        baseLevel1Texture = new Texture(Gdx.files.internal("base/base_level1.png") );
        baseLevel2Texture = new Texture(Gdx.files.internal("base/base_level2.png") );
        baseLevel3Texture = new Texture(Gdx.files.internal("base/base_level3.png") );
        baseLevel4Texture = new Texture(Gdx.files.internal("base/base_level4.png") );
        baseLevel5Texture = new Texture(Gdx.files.internal("base/base_level5.png") );
        /*Trap textures*/
        trapTexture = new Texture(Gdx.files.internal("trap/trap.png") );
        /*TurretTower textures*/
        turretTowerTexture = new Texture(Gdx.files.internal("turret/turret_tower.png") );
        /*TurretBase textures*/
        turretBaseTexture = new Texture(Gdx.files.internal("turret/turret.png") );

        int startX = 100;
        int startY = 100;
        for (int i = 0; i < NUMBER_OF_ENEMY; i++) {
            enemy[i] = new Enemy(startX, startY, 5, 0 ,0);
            enemy[i].sprite = new Sprite(enemyTexture);
            enemy[i].sprite.setPosition(enemy[i].getPositionX(), enemy[i].getPositionY() );
            startX += 50;
        }

        base = new Base(368, 280, 100);
        base.sprite = new Sprite(baseLevel0Texture);
        base.sprite.setPosition(base.getPositionX(), base.getPositionY() );
        base.sprite.rotate(45);

	    trap[0] = new Trap(200, 400, 100);
        trap[0].sprite = new Sprite(trapTexture);
        trap[0].sprite.setPosition(trap[0].getPositionX(), trap[0].getPositionY() );

        turretTower = new Base(720, 200, 300);
        turretBase = new Base(720, 200, 300);
        turretTower.sprite = new Sprite(turretTowerTexture);
        turretBase.sprite = new Sprite(turretBaseTexture);
        turretTower.sprite.setPosition(turretTower.getPositionX(), turretTower.getPositionY() );
        turretTower.sprite.rotate(-90);
        turretBase.sprite.setPosition(turretBase.getPositionX(), turretBase.getPositionY() );

        for (int i = 0; i < NUMBER_OF_ENEMY; i++) {
            destinationX[i] = (float)Math.random() * 730;
            destinationY[i] = (float)Math.random() * 530;
        }
	}

	@Override
	public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapes.setProjectionMatrix(camera.combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.circle(tp.x, tp.y, 0.25f, 16);
        shapes.end();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        bg.sprite.draw(batch);

        for (int i = 0; i < NUMBER_OF_ENEMY; i++) {
            enemy[i].sprite.draw(batch);
        }


        for (int i = 0; i < NUMBER_OF_ENEMY; i++) {
            if (!(enemy[i].getPositionX() <= destinationX[i]+5 &&
                    enemy[i].getPositionX() >= destinationX[i]-5  &&
                    enemy[i].getPositionY() <= destinationY[i]+5 &&
                    enemy[i].getPositionY() >= destinationY[i]-5)) {
                enemy[i].move(destinationX[i], destinationY[i]);
            } else {
                destinationX[i] = (float)Math.random() * 730;
                destinationY[i] = (float)Math.random() * 530;
            }
        }


        base.sprite.draw(batch);
        base.sprite.rotate(1);

        trap[0].sprite.draw(batch);

        turretBase.sprite.draw(batch);

        turretTower.sprite.draw(batch);
        turretTower.sprite.rotate(5);

        batch.end();
    }

    Vector3 tp = new Vector3();
    boolean dragging;
    @Override public boolean mouseMoved (int screenX, int screenY) {
        // we can also handle mouse movement without anything pressed
//		camera.unproject(tp.set(screenX, screenY, 0));
        return false;
    }

    @Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        // ignore if its not left mouse button or first touch pointer
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        dragging = true;
        return true;
    }

    @Override public boolean touchDragged (int screenX, int screenY, int pointer) {
        if (!dragging) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        return true;
    }

    @Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        dragging = false;
        return true;
    }

    @Override public void resize (int width, int height) {
        // viewport must be updated for it to work properly
        viewport.update(width, height, true);
    }

    @Override public boolean keyDown (int keycode) {
        return false;
    }

    @Override public boolean keyUp (int keycode) {
        return false;
    }

    @Override public boolean keyTyped (char character) {
        return false;
    }

    @Override public boolean scrolled (int amount) {
        return false;
    }

    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        config.useHDPI = true;
        new LwjglApplication(new Pericles(), config);
    }

	@Override
	public void dispose () {
        shapes.dispose();
		batch.dispose();
        /*Dispose Background texture*/
        bgTexture.dispose();
        /*Dispose Enemy texture*/
	    enemyTexture.dispose();
        /*Dispose Base textures by level*/
        baseLevel0Texture.dispose();
        baseLevel1Texture.dispose();
        baseLevel2Texture.dispose();
        baseLevel3Texture.dispose();
        baseLevel4Texture.dispose();
        baseLevel5Texture.dispose();
        /*Dispose Trap texture*/
        trapTexture.dispose();
        /*Dispose TurretTower texture*/
        turretTowerTexture.dispose();
        /*Dispose TurretBase texture*/
        turretBaseTexture.dispose();
	}
}
