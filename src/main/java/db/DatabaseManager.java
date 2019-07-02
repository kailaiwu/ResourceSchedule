package db;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entity.DeployInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * 数据库管理类
 */

public class DatabaseManager {
    private Connection conn;
    private Statement stat;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public DatabaseManager() {
        conn = ConnectionManager.getConnection();
    }

    /**
     * 获取所有主机IP
     */
    public Set<String> getAllIps() throws Exception {
        Set<String> allIps = new HashSet<String>();
        String sql = "SELECT ip FROM host";
        stat = conn.createStatement();
        rs = stat.executeQuery(sql);
        while (rs.next()) {
            String ip = rs.getString("ip");
            allIps.add(ip);
        }
        close();
        return allIps;
    }

    /**
     * 获取指定主机已部署应用信息
     * @param ip 主机IP
     * @return 已部署应用集合
     */
    public List<DeployInfo> getDeployedInfo(String ip) throws Exception {
        List<DeployInfo> list = new ArrayList<DeployInfo>();
        String sql = "SELECT deploy_info FROM host WHERE ip = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, ip);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            String jsonString = rs.getString("deploy_info");
            JsonArray jsonArray = new JsonParser().parse(jsonString).getAsJsonArray();
            int size = jsonArray.size();
            for (int i = 0; i < size; i++) {
                JsonElement element = jsonArray.get(i);
                JsonObject object = element.getAsJsonObject();
                DeployInfo info = new DeployInfo(object.get("id").getAsInt(), object.get("type").getAsInt());
                list.add(info);
            }
        }
        close();
        return list;
    }

    /**
     * 主机添加标签
     */
    public boolean addLabel(String ip, int label) throws Exception {
        String sql = "UPDATE host SET label = label | ?  WHERE ip = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, label);
        pstmt.setString(2, ip);
        int result = pstmt.executeUpdate();
        close();
        return  result > 0;
    }

    /**
     * 移除主机标签
     */
    public boolean removeLabel(String ip, int label) throws Exception {
        String sql = "UPDATE host SET label = label & ?  WHERE ip = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, ~label);
        pstmt.setString(2, ip);
        int result = pstmt.executeUpdate();
        close();
        return  result > 0;
    }

    /**
     * 关闭操作
     */
    private void close() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stat != null) {
            stat.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
