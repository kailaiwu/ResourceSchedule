package entity;

/**
 * 已部署应用信息实体类
 */

public class DeployInfo {
    /** 应用ID */
    private final int id;
    /** 应用类型 */
    private final int type;

    public DeployInfo(int id, int type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }
}