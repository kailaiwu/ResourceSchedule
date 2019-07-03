package rule;

import task.DeploySingleTask;

/**
 * 限制规则抽象类
 */

public abstract class AbstractConstraintRule {
    /** 限制类型 */
    protected int type;

    public AbstractConstraintRule(int type) {
        this.type = type;
    }

    /**
     * 限制规则是否满足
     */
    public abstract boolean isSatisfy(String ip, DeploySingleTask task) throws Exception;
}