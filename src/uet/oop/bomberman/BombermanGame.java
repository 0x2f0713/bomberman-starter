package uet.oop.bomberman;

import static uet.oop.bomberman.UpdateFlame.updateFlame;
import static uet.oop.bomberman.UpdateFlame.addFlame;
import static uet.oop.bomberman.UpdateFlame.flameList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import uet.oop.bomberman.audio.Music;
import uet.oop.bomberman.audio.Sound;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.ui.HUD;
import uet.oop.bomberman.ui.RetroGamingFonts;
import uet.oop.bomberman.utils.Level;

import java.util.*;
import java.util.List;

public class BombermanGame extends Application {

    private boolean isWin = false;
    private boolean isLose = false;

    public static int currentBomb = 0;
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    public static double ACTUAL_WIDTH = 0;
    public static double ACTUAL_HEIGHT = 0;
    private Bomber player;
    private Level level;
    public static Font retrogamingFont = Font.loadFont("file:res/fonts/Retro Gaming/Retro Gaming.ttf", 15);

    private GameState gameState = new GameState();
    // HUD
    private HUD hud;

    private GraphicsContext gc;
    private Canvas canvas;
    private GridPane hudPane;
    private GridPane layoutPane;

    public static List<MovingEntity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static ArrayDeque<Bomb> bombDeque = new ArrayDeque<>();

    public static List<Entity> obstacleObjects = new ArrayList<>();

    public static List<Booster> boosterObjects = new ArrayList<>();

    public static Portal portal;
    public static PlayerState state;

    boolean running, goNorth, goSouth, goEast, goWest;

    private Scene scene;
    public void init() {
        state = new PlayerState();
        level = Level.level1;
        player = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(player);

        hud = new HUD(retrogamingFont, state);

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);

        // Tao pane
        layoutPane = new GridPane();
        hudPane = new GridPane();
        hudPane.addColumn(0, hud.hud);
//        hudPane.addColumn(0, levelLabel);
//        hudPane.setHalignment(levelLabel, HPos.RIGHT);
//        hudPane.addColumn(1, scoreLabel);
//        hudPane.setHalignment(levelLabel, HPos.CENTER);
//        hudPane.addColumn(2, counterLabel);
//        hudPane.setHalignment(levelLabel, HPos.LEFT);

        gc = canvas.getGraphicsContext2D();

