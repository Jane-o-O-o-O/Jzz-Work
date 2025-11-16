-- ==========================================
-- 数据库初始化脚本
-- Database Initialization Script
-- ==========================================

-- 创建数据库 (Create Database)
CREATE DATABASE IF NOT EXISTS student_system
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE student_system;

-- ==========================================
-- 学生信息表 (Student Information Table)
-- ==========================================
DROP TABLE IF EXISTS student;

CREATE TABLE student (
    -- 学生ID，主键自增 (Student ID, Primary Key, Auto Increment)
    id INT PRIMARY KEY AUTO_INCREMENT,

    -- 学号，唯一标识 (Student Number, Unique Identifier)
    student_no VARCHAR(20) NOT NULL UNIQUE,

    -- 姓名 (Name)
    name VARCHAR(50) NOT NULL,

    -- 性别：1-男，2-女 (Gender: 1-Male, 2-Female)
    gender TINYINT NOT NULL,

    -- 年龄 (Age)
    age INT,

    -- 专业 (Major)
    major VARCHAR(100),

    -- 班级 (Class)
    class_name VARCHAR(50),

    -- 联系电话 (Phone Number)
    phone VARCHAR(20),

    -- 邮箱 (Email)
    email VARCHAR(100),

    -- 入学日期 (Enrollment Date)
    enrollment_date DATE,

    -- 创建时间 (Create Time)
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- 更新时间 (Update Time)
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- 状态：1-在读，2-休学，3-毕业 (Status: 1-Active, 2-Leave, 3-Graduated)
    status TINYINT DEFAULT 1

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生信息表';

-- ==========================================
-- 插入测试数据 (Insert Test Data)
-- ==========================================

INSERT INTO student (student_no, name, gender, age, major, class_name, phone, email, enrollment_date, status) VALUES
('2021001', '张三', 1, 20, '计算机科学与技术', '计科2101', '13800138001', 'zhangsan@example.com', '2021-09-01', 1),
('2021002', '李四', 2, 19, '计算机科学与技术', '计科2101', '13800138002', 'lisi@example.com', '2021-09-01', 1),
('2021003', '王五', 1, 21, '软件工程', '软工2101', '13800138003', 'wangwu@example.com', '2021-09-01', 1),
('2021004', '赵六', 2, 20, '软件工程', '软工2101', '13800138004', 'zhaoliu@example.com', '2021-09-01', 1),
('2021005', '孙七', 1, 22, '信息安全', '信安2101', '13800138005', 'sunqi@example.com', '2021-09-01', 1),
('2021006', '周八', 2, 19, '信息安全', '信安2101', '13800138006', 'zhouba@example.com', '2021-09-01', 1),
('2021007', '吴九', 1, 20, '数据科学与大数据技术', '数据2101', '13800138007', 'wujiu@example.com', '2021-09-01', 1),
('2021008', '郑十', 2, 21, '数据科学与大数据技术', '数据2101', '13800138008', 'zhengshi@example.com', '2021-09-01', 1),
('2022001', '陈一', 1, 19, '计算机科学与技术', '计科2201', '13800138009', 'chenyi@example.com', '2022-09-01', 1),
('2022002', '林二', 2, 18, '计算机科学与技术', '计科2201', '13800138010', 'linger@example.com', '2022-09-01', 1),
('2022003', '黄三', 1, 20, '软件工程', '软工2201', '13800138011', 'huangsan@example.com', '2022-09-01', 1),
('2022004', '刘四', 2, 19, '软件工程', '软工2201', '13800138012', 'liusi@example.com', '2022-09-01', 1),
('2022005', '杨五', 1, 21, '人工智能', '人工2201', '13800138013', 'yangwu@example.com', '2022-09-01', 1),
('2022006', '朱六', 2, 18, '人工智能', '人工2201', '13800138014', 'zhuliu@example.com', '2022-09-01', 1),
('2022007', '何七', 1, 19, '物联网工程', '物联2201', '13800138015', 'heqi@example.com', '2022-09-01', 1),
('2023001', '罗八', 2, 18, '计算机科学与技术', '计科2301', '13800138016', 'luoba@example.com', '2023-09-01', 1),
('2023002', '梁九', 1, 19, '软件工程', '软工2301', '13800138017', 'liangjiu@example.com', '2023-09-01', 1),
('2023003', '宋十', 2, 18, '网络工程', '网工2301', '13800138018', 'songshi@example.com', '2023-09-01', 1),
('2020001', '许一', 1, 22, '计算机科学与技术', '计科2001', '13800138019', 'xuyi@example.com', '2020-09-01', 3),
('2020002', '韩二', 2, 23, '软件工程', '软工2001', '13800138020', 'haner@example.com', '2020-09-01', 3);

-- ==========================================
-- 创建用户表（可选，用于登录功能）
-- User Table (Optional, for Login Function)
-- ==========================================
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    -- 用户ID (User ID)
    id INT PRIMARY KEY AUTO_INCREMENT,

    -- 用户名 (Username)
    username VARCHAR(50) NOT NULL UNIQUE,

    -- 密码（实际项目中应该加密存储）(Password - Should be encrypted in production)
    password VARCHAR(100) NOT NULL,

    -- 真实姓名 (Real Name)
    real_name VARCHAR(50),

    -- 角色：1-管理员，2-普通用户 (Role: 1-Admin, 2-User)
    role TINYINT DEFAULT 2,

    -- 创建时间 (Create Time)
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 插入默认管理员账号 (Insert Default Admin Account)
-- 用户名: admin, 密码: admin123
INSERT INTO user (username, password, real_name, role) VALUES
('admin', 'admin123', '系统管理员', 1),
('user', 'user123', '普通用户', 2);

-- ==========================================
-- 查询验证 (Query Verification)
-- ==========================================
SELECT '数据库初始化完成！' AS message;
SELECT COUNT(*) AS student_count FROM student;
SELECT COUNT(*) AS user_count FROM user;
