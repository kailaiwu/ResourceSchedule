package util;

import java.util.HashSet;
import java.util.Set;

/**
 * 应用类型枚举
 */

public class ApplicationType {
    /** 应用类型集合 */
    private static Set<Integer> VALIDATE_TYPES = new HashSet<Integer>();

    /** 站点 */
    public static final int SITE = 1;
    /** 组件 */
    public static final int ASSEMBLY = 2;
    /** 触发器 */
    public static final int TRIGGER = 4;
    /** 定时任务 */
    public static final int TIMING_TASK = 8;

    static {
        VALIDATE_TYPES.add(SITE);
        VALIDATE_TYPES.add(ASSEMBLY);
        VALIDATE_TYPES.add(TRIGGER);
        VALIDATE_TYPES.add(TIMING_TASK);
    }

    /**
     * 是否为合法的应用类型
     */
    public static boolean isValidateType(int type) {
        return VALIDATE_TYPES.contains(type);
    }
}