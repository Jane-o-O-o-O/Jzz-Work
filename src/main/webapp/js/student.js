/**
 * 学生信息管理系统 - 前端 JavaScript
 * Student Information Management System - Frontend JavaScript
 *
 * 功能说明 (Features):
 * 1. AJAX 数据交互
 * 2. 动态表格渲染
 * 3. 分页控制
 * 4. 排序功能
 * 5. 模态框管理
 * 6. 表单验证
 * 7. 用户交互处理
 *
 * @author Jzz
 * @version 1.0
 */

// ==================== 全局变量 (Global Variables) ====================

// 当前页码 (Current page number)
let currentPage = 1;

// 每页显示数量 (Records per page)
let pageSize = 10;

// 总记录数 (Total records)
let totalCount = 0;

// 总页数 (Total pages)
let totalPages = 0;

// 当前排序字段 (Current sort field)
let currentOrderBy = 'id';

// 当前排序类型 (Current sort type: ASC/DESC)
let currentOrderType = 'DESC';

// 是否为编辑模式 (Is edit mode)
let isEditMode = false;

// ==================== 页面加载时初始化 (Initialize on Page Load) ====================

/**
 * 页面加载完成后自动查询第一页数据
 * Automatically query first page data after page load
 */
window.onload = function() {
    console.log('页面加载完成，开始查询学生数据...');
    queryStudents();
};

// ==================== 查询功能 (Query Functions) ====================

/**
 * 查询学生列表
 * Query student list
 *
 * @param page 页码（可选，默认为当前页）
 */
function queryStudents(page) {
    // 如果指定了页码，则更新当前页 (Update current page if specified)
    if (page !== undefined) {
        currentPage = page;
    }

    // 获取查询条件 (Get query conditions)
    const studentNo = document.getElementById('queryStudentNo').value.trim();
    const name = document.getElementById('queryName').value.trim();
    const gender = document.getElementById('queryGender').value;
    const major = document.getElementById('queryMajor').value.trim();
    const className = document.getElementById('queryClassName').value.trim();
    const status = document.getElementById('queryStatus').value;

    // 构建查询参数 (Build query parameters)
    const params = new URLSearchParams({
        action: 'query',
        currentPage: currentPage,
        pageSize: pageSize,
        orderBy: currentOrderBy,
        orderType: currentOrderType
    });

    // 添加非空查询条件 (Add non-empty conditions)
    if (studentNo) params.append('studentNo', studentNo);
    if (name) params.append('name', name);
    if (gender) params.append('gender', gender);
    if (major) params.append('major', major);
    if (className) params.append('className', className);
    if (status) params.append('status', status);

    // 发送 AJAX 请求 (Send AJAX request)
    fetch('student?' + params.toString())
        .then(response => response.json())
        .then(result => {
            console.log('查询结果：', result);

            if (result.code === 200) {
                const pageResult = result.data;
                totalCount = pageResult.totalCount;
                totalPages = pageResult.totalPages;
                currentPage = pageResult.currentPage;

                // 渲染表格数据 (Render table data)
                renderTable(pageResult.data);

                // 渲染分页控件 (Render pagination controls)
                renderPagination();

                // 清除选中状态 (Clear selection)
                document.getElementById('selectAll').checked = false;

            } else {
                showMessage('查询失败：' + result.message, 'error');
            }
        })
        .catch(error => {
            console.error('查询出错：', error);
            showMessage('查询失败，请检查网络连接！', 'error');
        });
}

/**
 * 重置查询条件
 * Reset query conditions
 */
function resetQuery() {
    document.getElementById('queryForm').reset();
    currentPage = 1;
    currentOrderBy = 'id';
    currentOrderType = 'DESC';
    queryStudents();
}

/**
 * 改变每页显示数量
 * Change page size
 */
function changePageSize() {
    pageSize = parseInt(document.getElementById('pageSize').value);
    currentPage = 1;
    queryStudents();
}

// ==================== 表格渲染 (Table Rendering) ====================

/**
 * 渲染学生列表表格
 * Render student list table
 *
 * @param students 学生数据数组
 */
function renderTable(students) {
    const tbody = document.getElementById('studentTableBody');

    // 如果没有数据 (If no data)
    if (!students || students.length === 0) {
        tbody.innerHTML = '<tr><td colspan="13" class="empty-data">暂无数据</td></tr>';
        return;
    }

    // 构建表格行 (Build table rows)
    let html = '';
    students.forEach(student => {
        html += `
            <tr data-id="${student.id}">
                <td class="checkbox-cell">
                    <input type="checkbox" class="row-checkbox" value="${student.id}"
                           onchange="toggleRowSelection(this)">
                </td>
                <td>${student.id}</td>
                <td>${student.studentNo}</td>
                <td>${student.name}</td>
                <td>${student.genderText}</td>
                <td>${student.age || '-'}</td>
                <td>${student.major || '-'}</td>
                <td>${student.className || '-'}</td>
                <td>${student.phone || '-'}</td>
                <td>${student.email || '-'}</td>
                <td>${student.enrollmentDate || '-'}</td>
                <td>${student.statusText}</td>
                <td class="action-cell">
                    <button class="btn btn-info btn-small" onclick="showEditModal(${student.id})">
                        ✏️ 编辑
                    </button>
                    <button class="btn btn-danger btn-small" onclick="deleteStudent(${student.id})">
                        🗑️ 删除
                    </button>
                </td>
            </tr>
        `;
    });

    tbody.innerHTML = html;
}

