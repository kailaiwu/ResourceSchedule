package task;

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

    public DeployTask(int uniqId, List<DeploySingleTask> tasks) {
        this.uniqId = uniqId;
        this.tasks = tasks;
    }

    public int getUniqId() {
        return uniqId;
    }

    public List<DeploySingleTask> getTasks() {
        return tasks;
    }
}