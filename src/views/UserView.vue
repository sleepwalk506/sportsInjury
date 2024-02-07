<template>
  <div class="user-view">
    <div class="user-card">
      <div class="user-info" v-if="!isEditing">
        <p><strong>User ID:</strong> {{ userInfo.userId }}</p>
        <p><strong>Password:</strong> {{ userInfo.password }}</p>
        <p><strong>Gender:</strong> {{ userInfo.gender }}</p>
        <p><strong>Height:</strong> {{ userInfo.height }}</p>
        <p><strong>Weight:</strong> {{ userInfo.weight }}</p>
        <p><strong>Phone Number:</strong> {{ userInfo.phoneNumber }}</p>
        <p><strong>Email:</strong> {{ userInfo.email }}</p>
        <button class="edit-button" @click="toggleEdit">Edit</button>
      </div>
      <div class="edit-controls" v-else>
        <input v-model="editedUserInfo.userId" placeholder="User ID" disabled />
        <input v-model="editedUserInfo.password" placeholder="Password" />
        <input v-model="editedUserInfo.gender" placeholder="Gender" />
        <input v-model="editedUserInfo.height" placeholder="Height" />
        <input v-model="editedUserInfo.weight" placeholder="Weight" />
        <input v-model="editedUserInfo.phoneNumber" placeholder="Phone Number" />
        <input v-model="editedUserInfo.email" placeholder="Email" />
        <div class="edit-buttons">
          <button @click="saveChanges">Save</button>
          <button @click="cancelEdit">Cancel</button>
        </div>
      </div>
      <div class="user-view-back-button">
        <button @click="goBack">Back</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapState, mapMutations } from 'vuex'
import { userInfoUpdateService } from '@/api/user.js'
// import { mapMutations } from 'vuex'
export default {
  computed: {
    ...mapState(['userData'])
  },
  data() {
    return {
      isEditing: false,
      userInfo: {
        userId: this.$store.state.userData.userId,
        password: this.$store.state.userData.password,
        gender: null,
        height: this.$store.state.userData.height,
        weight: this.$store.state.userData.weight,
        phoneNumber: this.$store.state.userData.phoneNumber,
        email: this.$store.state.userData.email
      },
      editedUserInfo: {},
      updateData: {}
    }
  },
  created() {
    this.getGender()
  },
  methods: {
    ...mapMutations(['setUser']),
    getGender() {
      if (this.$store.state.userData.gender === 1) {
        this.userInfo.gender = 'Male'
      } else {
        this.userInfo.gender = 'Female'
      }
    },
    toggleEdit() {
      this.isEditing = !this.isEditing
      if (this.isEditing) {
        // 将原始用户信息复制到编辑用户信息对象中
        this.editedUserInfo = { ...this.userInfo }
      }
    },
    async saveChanges() {
      // this.$store.state.userData = { ...this.editedUserInfo }
      this.updateData = this.editedUserInfo
      if (this.updateData.gender === 'Male') {
        this.updateData.gender = 1
      } else {
        this.updateData.gender = 2
      }
      // 调用接口修改用户信息，错误已由拦截器处理
      const response = await userInfoUpdateService(this.updateData);
      console.log(response.data)
      this.userInfo = { ...this.editedUserInfo }
      this.setUser(this.userInfo)
      console.log(this.$store.state.userData)
      // 根据返回的数据处理登录成功逻辑
      this.$router.push('/main')
      this.isEditing = false
    },
    cancelEdit() {
      // 取消编辑模式
      this.isEditing = false
    },
    goBack() {
      // 返回到主界面
      this.$router.push({ name: 'main' })
    }
  }
}
</script>

<style scoped>
.user-view {
  background-color: #333;
  color: #fff;
  text-align: center;
  font-size: 18px;
  padding: 20px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.user-card {
  background-color: #444;
  padding: 20px;
  border-radius: 10px;
  width: 400px;
}

.edit-controls {
  margin-top: 20px;
}

.edit-buttons {
  margin-top: 10px;
}

.user-view-back-button {
  margin-top: 20px;
}

input {
  width: 100%;
  padding: 8px;
  margin-bottom: 10px;
}
</style>
