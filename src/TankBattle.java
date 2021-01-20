import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;//awt 基本没用

public class TankBattle {
    public static void main(String[] args) throws InterruptedException {
        TankFrame f = new TankFrame();
        while(true){
            Thread.sleep(50);
            f.repaint();
        }

    }
}
