package uet.oop.bomberman.audio;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class Sound {

    private Media media;
    private MediaPlayer player;

    public static Sound bomb_explored = new Sound("res/sounds/bomb_explored.wav");
    public static Sound next_level = new Sound("res/sounds/next_level.wav");
    public static Sound placed_bomb = new Sound("res/sounds/placed_bomb.wav");
    public static Sound power_up = new Sound("res/sounds/power_up.wav");
    public static Sound Stage_Start = new Sound("res/sounds/Stage_Start.mp3");
    public static Sound stage_start = new Sound("res/sounds/stage_start.wav");
    public static Sound walk_1 = new Sound("res/sounds/walk_1.wav");
    public static Sound walk_2 = new Sound("res/sounds/walk_2.wav");
    public static Sound walk_4 = new Sound("res/sounds/walk_4.wav");

    Sound(String path) {
        this.media = new Media(new File(path).toURI().toString());
        this.player = new MediaPlayer(media);
        this.player.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void play() {
        this.player.play();
    }
    public void stop() {
        this.player.stop();
    }
}