/**
 * 渲染分页控件
 * Render pagination controls
 */
function renderPagination() {
    const pagination = document.getElementById('pagination');

    let html = '';

    // 首页按钮 (First page button)
    html += `<button onclick="queryStudents(1)" ${currentPage === 1 ? 'disabled' : ''}>首页</button>`;

    // 上一页按钮 (Previous page button)
    html += `<button onclick="queryStudents(${currentPage - 1})" ${currentPage === 1 ? 'disabled' : ''}>上一页</button>`;

    // 页码信息 (Page info)
    html += `<span class="pagination-info">第 ${currentPage} / ${totalPages} 页，共 ${totalCount} 条记录</span>`;

    // 下一页按钮 (Next page button)
    html += `<button onclick="queryStudents(${currentPage + 1})" ${currentPage === totalPages ? 'disabled' : ''}>下一页</button>`;

    // 末页按钮 (Last page button)
    html += `<button onclick="queryStudents(${totalPages})" ${currentPage === totalPages ? 'disabled' : ''}>末页</button>`;

    pagination.innerHTML = html;
}

// ==================== 排序功能 (Sorting Functions) ====================

/**
 * 表格排序
 * Sort table
 *
 * @param column 排序字段
 */
function sortTable(column) {
    // 如果点击的是当前排序字段，则切换排序方向 (Toggle sort direction if same column)
    if (currentOrderBy === column) {
        currentOrderType = currentOrderType === 'ASC' ? 'DESC' : 'ASC';
    } else {
        currentOrderBy = column;
        currentOrderType = 'ASC';
    }

    // 更新表头样式 (Update table header styles)
    document.querySelectorAll('th.sortable').forEach(th => {
        th.classList.remove('asc', 'desc');
    });

    const th = document.querySelector(`th[data-column="${column}"]`);
    if (th) {
        th.classList.add(currentOrderType.toLowerCase());
    }

    // 重新查询 (Re-query)
    currentPage = 1;
    queryStudents();
}

// ==================== 添加/编辑功能 (Add/Edit Functions) ====================

/**
 * 显示添加学生模态框
 * Show add student modal
 */
function showAddModal() {
    isEditMode = false;
    document.getElementById('modalTitle').textContent = '➕ 添加学生';
    document.getElementById('studentForm').reset();
    document.getElementById('studentId').value = '';
    document.getElementById('studentModal').classList.add('show');
}

/**
 * 显示编辑学生模态框
 * Show edit student modal
 *
 * @param id 学生 ID
 */
function showEditModal(id) {
    isEditMode = true;
    document.getElementById('modalTitle').textContent = '✏️ 编辑学生';

    // 通过 AJAX 获取学生详细信息 (Get student details via AJAX)
    fetch(`student?action=getById&id=${id}`)
        .then(response => response.json())
        .then(result => {
            if (result.code === 200) {
                const student = result.data;

                // 填充表单数据 (Populate form data)
                document.getElementById('studentId').value = student.id;
                document.getElementById('studentNo').value = student.studentNo;
                document.getElementById('name').value = student.name;
                document.getElementById('gender').value = student.gender;
                document.getElementById('age').value = student.age || '';
                document.getElementById('major').value = student.major || '';
                document.getElementById('className').value = student.className || '';
                document.getElementById('phone').value = student.phone || '';
                document.getElementById('email').value = student.email || '';
                document.getElementById('enrollmentDate').value = student.enrollmentDate || '';
                document.getElementById('status').value = student.status;

                // 显示模态框 (Show modal)
                document.getElementById('studentModal').classList.add('show');
            } else {
                showMessage('获取学生信息失败：' + result.message, 'error');
            }
        })
        .catch(error => {
            console.error('获取学生信息出错：', error);
            showMessage('获取学生信息失败！', 'error');
        });
}

/**
 * 关闭模态框
 * Close modal
 */
function closeModal() {
    document.getElementById('studentModal').classList.remove('show');
    document.getElementById('studentForm').reset();
}

/**
 * 保存学生信息（添加或编辑）
 * Save student information (add or edit)
 */
