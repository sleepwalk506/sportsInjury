/* eslint-disable */
<template>
  <div class="container">
    <DoctorBot ref="doctorBotRef"></DoctorBot>
    <el-button @click="showDoctorBot" class="doctorBot" round size="large" :icon="Service" type="primary">在线咨询</el-button>
    <!-- search -->
    <el-card id="search">
      <el-row>
        <el-col :span="20" align="left">
          <el-input v-model="searchModel.injuryType" placeholder="受伤类型" clearable />
          <el-button type="primary" round :icon="Search" @click="getInjuryListByType">搜 索</el-button>
          <el-input v-model="searchModel.injuryPosition" placeholder="受伤位置" clearable />
          <el-button type="primary" round :icon="Search" @click="getInjuryListByPosition">搜 索</el-button>
        </el-col>
        <el-col :span="4" align="right">
          <el-button @click="openEditUI(null)" type="primary" :icon="Plus" circle />
        </el-col>
      </el-row>
    </el-card>

    <!-- result list   -->
    <el-card>
      <el-table :data="injuryList" stripe style="width: 100%">
        <el-table-column label="#" width="80">
          <template #default="scope">
            <!--    (pageNo-1)*pageSize + index + 1      -->
            {{ (searchModel.pageNo - 1) * searchModel.pageSize + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="recordId" label="记录ID" width="160" />
        <el-table-column prop="userId" label="用户ID" width="160" />
        <el-table-column prop="injuryType" label="受伤类型" width="160" />
        <el-table-column prop="injuryPosition" label="受伤部位" width="160" />
        <el-table-column prop="injuryDescription" label="受伤描述" width="160" />
        <el-table-column prop="dateOfInjury" label="受伤日期" width="160" />
        <el-table-column prop="recoveryStatus" label="恢复状态" width="160">
          <template #default="scope">
            <el-tag v-if="scope.row.recoveryStatus === '1'">已康复</el-tag>
            <el-tag v-else type="success">康复中</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button @click="openEditUI(scope.row.recordId)" type="primary" :icon="Edit" size="mini" circle></el-button>
            <el-button @click="deleteInjury(scope.row)" type="danger" :icon="Delete" size="mini" circle></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 分页 -->
    <el-pagination :current-page="searchModel.pageNo" :page-sizes="[5, 10, 20, 50]" :page-size="searchModel.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" />
    <!--  受伤信息编辑对话框  -->
    <el-dialog @close="closeDialog" :title="title" v-model="dialogFormVisible">
      <el-form ref="injuryFormRef" :model="injuryForm" :rules="rules">
        <el-form-item label="用户ID" :label-width="formLabelWidth" prop="userId">
          <el-input v-model="injuryForm.userId" autocomplete="off" />
        </el-form-item>
        <el-form-item label="受伤类型" :label-width="formLabelWidth" prop="injuryType">
          <el-input v-model="injuryForm.injuryType" autocomplete="off" />
        </el-form-item>
        <el-form-item label="受伤部位" :label-width="formLabelWidth" prop="injuryPosition">
          <el-input v-model="injuryForm.injuryPosition" autocomplete="off" />
        </el-form-item>
        <el-form-item label="伤病描述" :label-width="formLabelWidth" prop="injuryDescription">
          <el-input v-model="injuryForm.injuryDescription" autocomplete="off" />
        </el-form-item>
        <el-form-item label="受伤日期" :label-width="formLabelWidth" prop="dateOfInjury">
          <el-input v-model="injuryForm.dateOfInjury" autocomplete="off" />
        </el-form-item>
        <el-form-item label="恢复状态" :label-width="formLabelWidth">
          <el-switch v-model="injuryForm.recoveryStatus" active-value="1" inactive-value="0" />
        </el-form-item>
      </el-form>
      <template v-slot:footer>
        <div class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button @click="saveInjury()" type="primary">确 定</el-button>
        </div>
      </template>

    </el-dialog>
    <!--  伤病预防与康复建议  -->
    <el-card>
      <div style="padding: 50px 200px 50px 200px">
        <h3 style="color: #8c939d">------------------------ 伤病预防与康复建议 ------------------------</h3>
      </div>
      <div class="recommend">
        <el-card v-for="data in recommendList" :key=data.id class="card">
          <el-row>
            <el-col :span="14">
              <div class="carousel">
                <el-carousel :height="bannerHeight + 'px'" :autoplay="false">
                  <el-carousel-item v-for="item in data.picList" :key="item">
                    <!-- <img class="img" :src="item"> -->
                  </el-carousel-item>
                </el-carousel>
              </div>
            </el-col>
            <el-col :span="10">
              <el-scrollbar class="content" :height="bannerHeight">
                <h2>{{ data.title }}</h2>
                <hr>
                <p>{{ data.content }}</p>
              </el-scrollbar>
            </el-col>
          </el-row>
        </el-card>
      </div>
    </el-card>
  </div>
</template>

<script>
import {
  Edit,
  Plus,
  Search,
  Delete,
  Service
} from '@element-plus/icons-vue'
import recommendData from '@/data/injury'
import DoctorBot from '@/components/DoctorBot'
import { ref, Vue, computed, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { mapMutations, useStore, mapState } from 'vuex'
import axios from 'axios'
import store from '@/store'
// 导入写好的接口
import {
  injuryRecordsGetService, injuryRecordsAddService, injuryRecordsUpdateService,
  injuryRecordsDeleteService, injuryRecordsSearchByTypeService, injuryRecordsSearchByPositionService
} from '@/api/injury.js'
export default {
  ...mapMutations(['setUserData']),
  ...mapState(['userData']),
  components: { DoctorBot },
  setup() {
    const store = useStore()
    const doctorBotRef = ref()
    function showDoctorBot() {
      doctorBotRef.value.show()
    }
    return { Edit, Search, Plus, Delete, Service, doctorBotRef, showDoctorBot }
  },
  data() {
    return {
      store,
      isAdd: false,
      editRecordId: '',
      searchModel: {
        pageNo: 1,
        pageSize: 5
      },
      dialogFormVisible: false,
      injuryForm: {},
      injuryList: [],
      // # region data
      dataList: [

      ],
      // # endregion
      total: 10,
      formLabelWidth: '130px',
      title: '',
      bannerHeight: 0,
      screenWidth: 0,
      recommendList: []
    }
  },
  created() {
    this.getInjuryList()
    this.recommendList = recommendData
  },
  mounted() {
    // 首次加载时,初始化高度
    this.screenWidth = window.innerWidth
    this.bannerHeight = (600 / 1550 * this.screenWidth) * 1.2
    // 窗口大小发生改变
    window.onresize = () => {
      this.screenWidth = window.innerWidth
      this.bannerHeight = (600 / 1550 * this.screenWidth) * 1.2
    }
  },
  methods: {
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize
      this.getInjuryList()
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo
      this.getInjuryList()
    },
    async getInjuryList() {
      // 调用接口获取用户伤病记录，错误已由拦截器处理
      const response = await injuryRecordsGetService();
      this.dataList = response.data.data
      this.dataList.forEach(item => {
        item.dateOfInjury = this.formatDate(item.dateOfInjury)
      })
      const begin = (this.searchModel.pageNo - 1) * this.searchModel.pageSize
      let end = this.searchModel.pageNo * this.searchModel.pageSize
      if (end > this.dataList.length) {
        end = this.dataList.length
      }
      this.injuryList = this.dataList.slice(begin, end)
    },
    formatDate(dateString) {
      const date = new Date(dateString)
      date.setDate(date.getDate() + 1)
      const formattedDate = date.toISOString().split('T')[0]
      return formattedDate
    },
    openEditUI(id) {
      if (id == null) {
        this.title = '增加记录'
        this.isAdd = true
      } else {
        this.title = '修改记录'
        this.editRecordId = id
      }
      this.dialogFormVisible = true
    },
    closeDialog() {
      this.injuryForm = {}
      this.$refs.injuryFormRef.clearValidate()
    },
    async saveInjury() {
      // const store = useStore()
      if (this.isAdd === true) {
        // 调用接口获取用户伤病记录，错误已由拦截器处理
        const response = await injuryRecordsGetService();
        const recordLength = response.data.data.length + 1
        this.injuryForm.recordId = this.store.state.userData.userId + '_' + recordLength
        // 调用接口添加用户伤病记录，错误已由拦截器处理
        const response1 = await injuryRecordsAddService(this.injuryForm);
        this.getInjuryList()
        this.isAdd = false
      } else {
        // 调用接口更新用户伤病记录，错误已由拦截器处理
        this.injuryForm.recordId = this.editRecordId
        const response = await injuryRecordsUpdateService(this.injuryForm);
        this.getInjuryList();
      }
      this.$message({
        message: '保存记录成功',
        type: 'success'
      })
      // 鍏抽棴瀵硅瘽妗 
      this.dialogFormVisible = false
      // 鍒锋柊琛ㄦ牸

      this.isAdd = false
    },
    async getInjuryListByType() {
      // 调用接口查询用户伤病记录，错误已由拦截器处理
      const dataToSend = this.searchModel.injuryType;
      const response = await injuryRecordsSearchByTypeService(dataToSend);
      this.dataList = response.data.data
      this.dataList.forEach(item => {
        item.dateOfInjury = this.formatDate(item.dateOfInjury)
      })
      const begin = (this.searchModel.pageNo - 1) * this.searchModel.pageSize
      let end = this.searchModel.pageNo * this.searchModel.pageSize
      if (end > this.dataList.length) {
        end = this.dataList.length
      }
      this.injuryList = this.dataList.slice(begin, end)
    },
    async getInjuryListByPosition() {
      const dataToSend = this.searchModel.injuryPosition;
      // 调用接口查询用户伤病记录，错误已由拦截器处理
      const response = await injuryRecordsSearchByPositionService(dataToSend);
      this.dataList = response.data.data
      this.dataList.forEach(item => {
        item.dateOfInjury = this.formatDate(item.dateOfInjury)
      })
      const begin = (this.searchModel.pageNo - 1) * this.searchModel.pageSize
      let end = this.searchModel.pageNo * this.searchModel.pageSize
      if (end > this.dataList.length) {
        end = this.dataList.length
      }
      this.injuryList = this.dataList.slice(begin, end)
    },
    async deleteInjury(row) {
      // 调用接口删除用户伤病记录，错误已由拦截器处理
      const dataToSend = row.recordId;
      const response = await injuryRecordsDeleteService(dataToSend);
      this.getInjuryList()
    }
  }
}
</script>

<style scoped>
.container {
  padding: 10px;
}

#search {
  margin-bottom: 10px;
}

#search .el-input {
  width: 200px;
  margin-right: 10px;
}

.el-pagination {
  margin: 20px 0;
}

.recommend {
  padding: 50px 200px;
}

.img {
  width: 100%;
  height: inherit;
}

.card {
  margin-bottom: 50px;
}

.carousel {
  margin-right: 40px;
  padding: 0 20px;
}

.content h2,
.content hr,
.content p {
  text-align: left;
  white-space: pre-line;
  line-height: 2;
}

.content hr {
  margin: 15px 0;
}

.content {
  height: 100%;
}

.doctorBot {
  display: flex;
  flex-direction: column;
  position: fixed;
  right: 50px;
  bottom: 50px;
}

/*.el-carousel__item h3 {*/
/*  display: flex;*/
/*  color: #475669;*/
/*  opacity: 0.75;*/
/*  line-height: 300px;*/
/*  margin: 0;*/
/*}*/

/*.el-carousel__item:nth-child(2n) {*/
/*  background-color: #99a9bf;*/
/*}*/

/*.el-carousel__item:nth-child(2n + 1) {*/
/*  background-color: #d3dce6;*/
/*}*/
</style>
