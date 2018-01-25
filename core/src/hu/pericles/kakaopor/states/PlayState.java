package hu.pericles.kakaopor.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

import hu.pericles.kakaopor.Base;
import hu.pericles.kakaopor.Enemy;
import hu.pericles.kakaopor.Trap;
import hu.pericles.kakaopor.TurretBase;
import hu.pericles.kakaopor.TurretTower;
import hu.pericles.kakaopor.Wall;

public class PlayState extends State implements InputProcessor {

    private SpriteBatch batch;

    /*TEXTURES*/
    private Texture[] baseLevelTexture = new Texture[MAX_LEVEL];
    private Texture[] turretTowerLevelTexture = new Texture[MAX_LEVEL];
    private Texture[] turretBaseLevelTexture = new Texture[MAX_LEVEL];
    private Texture[] trapLevelTexture = new Texture[MAX_LEVEL];
    private Texture[] wallLevelTexture = new Texture[MAX_LEVEL];
    private Texture enemyTexture;

    /*FONT*/
    private BitmapFont font;

    /*GAME CONSTANTS*/
    public static  final int MAX_LEVEL = 6;
    private static final int NUMBER_OF_ENEMY = 10;
    private static final int NUMBER_OF_TRAP = 15;
    private static final int NUMBER_OF_TOWER = 15;
    private static final int NUMBER_OF_WALL = 20;

    private static final int SIZE_TILE = 64;
    private static final int NUMBER_OF_TILE_X = Gdx.graphics.getWidth() / SIZE_TILE;
    private static final int NUMBER_OF_TILE_Y = Gdx.graphics.getHeight() / SIZE_TILE;

    private Enemy[] enemy = new Enemy[NUMBER_OF_ENEMY];
    private Trap[] trap = new Trap[NUMBER_OF_TRAP];
    private TurretTower[] turretTower = new TurretTower[NUMBER_OF_TOWER];
    private TurretBase[] turretBase = new TurretBase[NUMBER_OF_TOWER];
    private Wall[] wall = new Wall[NUMBER_OF_WALL];
    private Base base;

    /*Global Game Variables*/
    private int actualTrap = 0;
    private int actualTower = 0;
    private int actualWall = 0;
    private String selectedType = "Trap";

    private static String not_enough_gold_warning = "";
    private int[][] occupancyGrid = new int[NUMBER_OF_TILE_X][NUMBER_OF_TILE_Y];

    private static int gold = 100000;
    private static int experiencePoint = 0;
    private static int increaseGold = 5000;

    private float[] destinationX = new float[NUMBER_OF_ENEMY];
    private float[] destinationY = new float[NUMBER_OF_ENEMY];

    PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        batch = new SpriteBatch();
        gameStateManager = new GameStateManager();
        gameStateManager.push(new MenuState(gameStateManager) );
        //cam.setToOrtho(false, Gdx.graphics.getWidth() / 2 - base.sprite.getWidth() / 2, Gdx.graphics.getHeight() / 2 - base.sprite.getHeight() / 2);

        /*TEXTURES*/

        /*Enemy textures*/
        enemyTexture = new Texture(Gdx.files.internal("enemy/enemy.png") );

