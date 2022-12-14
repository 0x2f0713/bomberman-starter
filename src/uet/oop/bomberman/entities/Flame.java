package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;


import static uet.oop.bomberman.UpdateFlame.flameList;
import static uet.oop.bomberman.BombermanGame.entities;

public class Flame extends Entity{

    private String position;
    private String direction;
    private int timeAnimationChange;
    private final int timeToDisappear = 10;
    private boolean disappear = false;

    public Flame(int xUnit, int yUnit, Image img, String direction) {
        super(xUnit, yUnit, img);
        this.direction = direction;
    }

    public Flame(int xUnit, int yUnit, Image img, String position, String direction) {
        super(xUnit, yUnit, img);

        this.position = position;
        this.direction = direction;

        timeAnimationChange = 0;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isDisappear() {
        return disappear;
    }

    private void updateHorizontal() {
        if (position.equals("mid")) {
            if (timeAnimationChange == 4) {
                img = Sprite.explosion_horizontal1.getFxImage();
            } else if (timeAnimationChange == 6) {
                img = Sprite.explosion_horizontal2.getFxImage();
            }
        } else if (position.equals("last")) {
            if (direction.equals("left")) {
                if (timeAnimationChange == 4) {
                    img = Sprite.explosion_horizontal_left_last1.getFxImage();
                } else if (timeAnimationChange == 6) {
                    img = Sprite.explosion_horizontal_left_last2.getFxImage();
                }
            }
            else if (direction.equals("right")) {
                if (timeAnimationChange == 4) {
                    img = Sprite.explosion_horizontal_right_last1.getFxImage();
                } else if (timeAnimationChange == 6) {
                    img = Sprite.explosion_horizontal_right_last2.getFxImage();
                }
            }
        }

    }

    private void updateVertical() {
        if (position.equals("mid")) {
            if (timeAnimationChange == 4) {
                img = Sprite.explosion_vertical1.getFxImage();
            } else if (timeAnimationChange == 6) {
                img = Sprite.explosion_vertical2.getFxImage();
            }
        } else if (position.equals("last")) {
            if (direction.equals("top")) {
                if (timeAnimationChange == 4) {
                    img = Sprite.explosion_vertical_top_last1.getFxImage();
                } else if (timeAnimationChange == 6) {
                    img = Sprite.explosion_vertical_top_last2.getFxImage();
                }
            }
            else if (direction.equals("down")) {
                if (timeAnimationChange == 4) {
                    img = Sprite.explosion_vertical_down_last1.getFxImage();
                } else if (timeAnimationChange == 6) {
                    img = Sprite.explosion_vertical_down_last2.getFxImage();
                }
            }
        }

    }

    private void updateCenter() {
        if (timeAnimationChange == 4) {
            img = Sprite.bomb_exploded1.getFxImage();
        } else if (timeAnimationChange == 6) {
            img = Sprite.bomb_exploded2.getFxImage();
        }
    }

    @Override
    public void update() {
        timeAnimationChange++;
        if (direction.equals("center")) {
            updateCenter();
        }

        if (direction.equals("top") || direction.equals("down")) {
            updateVertical();
        } else if(direction.equals("left") || direction.equals("right")) {
            updateHorizontal();
        }

        if (timeAnimationChange == timeToDisappear) {
            disappear = true;
        }
        entities.forEach(g -> {
            flameList.forEach(i -> {
                if (g.shape.intersects(i.shape.getLayoutBounds()) &&
                        (g instanceof Enemy || (g instanceof Bomber && g.getState() != EntityState.GOD)) ) {
                    g.setState(EntityState.DIE);
                }
            });
        });
    }

    @Override
    public void update(int dx, int dy) {

    }

    @Override
    public void update(int dx, int dy, Image img) {

    }
}
