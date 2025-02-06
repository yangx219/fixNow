package com.pc3ryangzhang.fixnow.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
    private static Properties prop = new Properties();
    static {
        try {
            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("mysql.properties");
            prop.load(in);
            Class.forName(prop.getProperty("driver"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * get the connection of database
     * */
    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            String dbUrl = prop.getProperty("dbUrl");
            String dbName = prop.getProperty("dbName");
            String dbPwd = prop.getProperty("dbPwd");
            conn = DriverManager.getConnection(dbUrl,dbName,dbPwd);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn,
                             Statement stmt,
                             ResultSet rs) throws SQLException {

        if (rs != null) {
            rs.close();
        }
        if(stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
