<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    学生信息管理系统 - 主页面
    Student Information Management System - Main Page

    功能说明 (Features):
    1. 学生列表展示，支持分页、排序
    2. 条件查询，支持模糊搜索
    3. 添加、编辑、删除学生信息
    4. 批量删除功能
    5. 所有操作通过 AJAX 实现，无需刷新页面

    @author Jzz
    @version 1.0
--%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>学生信息管理系统 - Student Information Management System</title>

    <!-- 引入样式表 (Import Stylesheet) -->
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">

    <!-- ==================== 页面标题 (Page Title) ==================== -->
    <h1>📚 学生信息管理系统</h1>

    <!-- ==================== 消息提示区 (Message Alert Area) ==================== -->
    <div id="alertMessage" class="alert"></div>

    <!-- ==================== 查询表单 (Query Form) ==================== -->
    <div class="query-form">
        <h3>🔍 查询条件</h3>
        <form id="queryForm">
            <div class="form-row">
                <div class="form-group">
                    <label for="queryStudentNo">学号：</label>
                    <input type="text" id="queryStudentNo" name="studentNo" placeholder="请输入学号">
                </div>
                <div class="form-group">
                    <label for="queryName">姓名：</label>
                    <input type="text" id="queryName" name="name" placeholder="请输入姓名（支持模糊查询）">
                </div>
                <div class="form-group">
                    <label for="queryGender">性别：</label>
                    <select id="queryGender" name="gender">
                        <option value="">全部</option>
                        <option value="1">男</option>
                        <option value="2">女</option>
                    </select>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="queryMajor">专业：</label>
                    <input type="text" id="queryMajor" name="major" placeholder="请输入专业（支持模糊查询）">
                </div>
                <div class="form-group">
                    <label for="queryClassName">班级：</label>
                    <input type="text" id="queryClassName" name="className" placeholder="请输入班级（支持模糊查询）">
                </div>
                <div class="form-group">
                    <label for="queryStatus">状态：</label>
                    <select id="queryStatus" name="status">
                        <option value="">全部</option>
                        <option value="1">在读</option>
                        <option value="2">休学</option>
                        <option value="3">毕业</option>
                    </select>
                </div>
            </div>

            <div class="form-row">
                <button type="button" class="btn btn-primary" onclick="queryStudents()">🔍 查询</button>
                <button type="reset" class="btn btn-secondary" onclick="resetQuery()">🔄 重置</button>
            </div>
        </form>
    </div>

    <!-- ==================== 工具栏 (Toolbar) ==================== -->
    <div class="toolbar">
        <div class="toolbar-left">
            <button class="btn btn-success" onclick="showAddModal()">➕ 添加学生</button>
            <button class="btn btn-danger" onclick="deleteBatch()">🗑️ 批量删除</button>
        </div>
        <div class="toolbar-right">
            <span>每页显示：</span>
            <select id="pageSize" onchange="changePageSize()">
                <option value="5">5 条</option>
                <option value="10" selected>10 条</option>
                <option value="20">20 条</option>
                <option value="50">50 条</option>
            </select>
        </div>
    </div>

    <!-- ==================== 学生列表表格 (Student List Table) ==================== -->
    <div class="table-container">
        <table id="studentTable">
            <thead>
            <tr>
                <th class="checkbox-cell">
                    <input type="checkbox" id="selectAll" onchange="toggleSelectAll()">
                </th>
                <th class="sortable" data-column="id" onclick="sortTable('id')">ID</th>
                <th class="sortable" data-column="student_no" onclick="sortTable('student_no')">学号</th>
                <th class="sortable" data-column="name" onclick="sortTable('name')">姓名</th>
                <th>性别</th>
                <th class="sortable" data-column="age" onclick="sortTable('age')">年龄</th>
                <th>专业</th>
                <th>班级</th>
                <th>联系电话</th>
                <th>邮箱</th>
                <th class="sortable" data-column="enrollment_date" onclick="sortTable('enrollment_date')">入学日期</th>
                <th>状态</th>
                <th class="action-cell">操作</th>
            </tr>
            </thead>
            <tbody id="studentTableBody">
            <!-- 数据通过 JavaScript 动态加载 (Data loaded dynamically via JavaScript) -->
            </tbody>
        </table>
    </div>

    <!-- ==================== 分页控件 (Pagination Controls) ==================== -->
    <div class="pagination" id="pagination">
        <!-- 分页按钮通过 JavaScript 动态生成 (Pagination buttons generated dynamically via JavaScript) -->
    </div>

</div>

<!-- ==================== 添加/编辑学生模态框 (Add/Edit Student Modal) ==================== -->
<div id="studentModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2 id="modalTitle">添加学生</h2>
            <span class="close" onclick="closeModal()">&times;</span>
        </div>
        <div class="modal-body">
            <form id="studentForm">
                <!-- 隐藏字段：学生 ID (Hidden field: Student ID) -->
                <input type="hidden" id="studentId" name="id">

                <div class="form-group">
                    <label for="studentNo" class="required">学号</label>
                    <input type="text" id="studentNo" name="studentNo" required
                           placeholder="请输入学号，如：2021001">
                </div>

                <div class="form-group">
                    <label for="name" class="required">姓名</label>
                    <input type="text" id="name" name="name" required
                           placeholder="请输入姓名">
                </div>

                <div class="form-group">
                    <label for="gender" class="required">性别</label>
                    <select id="gender" name="gender" required>
                        <option value="">请选择</option>
                        <option value="1">男</option>
                        <option value="2">女</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="age">年龄</label>
                    <input type="number" id="age" name="age" min="1" max="150"
                           placeholder="请输入年龄">
                </div>

                <div class="form-group">
                    <label for="major">专业</label>
                    <input type="text" id="major" name="major"
                           placeholder="请输入专业，如：计算机科学与技术">
                </div>

                <div class="form-group">
                    <label for="className">班级</label>
                    <input type="text" id="className" name="className"
                           placeholder="请输入班级，如：计科2101">
                </div>

                <div class="form-group">
                    <label for="phone">联系电话</label>
                    <input type="tel" id="phone" name="phone"
                           placeholder="请输入手机号，如：13800138000">
                </div>

                <div class="form-group">
                    <label for="email">邮箱</label>
                    <input type="email" id="email" name="email"
                           placeholder="请输入邮箱，如：student@example.com">
                </div>

                <div class="form-group">
                    <label for="enrollmentDate">入学日期</label>
                    <input type="date" id="enrollmentDate" name="enrollmentDate">
                </div>

                <div class="form-group">
                    <label for="status">状态</label>
                    <select id="status" name="status">
                        <option value="1">在读</option>
                        <option value="2">休学</option>
                        <option value="3">毕业</option>
                    </select>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" onclick="closeModal()">取消</button>
            <button type="button" class="btn btn-primary" onclick="saveStudent()">保存</button>
        </div>
    </div>
</div>

<!-- 引入 JavaScript 文件 (Import JavaScript File) -->
<script src="js/student.js"></script>

</body>
</html>
