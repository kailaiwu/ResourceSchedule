package rule;

import db.DatabaseManager;
import entity.DeployInfo;
import task.DeploySingleTask;
import util.ConstraintType;

import java.util.List;

/**
 * 通用限制规则：不带参数的限制规则
 */

public class CommonConstraintRule extends AbstractConstraintRule {
    public CommonConstraintRule() {
        super(ConstraintType.COMMON);
    }

    @Override
    public boolean isSatisfy(String ip, DeploySingleTask task) throws Exception {
        List<DeployInfo> infos = new DatabaseManager().getDeployedInfo(ip);
        //每台主机最多只能部署同一应用的一个副本
        for(DeployInfo info : infos) {
            if(info.getId() == task.getId()) {
                return false;
            }
        }
        return true;
    }
}