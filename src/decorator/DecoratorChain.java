package decorator;

import TankBattle.GameObject;

import java.awt.*;
import java.util.ArrayList;

public class DecoratorChain implements Decorator {
    public ArrayList<Decorator> chain = new ArrayList();

    {
        add(new RectDecorator());
    }

    public void DecoratorChain() {
    }

    public void add(Decorator d) {
        chain.add(d);
    }

    @Override
    public void decorate(GameObject o, Graphics g) {
        for (int i = 0; i < chain.size(); i++) {
            chain.get(i).decorate(o, g);
        }
    }
}
