import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Tank {
    private static final int SPEED = 10;
    private int x;
    private int y;

    private Dir dir = Dir.DOWN;
    private Boolean moving = false;
    private TankFrame tf = null;
    private Boolean live = true;



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

    public Tank(int x, int y, Dir dir,TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf =tf;
    }

    public Boolean istMoving() {
        return moving;
    }

    public void setMoving(Boolean moving) {
        this.moving = moving;
    }

    public void paint(Graphics g) {
        //根据方向确定图片
        switch(dir){
            case UP: g.drawImage(ResourceMgr.tankU,x,y,null);
                break;
            case DOWN:g.drawImage(ResourceMgr.tankD,x,y,null);
                break;
            case LEFT:g.drawImage(ResourceMgr.tankL,x,y,null);
                break;
            case RIGHT:g.drawImage(ResourceMgr.tankR,x,y,null);
                break;
        }

        move();
    }

    //根据方向前进
    private void move() {
        if(!moving) return;
        switch (dir){
            case LEFT: x -= SPEED; break;
            case RIGHT: x += SPEED; break;
            case UP: y -= SPEED; break;
            case DOWN: y += SPEED; break;
        }
    }


    public void fire() {
        tf.bullets.add(new Bullet(this.x + ResourceMgr.Tank_WIDTH/2 - ResourceMgr.BULLET_WIDTH/2,
                this.y + ResourceMgr.Tank_HEIGHT/2 - ResourceMgr.BULLET_HEIGHT/2, this.dir, this.tf));
    }


    public void die() {
        live = false;
        tf.enemies.remove(this);
    }
}
