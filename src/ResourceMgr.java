import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceMgr {
    //加载坦克、子弹图像资源，获取图像宽高以便计算子弹位置
    public static BufferedImage tankU,tankD,tankL,tankR;
    public static BufferedImage bulletU,bulletD,bulletL,bulletR;
    public static int Tank_WIDTH,Tank_HEIGHT,BULLET_WIDTH, BULLET_HEIGHT;
    public static BufferedImage[] Explode = new BufferedImage[16];
    public static int Explode_WIDTH;
    public static int Explode_HEIGHT;

    static{
        try{
            tankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankU.gif"));
            tankD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankD.gif"));
            tankL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankL.gif"));
            tankR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankR.gif"));
            Tank_WIDTH = tankU.getWidth();
            Tank_HEIGHT = tankU.getHeight();

            bulletD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
            bulletR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletR.gif"));
            bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
            bulletL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletL.gif"));
            BULLET_WIDTH = bulletL.getWidth();
            BULLET_HEIGHT = bulletL.getHeight();

            for(int i=0; i<16; i++){
                Explode[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e"+(i+1)+".gif"));}
            Explode_WIDTH = Explode[0].getWidth();
            Explode_HEIGHT = Explode[0].getHeight();
        }
        catch (IOException e){
            e.printStackTrace();
        }


}}