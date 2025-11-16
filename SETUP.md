# 学生信息管理系统 - 部署指南

## 📋 项目简介

这是一个基于 Java Web 的学生信息管理系统，实现了完整的 CRUD（增删改查）功能。所有操作通过 AJAX 异步实现，无需刷新页面，提供流畅的用户体验。

### 主要功能

1. **查询功能**
   - 支持多条件组合查询
   - 支持模糊查询（姓名、专业、班级）
   - 支持分页显示
   - 支持字段排序（升序/降序）
   - 表格交互优化（隔行变色、悬停高亮、批量选择）

2. **删除功能**
   - 单条记录删除
   - 批量删除

3. **编辑功能**
   - 弹窗编辑，无需跳转页面
   - 表单验证

4. **添加功能**
   - 弹窗添加新学生
   - 实时验证

## 🛠️ 技术栈

- **后端**: Java 17, Servlet 6.0 (Jakarta EE)
- **服务器**: Apache Tomcat 10.1.48
- **数据库**: MySQL 8.0+
- **前端**: HTML5, CSS3, JavaScript (ES6+)
- **构建工具**: Maven 3.6+

## 📁 项目结构

```
Jzz-Work/
├── database/                    # 数据库脚本
│   └── init.sql                # 数据库初始化脚本
├── src/
│   └── main/
│       ├── java/
│       │   └── com/jzz/
│       │       ├── controller/ # Servlet 控制器
│       │       ├── dao/        # 数据访问层
│       │       ├── filter/     # 过滤器
│       │       ├── model/      # 实体类
│       │       ├── service/    # 业务逻辑层
│       │       └── util/       # 工具类
│       └── webapp/
│           ├── WEB-INF/
│           │   ├── web.xml     # Web 配置文件
│           │   └── db.properties # 数据库配置
│           ├── css/            # 样式文件
│           ├── js/             # JavaScript 文件
│           └── index.jsp       # 主页面
├── pom.xml                     # Maven 配置文件
└── README.md                   # 项目说明文档
```

## 🚀 环境要求

### 必需软件

1. **JDK 17** 或更高版本
   - 下载地址: https://www.oracle.com/java/technologies/downloads/

2. **Apache Tomcat 10.1.48**
   - 下载地址: https://tomcat.apache.org/download-10.cgi
   - **重要**: 必须使用 Tomcat 10.x，因为它支持 Jakarta EE

3. **MySQL 8.0** 或更高版本
   - 下载地址: https://dev.mysql.com/downloads/mysql/

4. **Maven 3.6+** (可选，如果使用 Maven 构建)
   - 下载地址: https://maven.apache.org/download.cgi

## 📝 部署步骤

### 第一步：安装和配置 MySQL

1. 启动 MySQL 服务

2. 执行数据库初始化脚本：
   ```bash
   mysql -u root -p < database/init.sql
   ```

   或者在 MySQL 客户端中执行：
   ```sql
   source /path/to/database/init.sql;
   ```

3. 验证数据库创建成功：
   ```sql
   USE student_system;
   SHOW TABLES;
   SELECT COUNT(*) FROM student;
   ```

### 第二步：配置数据库连接

