package TankBattle;

import java.io.IOException;
import java.util.Properties;

public class TestProperty {
    public static void main(String[] args) throws IOException {
        Properties p = new Properties();
        p.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
    }

}
