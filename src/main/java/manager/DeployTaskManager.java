package manager;

import task.DeployTask;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 部署任务管理器
 */

public class DeployTaskManager {
    private DeployTaskManager() {
    }

    private static class SingletonHolder {
        private final static DeployTaskManager instance = new DeployTaskManager();
    }

    public static DeployTaskManager getInstance() {
        return DeployTaskManager.SingletonHolder.instance;
    }

    /**
     * 部署任务队列
     * 需设置一个容量上限
     */
    private volatile BlockingQueue<DeployTask> deployTasks = new LinkedBlockingQueue<>(10);

    /** 间隔时间：间隔时间内不允许两个相同的部署任务同时进入队列 */
    public static final long INTERVAL_TIME = 3 * 1000;

    /**
     * 记录新部署任务入队时间
     * 用于规避处理重复的请求
     */
    private final LinkedHashMap<Integer, Long> uniqId2Time = new LinkedHashMap<Integer, Long>() {
        @Override
        protected boolean removeEldestEntry(Entry<Integer, Long> eldest) {
            //过期了就删掉
            return System.currentTimeMillis() - eldest.getValue() > INTERVAL_TIME;
        }
    };

    /**
     * 是否可以进入队列
     */
    private boolean canEnterQueue(DeployTask task) {
        int uniqId = task.getUniqId();
        Long time = uniqId2Time.get(uniqId);
        if(time != null) {
            return System.currentTimeMillis() - time > INTERVAL_TIME;
        }
        return true;
    }

    /**
     * 新添加一个部署任务
     * 加锁
     */
    public synchronized boolean addDeployTask(DeployTask task) {
        if(!canEnterQueue(task)) {
            return false;
        }
        if(!deployTasks.offer(task)) {
            return false;
        }
        uniqId2Time.put(task.getUniqId(), System.currentTimeMillis());
        return true;
    }

    /**
     * 获取部署任务队列
     */
    public BlockingQueue<DeployTask> getDeployTasks() {
        return deployTasks;
    }
}