package TankBattle;

//Ctrl 开火，方向键控制方向


public class TankBattle {
    public static void start() {
        TankFrame f = new TankFrame();

        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            f.repaint();
        }
    }
}
