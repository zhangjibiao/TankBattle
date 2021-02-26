package decorator;

import TankBattle.GameObject;

import java.awt.*;

public interface Decorator {
    public abstract void decorate(GameObject o1, Graphics g);
}
