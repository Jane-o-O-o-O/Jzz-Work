package com.jzz.service;

import com.jzz.dao.StudentDAO;
import com.jzz.dao.StudentDAOImpl;
import com.jzz.model.PageResult;
import com.jzz.model.Student;
import com.jzz.util.StringUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 学生业务逻辑实现类
 * Student Service Implementation
 *
 * 实现 StudentService 接口，提供具体的业务逻辑处理
 * Implements StudentService interface, provides specific business logic processing
 *
 * @author Jzz
 * @version 1.0
 */
public class StudentServiceImpl implements StudentService {

    /**
     * 学生 DAO 对象
     * Student DAO object
     */
    private StudentDAO studentDAO = new StudentDAOImpl();

    /**
     * 分页查询学生列表（支持条件查询和排序）
     * Query student list with pagination (supports conditional query and sorting)
     */
    @Override
    public PageResult<Student> queryPage(Map<String, Object> conditions, String orderBy,
                                        String orderType, int currentPage, int pageSize) {
        try {
            // 参数校验和默认值设置 (Parameter validation and default value setting)
            if (currentPage < 1) {
                currentPage = 1;
            }
            if (pageSize < 1) {
                pageSize = 10;
            }
            if (StringUtil.isEmpty(orderBy)) {
                orderBy = "id"; // 默认按 ID 排序
            }
            if (StringUtil.isEmpty(orderType)) {
                orderType = "DESC"; // 默认降序
            }

            // 计算起始位置 (Calculate offset)
            int offset = (currentPage - 1) * pageSize;

            // 查询总记录数 (Query total count)
            long totalCount = studentDAO.countByConditions(conditions);

            // 查询当前页数据 (Query current page data)
            List<Student> students = studentDAO.selectByConditions(
                    conditions, orderBy, orderType, offset, pageSize);

            // 封装分页结果 (Encapsulate pagination result)
            return new PageResult<>(currentPage, pageSize, totalCount, students);

        } catch (SQLException e) {
            System.err.println("分页查询学生列表失败：" + e.getMessage());
            e.printStackTrace();
            // 返回空结果 (Return empty result)
            return new PageResult<>(currentPage, pageSize, 0L, null);
        }
    }

