import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;//awt 基本没用

public class Tank {
    public static void main(String[] args) {
        Frame f = new Frame();
        f.setSize(800,400);
        f.setResizable(false);
        f.setTitle("Tank Battle");
        //设置窗口监听器，使窗口能被用户关闭
        f.addWindowListener( new WindowAdapter() { //匿名内部类
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        f.setVisible(true);
    }
}
