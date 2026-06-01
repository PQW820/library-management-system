# 班级图书管理系统

基于 SpringBoot + MyBatis 的班级图书管理系统，提供图书管理、学生管理、借阅管理等功能。

## 项目特性

- 🎯 **完整的图书管理系统** - 包含图书、学生、借阅等核心模块
- 📚 **图书管理** - 支持图书的增删改查、分类管理
- 👥 **学生管理** - 学生信息管理、班级分类
- 📖 **借阅管理** - 借书、还书、超期提醒
- 🔍 **数据查询** - 强大的查询和搜索功能
- 🏗️ **优雅的架构** - 分层设计，代码结构清晰

## 技术栈

| 技术 | 版本 |
|-----|------|
| SpringBoot | 3.1.0 |
| MyBatis | 3.0.2 |
| MySQL | 8.0.33 |
| JDK | 11 |
| Maven | 3.6+ |

## 项目结构

```
library-management-system/
├── src/main/java/com/library/
│   ├── controller/          # 控制层
│   │   ├── BookController.java
│   │   ├── StudentController.java
│   │   ├── BorrowController.java
│   │   └── CategoryController.java
│   ├── service/             # 业务接口
│   │   ├── BookService.java
│   │   ├── StudentService.java
│   │   ├── BorrowService.java
│   │   └── CategoryService.java
│   ├── service/impl/        # 业务实现
│   │   ├── BookServiceImpl.java
│   │   ├── StudentServiceImpl.java
│   │   ├── BorrowServiceImpl.java
│   │   └── CategoryServiceImpl.java
│   ├── mapper/              # MyBatis Mapper
│   │   ├── BookMapper.java
│   │   ├── StudentMapper.java
│   │   ├── BorrowRecordMapper.java
│   │   └── CategoryMapper.java
│   ├── entity/              # 实体类
│   │   ├── Book.java
│   │   ├── Student.java
│   │   ├── BorrowRecord.java
│   │   └── Category.java
│   ├── dto/                 # 数据传输对象
│   │   └── ResponseResult.java
│   └── LibraryApplication.java
├── src/main/resources/
│   ├── mapper/              # MyBatis XML文件
│   │   ├── BookMapper.xml
│   │   ├── StudentMapper.xml
│   │   ├── BorrowRecordMapper.xml
│   │   └── CategoryMapper.xml
│   ├── db/
│   │   └── schema.sql       # 数据库初始化脚本
│   └── application.yml      # 应用配置
├── pom.xml                  # Maven依赖
└── README.md                # 项目说明
```

## 快速开始

### 1. 环境要求

- Java 11+
- MySQL 8.0+
- Maven 3.6+

### 2. 数据库初始化

```bash
# 连接到MySQL
mysql -u root -p

# 执行SQL初始化脚本
source src/main/resources/db/schema.sql
```

### 3. 修改数据库配置

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/library_system?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: root          # 改为您的MySQL用户名
    password: root          # 改为您的MySQL密码
```

### 4. 运行项目

```bash
# 使用Maven运行
mvn spring-boot:run

# 或者编译后运行
mvn clean package
java -jar target/library-management-system-1.0.0.jar
```

应用启动后访问：`http://localhost:8080/api`

## API 接口文档

### 图书管理

#### 获取所有图书
```
GET /api/books
```

#### 获取单个图书
```
GET /api/books/{id}
```

#### 添加图书
```
POST /api/books
Content-Type: application/json

{
  "isbn": "978-7-5632-0001-1",
  "title": "Java编程思想",
  "author": "Bruce Eckel",
  "press": "机械工业出版社",
  "publishDate": "2006-06-01",
  "categoryId": 2,
  "totalNumber": 5,
  "availableNumber": 5
}
```

#### 更新图书
```
PUT /api/books/{id}
Content-Type: application/json

{
  "title": "Java编程思想(第四版)",
  "availableNumber": 3
}
```

#### 删除图书
```
DELETE /api/books/{id}
```

#### 搜索图书
```
GET /api/books/search?title=Java
```

### 学生管理

#### 获取所有学生
```
GET /api/students
```

#### 获取单个学生
```
GET /api/students/{id}
```

#### 按班级查询
```
GET /api/students/class/{className}
```

#### 添加学生
```
POST /api/students
Content-Type: application/json

{
  "studentNumber": "20201001",
  "name": "张三",
  "className": "高一1班",
  "gender": "男",
  "phone": "13800000001",
  "email": "zhangsan@qq.com",
  "password": "password123"
}
```

### 借阅管理

#### 借书
```
POST /api/borrows/borrow?studentId=1&bookId=1
```

#### 还书
```
POST /api/borrows/return/{recordId}
```

#### 查询学生的借阅记录
```
GET /api/borrows/student/{studentId}
```

#### 查询未归还的图书
```
GET /api/borrows/unreturned/{studentId}
```

#### 查询超期记录
```
GET /api/borrows/overdue
```

### 分类管理

#### 获取所有分类
```
GET /api/categories
```

#### 获取单个分类
```
GET /api/categories/{id}
```

#### 添加分类
```
POST /api/categories
Content-Type: application/json

{
  "name": "科技",
  "description": "科技类书籍"
}
```

## 核心功能说明

### 借书流程

1. 检查图书可借数量是否大于0
2. 创建借阅记录，状态为"已借出"
3. 减少图书的可借数量
4. 设置30天的借阅期限

### 还书流程

1. 查询借阅记录
2. 检查是否超期
3. 更新借阅记录状态
4. 增加图书的可借数量

### 超期检查

- 系统自动计算是否超期
- 超期记录状态标记为"2"
- 可通过API获取所有超期记录

## 响应格式

所有API返回统一的JSON格式：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

## 数据库设计

### 表关系图

```
categories (分类)
    ↑
    │ (1:N)
    │
books (图书)
    ↑
    │ (1:N)
    │
borrow_records (借阅记录)
    ↑
    │ (N:1)
    │
students (学生)
```

## 常见问题

### Q: 无法连接数据库

**A:** 检查以下几点：
1. MySQL服务是否启动
2. 数据库连接配置是否正确
3. 用户名和密码是否正确
4. 数据库是否存在

### Q: 如何修改借书期限

**A:** 修改 `BorrowServiceImpl.java` 中的 `borrowBook` 方法：
```java
record.setReturnDate(LocalDate.now().plusDays(30));  // 改为需要的天数
```

### Q: 如何添加新的权限控制

**A:** 可以集成 Spring Security 进行权限管理。参考以下步骤：
1. 添加 Spring Security 依赖
2. 创建权限管理类
3. 在Controller上添加权限注解

## 扩展建议

1. **用户认证** - 集成 Spring Security 或 JWT
2. **前端开发** - 使用 Vue 3 + Element Plus 开发管理界面
3. **缓存优化** - 集成 Redis 缓存热点数据
4. **日志系统** - 集成 ELK 或其他日志系统
5. **文件管理** - 支持图书封面图片上传
6. **消息通知** - 超期提醒邮件/短信通知

## 许可证

MIT License

## 贡献指南

欢迎提交Issue和Pull Request！

## 联系方式

如有问题或建议，欢迎联系开发者。

---

**最后更新**: 2026年06月01日
