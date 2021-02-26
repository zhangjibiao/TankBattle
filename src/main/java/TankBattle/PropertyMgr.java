package TankBattle;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
    static Properties p = new Properties();

    static {
        try {
            p.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getValue(String str) {
        if (p.get(str) == null) return null;
        return p.get(str);
    }

    private void PropertyMgr() {
    }

    ;

}

