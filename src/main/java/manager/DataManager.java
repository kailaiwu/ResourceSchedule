package manager;

import db.DatabaseManager;

import java.util.HashSet;
import java.util.Set;

/**
 * 数据管理器
 */

public class DataManager {
    private DataManager() {
    }

    private static class SingletonHolder {
        private final static DataManager instance = new DataManager();
    }

    public static DataManager getInstance() {
        return DataManager.SingletonHolder.instance;
    }

    /** 所有主机IP */
    private Set<String> allIps = new HashSet<String>();

    public void init() throws Exception {
        allIps = new DatabaseManager().getAllIps();
    }

    /**
     * 获取所有主机IP
     */
    public Set<String> getAllIps() {
        return allIps;
    }
}