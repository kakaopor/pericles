package hu.pericles.kakaopor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pericles extends ApplicationAdapter implements InputProcessor {
    private SpriteBatch batch;
    /*TEXTURES*/
    private Texture baseLevel0Texture, baseLevel1Texture, baseLevel2Texture, baseLevel3Texture, baseLevel4Texture, baseLevel5Texture;
    private Texture enemyTexture, trapTexture;
    private Texture turretTowerTexture, turretBaseTexture;
    /*GAME CONSTANTS*/
    private static final int NUMBER_OF_ENEMY = 10;
    private static final int NUMBER_OF_TRAP = 15;
    private static final int NUMBER_OF_TOWER = 15;

    private Enemy[] enemy = new Enemy[NUMBER_OF_ENEMY];
    private Trap[] trap = new Trap[NUMBER_OF_TRAP];
    private TurretTower[] turretTower = new TurretTower[NUMBER_OF_TOWER];
    private TurretBase[] turretBase = new TurretBase[NUMBER_OF_TOWER];
    private Base base;

    /*Global Game Variables*/
    private int actualTrap = 0;
    private int actualTower = 0;
    private boolean isTrap = true;

    private float[] destinationX = new float[NUMBER_OF_ENEMY];
    private float[] destinationY = new float[NUMBER_OF_ENEMY];

    @Override
    public void create () {
        batch = new SpriteBatch();
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

        for (int i = 0; i < NUMBER_OF_TRAP; i++) {
            trap[i] = new Trap(0, 0, 100);
            trap[i].sprite = new Sprite(trapTexture);
        }

        int startX = 100;
        int startY = 100;
        for (int i = 0; i < NUMBER_OF_ENEMY; i++) {
            enemy[i] = new Enemy(startX, startY, 5, 0 ,0);
            enemy[i].sprite = new Sprite(enemyTexture);
            enemy[i].sprite.setPosition(enemy[i].getPositionX(), enemy[i].getPositionY() );
            startX += 50;
        }

        base = new Base(0, 0, 100);
        base.sprite = new Sprite(baseLevel0Texture);
        base.setPositionX(Gdx.graphics.getWidth() / 2 - base.sprite.getWidth() / 2);
        base.setPositionY(Gdx.graphics.getHeight() / 2 - base.sprite.getWidth() / 2);
        base.sprite.setPosition(base.getPositionX(), base.getPositionY() );
        base.rotate(45);

        for (int i = 0; i < NUMBER_OF_TOWER; i++) {
            turretTower[i] = new TurretTower(64f, 0f, 25f);
            turretBase[i] = new TurretBase(64f, 0f);
            turretTower[i].sprite = new Sprite(turretTowerTexture);
            turretBase[i].sprite = new Sprite(turretBaseTexture);
            turretTower[i].sprite.setPosition(turretTower[i].getPositionX(), turretTower[i].getPositionY() );
            turretBase[i].sprite.setPosition(turretBase[i].getPositionX(), turretBase[i].getPositionY() );
            turretTower[i].sprite.rotate(-90);
        }

        for (int i = 0; i < NUMBER_OF_ENEMY; i++) {
            destinationX[i] = (float)Math.random() * 730;
            destinationY[i] = (float)Math.random() * 530;
        }

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(64/255f, 128/255f, 35/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

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
        base.rotate(1);

        for (int i = 0; i < NUMBER_OF_TRAP; i++) {
            trap[i].sprite.draw(batch);
        }

        for (int i = 0; i < NUMBER_OF_TOWER; i++) {
            turretBase[i].sprite.draw(batch);
            turretTower[i].sprite.draw(batch);
            turretTower[i].rotate(5);
        }

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            if (isTrap) {
                isTrap = false;
            } else {
                isTrap = true;
            }
        }
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
        //Debug
        //System.out.println(actualTrap);
        if (isTrap) {
            if (actualTrap < NUMBER_OF_TRAP - 1) {
                actualTrap++;
            } else {
                actualTrap = 0;
            }
        } else {
            if (actualTower < NUMBER_OF_TOWER - 1) {
                actualTower++;
            } else {
                actualTrap = 0;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isTrap) {
            trap[actualTrap].sprite.setPosition(screenX, Gdx.graphics.getHeight() - screenY);
        } else {
            turretBase[actualTower].sprite.setPosition(screenX, Gdx.graphics.getHeight() - screenY);
            turretTower[actualTower].sprite.setPosition(screenX, Gdx.graphics.getHeight() - screenY);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}