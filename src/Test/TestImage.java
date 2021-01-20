package Test;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class TestImage {
    @Test
    public void test() {
        System.out.println("测试方法");
        try {
            //测试图片能被加载
           // BufferedImage img = ImageIO.read(new FileInputStream(new File("File\\1.jpg")));
            BufferedImage img2 = ImageIO.read(TestImage.class.getClassLoader().getResourceAsStream("File/1.jpg"));

           // Assert.assertNotNull(img);
            Assert.assertNotNull(img2);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

