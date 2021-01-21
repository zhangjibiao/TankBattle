import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
    int initEnemies = 5;



    static final int GAME_WIDTH = 800, GAME_HEIGHT=800;
    Tank mytank = new Tank(400,400,Dir.DOWN, this, Group.Good);
    List<Tank> enemies =new ArrayList<>();
    List<Bullet> bullets = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();

    public TankFrame(){

        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("Tank Battle");
        setVisible(true);

        //设置窗口监听器，使窗口能被用户关闭
        addWindowListener( new WindowAdapter() { //匿名内部类
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }});
        addKeyListener(new MyKeyListener());


        initEnemies = Integer.parseInt((String) PropertyMgr.getvalue("initEnemies")) ;
        for(int i=0; i<=initEnemies-1; i++){
            enemies.add(new Tank(100+100*i, 200, Dir.DOWN, this, Group.Bad));
            enemies.get(i).setMoving(true);
        }
    }

    // 解决双缓存问题
    Image offScreenImage = null; //定义一个Image
    @Override
    public void update(Graphics g) {    //调用repaint方法时，会先调用update
        if(offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
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

    class MyKeyListener extends KeyAdapter{
        //键盘布尔值
        boolean BL = false;
        boolean BR = false;
        boolean BU = false;
        boolean BD = false;

        @Override
        //根据按键方向使坦克前进
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode(); //获取被按下的键
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

        //
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode(); //获取被按下的键
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
    }
}
