package uet.oop.bomberman.ui;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameText extends Text {
    private String format;
    GameText(String s, Font font) {
        super(s);
        setFont(font);
    }

    GameText(double v1, double v2, Font font, String format, Object... args) {
        super(v1, v2, String.format(format, args));
        setFormat(format);
        setFont(font);
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void updateText(Object... args) {
        super.setText(String.format(this.format, args));
    }
}
