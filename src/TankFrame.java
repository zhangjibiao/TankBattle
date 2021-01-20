import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {
    private static final int GAME_WIDTH = 800, GAME_HEIGHT=800;
    Tank mytank = new Tank(400,400,Dir.DOWN, this);
    Bullet b = new Bullet(300, 300, Dir.DOWN);

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
       mytank.paint(g);
       b.paint(g);
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
                    mytank.fire();
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
