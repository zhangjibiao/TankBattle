package TankBattle;

//Ctrl 开火，方向键控制方向


public class TankBattle {
    public static void main(String[] args) throws InterruptedException {
        TankFrame f = new TankFrame();
        while(true){
            Thread.sleep(50);
            f.repaint();
        }

    }
}
