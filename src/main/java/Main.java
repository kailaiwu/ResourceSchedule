import db.DatabaseManager;
import manager.InitManager;
import schedule.Scheduler;


/**
 * 资源调度入口
 */

public class Main {
    public static void main(String[] args) throws Exception {
        //初始化
        InitManager.getInstance().init();
        //单线程执行
        new Thread(new Scheduler()).start();
    }
}