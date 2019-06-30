package task;

/**
 * 单部署子任务
 */

public class DeploySingleTask {
    /** 应用Id */
    private final int id;
    /** 应用类型 */
    private final int type;
    /** 是否指定部署：0：非指定；1：指定 */
    private final int isAppointed;
    /** 指定部署IP */
    private final String ip;

    public DeploySingleTask(int id, int type) {
        this.id = id;
        this.type = type;
        this.isAppointed = 0;
        this.ip = "";
    }

    public DeploySingleTask(int id, int type, int isAppointed, String ip) {
        this.id = id;
        this.type = type;
        this.isAppointed = isAppointed;
        this.ip = ip;
    }
}