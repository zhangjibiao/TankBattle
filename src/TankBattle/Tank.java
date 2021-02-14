package TankBattle;

import java.awt.*;
import java.util.Random;

public class Tank extends GameObject {
    private static final int SPEED = Integer.parseInt((String)PropertyMgr.getvalue("tankSpeed"));

    Dir dir;
    public int oldX, oldY;
    private Boolean moving = true;
    public GameModel gm = GameModel.getInstance();
    public Boolean live = true;
    Group group ;
    FireStrategy fs;

    {
        WIDTH = ResourceMgr.Tank_WIDTH;
        HEIGHT = ResourceMgr.Tank_HEIGHT;
    }

    public Tank(int x, int y, Group group) {
        this.x = x;
        this.y = y;
        oldX = x;
        oldY = y;
        this.group = group;

        if (group == Group.Bad) fs = new DefaultStrategy();
        else fs = new FourDirStrategy();

        rec.setBounds(x, y, ResourceMgr.Tank_WIDTH, ResourceMgr.Tank_HEIGHT);
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
        if (group == Group.Good) {
            if (dir == null) {
                g.drawImage(ResourceMgr.mytankU1, x, y, null);
                return;
            }
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
        if(!moving) return;
        switch (dir){
            case LEFT: {
                if (x > 5)
                    x -= SPEED;
                break;}
            case RIGHT:{
                if (x < gm.TfWidth - WIDTH)
                    x += SPEED;
                break;}
            case UP:{
                if (y > gm.TfTopHeight)
                    y -= SPEED;
                break;}
            case DOWN:{
                if (y < gm.TfHeight - HEIGHT)
                    y += SPEED;
                break;}
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
        if (dir == null) dir = Dir.DOWN;
        if(new Random().nextInt(10) > 8){ //3/10 的概率改变方向
            switch (new Random().nextInt(4)){ //随机改变方向
                case 0: this.dir = Dir.UP; break;
                case 1: this.dir = Dir.DOWN; break;
                case 2: this.dir = Dir.LEFT; break;
                case 3: this.dir = Dir.RIGHT; break;
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
