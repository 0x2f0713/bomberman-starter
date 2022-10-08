package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.ui.HUD;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;

    public static Font retrogamingFont = Font.loadFont("file:res/fonts/Retro Gaming/Retro Gaming.ttf", 15);

    // HUD
    private HUD hud;

    private GraphicsContext gc;
    private Canvas canvas;
    private GridPane hudPane;
    private GridPane layoutPane;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    private int level = 1;

    boolean running, goNorth, goSouth, goEast, goWest;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {

        Label levelLabel = new Label("LEVEL 1");
        HUD hud = new HUD(retrogamingFont);

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
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    goNorth = true; break;
                    case DOWN:  goSouth = true; break;
                    case LEFT:  goWest  = true; break;
                    case RIGHT: goEast  = true; break;
                    case SHIFT: running = true; break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    goNorth = false; break;
                    case DOWN:  goSouth = false; break;
                    case LEFT:  goWest  = false; break;
                    case RIGHT: goEast  = false; break;
                    case SHIFT: running = false; break;
                }
            }
        });

        // Them scene vao stage
        stage.setTitle("Bomberman Game");
        stage.setScene(scene);
        stage.show();

        hud.setRowWidth(stage.getWidth());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
                render();
            }
        };
        timer.start();

        createMap();

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
    }

    public void createMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }
                else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    public void update() {
        int dx = 0, dy = 0;

        if (goNorth) dy -= 1;
        if (goSouth) dy += 1;
        if (goEast)  dx += 1;
        if (goWest)  dx -= 1;
        if (running) { dx *= 3; dy *= 3; }
        int finalDx = dx;
        int finalDy = dy;
        entities.forEach(i -> i.update(finalDx, finalDy));
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
