package TankBattle;

import java.awt.*;

public abstract class GameObject {
    public int x, y, WIDTH, HEIGHT;
    public Rectangle rec = new Rectangle();

    public abstract void paint(Graphics g);
}
