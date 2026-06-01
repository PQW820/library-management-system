# 班级图书管理系统前端

基于 Vue 3 + Element Plus 开发的图书管理系统前端界面。

## 技术栈

- Vue 3 (Composition API)
- Element Plus
- Vue Router
- Axios
- Vite

## 项目结构

```
frontend/
├── src/
│   ├── api/              # API 接口
│   │   ├── book.js       # 图书相关接口
│   │   ├── student.js    # 学生相关接口
│   │   ├── borrow.js     # 借阅相关接口
│   │   └── category.js   # 分类相关接口
│   ├── views/            # 页面组件
│   │   ├── Layout.vue    # 主布局
│   │   ├── Books.vue     # 图书管理
│   │   ├── Students.vue  # 学生管理
│   │   └── Borrows.vue   # 借阅管理
│   ├── router/           # 路由配置
│   ├── utils/            # 工具函数
│   │   └── request.js    # Axios 封装
│   ├── App.vue           # 根组件
│   └── main.js           # 入口文件
├── index.html            # HTML 模板
├── vite.config.js        # Vite 配置
└── package.json          # 依赖配置
```

## 功能模块

### 1. 图书管理
- 图书列表展示
- 添加/编辑/删除图书
- 按分类筛选
- 搜索图书
- 查看图书库存状态

### 2. 学生管理
- 学生列表展示
- 添加/编辑/删除学生
- 按班级筛选
- 学生状态管理

### 3. 借阅管理
- 借阅记录列表
- 借书功能
- 还书功能
- 查看超期记录
- 按学生筛选借阅记录

## 安装和运行

### 1. 安装依赖

```bash
cd frontend
npm install
```

### 2. 启动开发服务器

```bash
npm run dev
```

访问 http://localhost:5173

### 3. 构建生产版本

```bash
npm run build
```

## 配置说明

### API 代理

在 `vite.config.js` 中配置了 API 代理：

```javascript
proxy: {
  '/api': {
    target: 'http://localhost:8080',
    changeOrigin: true,
    rewrite: (path) => path.replace(/^\/api/, '')
  }
}
```

确保后端服务运行在 `http://localhost:8080`

### 后端要求

后端需要支持 CORS 跨域请求，已在 Controller 中添加 `@CrossOrigin` 注解。

## 开发说明

### 添加新页面

1. 在 `src/views/` 创建新的 Vue 组件
2. 在 `src/router/index.js` 中添加路由配置
3. 在 `Layout.vue` 中添加导航菜单项

### 添加新 API

1. 在 `src/api/` 创建对应的 API 文件
2. 使用 `request` 工具发送 HTTP 请求

## 注意事项

1. 确保后端服务已启动
2. 数据库已正确配置
3. 前端默认端口为 5173，如需修改请在 `vite.config.js` 中配置