package rule;

import db.DatabaseManager;
import entity.DeployInfo;
import task.DeploySingleTask;
import util.ConstraintType;

import java.util.List;

/**
 * 应用总数量限制规则类
 */

public class TotalNumberConstraintRule extends AbstractConstraintRule {
    /** 应用限制总数量 **/
    private final int totalConstraintNum;

    public TotalNumberConstraintRule(int totalConstraintNum) {
        super(ConstraintType.TOTAL_NUM_LIMIT);
        this.totalConstraintNum = totalConstraintNum;
    }

    @Override
    public boolean isSatisfy(String ip, DeploySingleTask task) throws Exception {
        List<DeployInfo> infos = new DatabaseManager().getDeployedInfo(ip);
        return infos.size() < totalConstraintNum;
    }
}