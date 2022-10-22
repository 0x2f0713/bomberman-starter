package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends MovingEntity{
    protected int SPEED = 4;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    protected int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    protected abstract void changeDirection();

    protected abstract void changeAnimation() ;

    protected void changeDieAnimation() {
        if (timeDieAnimation == 3) {
            img = Sprite.mob_dead1.getFxImage();
        } else if (timeDieAnimation == 5) {
            img = Sprite.mob_dead2.getFxImage();
        } else if (timeDieAnimation == 8) {
            img = Sprite.mob_dead2.getFxImage();
            disappear = true;
        }
        timeDieAnimation ++;
    }

}