function saveStudent() {
    // 获取表单数据 (Get form data)
    const form = document.getElementById('studentForm');
    const formData = new FormData(form);

    // 表单验证 (Form validation)
    if (!formData.get('studentNo')) {
        showMessage('学号不能为空！', 'warning');
        return;
    }
    if (!formData.get('name')) {
        showMessage('姓名不能为空！', 'warning');
        return;
    }
    if (!formData.get('gender')) {
        showMessage('请选择性别！', 'warning');
        return;
    }

    // 设置操作类型 (Set action type)
    const action = isEditMode ? 'update' : 'add';
    formData.append('action', action);

    // 发送 AJAX 请求 (Send AJAX request)
    fetch('student', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(result => {
            if (result.code === 200) {
                showMessage(result.message, 'success');
                closeModal();
                queryStudents(); // 刷新列表 (Refresh list)
            } else {
                showMessage(result.message, 'error');
            }
        })
        .catch(error => {
            console.error('保存出错：', error);
            showMessage('保存失败，请检查网络连接！', 'error');
        });
}

// ==================== 删除功能 (Delete Functions) ====================

/**
 * 删除单个学生
 * Delete single student
 *
 * @param id 学生 ID
 */
function deleteStudent(id) {
    if (!confirm('确定要删除这个学生吗？删除后无法恢复！')) {
        return;
    }

    // 发送删除请求 (Send delete request)
    fetch(`student?action=delete&id=${id}`, {
        method: 'POST'
    })
        .then(response => response.json())
        .then(result => {
            if (result.code === 200) {
                showMessage(result.message, 'success');
                queryStudents(); // 刷新列表 (Refresh list)
            } else {
                showMessage(result.message, 'error');
            }
        })
        .catch(error => {
            console.error('删除出错：', error);
            showMessage('删除失败，请检查网络连接！', 'error');
        });
}

/**
 * 批量删除学生
 * Batch delete students
 */
function deleteBatch() {
    // 获取所有选中的复选框 (Get all checked checkboxes)
    const checkboxes = document.querySelectorAll('.row-checkbox:checked');

    if (checkboxes.length === 0) {
        showMessage('请先选择要删除的学生！', 'warning');
        return;
    }

    if (!confirm(`确定要删除选中的 ${checkboxes.length} 个学生吗？删除后无法恢复！`)) {
        return;
    }

    // 构建 FormData (Build FormData)
    const formData = new FormData();
    formData.append('action', 'deleteBatch');
    checkboxes.forEach(checkbox => {
        formData.append('ids[]', checkbox.value);
    });

    // 发送批量删除请求 (Send batch delete request)
    fetch('student', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(result => {
            if (result.code === 200) {
                showMessage(result.message, 'success');
                queryStudents(); // 刷新列表 (Refresh list)
            } else {
                showMessage(result.message, 'error');
            }
        })
        .catch(error => {
            console.error('批量删除出错：', error);
            showMessage('批量删除失败，请检查网络连接！', 'error');
        });
}

// ==================== 行选择功能 (Row Selection Functions) ====================

/**
 * 全选/取消全选
 * Select all / Deselect all
 */
function toggleSelectAll() {
    const selectAll = document.getElementById('selectAll');
    const checkboxes = document.querySelectorAll('.row-checkbox');

    checkboxes.forEach(checkbox => {
        checkbox.checked = selectAll.checked;
        toggleRowHighlight(checkbox.closest('tr'), selectAll.checked);
    });
}

/**
 * 切换单行选择状态
 * Toggle single row selection
 *
 * @param checkbox 复选框元素
 */
function toggleRowSelection(checkbox) {
    const row = checkbox.closest('tr');
    toggleRowHighlight(row, checkbox.checked);

    // 更新全选复选框状态 (Update select all checkbox state)
    updateSelectAllState();
}

/**
 * 切换行高亮样式
 * Toggle row highlight style
 *
 * @param row 表格行元素
 * @param selected 是否选中
 */
function toggleRowHighlight(row, selected) {
    if (selected) {
        row.classList.add('selected');
    } else {
        row.classList.remove('selected');
    }
}

/**
 * 更新全选复选框状态
 * Update select all checkbox state
 */
function updateSelectAllState() {
    const selectAll = document.getElementById('selectAll');
    const checkboxes = document.querySelectorAll('.row-checkbox');
    const checkedBoxes = document.querySelectorAll('.row-checkbox:checked');

    selectAll.checked = checkboxes.length > 0 && checkboxes.length === checkedBoxes.length;
}

// ==================== 消息提示功能 (Message Alert Functions) ====================

/**
 * 显示消息提示
 * Show message alert
 *
 * @param message 消息内容
 * @param type 消息类型：success, error, warning, info
 */
function showMessage(message, type) {
    const alertDiv = document.getElementById('alertMessage');
    alertDiv.textContent = message;
    alertDiv.className = `alert alert-${type} show`;

    // 3 秒后自动隐藏 (Auto hide after 3 seconds)
    setTimeout(() => {
        alertDiv.classList.remove('show');
    }, 3000);
}

// ==================== 键盘事件处理 (Keyboard Event Handling) ====================

/**
 * 监听 ESC 键关闭模态框
 * Listen for ESC key to close modal
 */
document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape') {
        closeModal();
    }
});

/**
 * 监听 Enter 键提交查询
 * Listen for Enter key to submit query
 */
document.getElementById('queryForm').addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        queryStudents(1);
    }
});

/**
 * 点击模态框外部区域关闭模态框
 * Click outside modal to close
 */
document.getElementById('studentModal').addEventListener('click', function(event) {
    if (event.target === this) {
        closeModal();
    }
});

console.log('学生信息管理系统 JavaScript 加载完成！');
