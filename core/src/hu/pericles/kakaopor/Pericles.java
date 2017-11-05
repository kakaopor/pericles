package hu.pericles.kakaopor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pericles extends ApplicationAdapter implements InputProcessor {
    private SpriteBatch batch;

    /*TEXTURES*/
    private Texture[] baseLevelTexture = new Texture[6];
    private Texture enemyTexture, trapTexture;
    private Texture turretTowerTexture, turretBaseTexture;
    /*FONT*/
    BitmapFont font;

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

    private int gold = 1000;
    private int experiencePoint = 0;
    protected int increaserGold = 5;

    protected boolean baseRotate = true;
    protected int baseRotateCounter = 0;

    private float[] destinationX = new float[NUMBER_OF_ENEMY];
    private float[] destinationY = new float[NUMBER_OF_ENEMY];

    @Override
    public void create () {
        batch = new SpriteBatch();

        /*TEXTURES*/
        /*Enemy textures*/
        enemyTexture = new Texture(Gdx.files.internal("enemy/enemy.png") );
        /*Base textures by level*/
        for (int i = 0; i < 6; i++) {
            baseLevelTexture[i] = new Texture(Gdx.files.internal("base/base_level" + i + ".png") );
        }
        /*Trap textures*/
        trapTexture = new Texture(Gdx.files.internal("trap/trap.png") );
        /*TurretTower textures*/
        turretTowerTexture = new Texture(Gdx.files.internal("turret/turret_tower.png") );
        /*TurretBase textures*/
        turretBaseTexture = new Texture(Gdx.files.internal("turret/turret.png") );

        /*Font*/
        font = new BitmapFont(Gdx.files.internal("font/pericles_roboto.fnt"), false);

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
        base.sprite = new Sprite(baseLevelTexture[0]);
        base.setPositionX(Gdx.graphics.getWidth() / 2 - base.sprite.getWidth() / 2);
        base.setPositionY(Gdx.graphics.getHeight() / 2 - base.sprite.getWidth() / 2);
        base.sprite.setPosition(base.getPositionX(), base.getPositionY() );

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

        font.draw(batch, "Gold: " + gold, Gdx.graphics.getWidth() - 250, 32);
        font.draw(batch, "XP: " + experiencePoint, Gdx.graphics.getWidth() - 350, 32);
        font.draw(batch, "HP: " + (int)base.getHealthPoint(), Gdx.graphics.getWidth() - 475, 32);
        font.draw(batch, "Base level: " + base.getLevel(), Gdx.graphics.getWidth() - 650, 32 );

        //increase gold
        gold += increaserGold;

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


        baseRotator();
        base.sprite.draw(batch);

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
        for (int i = 0; i < 6; i++) {
            baseLevelTexture[i].dispose();
        }
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
        } else if (keycode == Input.Keys.B && gold >= (10000 * base.getLevel() + 10000) && base.getLevel() < 5) {
            gold -= (10000 * base.getLevel() + 10000);
            base.upLevel();
            base.sprite = new Sprite(baseLevelTexture[base.getLevel()] );
            base.setPositionX(Gdx.graphics.getWidth() / 2 - base.sprite.getWidth() / 2);
            base.setPositionY(Gdx.graphics.getHeight() / 2 - base.sprite.getWidth() / 2);
            base.sprite.setPosition(base.getPositionX(), base.getPositionY() );
            increaserGold *= base.getLevel();
            base.setHealthPoint(base.getHealthPoint() * base.getLevel() );
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
        if (isTrap && (gold >= (2500 * base.getLevel() + 2500) ) ) {
            if (actualTrap < NUMBER_OF_TRAP - 1) {
                actualTrap++;
                gold -= (2500 * base.getLevel() + 2500);
            } else {
                actualTrap = 0;
            }
        } else if (gold >= (5000 * base.getLevel() + 5000) ) {
            if (actualTower < NUMBER_OF_TOWER - 1) {
                actualTower++;
                gold -= (5000 * base.getLevel() + 5000);
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

    /*Rotates the base 180 degress left, then right and repeat.*/
    public void baseRotator() {
        if (baseRotateCounter >= 180) {
            if (baseRotate) {
                baseRotate = false;
            } else {
                baseRotate = true;
            }
            baseRotateCounter = 0;
        }

        if (baseRotate) {
            base.rotate(1);
        } else {
            base.rotate(-1);
        }
        baseRotateCounter++;

    }

}