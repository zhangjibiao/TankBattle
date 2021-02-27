package TankBattle;

import java.awt.*;

public class Explode extends GameObject {
    {
        WIDTH = ResourceMgr.Explode_WIDTH;
        HEIGHT = ResourceMgr.Explode_HEIGHT;
    }

    private int step = 0;
    public GameModel gm = GameModel.getInstance();

    public Explode(int x, int y) {
        //根据爆炸坐标计算图片左上角坐标
        this.x = x - WIDTH / 2;
        this.y = y - HEIGHT / 2;
        gm.addGo(this);
    }

    public void paint(Graphics g) {
        //for(BufferedImage img:img){
        //   g.drawImage(img, x, y,null);
        g.drawImage(ResourceMgr.Explode[step++], x, y, null);
        if (step >= ResourceMgr.Explode.length)
            gm.go.remove(this);
    }

}
