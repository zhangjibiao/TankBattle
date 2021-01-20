import java.awt.*;

public class Bullet {
    private static int SPEED = 10;
    private static int WIDTH = 30, HEIGHT = 30;
    private int x;
    private int y;

    private boolean live = true;
    TankFrame tf = null;

    public Bullet(int x, int y, Dir dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
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
    }


    public void collideWith(Tank tank) {
        Rectangle rec1 = new Rectangle(x, y, ResourceMgr.BULLET_WIDTH, ResourceMgr.BULLET_HEIGHT);
        Rectangle rec2 = new Rectangle(tank.getX(), tank.getY(), ResourceMgr.Tank_WIDTH, ResourceMgr.Tank_HEIGHT);
        if (rec1.intersects(rec2)) {
            this.die();
            tank.die();
        }
    }

    private void die() {
        live = false;
    }
}
