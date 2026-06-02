<template>
  <div class="register-container">
    <div class="register-box">
      <div class="register-header">
        <h2>图书馆管理系统</h2>
        <p>管理员注册</p>
      </div>

      <el-form ref="formRef" :model="form" :rules="rules" class="register-form">
        <el-form-item prop="username">
          <el-input
            v-model="form.username"
            placeholder="请输入用户名"
            prefix-icon="User"
            size="large"
            @blur="checkUsername"
          />
          <span v-if="usernameCheckResult" :class="['check-result', usernameCheckExists ? 'error' : 'success']">
            {{ usernameCheckExists ? '用户名已存在' : '用户名可用' }}
          </span>
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            size="large"
          />
        </el-form-item>

        <el-form-item prop="confirmPassword">
          <el-input
            v-model="form.confirmPassword"
            type="password"
            placeholder="请确认密码"
            prefix-icon="Lock"
            size="large"
          />
        </el-form-item>

        <el-form-item prop="email">
          <el-input
            v-model="form.email"
            placeholder="请输入邮箱（选填）"
            prefix-icon="Mail"
            size="large"
          />
        </el-form-item>

        <el-form-item prop="phone">
          <el-input
            v-model="form.phone"
            placeholder="请输入手机号（选填）"
            prefix-icon="Phone"
            size="large"
          />
        </el-form-item>

        <el-form-item prop="captcha">
          <div class="captcha-row">
            <el-input
              v-model="form.captcha"
              placeholder="请输入验证码"
              prefix-icon="Code"
              size="large"
              style="width: 60%"
            />
            <div class="captcha-img" @click="refreshCaptcha">
              <img :src="captchaUrl" alt="验证码" />
            </div>
          </div>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" size="large" style="width: 100%" @click="handleRegister">
            注册
          </el-button>
        </el-form-item>
      </el-form>

      <div class="register-footer">
        <span>已有账号？</span>
        <a href="/login" class="login-link">立即登录</a>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { adminApi } from '@/api/admin'

const formRef = ref(null)
const captchaUrl = ref('')
const usernameCheckResult = ref(false)
const usernameCheckExists = ref(false)

const form = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  email: '',
  phone: '',
  captcha: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在3-20个字符之间', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 30, message: '密码长度在6-30个字符之间', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: (rule, value, callback) => {
      if (value !== form.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }, trigger: 'blur' }
  ],
  captcha: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
}

const refreshCaptcha = () => {
  captchaUrl.value = `/api/admin/captcha?timestamp=${Date.now()}`
}

const checkUsername = async () => {
  if (!form.username) return

  try {
    const res = await adminApi.checkUsername(form.username)
    usernameCheckResult.value = true
    usernameCheckExists.value = res.data
  } catch (error) {
    console.error('检查用户名失败:', error)
  }
}

const handleRegister = async () => {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const res = await adminApi.register(form)
        if (res) {
          ElMessage.success('注册成功，请登录')
          setTimeout(() => {
            window.location.href = '/login'
          }, 1500)
        }
      } catch (error) {
        console.error('注册失败:', error)
        ElMessage.error(error.response?.data?.message || '注册失败')
        refreshCaptcha()
        form.captcha = ''
      }
    }
  })
}

onMounted(() => {
  refreshCaptcha()
})
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-box {
  width: 450px;
  background: #fff;
  border-radius: 12px;
  padding: 40px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
}

.register-header {
  text-align: center;
  margin-bottom: 30px;
}

.register-header h2 {
  color: #333;
  margin: 0 0 10px 0;
  font-size: 24px;
}

.register-header p {
  color: #999;
  margin: 0;
}

.register-form {
  margin-bottom: 20px;
}

.captcha-row {
  display: flex;
  align-items: center;
  gap: 10px;
}

.captcha-img {
  width: 35%;
  height: 40px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  cursor: pointer;
  overflow: hidden;
}

.captcha-img img {
  width: 100%;
  height: 100%;
}

.check-result {
  font-size: 12px;
  margin-top: 4px;
}

.check-result.success {
  color: #67c23a;
}

.check-result.error {
  color: #f56c6c;
}

.register-footer {
  text-align: center;
  color: #999;
}

.login-link {
  color: #409eff;
  text-decoration: none;
  margin-left: 5px;
}

.login-link:hover {
  text-decoration: underline;
}
</style>
