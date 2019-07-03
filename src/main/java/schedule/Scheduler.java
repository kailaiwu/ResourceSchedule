package schedule;

import db.DatabaseManager;
import manager.ConstraintManager;
import manager.DataManager;
import manager.DeployTaskManager;
import rule.AbstractConstraintRule;
import task.DeploySingleTask;
import task.DeployTask;

import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * 调度器
 */

public class Scheduler implements Runnable {
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
                //筛选
                List<String> ips = DataManager.getInstance().getAllIps();
                List<String> candidates = new ArrayList<String>();
                for (String ip : ips) {
                    if (!isAllRuleSatisfy(ip, task)) {
                        continue;
                    }
                    candidates.add(ip);
                }
                if (candidates.isEmpty()) {
                    System.err.println("id = " + task.getId() + "的应用部署失败");
                    continue;
                }
                String finalIp = selectBest(candidates);
                deploy(task.getId(), finalIp);
                //更新数据库
                new DatabaseManager().addDeploy(finalIp, task.getId(), task.getType());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断所有限制规则是否都满足
     */
    private boolean isAllRuleSatisfy(String ip, DeploySingleTask task) throws Exception {
        Set<AbstractConstraintRule> rules = ConstraintManager.getInstance().getConstraintRules();
        for(AbstractConstraintRule rule : rules) {
            //只要有一个限制规则不满足，即为匹配失败
            if (!rule.isSatisfy(ip, task)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 优选
     */
    private String selectBest(List<String> candidates) {
        int index = new Random().nextInt(candidates.size());
        return candidates.get(index);
    }

    /**
     * 部署
     */
    private void deploy(int id, String ip) {
        System.out.println("id = " + id + "的应用成功部署在ip = " + ip + "的主机上");
    }
}