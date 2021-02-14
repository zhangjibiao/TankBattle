package TankBattle;

import java.awt.*;

public class Wall extends GameObject {
    public Wall(int x, int y, int WIDTH, int HEIGHT) {
        super.x = x;
        super.y = y;
        super.WIDTH = WIDTH;
        super.HEIGHT = HEIGHT;
        rec.setBounds(x, y, WIDTH, HEIGHT);
    }

    @Override
    void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(c);
    }
}