        for (int i = 0; i < MAX_LEVEL; i++) {
            /*Texture of Level i Base*/
            baseLevelTexture[i] = new Texture(Gdx.files.internal("base/base_level" + i + ".png") );
            /*Texture of Level i Trap*/
            trapLevelTexture[i] = new Texture(Gdx.files.internal("trap/trap_level" + i + ".png") );
            /*Texture of Level i TurretTower*/
            turretTowerLevelTexture[i] = new Texture(Gdx.files.internal("turret/turret_tower_level" + i + ".png") );
            /*Texture of Level i TurretBase*/
            turretBaseLevelTexture[i] = new Texture(Gdx.files.internal("turret/turret_base_level" + i  + ".png") );
            /*Texture of Level i Wall*/
            wallLevelTexture[i] = new Texture(Gdx.files.internal("wall/wall_level" + i + ".png") );
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
            enemy[i] = new Enemy(startX, startY, 5, 1 ,10);
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

        for (int i = 0; i < NUMBER_OF_WALL; i++) {
            wall[i] = new Wall(128f, 0f, 200f, 0);
            wall[i].sprite = new Sprite(wallLevelTexture[0]);
            wall[i].sprite.setPosition(wall[i].getPositionX(), wall[i].getPositionY() );
        }

        for (int i = 0; i < NUMBER_OF_ENEMY; i++) {
            destinationX[i] = Gdx.graphics.getWidth() / 2 - base.sprite.getWidth() / 2;
            destinationY[i] = Gdx.graphics.getHeight() / 2 - base.sprite.getWidth() / 2;
        }

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void update(float timeDelta) {

    }

    @Override
    public void render(SpriteBatch batch) {

        batch.begin();  

        font.setColor(1,1,1,1);
        font.draw(batch, "Gold: " + gold, 0, Gdx.graphics.getHeight() - 64 );
        font.draw(batch, "XP: " + experiencePoint, 0, Gdx.graphics.getHeight() - 128);
        font.draw(batch, "HP: " + base.getHealthPoint(), 0, Gdx.graphics.getHeight() - 192);
        font.draw(batch, "Base level: " + base.getLevel(), 0, Gdx.graphics.getHeight() - 256 );
        font.draw(batch, not_enough_gold_warning, 0, Gdx.graphics.getHeight() - 384);

        gold += increaseGold;

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
            float delay = 1; // seconds

            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    gameStateManager.set(new MenuState(gameStateManager) );
                }
            }, delay);
        }

        for (int i = 0; i < NUMBER_OF_TRAP; i++) {
            trap[i].sprite.draw(batch);
        }

        for (int i = 0; i < NUMBER_OF_TOWER; i++) {
            turretBase[i].sprite.draw(batch);
            turretTower[i].sprite.draw(batch);
            turretTower[i].rotate(5);
        }

        for (int i = 0; i < NUMBER_OF_WALL; i++) {
            wall[i].sprite.draw(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {

        batch.dispose();

        /*Dispose Enemy texture*/
        enemyTexture.dispose();

        for (int i = 0; i < MAX_LEVEL; i++) {
            /*Dispose Texture of Level i Base*/
            baseLevelTexture[i].dispose();
            /*Dispose Texture of Level i Trap*/
            trapLevelTexture[i].dispose();
            /*Dispose Texture of Level i TurretTower*/
            turretTowerLevelTexture[i].dispose();
            /*Dispose Texture of Level i TurretBase*/
            turretBaseLevelTexture[i].dispose();
            /*Dispose Texture of Level i Wall*/
            wallLevelTexture[i].dispose();
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            if (selectedType == "Trap") {
                selectedType = "Turret";
            } else if (selectedType == "Turret") {
                selectedType = "Wall";
            } else {
                selectedType = "Trap";
            }
        } else if (keycode == Input.Keys.Q && Base.getLevel() < 5) {
            if (gold >= Base.getPriceUpgrade(Base.getLevel() ) ) {
                gold -= Base.getPriceUpgrade(Base.getLevel() );
                Base.upLevel();
                base.sprite = new Sprite(baseLevelTexture[Base.getLevel()]);
                base.setPosition(
                        Gdx.graphics.getWidth() / 2 - base.sprite.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 - base.sprite.getWidth() / 2
                );
                base.sprite.setPosition(base.getPositionX(), base.getPositionY());
                increaseGold += base.getLevel();
                Base.setHealthPoint(Base.getHealthPoint() * Base.getLevel());
                not_enough_gold_warning = "";
            } else {
                font.setColor(1, 0, 0, 1);
                not_enough_gold_warning = "You have not enough gold (" + (Base.getPriceUpgrade(Base.getLevel() )  ) + ")";
            }
        } else if (keycode == Input.Keys.W && TurretBase.getLevel() < 5) {
            if (gold >= TurretBase.getPriceUpgrade(TurretBase.getLevel() ) ) {
                gold -= TurretBase.getPriceUpgrade(TurretBase.getLevel() );
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
            if (gold >= Trap.getPriceUpgrade(Trap.getLevel() ) ) {
                gold -= Trap.getPriceUpgrade(Trap.getLevel() );
                Trap.upLevel();
                for (int i = 0; i < NUMBER_OF_TRAP; i++) {
                    trap[i].sprite = new Sprite(trapLevelTexture[Trap.getLevel()]);
                    trap[i].sprite.setPosition(trap[i].getPositionX(), trap[i].getPositionY() );
                }
            } else {
                not_enough_gold_warning = "You have not enough gold";
            }
        } else if (keycode == Input.Keys.E && Wall.getLevel() < 5) {
            if (gold >= Wall.getPriceUpgrade(Wall.getLevel() ) ) {
                gold -= Wall.getPriceUpgrade(Wall.getLevel());
                Wall.upLevel();
                for (int i = 0; i < NUMBER_OF_WALL; i++) {
                    wall[i].sprite = new Sprite(wallLevelTexture[Wall.getLevel()]);
                    wall[i].sprite.setPosition(wall[i].getPositionX(), wall[i].getPositionY());
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
        if (selectedType == "Trap") {
            if (gold >= Trap.getPRICE() ) {
                not_enough_gold_warning = "";
                if (actualTrap < NUMBER_OF_TRAP - 1) {
                    actualTrap++;
                    gold -= Trap.getPRICE();
                }
            } else {
                not_enough_gold_warning = "You have not enough gold (" + Trap.getPRICE() + ")";
            }
        } else if (selectedType == "Turret") {
            if (gold >= TurretBase.getPRICE() ) {
                not_enough_gold_warning = "";
                if (actualTower < NUMBER_OF_TOWER - 1) {
                    actualTower++;
                    gold -= TurretBase.getPRICE();
                }
            } else {
                not_enough_gold_warning = "You have not enough gold (" + TurretBase.getPRICE()+ ")";
            }
        } else {
            if (gold >= Wall.getPRICE() )  {
                not_enough_gold_warning = "";
                if (actualWall < NUMBER_OF_WALL - 1) {
                    actualWall++;
                    gold -= Wall.getPRICE();
                }
            } else {
                not_enough_gold_warning = "You have not enough gold (" + Wall.getPRICE() + ")";
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        int x_parsed = (int)Math.floor(screenX / SIZE_TILE) * SIZE_TILE;
        int y_parsed_inverse = Gdx.graphics.getHeight() - (int)Math.floor(screenY / SIZE_TILE) * SIZE_TILE;

        if (Math.floor(screenX / SIZE_TILE) != Math.floor(screenY / SIZE_TILE) ) {
            if (selectedType == "Trap" && actualTrap < NUMBER_OF_TRAP - 1) {
                trap[actualTrap].setPosition(x_parsed, y_parsed_inverse);
                trap[actualTrap].sprite.setPosition(trap[actualTrap].getPositionX(), trap[actualTrap].getPositionY() );
            } else if (selectedType == "Turret" && actualTower < NUMBER_OF_TOWER - 1) {
                turretBase[actualTower].setPosition(x_parsed, y_parsed_inverse);
                turretTower[actualTower].setPosition(x_parsed, y_parsed_inverse);
                turretBase[actualTower].sprite.setPosition(turretBase[actualTower].getPositionX(), turretBase[actualTower].getPositionY() );
                turretTower[actualTower].sprite.setPosition(turretTower[actualTower].getPositionX(), turretTower[actualTower].getPositionY() );
            } else {
                wall[actualWall].setPosition(x_parsed, y_parsed_inverse);
                wall[actualWall].sprite.setPosition(wall[actualWall].getPositionX(), wall[actualWall].getPositionY() );
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
