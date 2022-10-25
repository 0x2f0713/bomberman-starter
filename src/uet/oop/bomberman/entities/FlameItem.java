package uet.oop.bomberman.entities;

import static uet.oop.bomberman.BombermanGame.entities;
import static uet.oop.bomberman.BombermanGame.state;
import javafx.scene.image.Image;

public class FlameItem extends Booster {

    public FlameItem(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if (entities.size() > 0 && shape.intersects(entities.get(0).shape.getLayoutBounds()) && entities.get(0) instanceof  Bomber) {
            state.increaseFlame();
            disappear = true;
        }
    }

    @Override
    public void update(int dx, int dy) {

    }

    @Override
    public void update(int dx, int dy, Image img) {

    }
}
