package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity{
    private int timeAnimationChange;
    private final int timeExplode = 3;
    private int timeCount;
    private boolean exploded = false;

    public boolean isExploded() {
        return exploded;
    }

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        timeAnimationChange = 0;
        timeCount = 0;

    }

    @Override
    public void update() {
        timeAnimationChange++;
        if (timeAnimationChange == 5) {
            img = Sprite.bomb_1.getFxImage();
        }
        else if(timeAnimationChange == 10) {
            img = Sprite.bomb_2.getFxImage();
            timeCount++;
            timeAnimationChange = 0;
        }

        if (timeCount == timeExplode) {
            exploded = true;
        }


    }

    @Override
    public void update(int dx, int dy) {

    }

    @Override
    public void update(int dx, int dy, Image img) {

    }
}
