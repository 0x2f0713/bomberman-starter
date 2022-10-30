package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;

import java.util.concurrent.atomic.AtomicBoolean;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.BombermanGame.entities;

public class Balloom extends Enemy {


    private EntityState balloomState;

    public Balloom(int x, int y, Image img) {
        super( x, y, img);
        balloomState = EntityState.ALIVE;
        changeDirection();
    }

    @Override
    public void update() {
        if (balloomState == EntityState.ALIVE) {
            checkCollision();
            changeAnimation();
        } else {
            if (timeDieAnimation == 0) {
                img = Sprite.balloom_dead.getFxImage();
            }
            changeDieAnimation();
        }
    }

    @Override
    protected void changeDirection() {
        int randomDirection = getRandomNumber(0, 4);
        if (randomDirection == 0) {
            goRight = true;
            goDown = goUp = goLeft = false;
        } else if (randomDirection == 1) {
            goLeft = true;
            goDown = goUp = goRight = false;
        } else if (randomDirection == 2) {
            goDown = true;
            goUp = goLeft = goRight = false;
        } else if (randomDirection == 3) {
            goUp = true;
            goDown = goLeft = goRight = false;
        }
    }

    @Override
    protected void changeAnimation() {
        if (goRight || goUp) {
            if (timeAnimationChange == 0) {
                img = Sprite.balloom_right1.getFxImage();
            } else if (timeAnimationChange == 8) {
                img = Sprite.balloom_right2.getFxImage();
            } else if (timeAnimationChange == 16) {
                img = Sprite.balloom_right3.getFxImage();
                timeAnimationChange = 0;
            }
        } else if (goLeft || goDown) {
            if (timeAnimationChange == 0) {
                img = Sprite.balloom_left1.getFxImage();
            } else if (timeAnimationChange == 8) {
                img = Sprite.balloom_left2.getFxImage();
            } else if (timeAnimationChange == 16) {
                img = Sprite.balloom_left3.getFxImage();
                timeAnimationChange = 0;
            }
        }
        timeAnimationChange++;
    }


    private void checkCollision() {
        AtomicBoolean isCollide = new AtomicBoolean(false);
        if (goRight) {
            Rectangle newShape = new Rectangle(shape.getX() + SPEED, shape.getY(),
                    shape.getHeight(), shape.getWidth());

            bombDeque.forEach(i->{
                if (i.shape.intersects(newShape.getLayoutBounds())) {
                    changeDirection();
                    isCollide.set(true);
                }
            });

            obstacleObjects.forEach(i->{
                if (i.shape.intersects(newShape.getLayoutBounds())) {
                    changeDirection();
                    isCollide.set(true);
                }
            });
            if (isCollide.get()) return;
            x += SPEED;

        } else if (goLeft) {
            Rectangle newShape = new Rectangle(shape.getX() - SPEED, shape.getY(),
                    shape.getHeight(), shape.getWidth());

            bombDeque.forEach(i->{
                if (i.shape.intersects(newShape.getLayoutBounds())) {
                    changeDirection();
                    isCollide.set(true);
                }
            });

            obstacleObjects.forEach(i->{
                if (newShape.intersects(i.shape.getLayoutBounds())) {
                    changeDirection();
                    isCollide.set(true);
                }
            });
            if (isCollide.get()) return;
            x -= SPEED;
        } else if (goDown) {
            Rectangle newShape = new Rectangle(shape.getX(), shape.getY() + SPEED,
                    shape.getHeight(), shape.getWidth());

            bombDeque.forEach(i->{
                if (i.shape.intersects(newShape.getLayoutBounds())) {
                    changeDirection();
                    isCollide.set(true);
                }
            });

            obstacleObjects.forEach(i->{
                if (newShape.intersects(i.shape.getLayoutBounds())) {
                    changeDirection();
                    isCollide.set(true);
                }
            });
            if (isCollide.get()) return;
            y += SPEED;
        } else if (goUp) {
            Rectangle newShape = new Rectangle(shape.getX(), shape.getY() - SPEED,
                    shape.getHeight(), shape.getWidth());

            bombDeque.forEach(i->{
                if (i.shape.intersects(newShape.getLayoutBounds())) {
                    changeDirection();
                    isCollide.set(true);
                }
            });

            obstacleObjects.forEach(i->{
                if (newShape.intersects(i.shape.getLayoutBounds())) {
                    changeDirection();
                    isCollide.set(true);
                }
            });
            if (isCollide.get()) return;
            y -= SPEED;
        }

        if (entities.get(0).shape.intersects(shape.getLayoutBounds())
                && entities.get(0).getState() != EntityState.GOD && entities.size() > 0) {
            entities.get(0).setState(EntityState.DIE);
        }

        shape.setX(x + 3);
        shape.setY(y + 6);
    }

    @Override
    public void update(int dx, int dy) {

    }

    @Override
    public void update(int dx, int dy, Image img) {

    }

    @Override
    public void setState(EntityState State) {
        this.balloomState = State;
    }

    @Override
    public EntityState getState() {
        return balloomState;
    }


}
