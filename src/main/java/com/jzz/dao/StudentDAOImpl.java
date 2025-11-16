package com.jzz.dao;

import com.jzz.model.Student;
import com.jzz.util.DBUtil;
import com.jzz.util.StringUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 学生数据访问实现类
 * Student Data Access Object Implementation
 *
 * 实现 StudentDAO 接口，提供具体的数据库操作
 * Implements StudentDAO interface, provides specific database operations
 *
 * @author Jzz
 * @version 1.0
 */
public class StudentDAOImpl implements StudentDAO {

    /**
     * 根据条件查询学生列表（支持分页和排序）
     * Query student list by conditions (supports pagination and sorting)
     */
    @Override
    public List<Student> selectByConditions(Map<String, Object> conditions, String orderBy,
                                           String orderType, int offset, int limit) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();

            // 构建 SQL 语句 (Build SQL statement)
            StringBuilder sql = new StringBuilder("SELECT * FROM student WHERE 1=1");
            List<Object> params = new ArrayList<>();

            // 添加查询条件 (Add query conditions)
            sql = appendConditions(sql, conditions, params);

            // 添加排序 (Add ordering)
            if (StringUtil.isNotEmpty(orderBy)) {
                sql.append(" ORDER BY ").append(orderBy);
                if ("DESC".equalsIgnoreCase(orderType)) {
                    sql.append(" DESC");
                } else {
                    sql.append(" ASC");
                }
            }

            // 添加分页 (Add pagination)
            sql.append(" LIMIT ?, ?");
            params.add(offset);
            params.add(limit);

            // 执行查询 (Execute query)
            pstmt = conn.prepareStatement(sql.toString());
            setParameters(pstmt, params);

            System.out.println("执行 SQL: " + sql);
            rs = pstmt.executeQuery();

