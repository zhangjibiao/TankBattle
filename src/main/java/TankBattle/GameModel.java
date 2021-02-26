package TankBattle;

import Collider.CorChain;
import net.Client;
import net.TankJoinMsg;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    public static final GameModel INSTANCE = new GameModel();

    private GameModel() {
    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE.init();
    }

    List<GameObject> go = new ArrayList();
    public TankFrame tf = null;
    public int TfWidth = Integer.parseInt((String) PropertyMgr.getValue("gameWidth"));
    public int TfHeight = Integer.parseInt((String) PropertyMgr.getValue("gameHeight"));
    public int TfTopHeight;
    public Tank mytank = null;
    Client client = null;
    int initEnemies = 5;
    //键盘布尔值
    boolean BL = false;
    boolean BR = false;
    boolean BU = false;
    boolean BD = false;
    public CorChain corchain = new CorChain();

    private void init() {
        //画出我军坦克
//        mytank = new Tank(400, 400, Group.Good);
        Random r = new Random();
        mytank = new Tank(r.nextInt(TfWidth), r.nextInt(TfHeight), Group.Good, Dir.UP);
        go.add(mytank);

        //画出墙
        go.add(new Wall(0, 200, 300, 60));
        go.add(new Wall(240, 260, 60, 200));
        go.add(new Wall(600, 30, 60, 150));

        //画出敌军坦克
//        addEnemies()
        new Thread(() -> {
            client = new Client();
            client.connect();
        }).start();

        ;

    }

    private void addEnemies() {
        initEnemies = Integer.parseInt((String) PropertyMgr.getValue("initEnemies"));
        Random r = new Random();
        Tank t;
        for (int i = 0; i <= initEnemies - 1; i++) {
            t = new Tank(
                    r.nextInt(Integer.parseInt((String) PropertyMgr.getValue("gameWidth")) - mytank.WIDTH),
                    r.nextInt(Integer.parseInt((String) PropertyMgr.getValue("gameHeight")) - mytank.HEIGHT),
                    Group.Bad,
                    Dir.DOWN);
            if (checkTank(t)) go.add(t);
            else i--;
        }
    }

    //检测坦克t1是否与现有的坦克、墙冲突
    private boolean checkTank(Tank t) {
        GameObject o;
        for (int i = 0; i < go.size(); i++) {
            o = go.get(i);
            if (o instanceof Tank || o instanceof Wall) {
                if (t.rec.intersects(o.rec)) return false;
            }
        }
        return true;
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
        if (mytank.istMoving() == false) mytank.setMoving(true);
        switch (key) {
            case (KeyEvent.VK_LEFT):
                BL = false;
                break;
            case (KeyEvent.VK_RIGHT):
                BR = false;
                break;
            case (KeyEvent.VK_UP):
                BU = false;
                break;
            case (KeyEvent.VK_DOWN):
                BD = false;
                break;
            case KeyEvent.VK_CONTROL:
                if (mytank.live == true) mytank.fire();
                break;
            default:
                break;
        }
        //根据键盘布尔值改变方向
        setdir();
    }

    private void setdir() {
        if (!BU && !BD && !BL && !BR) mytank.setMoving(false);
        else {
            mytank.setMoving(true);

            if (BU) mytank.setDir(Dir.UP);
            if (BD) mytank.setDir(Dir.DOWN);
            if (BR) mytank.setDir(Dir.RIGHT);
            if (BL) mytank.setDir(Dir.LEFT);
        }
    }

    public void keyPressed(int key) {
        switch (key) {
            case (KeyEvent.VK_LEFT):
                BL = true;
                break;
            case (KeyEvent.VK_RIGHT):
                BR = true;
                break;
            case (KeyEvent.VK_UP):
                BU = true;
                break;
            case (KeyEvent.VK_DOWN):
                BD = true;
                break;
            default:
                break;
        }

        //根据键盘布尔值改变方向
        setdir();
        //加入坦克移动的音效
        //new Thread(()->new Audio("audio/tank_move.wav").play()).start();
    }

    public void newTankJoin(TankJoinMsg msg) {
        go.add(new Tank(msg));
    }
}