    /**
     * 根据 ID 查询学生
     * Query student by ID
     */
    @Override
    public Student queryById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }

        try {
            return studentDAO.selectById(id);
        } catch (SQLException e) {
            System.err.println("根据 ID 查询学生失败：" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加新学生
     * Add new student
     */
    @Override
    public boolean add(Student student) throws Exception {
        // 业务逻辑校验 (Business logic validation)
        validateStudent(student, true);

        // 检查学号是否已存在 (Check if student number already exists)
        if (isStudentNoExist(student.getStudentNo(), null)) {
            throw new Exception("学号 " + student.getStudentNo() + " 已存在，无法添加！");
        }

        try {
            int result = studentDAO.insert(student);
            return result > 0;
        } catch (SQLException e) {
            System.err.println("添加学生失败：" + e.getMessage());
            e.printStackTrace();
            throw new Exception("添加学生失败：" + e.getMessage());
        }
    }

    /**
     * 更新学生信息
     * Update student information
     */
    @Override
    public boolean update(Student student) throws Exception {
        // 业务逻辑校验 (Business logic validation)
        if (student.getId() == null || student.getId() <= 0) {
            throw new Exception("学生 ID 不能为空！");
        }

        validateStudent(student, false);

        // 检查学号是否与其他学生重复 (Check if student number conflicts with other students)
        if (isStudentNoExist(student.getStudentNo(), student.getId())) {
            throw new Exception("学号 " + student.getStudentNo() + " 已被其他学生使用，无法修改！");
        }

        try {
            int result = studentDAO.update(student);
            return result > 0;
        } catch (SQLException e) {
            System.err.println("更新学生信息失败：" + e.getMessage());
            e.printStackTrace();
            throw new Exception("更新学生信息失败：" + e.getMessage());
        }
    }

    /**
     * 根据 ID 删除学生
     * Delete student by ID
     */
    @Override
    public boolean delete(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }

        try {
            int result = studentDAO.deleteById(id);
            return result > 0;
        } catch (SQLException e) {
            System.err.println("删除学生失败：" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量删除学生
     * Batch delete students
     */
    @Override
    public int deleteBatch(Integer[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }

        try {
            return studentDAO.deleteBatch(ids);
        } catch (SQLException e) {
            System.err.println("批量删除学生失败：" + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 检查学号是否已存在
     * Check if student number already exists
     */
    @Override
    public boolean isStudentNoExist(String studentNo, Integer excludeId) {
        if (StringUtil.isEmpty(studentNo)) {
            return false;
        }

        try {
            Student existStudent = studentDAO.selectByStudentNo(studentNo);

            // 如果查询不到，说明不存在 (If not found, it doesn't exist)
            if (existStudent == null) {
                return false;
            }

            // 如果指定了要排除的 ID（更新场景）
            // If an ID to exclude is specified (update scenario)
            if (excludeId != null) {
                // 如果找到的学生 ID 等于要排除的 ID，说明是同一个学生，不算重复
                // If found student ID equals excluded ID, it's the same student, not a duplicate
                return !existStudent.getId().equals(excludeId);
            }

            // 添加场景，只要查到就是重复 (Add scenario, if found it's a duplicate)
            return true;

        } catch (SQLException e) {
            System.err.println("检查学号是否存在失败：" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ==================== 私有辅助方法 (Private Helper Methods) ====================

    /**
     * 校验学生信息
     * Validate student information
     *
     * @param student 学生对象
     * @param isAdd 是否是添加操作
     * @throws Exception 校验异常
     */
    private void validateStudent(Student student, boolean isAdd) throws Exception {
        if (student == null) {
            throw new Exception("学生信息不能为空！");
        }

        // 学号校验 (Student number validation)
        if (StringUtil.isEmpty(student.getStudentNo())) {
            throw new Exception("学号不能为空！");
        }
        if (student.getStudentNo().length() > 20) {
            throw new Exception("学号长度不能超过 20 个字符！");
        }

        // 姓名校验 (Name validation)
        if (StringUtil.isEmpty(student.getName())) {
            throw new Exception("姓名不能为空！");
        }
        if (student.getName().length() > 50) {
            throw new Exception("姓名长度不能超过 50 个字符！");
        }

        // 性别校验 (Gender validation)
        if (student.getGender() == null) {
            throw new Exception("性别不能为空！");
        }
        if (student.getGender() != 1 && student.getGender() != 2) {
            throw new Exception("性别值无效，必须为 1（男）或 2（女）！");
        }

        // 年龄校验 (Age validation)
        if (student.getAge() != null) {
            if (student.getAge() < 1 || student.getAge() > 150) {
                throw new Exception("年龄必须在 1 到 150 之间！");
            }
        }

        // 专业校验 (Major validation)
        if (StringUtil.isNotEmpty(student.getMajor()) && student.getMajor().length() > 100) {
            throw new Exception("专业名称长度不能超过 100 个字符！");
        }

        // 班级校验 (Class validation)
        if (StringUtil.isNotEmpty(student.getClassName()) && student.getClassName().length() > 50) {
            throw new Exception("班级名称长度不能超过 50 个字符！");
        }

        // 手机号校验 (Phone validation)
        if (StringUtil.isNotEmpty(student.getPhone())) {
            if (student.getPhone().length() > 20) {
                throw new Exception("手机号长度不能超过 20 个字符！");
            }
            // 简单的手机号格式校验（中国手机号）
            // Simple phone number format validation (China)
            if (!student.getPhone().matches("^1[3-9]\\d{9}$")) {
                // 如果不符合中国手机号格式，给出提示但不强制
                // If doesn't match China phone format, give a warning but don't enforce
                System.out.println("警告：手机号格式可能不正确：" + student.getPhone());
            }
        }

        // 邮箱校验 (Email validation)
        if (StringUtil.isNotEmpty(student.getEmail())) {
            if (student.getEmail().length() > 100) {
                throw new Exception("邮箱长度不能超过 100 个字符！");
            }
            // 简单的邮箱格式校验
            // Simple email format validation
            if (!student.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new Exception("邮箱格式不正确！");
            }
        }

        // 状态校验 (Status validation)
        if (student.getStatus() != null) {
            if (student.getStatus() < 1 || student.getStatus() > 3) {
                throw new Exception("状态值无效，必须为 1（在读）、2（休学）或 3（毕业）！");
            }
        }
    }
}
