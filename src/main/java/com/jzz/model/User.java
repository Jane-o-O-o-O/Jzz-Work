package com.jzz.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户实体类
 * User Entity Class
 *
 * 对应数据库中的 user 表
 * Corresponds to the 'user' table in the database
 *
 * @author Jzz
 * @version 1.0
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID，主键
     * User ID, Primary Key
     */
    private Integer id;

    /**
     * 用户名
     * Username
     */
    private String username;

    /**
     * 密码（实际项目中应该加密存储）
     * Password (should be encrypted in production)
     */
    private String password;

    /**
     * 真实姓名
     * Real Name
     */
    private String realName;

    /**
     * 角色：1-管理员，2-普通用户
     * Role: 1-Admin, 2-User
     */
    private Integer role;

    /**
     * 创建时间
     * Create Time
     */
    private Timestamp createTime;

    // ==================== 构造方法 (Constructors) ====================

    /**
     * 无参构造方法
     * No-argument Constructor
     */
    public User() {
    }

    /**
     * 全参构造方法
     * Full-argument Constructor
     */
    public User(Integer id, String username, String password, String realName,
                Integer role, Timestamp createTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.role = role;
        this.createTime = createTime;
    }

    // ==================== Getter 和 Setter 方法 (Getter and Setter Methods) ====================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    /**
     * 获取角色文本描述
     * Get Role Text Description
     *
     * @return 角色文本："管理员" 或 "普通用户"
     */
    public String getRoleText() {
        return role != null && role == 1 ? "管理员" : "普通用户";
    }

    /**
     * 判断是否为管理员
     * Check if user is admin
     *
     * @return true 是管理员，false 不是管理员
     */
    public boolean isAdmin() {
        return role != null && role == 1;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", role=" + role +
                ", createTime=" + createTime +
                '}';
    }
}
