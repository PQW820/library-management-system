import request from '@/utils/request'

export const studentApi = {
  getAllStudents() {
    return request.get('/students')
  },

  getStudentById(id) {
    return request.get(`/students/${id}`)
  },

  getStudentsByClass(className) {
    return request.get(`/students/class/${className}`)
  },

  searchStudents(studentNumber, name, className) {
    return request.get('/students/search', { params: { studentNumber, name, className } })
  },

  addStudent(data) {
    return request.post('/students', data)
  },

  updateStudent(id, data) {
    return request.put(`/students/${id}`, data)
  },

  deleteStudent(id) {
    return request.delete(`/students/${id}`)
  }
}