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
    private Texture[] turretTowerLevelTexture = new Texture[6];
    private Texture[] turretBaseLevelTexture = new Texture[6];
    private Texture[] trapLevelTexture = new Texture[6];
    private Texture enemyTexture;
    /*FONT*/
    private BitmapFont font;

    /*GAME CONSTANTS*/
    private static final int NUMBER_OF_ENEMY = 10;
    private static final int NUMBER_OF_TRAP = 15;
    private static final int NUMBER_OF_TOWER = 15;
    private static final int PRICE_TRAP = 500;
    private static final int PRICE_TOWER = 1750;
    private static final int PRICE_UPGRADE_BASE = 10000;
    private static final int PRICE_UPGRADE_TRAP = 500;
    private static final int PRICE_UPGRADE_TOWER = 1000;
    private static final int SIZE_TILE = 64;

    private Enemy[] enemy = new Enemy[NUMBER_OF_ENEMY];
    private Trap[] trap = new Trap[NUMBER_OF_TRAP];
    private TurretTower[] turretTower = new TurretTower[NUMBER_OF_TOWER];
    private TurretBase[] turretBase = new TurretBase[NUMBER_OF_TOWER];
    private Base base;

    /*Global Game Variables*/
    private int actualTrap = 0;
    private int actualTower = 0;
    private boolean isTrap = true;
    private static String not_enough_gold_warning = "";

    private static int gold = 100000;
    private static int experiencePoint = 0;
    private static int increaserGold = 5;

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
        for (int i = 0; i < 6; i++) {
            trapLevelTexture[i] = new Texture(Gdx.files.internal("trap/trap_level" + i + ".png") );
        }

        /*TurretTower textures*/
        for (int i = 0; i < 6; i++) {
            turretTowerLevelTexture[i] = new Texture(Gdx.files.internal("turret/turret_tower_level" + i + ".png") );
        }

        /*TurretBase textures*/
        for (int i = 0; i < 6; i++) {
            turretBaseLevelTexture[i] = new Texture(Gdx.files.internal("turret/turret_base_level" + i  + ".png") );
        }

        /*Font*/
        font = new BitmapFont(Gdx.files.internal("font/pericles_roboto.fnt"), false);

        for (int i = 0; i < NUMBER_OF_TRAP; i++) {
            trap[i] = new Trap(0, Gdx.graphics.getHeight() - 320, 100);
            trap[i].sprite = new Sprite(trapLevelTexture[0]);
        }

        int startX = 100;
        int startY = 100;
        for (int i = 0; i < NUMBER_OF_ENEMY; i++) {
            enemy[i] = new Enemy(startX, startY, 5, 5 ,10);
            enemy[i].sprite = new Sprite(enemyTexture);
            enemy[i].sprite.setPosition(enemy[i].getPositionX(), enemy[i].getPositionY() );
            startX += 50;
        }

        base = new Base(0, 0, 100);
        base.sprite = new Sprite(baseLevelTexture[0]);
        base.setPosition(
                Gdx.graphics.getWidth() / 2 - base.sprite.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - base.sprite.getWidth() / 2
        );
        base.sprite.setPosition(base.getPositionX(), base.getPositionY() );

        for (int i = 0; i < NUMBER_OF_TOWER; i++) {
            turretTower[i] = new TurretTower(64f, 0f, 25f);
            turretBase[i] = new TurretBase(64f, 0f);
            turretTower[i].sprite = new Sprite(turretTowerLevelTexture[0]);
            turretBase[i].sprite = new Sprite(turretBaseLevelTexture[0]);
            turretTower[i].sprite.setPosition(turretTower[i].getPositionX(), turretTower[i].getPositionY() );
            turretBase[i].sprite.setPosition(turretBase[i].getPositionX(), turretBase[i].getPositionY() );
            turretTower[i].sprite.rotate(-90);
        }

        for (int i = 0; i < NUMBER_OF_ENEMY; i++) {
            destinationX[i] = Gdx.graphics.getWidth() / 2 - base.sprite.getWidth() / 2;
            destinationY[i] = Gdx.graphics.getHeight() / 2 - base.sprite.getWidth() / 2;
        }

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(64/255f, 128/255f, 35/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        font.setColor(1,1,1,1);
        font.draw(batch, "Gold: " + gold, 0, Gdx.graphics.getHeight() - 64 );
        font.draw(batch, "XP: " + experiencePoint, 0, Gdx.graphics.getHeight() - 128);
        font.draw(batch, "HP: " + base.getHealthPoint(), 0, Gdx.graphics.getHeight() - 192);
        font.draw(batch, "Base level: " + base.getLevel(), 0, Gdx.graphics.getHeight() - 256 );
        font.draw(batch, not_enough_gold_warning, 0, Gdx.graphics.getHeight() - 384);
        //increase gold
        gold += increaserGold;

        for (int i = 0; i < NUMBER_OF_ENEMY; i++) {
            if (enemy[i].isAlive() ) {
                enemy[i].sprite.draw(batch);
            }
        }

        for (int i = 0; i < NUMBER_OF_ENEMY; i++) {
            if (!(enemy[i].getPositionX() <= destinationX[i]+8 &&
                enemy[i].getPositionX() >= destinationX[i]-8  &&
                enemy[i].getPositionY() <= destinationY[i]+8 &&
                enemy[i].getPositionY() >= destinationY[i]-8)) {
                    enemy[i].move(destinationX[i], destinationY[i]);
            } else if (enemy[i].getPositionX() <= destinationX[i]+8 &&
                    enemy[i].getPositionX() >= destinationX[i]-8 &&
                    enemy[i].getPositionY() <= destinationY[i]+8 &&
                    enemy[i].getPositionY() >= destinationY[i]-8 &&
                    enemy[i].isAlive() ) {
                Base.setHealthPoint( (Base.getHealthPoint() - enemy[i].getHealth() )  );
                enemy[i].kill();
                if (Base.getHealthPoint() <= 0) {
                    Base.kill();
                }
            } else {
                destinationX[i] = -10;
                destinationY[i] = -10;
            }
        }

        base.rotator();

        if (Base.isAlive() ) {
            base.sprite.draw(batch);
        } else {
            font.setColor(1, 0, 0 ,1);
            font.draw(batch, "YOUR BASE HAS BEEN DESTROYED", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        }

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

        /*Dispose Trap textures by level*/
        for (int i = 0; i < 6; i++) {
            trapLevelTexture[i].dispose();
        }

        /*Dispose TurretTower textures by level*/
        for (int i = 0; i < 6; i++) {
            turretTowerLevelTexture[i].dispose();
        }

        /*Dispose TurretBase textures by level*/
        for (int i = 0; i < 6; i++) {
            turretBaseLevelTexture[i].dispose();
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            if (isTrap) {
                isTrap = false;
            } else {
                isTrap = true;
            }
        } else if (keycode == Input.Keys.Q && Base.getLevel() < 5) {
            if (gold >= PRICE_UPGRADE_BASE * Base.getLevel() ) {
                gold -= PRICE_UPGRADE_BASE * Base.getLevel();
                Base.upLevel();
                base.sprite = new Sprite(baseLevelTexture[Base.getLevel()]);
                base.setPosition(
                        Gdx.graphics.getWidth() / 2 - base.sprite.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 - base.sprite.getWidth() / 2
                );
                base.sprite.setPosition(base.getPositionX(), base.getPositionY());
                increaserGold += base.getLevel();
                Base.setHealthPoint(Base.getHealthPoint() * Base.getLevel());
                not_enough_gold_warning = "";
            } else {
                font.setColor(1, 0, 0, 1);
                not_enough_gold_warning = "You have not enough gold (" + (PRICE_UPGRADE_BASE * base.getLevel() ) + ")";
            }
        } else if (keycode == Input.Keys.W && TurretBase.getLevel() < 5) {
            if (gold >= PRICE_UPGRADE_TOWER * actualTower * TurretBase.getLevel() ) {
                gold -= PRICE_UPGRADE_TOWER * TurretBase.getLevel();
                TurretBase.upLevel();
                for (int i = 0; i < NUMBER_OF_TOWER; i++) {
                            turretTower[i].sprite = new Sprite(turretTowerLevelTexture[TurretBase.getLevel()]);
                            turretBase[i].sprite = new Sprite(turretBaseLevelTexture[TurretBase.getLevel()]);
                            turretTower[i].sprite.setPosition(turretTower[i].getPositionX(), turretTower[i].getPositionY() );
                            turretBase[i].sprite.setPosition(turretBase[i].getPositionX(), turretBase[i].getPositionY() );
                            turretTower[i].sprite.rotate(-90);
                        }
            } else {
                font.setColor(1, 0, 0, 1);
                not_enough_gold_warning = "You have not enough gold";
            }
        } else if (keycode == Input.Keys.R && Trap.getLevel() < 5) {
            if (gold >= PRICE_UPGRADE_TRAP * actualTrap * Trap.getLevel() ) {
                gold -= PRICE_UPGRADE_TRAP * actualTrap * Trap.getLevel();
                Trap.upLevel();
                for (int i = 0; i < NUMBER_OF_TRAP; i++) {
                    trap[i].sprite = new Sprite(trapLevelTexture[Trap.getLevel()]);
                    trap[i].sprite.setPosition(trap[i].getPositionX(), trap[i].getPositionY() );
                }
            } else {
                not_enough_gold_warning = "You have not enough gold";
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
        if (isTrap) {
            if (gold >= (PRICE_TRAP * base.getLevel() + PRICE_TRAP) ) {
                not_enough_gold_warning = "";
                if (actualTrap < NUMBER_OF_TRAP - 1) {
                    actualTrap++;
                    gold -= (PRICE_TRAP * base.getLevel() + PRICE_TRAP);
                }
            } else {
                not_enough_gold_warning = "You have not enough gold (" + (PRICE_TRAP * base.getLevel() + PRICE_TRAP) + ")";
            }
        } else {
            if (gold >= (PRICE_TOWER * Base.getLevel() + PRICE_TOWER ) ) {
                not_enough_gold_warning = "";
                if (actualTower < NUMBER_OF_TOWER - 1) {
                    actualTower++;
                    gold -= (PRICE_TOWER * Base.getLevel() + PRICE_TOWER);
                }
            } else {
                not_enough_gold_warning = "You have not enough gold (" + (PRICE_TOWER * base.getLevel() + PRICE_TOWER) + ")";
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        int x_parsed = (int)Math.floor(screenX / SIZE_TILE) * SIZE_TILE;
        int y_parsed = (int)Math.floor(screenY / SIZE_TILE) * SIZE_TILE;
        if (Math.floor(screenX / SIZE_TILE) != Math.floor(screenY / SIZE_TILE) ) {
            if (isTrap && actualTrap < NUMBER_OF_TRAP - 1) {
                trap[actualTrap].setPosition(x_parsed, Gdx.graphics.getHeight() - y_parsed);
                trap[actualTrap].sprite.setPosition(x_parsed, Gdx.graphics.getHeight() - y_parsed);
            } else if (actualTower < NUMBER_OF_TOWER - 1) {
                turretBase[actualTower].setPosition(x_parsed, Gdx.graphics.getHeight() -  y_parsed);
                turretTower[actualTower].setPosition(x_parsed, Gdx.graphics.getHeight() - y_parsed);
                turretBase[actualTower].sprite.setPosition(x_parsed, Gdx.graphics.getHeight() - y_parsed);
                turretTower[actualTower].sprite.setPosition(x_parsed, Gdx.graphics.getHeight() - y_parsed);
            }
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