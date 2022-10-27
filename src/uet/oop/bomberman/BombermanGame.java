package uet.oop.bomberman;

import static uet.oop.bomberman.UpdateFlame.updateFlame;
import static uet.oop.bomberman.UpdateFlame.addFlame;
import static uet.oop.bomberman.UpdateFlame.flameList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.ui.HUD;
import uet.oop.bomberman.ui.RetroGamingFonts;
import uet.oop.bomberman.utils.Level;

import java.util.*;

public class BombermanGame extends Application {

    public static int currentBomb = 0;
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    public static double ACTUAL_WIDTH = 0;
    public static double ACTUAL_HEIGHT = 0;
    private Bomber player;
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

    public static List<Entity> brokenBrick = new ArrayList<>();

    public static PlayerState state;

    boolean running, goNorth, goSouth, goEast, goWest;

    private Scene scene;
    public void init() {
        state = new PlayerState();
        player = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(player);
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {

        Label levelLabel = new Label("LEVEL 1");
        hud = new HUD(RetroGamingFonts.size15, state);

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

    }

    public void createMap() {
        for (int i = 0; i < Level.level1.getRowCount(); i++) {
            for (int j = 0; j < Level.level1.getColumnCount(); j++) {
                Entity object;
                char o = Level.level1.map.map.get(i).row.get(j);
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
                        object = new Portal(j, i, Sprite.portal.getFxImage());
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
                        object = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                        boosterObjects.add((Booster) object);

                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        obstacleObjects.add(object);
                        break;
                    case 'f':
                        object = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                        boosterObjects.add((Booster) object);

                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        obstacleObjects.add(object);
                        break;
                    case 's':
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

        final int DEFAULT_SPEED = 5;

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
            boosterObjects.forEach(Booster::update);
        }
    }

    private void updateMovingEntity(int finalDx, int finalDy) {
        if (entities.get(0).isDisappear()) {
            state.minusLife();
            entities.remove(0);
            if (state.getLife() > 0) {
                player = new Bomber(4, 4, Sprite.player_right.getFxImage());
                player.setState(EntityState.GOD);
                entities.add(0, player);
            } else {
                System.out.println(123);
            }
        }

        entities.removeIf(movingEntity -> movingEntity.isDisappear() && movingEntity instanceof Enemy);

        entities.forEach(i -> {
            if (i instanceof Bomber) {
                for (Entity entity : obstacleObjects) {
                    Rectangle shape = new Rectangle(i.shape.getX() + finalDx, i.shape.getY() + finalDy,
                            i.shape.getHeight(), i.shape.getWidth());
                    if (entity.shape.intersects(shape.getBoundsInLocal())) {
                        return;
                    }
                }
            }
            i.update(finalDx, finalDy);
        });

        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));

        if (boosterObjects.size() > 0) {
            boosterObjects.forEach(g -> g.render(gc));
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
        if (!state.isPlaying) {
            drawMenuWindow();
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
                        } else {
                            gameState.optionNumber = (gameState.optionNumber == 0 ? GameState.MAX_OPTION_NUMBER - 1 : --gameState.optionNumber) % GameState.MAX_OPTION_NUMBER;
                        }
                        break;
                    case DOWN:
                        if (state.isPlaying) {
                            goSouth = true;
                        } else {
                            gameState.optionNumber = (gameState.optionNumber + 1) % GameState.MAX_OPTION_NUMBER;
                        }
                        break;
                    case LEFT:
                        goWest = true;
                        break;
                    case RIGHT:
                        goEast = true;
                        break;
                    case SHIFT:
                        running = true;
                        break;
                    case SPACE:
                        if (currentBomb < state.getBomb() && player.getState() != EntityState.DIE) {
                            Bomb bomb = new Bomb((player.getX() + 10) / 32, (player.getY() + 10) / 32, Sprite.bomb.getFxImage());
                            bombDeque.offerLast(bomb);
                            currentBomb++;
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
        assert bombDeque.peek() != null;
        if (bombDeque.peek().isExploded()) {
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

    public void drawMenuWindow() {
        drawSubWindow((int) Math.round(ACTUAL_WIDTH / 2) - 150, (int) Math.round(ACTUAL_HEIGHT / 2) - 150, 300, 300);
        gc.setFill(Color.WHITE);
        gc.setFont(RetroGamingFonts.size30);
        gc.fillText("MENU", Math.round(ACTUAL_WIDTH / 2) - 50, (int) Math.round(ACTUAL_HEIGHT / 2) - 150 + 30);
        gc.setFont(RetroGamingFonts.size20);
        gc.fillText("Music", Math.round(ACTUAL_WIDTH / 2) - 100, (int) Math.round(ACTUAL_HEIGHT / 2) - 150 + 70);
        if (gameState.optionNumber == 0) {
            gc.fillText(">", Math.round(ACTUAL_WIDTH / 2) - 120, (int) Math.round(ACTUAL_HEIGHT / 2) - 150 + 70);
        }
        gc.fillText("Sound", Math.round(ACTUAL_WIDTH / 2) - 100, (int) Math.round(ACTUAL_HEIGHT / 2) - 150 + 100);
        if (gameState.optionNumber == 1) {
            gc.fillText(">", Math.round(ACTUAL_WIDTH / 2) - 120, (int) Math.round(ACTUAL_HEIGHT / 2) - 150 + 100);
        }
        gc.fillText("Exit game", Math.round(ACTUAL_WIDTH / 2) - 100, (int) Math.round(ACTUAL_HEIGHT / 2) - 150 + 130);
        if (gameState.optionNumber == 2) {
            gc.fillText(">", Math.round(ACTUAL_WIDTH / 2) - 120, (int) Math.round(ACTUAL_HEIGHT / 2) - 150 + 130);
        }

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