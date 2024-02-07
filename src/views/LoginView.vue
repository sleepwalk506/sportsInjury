<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 class="login-title">{{ formMode === 'login' ? 'User Login' : 'User Registration' }}</h2>
      <el-form ref="form" :model="formData" :rules="formRules" label-width="80px">
        <el-form-item label="userId" prop="userId">
          <el-input v-model="formData.userId"></el-input>
        </el-form-item>
        <el-form-item label="password" prop="password">
          <el-input type="password" v-model="formData.password"></el-input>
        </el-form-item>
        <el-form-item v-if="formMode === 'register'" label="confirmPassword" prop="confirmPassword">
          <el-input type="password" v-model="formData.confirmPassword"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">{{ formMode === 'login' ? 'Login' : 'Register' }}</el-button>
          <el-button @click="toggleFormMode">{{ formMode === 'login' ? 'Switch to Register' : 'Switch to Login'
          }}</el-button>
        </el-form-item>
        <div style="display: flex; justify-content: flex-end;">
          <p @click="openDialog" class="button">Admin Login</p>
        </div>
      </el-form>
    </el-card>
    <el-dialog align-center :title="title" v-model="dialogFormVisible">
      <el-form-item label="Admin ID" label-width="100px" prop="userId">
        <el-input v-model="adminId" autocomplete="off" />
      </el-form-item>
      <el-form-item label="password" label-width="100px" prop="userId">
        <el-input v-model="adminPassword" type="password" autocomplete="off" />
      </el-form-item>
      <div>
        <el-button @click="closeDialog">cancel</el-button>
        <el-button type="primary" @click="adminLogin">login</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
import { mapMutations } from 'vuex'
import { ElMessageBox } from 'element-plus'
import { useTokenStore } from '@/store/token.js'
import { userLoginService } from '@/api/user.js'
export default {
  data() {
    return {
      dialogFormVisible: false,
      adminId: "",
      adminPassword: "",
      formMode: 'login', // 'login' or 'register'
      formData: {
        userId: '',
        password: '',
        confirmPassword: ''
      },
      formRules: {
        userId: [{ required: true, message: 'Please input userId', trigger: 'blur' }],
        password: [{ required: true, message: 'Please input password', trigger: 'blur' }],
        confirmPassword: [
          { required: true, message: 'Please confirm password', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              if (value !== this.formData.password) {
                callback(new Error('Passwords do not match'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    ...mapMutations(['setUser']),
    openDialog() {
      this.dialogFormVisible = true
    },
    closeDialog() {
      this.dialogFormVisible = false
    },
    adminLogin() {
      this.closeDialog()
      if (this.adminId == "admin" && this.adminPassword == "123456") {
        this.$alert('登录成功', {
          type: 'success',
        })
        this.$router.push('/admin');
      } else {
        this.$alert('用户名或密码错误', {
          type: 'fail',
        })

      }
    },
    toggleFormMode() {
      this.formMode = this.formMode === 'login' ? 'register' : 'login'
      this.clearForm()
    },
    clearForm() {
      this.$refs.form.resetFields()
    },
    submitForm() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          const apiUrl = this.formMode === 'login' ? 'user/login' : 'user/register'
          const userData = {
            userId: this.formData.userId,
            password: this.formData.password
          }
          if (this.formMode === 'register') {
            userData.confirmPassword = this.formData.confirmPassword
          }
          const response = await userLoginService(userData);
          if (response.data.code === 0) {
            if (response.data.msg === 'this user id has been used,please change a user id') {
              ElMessageBox.alert('this user id has been used,please change a user id', {
                confirmButtonText: 'OK',
                type: 'error'
              })
            } else if (response.data.msg === 'user register failed') {
              ElMessageBox.alert('user register failed', {
                confirmButtonText: 'OK',
                type: 'error'
              })
            } else {
              ElMessageBox.alert('User or password is wrong', 'Login Failed', {
                confirmButtonText: 'OK',
                type: 'error'
              })
            }
          } else if (response.data.code === 1) {
            this.setUser(response.data.data)
            // console.log(this.$store.state.userData)
            // ���ݷ��ص����ݴ����¼/ע��ɹ��߼�
            console.log(response.data.data);
            const tokenStore = useTokenStore();
            // 把得到的token存储到pinia中
            tokenStore.removeToken();
            tokenStore.setToken(response.data.data);
            this.$router.push('/main')
          }
        }
        else {
          console.log('Form validation failed')
        }
      })
    }
  }
}

</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(to bottom, #000000, #333333);
}

.login-card {
  width: 400px;
  padding: 20px;
  background-color: #222;
  border-radius: 15px;
  color: white;
}

.login-title {
  font-size: 24px;
  margin-bottom: 20px;
  text-align: center;
}

.button {
  margin-right: 15px;
  width: 100px;
  height: 5px;
  font-size: 12px;
}
</style>
