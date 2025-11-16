package com.jzz.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 学生实体类
 * Student Entity Class
 *
 * 对应数据库中的 student 表
 * Corresponds to the 'student' table in the database
 *
 * @author Jzz
 * @version 1.0
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生ID，主键
     * Student ID, Primary Key
     */
    private Integer id;

    /**
     * 学号，唯一标识
     * Student Number, Unique Identifier
     */
    private String studentNo;

    /**
     * 姓名
     * Name
     */
    private String name;

    /**
     * 性别：1-男，2-女
     * Gender: 1-Male, 2-Female
     */
    private Integer gender;

    /**
     * 年龄
     * Age
     */
    private Integer age;

    /**
     * 专业
     * Major
     */
    private String major;

    /**
     * 班级
     * Class Name
     */
    private String className;

    /**
     * 联系电话
     * Phone Number
     */
    private String phone;

    /**
     * 邮箱
     * Email
     */
    private String email;

    /**
     * 入学日期
     * Enrollment Date
     */
    private Date enrollmentDate;

    /**
     * 创建时间
     * Create Time
     */
    private Timestamp createTime;

    /**
     * 更新时间
     * Update Time
     */
    private Timestamp updateTime;

    /**
     * 状态：1-在读，2-休学，3-毕业
     * Status: 1-Active, 2-Leave, 3-Graduated
     */
    private Integer status;

    // ==================== 构造方法 (Constructors) ====================

    /**
     * 无参构造方法
     * No-argument Constructor
     */
    public Student() {
    }

    /**
     * 全参构造方法
     * Full-argument Constructor
     */
    public Student(Integer id, String studentNo, String name, Integer gender, Integer age,
                   String major, String className, String phone, String email,
                   Date enrollmentDate, Timestamp createTime, Timestamp updateTime, Integer status) {
        this.id = id;
        this.studentNo = studentNo;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.major = major;
        this.className = className;
        this.phone = phone;
        this.email = email;
        this.enrollmentDate = enrollmentDate;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
    }

    // ==================== Getter 和 Setter 方法 (Getter and Setter Methods) ====================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 获取性别文本描述
     * Get Gender Text Description
     *
     * @return 性别文本："男" 或 "女"
     */
    public String getGenderText() {
        return gender != null && gender == 1 ? "男" : "女";
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取状态文本描述
     * Get Status Text Description
     *
     * @return 状态文本："在读"、"休学" 或 "毕业"
     */
    public String getStatusText() {
        if (status == null) return "未知";
        switch (status) {
            case 1: return "在读";
            case 2: return "休学";
            case 3: return "毕业";
            default: return "未知";
        }
    }

    // ==================== Object 方法重写 (Object Method Overrides) ====================

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentNo='" + studentNo + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", major='" + major + '\'' +
                ", className='" + className + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", enrollmentDate=" + enrollmentDate +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}
