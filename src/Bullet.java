import java.awt.*;

public class Bullet {
    private static int SPEED = 10;
    private static int WIDTH = 30, HEIGHT = 30;
    private int x;
    private int y;

    public Bullet(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
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

    public void paint(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.RED);
        move();
        g.fillOval(x,y,WIDTH,HEIGHT);
        g.setColor(c);
    }

    private void move() {
        switch(dir){
            case LEFT: x -= SPEED; break;
            case RIGHT: x += SPEED; break;
            case UP: y -= SPEED; break;
            case DOWN: y += SPEED; break;

        }
    }


}
