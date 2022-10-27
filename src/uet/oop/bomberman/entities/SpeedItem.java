package uet.oop.bomberman.entities;


import javafx.scene.image.Image;

import static uet.oop.bomberman.BombermanGame.entities;
import static uet.oop.bomberman.BombermanGame.state;

public class SpeedItem extends Booster {

    public SpeedItem(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        entities.forEach(i -> {
            if (entities.size() > 0 && shape.intersects(i.shape.getLayoutBounds()) ) {
                if (i instanceof Bomber) {
                    state.increaseSpeed();
                    ((Bomber) entities.get(0)).setSpeed(state.getSpeed() / 30);
                }
                else {
                    i.SPEED += 4;
                }
                disappear = true;
            }
        });
    }

    @Override
    public void update(int dx, int dy) {

    }

    @Override
    public void update(int dx, int dy, Image img) {

    }
}
