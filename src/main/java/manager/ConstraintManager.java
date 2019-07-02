package manager;

import util.ConstraintType;
import rule.AbstractConstraintRule;
import rule.TotalNumberConstraintRule;
import rule.TypeNumberConstraintRule;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * 限制规则管理类
 */

public class ConstraintManager {
    private ConstraintManager() {
    }

    private static class SingletonHolder {
        private final static ConstraintManager instance = new ConstraintManager();
    }

    public static ConstraintManager getInstance() {
        return ConstraintManager.SingletonHolder.instance;
    }

    private static final String filePath = "D:\\MyProject\\Github\\ResourceSchedule\\constraint.properties";

    private Set<AbstractConstraintRule> rules = new HashSet<AbstractConstraintRule>();

    /**
     * 初始化限制规则
     */
    public void init() throws Exception {
        //加载配置文件
        Properties pros = new Properties();
        InputStream rs = new FileInputStream(filePath);
        pros.load(rs);
        //遍历读取文件
        for (Entry<Object, Object> entry : pros.entrySet()) {
            int type = Integer.parseInt((String) entry.getKey());
            String parameters = (String) entry.getValue();
            parse(type, parameters);
        }
    }

    /**
     * 解析限制规则
     */
    private void parse(int type, String parameters) {
       switch(type) {
           case ConstraintType.TOTAL_NUM_LIMIT:
               int totalConstraintNum = Integer.parseInt(parameters);
               rules.add(new TotalNumberConstraintRule(totalConstraintNum));
               break;
           case ConstraintType.TYPE_NUM_LIMIT:
               String[] ruleStrs = parameters.split(";");
               for(String ruleStr : ruleStrs) {
                   int applicationType = Integer.parseInt(ruleStr.split(":")[0]);
                   int typeConstraintNum = Integer.parseInt(ruleStr.split(":")[1]);
                   rules.add(new TypeNumberConstraintRule(applicationType, typeConstraintNum));
               }
               break;
           default:
       }
    }

    /**
     * 获取限制规则集合
     */
    public Set<AbstractConstraintRule> getConstraintRules() {
        return rules;
    }
}