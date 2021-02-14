package TankBattle;

import java.awt.*;

public abstract class GameObject {
    protected int x, y, WIDTH, HEIGHT;
    public Rectangle rec = new Rectangle();

    abstract void paint(Graphics g);
}
