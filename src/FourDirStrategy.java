public class FourDirStrategy implements FireStrategy {
    @Override
    public void fire(GameModel gm, Tank t){
        Dir[] dirs = Dir.values();
        for(Dir dir: dirs){
            gm.bullets.add(new Bullet(t.x + ResourceMgr.Tank_WIDTH/2 - ResourceMgr.BULLET_WIDTH/2,
                    t.y + ResourceMgr.Tank_HEIGHT/2 - ResourceMgr.BULLET_HEIGHT/2, dir, gm, t.group));

        }

    }
}
