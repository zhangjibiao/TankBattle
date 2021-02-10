public class FourDirStrategy implements FireStrategy {
    @Override
    public void fire(TankFrame tf, Tank t){
        Dir[] dirs = Dir.values();
        for(Dir dir: dirs){
            tf.bullets.add(new Bullet(t.x + ResourceMgr.Tank_WIDTH/2 - ResourceMgr.BULLET_WIDTH/2,
                    t.y + ResourceMgr.Tank_HEIGHT/2 - ResourceMgr.BULLET_HEIGHT/2, dir, tf, t.group));

        }

    }
}
