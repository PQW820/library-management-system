<template>
  <div class="borrows-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>借阅管理</span>
          <div class="header-actions">
            <el-button type="primary" @click="handleBorrow">
              <el-icon><Plus /></el-icon>
              借书
            </el-button>
            <el-button type="warning" @click="loadOverdueRecords">
              <el-icon><Warning /></el-icon>
              查看超期记录
            </el-button>
          </div>
        </div>
      </template>

      <div class="search-bar">
        <el-select
          v-model="selectedStudent"
          placeholder="选择学生"
          filterable
          clearable
          style="width: 300px"
          @change="handleStudentChange"
        >
          <el-option
            v-for="student in students"
            :key="student.studentId"
            :label="`${student.name} (${student.studentNumber})`"
            :value="student.studentId"
          />
        </el-select>
        <el-button @click="loadRecords" style="margin-left: 10px">重置</el-button>
      </div>

      <el-table :data="records" stripe style="width: 100%; margin-top: 20px">
        <el-table-column prop="studentName" label="学生姓名" width="100" />
        <el-table-column prop="studentNumber" label="学号" width="120" />
        <el-table-column prop="bookTitle" label="图书名称" width="200" />
        <el-table-column prop="bookIsbn" label="ISBN" width="150" />
        <el-table-column prop="borrowCount" label="借阅次数" width="100" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button
              v-if="row.status === '0'"
              type="success"
              size="small"
              @click="handleReturnAll(row)"
            >
              全部还书
            </el-button>
            <span v-else style="color: #999">已归还</span>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog
      v-model="borrowDialogVisible"
      title="借书"
      width="600px"
      @close="resetBorrowForm"
    >
      <el-form
        ref="borrowFormRef"
        :model="borrowForm"
        :rules="borrowRules"
        label-width="100px"
      >
        <el-form-item label="学生" prop="studentId">
          <el-select
            v-model="borrowForm.studentId"
            placeholder="选择学生"
            filterable
            style="width: 100%"
          >
            <el-option
              v-for="student in students"
              :key="student.studentId"
              :label="`${student.name} (${student.studentNumber})`"
              :value="student.studentId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="图书" prop="bookId">
          <el-select
            v-model="borrowForm.bookId"
            placeholder="选择图书"
            filterable
            style="width: 100%"
          >
            <el-option
              v-for="book in availableBooks"
              :key="book.bookId"
              :label="`${book.title} (可借: ${book.availableNumber})`"
              :value="book.bookId"
              :disabled="book.availableNumber <= 0"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="borrowDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleBorrowSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { borrowApi } from '@/api/borrow'
import { studentApi } from '@/api/student'
import { bookApi } from '@/api/book'

const records = ref([])
const students = ref([])
const availableBooks = ref([])
const borrowDialogVisible = ref(false)
const borrowFormRef = ref(null)
const selectedStudent = ref(null)

const borrowForm = reactive({
  studentId: null,
  bookId: null
})

const borrowRules = {
  studentId: [{ required: true, message: '请选择学生', trigger: 'change' }],
  bookId: [{ required: true, message: '请选择图书', trigger: 'change' }]
}

const loadRecords = async () => {
  try {
    const res = await borrowApi.getAllRecordsGrouped()
    records.value = res.data
  } catch (error) {
    console.error('加载借阅记录失败:', error)
  }
}

const loadStudents = async () => {
  try {
    const res = await studentApi.getAllStudents()
    students.value = res.data
  } catch (error) {
    console.error('加载学生失败:', error)
  }
}

const loadAvailableBooks = async () => {
  try {
    const res = await bookApi.getAllBooks()
    availableBooks.value = res.data.filter(book => book.availableNumber > 0)
  } catch (error) {
    console.error('加载图书失败:', error)
  }
}

const handleStudentChange = async () => {
  if (selectedStudent.value) {
    try {
      const res = await borrowApi.getRecordsGroupedByStudent(selectedStudent.value)
      records.value = res.data
    } catch (error) {
      console.error('加载学生借阅记录失败:', error)
    }
  } else {
    loadRecords()
  }
}

const loadOverdueRecords = async () => {
  try {
    const res = await borrowApi.getOverdueRecords()
    records.value = res.data
    ElMessage.success(`共找到 ${res.data.length} 条超期记录`)
  } catch (error) {
    console.error('加载超期记录失败:', error)
  }
}

const handleBorrow = () => {
  loadAvailableBooks()
  if (selectedStudent.value) {
    borrowForm.studentId = selectedStudent.value
  }
  borrowDialogVisible.value = true
}

const handleBorrowSubmit = async () => {
  if (!borrowFormRef.value) return
  
  await borrowFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await borrowApi.borrowBook(borrowForm.studentId, borrowForm.bookId)
        ElMessage.success('借书成功')
        borrowDialogVisible.value = false
        loadRecords()
      } catch (error) {
        console.error('借书失败:', error)
      }
    }
  })
}

const handleReturnAll = (row) => {
  const unreturnedRecords = row.records?.filter(r => r.status === '0') || []
  if (unreturnedRecords.length === 0) {
    ElMessage.warning('没有需要归还的图书')
    return
  }
  
  ElMessageBox.confirm(`确定要归还 ${unreturnedRecords.length} 本图书吗?`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      for (const record of unreturnedRecords) {
        await borrowApi.returnBook(record.recordId)
      }
      ElMessage.success(`成功归还 ${unreturnedRecords.length} 本图书`)
      loadRecords()
    } catch (error) {
      console.error('还书失败:', error)
      ElMessage.error('还书失败')
    }
  }).catch(() => {})
}

const resetBorrowForm = () => {
  borrowFormRef.value?.resetFields()
  Object.assign(borrowForm, {
    studentId: null,
    bookId: null
  })
}

const getStatusType = (status) => {
  switch (status) {
    case '0':
      return 'warning'
    case '1':
      return 'success'
    case '2':
      return 'danger'
    default:
      return 'info'
  }
}

const getStatusText = (status) => {
  switch (status) {
    case '0':
      return '已借出'
    case '1':
      return '已归还'
    case '2':
      return '超期'
    default:
      return '未知'
  }
}

onMounted(() => {
  loadRecords()
  loadStudents()
})
</script>

<style scoped>
.borrows-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-actions {
  display: flex;
  gap: 10px;
}

.search-bar {
  display: flex;
  align-items: center;
}
</style>