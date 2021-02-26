package Collider;

import TankBattle.GameObject;

public interface Collider {
    public abstract boolean collide(GameObject o1, GameObject o2);
}
