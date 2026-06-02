<template>
  <div class="books-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>图书列表</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            添加图书
          </el-button>
        </div>
      </template>

      <div class="search-bar">
        <el-input
          v-model="searchTitle"
          placeholder="搜索图书名称"
          clearable
          style="width: 200px"
          @keyup.enter="handleSearch"
        />
        <el-input
          v-model="searchIsbn"
          placeholder="ISBN"
          clearable
          style="width: 150px; margin-left: 10px"
          @keyup.enter="handleSearch"
        />
        <el-input
          v-model="searchAuthor"
          placeholder="作者"
          clearable
          style="width: 120px; margin-left: 10px"
          @keyup.enter="handleSearch"
        />
        <el-input
          v-model="searchPress"
          placeholder="出版社"
          clearable
          style="width: 150px; margin-left: 10px"
          @keyup.enter="handleSearch"
        />
        <el-select
          v-model="searchCategoryId"
          placeholder="选择分类"
          clearable
          style="width: 150px; margin-left: 10px"
          @change="handleCategoryChange"
        >
          <el-option
            v-for="category in categories"
            :key="category.categoryId"
            :label="category.name"
            :value="category.categoryId"
          />
        </el-select>
        <el-button type="primary" @click="handleSearch" style="margin-left: 10px">
          <el-icon><Search /></el-icon>
          搜索
        </el-button>
        <el-button @click="handleReset" style="margin-left: 10px">重置</el-button>
      </div>

      <el-table :data="books" stripe style="width: 100%; margin-top: 20px">
        <el-table-column prop="bookId" label="ID" width="80" />
        <el-table-column prop="isbn" label="ISBN" width="150" />
        <el-table-column prop="title" label="书名" width="200" />
        <el-table-column prop="author" label="作者" width="120" />
        <el-table-column prop="press" label="出版社" width="150" />
        <el-table-column prop="publishDate" label="出版日期" width="120" />
        <el-table-column prop="totalNumber" label="总数" width="80" />
        <el-table-column prop="availableNumber" label="可借" width="80">
          <template #default="{ row }">
            <el-tag :type="row.availableNumber > 0 ? 'success' : 'danger'">
              {{ row.availableNumber }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">
              编辑
            </el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      @close="resetForm"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="ISBN" prop="isbn">
          <el-input 
            v-model="form.isbn" 
            placeholder="请输入ISBN，自动识别图书信息" 
            @blur="handleIsbnBlur"
          />
        </el-form-item>
        <el-form-item label="书名" prop="title">
          <el-input v-model="form.title" placeholder="请输入书名" />
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="出版社" prop="press">
          <el-input v-model="form.press" placeholder="请输入出版社" />
        </el-form-item>
        <el-form-item label="出版日期" prop="publishDate">
          <el-date-picker
            v-model="form.publishDate"
            type="date"
            placeholder="选择日期"
            value-format="YYYY-MM-DD"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="选择分类" style="width: 100%">
            <el-option
              v-for="category in categories"
              :key="category.categoryId"
              :label="category.name"
              :value="category.categoryId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="总数" prop="totalNumber">
          <el-input-number v-model="form.totalNumber" :min="1" style="width: 100%" @change="handleTotalNumberChange" />
        </el-form-item>
        <el-form-item label="可借数量" prop="availableNumber">
          <el-input-number v-model="form.availableNumber" :min="0" :max="form.totalNumber" style="width: 100%" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="3"
            placeholder="请输入描述"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { bookApi } from '@/api/book'
import { categoryApi } from '@/api/category'

const books = ref([])
const categories = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('添加图书')
const formRef = ref(null)
const searchTitle = ref('')
const searchIsbn = ref('')
const searchAuthor = ref('')
const searchPress = ref('')
const searchCategoryId = ref(null)

const form = reactive({
  bookId: null,
  isbn: '',
  title: '',
  author: '',
  press: '',
  publishDate: '',
  categoryId: null,
  totalNumber: 1,
  availableNumber: 1,
  description: ''
})

const rules = {
  isbn: [{ required: true, message: '请输入ISBN', trigger: 'blur' }],
  title: [{ required: true, message: '请输入书名', trigger: 'blur' }],
  author: [{ required: true, message: '请输入作者', trigger: 'blur' }],
  press: [{ required: true, message: '请输入出版社', trigger: 'blur' }],
  publishDate: [{ required: true, message: '请选择出版日期', trigger: 'change' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
  totalNumber: [{ required: true, message: '请输入总数', trigger: 'blur' }]
}

const loadBooks = async () => {
  try {
    const res = await bookApi.getAllBooks()
    books.value = res.data
  } catch (error) {
    console.error('加载图书失败:', error)
  }
}

const loadCategories = async () => {
  try {
    const res = await categoryApi.getAllCategories()
    categories.value = res.data
  } catch (error) {
    console.error('加载分类失败:', error)
  }
}

const handleReset = () => {
  searchTitle.value = ''
  searchIsbn.value = ''
  searchAuthor.value = ''
  searchPress.value = ''
  searchCategoryId.value = null
  loadBooks()
}

const handleTotalNumberChange = () => {
  if (form.availableNumber > form.totalNumber) {
    form.availableNumber = form.totalNumber
  }
}

const handleIsbnBlur = async () => {
  if (!form.isbn.trim()) return
  
  try {
    ElMessage.info('正在识别图书信息...')
    const res = await bookApi.getBookByIsbn(form.isbn)
    if (res && res.data) {
      const book = res.data
      console.log('获取到图书信息:', book)
      form.isbn = book.isbn || form.isbn
      form.title = book.title || ''
      form.author = book.author || ''
      form.press = book.press || ''
      form.publishDate = book.publishDate || ''
      form.categoryId = book.categoryId || null
      form.totalNumber = book.totalNumber || 1
      form.availableNumber = book.availableNumber || 1
      form.description = book.description || ''
      setTimeout(() => {
        ElMessage.success('已自动识别图书信息')
      }, 100)
    } else {
      ElMessage.info('未找到该 ISBN 对应的图书信息')
    }
  } catch (error) {
    console.error('ISBN识别失败:', error)
    ElMessage.error('识别失败，请稍后重试')
  }
}

const handleSearch = async () => {
  try {
    const res = await bookApi.searchBooks(searchTitle.value, searchIsbn.value, searchAuthor.value, searchPress.value, searchCategoryId.value)
    books.value = res.data
  } catch (error) {
    console.error('搜索失败:', error)
  }
}

const handleCategoryChange = async () => {
  await handleSearch()
}

const handleAdd = () => {
  dialogTitle.value = '添加图书'
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑图书'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该图书吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await bookApi.deleteBook(row.bookId)
      ElMessage.success('删除成功')
      loadBooks()
    } catch (error) {
      console.error('删除失败:', error)
    }
  }).catch(() => {})
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (form.bookId) {
          await bookApi.updateBook(form.bookId, form)
          ElMessage.success('更新成功')
        } else {
          form.availableNumber = form.totalNumber
          await bookApi.addBook(form)
          ElMessage.success('添加成功')
        }
        dialogVisible.value = false
        loadBooks()
      } catch (error) {
        console.error('操作失败:', error)
      }
    }
  })
}

const resetForm = () => {
  formRef.value?.resetFields()
  Object.assign(form, {
    bookId: null,
    isbn: '',
    title: '',
    author: '',
    press: '',
    publishDate: '',
    categoryId: null,
    totalNumber: 1,
    availableNumber: 1,
    description: ''
  })
}

onMounted(() => {
  loadBooks()
  loadCategories()
})
</script>

<style scoped>
.books-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-bar {
  display: flex;
  align-items: center;
}
</style>