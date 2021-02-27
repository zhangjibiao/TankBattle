package TankBattle;

public class FourDirStrategy implements FireStrategy {
    @Override
    public void fire(Tank t) {
        Dir[] dirs = Dir.values();
        for (Dir dir : dirs) {
            GameModel.getInstance().addGo(new Bullet(t.x + ResourceMgr.Tank_WIDTH / 2 - ResourceMgr.BULLET_WIDTH / 2,
                    t.y + ResourceMgr.Tank_HEIGHT / 2 - ResourceMgr.BULLET_HEIGHT / 2, dir, t.group));

        }

    }
}
