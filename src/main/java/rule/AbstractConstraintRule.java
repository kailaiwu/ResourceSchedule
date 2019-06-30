package rule;

/**
 * 限制条件抽象类
 */

public abstract class AbstractConstraintRule {
    /** 限制类型 */
    protected int type;

    public AbstractConstraintRule(int type) {
        this.type = type;
    }

    /**
     * 限制条件是否满足
     */
    public abstract boolean isSatisfy();
}