        // Tao root container
        layoutPane.addRow(0, hudPane);
        layoutPane.addRow(1, canvas);
    }


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {


        Group root = new Group();
        root.getChildren().addAll(layoutPane);

        // Tao scene
        scene = new Scene(root);

        // Them scene vao stage
        stage.setTitle("Bomberman Game");
        stage.setScene(scene);
        stage.show();

        hud.setRowWidth(stage.getWidth());

        ACTUAL_WIDTH = canvas.getWidth();
        ACTUAL_HEIGHT = canvas.getHeight();

        AnimationTimer timer = new AnimationTimer() {

            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate > 36000000) {
                    keyPressedListener();
                    keyReleasedListener();
                    if (state.isPlaying) {
                        update();
                    }
                    render();
                    lastUpdate = now;
                }
            }
        };
        timer.start();

        createMap();
        Music.stage_theme.play();

    }

    private void clear() {
        flameList.clear();
        entities.clear();
        boosterObjects.clear();
        stillObjects.clear();
        bombDeque.clear();
        obstacleObjects.clear();

    }

    private void changeLevel() {
        level = Level.level2;
        clear();
        createMap();
        state = new PlayerState();
        state.setLevel(2);
        player = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(0, player);

    }

    public void createMap() {
        for (int i = 0; i < level.getRowCount(); i++) {
            for (int j = 0; j < level.getColumnCount(); j++) {
                Entity object;
                char o = level.map.map.get(i).row.get(j);
                switch (o) {
                    case '#':
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        obstacleObjects.add(object);
                        break;
                    case '*':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        obstacleObjects.add(object);
                        break;
                    case 'x':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);

                        portal = new Portal(j, i, Sprite.portal.getFxImage());

                        break;
                    case '1':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        object = new Balloom(j, i, Sprite.balloom_left1.getFxImage());
                        entities.add((MovingEntity) object);
                        break;
                    case '2':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        object = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                        entities.add((MovingEntity) object);
                        break;
                    case 'b':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);

                        object = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                        boosterObjects.add((Booster) object);

                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        obstacleObjects.add(object);
                        break;
                    case 'f':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);

                        object = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                        boosterObjects.add((Booster) object);

                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        obstacleObjects.add(object);
                        break;
                    case 's':
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);

                        object = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                        boosterObjects.add((Booster) object);

                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        obstacleObjects.add(object);
                        break;

                    default:
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);

                }

            }
        }
    }

    public void update() {
        int dx = 0, dy = 0;

        int DEFAULT_SPEED = player.getSpeed();

        if (goNorth) dy -= DEFAULT_SPEED;
        if (goSouth) dy += DEFAULT_SPEED;
        if (goEast) dx += DEFAULT_SPEED;
        if (goWest) dx -= DEFAULT_SPEED;
        if (running) {
            dx *= 1.5;
            dy *= 1.5;
        }

        int finalDx = dx;
        int finalDy = dy;

        if (entities.size() > 0) {
            updateMovingEntity(finalDx, finalDy);
        }

        if (bombDeque.size() > 0) {
            updateBomb();
        }

        if (flameList.size() > 0) {
            updateFlame();
            flameList.forEach(Flame::update);
        }

        updateBrick();

        if (boosterObjects.size() > 0) {
            updateBooster();
        }

        // Update HUD
        hud.updateBomb(state.getBomb());
        hud.updateFlame(state.getFlame());
        hud.updateLevel(state.getLevel());
        hud.updateLife(state.getLife());
        hud.updateScore(state.getScore());
        hud.updateSpeed(state.getSpeed());
    }

    private void updateMovingEntity(int finalDx, int finalDy) {

        if (entities.get(0).isDisappear() && entities.get(0) instanceof Bomber) {
            state.minusLife();
            entities.remove(0);
            if (state.getLife() > 0) {
                player = new Bomber(1, 1, Sprite.player_right.getFxImage());
                player.setState(EntityState.GOD);
                entities.add(0, player);
            } else {
                isLose = true;
                if (GameState.soundEnabled) {
                    Sound.game_over.play();
                }
            }
        }

        entities.removeIf(movingEntity -> movingEntity.isDisappear() && movingEntity instanceof Enemy);

        entities.forEach(i -> {
            if (i instanceof Bomber) {
                for (Entity entity : obstacleObjects) {
                    Rectangle shape = new Rectangle(i.shape.getX() + finalDx, i.shape.getY() + finalDy,
                            i.shape.getHeight(), i.shape.getWidth());
                    if (entity.shape.intersects(shape.getBoundsInLocal())) {
                        if (goNorth) {
                            player.setImg(Sprite.player_up.getFxImage());
                        } else if (goSouth) {
                            player.setImg(Sprite.player_down.getFxImage());
                        } else if (goEast) {
                            player.setImg(Sprite.player_right.getFxImage());
                        } else if (goWest) {
                            player.setImg(Sprite.player_left.getFxImage());
                        }
                        return;
                    }
                }

                if (i.shape.intersects(portal.shape.getLayoutBounds()) && entities.size() == 1) {
                    isWin = true;
                }
            }
            i.update(finalDx, finalDy);
        });

        entities.forEach(Entity::update);

        if (isWin) {
            if (state.getLevel() == 1) {
                changeLevel();
                if (GameState.soundEnabled) {
                    Sound.next_level.play();
                }
                isWin = false;
            }
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        stillObjects.forEach(g -> g.render(gc));

        portal.render(gc);

        if (boosterObjects.size() > 0) {
            boosterObjects.forEach(g -> {
//                drawRectangle(gc, g.shape, Color.BLUE);
                g.render(gc);
            });
        }

        obstacleObjects.forEach(g -> {
//            drawRectangle(gc, g.shape, Color.BLUE);
            g.render(gc);
        });
        if (entities.size() > 0) {
            entities.forEach(g -> {
//                drawRectangle(gc, g.shape, Color.RED);
                g.render(gc);
            });
        }



        if (bombDeque.size() > 0) {

            bombDeque.forEach(g -> g.render(gc));
        }

        if (flameList.size() > 0) {
            flameList.forEach(g -> {
//                drawRectangle(gc, g.shape, Color.RED);
                g.render(gc);
            });
        }
        if (isLose) {
            drawGameOver();
        }
        if (!state.isPlaying) {
            drawMenuWindow();
        }

        if (isWin && state.getLevel() == 2) {
            drawGameWin();
        }
    }

    private void keyPressedListener() {
        if (player.getState() != EntityState.DIE) {
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case ESCAPE:
                        state.isPlaying = !state.isPlaying;
                        hud.updateIsPausing(!state.isPlaying);
                        drawMenuWindow();
                        break;
                    case UP:
                        if (state.isPlaying) {
                            goNorth = true;
//                            Sound.walk_1.play();
                        } else {
                            gameState.optionNumber = (gameState.optionNumber == 0 ? GameState.MAX_OPTION_NUMBER - 1 : --gameState.optionNumber) % GameState.MAX_OPTION_NUMBER;
                        }
                        break;
                    case DOWN:
                        if (state.isPlaying) {
                            goSouth = true;
//                            Sound.walk_2.play();
                        } else {
                            gameState.optionNumber = (gameState.optionNumber + 1) % GameState.MAX_OPTION_NUMBER;
                        }
                        break;
                    case LEFT:
                        goWest = true;
//                        Sound.walk_4.play();
                        break;
                    case RIGHT:
                        goEast = true;
//                        Sound.walk_1.play();
                        break;
                    case SHIFT:
                        running = true;
                        break;
                    case SPACE:
                        if (state.isPlaying) {
                            if (currentBomb < state.getBomb() && player.getState() != EntityState.DIE) {
                                Bomb bomb = new Bomb((player.getX() + 10) / 32, (player.getY() + 10) / 32, Sprite.bomb.getFxImage());
                                if (GameState.soundEnabled) {
                                    Sound.placed_bomb.play();
                                }
                                bombDeque.offerLast(bomb);
                                currentBomb++;
                            }
                        } else {
                            switch (gameState.optionNumber) {
                                case 0:
                                    gameState.musicEnabled = !gameState.musicEnabled;
                                    Music.stage_theme.setVolume(gameState.musicEnabled ? 0.5 : 0);
                                    break;
                                case 1:
                                    gameState.soundEnabled = !gameState.soundEnabled;
                                    break;
                                case 2:
                                    Platform.exit();
                                    break;
                            }
                        }
                }

            });
        }
    }

    private void keyReleasedListener() {
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    goNorth = false;
                    break;
                case DOWN:
                    goSouth = false;
                    break;
                case LEFT:
                    goWest = false;
                    break;
                case RIGHT:
                    goEast = false;
                    break;
                case SHIFT:
                    running = false;
                    break;
            }
        });
    }

    private void updateBomb() {
        //ch?? n??y l?? ????? xem qu??? n??o n??? r???i th?? cho ra xong t???o l???a cho v??o addflame
        assert bombDeque.peek() != null;
        if (bombDeque.peek().isExploded()) {
            if (GameState.soundEnabled) {
                Sound.bomb_explored.play();
            }
            addFlame(Objects.requireNonNull(bombDeque.poll()));
            currentBomb--;
        }
        bombDeque.forEach(Entity::update);
    }

    private void updateBrick() {
        int i = 0;
        while (i < obstacleObjects.size()) {

            if (obstacleObjects.get(i) instanceof Brick && ((Brick) obstacleObjects.get(i)).isDisappear()) {
                obstacleObjects.remove(i);
                i--;
            }
            i++;
        }
        obstacleObjects.forEach(Entity::update);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0,0.9);
        gc.setFill(c);
        gc.fillRoundRect(x, y, width, height, 35, 35);
    }
    private void updateBooster() {
        List<Booster> tmp = new ArrayList<>();
        boosterObjects.forEach(Booster::update);
        boosterObjects.forEach(i -> {
            if (i.isDisappear()) {
                tmp.add(i);
            }
        });

        boosterObjects.removeAll(tmp);
    }

    public void drawMenuWindow() {
        int line1 = 90;
        int line2 = 140;
        int line3 = 190;
        drawSubWindow((int) Math.round(ACTUAL_WIDTH / 2) - 150, (int) Math.round(ACTUAL_HEIGHT / 2) - 150, 300, 300);
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.WHITE);
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.BASELINE);
        gc.setLineWidth(3);
        gc.setFont(RetroGamingFonts.size30);
        gc.fillText("MENU", Math.round(ACTUAL_WIDTH / 2) - 50, (int) Math.round(ACTUAL_HEIGHT / 2) - 150 + 40);
        gc.setFont(RetroGamingFonts.size20);

        // Line 1
        gc.fillText("Music", Math.round(ACTUAL_WIDTH / 2) - 100, (int) Math.round(ACTUAL_HEIGHT / 2) - 150 + line1);
        if (gameState.optionNumber == 0) {
            gc.fillText(">", Math.round(ACTUAL_WIDTH / 2) - 120, (int) Math.round(ACTUAL_HEIGHT / 2) - 150 + line1);
        }
        gc.strokeRect(Math.round(ACTUAL_WIDTH / 2) + 50, Math.round(ACTUAL_HEIGHT / 2) - 150 + line1 - 17, 20, 20);
        if (gameState.musicEnabled) {
            gc.fillText("x",Math.round(ACTUAL_WIDTH / 2) + 50 + 2, Math.round(ACTUAL_HEIGHT / 2) - 150 + line1 - 2);
        }

        // Line 2
        gc.fillText("Sound FX", Math.round(ACTUAL_WIDTH / 2) - 100, (int) Math.round(ACTUAL_HEIGHT / 2) - 150 + line2);
        if (gameState.optionNumber == 1) {
            gc.fillText(">", Math.round(ACTUAL_WIDTH / 2) - 120, (int) Math.round(ACTUAL_HEIGHT / 2) - 150 + line2);
        }
        gc.strokeRect(Math.round(ACTUAL_WIDTH / 2) + 50, Math.round(ACTUAL_HEIGHT / 2) - 150 + line2 - 17, 20, 20);
        if (gameState.soundEnabled) {
            gc.fillText("x",Math.round(ACTUAL_WIDTH / 2) + 50 + 2, Math.round(ACTUAL_HEIGHT / 2) - 150 + line2 - 2);
//            gc.fillRect(Math.round(ACTUAL_WIDTH / 2) + 30, Math.round(ACTUAL_HEIGHT / 2) - 150 + line2 - 17, 20, 20);
        }

        // Line 3
        gc.fillText("Exit game", Math.round(ACTUAL_WIDTH / 2) - 100, (int) Math.round(ACTUAL_HEIGHT / 2) - 150 + line3);
        if (gameState.optionNumber == 2) {
            gc.fillText(">", Math.round(ACTUAL_WIDTH / 2) - 120, (int) Math.round(ACTUAL_HEIGHT / 2) - 150 + line3);
        }

    }

    public void drawGameOver() {
        drawSubWindow((int) Math.round(ACTUAL_WIDTH / 2) - 150, (int) Math.round(ACTUAL_HEIGHT / 2) - 100, 300, 200);
        gc.setFill(Color.WHITE);
        gc.setFont(RetroGamingFonts.size30);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("GAMEOVER", Math.round(ACTUAL_WIDTH / 2), (int) Math.round(ACTUAL_HEIGHT / 2));
    }
    public void drawGameWin() {
        drawSubWindow((int) Math.round(ACTUAL_WIDTH / 2) - 150, (int) Math.round(ACTUAL_HEIGHT / 2) - 100, 300, 200);
        gc.setFill(Color.WHITE);
        gc.setFont(RetroGamingFonts.size30);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("YOU WIN!", Math.round(ACTUAL_WIDTH / 2), (int) Math.round(ACTUAL_HEIGHT / 2));
    }
    public void drawRectangle(GraphicsContext gc, Rectangle rect, Color color){
        gc.setFill(color);
        gc.fillRect(rect.getX(),
                rect.getY(),
                rect.getWidth(),
                rect.getHeight());
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
    }
}