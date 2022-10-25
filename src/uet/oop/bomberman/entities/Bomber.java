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

public class Bomber extends MovingEntity {

    private int GodModeCount = 50;
    private final int firstChange = 2;
    private final int secondChange = 6;

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

    @Override
    public EntityState getState() {
        return BombermanState;
    }

    @Override
    public void setState(EntityState bombermanState) {
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
        shape = new Rectangle(32, 32, 23 ,27);
        BombermanState = EntityState.ALIVE;
        goUp = goDown = goLeft = false;
        goRight = true;
    }

    @Override
    public void update() {
        if (BombermanState == EntityState.GOD) {
            GodModeCount--;
            if (GodModeCount == 0) {
                BombermanState = EntityState.ALIVE;
            }
        }
    }

    @Override
    public void update(int dx, int dy) {
        if (BombermanState != EntityState.DIE) {
            x += dx;
            y += dy;
            shape.setX(x - 2);
            shape.setY(y + 5);
            //System.out.println(x + " " + y);
            changeMovement(dx, dy);
        } else {
            if (timeDieAnimation == 0) {
                img = Sprite.player_dead1.getFxImage();
            } else if(timeDieAnimation == 10) {
                img = Sprite.player_dead2.getFxImage();
            } else if (timeDieAnimation == 20) {
                img = Sprite.player_dead3.getFxImage();
            } else if (timeDieAnimation == 30) {
                disappear = true;
            }
            timeDieAnimation++;
        }
    }



    private void changeMovement(int dx, int dy) {
        if (dx != 0) {
            timeAnimationChange++;
            goUp = false;
            goDown = false;
            if (dx > 0) {
                goRight = true;
                goLeft = false;
                if (timeAnimationChange == firstChange) {
                    img = Sprite.player_right_1.getFxImage();
                } else if (timeAnimationChange == secondChange) {
                    img = Sprite.player_right_2.getFxImage();
                    timeAnimationChange = -2;
                }
            } else {
                goLeft = true;
                goRight = false;
                if (timeAnimationChange == firstChange) {
                    img = Sprite.player_left_1.getFxImage();

                } else if (timeAnimationChange == secondChange) {
                    img = Sprite.player_left_2.getFxImage();
                    timeAnimationChange = -2;
                }
            }
        } else if (dy != 0) {
            timeAnimationChange++;
            goLeft = false;
            goRight = false;
            if (dy > 0) {
                goDown = true;
                goUp = false;
                if (timeAnimationChange == firstChange) {
                    img = Sprite.player_down_1.getFxImage();
                } else if (timeAnimationChange == secondChange) {
                    img = Sprite.player_down_2.getFxImage();
                    timeAnimationChange = -2;
                }
            } else {
                goUp = true;
                goDown = false;
                if (timeAnimationChange == firstChange) {
                    img = Sprite.player_up_1.getFxImage();
                } else if (timeAnimationChange == secondChange) {
                    img = Sprite.player_up_2.getFxImage();
                    timeAnimationChange = -2;
                }
            }
        }  else {
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