package TankBattle;

import java.awt.*;
import java.util.UUID;

public abstract class GameObject {
    public int x, y, WIDTH, HEIGHT;
    public Rectangle rec = new Rectangle();
    public UUID id = UUID.randomUUID();

    public abstract void paint(Graphics g);
}
