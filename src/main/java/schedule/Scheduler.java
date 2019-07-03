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
        while (true) {
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
                    //标签筛选
                    if (!isLabelSatisfy(ip, task)) {
                        continue;
                    }
                    //限制规则筛选
                    if (!isAllRuleSatisfy(ip, task)) {
                        continue;
                    }
                    candidates.add(ip);
                }
                if (candidates.isEmpty()) {
                    System.err.println("id = " + task.getId() + "的应用部署失败。失败原因：当前没有主机匹配");
                    continue;
                }
                //指定部署
                if (task.isAppointed()) {
                    String ip = task.getIp();
                    if (!candidates.contains(ip)) {
                        System.err.println("id = " + task.getId() + "的应用指定部署失败。" +
                                "失败原因：指定部署主机不在匹配主机集合里面");
                        continue;
                    }
                }
                //优选
                String finalIp = selectBest(task, candidates);
                //部署
                deploy(task.getId(), finalIp);
                //更新数据库
                new DatabaseManager().addDeploy(finalIp, task.getId(), task.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断主机label是否满足
     */
    private boolean isLabelSatisfy(String ip, DeploySingleTask task) throws Exception {
        int label = new DatabaseManager().getLabel(ip);
        return (label & task.getType()) == 0;
    }

    /**
     * 判断所有限制规则是否都满足
     */
    private boolean isAllRuleSatisfy(String ip, DeploySingleTask task) throws Exception {
        Set<AbstractConstraintRule> rules = ConstraintManager.getInstance().getConstraintRules();
        for (AbstractConstraintRule rule : rules) {
            //只要有一个限制规则不满足，即为匹配失败
            if (!rule.isSatisfy(ip, task)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 优选
     * 当前优选规则：随机策略
     */
    private String selectBest(DeploySingleTask task, List<String> candidates) {
        if (task.isAppointed()) {
            return task.getIp();
        }
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