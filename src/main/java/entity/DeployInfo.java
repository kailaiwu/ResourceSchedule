package entity;

/**
 * 已部署应用信息封装类
 */

public class DeployInfo {
    /** 应用唯一ID */
    int id;
    /** 应用类型 */
    int type;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }
}