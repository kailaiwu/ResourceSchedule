package schedule;

import manager.DeployTaskManager;
import task.DeploySingleTask;
import task.DeployTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 调度器
 */

public class Scheduler implements Runnable {
    //锁
    ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while(true) {
            BlockingQueue<DeployTask> deployTasks = DeployTaskManager.getInstance().getDeployTasks();
            if (!deployTasks.isEmpty()) {
                List<DeployTask> tasks = new ArrayList<>();
                deployTasks.drainTo(tasks);
                //部署应用
                for (DeployTask task : tasks) {
                    doDeploy(task);
                }
            }
        }
    }

    /**
     * 执行部署应用操作
     */
    private void doDeploy(DeployTask deployTask) {
        List<DeploySingleTask> tasks = deployTask.getTasks();
        for (DeploySingleTask task : tasks) {
            try {
                lock.lockInterruptibly();
                //1）查询数据库 + 获得调度结果
                //2）执行部署操作
                //3）更新数据库
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}