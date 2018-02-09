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
import hu.pericles.kakaopor.OccupancyGrid;
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
    private Texture[] enemyLevelTexture = new Texture[MAX_LEVEL];
    /*UI buttons*/
    /*Button Textures*/
    private Texture buttonNewTurretTexture;
    private Texture buttonNewTrapTexture;
    private Texture buttonNewWallTexture;
    private Texture buttonUpgradeBaseTexture;
    private Texture buttonUpgradeTurretTexture;
    private Texture buttonUpgradeTrapTexture;
    private Texture buttonUpgradeWallTexture;
    /*Button Sprites*/
    private Sprite buttonNewTurret;
    private Sprite buttonNewTrap;
    private Sprite buttonNewWall;
    private Sprite buttonUpgradeBase;
    private Sprite buttonUpgradeTurret;
    private Sprite buttonUpgradeTrap;
    private Sprite buttonUpgradeWall;

    /*FONT*/
    private BitmapFont font;

    /*GAME CONSTANTS*/
    /*Maximum level of an Entity*/
    public static  final int MAX_LEVEL = 6;
    /*Maximum number of */
    private static final int NUMBER_OF_ENEMY = 10;
    private static final int NUMBER_OF_TRAP = 20;
    private static final int NUMBER_OF_TOWER = 20;
    private static final int NUMBER_OF_WALL = 20;

    private static final int SIZE_TILE = 64;
    private OccupancyGrid asd = new OccupancyGrid(8, 6, 16, 12);

    private Enemy[] enemy = new Enemy[NUMBER_OF_ENEMY];
    private Trap[] trap = new Trap[NUMBER_OF_TRAP];
    private TurretTower[] turretTower = new TurretTower[NUMBER_OF_TOWER];
    private TurretBase[] turretBase = new TurretBase[NUMBER_OF_TOWER];
    private Wall[] wall = new Wall[NUMBER_OF_WALL];
    private Base base;

    /*GLOBAL VARIABLES*/
    private int actualTrap = 0;
    private int actualTurret = 0;
    private int actualWall = 0;
    private String selectedType = "Trap";

    private static int gold = 5000;
    private static int experiencePoint = 0;
    private static int increaseGold = 100;

    PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        batch = new SpriteBatch();
        gameStateManager = new GameStateManager();
        gameStateManager.push(new MenuState(gameStateManager) );

        /*LOAD TEXTURES*/
        for (int i = 0; i < MAX_LEVEL; i++) {
            /*Load Texture of Level i Base*/
            baseLevelTexture[i] = new Texture(Gdx.files.internal("base/base_level" + i + ".png") );
            /*Load Texture of Level i Trap*/
            trapLevelTexture[i] = new Texture(Gdx.files.internal("trap/trap_level" + i + ".png") );
            /*Load Texture of Level i TurretTower*/
            turretTowerLevelTexture[i] = new Texture(Gdx.files.internal("turret/turret_tower_level" + i + ".png") );
            /*Load Texture of Level i TurretBase*/
            turretBaseLevelTexture[i] = new Texture(Gdx.files.internal("turret/turret_base_level" + i  + ".png") );
            /*Load Texture of Level i Wall*/
            wallLevelTexture[i] = new Texture(Gdx.files.internal("wall/wall_level" + i + ".png") );
            /*Load Texture of Level i Enemy*/
            enemyLevelTexture[i] = new Texture(Gdx.files.internal("enemy/enemy_level" + i + ".png") );
        }
        /*LOAD UI*/
        /*Button Textures*/
        buttonNewTurretTexture = new Texture(Gdx.files.internal("ui/button_new_turret.png") );
        buttonNewTrapTexture = new Texture(Gdx.files.internal("ui/button_new_trap.png") );
        buttonNewWallTexture = new Texture(Gdx.files.internal("ui/button_new_wall.png") );
        buttonUpgradeBaseTexture = new Texture(Gdx.files.internal("ui/button_upgrade_base.png") );
        buttonUpgradeTurretTexture = new Texture(Gdx.files.internal("ui/button_upgrade_turret.png") );
        buttonUpgradeTrapTexture = new Texture(Gdx.files.internal("ui/button_upgrade_trap.png") );
        buttonUpgradeWallTexture = new Texture(Gdx.files.internal("ui/button_upgrade_wall.png") );
        /*Button Sprites*/
        buttonNewTurret = new Sprite(buttonNewTurretTexture);
        buttonNewTurret.setPosition(0, Gdx.graphics.getHeight() );
        buttonNewTrap = new Sprite(buttonNewTrapTexture);
        buttonNewTrap.setPosition(0, Gdx.graphics.getHeight() - SIZE_TILE);
        buttonNewWall = new Sprite(buttonNewWallTexture);
        buttonNewWall.setPosition(0, Gdx.graphics.getHeight() - SIZE_TILE * 2);
        buttonUpgradeBase = new Sprite(buttonUpgradeBaseTexture);
        buttonUpgradeBase.setPosition(0, Gdx.graphics.getHeight() - SIZE_TILE * 3);
        buttonUpgradeTurret = new Sprite(buttonUpgradeTrapTexture);
        buttonUpgradeTurret.setPosition(0, Gdx.graphics.getHeight() - SIZE_TILE * 4);
        buttonUpgradeTrap = new Sprite(buttonUpgradeTurretTexture);
        buttonUpgradeTrap.setPosition(0, Gdx.graphics.getHeight() - SIZE_TILE * 5);
        buttonUpgradeWall = new Sprite(buttonUpgradeWallTexture);
        buttonUpgradeWall.setPosition(0, Gdx.graphics.getHeight() - SIZE_TILE * 6);

        /*Font*/
        font = new BitmapFont(Gdx.files.internal("font/pericles_roboto.fnt"), false);

        for (int i = 0; i < NUMBER_OF_TRAP; i++) {
            trap[i] = new Trap(0, 0, 100);
            trap[i].sprite = new Sprite(trapLevelTexture[0]);
        }

        for (int i = 0; i < NUMBER_OF_ENEMY; i++) {
            enemy[i] = new Enemy(SIZE_TILE, 0, 5, 1 ,10);
            enemy[i].sprite = new Sprite(enemyLevelTexture[0]);
            enemy[i].sprite.setPosition(enemy[i].getPositionX(), enemy[i].getPositionY() );
        }

        base = new Base(0, 0, 100);
        base.sprite = new Sprite(baseLevelTexture[0]);
        base.setPosition(SIZE_TILE * 8, SIZE_TILE * 7);
        asd.grid[7][6].isFilled = true;
        asd.grid[7][7].isFilled = true;
        asd.grid[8][5].isFilled = true;
        asd.grid[8][6].isFilled = true;
        asd.grid[8][7].isFilled = true;
        asd.grid[8][8].isFilled = true;
        asd.grid[9][5].isFilled = true;
        asd.grid[9][6].isFilled = true;
        asd.grid[9][7].isFilled = true;
        asd.grid[9][8].isFilled = true;
        asd.grid[10][6].isFilled = true;
        asd.grid[10][7].isFilled = true;

        base.sprite.setPosition(SIZE_TILE * 8, SIZE_TILE * 7 );

        for (int i = 0; i < NUMBER_OF_TOWER; i++) {
            turretTower[i] = new TurretTower(0, 0, 25f);
            turretBase[i] = new TurretBase(0, 0);
            turretTower[i].sprite = new Sprite(turretTowerLevelTexture[0]);
            turretBase[i].sprite = new Sprite(turretBaseLevelTexture[0]);
            turretTower[i].sprite.setPosition(turretTower[i].getPositionX(), turretTower[i].getPositionY() );
            turretBase[i].sprite.setPosition(turretBase[i].getPositionX(), turretBase[i].getPositionY() );
            turretTower[i].sprite.rotate(-90);
        }

        for (int i = 0; i < NUMBER_OF_WALL; i++) {
            wall[i] = new Wall(0, 0, 200);
            wall[i].sprite = new Sprite(wallLevelTexture[0]);
            wall[i].sprite.setPosition(wall[i].getPositionX(), wall[i].getPositionY() );
        }

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void update(float timeDelta) {}

    @Override
    public void render(SpriteBatch batch) {

        asd.destinationDetermination();
        batch.begin();

        /*DRAW UI*/
        /*Draw Buttons*/
        buttonNewTurret.draw(batch);
        buttonNewTrap.draw(batch);
        buttonNewWall.draw(batch);
        buttonUpgradeBase.draw(batch);
        buttonUpgradeTurret.draw(batch);
        buttonUpgradeTrap.draw(batch);
        buttonUpgradeWall.draw(batch);
        /*Draw Texts*/
        font.setColor(0,0,0,1);
        font.draw(batch, "Gold: " + gold, SIZE_TILE, Gdx.graphics.getHeight() );
        font.draw(batch, "XP: " + experiencePoint, SIZE_TILE, Gdx.graphics.getHeight() - SIZE_TILE);
        font.draw(batch, "HP: " + Base.getHealthPoint(), SIZE_TILE, Gdx.graphics.getHeight() - SIZE_TILE * 2);
        font.draw(batch, "Base level: " + Base.getLevel(), SIZE_TILE, Gdx.graphics.getHeight() - SIZE_TILE * 3);

        gold += increaseGold;

        for (int i = 0; i < NUMBER_OF_ENEMY; i++) {
            int tileX = (int)Math.floor(enemy[i].getPositionX() / SIZE_TILE);
            int tileY = (int)Math.floor(enemy[i].getPositionY() / SIZE_TILE);

            if (enemy[i].isAlive() ) {
                enemy[i].sprite.draw(batch);
                if (enemy[i].Move(tileX, tileY, 8, 7) ) {
                    enemy[i].move(SIZE_TILE * 8, SIZE_TILE * 7);
                } else {
                    Base.setHealthPoint(Base.getHealthPoint() - enemy[i].getHealth());
                    enemy[i].kill();
                }
            }
        }

/*if (Base.getHealthPoint() <= 0) {
 Base.kill();*/

        if (Base.isAlive() ) {
            base.rotator();
            base.sprite.draw(batch);
        } else {
            font.setColor(1, 0, 0 ,1);
            font.draw(batch, "Your Base Has Been Destroyed", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            float delay = 1; // seconds

            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    gameStateManager.set(new MenuState(gameStateManager) );
                }
            }, delay);
        }

        for (int i = 0; i < NUMBER_OF_TRAP; i++) {
            if (trap[i].getPositionX() != 0) {
                trap[i].sprite.draw(batch);
            }
        }

        for (int i = 0; i < NUMBER_OF_TOWER; i++) {
            if (turretBase[i].getPositionX() != 0) {
                turretBase[i].sprite.draw(batch);
                turretTower[i].sprite.draw(batch);
                turretTower[i].rotate(3);
            }
        }

        for (int i = 0; i < NUMBER_OF_WALL; i++) {
            if (wall[i].getPositionX() != 0) {
                wall[i].sprite.draw(batch);
            }
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();

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
            /*Dispose Texture of Level i Enemy*/
            enemyLevelTexture[i].dispose();
        }
        /*Dispose UI Buttons*/
        buttonNewTurretTexture.dispose();
        buttonNewTrapTexture.dispose();
        buttonNewWallTexture.dispose();
        buttonUpgradeBaseTexture.dispose();
        buttonUpgradeTurretTexture.dispose();
        buttonUpgradeTrapTexture.dispose();
        buttonUpgradeWallTexture.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {
        // if (keycode == Input:keys.E) <-- E for E key
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
        int y_parsed = Gdx.graphics.getHeight() - screenY;

        /*UI Buttons*/
        if (screenX <= SIZE_TILE) {
            if (y_parsed >= Gdx.graphics.getHeight() - SIZE_TILE ) {
                selectedType = "Turret";
            } else if (y_parsed >= Gdx.graphics.getHeight() - SIZE_TILE * 2 ) {
                selectedType = "Trap";
            } else if (y_parsed >= Gdx.graphics.getHeight() - SIZE_TILE * 3) {
                selectedType = "Wall";
            } else if (y_parsed >= Gdx.graphics.getHeight() - SIZE_TILE * 4) {
                if (Base.getLevel() < MAX_LEVEL - 1 && gold >= Base.getPriceUpgrade(Base.getLevel() ) ) {
                    gold -= Base.getPriceUpgrade(Base.getLevel() );
                    Base.upLevel();
                    base.sprite = new Sprite(baseLevelTexture[Base.getLevel()]);
                    base.setPosition(SIZE_TILE * 8, SIZE_TILE * 7);
                    base.sprite.setPosition(base.getPositionX(), base.getPositionY() );
                    increaseGold += Base.getLevel();
                    Base.setHealthPoint(Base.getHealthPoint() * Base.getLevel());
                }
            } else if (y_parsed >= Gdx.graphics.getHeight() - SIZE_TILE * 5) {
                if (TurretBase.getLevel() < MAX_LEVEL - 1 && gold >= TurretBase.getPriceUpgrade(TurretBase.getLevel() ) ) {
                    gold -= TurretBase.getPriceUpgrade(TurretBase.getLevel());
                    TurretBase.upLevel();
                    for (int i = 0; i < NUMBER_OF_TOWER; i++) {
                        turretTower[i].sprite = new Sprite(turretTowerLevelTexture[TurretBase.getLevel()]);
                        turretBase[i].sprite = new Sprite(turretBaseLevelTexture[TurretBase.getLevel()]);
                        turretTower[i].sprite.setPosition(turretTower[i].getPositionX(), turretTower[i].getPositionY());
                        turretBase[i].sprite.setPosition(turretBase[i].getPositionX(), turretBase[i].getPositionY());
                        turretTower[i].sprite.rotate(-90);
                    }
                }
            } else if (y_parsed >= Gdx.graphics.getHeight() - SIZE_TILE * 6) {
                if (Trap.getLevel() <  MAX_LEVEL - 1 && gold >= Trap.getPriceUpgrade(Trap.getLevel() ) ) {
                    gold -= Trap.getPriceUpgrade(Trap.getLevel() );
                    Trap.upLevel();
                    for (int i = 0; i < NUMBER_OF_TRAP; i++) {
                        trap[i].sprite = new Sprite(trapLevelTexture[Trap.getLevel()]);
                        trap[i].sprite.setPosition(trap[i].getPositionX(), trap[i].getPositionY() );
                    }
                }
            } else if (y_parsed >= Gdx.graphics.getHeight() - SIZE_TILE * 7) {
                if (Wall.getLevel() < MAX_LEVEL - 1 && gold >= Wall.getPriceUpgrade(Wall.getLevel() ) ) {
                    gold -= Wall.getPriceUpgrade(Wall.getLevel() );
                    Wall.upLevel();
                    for (int i = 0; i < NUMBER_OF_WALL; i++) {
                        wall[i].sprite = new Sprite(wallLevelTexture[Wall.getLevel()]);
                        wall[i].sprite.setPosition(wall[i].getPositionX(), wall[i].getPositionY());
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int x_parsed = (int)Math.floor(screenX / SIZE_TILE);
        int y_parsed_inverse = (int)Math.floor( (Gdx.graphics.getHeight() - screenY) / SIZE_TILE);

        if (!asd.grid[x_parsed][y_parsed_inverse].isFilled) {
            if (selectedType.equals("Trap") && gold >= Trap.getPRICE() && actualTrap < NUMBER_OF_TRAP - 1) {
                asd.grid[x_parsed][y_parsed_inverse].isFilled = true;
                gold -= Trap.getPRICE();
                actualTrap++;
            } else if (selectedType.equals("Turret") && gold >= TurretBase.getPRICE() && actualTurret < NUMBER_OF_TOWER - 1) {
                asd.grid[x_parsed][y_parsed_inverse].isFilled = true;
                gold -= TurretBase.getPRICE();
                actualTurret++;
            } else if (selectedType.equals("Wall") && gold >= Wall.getPRICE() && actualWall < NUMBER_OF_WALL - 1) {
                asd.grid[x_parsed][y_parsed_inverse].isFilled = true;
                gold -= Wall.getPRICE();
                actualWall++;
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
        int x_parsed = (int)Math.floor(screenX / SIZE_TILE) * SIZE_TILE;
        int y_parsed_inverse = Gdx.graphics.getHeight() - (int)Math.floor(screenY / SIZE_TILE) * SIZE_TILE;

        if (selectedType.equals("Trap") ) {
            trap[actualTrap].setPosition(x_parsed, y_parsed_inverse);
            trap[actualTrap].sprite.setPosition(trap[actualTrap].getPositionX(), trap[actualTrap].getPositionY() );
        } else if (selectedType.equals("Turret") ) {
            turretBase[actualTurret].setPosition(x_parsed, y_parsed_inverse);
            turretTower[actualTurret].setPosition(x_parsed, y_parsed_inverse);
            turretBase[actualTurret].sprite.setPosition(turretBase[actualTurret].getPositionX(), turretBase[actualTurret].getPositionY() );
            turretTower[actualTurret].sprite.setPosition(turretTower[actualTurret].getPositionX(), turretTower[actualTurret].getPositionY() );
        } else if (selectedType.equals("Wall") ) {
            wall[actualWall].setPosition(x_parsed, y_parsed_inverse);
            wall[actualWall].sprite.setPosition(wall[actualWall].getPositionX(), wall[actualWall].getPositionY() );
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
