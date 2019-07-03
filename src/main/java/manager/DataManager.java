package manager;

import db.DatabaseManager;
import task.DeploySingleTask;
import task.DeployTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private List<String> allIps = new ArrayList<String>();

    public void init() throws Exception {
        //获取所有主机IP
        allIps = new DatabaseManager().getAllIps();
        //初始化部署任务
        int uniqId = 1;
        List<DeploySingleTask> subTasks = new ArrayList<DeploySingleTask>();
        subTasks.add(new DeploySingleTask(1, 1));
        subTasks.add(new DeploySingleTask(2, 2));
        subTasks.add(new DeploySingleTask(3, 4));
        subTasks.add(new DeploySingleTask(4, 8));
        DeployTask task = new DeployTask(uniqId, subTasks);
        DeployTaskManager.getInstance().addDeployTask(task);
    }

    /**
     * 获取所有主机IP
     */
    public List<String> getAllIps() {
        return allIps;
    }
}