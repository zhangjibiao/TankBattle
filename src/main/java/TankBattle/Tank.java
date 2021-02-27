package TankBattle;

import net.TankJoinMsg;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

public class Tank extends GameObject {
    private static final int SPEED = Integer.parseInt((String) PropertyMgr.getValue("tankSpeed"));

    Dir dir;
    public int oldX, oldY;
    private boolean moving = false;
    public GameModel gm = GameModel.getInstance();
    public boolean live = true;
    Group group;
    FireStrategy fs;


    {
        WIDTH = ResourceMgr.Tank_WIDTH;
        HEIGHT = ResourceMgr.Tank_HEIGHT;
    }

    public Tank(int x, int y, Group group, Dir dir) {
        this.x = x;
        this.y = y;
        oldX = x;
        oldY = y;
        this.group = group;
        this.dir = dir;

        if (group == Group.Bad) fs = new DefaultStrategy();
        else fs = new FourDirStrategy();

        rec.setBounds(x, y, ResourceMgr.Tank_WIDTH, ResourceMgr.Tank_HEIGHT);
    }

    public Tank(TankJoinMsg msg) {
        this.x = msg.x;
        this.y = msg.y;
        oldX = msg.x;
        oldY = msg.y;
        this.group = msg.group;
        this.id = msg.id;

        if (group == Group.Bad) fs = new DefaultStrategy();
        else fs = new FourDirStrategy();

        rec.setBounds(x, y, ResourceMgr.Tank_WIDTH, ResourceMgr.Tank_HEIGHT);

        this.dir = msg.dir;
        this.moving = msg.moving;
        this.live = msg.live;
    }

    public UUID getId() {
        return id;
    }

    public Boolean getMoving() {
        return moving;
    }

    public Group getGroup() {
        return group;
    }

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

    public Boolean istMoving() {
        return moving;
    }

    public void setMoving(Boolean moving) {
        this.moving = moving;
    }

    public void paint(Graphics g) {
        //在坦克上方显示UUID
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawString(id.toString(), x, y - 10);
        g.setColor(c);

        if (group == Group.Good) {
            switch (dir) {
                case UP:
                    g.drawImage(ResourceMgr.mytankU1, x, y, null);
                    break;
                case DOWN:
                    g.drawImage(ResourceMgr.mytankD1, x, y, null);
                    break;
                case LEFT:
                    g.drawImage(ResourceMgr.mytankL1, x, y, null);
                    break;
                case RIGHT:
                    g.drawImage(ResourceMgr.mytankR1, x, y, null);
                    break;
            }
        } else {
            //敌军自动开火、移动
            autodrive();
            autofire();
            //根据方向确定图片
            switch (dir) {
                case UP:
                    g.drawImage(ResourceMgr.tankU1, x, y, null);
                    break;
                case DOWN:
                    g.drawImage(ResourceMgr.tankD1, x, y, null);
                    break;
                case LEFT:
                    g.drawImage(ResourceMgr.tankL1, x, y, null);
                    break;
                case RIGHT:
                    g.drawImage(ResourceMgr.tankR1, x, y, null);
                    break;
            }
        }
        move();
        //更新方块
        rec.setBounds(x, y, ResourceMgr.Tank_WIDTH, ResourceMgr.Tank_HEIGHT);
    }

    //根据方向前进
    private void move() {
        oldX = x;
        oldY = y;
        if (!moving) return;
        switch (dir) {
            case LEFT: {
                if (x > 5)
                    x -= SPEED;
                break;
            }
            case RIGHT: {
                if (x < gm.TfWidth - WIDTH)
                    x += SPEED;
                break;
            }
            case UP: {
                if (y > gm.TfTopHeight)
                    y -= SPEED;
                break;
            }
            case DOWN: {
                if (y < gm.TfHeight - HEIGHT)
                    y += SPEED;
                break;
            }
        }
    }


    public void fire() {
        fs.fire(this);
        //TODO：内存泄漏
        //if(this.group == Group.Good) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
    }


    public void die() {
        live = false;
        gm.go.remove(this);
    }

    // 敌军坦克，自动开火
    public void autodrive() {
        if (moving == false) moving = true;
        if (new Random().nextInt(10) > 8) { //3/10 的概率改变方向
            switch (new Random().nextInt(4)) { //随机改变方向
                case 0:
                    this.dir = Dir.UP;
                    break;
                case 1:
                    this.dir = Dir.DOWN;
                    break;
                case 2:
                    this.dir = Dir.LEFT;
                    break;
                case 3:
                    this.dir = Dir.RIGHT;
                    break;
            }
        }
    }

    public void autofire() {
        if (new Random().nextInt(100) > 95) { // 1/10 的概率开火
            this.fire();
        }
    }

    public void back() {
        x = oldX;
        y = oldY;
    }
}
