import request from '@/utils/request'

export const bookApi = {
  getAllBooks() {
    return request.get('/books')
  },

  getBookById(id) {
    return request.get(`/books/${id}`)
  },

  getBooksByCategory(categoryId) {
    return request.get(`/books/category/${categoryId}`)
  },

  searchBooks(title, isbn, author, press, categoryId) {
    return request.get('/books/search', { params: { title, isbn, author, press, categoryId } })
  },

  addBook(data) {
    return request.post('/books', data)
  },

  updateBook(id, data) {
    return request.put(`/books/${id}`, data)
  },

  deleteBook(id) {
    return request.delete(`/books/${id}`)
  }
}