<template>
  <div style="position: relative">
    <div style="position: fixed; left: 20px; top: 50px ">
      <p>管理员账号: admin</p>
    </div>
    <div style="margin: 30px; display: flex; justify-content: center; color: #8c939d; font-size: 32px">
      <p>违规博客管理社区</p>
    </div>
    <el-card>
      <el-table :data="blogList" stripe style="width: 100%">
        <el-table-column label="#" width="80">
          <template #default="scope">
            <!--    (pageNo-1)*pageSize + index + 1      -->
            {{ (searchModel.pageNo - 1) * searchModel.pageSize + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="blogId" label="博客ID" width="180" />
        <el-table-column prop="userId" label="用户ID" width="180" />
        <el-table-column prop="title" label="博客标题" width="200">
          <template #default="scope">
            <el-scrollbar height="100px">
              <p>{{ scope.row.title }}</p>
            </el-scrollbar>
          </template>
        </el-table-column>
        <el-table-column label="博客内容" width="auto">
          <template #default="scope">
            <el-scrollbar height="100px">
              <p>{{ scope.row.content }}</p>
            </el-scrollbar>
          </template>

        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button @click="openEditUI(scope.row.blogId)" type="primary" :icon="Edit" size="mini" circle></el-button>
            <el-button @click="deleteBlog(scope.row.blogId)" type="danger" :icon="Delete" size="mini" circle></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 分页 -->
    <el-pagination :current-page="searchModel.pageNo" :page-sizes="[5, 10, 20, 50]" :page-size="searchModel.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" style="margin-top: 20px" />
    <!--  博客信息编辑对话框  -->
    <el-dialog @close="closeDialog" :title="title" v-model="dialogFormVisible">
      <el-form ref="blogFormRef" :model="blogForm">
        <el-form-item label="博客ID" :label-width="formLabelWidth" prop="blogId">
          <el-input v-model="blogForm.blogId" autocomplete="off" />
        </el-form-item>
        <el-form-item label="标题" :label-width="formLabelWidth" prop="title">
          <el-input v-model="blogForm.title" autocomplete="off" />
        </el-form-item>
        <el-form-item label="博客内容" :label-width="formLabelWidth" prop="content">
          <el-input v-model="blogForm.content" autocomplete="off" />
        </el-form-item>

      </el-form>
      <template v-slot:footer>
        <div>
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button @click="saveBlog" type="primary">确 定</el-button>
        </div>
      </template>
    </el-dialog>

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
import { mapMutations, useStore, mapState } from 'vuex'
import axios from 'axios'
import store from '@/store'
import { blogGetAllService, blogUpdateService, blogDeleteService } from '@/api/blog.js'

export default {
  ...mapMutations(['setUserData']),
  ...mapState(['userData']),
  setup() {
    const store = useStore()
    return { Edit, Search, Plus, Delete, Service }
  },

  data() {
    return {
      deleteBlogId: '',
      store,
      editBlogId: '',
      dialogFormVisible: false,
      total: 10,
      searchModel: {
        pageNo: 1,
        pageSize: 5
      },
      title: '',
      formLabelWidth: '130px',
      blogForm: {},
      dataList: [],
      blogList: []
    }
  },
  created() {
    this.getBlogList()
  },
  methods: {
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize
      this.getBlogList()
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo
      this.getBlogList()
    },
    async getBlogList() {
      // 调用接口获取所有博客，错误由拦截器处理
      const response = await blogGetAllService();
      console.log(response.data.data);
      this.dataList = response.data.data;
      const begin = (this.searchModel.pageNo - 1) * this.searchModel.pageSize;
      let end = this.searchModel.pageNo * this.searchModel.pageSize;
      if (end > this.dataList.length) {
        end = this.dataList.length;
      }
      this.blogList = this.dataList.slice(begin, end);
    },
    async deleteBlog(blogId) {
      this.deleteBlogId = blogId;
      // 调用接口获取删除博客，错误由拦截器处理
      const response = await blogDeleteService(this.deleteBlogId);
      console.log(response.data.data);
      this.getBlogList();
    },
    openEditUI(id) {
      if (id == null) {
        this.title = '增加记录'
      } else {
        this.title = '修改记录'
        this.editBlogId = id
        for (const injury of this.dataList) {
          if (injury.recordId == id) {
            this.blogForm = injury
            break
          }
        }
      }
      this.dialogFormVisible = true
    },
    closeDialog() {
      this.blogForm = {}
      this.$refs.blogFormRef.clearValidate()
    },
    async saveBlog() {
      this.blogForm.blogId = this.editBlogId
      // 调用接口获取修改博客，错误由拦截器处理
      const response = await blogUpdateService(this.blogForm);
      this.getBlogList();
      this.$message({
        message: "保存记录成功",
        type: 'success'
      })
      // 关闭对话框
      this.dialogFormVisible = false
      // 刷新表格
      this.getInjuryList()
    },
  }
}
</script>

<style scoped></style>
