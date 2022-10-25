package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;

public class Booster extends Entity {
    protected boolean disappear = false;

    public boolean isDisappear() {
        return disappear;
    }

    public Booster(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        int plus = 4;
        int subtract = plus * 2;
        this.shape = new Rectangle(xUnit * Sprite.SCALED_SIZE + 10, yUnit * Sprite.SCALED_SIZE + 4,
                Sprite.SCALED_SIZE - 18, Sprite.SCALED_SIZE - subtract);
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
}
