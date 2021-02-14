package Collider;

import TankBattle.Bullet;
import TankBattle.GameObject;
import TankBattle.Wall;

public class WallBulletCor implements Collider {
    public boolean collide(GameObject o1, GameObject o2) {
        if ((o1 instanceof Wall && o2 instanceof Bullet) || (o1 instanceof Bullet && o2 instanceof Wall)) {

            if (o1.rec.intersects(o2.rec)) {//如果相撞

                //判断o1、o2哪个为子弹
                if (o1 instanceof Bullet) {
                    ((Bullet) o1).die();
                    return false;
                } else {
                    ((Bullet) o2).die();
                    return true;
                }
            }

            return true; //没撞上
        }

        return true; //不是子弹和墙
    }
}
