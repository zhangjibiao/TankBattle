package Collider;

import TankBattle.*;

public class TankTankCor implements Collider {
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Tank) {
            Tank t1 = (Tank) o1;
            Tank t2 = (Tank) o2;


            if (t1.rec.intersects(t2.rec)) {
                if (t1.getGroup() == t2.getGroup()) {
                    t1.back();
                    t2.back();
                    return true;
                } else if (t1.getGroup() == Group.Good) { //t1是主站坦克，t2是敌军
                    t1.die();
                    new Explode(t1.getX() + ResourceMgr.Tank_WIDTH / 2, t1.getY() + ResourceMgr.Tank_HEIGHT / 2);
                    return false;
                } else { //t1是敌军，t2是主站坦克
                    t2.die();
                    new Explode(t2.getX() + ResourceMgr.Tank_WIDTH / 2, t2.getY() + ResourceMgr.Tank_HEIGHT / 2);
                    return true;
                }
            }
        }
        return true;
    }
}
