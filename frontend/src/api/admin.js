import request from '@/utils/request'

const adminApi = {
  /**
   * 管理员登录
   */
  login(data) {
    return request.post('/admin/login', data)
  },

  /**
   * 管理员注册
   */
  register(data) {
    return request.post('/admin/register', data)
  },

  /**
   * 检查用户名是否存在
   */
  checkUsername(username) {
    return request.get('/admin/check-username', {
      params: { username }
    })
  }
}

export { adminApi }
