package TankBattle;

public class DefaultStrategy implements FireStrategy {
    @Override
    public void fire(GameModel gm, Tank t){
        gm.go.add(new Bullet(t.x + ResourceMgr.Tank_WIDTH / 2 - ResourceMgr.BULLET_WIDTH / 2,
                t.y + ResourceMgr.Tank_HEIGHT/2 - ResourceMgr.BULLET_HEIGHT/2, t.dir, gm, t.group));
    }
}
