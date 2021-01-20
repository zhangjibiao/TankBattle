import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {
    private static final int SPEED = 10;
    //设置坦克出现屏幕中心
    private int x= 800/2;
    private int y= 800/2;
    private Dir dir = Dir.DOWN;

    //键盘布尔值
    boolean BL = false;
    boolean BR = false;
    boolean BU = false;
    boolean BD = false;


    public TankFrame(){


        setSize(800,800);
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

    @Override
    public void paint(Graphics g) {
        //根据键盘布尔值确定方向
        if(BL) x -= 10;
        if(BR) x += 10;
        if(BU) y -= 10;
        if(BD) y += 10;
        g.fillRect(x,y,20,20);
    }

    class MyKeyListener extends KeyAdapter{

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
                default:break;
            }
            //根据键盘布尔值改变方向
            setdir();
        }

        private void setdir() {
            if(!BU && !BD && !BL && !BR) ;
            else{
                if(BU) y -= SPEED;
                if(BD) y += SPEED;
                if(BR) x += SPEED;
                if(BL) x -= SPEED;
            }
        }
    }
}
