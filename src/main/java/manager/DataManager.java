package manager;

import db.DatabaseManager;
import task.DeploySingleTask;
import task.DeployTask;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> allIps = new ArrayList<String>();

    public void init() throws Exception {
        //初始化所有主机IP
        allIps = new DatabaseManager().getAllIps();
        //初始化部署任务
        int uniqId = 1;
        DeployTask task = new DeployTask(uniqId);
        task.addTask(new DeploySingleTask(1, 1));
        task.addTask(new DeploySingleTask(2, 2));
        task.addTask(new DeploySingleTask(3, 4));
        task.addTask(new DeploySingleTask(4, 8));
        DeployTaskManager.getInstance().addDeployTask(task);
    }

    /**
     * 获取所有主机IP
     */
    public List<String> getAllIps() {
        return allIps;
    }
}