package rule;

import util.ConstraintType;

/**
 * 应用总数量限制规则类
 */

public class TotalNumberConstraintRule extends AbstractConstraintRule {
    /** 限制数量 **/
    private final int totalConstraintNum;

    public TotalNumberConstraintRule(int totalConstraintNum) {
        super(ConstraintType.TOTAL_NUM_LIMIT);
        this.totalConstraintNum = totalConstraintNum;
    }

    @Override
    public boolean isSatisfy() {
        return true;
    }
}