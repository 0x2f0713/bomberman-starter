package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Booster extends Entity{
    protected boolean disappear = false;

    public boolean isDisappear() {
        return disappear;
    }

    public Booster(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
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
