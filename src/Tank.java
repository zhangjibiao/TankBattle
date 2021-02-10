import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class Tank {
    private static final int SPEED = Integer.parseInt((String)PropertyMgr.getvalue("tankSpeed"));

    private int x;
    private int y;

    private Dir dir = Dir.DOWN;

    public Boolean getMoving() {
        return moving;
    }

    private Boolean moving = false;
    private TankFrame tf = null;
    private Boolean live = true;
    private Group group ;

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

    public Tank(int x, int y, Dir dir,TankFrame tf,Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf =tf;
        this.group = group;
    }

    public Boolean istMoving() {
        return moving;
    }

    public void setMoving(Boolean moving) {
        this.moving = moving;
    }

    public void paintenemies(Graphics g){
        //根据方向确定图片
        switch(dir){
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
        move();
    }

    public void paintmytank(Graphics g) {
        //根据方向确定图片
        switch(dir){
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
        move();
    }

    //根据方向前进
    private void move() {
        if(!moving) return;
        switch (dir){
            //TODO:优化边缘检测
            case LEFT: {
                if(x > 0)
                    x -= SPEED;
                break;}
            case RIGHT:{
                if(x < TankFrame.GAME_WIDTH-ResourceMgr.Tank_WIDTH)
                    x += SPEED;
                break;}
            case UP:{
                if(y > tf.getInsets().top-tf.getInsets().bottom)
                    y -= SPEED;
                break;}
            case DOWN:{
                if(y < TankFrame.GAME_HEIGHT-ResourceMgr.Tank_HEIGHT)
                    y += SPEED;
                break;}
        }
    }


    public void fire(Group group) {
        tf.bullets.add(new Bullet(this.x + ResourceMgr.Tank_WIDTH/2 - ResourceMgr.BULLET_WIDTH/2,
                this.y + ResourceMgr.Tank_HEIGHT/2 - ResourceMgr.BULLET_HEIGHT/2, this.dir, this.tf, group));
        //TODO：内存泄漏
        //if(this.group == Group.Good) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();

    }


    public void die() {
        live = false;
        tf.enemies.remove(this);
    }

    // 敌军坦克，自动开火
    public void autodrive() {
        if(new Random().nextInt(10) > 8){ //3/10 的概率改变方向
            switch (new Random().nextInt(4)){ //随机改变方向
                case 0: this.dir = Dir.UP; break;
                case 1: this.dir = Dir.DOWN; break;
                case 2: this.dir = Dir.LEFT; break;
                case 3: this.dir = Dir.RIGHT; break;
            }
        }


    }

    public void autofire(Group group) {
        if (new Random().nextInt(100) > 95) { // 1/10 的概率开火
            this.fire(group);
        }
    }
}
