package com.jzz.dao;

import com.jzz.model.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 学生数据访问接口
 * Student Data Access Object Interface
 *
 * 定义学生表的所有数据库操作方法
 * Defines all database operation methods for the student table
 *
 * @author Jzz
 * @version 1.0
 */
public interface StudentDAO {

    /**
     * 根据条件查询学生列表（支持分页和排序）
     * Query student list by conditions (supports pagination and sorting)
     *
     * @param conditions 查询条件 Map (Query conditions)
     *                   可包含：studentNo, name, gender, major, className, status 等
     *                   May include: studentNo, name, gender, major, className, status, etc.
     * @param orderBy 排序字段 (Order by field)，如 "name", "age" 等
     * @param orderType 排序类型 (Order type)："ASC" 或 "DESC"
     * @param offset 起始位置 (Start offset)，用于分页
     * @param limit 返回记录数 (Number of records to return)
     * @return 学生列表
     * @throws SQLException SQL 异常
     */
    List<Student> selectByConditions(Map<String, Object> conditions, String orderBy,
                                     String orderType, int offset, int limit) throws SQLException;

    /**
     * 根据条件统计学生总数
     * Count total number of students by conditions
     *
     * @param conditions 查询条件 Map
     * @return 学生总数
     * @throws SQLException SQL 异常
     */
    long countByConditions(Map<String, Object> conditions) throws SQLException;

    /**
     * 根据 ID 查询学生
     * Query student by ID
     *
     * @param id 学生 ID
     * @return Student 对象，如果不存在返回 null
     * @throws SQLException SQL 异常
     */
    Student selectById(Integer id) throws SQLException;

    /**
     * 根据学号查询学生
     * Query student by student number
     *
     * @param studentNo 学号
     * @return Student 对象，如果不存在返回 null
     * @throws SQLException SQL 异常
     */
    Student selectByStudentNo(String studentNo) throws SQLException;

    /**
     * 插入新学生记录
     * Insert new student record
     *
     * @param student 学生对象
     * @return 插入成功的记录数（1 表示成功，0 表示失败）
     * @throws SQLException SQL 异常
     */
    int insert(Student student) throws SQLException;

    /**
     * 更新学生信息
     * Update student information
     *
     * @param student 学生对象（必须包含 ID）
     * @return 更新成功的记录数（1 表示成功，0 表示失败）
     * @throws SQLException SQL 异常
     */
    int update(Student student) throws SQLException;

    /**
     * 根据 ID 删除学生
     * Delete student by ID
     *
     * @param id 学生 ID
     * @return 删除成功的记录数（1 表示成功，0 表示失败）
     * @throws SQLException SQL 异常
     */
    int deleteById(Integer id) throws SQLException;

    /**
     * 批量删除学生
     * Batch delete students
     *
     * @param ids 学生 ID 数组
     * @return 删除成功的记录数
     * @throws SQLException SQL 异常
     */
    int deleteBatch(Integer[] ids) throws SQLException;

    /**
     * 查询所有学生
     * Query all students
     *
     * @return 所有学生列表
     * @throws SQLException SQL 异常
     */
    List<Student> selectAll() throws SQLException;
}
