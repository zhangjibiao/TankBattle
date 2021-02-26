package Collider;

import TankBattle.GameObject;
import TankBattle.Tank;
import TankBattle.Wall;

public class TankWallCor implements Collider {
    public boolean collide(GameObject o1, GameObject o2) {
        if ((o1 instanceof Tank && o2 instanceof Wall) || (o1 instanceof Wall && o2 instanceof Tank)) {
            Tank t;

            if (o1 instanceof Tank) {
                t = (Tank) o1;
            } else {
                t = (Tank) o2;
            }

            if (o1.rec.intersects(o2.rec)) {
                t.back();
                return true;
            }

        }
        return true;
    }
}
