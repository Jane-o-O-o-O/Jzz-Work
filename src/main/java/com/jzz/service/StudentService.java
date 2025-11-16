package com.jzz.service;

import com.jzz.model.PageResult;
import com.jzz.model.Student;

import java.util.Map;

/**
 * 学生业务逻辑接口
 * Student Service Interface
 *
 * 定义学生相关的业务逻辑方法
 * Defines business logic methods related to students
 *
 * @author Jzz
 * @version 1.0
 */
public interface StudentService {

    /**
     * 分页查询学生列表（支持条件查询和排序）
     * Query student list with pagination (supports conditional query and sorting)
     *
     * @param conditions 查询条件 Map
     * @param orderBy 排序字段
     * @param orderType 排序类型："ASC" 或 "DESC"
     * @param currentPage 当前页码（从 1 开始）
     * @param pageSize 每页记录数
     * @return PageResult 分页结果对象
     */
    PageResult<Student> queryPage(Map<String, Object> conditions, String orderBy,
                                  String orderType, int currentPage, int pageSize);

    /**
     * 根据 ID 查询学生
     * Query student by ID
     *
     * @param id 学生 ID
     * @return Student 对象，如果不存在返回 null
     */
    Student queryById(Integer id);

    /**
     * 添加新学生
     * Add new student
     *
     * 会进行业务逻辑校验，如学号是否重复等
     * Performs business logic validation, such as checking for duplicate student numbers
     *
     * @param student 学生对象
     * @return true 添加成功，false 添加失败
     * @throws Exception 如果学号已存在或其他业务异常
     */
    boolean add(Student student) throws Exception;

    /**
     * 更新学生信息
     * Update student information
     *
     * @param student 学生对象（必须包含 ID）
     * @return true 更新成功，false 更新失败
     * @throws Exception 业务异常
     */
    boolean update(Student student) throws Exception;

    /**
     * 根据 ID 删除学生
     * Delete student by ID
     *
     * @param id 学生 ID
     * @return true 删除成功，false 删除失败
     */
    boolean delete(Integer id);

    /**
     * 批量删除学生
     * Batch delete students
     *
     * @param ids 学生 ID 数组
     * @return 删除成功的记录数
     */
    int deleteBatch(Integer[] ids);

    /**
     * 检查学号是否已存在
     * Check if student number already exists
     *
     * @param studentNo 学号
     * @param excludeId 要排除的学生 ID（用于更新时的检查，可为 null）
     * @return true 已存在，false 不存在
     */
    boolean isStudentNoExist(String studentNo, Integer excludeId);
}
