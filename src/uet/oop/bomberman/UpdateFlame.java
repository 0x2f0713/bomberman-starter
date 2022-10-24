package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static uet.oop.bomberman.BombermanGame.*;

public class UpdateFlame {

    public static List<Flame> flameList = new ArrayList<>();

    public static void updateFlame() {
        int i = 0;
        while (i < flameList.size()) {
            if (flameList.get(i).isDisappear()) {
                flameList.remove(0);
            } else {
                break;
            }

        }
    }

    public static void addFlame(Bomb bomb) {
        updateTop(bomb);
        updateDown(bomb);
        updateLeft(bomb);
        updateRight(bomb);
        Flame flame = new Flame((bomb.getX() / 32), (bomb.getY() / 32),
                Sprite.bomb_exploded.getFxImage(), "center");
        flameList.add(flame);
        entities.forEach(g -> {
            if (g.shape.intersects(flame.shape.getLayoutBounds())) {
                g.setState(EntityState.DIE);
            }
        });
    }

    private static void updateTop(Bomb bomb) {
        AtomicBoolean stop = new AtomicBoolean(false);
        int i = 1;
        while (i <= state.getFlame() && !stop.get()) {
            Flame flame = new Flame((bomb.getX() / 32), (bomb.getY() / 32) - i,
                    Sprite.explosion_vertical.getFxImage(), "mid", "top");
            if (i == state.getFlame()) {
                flame.setPosition("last");
                flame.setImg(Sprite.explosion_vertical_top_last.getFxImage());
            }
            flameList.add(flame);
            obstacleObjects.forEach(g -> {
                if (g.shape.intersects(flame.shape.getLayoutBounds())) {
                    if (g instanceof Brick) {
                        ((Brick) g).setBreak(true);
                    }
                    flameList.remove(flameList.size()-1);
                    stop.set(true);
                }
            });

            entities.forEach(g -> {
                if (g.shape.intersects(flame.shape.getLayoutBounds()) && g instanceof Enemy) {
                    g.setState(EntityState.DIE);
                } else if(g.shape.intersects(flame.shape.getLayoutBounds()) &&
                        g instanceof Bomber && g.getState() != EntityState.GOD) {
                    g.setState(EntityState.DIE);
                }
            });

            i++;
        }
    }

    private static void updateDown(Bomb bomb) {
        AtomicBoolean stop = new AtomicBoolean(false);
        int i = 1;
        while (i <= state.getFlame() && !stop.get()) {
            Flame flame = new Flame((bomb.getX() / 32), (bomb.getY() / 32) + i,
                    Sprite.explosion_vertical.getFxImage(),"mid", "down");

            if (i == state.getFlame()){
                flame.setPosition("last");
                flame.setImg(Sprite.explosion_vertical_down_last.getFxImage());

            }
            flameList.add(flame);
            obstacleObjects.forEach(g -> {
                if (g.shape.intersects(flame.shape.getLayoutBounds())) {
                    if (g instanceof Brick) {
                        ((Brick) g).setBreak(true);
                    }
                    flameList.remove(flameList.size()-1);
                    stop.set(true);
                }

            });

            entities.forEach(g -> {
                if (g.shape.intersects(flame.shape.getLayoutBounds()) && g instanceof Enemy) {
                    g.setState(EntityState.DIE);
                } else if(g.shape.intersects(flame.shape.getLayoutBounds()) &&
                        g instanceof Bomber && g.getState() != EntityState.GOD) {
                    g.setState(EntityState.DIE);
                }
            });

            i++;
        }
    }

    private static void updateLeft(Bomb bomb) {
        AtomicBoolean stop = new AtomicBoolean(false);
        int i = 1;
        while (i <= state.getFlame() && !stop.get()) {
            Flame flame = new Flame((bomb.getX() / 32) - i, (bomb.getY() / 32),
                    Sprite.explosion_horizontal.getFxImage(),"mid", "left");

            if (i == state.getFlame()){
                flame.setPosition("last");
                flame.setImg(Sprite.explosion_horizontal_left_last.getFxImage());

            }
            flameList.add(flame);
            obstacleObjects.forEach(g -> {
                if (g.shape.intersects(flame.shape.getLayoutBounds())) {
                    if (g instanceof Brick) {
                        ((Brick) g).setBreak(true);
                    }
                    flameList.remove(flameList.size()-1);
                    stop.set(true);
                }

            });

            entities.forEach(g -> {
                if (g.shape.intersects(flame.shape.getLayoutBounds()) && g instanceof Enemy) {
                    g.setState(EntityState.DIE);
                } else if(g.shape.intersects(flame.shape.getLayoutBounds()) &&
                        g instanceof Bomber && g.getState() != EntityState.GOD) {
                    g.setState(EntityState.DIE);
                }
            });

            i++;
        }
    }

    private static void updateRight(Bomb bomb) {
        AtomicBoolean stop = new AtomicBoolean(false);
        int i = 1;
        while (i <= state.getFlame() && !stop.get()) {
            Flame flame = new Flame((bomb.getX() / 32) + i, (bomb.getY() / 32),
                    Sprite.explosion_horizontal.getFxImage(),"mid", "right");

            if (i == state.getFlame()){
                flame.setPosition("last");
                flame.setImg(Sprite.explosion_horizontal_right_last.getFxImage());

            }
            flameList.add(flame);
            obstacleObjects.forEach(g -> {
                if (g.shape.intersects(flame.shape.getLayoutBounds())) {
                    if (g instanceof Brick) {
                        ((Brick) g).setBreak(true);
                    }
                    flameList.remove(flameList.size()-1);
                    stop.set(true);
                }

            });

            entities.forEach(g -> {
                if (g.shape.intersects(flame.shape.getLayoutBounds()) && g instanceof Enemy) {
                    g.setState(EntityState.DIE);
                } else if(g.shape.intersects(flame.shape.getLayoutBounds()) &&
                        g instanceof Bomber && g.getState() != EntityState.GOD) {
                    g.setState(EntityState.DIE);
                }
            });

            i++;
        }
    }

}
