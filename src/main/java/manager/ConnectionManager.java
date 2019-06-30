package manager;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 数据库连接管理类
 */

public class ConnectionManager {
    private ConnectionManager() {
    }

    private static class SingletonHolder {
        private final static ConnectionManager instance = new ConnectionManager();
    }

    public static ConnectionManager getInstance() {
        return SingletonHolder.instance;
    }

    private static final String filePath = "D:\\MyProject\\Github\\ResourceSchedule\\jdbc.properties";

    private Connection connection;

    /**
     * 初始化数据库连接
     */
    public void init() throws Exception {
        //1. 加载配置文件
        Properties pros = new Properties();
        InputStream rs = new FileInputStream(filePath);
        pros.load(rs);
        //2. 获取连接所需的四个基本信息
        String driverClass = pros.getProperty("driverClass");
        String url = pros.getProperty("url");
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        //3. 通过反射加载驱动
        Class.forName(driverClass);
        //4. 初始化连接
        connection = DriverManager.getConnection(url,user,password);
    }

    /**
     * 获取数据库连接
     */
    public Connection getConnection() {
        return connection;
    }
}