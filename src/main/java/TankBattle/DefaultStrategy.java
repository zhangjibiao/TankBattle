package TankBattle;

public class DefaultStrategy implements FireStrategy {
    @Override
    public void fire(Tank t) {
        GameModel.getInstance().go.add(
                new Bullet(t.x + ResourceMgr.Tank_WIDTH / 2,
                        t.y + ResourceMgr.Tank_HEIGHT / 2,
                        t.dir,
                        t.group));
    }
}
