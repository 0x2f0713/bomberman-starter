package uet.oop.bomberman.audio;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Sound {

    private Media media;
    private MediaPlayer player;

    public static Sound walk_1 = new Sound("res/sounds/walk_1.wav");

    Sound(String path) {
        this.media = new Media(path);
        this.player = new MediaPlayer(media);
    }

    public void play() {
        this.player.play();
    }
}
