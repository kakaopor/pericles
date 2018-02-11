package hu.pericles.kakaopor.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.Iterator;

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
    /*New Entity Hologram Textures*/
    private Texture[] holoTurretLevelTexture = new Texture[MAX_LEVEL];
    private Texture[] holoTrapLevelTexture = new Texture[MAX_LEVEL];
    private Texture[] holoWallLevelTexture = new Texture[MAX_LEVEL];
    /*New Entity Hologram Sprites*/
    private Sprite holoTurret;
    private Sprite holoTrap;
    private Sprite holoWall;

    /*FONT*/
    private BitmapFont font;

    /*GAME CONSTANTS*/
    /*Maximum level of an Entity*/
    public static  final int MAX_LEVEL = 6;
    /*Size of a Tile of the OccupancyGrid*/
    private static final int SIZE_TILE = 32;

    private OccupancyGrid asd = new OccupancyGrid(16, 12, 32, 24);

    private ArrayList<Enemy> enemy = new ArrayList<Enemy>();
    private ArrayList<Trap> trap = new ArrayList<Trap>();
    private ArrayList<TurretTower> turretTower = new ArrayList<TurretTower>();
    private ArrayList<TurretBase> turretBase = new ArrayList<TurretBase>();
    private ArrayList<Wall> wall = new ArrayList<Wall>();
    private Base base;

    private String selectedType = "Trap";

    private static int gold = 5000;
    //private static int experiencePoint = 0;
    private static int increaseGold = 100;
    //UI reset timer variables
    private static long uiResetTime = 500;
    private long uiStartTime = System.currentTimeMillis();
    //Enemy timer variables
    private long enemyFollowingTime = 1500;
    private long enemyStartTime = System.currentTimeMillis();
    /*Cursor position for hologram effect*/
    private float holoX;
    private float holoY;

    PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        batch = new SpriteBatch();
        gameStateManager = new GameStateManager();
        gameStateManager.push(new MenuState(gameStateManager) );

        /*LOAD TEXTURES*/
        for (int i = 0; i < MAX_LEVEL; i++) {
            /*Entity Textures*/
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
            /*New Entity Hologram Textures*/
            /*Load Hologram Texture of Level i Turret*/
            holoTurretLevelTexture[i] = new Texture(Gdx.files.internal("ui/new_entity/new_turret_level" + i + ".png") );
            /*Load Hologram Texture of Level i Trap*/
            holoTrapLevelTexture[i] = new Texture(Gdx.files.internal("ui/new_entity/new_trap_level" + i + ".png") );
            /*Load Hologram Texture of Level i Wall*/
            holoWallLevelTexture[i] = new Texture(Gdx.files.internal("ui/new_entity/new_wall_level" + i + ".png") );
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
        buttonNewTrap.setPosition(0, Gdx.graphics.getHeight() - SIZE_TILE * 2);
        buttonNewWall = new Sprite(buttonNewWallTexture);
        buttonNewWall.setPosition(0, Gdx.graphics.getHeight() - SIZE_TILE * 4);
        buttonUpgradeBase = new Sprite(buttonUpgradeBaseTexture);
        buttonUpgradeBase.setPosition(0, Gdx.graphics.getHeight() - SIZE_TILE * 6);
        buttonUpgradeTurret = new Sprite(buttonUpgradeTurretTexture);
        buttonUpgradeTurret.setPosition(0, Gdx.graphics.getHeight() - SIZE_TILE * 8);
        buttonUpgradeTrap = new Sprite(buttonUpgradeTrapTexture);
        buttonUpgradeTrap.setPosition(0, Gdx.graphics.getHeight() - SIZE_TILE * 10);
        buttonUpgradeWall = new Sprite(buttonUpgradeWallTexture);
        buttonUpgradeWall.setPosition(0, Gdx.graphics.getHeight() - SIZE_TILE * 12);

        /*Font*/
        font = new BitmapFont(Gdx.files.internal("font/pericles_roboto.fnt"), false);

        base = new Base(baseLevelTexture[0], SIZE_TILE * 16, SIZE_TILE * 12,  100);
        base.setPosition(SIZE_TILE * 16, Gdx.graphics.getHeight() - SIZE_TILE * 12);
       /* asd.grid[16][12].isFilled = true;
        asd.grid[15][12].isFilled = true;
        asd.grid[16][13].isFilled = true;
        asd.grid[15][13].isFilled = true;*/
        asd.destinationDetermination();

        Gdx.input.setInputProcessor(this);
    }

    /*Removes all dead objects*/
    public void cleanUp() {
        //Removes itemEnemy from enemy list, if itemEnemy isn't alive (if itemEnemy was killed by kill() method)
        Iterator<Enemy> itemEnemy = enemy.iterator();

        while(itemEnemy.hasNext()) {
            Enemy actualEnemy = itemEnemy.next();
            if (!actualEnemy.isAlive() ) {
                itemEnemy.remove();
            }
        }

        //Removes itemTrap from trap list, if itemTrap isn't alive (if itemTrap was killed by kill() method)
        Iterator<Trap> itemTrap = trap.iterator();

        while(itemTrap.hasNext()) {
            Trap actualTrap = itemTrap.next();
            if (!actualTrap.isAlive() ) {
                itemTrap.remove();
            }
        }

        //Removes itemTurretBase from turretBase list, if itemTurretBase isn't alive (if itemTurretBase was killed by kill() method)
        Iterator<TurretBase>itemTurretBase = turretBase.iterator();

        while(itemTurretBase.hasNext()) {
            TurretBase actualTurretBase = itemTurretBase.next();
            if (!actualTurretBase.isAlive() ) {
                itemTurretBase.remove();
            }
        }

        //Removes itemTurretTower from turretTower list, if itemTurretTower isn't alive (if itemTurretTower was killed by kill() method)
        Iterator<TurretTower> itemTurretTower = turretTower.iterator();

        while(itemTurretTower.hasNext()) {
            TurretTower actualTurretTower = itemTurretTower.next();
            if (!actualTurretTower.isAlive() ) {
                itemTurretTower.remove();
            }
        }

        //Removes itemWall from wall list, if itemWall isn't alive (if itemWall was killed by kill() method)
        Iterator<Wall> itemWall = wall.iterator();

        while(itemWall.hasNext()) {
            Wall actualWall = itemWall.next();
            if (!actualWall.isAlive() ) {
                itemWall.remove();
            }
        }
    }

    @Override
    public void update(float timeDelta) {}

    @Override
    public void render(SpriteBatch batch) {
        cleanUp();

        //asd.destinationDetermination();
        batch.begin();

        /*DRAW UI*/
        /*Reset UI Button's Colors*/
        //UI reset timer
        if (System.currentTimeMillis() - uiStartTime > uiResetTime) {
            buttonNewTurret.setColor(1, 1, 1, 1);
            buttonNewTrap.setColor(1, 1, 1, 1);
            buttonNewWall.setColor(1, 1, 1, 1);
            buttonUpgradeBase.setColor(1, 1, 1, 1);
            buttonUpgradeTurret.setColor(1, 1, 1, 1);
            buttonUpgradeTrap.setColor(1, 1, 1, 1);
            buttonUpgradeWall.setColor(1, 1, 1, 1);
            uiStartTime = System.currentTimeMillis();
        }
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
        font.draw(batch, "Gold: " + gold, SIZE_TILE * 2, Gdx.graphics.getHeight() );
        //font.draw(batch, "XP: " + experiencePoint, SIZE_TILE, Gdx.graphics.getHeight() - SIZE_TILE);
        font.draw(batch, "HP: " + Base.getHealthPoint(), SIZE_TILE * 2, Gdx.graphics.getHeight() - SIZE_TILE * 4);
        font.draw(batch, "Base level: " + Base.getLevel(), SIZE_TILE * 2, Gdx.graphics.getHeight() - SIZE_TILE * 6);
        /*Update and Draw New Entity Hologram Sprites*/
        holoTurret = new Sprite(holoTurretLevelTexture[TurretBase.getLevel()] );
        holoTrap = new Sprite(holoTrapLevelTexture[Trap.getLevel()] );
        holoWall = new Sprite(holoWallLevelTexture[Wall.getLevel()] );
        if (selectedType.equals("Turret") ) {
            holoTurret.setPosition(holoX, holoY);
            holoTurret.draw(batch);
        } else if (selectedType.equals("Trap") ) {
            holoTrap.setPosition(holoX, holoY);
            holoTrap.draw(batch);
        } else if (selectedType.equals("Wall") ) {
            holoWall.setPosition(holoX, holoY);
            holoWall.draw(batch);
        }

        gold += increaseGold;

        //Draw Base
        if (Base.getHealthPoint() <= 0) { //if Base is dead, not draw
            base.kill();
        }
        if (base.isAlive() ) {
            base.rotator(); //if Base is alive, rotate
            base.draw(batch);
        } else {
            //if base is destroyed
            font.setColor(1, 0, 0 ,1);
            font.draw(batch, "Your Base Has Been Destroyed", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            //wait a second and start new menu state
            float delay = 1; // seconds

            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    gameStateManager.set(new MenuState(gameStateManager) );
                }
            }, delay);
        }

        //Enemy start timer
        if (System.currentTimeMillis() - enemyStartTime > enemyFollowingTime) {
            enemy.add(new Enemy(enemyLevelTexture[0], SIZE_TILE * 2, SIZE_TILE * 0, 1, 5, 0));
            enemyStartTime = System.currentTimeMillis();
        }

        for (Enemy iterator : enemy) {
            iterator.draw(batch);
            int nextX = asd.grid[(int)(iterator.getX() / SIZE_TILE)][(int)(iterator.getY() / SIZE_TILE)].desX;
            int nextY = asd.grid[(int)(iterator.getX() / SIZE_TILE)][(int)(iterator.getY() / SIZE_TILE)].desY;
            float deltaX = nextX * SIZE_TILE - iterator.getX();
            float deltaY = nextY * SIZE_TILE - iterator.getY();
            float abs = Math.abs(deltaX) + Math.abs(deltaY);
            iterator.translate(deltaX / abs, deltaY / abs);
            iterator.setPosition(iterator.getX() + deltaX / abs, iterator.getY() + deltaY / abs);
            iterator.setSize(SIZE_TILE, SIZE_TILE);
            if (nextX == asd.baseX && nextY == asd.baseY) {
                Base.setHealthPoint(Base.getHealthPoint() - iterator.getHealth() );
                iterator.kill();
            }
        }

        //Draw TurretBases
        for (TurretBase iterator : turretBase) {
            if (iterator.getX() != 0) {
                iterator.setPosition(iterator.getX(), iterator.getY());
                iterator.setSize(SIZE_TILE, SIZE_TILE);
                iterator.draw(batch);
            }
        }

        //Draw TurretTowers
        for (TurretTower iterator:turretTower) {
            if (iterator.getX() != 0) {
                iterator.setPosition(iterator.getX(), iterator.getY());
                iterator.rotate(5);
                iterator.draw(batch);
            }
        }

        //Draw traps
        for (Trap iterator : trap) {
            if (iterator.getX() != 0) {
                iterator.setPosition(iterator.getX(), iterator.getY());
                iterator.setSize(SIZE_TILE, SIZE_TILE);
                iterator.draw(batch);
            }
        }

        //Draw Walls
        for (Wall iterator : wall) {
            if (iterator.getX() != 0) {
                iterator.setPosition(iterator.getX(), iterator.getY());
                iterator.setSize(SIZE_TILE, SIZE_TILE);
                iterator.draw(batch);
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
            /*Dispose Texture of Level i New Turret Hologram*/
            holoTurretLevelTexture[i].dispose();
            /*Dispose Texture of Level i New Turret Hologram*/
            holoTrapLevelTexture[i].dispose();
            /*Dispose Texture of Level i New Turret Hologram*/
            holoWallLevelTexture[i].dispose();
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
        if (screenX <= SIZE_TILE * 2) {
            if (y_parsed >= Gdx.graphics.getHeight() - SIZE_TILE * 2) {
                selectedType = "Turret";
                buttonNewTurret.setColor(1,1,1,0.5f);
            } else if (y_parsed >= Gdx.graphics.getHeight() - SIZE_TILE * 4 ) {
                selectedType = "Trap";
                buttonNewTrap.setColor(1,1,1,0.5f);
            } else if (y_parsed >= Gdx.graphics.getHeight() - SIZE_TILE * 6) {
                selectedType = "Wall";
                buttonNewWall.setColor(1,1,1,0.5f);
            } else if (y_parsed >= Gdx.graphics.getHeight() - SIZE_TILE * 8) {
                if (Base.getLevel() < MAX_LEVEL - 1 && gold >= Base.getPriceUpgrade(Base.getLevel() ) ) {
                    gold -= Base.getPriceUpgrade(Base.getLevel() );
                    Base.upLevel();
                    base.setTexture(baseLevelTexture[Base.getLevel()]);
                    base.setPosition(SIZE_TILE * 16, SIZE_TILE * 12);
                    increaseGold += Base.getLevel();
                    Base.setHealthPoint(Base.getHealthPoint() * Base.getLevel());
                }
                buttonUpgradeBase.setColor(1,1,1,0.5f);
            } else if (y_parsed >= Gdx.graphics.getHeight() - SIZE_TILE * 10) {
                if (TurretBase.getLevel() < MAX_LEVEL - 1 && gold >= TurretBase.getPriceUpgrade(TurretBase.getLevel() ) ) {
                    gold -= TurretBase.getPriceUpgrade(TurretBase.getLevel());
                    TurretBase.upLevel();

                    for (TurretBase iterator : turretBase) {
                        iterator.setTexture(turretBaseLevelTexture[TurretBase.getLevel()] );
                    }

                    for (TurretTower iterator : turretTower) {
                        iterator.setTexture(turretTowerLevelTexture[TurretBase.getLevel()] );
                    }

                }
                buttonUpgradeTurret.setColor(1,1,1,0.5f);
            } else if (y_parsed >= Gdx.graphics.getHeight() - SIZE_TILE * 12) {
                if (Trap.getLevel() <  MAX_LEVEL - 1 && gold >= Trap.getPriceUpgrade(Trap.getLevel() ) ) {
                    gold -= Trap.getPriceUpgrade(Trap.getLevel() );
                    Trap.upLevel();
                    for (Trap iterator : trap) {
                        iterator.setTexture(trapLevelTexture[Trap.getLevel()]);
                    }
                }
                buttonUpgradeTrap.setColor(1,1,1,0.5f);
            } else if (y_parsed >= Gdx.graphics.getHeight() - SIZE_TILE * 14) {
                if (Wall.getLevel() < MAX_LEVEL - 1 && gold >= Wall.getPriceUpgrade(Wall.getLevel() ) ) {
                    gold -= Wall.getPriceUpgrade(Wall.getLevel() );
                    Wall.upLevel();
                    for (Wall iterator : wall) {
                        iterator.setTexture(wallLevelTexture[Wall.getLevel()] );
                    }
                }
                buttonUpgradeWall.setColor(1,1,1,0.5f);
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int x_parsed = (int)Math.floor(screenX / SIZE_TILE);
        int y_parsed_inverse = (int)Math.floor( (Gdx.graphics.getHeight() - screenY) / SIZE_TILE);

        if (!asd.grid[x_parsed][y_parsed_inverse].isFilled) {
            if (selectedType.equals("Trap") && gold >= Trap.getPRICE() ) {
                asd.grid[x_parsed][y_parsed_inverse].isFilled = true;
                asd.destinationDetermination();
                gold -= Trap.getPRICE();
                trap.add( new Trap(trapLevelTexture[Trap.getLevel()], x_parsed * SIZE_TILE, y_parsed_inverse * SIZE_TILE, 5) );
            } else if (selectedType.equals("Turret") && gold >= TurretBase.getPRICE() ) {
                asd.grid[x_parsed][y_parsed_inverse].isFilled = true;
                asd.destinationDetermination();
                gold -= TurretBase.getPRICE();
                turretBase.add( new TurretBase(turretBaseLevelTexture[TurretBase.getLevel()], x_parsed * SIZE_TILE, y_parsed_inverse * SIZE_TILE) );
                turretTower.add( new TurretTower(turretTowerLevelTexture[TurretBase.getLevel()], x_parsed * SIZE_TILE, y_parsed_inverse * SIZE_TILE, 5) );
            } else if (selectedType.equals("Wall") && gold >= Wall.getPRICE() ) {
                asd.grid[x_parsed][y_parsed_inverse].isFilled = true;
                asd.destinationDetermination();
                gold -= Wall.getPRICE();
                wall.add( new Wall(wallLevelTexture[Wall.getLevel()], x_parsed * SIZE_TILE, y_parsed_inverse * SIZE_TILE, 10) );
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

        if(x_parsed >= SIZE_TILE * 2) {
            holoX = x_parsed;
            holoY = y_parsed_inverse;
        }

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
