package Collider;

import TankBattle.*;

public class TankBulletCor implements Collider {
    public boolean collide(GameObject o1, GameObject o2) {
        if ((o1 instanceof Tank && o2 instanceof Bullet) || (o1 instanceof Bullet && o2 instanceof Tank)) {
            Tank t;
            Bullet b;

            //判断o1、o2哪个为坦克
            if (o1 instanceof Tank) {
                t = (Tank) o1;
                b = (Bullet) o2;
            } else {
                t = (Tank) o2;
                b = (Bullet) o1;
            }

            //如果属于我方子弹，没有友方伤害，直接返回
            if (t.getGroup() == b.getGroup()) return true;
            if (t.rec.intersects(b.rec)) {
                new Explode(t.getX() + ResourceMgr.Tank_WIDTH / 2, t.getY() + ResourceMgr.Tank_HEIGHT / 2);
                t.die();
                b.die();
                return false;
            }
        }
        return true;
    }
}
