package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;

public class Wall extends Entity {

    public Wall(int x, int y, Image img) {
        super(x, y, img);
        int plus = 6;
        int subtract = plus * 2;
        this.shape = new Rectangle(x * Sprite.SCALED_SIZE + plus, y * Sprite.SCALED_SIZE + plus,
                Sprite.SCALED_SIZE - subtract, Sprite.SCALED_SIZE - subtract);
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
