package task;

import java.util.ArrayList;
import java.util.List;

/**
 * 部署任务
 * 包括多个单部署子任务
 */

public class DeployTask {
    /** 部署任务唯一Id */
    private final int uniqId;
    /** 单部署子任务集合 */
    private final List<DeploySingleTask> tasks;

    public DeployTask(int uniqId) {
        this.uniqId = uniqId;
        this.tasks = new ArrayList<DeploySingleTask>();
    }

    /**
     * 添加部署子任务
     */
    public void addTask(DeploySingleTask task) {
        tasks.add(task);
    }

    public int getUniqId() {
        return uniqId;
    }

    public List<DeploySingleTask> getTasks() {
        return tasks;
    }
}