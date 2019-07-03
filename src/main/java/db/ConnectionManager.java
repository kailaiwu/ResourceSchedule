package db;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 数据库连接管理类
 */

public class ConnectionManager {
    private static final String filePath = "D:\\MyProject\\Github\\ResourceSchedule\\jdbc.properties";

    /**
     * 初始化数据库连接
     */
    public static Connection getConnection() {
        try {
            //加载配置文件
            Properties pros = new Properties();
            InputStream rs = new FileInputStream(filePath);
            pros.load(rs);
            //获取连接所需的四个基本信息
            String driverClass = pros.getProperty("driverClass");
            String url = pros.getProperty("url");
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            //通过反射加载驱动
            Class.forName(driverClass);
            //初始化连接
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}