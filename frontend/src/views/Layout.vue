<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <div class="logo">
        <el-icon><Reading /></el-icon>
        <span>图书管理系统</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409eff"
      >
        <el-menu-item index="/books">
          <el-icon><Document /></el-icon>
          <span>图书管理</span>
        </el-menu-item>
        <el-menu-item index="/students">
          <el-icon><User /></el-icon>
          <span>学生管理</span>
        </el-menu-item>
        <el-menu-item index="/borrows">
          <el-icon><List /></el-icon>
          <span>借阅管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-content">
          <h2>{{ currentTitle }}</h2>
        </div>
        <div class="header-right">
          <span class="admin-name">欢迎, {{ adminName }}</span>
          <el-button type="text" @click="handleLogout">
            <el-icon><Logout /></el-icon>
            退出登录
          </el-button>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'

const route = useRoute()

const activeMenu = computed(() => route.path)

const currentTitle = computed(() => {
  return route.meta.title || '图书管理系统'
})

const adminName = computed(() => {
  const admin = localStorage.getItem('admin')
  if (admin) {
    return JSON.parse(admin).username
  }
  return '管理员'
})

const handleLogout = () => {
  localStorage.removeItem('admin')
  localStorage.removeItem('isLogin')
  ElMessage.success('已退出登录')
  setTimeout(() => {
    window.location.href = '/login'
  }, 1000)
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.el-aside {
  background-color: #304156;
  overflow: hidden;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  gap: 10px;
}

.el-menu {
  border-right: none;
}

.el-header {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.header-content h2 {
  font-size: 20px;
  color: #333;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.admin-name {
  color: #666;
  font-size: 14px;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>