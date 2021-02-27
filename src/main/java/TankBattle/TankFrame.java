package TankBattle;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {
    GameModel gm = GameModel.getInstance();


    static final int GAME_WIDTH = Integer.parseInt((String) PropertyMgr.getValue("gameWidth"));
    static final int GAME_HEIGHT = Integer.parseInt((String) PropertyMgr.getValue("gameHeight"));


    public TankFrame(){

        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("Tank Battle");
        setVisible(true);
        gm.tf = this;
        gm.TfWidth = GAME_WIDTH;
        gm.TfHeight = GAME_HEIGHT;
        gm.TfTopHeight = getInsets().top;

        //设置窗口监听器，使窗口能被用户关闭
        addWindowListener(new WindowAdapter() { //匿名内部类
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }});
        addKeyListener(new MyKeyListener());
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
        gm.paint(g);
    }

    class MyKeyListener extends KeyAdapter{
        @Override
        //根据按键方向使坦克前进
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode(); //获取被按下的键
            gm.keyPressed(key);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode(); //获取被按下的键
            gm.KeyReleased(key);
        }
    }
}
