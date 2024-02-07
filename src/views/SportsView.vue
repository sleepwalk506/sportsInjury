<template>
  <div class="sport-view">
    <el-table :data="exerciseRecords" style="width: 100%">
      <el-table-column label="Exercise Type" prop="exerciseType">
        <template v-slot="scope">
          <template v-if="!scope.row.editing">
            {{ scope.row.exerciseType }}
          </template>
          <template v-else>
            <el-dropdown>
              <span class="el-dropdown-link">
                {{ scope.row.editData.exerciseType || 'Select' }} <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="setExerciseType(scope.row, 'Running')">Running</el-dropdown-item>
                  <el-dropdown-item @click="setExerciseType(scope.row, 'Swimming')">Swimming</el-dropdown-item>
                  <el-dropdown-item @click="setExerciseType(scope.row, 'Cycling')">Cycling</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="Exercise Date" prop="exerciseDate">
        <template v-slot="scope">
          <template v-if="!scope.row.editing">
            {{ formatDate(scope.row.exerciseDate) }}
          </template>
          <template v-else>
            <el-date-picker v-model="scope.row.editData.exerciseDate">
            </el-date-picker>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="Exercise Time" prop="exerciseTime">
        <template v-slot="scope">
          <template v-if="!scope.row.editing">
            {{ scope.row.exerciseTime }}
          </template>
          <template v-else>
            <el-time-picker v-model="scope.row.editData.exerciseTime" value-format="HH:mm:ss" :picker-options="{
              selectableRange: '00:00:00 - 23:59:59'
            }" placeholder="any"></el-time-picker>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="kcal" prop="kcal">
        <template v-slot="scope">
          <template v-if="!scope.row.editing">
            {{ scope.row.kcal }}
          </template>
          <template v-else>
            <el-input v-model="scope.row.editData.kcal"></el-input>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="AvgSpeed" prop="avgSpeed">
        <template v-slot="scope">
          <template v-if="!scope.row.editing">
            {{ scope.row.avgSpeed }}
          </template>
          <template v-else>
            <el-input v-model="scope.row.editData.avgSpeed"></el-input>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="AvgHeartRate" prop="avgHeartRate">
        <template v-slot="scope">
          <template v-if="!scope.row.editing">
            {{ scope.row.avgHeartRate }}
          </template>
          <template v-else>
            <el-input v-model="scope.row.editData.avgHeartRate"></el-input>
          </template>
        </template>
      </el-table-column>
      <!-- ... 省略其他列 ... -->
      <el-table-column label="Actions">
        <template v-slot="scope">
          <template v-if="!scope.row.editing">
            <el-button @click="editRow(scope.row)">Edit</el-button>
          </template>
          <template v-else>
            <el-button @click="saveRow(scope.row)">Save</el-button>
            <el-button @click="cancelEdit(scope.row)">Cancel</el-button>
          </template>
        </template>
      </el-table-column>
    </el-table>

    <el-button @click="addNewRecord">Add New Record</el-button>
    <el-button @click="goBack">Back to Main Menu</el-button>
  </div>
</template>

