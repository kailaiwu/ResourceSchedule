package rule;

import task.DeploySingleTask;
import util.ConstraintType;

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
    public boolean isSatisfy(String ip, DeploySingleTask task) {
        return true;
    }
}