package com.jzz.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 数据库工具类
 * Database Utility Class
 *
 * 负责数据库连接的获取和释放
 * Responsible for database connection acquisition and release
 *
 * @author Jzz
 * @version 1.0
 */
public class DBUtil {

    // 数据库配置信息 (Database Configuration)
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    // 静态代码块，加载数据库配置
    // Static block to load database configuration
    static {
        try {
            // 加载配置文件 (Load configuration file)
            Properties props = new Properties();
            InputStream is = DBUtil.class.getClassLoader()
                    .getResourceAsStream("../../WEB-INF/db.properties");

            if (is == null) {
                // 如果在类路径下找不到，尝试从 WEB-INF 读取
                // If not found in classpath, try reading from WEB-INF
                System.err.println("警告：无法从类路径加载 db.properties，使用默认配置");
                driver = "com.mysql.cj.jdbc.Driver";
                url = "jdbc:mysql://localhost:3306/student_system?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8";
                username = "root";
                password = "root";
            } else {
                props.load(is);
                driver = props.getProperty("jdbc.driver");
                url = props.getProperty("jdbc.url");
                username = props.getProperty("jdbc.username");
                password = props.getProperty("jdbc.password");
                is.close();
            }

            // 加载数据库驱动 (Load database driver)
            Class.forName(driver);
            System.out.println("数据库驱动加载成功！Driver: " + driver);

        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动加载失败！");
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        } catch (IOException e) {
            System.err.println("配置文件读取失败！");
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 获取数据库连接
     * Get Database Connection
     *
     * @return Connection 数据库连接对象
     * @throws SQLException SQL异常
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, password);
        System.out.println("数据库连接成功！");
        return conn;
    }

    /**
     * 关闭数据库连接资源
     * Close Database Resources
     *
     * 关闭 ResultSet、Statement 和 Connection
     * Close ResultSet, Statement and Connection
     *
     * @param rs ResultSet 对象
     * @param stmt Statement 对象
     * @param conn Connection 对象
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        // 关闭 ResultSet
        if (rs != null) {
            try {
                rs.close();
                System.out.println("ResultSet 关闭成功");
            } catch (SQLException e) {
                System.err.println("ResultSet 关闭失败！");
                e.printStackTrace();
            }
        }

        // 关闭 Statement
        if (stmt != null) {
            try {
                stmt.close();
                System.out.println("Statement 关闭成功");
            } catch (SQLException e) {
                System.err.println("Statement 关闭失败！");
                e.printStackTrace();
            }
        }

        // 关闭 Connection
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection 关闭成功");
            } catch (SQLException e) {
                System.err.println("Connection 关闭失败！");
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭数据库连接资源（重载方法）
     * Close Database Resources (Overloaded Method)
     *
     * @param stmt Statement 对象
     * @param conn Connection 对象
     */
    public static void close(Statement stmt, Connection conn) {
        close(null, stmt, conn);
    }

    /**
     * 关闭数据库连接
     * Close Database Connection
     *
     * @param conn Connection 对象
     */
    public static void close(Connection conn) {
        close(null, null, conn);
    }

    /**
     * 测试数据库连接
     * Test Database Connection
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = getConnection();
            if (conn != null) {
                System.out.println("========================================");
                System.out.println("数据库连接测试成功！");
                System.out.println("数据库类型: " + conn.getMetaData().getDatabaseProductName());
                System.out.println("数据库版本: " + conn.getMetaData().getDatabaseProductVersion());
                System.out.println("驱动版本: " + conn.getMetaData().getDriverVersion());
                System.out.println("========================================");
            }
        } catch (SQLException e) {
            System.err.println("数据库连接测试失败！");
            e.printStackTrace();
        } finally {
            close(conn);
        }
    }
}