            // 封装结果 (Encapsulate results)
            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }

        } finally {
            DBUtil.close(rs, pstmt, conn);
        }

        return students;
    }

    /**
     * 根据条件统计学生总数
     * Count total number of students by conditions
     */
    @Override
    public long countByConditions(Map<String, Object> conditions) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long count = 0;

        try {
            conn = DBUtil.getConnection();

            // 构建 SQL 语句 (Build SQL statement)
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM student WHERE 1=1");
            List<Object> params = new ArrayList<>();

            // 添加查询条件 (Add query conditions)
            sql = appendConditions(sql, conditions, params);

            // 执行查询 (Execute query)
            pstmt = conn.prepareStatement(sql.toString());
            setParameters(pstmt, params);

            System.out.println("执行 SQL: " + sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getLong(1);
            }

        } finally {
            DBUtil.close(rs, pstmt, conn);
        }

        return count;
    }

    /**
     * 根据 ID 查询学生
     * Query student by ID
     */
    @Override
    public Student selectById(Integer id) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Student student = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM student WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                student = mapResultSetToStudent(rs);
            }

        } finally {
            DBUtil.close(rs, pstmt, conn);
        }

        return student;
    }

    /**
     * 根据学号查询学生
     * Query student by student number
     */
    @Override
    public Student selectByStudentNo(String studentNo) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Student student = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM student WHERE student_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentNo);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                student = mapResultSetToStudent(rs);
            }

        } finally {
            DBUtil.close(rs, pstmt, conn);
        }

        return student;
    }

    /**
     * 插入新学生记录
     * Insert new student record
     */
    @Override
    public int insert(Student student) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            conn = DBUtil.getConnection();

            String sql = "INSERT INTO student (student_no, name, gender, age, major, " +
                    "class_name, phone, email, enrollment_date, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getStudentNo());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, student.getGender());
            pstmt.setInt(4, student.getAge());
            pstmt.setString(5, student.getMajor());
            pstmt.setString(6, student.getClassName());
            pstmt.setString(7, student.getPhone());
            pstmt.setString(8, student.getEmail());
            pstmt.setDate(9, student.getEnrollmentDate());
            pstmt.setInt(10, student.getStatus() != null ? student.getStatus() : 1);

            result = pstmt.executeUpdate();
            System.out.println("插入学生记录，影响行数: " + result);

        } finally {
            DBUtil.close(pstmt, conn);
        }

        return result;
    }

    /**
     * 更新学生信息
     * Update student information
     */
    @Override
    public int update(Student student) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            conn = DBUtil.getConnection();

            String sql = "UPDATE student SET student_no = ?, name = ?, gender = ?, " +
                    "age = ?, major = ?, class_name = ?, phone = ?, email = ?, " +
                    "enrollment_date = ?, status = ? WHERE id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getStudentNo());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, student.getGender());
            pstmt.setInt(4, student.getAge());
            pstmt.setString(5, student.getMajor());
            pstmt.setString(6, student.getClassName());
            pstmt.setString(7, student.getPhone());
            pstmt.setString(8, student.getEmail());
            pstmt.setDate(9, student.getEnrollmentDate());
            pstmt.setInt(10, student.getStatus());
            pstmt.setInt(11, student.getId());

            result = pstmt.executeUpdate();
            System.out.println("更新学生记录，影响行数: " + result);

        } finally {
            DBUtil.close(pstmt, conn);
        }

        return result;
    }

    /**
     * 根据 ID 删除学生
     * Delete student by ID
     */
    @Override
    public int deleteById(Integer id) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM student WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            result = pstmt.executeUpdate();
            System.out.println("删除学生记录，影响行数: " + result);

        } finally {
            DBUtil.close(pstmt, conn);
        }

        return result;
    }

    /**
     * 批量删除学生
     * Batch delete students
     */
    @Override
    public int deleteBatch(Integer[] ids) throws SQLException {
        if (ids == null || ids.length == 0) {
            return 0;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            conn = DBUtil.getConnection();

            // 构建 IN 子句 (Build IN clause)
            StringBuilder sql = new StringBuilder("DELETE FROM student WHERE id IN (");
            for (int i = 0; i < ids.length; i++) {
                sql.append("?");
                if (i < ids.length - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");

            pstmt = conn.prepareStatement(sql.toString());

            // 设置参数 (Set parameters)
            for (int i = 0; i < ids.length; i++) {
                pstmt.setInt(i + 1, ids[i]);
            }

            result = pstmt.executeUpdate();
            System.out.println("批量删除学生记录，影响行数: " + result);

        } finally {
            DBUtil.close(pstmt, conn);
        }

        return result;
    }

    /**
     * 查询所有学生
     * Query all students
     */
    @Override
    public List<Student> selectAll() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM student ORDER BY id DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }

        } finally {
            DBUtil.close(rs, pstmt, conn);
        }

        return students;
    }

    // ==================== 私有辅助方法 (Private Helper Methods) ====================

    /**
     * 将 ResultSet 映射为 Student 对象
     * Map ResultSet to Student object
     *
     * @param rs ResultSet 对象
     * @return Student 对象
     * @throws SQLException SQL 异常
     */
    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setStudentNo(rs.getString("student_no"));
        student.setName(rs.getString("name"));
        student.setGender(rs.getInt("gender"));
        student.setAge(rs.getInt("age"));
        student.setMajor(rs.getString("major"));
        student.setClassName(rs.getString("class_name"));
        student.setPhone(rs.getString("phone"));
        student.setEmail(rs.getString("email"));
        student.setEnrollmentDate(rs.getDate("enrollment_date"));
        student.setCreateTime(rs.getTimestamp("create_time"));
        student.setUpdateTime(rs.getTimestamp("update_time"));
        student.setStatus(rs.getInt("status"));
        return student;
    }

    /**
     * 根据条件 Map 追加 WHERE 子句
     * Append WHERE clause based on conditions map
     *
     * @param sql SQL StringBuilder
     * @param conditions 条件 Map
     * @param params 参数列表
     * @return 更新后的 SQL StringBuilder
     */
    private StringBuilder appendConditions(StringBuilder sql, Map<String, Object> conditions,
                                          List<Object> params) {
        if (conditions == null || conditions.isEmpty()) {
            return sql;
        }

        // 学号查询（精确匹配）(Student number - exact match)
        if (conditions.containsKey("studentNo") && StringUtil.isNotEmpty((String) conditions.get("studentNo"))) {
            sql.append(" AND student_no = ?");
            params.add(conditions.get("studentNo"));
        }

        // 姓名查询（模糊匹配）(Name - fuzzy match)
        if (conditions.containsKey("name") && StringUtil.isNotEmpty((String) conditions.get("name"))) {
            sql.append(" AND name LIKE ?");
            params.add(StringUtil.toLikePattern((String) conditions.get("name")));
        }

        // 性别查询 (Gender)
        if (conditions.containsKey("gender") && conditions.get("gender") != null) {
            sql.append(" AND gender = ?");
            params.add(conditions.get("gender"));
        }

        // 专业查询（模糊匹配）(Major - fuzzy match)
        if (conditions.containsKey("major") && StringUtil.isNotEmpty((String) conditions.get("major"))) {
            sql.append(" AND major LIKE ?");
            params.add(StringUtil.toLikePattern((String) conditions.get("major")));
        }

        // 班级查询（模糊匹配）(Class - fuzzy match)
        if (conditions.containsKey("className") && StringUtil.isNotEmpty((String) conditions.get("className"))) {
            sql.append(" AND class_name LIKE ?");
            params.add(StringUtil.toLikePattern((String) conditions.get("className")));
        }

        // 状态查询 (Status)
        if (conditions.containsKey("status") && conditions.get("status") != null) {
            sql.append(" AND status = ?");
            params.add(conditions.get("status"));
        }

        return sql;
    }

    /**
     * 设置 PreparedStatement 参数
     * Set PreparedStatement parameters
     *
     * @param pstmt PreparedStatement 对象
     * @param params 参数列表
     * @throws SQLException SQL 异常
     */
    private void setParameters(PreparedStatement pstmt, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            pstmt.setObject(i + 1, params.get(i));
        }
    }
}
