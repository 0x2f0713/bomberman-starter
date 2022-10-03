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

import static uet.oop.bomberman.BombermanGame.retrogamingFont;

public class HUD {

    private final static int textPadding = 20;
    private final static int rowPadding = 10;

    private final static Insets collumnPaddingInsets = new Insets(rowPadding, textPadding, rowPadding, textPadding);
//    private final static Insets collumnPaddingInsets = new Insets(0, 0, 0, 0);
//    private final static Insets rowPaddingInsets = new Insets(padding, 0, padding, 0);

    public VBox hud;
    private HBox row1;
    private HBox row2;

    public HUD(Font font) {
        row1 = createRow(
                new GameText(10, 50, "LEVEL 1", font),
                spacer(),
                new GameText(10, 50, "TIME 120", font)
        );
        row2 = createRow(
                new GameText(10, 50, "SCORE: 0", font),
                spacer(),
                new GameText(10, 50, "SPEED: 120", font),
                spacer(),
                new GameText(10, 50, "FLAME 1", font),
                spacer(),
                new GameText(10, 50, "BOMB 1", font),
                spacer(),
                new GameText(10, 50, "LIFE 3", font),
                spacer(),
                new GameText(10, 50, "E 10", font)
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

    public Text text(double v, double v1, String s) {
        Text _text = new Text(v, v1, s);
        _text.setFont(retrogamingFont);
        return _text;
    }
}
