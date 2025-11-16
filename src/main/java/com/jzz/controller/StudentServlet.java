package com.jzz.controller;

import com.jzz.model.PageResult;
import com.jzz.model.Result;
import com.jzz.model.Student;
import com.jzz.service.StudentService;
import com.jzz.service.StudentServiceImpl;
import com.jzz.util.JsonUtil;
import com.jzz.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 学生控制器 Servlet
 * Student Controller Servlet
 *
 * 处理所有与学生相关的 HTTP 请求
 * Handles all HTTP requests related to students
 *
 * URL 映射：/student
 * 通过 action 参数区分不同的操作
 * Different operations are distinguished by the action parameter
 *
 * @author Jzz
 * @version 1.0
 */
@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    /**
     * 学生业务逻辑服务
     * Student service
     */
    private StudentService studentService = new StudentServiceImpl();

    /**
     * 处理 GET 请求
     * Handle GET requests
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // GET 和 POST 统一处理 (Unified processing for GET and POST)
        doPost(request, response);
    }

    /**
     * 处理 POST 请求
     * Handle POST requests
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 设置请求和响应编码 (Set request and response encoding)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        // 获取操作类型 (Get action type)
        String action = request.getParameter("action");

        System.out.println("StudentServlet 收到请求，action: " + action);

        try {
            // 根据 action 参数分发到不同的处理方法
            // Dispatch to different handler methods based on action parameter
            if ("query".equals(action)) {
                // 分页查询 (Paginated query)
                handleQuery(request, response);
            } else if ("add".equals(action)) {
                // 添加学生 (Add student)
                handleAdd(request, response);
            } else if ("update".equals(action)) {
                // 更新学生 (Update student)
                handleUpdate(request, response);
            } else if ("delete".equals(action)) {
                // 删除学生 (Delete student)
                handleDelete(request, response);
            } else if ("deleteBatch".equals(action)) {
                // 批量删除 (Batch delete)
                handleDeleteBatch(request, response);
            } else if ("getById".equals(action)) {
                // 根据 ID 查询 (Query by ID)
                handleGetById(request, response);
            } else {
                // 未知操作 (Unknown action)
                writeJson(response, Result.badRequest("未知的操作类型：" + action));
            }
        } catch (Exception e) {
            System.err.println("处理请求时发生异常：" + e.getMessage());
            e.printStackTrace();
            writeJson(response, Result.error("服务器内部错误：" + e.getMessage()));
        }
    }

    /**
     * 处理分页查询请求
     * Handle paginated query request
     */
    private void handleQuery(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // 获取查询条件 (Get query conditions)
        Map<String, Object> conditions = new HashMap<>();

        String studentNo = request.getParameter("studentNo");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String major = request.getParameter("major");
        String className = request.getParameter("className");
        String status = request.getParameter("status");

        // 只添加非空条件 (Only add non-empty conditions)
        if (StringUtil.isNotEmpty(studentNo)) {
            conditions.put("studentNo", studentNo);
        }
        if (StringUtil.isNotEmpty(name)) {
            conditions.put("name", name);
        }
        if (StringUtil.isNotEmpty(gender)) {
            conditions.put("gender", StringUtil.toInt(gender));
        }
        if (StringUtil.isNotEmpty(major)) {
            conditions.put("major", major);
        }
        if (StringUtil.isNotEmpty(className)) {
            conditions.put("className", className);
        }
        if (StringUtil.isNotEmpty(status)) {
            conditions.put("status", StringUtil.toInt(status));
        }

        // 获取排序参数 (Get sorting parameters)
        String orderBy = request.getParameter("orderBy");
        String orderType = request.getParameter("orderType");

        // 获取分页参数 (Get pagination parameters)
        int currentPage = StringUtil.toInt(request.getParameter("currentPage"), 1);
        int pageSize = StringUtil.toInt(request.getParameter("pageSize"), 10);

        // 调用业务逻辑层查询 (Call service layer for query)
        PageResult<Student> pageResult = studentService.queryPage(
                conditions, orderBy, orderType, currentPage, pageSize);

        // 返回成功结果 (Return success result)
        writeJson(response, Result.success("查询成功", pageResult));
    }

    /**
     * 处理添加学生请求
     * Handle add student request
     */
    private void handleAdd(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            // 获取表单参数并构建 Student 对象 (Get form parameters and build Student object)
            Student student = buildStudentFromRequest(request);

            // 调用业务逻辑层添加 (Call service layer to add)
            boolean success = studentService.add(student);

            if (success) {
                writeJson(response, Result.success("添加学生成功！"));
            } else {
                writeJson(response, Result.error("添加学生失败！"));
            }

        } catch (Exception e) {
            System.err.println("添加学生失败：" + e.getMessage());
            writeJson(response, Result.error(e.getMessage()));
        }
    }

    /**
     * 处理更新学生请求
     * Handle update student request
     */
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            // 获取学生 ID (Get student ID)
            Integer id = StringUtil.toInt(request.getParameter("id"));
            if (id == null) {
                writeJson(response, Result.badRequest("学生 ID 不能为空！"));
                return;
            }

            // 获取表单参数并构建 Student 对象 (Get form parameters and build Student object)
            Student student = buildStudentFromRequest(request);
            student.setId(id);

            // 调用业务逻辑层更新 (Call service layer to update)
            boolean success = studentService.update(student);

            if (success) {
                writeJson(response, Result.success("更新学生信息成功！"));
            } else {
                writeJson(response, Result.error("更新学生信息失败！"));
            }

        } catch (Exception e) {
            System.err.println("更新学生失败：" + e.getMessage());
            writeJson(response, Result.error(e.getMessage()));
        }
    }

    /**
     * 处理删除学生请求
     * Handle delete student request
     */
    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // 获取学生 ID (Get student ID)
        Integer id = StringUtil.toInt(request.getParameter("id"));

        if (id == null) {
            writeJson(response, Result.badRequest("学生 ID 不能为空！"));
            return;
        }

        // 调用业务逻辑层删除 (Call service layer to delete)
        boolean success = studentService.delete(id);

        if (success) {
            writeJson(response, Result.success("删除学生成功！"));
        } else {
            writeJson(response, Result.error("删除学生失败！"));
        }
    }

    /**
     * 处理批量删除学生请求
     * Handle batch delete students request
     */
    private void handleDeleteBatch(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // 获取 ID 数组 (Get ID array)
        String[] idStrings = request.getParameterValues("ids[]");

        if (idStrings == null || idStrings.length == 0) {
            writeJson(response, Result.badRequest("请选择要删除的学生！"));
            return;
        }

        // 转换为 Integer 数组 (Convert to Integer array)
        Integer[] ids = StringUtil.toIntArray(idStrings);

        // 调用业务逻辑层批量删除 (Call service layer to batch delete)
        int count = studentService.deleteBatch(ids);

        if (count > 0) {
            writeJson(response, Result.success("成功删除 " + count + " 条记录！"));
        } else {
            writeJson(response, Result.error("批量删除失败！"));
        }
    }

    /**
     * 处理根据 ID 查询学生请求
     * Handle query student by ID request
     */
    private void handleGetById(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // 获取学生 ID (Get student ID)
        Integer id = StringUtil.toInt(request.getParameter("id"));

        if (id == null) {
            writeJson(response, Result.badRequest("学生 ID 不能为空！"));
            return;
        }

        // 调用业务逻辑层查询 (Call service layer to query)
        Student student = studentService.queryById(id);

        if (student != null) {
            writeJson(response, Result.success("查询成功", student));
        } else {
            writeJson(response, Result.error("学生不存在！"));
        }
    }

    // ==================== 私有辅助方法 (Private Helper Methods) ====================

    /**
     * 从请求中构建 Student 对象
     * Build Student object from request
     *
     * @param request HTTP 请求
     * @return Student 对象
     */
    private Student buildStudentFromRequest(HttpServletRequest request) {
        Student student = new Student();

        student.setStudentNo(request.getParameter("studentNo"));
        student.setName(request.getParameter("name"));
        student.setGender(StringUtil.toInt(request.getParameter("gender")));
        student.setAge(StringUtil.toInt(request.getParameter("age")));
        student.setMajor(request.getParameter("major"));
        student.setClassName(request.getParameter("className"));
        student.setPhone(request.getParameter("phone"));
        student.setEmail(request.getParameter("email"));

        // 处理日期 (Handle date)
        String enrollmentDateStr = request.getParameter("enrollmentDate");
        if (StringUtil.isNotEmpty(enrollmentDateStr)) {
            try {
                student.setEnrollmentDate(Date.valueOf(enrollmentDateStr));
            } catch (IllegalArgumentException e) {
                System.err.println("日期格式错误：" + enrollmentDateStr);
            }
        }

        student.setStatus(StringUtil.toInt(request.getParameter("status"), 1));

        return student;
    }

    /**
     * 将结果对象转换为 JSON 并写入响应
     * Convert result object to JSON and write to response
     *
     * @param response HTTP 响应
     * @param result 结果对象
     * @throws IOException IO 异常
     */
    private void writeJson(HttpServletResponse response, Result<?> result) throws IOException {
        PrintWriter out = response.getWriter();
        String json = JsonUtil.toJson(result);
        System.out.println("返回 JSON: " + json);
        out.print(json);
        out.flush();
    }
}
