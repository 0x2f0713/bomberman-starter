package uet.oop.bomberman.audio;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class Music {

    private Media media;
    private MediaPlayer player;

    public static Music stage_start = new Music("res/music/stage_start.mp3");
    public static Music stage_theme = new Music("res/music/stage_theme.mp3");
    public static Music title_screen = new Music("res/music/title_screen.mp3");
    Music(String path) {
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

    public void setVolume(double v) {
        this.player.setVolume(v);
    }
}
