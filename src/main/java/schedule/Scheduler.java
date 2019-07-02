package schedule;

import manager.ConstraintManager;
import manager.DataManager;
import manager.DeployTaskManager;
import rule.AbstractConstraintRule;
import task.DeploySingleTask;
import task.DeployTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 调度器
 */

public class Scheduler implements Runnable {
    //锁
    ReentrantLock lock = new ReentrantLock();

    public void run() {
        while(true) {
            BlockingQueue<DeployTask> deployTasks = DeployTaskManager.getInstance().getDeployTasks();
            if (!deployTasks.isEmpty()) {
                List<DeployTask> tasks = new ArrayList<DeployTask>();
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
                //筛选
                Set<String> ips = DataManager.getInstance().getAllIps();
                Set<String> candidates = new HashSet<String>();
                for (String ip : ips) {
                    if (!isAllRuleSatisfy(ip, task)) {
                         continue;
                    }
                    candidates.add(ip);
                }
                //优选
                //部署
                //更新数据库
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 判断所有限制规则是否都满足
     */
    private boolean isAllRuleSatisfy(String ip, DeploySingleTask task) {
        Set<AbstractConstraintRule> rules = ConstraintManager.getInstance().getConstraintRules();
        for(AbstractConstraintRule rule : rules) {
            //只要有一个限制规则不满足，即为匹配失败
            if (!rule.isSatisfy(ip, task)) {
                return false;
            }
        }
        return true;
    }
}