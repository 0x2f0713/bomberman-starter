package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.audio.Sound;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public class Bomber extends Entity {
    private boolean goUp;
    private boolean goDown;
    private boolean goLeft;
    private boolean goRight;

    public boolean collisionOn = false;
    private EntityState BombermanState;

    public boolean isGoUp() {
        return goUp;
    }

    public void setGoUp(boolean goUp) {
        this.goUp = goUp;
    }

    public boolean isGoDown() {
        return goDown;
    }

    public void setGoDown(boolean goDown) {
        this.goDown = goDown;
    }

    public boolean isGoLeft() {
        return goLeft;
    }

    public EntityState getBombermanState() {
        return BombermanState;
    }

    public void setBombermanState(EntityState bombermanState) {
        BombermanState = bombermanState;
    }

    public void setGoLeft(boolean goLeft) {
        this.goLeft = goLeft;
    }

    public boolean isGoRight() {
        return goRight;
    }

    public void setGoRight(boolean goRight) {
        this.goRight = goRight;
    }

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        shape = new Rectangle(this.getX() + 4, this.getY() + 5, 14 * Sprite.SCALED_SIZE / 16, 12 * Sprite.SCALED_SIZE / 16);
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
        y += dy;
        shape.setX(x);
        shape.setY(y);

//        if (dx != 0 ||  dy != 0) {
//            Sound.walk_1.play();
//            Sound.walk_1.stop();
//        }
        //System.out.println(x + " " + y);
        changeMovement(dx, dy);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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