package rule;

import db.DatabaseManager;
import entity.DeployInfo;
import task.DeploySingleTask;
import util.ConstraintType;

import java.util.List;

/**
 * 特定类型应用数量限制规则类
 */

public class TypeNumberConstraintRule extends AbstractConstraintRule {
    /** 应用类型 */
    private final int applicationType;
    /** 应用类型限制数量 **/
    private final int typeConstraintNum;

    public TypeNumberConstraintRule(int applicationType, int typeConstraintNum) {
        super(ConstraintType.TYPE_NUM_LIMIT);
        this.applicationType = applicationType;
        this.typeConstraintNum = typeConstraintNum;
    }

    @Override
    public boolean isSatisfy(String ip, DeploySingleTask task) throws Exception {
        if (task.getType() != applicationType) {
            return true;
        }
        List<DeployInfo> infos = new DatabaseManager().getDeployedInfo(ip);
        int num = 0;
        for (DeployInfo info : infos) {
            if(info.getType() == applicationType) {
                num++;
            }
        }
        return num < typeConstraintNum;
    }
}