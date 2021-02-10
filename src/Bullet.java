import java.awt.*;

public class Bullet {
    private static final int SPEED = Integer.parseInt((String)PropertyMgr.getvalue("bulletSpeed"));
    private static int WIDTH = 30, HEIGHT = 30;
    private int x;
    private int y;
    private Group group;
    Rectangle rec1 = new Rectangle();
    Rectangle rec2 = new Rectangle();


    private boolean live = true;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    TankFrame tf = null; //获取其引用

    public Bullet(int x, int y, Dir dir, TankFrame tf, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
    }

    private Dir dir;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void paint(Graphics g) {
        if (!live) {
            tf.bullets.remove(this);
        }
        switch (dir) {
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
        }

        move();

    }

    private void move() {
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;

        }
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) live = false;

        rec1.setBounds(x, y, ResourceMgr.BULLET_WIDTH, ResourceMgr.BULLET_HEIGHT);
    }


    public void collideWith(Tank tank, Graphics g, TankFrame tf) {
        //如果属于我方子弹，没有友方伤害，直接返回
        if(this.group == tank.getGroup()) return;

        rec2.setBounds(tank.getX(), tank.getY(), ResourceMgr.Tank_WIDTH, ResourceMgr.Tank_HEIGHT);
        if (rec1.intersects(rec2)) {

            tf.explodes.add(new Explode(tank.getX()+ResourceMgr.Tank_WIDTH/2,tank.getY()+ResourceMgr.Tank_HEIGHT/2, tf));
            this.die();
            tank.die();
        }
    }

    private void die() {
        live = false;
    }
}
