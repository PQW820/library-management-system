import request from '@/utils/request'

export const borrowApi = {
  getAllRecords() {
    return request.get('/borrows')
  },

  getAllRecordsGrouped() {
    return request.get('/borrows/grouped')
  },

  getRecordsWithPagination(page, size, studentId = null) {
    return request.get('/borrows/page', {
      params: {
        page,
        size,
        studentId
      }
    })
  },

  getRecordsByStudent(studentId) {
    return request.get(`/borrows/student/${studentId}`)
  },

  getRecordsGroupedByStudent(studentId) {
    return request.get(`/borrows/grouped/student/${studentId}`)
  },

  getUnreturnedBooks(studentId) {
    return request.get(`/borrows/unreturned/${studentId}`)
  },

  getOverdueRecords() {
    return request.get('/borrows/overdue')
  },

  borrowBook(studentId, bookId) {
    return request.post('/borrows/borrow', null, {
      params: { studentId, bookId }
    })
  },

  returnBook(recordId) {
    return request.post(`/borrows/return/${recordId}`)
  }
}