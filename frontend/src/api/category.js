import request from '@/utils/request'

export const categoryApi = {
  getAllCategories() {
    return request.get('/categories')
  },

  getCategoryById(id) {
    return request.get(`/categories/${id}`)
  },

  addCategory(data) {
    return request.post('/categories', data)
  },

  updateCategory(id, data) {
    return request.put(`/categories/${id}`, data)
  },

  deleteCategory(id) {
    return request.delete(`/categories/${id}`)
  }
}