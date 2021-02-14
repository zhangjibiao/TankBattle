package Collider;

import TankBattle.GameObject;

import java.util.ArrayList;

public class CorChain implements Collider {
    public ArrayList<Collider> chain = new ArrayList();

    {
        add(new TankTankCor());
        add(new TankBulletCor());

    }

    public void CorChain() {
    }

    public void add(Collider c) {
        chain.add(c);
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        for (int i = 0; i < chain.size(); i++) {
            if (!chain.get(i).collide(o1, o2))
                return false; //表明o1在碰撞过程中死亡，不需要继续碰撞
        }
        return true;//完成全部碰撞检测责任链
    }
}
