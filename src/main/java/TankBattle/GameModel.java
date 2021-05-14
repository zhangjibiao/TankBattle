package TankBattle;

import Collider.CorChain;
import net.Client;
import net.Msg.TankJoinMsg;
import net.Msg.TankStartMoveMsg;
import net.Msg.TankStopMsg;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class GameModel {
    public static final GameModel INSTANCE = new GameModel();

    private GameModel() {
    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    static Map<UUID, GameObject> go = new HashMap<>();

    static {
        INSTANCE.init();
    }


    public TankFrame tf = null;
    public int TfWidth = Integer.parseInt((String) PropertyMgr.getValue("gameWidth"));
    public int TfHeight = Integer.parseInt((String) PropertyMgr.getValue("gameHeight"));
    public int TfTopHeight;
    public Tank mytank = null;
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
        addGo(mytank);

        //画出墙
        addGo(new Wall(0, 200, 300, 60));
        addGo(new Wall(240, 260, 60, 200));
        addGo(new Wall(600, 30, 60, 150));
    }

    void addGo(GameObject o) {
        go.put(o.id, o);
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
            if (checkTank(t)) addGo(t);
            else i--;
        }
    }

    //检测坦克t1是否与现有的坦克、墙冲突
    //TODO: change to iterator, include other collider
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
        g.setColor(c);

        //碰撞检测
        //Idea： 每个坦克添加bullet数组，如果是自己的bullet则无伤害
        Object[] ids = go.keySet().toArray();
        Object[] id = go.keySet().toArray();

        for (int i = 0; i < ids.length; i++) {
            for (int j = i + 1; j < ids.length; j++) {
                //碰撞检测
                GameObject o1 = go.get(ids[i]);
                GameObject o2 = go.get(ids[j]);
                corchain.collide(o1, o2);
            }
            //画出每一个物体
            go.get(ids[i]).paint(g);
        }
    }


    public void KeyReleased(int key) {
        if (mytank.istMoving() == false) mytank.setMoving(true);//??
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
        //根据键盘布尔值停止移动或改变方向
        if (!BU && !BD && !BL && !BR) {
            mytank.setMoving(false);
            Client.INSTANCE.send(new TankStopMsg(mytank));
        }
    }

//    private void setdir() {
//        if (!BU && !BD && !BL && !BR) {
//            mytank.setMoving(false);
//            //Client.INSTANCE.send(new TankStopMsg(mytank));
//        }
//        else {
//            mytank.setMoving(true);
//
//            if (BU) mytank.setDir(Dir.UP);
//            if (BD) mytank.setDir(Dir.DOWN);
//            if (BR) mytank.setDir(Dir.RIGHT);
//            if (BL) mytank.setDir(Dir.LEFT);
//        }
//    }

    public void keyPressed(int key) {
        switch (key) {
            case (KeyEvent.VK_LEFT):
                BL = true;
                mytank.setDir(Dir.LEFT);
                mytank.setMoving(true);
                break;
            case (KeyEvent.VK_RIGHT):
                BR = true;
                mytank.setDir(Dir.RIGHT);
                mytank.setMoving(true);
                break;
            case (KeyEvent.VK_UP):
                BU = true;
                mytank.setDir(Dir.UP);
                mytank.setMoving(true);
                break;
            case (KeyEvent.VK_DOWN):
                BD = true;
                mytank.setDir(Dir.DOWN);
                mytank.setMoving(true);
                break;
            default:
                break;
        }

        Client.INSTANCE.send(new TankStartMoveMsg(mytank));
    }

    public void newTankJoin(TankJoinMsg msg) {
        addGo(new Tank(msg));
    }

    public GameObject findByUUID(UUID id) {
        return go.get(id);
    }
}
