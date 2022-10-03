package uet.oop.bomberman.ui;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameText extends Text {
    GameText(String s, Font font) {
        super(s);
        setFont(font);
    }

    GameText(double v1, double v2, String s, Font font) {
        super(v1, v2, s);
        setFont(font);
    }
}
