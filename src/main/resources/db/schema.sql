-- 创建数据库
CREATE DATABASE IF NOT EXISTS library_system DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE library_system;

-- 管理员表
CREATE TABLE IF NOT EXISTS admins (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(15),
    status INT DEFAULT 1 COMMENT '0:禁用,1:启用',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';

-- 分类表
CREATE TABLE IF NOT EXISTS categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='图书分类表';

-- 图书表
CREATE TABLE IF NOT EXISTS books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(50),
    press VARCHAR(50),
    publish_date VARCHAR(10),
    category_id INT,
    total_number INT DEFAULT 1,
    available_number INT DEFAULT 1,
    description VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(category_id),
    INDEX idx_title (title),
    INDEX idx_author (author),
    INDEX idx_category (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='图书表';

-- 学生表
CREATE TABLE IF NOT EXISTS students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    student_number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    class_name VARCHAR(30),
    gender CHAR(2),
    phone VARCHAR(15),
    email VARCHAR(50),
    password VARCHAR(255),
    status INT DEFAULT 0 COMMENT '0:正常,1:禁用',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_student_number (student_number),
    INDEX idx_class (class_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生表';

-- 借阅记录表
CREATE TABLE IF NOT EXISTS borrow_records (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE NOT NULL,
    actual_return_date DATE,
    status VARCHAR(10) DEFAULT '0' COMMENT '0:已借出,1:已归还,2:超期',
    remark VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    INDEX idx_student (student_id),
    INDEX idx_book (book_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='借阅记录表';

-- 插入示例数据
INSERT INTO categories (name, description) VALUES
('文学', '文学类书籍'),
('计算机', '计算机技术类书籍'),
('历史', '历史类书籍'),
('科学', '科学类书籍');

INSERT INTO books (isbn, title, author, press, publish_date, category_id, total_number, available_number, description) VALUES
('978-7-5632-0001-1', 'Java编程思想', 'Bruce Eckel', '机械工业出版社', '2006-06-01', 2, 5, 3, '经典Java编程书籍'),
('978-7-5632-0002-8', '活着', '余华', '南海出版社', '1992-01-01', 1, 3, 2, '现实主义文学作品'),
('978-7-5632-0003-5', '三体', '刘慈欣', '重庆出版社', '2008-05-01', 1, 4, 2, '科幻小说'),
('978-7-5632-0004-2', 'MySQL必知必会', 'Ben Forta', '人民邮电出版社', '2006-06-01', 2, 6, 4, '数据库学习书籍');

INSERT INTO students (student_number, name, class_name, gender, phone, email, password, status) VALUES
('20201001', '张三', '高一1班', '男', '13800000001', 'zhangsan@qq.com', 'password123', 0),
('20201002', '李四', '高一1班', '女', '13800000002', 'lisi@qq.com', 'password123', 0),
('20201003', '王五', '高一2班', '男', '13800000003', 'wangwu@qq.com', 'password123', 0),
('20201004', '赵六', '高一2班', '女', '13800000004', 'zhaoliu@qq.com', 'password123', 0);