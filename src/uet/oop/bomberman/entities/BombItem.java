package uet.oop.bomberman.entities;


import javafx.scene.image.Image;
import uet.oop.bomberman.GameState;
import uet.oop.bomberman.audio.Sound;

import static uet.oop.bomberman.BombermanGame.entities;
import static uet.oop.bomberman.BombermanGame.state;

public class BombItem extends Booster {

    public BombItem(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if (entities.size() > 0 && shape.intersects(entities.get(0).shape.getLayoutBounds()) && entities.get(0) instanceof  Bomber) {
            state.increaseBomb();
            if (GameState.soundEnabled) {
                Sound.power_up.play();
            }
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
