package uet.oop.bomberman.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uet.oop.bomberman.PlayerState;

public class HUD {

    private GameText levelText;
    private GameText isPausingText;
    private GameText timeText;
    private GameText scoreText;
    private GameText speedText;
    private GameText flameText;
    private GameText bombText;
    private GameText lifeText;


    private final static int textPadding = 20;
    private final static int rowPadding = 10;

    private final static Insets collumnPaddingInsets = new Insets(rowPadding, textPadding, rowPadding, textPadding);

    public VBox hud;
    private HBox row1;
    private HBox row2;

    public HUD(Font font, PlayerState state) {
        setLevelText(new GameText(10, 50, font, "LEVEL %d", state.getLevel()));
        setTimeText(new GameText(10, 50, font, "TIME %d", state.getRemainingTime()));
        setIsPausingText(new GameText(10, 50, font, "%s", state.isPlaying ? "" : "PAUSING"));
        row1 = createRow(
                this.levelText,
                spacer(),
                this.isPausingText,
                spacer(),
                this.timeText
        );
        setScoreText(new GameText(10, 50, font, "SCORE %d", state.getScore()));
        setSpeedText(new GameText(10, 50, font, "SPEED %d", state.getSpeed()));
        setFlameText(new GameText(10, 50, font, "FLAME %d", state.getFlame()));
        setBombText(new GameText(10, 50, font, "BOMB %d", state.getBomb()));
        setLifeText(new GameText(10, 50, font, "LIFE %d", state.getLife()));
        row2 = createRow(
                this.scoreText,
                spacer(),
                this.speedText,
                spacer(),
                this.flameText,
                spacer(),
                this.bombText,
                spacer(),
                this.lifeText
//                spacer(),
//                new GameText(10, 50, "E 10", font)
        );
        hud = new VBox(
                row1,
                row2
        );
    }

    private HBox createRow(Node... nodes) {
        HBox row = new HBox(nodes);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(collumnPaddingInsets);
        return row;
    }
    private Node spacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    public void setRowWidth(double width) {
        row1.setMinWidth(width);
        row2.setMinWidth(width);
    }

//    public Text text(double v, double v1, String s) {
//        Text _text = new Text(v, v1, s);
//        _text.setFont(RetroGamingFonts.s);
//        return _text;
//    }

    public void updateLevel(int newLevel) {
        levelText.updateText(newLevel);
    }

    public void updateIsPausing(boolean isPausing) {
        isPausingText.updateText(isPausing ? "PAUSING" : "");
    }

    public void updateRemainingTime(int newRemainingTime) {
        timeText.updateText(newRemainingTime);
    }

    public void updateScore(int newScore) {
        scoreText.updateText(newScore);
    }

    public void updateSpeed(int newSpeed) {
        speedText.updateText(newSpeed);
    }
    public void updateFlame(int newFlame) {
        flameText.updateText(newFlame);
    }

    public void updateBomb(int newBomb) {
        bombText.updateText(newBomb);
    }

    public void updateLife(int newLife) {
        lifeText.updateText(newLife);
    }

    public GameText getLevelText() {
        return levelText;
    }

    public void setLevelText(GameText levelText) {
        this.levelText = levelText;
    }

    public GameText getTimeText() {
        return timeText;
    }

    public void setTimeText(GameText timeText) {
        this.timeText = timeText;
    }

    public GameText getScoreText() {
        return scoreText;
    }

    public void setScoreText(GameText scoreText) {
        this.scoreText = scoreText;
    }

    public GameText getSpeedText() {
        return speedText;
    }

    public void setSpeedText(GameText speedText) {
        this.speedText = speedText;
    }

    public GameText getFlameText() {
        return flameText;
    }

    public void setFlameText(GameText flameText) {
        this.flameText = flameText;
    }

    public GameText getBombText() {
        return bombText;
    }

    public void setBombText(GameText bombText) {
        this.bombText = bombText;
    }

    public GameText getLifeText() {
        return lifeText;
    }

    public void setLifeText(GameText lifeText) {
        this.lifeText = lifeText;
    }

    public GameText getIsPausingText() {
        return isPausingText;
    }

    public void setIsPausingText(GameText isPausingText) {
        this.isPausingText = isPausingText;
    }
}
