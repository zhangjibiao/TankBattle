import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Tank {
    private static final int SPEED = 10;
    //设置坦克出现屏幕中心
    private int x;
    private int y;

    private Dir dir = Dir.DOWN;
    private Boolean moving = false;
    private TankFrame tf = null;



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
        Color c = g.getColor();
        g.setColor(Color.BLUE);
        move();
        g.fillRect(x,y,20,20);
        g.setColor(c);
    }

    private void move() {
        if(!moving) return;
        //根据方向前进
        switch (dir){
            case LEFT: x -= SPEED; break;
            case RIGHT: x += SPEED; break;
            case UP: y -= SPEED; break;
            case DOWN: y += SPEED; break;
        }
    }

    public void fire() {
        tf.b = new Bullet(this.x, this.y, this.dir);
    }


}
