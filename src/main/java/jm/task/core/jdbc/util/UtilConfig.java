package jm.task.core.jdbc.util;

public class UtilConfig {
    public Util getUtil(){
        Util util = new Util();
        util.setHOSTNAME("localhost");
        util.setPORT("3306");
        util.setDATABASENAME("mydbtest");
        util.setLOGIN("test");
        util.setPASSWORD("test");
        util.setTABLENAME("users");
        return util;
    }
}
