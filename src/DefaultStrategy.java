public class DefaultStrategy implements FireStrategy {
    @Override
    public void fire(TankFrame tf, Tank t){
        tf.bullets.add(new Bullet(t.x + ResourceMgr.Tank_WIDTH/2 - ResourceMgr.BULLET_WIDTH/2,
                t.y + ResourceMgr.Tank_HEIGHT/2 - ResourceMgr.BULLET_HEIGHT/2, t.dir, tf, t.group));
    }
}