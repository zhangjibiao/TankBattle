import java.awt.*;
import java.awt.image.BufferedImage;

public class Explode {
    private int x;
    private int y;
    private int step = 0;
    private  GameModel gm;

    public Explode(int x, int y, GameModel gm) {
        this.gm =gm;
        //根据爆炸坐标计算图片左上角坐标
        this.x = x-ResourceMgr.Explode_WIDTH/2;
        this.y = y-ResourceMgr.Explode_HEIGHT/2;
    }

    public void paint(Graphics g){
        //for(BufferedImage img:img){
         //   g.drawImage(img, x, y,null);
        g.drawImage(ResourceMgr.Explode[step++], x, y, null);
        if(step >= ResourceMgr.Explode.length)
            gm.explodes.remove(this);
        }

}
