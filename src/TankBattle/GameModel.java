package TankBattle;

import Collider.CorChain;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    public static final GameModel INSTANCE = new GameModel();

    private GameModel() {
    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    static List<GameObject> go = new ArrayList();

    static {
        INSTANCE.init();
    }

    public TankFrame tf = null;
    Tank mytank = null;
    int initEnemies = 5;
    //键盘布尔值
    boolean BL = false;
    boolean BR = false;
    boolean BU = false;
    boolean BD = false;
    public CorChain corchain = new CorChain();

    private void init() {
        mytank = new Tank(400, 400, Group.Good);
        go.add(mytank);
        //画出敌军坦克
        initEnemies = Integer.parseInt((String) PropertyMgr.getvalue("initEnemies")) ;
        for(int i = 0; i<=initEnemies-1; i++) {
            go.add(new Tank(100 + 100 * i, 200, Group.Bad));
        }
    }

    public void paint(Graphics g) {
        //显示子弹，敌人数量
        Color c = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("子弹的数量:" + bullets.size(), 10, 60);
//        g.drawString("敌人的数量:" + enemies.size(), 10, 100);
//        g.drawString("爆炸的数量:" + explodes.size(), 10, 140);
        g.setColor(c);

        //碰撞检测
        //Idea： 每个坦克添加bullet数组，如果是自己的bullet则无伤害
        for (int i = 0; i < go.size(); i++) {
            for (int j = i + 1; j < go.size(); j++) {
                //碰撞检测
                GameObject o1 = go.get(i);
                GameObject o2 = go.get(j);
                corchain.collide(o1, o2);
            }
            //画出每一个物体
            go.get(i).paint(g);
        }

    }


    public void KeyReleased(int key) {
        switch(key){
            case(KeyEvent.VK_LEFT):  BL = false; break;
            case(KeyEvent.VK_RIGHT): BR = false; break;
            case(KeyEvent.VK_UP):  BU = false; break;
            case(KeyEvent.VK_DOWN):  BD = false; break;
            case KeyEvent.VK_CONTROL:
                if (mytank.live == true) mytank.fire();
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