package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public class Bomber extends Entity {
    private boolean goUp;
    private boolean goDown;
    private boolean goLeft;
    private boolean goRight;

    public boolean collisionOn = false;
    private EntityState BombermanState;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        shape = new Rectangle(33, 33, 12 / 16.0 * Sprite.SCALED_SIZE, 12 / 16.0 * Sprite.SCALED_SIZE);
        BombermanState = EntityState.STOP;
        goUp = goDown = goLeft = false;
        goRight = true;
    }

    @Override
    public void update() {

    }

    @Override
    public void update(int dx, int dy) {
        if (BombermanState == EntityState.STOP) {
            BombermanState = EntityState.MOVE_1;
        }
        x += dx;
        shape.setX(x);
        shape.setY(y);
        y += dy;
        changeMovement(dx, dy);
    }

    private void changeMovement(int dx, int dy) {
        if (dx != 0 && dy == 0) {
            if (dx > 0) {
                goRight = true;
                goLeft = false;
                goUp = false;
                goDown = false;
                if (BombermanState == EntityState.MOVE_1) {
                    img = Sprite.player_right_1.getFxImage();
                    BombermanState = EntityState.MOVE_2;
                } else if (BombermanState == EntityState.MOVE_2) {
                    img = Sprite.player_right_2.getFxImage();
                    BombermanState = EntityState.MOVE_1;
                }
            } else {
                goLeft = true;
                goRight = false;
                goUp = false;
                goDown = false;
                if (BombermanState == EntityState.MOVE_1) {
                    img = Sprite.player_left_1.getFxImage();
                    BombermanState = EntityState.MOVE_2;
                } else if (BombermanState == EntityState.MOVE_2) {
                    img = Sprite.player_left_2.getFxImage();
                    BombermanState = EntityState.MOVE_1;
                }
            }
        } else if (dy != 0 && dx == 0) {
            if (dy > 0) {
                goDown = true;
                goUp = false;
                goLeft = false;
                goRight = false;
                if (BombermanState == EntityState.MOVE_1) {
                    img = Sprite.player_down_1.getFxImage();
                    BombermanState = EntityState.MOVE_2;
                } else if (BombermanState == EntityState.MOVE_2) {
                    img = Sprite.player_down_2.getFxImage();
                    BombermanState = EntityState.MOVE_1;
                }
            } else {
                goUp = true;
                goDown = false;
                goLeft = false;
                goRight = false;
                if (BombermanState == EntityState.MOVE_1) {
                    img = Sprite.player_up_1.getFxImage();
                    BombermanState = EntityState.MOVE_2;
                } else if (BombermanState == EntityState.MOVE_2) {
                    img = Sprite.player_up_2.getFxImage();
                    BombermanState = EntityState.MOVE_1;
                }
            }
        } else if (dy != 0) {
            if (dx > 0) {
                goRight = true;
                goLeft = false;
                goUp = false;
                goDown = false;
                if (BombermanState == EntityState.MOVE_1) {
                    img = Sprite.player_right_1.getFxImage();
                    BombermanState = EntityState.MOVE_2;
                } else if (BombermanState == EntityState.MOVE_2) {
                    img = Sprite.player_right_2.getFxImage();
                    BombermanState = EntityState.MOVE_1;
                }
            } else {
                goLeft = true;
                goRight = false;
                goUp = false;
                goDown = false;
                if (BombermanState == EntityState.MOVE_1) {
                    img = Sprite.player_left_1.getFxImage();
                    BombermanState = EntityState.MOVE_2;
                } else if (BombermanState == EntityState.MOVE_2) {
                    img = Sprite.player_left_2.getFxImage();
                    BombermanState = EntityState.MOVE_1;
                }
            }
        } else {
            BombermanState = EntityState.STOP;
            if (goRight) {
                img = Sprite.player_right.getFxImage();
            }
            if (goLeft) {
                img = Sprite.player_left.getFxImage();
            }
            if (goUp) {
                img = Sprite.player_up.getFxImage();
            }
            if (goDown) {
                img = Sprite.player_down.getFxImage();
            }
        }
    }

    @Override
    public void update(int dx, int dy, Image img) {

    }
}