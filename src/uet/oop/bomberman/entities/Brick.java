package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private boolean Break;
    boolean Disappear;
    private int timeAnimationChange;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
        Disappear = Break = false;
        int plus = 6;
        int subtract = plus * 2;
        this.shape = new Rectangle(x * Sprite.SCALED_SIZE + plus, y * Sprite.SCALED_SIZE + plus,
                Sprite.SCALED_SIZE - subtract, Sprite.SCALED_SIZE - subtract);
    }

    @Override
    public void update() {
        if (Break) {
            if (timeAnimationChange == 0) {
                img = Sprite.brick_exploded.getFxImage();
            } else if (timeAnimationChange == 4) {
                img = Sprite.brick_exploded1.getFxImage();
            } else if (timeAnimationChange == 8) {
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
