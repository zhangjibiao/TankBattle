import java.awt.*;
import java.awt.image.BufferedImage;

public class Explode {
    private int x;
    private int y;
    private int step = 0;
    private  TankFrame tf;

    public Explode(int x, int y, TankFrame tf) {
        this.tf =tf;
        this.x = x-ResourceMgr.Explode_WIDTH;
        this.y = y-ResourceMgr.Explode_HEIGHT;
    }

    public void paint(Graphics g){
        //for(BufferedImage img:img){
         //   g.drawImage(img, x, y,null);
        g.drawImage(ResourceMgr.Explode[step++], x, y, null);
        if(step >= ResourceMgr.Explode.length)
            step = 0;
        }

}
