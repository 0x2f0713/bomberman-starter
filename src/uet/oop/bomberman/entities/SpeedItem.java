package uet.oop.bomberman.entities;


import javafx.scene.image.Image;
import uet.oop.bomberman.GameState;
import uet.oop.bomberman.audio.Sound;

import static uet.oop.bomberman.BombermanGame.entities;
import static uet.oop.bomberman.BombermanGame.state;

public class SpeedItem extends Booster {

    public SpeedItem(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        // riênt speedItem tôi cho quái cũng ăn được :)))
        entities.forEach(i -> {
            if (entities.size() > 0 && shape.intersects(i.shape.getLayoutBounds()) ) {
                if (i instanceof Bomber) {
                    state.increaseSpeed();
                    ((Bomber) entities.get(0)).setSpeed(state.getSpeed() / 30);
                    if (GameState.soundEnabled) {
                        Sound.power_up.play();
                    }
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
