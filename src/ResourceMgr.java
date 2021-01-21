import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceMgr {
    //加载坦克、子弹图像资源，获取图像宽高以便计算子弹位置
    public static BufferedImage tankU1,tankD1,tankL1,tankR1;
    public static BufferedImage tankU2,tankD2,tankL2,tankR2;
    public static BufferedImage mytankU1, mytankD1, mytankL1, mytankR1;
    public static BufferedImage mytankU2, mytankD2, mytankL2, mytankR2;
    public static BufferedImage bulletU,bulletD,bulletL,bulletR;
    public static int Tank_WIDTH,Tank_HEIGHT,BULLET_WIDTH, BULLET_HEIGHT;
    public static BufferedImage[] Explode = new BufferedImage[16];
    public static int Explode_WIDTH;
    public static int Explode_HEIGHT;

    static{
        try{
            tankU1 = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank2.png"));
            tankD1 = ImageUtil.rotateImage(tankU1, 180);
            tankL1 = ImageUtil.rotateImage(tankU1, 270);
            tankR1 = ImageUtil.rotateImage(tankU1, 90);

            tankU2 = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            tankD2 = ImageUtil.rotateImage(tankU2, 180);
            tankL2 = ImageUtil.rotateImage(tankU2, 270);
            tankR2 = ImageUtil.rotateImage(tankU2, 90);

            mytankU1 = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank2.png"));
            mytankD1 = ImageUtil.rotateImage(mytankU1, 180);
            mytankL1 = ImageUtil.rotateImage(mytankU1, 270);
            mytankR1 = ImageUtil.rotateImage(mytankU1, 90);

            mytankU2 = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            mytankD2 = ImageUtil.rotateImage(mytankU2, 180);
            mytankL2 = ImageUtil.rotateImage(mytankU2, 270);
            mytankR2 = ImageUtil.rotateImage(mytankU2, 90);

            Tank_WIDTH = tankU1.getWidth();
            Tank_HEIGHT = tankU1.getHeight();

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
