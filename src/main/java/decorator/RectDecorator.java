package decorator;

import TankBattle.GameObject;

import java.awt.*;

public class RectDecorator implements Decorator {
    public RectDecorator() {
    }

    @Override
    public void decorate(GameObject o, Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.drawRect(o.x, o.y, o.WIDTH, o.HEIGHT);
        g.setColor(c);
    }
}
