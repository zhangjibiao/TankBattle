import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    int initEnemies = 5;
    //键盘布尔值
    boolean BL = false;
    boolean BR = false;
    boolean BU = false;
    boolean BD = false;

    Tank mytank = new Tank(400,400,Dir.DOWN, this, Group.Good);
    List<Tank> enemies =new ArrayList<>();
    List<Bullet> bullets = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();
    TankFrame tf = null;

    {
        //画出敌军坦克
        initEnemies = Integer.parseInt((String) PropertyMgr.getvalue("initEnemies")) ;
        for(int i=0; i<=initEnemies-1; i++)
        {
            enemies.add(new Tank(100 + 100 * i, 200, Dir.DOWN, this, Group.Bad));
            enemies.get(i).setMoving(true);
        }
    }

    public GameModel(TankFrame tf) {
        this.tf = tf;
    }

    public void paint(Graphics g) {
        //显示子弹，敌人数量
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量:" + bullets.size(), 10, 60);
        g.drawString("敌人的数量:" + enemies.size(), 10, 100);
        g.drawString("爆炸的数量:" + explodes.size(), 10, 140);
        g.setColor(c);

        //碰撞检测
        //Idea： 每个坦克添加bullet数组，如果是自己的bullet则无伤害
        for(int i=0; i<bullets.size();i++){
            for(int j=0; j<enemies.size();j++){
                bullets.get(i).collideWith(enemies.get(j),g, this);
            }
        }
        //画出爆炸效果
        for(int i=0; i<explodes.size();i++){
            explodes.get(i).paint(g);
        }

        //画出主战坦克、子弹，敌军坦克
        mytank.paintmytank(g);
        for(int i=0; i<bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        for(int i=0; i<enemies.size();i++){
            enemies.get(i).autodrive();
            enemies.get(i).autofire(Group.Bad);
            enemies.get(i).paintenemies(g);
        }
    }

    public Tank getmytank() {
            return mytank;
    }

    public void KeyReleased(int key) {
        switch(key){
            case(KeyEvent.VK_LEFT):  BL = false; break;
            case(KeyEvent.VK_RIGHT): BR = false; break;
            case(KeyEvent.VK_UP):  BU = false; break;
            case(KeyEvent.VK_DOWN):  BD = false; break;
            case KeyEvent.VK_CONTROL:
                mytank.fire(Group.Good);
                break;
            default:break;
        }
        //根据键盘布尔值改变方向
        setdir();
    }

    private void setdir() {
        if(!BU && !BD && !BL && !BR) mytank.setMoving(false);
        else{
            mytank.setMoving(true);

            if(BU) mytank.setDir(Dir.UP);
            if(BD) mytank.setDir(Dir.DOWN);
            if(BR) mytank.setDir(Dir.RIGHT);
            if(BL) mytank.setDir(Dir.LEFT);
        }
    }

    public void keyPressed(int key) {
        switch(key){
            case(KeyEvent.VK_LEFT):  BL = true; break;
            case(KeyEvent.VK_RIGHT): BR = true; break;
            case(KeyEvent.VK_UP):  BU = true; break;
            case(KeyEvent.VK_DOWN):  BD = true; break;
            default:break;
        }

        //根据键盘布尔值改变方向
        setdir();
        //加入坦克移动的音效
        //new Thread(()->new Audio("audio/tank_move.wav").play()).start();
    }
}
