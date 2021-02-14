package TankBattle;

import decorator.DecoratorChain;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {
    private static final int SPEED = Integer.parseInt((String)PropertyMgr.getvalue("bulletSpeed"));
    public static DecoratorChain chain = new DecoratorChain();

    private Group group;
    private boolean live = true;
    private Dir dir;
    public BufferedImage img;

    public Bullet(int x, int y, Dir dir, Group group) { // x， y为子弹尾部中心的坐标
        this.dir = dir;
        this.group = group;
        switch (dir) {
            case UP:
                img = ResourceMgr.bulletU;
                WIDTH = ResourceMgr.BULLET_WIDTH;
                HEIGHT = ResourceMgr.BULLET_HEIGHT;
                this.x = x - WIDTH / 2;
                this.y = y - HEIGHT;
                break;
            case DOWN:
                img = ResourceMgr.bulletD;
                WIDTH = ResourceMgr.BULLET_WIDTH;
                HEIGHT = ResourceMgr.BULLET_HEIGHT;
                this.x = x - WIDTH / 2;
                this.y = y + HEIGHT;
                break;
            case LEFT:
                img = ResourceMgr.bulletL;
                WIDTH = ResourceMgr.BULLET_HEIGHT;
                HEIGHT = ResourceMgr.BULLET_WIDTH;
                this.x = x - WIDTH;
                this.y = y - HEIGHT / 2;
                break;
            case RIGHT:
                img = ResourceMgr.bulletR;
                WIDTH = ResourceMgr.BULLET_HEIGHT;
                HEIGHT = ResourceMgr.BULLET_WIDTH;
                this.x = x + WIDTH;
                this.y = y - HEIGHT / 2;
                break;
        }
    }

    public Group getGroup() {
        return group;
    }

    public void paint(Graphics g) {
        if (!live) {
            GameModel.getInstance().go.remove(this);
            return;
        }
        g.drawImage(img, x, y, null); // 画出子弹
        chain.decorate(this, g); // 画出装饰
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
        if (x < 0 || y < 0 || x > GameModel.getInstance().TfWidth || y > GameModel.getInstance().TfHeight)
            live = false;

        rec.setBounds(x, y, WIDTH, HEIGHT);
    }

    public void die() {
        live = false;
    }
}
