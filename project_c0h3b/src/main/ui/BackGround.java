package ui;

import java.awt.*;

public class BackGround {

    public BackGround() {

    }

    public void render(Graphics graphics) {
        graphics.setColor(Color.getHSBColor(16, 51, 43));
        graphics.fillRect(0, Main.HEIGHT * 9 / 10, Main.WIDTH, Main.HEIGHT / 10);
    }
}
