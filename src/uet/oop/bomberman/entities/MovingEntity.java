package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import static uet.oop.bomberman.BombermanGame.state;
public abstract class MovingEntity extends Entity{
    protected int SPEED = state.getSpeed()/30;
    protected int timeAnimationChange;
    protected int timeDieAnimation;
    protected boolean disappear;
    protected boolean goUp;
    protected boolean goDown;
    protected boolean goLeft;
    protected boolean goRight;
    public MovingEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        disappear = false;
        timeAnimationChange = timeDieAnimation = 0;
    }

    @Override
    public void update() {

    }

    @Override
    public void update(int dx, int dy) {

    }

    @Override
    public void update(int dx, int dy, Image img) {

    }

    public boolean isDisappear() {
        return disappear;
    }

    public abstract void setState(EntityState State);
    public abstract EntityState getState();
}