编辑文件 `src/main/webapp/WEB-INF/db.properties`，修改数据库连接信息：

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/student_system?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8&allowPublicKeyRetrieval=true
jdbc.username=root
jdbc.password=你的数据库密码
```

**注意**: 请将 `jdbc.password` 修改为您的 MySQL 数据库密码。

### 第三步：编译项目

#### 方法 1: 使用 Maven 编译

```bash
cd /path/to/Jzz-Work
mvn clean package
```

编译完成后，会在 `target` 目录下生成 `student-system.war` 文件。

#### 方法 2: 使用 IDE 编译

1. 使用 IntelliJ IDEA 或 Eclipse 导入项目
2. 配置 Tomcat 服务器
3. 直接运行项目

### 第四步：部署到 Tomcat

1. 将编译好的 `student-system.war` 文件复制到 Tomcat 的 `webapps` 目录：
   ```bash
   cp target/student-system.war /path/to/tomcat/webapps/
   ```

2. 或者，将整个 `src/main/webapp` 目录复制到 `webapps` 下并重命名：
   ```bash
   cp -r src/main/webapp /path/to/tomcat/webapps/student-system
   ```

3. 确保将编译好的 `.class` 文件放到正确的位置：
   ```bash
   cp -r target/classes/* /path/to/tomcat/webapps/student-system/WEB-INF/classes/
   ```

### 第五步：启动 Tomcat

#### Windows 系统:
```cmd
cd C:\path\to\tomcat\bin
startup.bat
```

#### Linux/Mac 系统:
```bash
cd /path/to/tomcat/bin
./startup.sh
```

### 第六步：访问系统

打开浏览器，访问：
```
http://localhost:8080/student-system/
```

默认端口是 8080，如果修改了 Tomcat 端口，请相应调整。

## 🔍 测试账号

系统已预置测试数据：

- **学生数据**: 20 条测试学生记录
- **用户账号**:
  - 管理员: `admin` / `admin123`
  - 普通用户: `user` / `user123`

## ⚙️ 配置说明

### 修改服务器端口

编辑 Tomcat 的 `conf/server.xml` 文件，修改以下配置：

```xml
<Connector port="8080" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443" />
```

将 `port="8080"` 修改为您想要的端口号。

### 修改数据库配置

如果需要连接到其他数据库服务器，请修改 `src/main/webapp/WEB-INF/db.properties` 文件中的配置。

## 🐛 常见问题

### 1. 数据库连接失败

**问题**: 提示 "数据库连接失败"

**解决方案**:
- 检查 MySQL 服务是否启动
- 检查 `db.properties` 中的用户名和密码是否正确
- 检查数据库 `student_system` 是否已创建
- 检查 MySQL 驱动 jar 包是否在 `WEB-INF/lib` 目录下

### 2. 页面 404 错误

**问题**: 访问页面时显示 404

**解决方案**:
- 检查 Tomcat 是否正常启动
- 检查访问路径是否正确
- 检查 WAR 包是否正确部署到 `webapps` 目录
- 查看 Tomcat 日志 `logs/catalina.out` 了解详细错误

### 3. 中文乱码问题

**问题**: 页面显示中文乱码

**解决方案**:
- 确保 `EncodingFilter` 正常工作
- 检查浏览器编码设置为 UTF-8
- 检查数据库连接 URL 中是否包含 `characterEncoding=utf8`
- 检查 JSP 页面头部是否有 `<%@ page contentType="text/html;charset=UTF-8" %>`

### 4. Servlet 找不到

**问题**: 提示 "Servlet not found"

**解决方案**:
- 检查 `@WebServlet` 注解是否正确
- 检查 Servlet 类是否正确编译到 `WEB-INF/classes` 目录
- 重启 Tomcat 服务器

### 5. Maven 依赖下载失败

**问题**: Maven 构建时依赖下载失败

**解决方案**:
- 配置国内 Maven 镜像（阿里云镜像）
- 检查网络连接
- 手动下载所需的 jar 包放到 `WEB-INF/lib` 目录

## 📚 功能使用说明

### 查询学生

1. 在查询表单中输入查询条件（支持多条件组合）
2. 点击"查询"按钮
3. 支持按字段排序：点击表头进行排序
4. 支持分页：使用底部分页控件切换页面

### 添加学生

1. 点击"添加学生"按钮
2. 在弹出的表单中填写学生信息
3. 点击"保存"按钮
4. 必填字段：学号、姓名、性别

### 编辑学生

1. 在学生列表中找到要编辑的学生
2. 点击该行的"编辑"按钮
3. 在弹出的表单中修改信息
4. 点击"保存"按钮

### 删除学生

#### 单个删除
1. 点击学生行的"删除"按钮
2. 确认删除操作

#### 批量删除
1. 勾选要删除的学生（表格第一列的复选框）
2. 点击"批量删除"按钮
3. 确认删除操作

## 🔒 安全建议

1. **密码加密**: 生产环境中应使用 MD5 或 SHA-256 加密存储密码
2. **SQL 注入防护**: 本系统已使用 PreparedStatement 防止 SQL 注入
3. **XSS 防护**: 建议对用户输入进行 HTML 转义
4. **访问控制**: 可添加登录认证和权限管理功能
5. **HTTPS**: 生产环境建议使用 HTTPS 协议

## 📊 数据库表结构

### student 表

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | INT | 主键，自增 |
| student_no | VARCHAR(20) | 学号，唯一 |
| name | VARCHAR(50) | 姓名 |
| gender | TINYINT | 性别：1-男，2-女 |
| age | INT | 年龄 |
| major | VARCHAR(100) | 专业 |
| class_name | VARCHAR(50) | 班级 |
| phone | VARCHAR(20) | 联系电话 |
| email | VARCHAR(100) | 邮箱 |
| enrollment_date | DATE | 入学日期 |
| create_time | TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | 更新时间 |
| status | TINYINT | 状态：1-在读，2-休学，3-毕业 |

## 📞 技术支持

如有问题，请查看以下资源：

- Tomcat 官方文档: https://tomcat.apache.org/tomcat-10.1-doc/
- MySQL 官方文档: https://dev.mysql.com/doc/
- Servlet 规范: https://jakarta.ee/specifications/servlet/

## 📄 许可证

本项目仅供学习和研究使用。

---

**祝您使用愉快！ 🎉**