<script>
import { ref, Vue, computed, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { mapMutations, useStore, mapState } from 'vuex'
import { ElMessageBox } from 'element-plus'
import { sportsRecordsGetService, sportsRecordsAddService, sportsRecordsUpdateService } from '@/api/sport.js'

export default {
  name: 'SportView',
  ...mapMutations(['setSportsRecords']),
  ...mapState(['sportsRecordsData']),
  setup() {
    // const backendData = Vue.ref([])
    // const apiUrl = '/sportsRecords'
    const store = useStore()
    const exerciseRecords = ref([])
    // const userId = computed(() => store.state.userData.userId)
    onMounted(async () => {
      // 调用接口获取用户运动记录，错误已由拦截器处理
      const response = await sportsRecordsGetService();
      store.commit('setSportsRecords', response.data.data)
      // 初始化 exerciseRecords 数组
      exerciseRecords.value = store.state.sportsRecordsData.map(item => {
        return { ...item, editing: false, isAdd: false, editData: {} }
      })
    })
    // 初始化时间
    const formatDate = (dateString) => {
      const date = new Date(dateString)
      date.setDate(date.getDate() + 1)
      const formattedDate = date.toISOString().split('T')[0]
      return formattedDate
    }
    // 初始化类型
    const setExerciseType = (row, type) => {
      row.editData.exerciseType = type
    }
    const addNewRecord = () => {
      const newRecord = {
        exerciseType: '',
        exerciseDate: '',
        exerciseTime: '',
        kcal: '',
        avgSpeed: '',
        avgHeartRate: '',
        editing: true, // 设置为编辑状态
        editData: { exerciseDate: '1970-01-01', exerciseTime: '00:00:00' }, // 初始化编辑数据对象
        isAdd: true // 设置为新增状态
      }
      exerciseRecords.value.push(newRecord)
    }

    const editRow = (row) => {
      row.editing = true
      row.editData = { ...row }
    }

    const saveRow = async (row) => {
      if (row.editData) {
        // Update the original row data with the edited values
        const index = exerciseRecords.value.indexOf(row)
        const newExerciseIdBack = store.state.sportsRecordsData.length + 1
        const sendRecord = { userId: store.state.userData.userId, exerciseId: store.state.userData.userId + '_' + newExerciseIdBack, ...row.editData }
        exerciseRecords.value[index] = { ...row.editData }
        const checkRecord = exerciseRecords.value[index]
        if (checkRecord.exerciseType == null || checkRecord.exerciseDate == null || checkRecord.exerciseTime == null ||
          checkRecord.kcal == null || checkRecord.avgSpeed == null || checkRecord.avgHeartRate == null) {
          ElMessageBox.alert('Please fill in the complete entries', {
            confirmButtonText: 'OK',
            type: 'error'
          })
          row.editData = {}
          row.editing = false
          row.isAdd = false
          exerciseRecords.value.pop()
        } else {
          row.editData = {}
          row.editing = false
          try {
            // 调用接口添加或更新运动记录，错误已由拦截器处理
            if (row.isAdd) {
              const response = await sportsRecordsAddService(sendRecord);
              row.isAdd = false
            } else {
              const response = await sportsRecordsUpdateService(exerciseRecords.value[index]);
            }
            console.log(exerciseRecords.value[index])
            // 请求成功后重新获取后端数据
            const response = await sportsRecordsGetService();
            store.commit('setSportsRecords', response.data.data)
            // 更新 exerciseRecords
            exerciseRecords.value = store.state.sportsRecordsData.map(item => {
              return { ...item, editing: false, editData: {} }
            })
          } catch (error) {
            console.error(error.response.data)
            // 处理错误逻辑
          }
        }
      }
    }

    const exerciseRecordsComputed = computed(() => {
      return store.state.sportsRecordsData.map(item => {
        return { ...item, editing: false, editData: {} }
      })
    })

    // 监听 exerciseRecordsComputed 的变化
    watch(exerciseRecordsComputed, newData => {
      exerciseRecords.value = newData
    })

    const cancelEdit = (row) => {
      row.editData = {}
      row.editing = false
      if (row.isAdd) {
        exerciseRecords.value.pop()
      }
      row.isAdd = false
      // exerciseRecords.value.pop()
    }

    const router = useRouter()
    const goBack = () => {
      router.push('/main') // 跳转到主菜单页面的路由路径
    }
    console.log('exerciseRecords2:', exerciseRecords)

    return {
      exerciseRecords,
      editRow,
      saveRow,
      cancelEdit,
      goBack,
      addNewRecord,
      formatDate,
      setExerciseType
    }
  }
}
</script>

<style scoped>
.sport-view {
  background-color: #f0f0f0;
  /* Light gray background */
  padding: 20px;
}
</style>
