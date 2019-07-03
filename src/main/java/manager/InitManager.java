package manager;

/**
 * 初始化管理器
 */

public class InitManager {
    private InitManager() {
    }

    private static class SingletonHolder {
        private final static InitManager instance = new InitManager();
    }

    public static InitManager getInstance() {
        return InitManager.SingletonHolder.instance;
    }

    public void init() throws Exception {
        //初始化限制条件
        ConstraintManager.getInstance().init();
        //初始化任务

        //初始化数据
        DataManager.getInstance().init();
    }
}