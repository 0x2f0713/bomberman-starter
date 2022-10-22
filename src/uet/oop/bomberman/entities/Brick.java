package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private boolean Break;
    boolean Disappear;
    private int timeAnimationChange;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
        Disappear = Break = false;
    }

    @Override
    public void update() {
        if (Break) {
            if (timeAnimationChange == 0) {
                img = Sprite.brick_exploded.getFxImage();
            } else if (timeAnimationChange == 2) {
                img = Sprite.brick_exploded1.getFxImage();
            } else if (timeAnimationChange == 4) {
                img = Sprite.brick_exploded2.getFxImage();
                Disappear = true;
            }
            timeAnimationChange++;
        }
    }

    public boolean isDisappear() {
        return Disappear;
    }

    public boolean isBreak() {
        return Break;
    }

    public void setBreak(boolean aBreak) {
        Break = aBreak;
    }

    @Override
    public void update(int dx, int dy) {

    }

    @Override
    public void update(int dx, int dy, Image img) {

    }
}
