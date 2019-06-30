package util;

import java.util.HashSet;
import java.util.Set;

/**
 * 限制规则类型枚举
 */

public class ConstraintType {
    private static Set<Integer> VALIDATE_TYPES = new HashSet<Integer>();

    /** 单机部署应用总数量限制 */
    public static final int TOTAL_NUM_LIMIT = 1;
    /** 单机部署特定类型应用数量限制 */
    public static final int TYPE_NUM_LIMIT = 2;

    static {
        VALIDATE_TYPES.add(TOTAL_NUM_LIMIT);
        VALIDATE_TYPES.add(TYPE_NUM_LIMIT);
    }

    public static boolean isValidateType(int type) {
        return VALIDATE_TYPES.contains(type);
    }
}