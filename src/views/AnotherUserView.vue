<template>
  <div class="user-profile-another">
    <div class="user-info-another">
      <div class="user-info-box-another">
        <div class="avatar-uploader-another">
          <label class="avatar-uploader-label-another" :style="{ backgroundImage: `url(${imageUrl})` }" for="fileInput">
            <input id="fileInput-another" ref="fileInput" type="file" accept="image/*" style="display: none;"
              @change="handleFileChange" />
          </label>
        </div>
        <div class="info-item-another">
          <span class="info-label-another">User ID:</span> {{ userInfo.userId }}
        </div>
        <div class="info-item-another">
          <span class="info-label-another">Gender:</span> {{ userInfo.gender }}
        </div>
        <div class="info-item-another">
          <span class="info-label-another">Height:</span> {{ userInfo.height }}
        </div>
        <div class="info-item-another">
          <span class="info-label-another">Weight:</span> {{ userInfo.weight }}
        </div>
        <div class="info-item-another">
          <span class="info-label-another">Age:</span> {{ userInfo.age }}
        </div>
      </div>
    </div>
    <div class="blog-list-another">
      <div v-for="blog in blogs" :key="blog.id" class="blog-item-another">
        <div class="blog-content-another">
          <h3>{{ blog.title }}</h3>
          <p>{{ blog.content }}</p>
        </div>
        <div class="blog-footer-another">
          <div class="author-info-another">
            <span class="spacing-another">Author: {{ blog.userId }}</span>
            <span class="spacing-another">Update Date: {{ formatDate(blog.blogDate) }}</span>
            <span>Injury Type: {{ blog.injuryType }}</span>
          </div>
        </div>
      </div>
    </div>
    <router-link to="/userblog" class="user-post-button-another">
      <span>+</span>
    </router-link>
    <div class="user-refresh-button-another" @click="userRefreshPage">
      <i class="el-icon-refresh-another">refresh</i>
    </div>
    <div class="back-button-another" @click="navigateBack">
      <i class="el-icon-arrow-left-another">back</i>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { computed } from 'vue'
import { mapState, mapMutations } from 'vuex'
import store from '@/store'
import { userGetUserImageService } from '@/api/user.js'
import { blogSearchByAuthorService } from '@/api/blog.js'
// import VueUploadComponent from 'vue-upload-component'
export default {
  // computed: {
  //   ...mapMutations(['anotherUserData'])
  // },
  data() {
    const blogs = []
    return {
      imageUrl: '',
      userInfo: {
        imageUrl: this.imageUrl,
        userId: this.$store.state.anotherUserData.userId,
        password: this.$store.state.anotherUserData.password,
        gender: null,
        height: this.$store.state.anotherUserData.height,
        weight: this.$store.state.anotherUserData.weight,
        age: this.$store.state.anotherUserData.age,
        phoneNumber: this.$store.state.anotherUserData.phoneNumber,
        email: this.$store.state.anotherUserData.email
      },
      blogs
    }
  },
  created() {
    this.getGender()
    this.fetchBlogData()
    this.getUserImage()
  },
  methods: {
    formatDate(dateString) {
      const date = new Date(dateString)
      date.setDate(date.getDate() + 1)
      const formattedDate = date.toISOString().split('T')[0]
      return formattedDate
    },
    async getUserImage() {
      // 调用接口获取用户头像url，错误由拦截器处理
      const response = await userGetUserImageService();
      if (response.data.code !== 0) {
        const imageDataUrl = response.data.data.imageUrl
        const resizedImage = await this.resizeImageFromUrl(imageDataUrl, 100, 100)
        this.imageUrl = resizedImage
      } else {
        this.imageUrl = ''
      }
    },
    navigateBack() {
      this.$router.push('/chatAndBlog')
    },
    getGender() {
      if (this.$store.state.anotherUserData.gender === 1) {
        this.userInfo.gender = 'Male'
      } else {
        this.userInfo.gender = 'Female'
      }
    },
    openFileInput() {
      this.$refs.fileInput.click() // 打开文件输入框
    },

    async handleFileChange(event) {
      const selectedFile = event.target.files[0]
      if (selectedFile) {
        const resizedImage = await this.resizeImage(selectedFile, 100, 100)
        this.imageUrl = resizedImage
      }
    },
    async resizeImage(file, width, height) {
      return new Promise((resolve) => {
        const reader = new FileReader()
        reader.onload = (e) => {
          const img = new Image()
          img.src = e.target.result
          img.onload = () => {
            const canvas = document.createElement('canvas')
            canvas.width = width
            canvas.height = height
            const ctx = canvas.getContext('2d')
            ctx.drawImage(img, 0, 0, width, height)
            const resizedDataUrl = canvas.toDataURL(file.type)
            resolve(resizedDataUrl)
          }
        }
        reader.readAsDataURL(file)
      })
    },
    async resizeImageFromUrl(imageUrl, width, height) {
      return new Promise((resolve) => {
        const img = new Image()
        img.crossOrigin = 'anonymous' // This might be needed if the image URL doesn't have CORS headers.
        img.src = imageUrl
        img.onload = () => {
          const canvas = document.createElement('canvas')
          canvas.width = width
          canvas.height = height
          const ctx = canvas.getContext('2d')
          ctx.drawImage(img, 0, 0, width, height)
          const resizedDataUrl = canvas.toDataURL()
          resolve(resizedDataUrl)
        }
        img.onerror = () => {
          // Handle error loading the image
          resolve('') // Return an empty URL
        }
      })
    },
    async fetchBlogData() {
      // 调用接口获取用户博客，错误由拦截器处理
      const response = await blogSearchByAuthorService(store.state.anotherUserData);
      this.blogs = response.data.data // 更新组件的数据
    }
  }
}
</script>

<style>
body {
  margin: 0;
  padding: 0;
}

.user-profile-another {
  background-image: url('../images/hinata.jpg');
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
}

.user-profile-another {
  display: flex;
}

.user-info-another {
  flex: 1;
  /* 用户信息占比一分 */
  padding: 20px;
}

.user-info-box-another {
  padding: 15px;
  border-radius: 10px;
  background-color: #f5f5f5;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s, transform 0.3s;
}

.user-info-box-another:hover {
  background-color: #ffffff;
  transform: translateY(-5px);
}

.info-item-another {
  margin-bottom: 10px;
}

.info-label-another {
  font-weight: bold;
  margin-right: 5px;
}

.blog-list-another {
  flex: 3;
  /* 博客列表占比三分 */
  padding: 20px;
  overflow-y: auto;
  /* 添加滚动条 */
  box-sizing: border-box;
  min-height: 100vh;
}

.blog-item-another {
  margin-bottom: 20px;
  padding: 15px;
  border-radius: 10px;
  background-color: #f5f5f5;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s, transform 0.3s;
}

.blog-item-another:hover {
  background-color: #ffffff;
  transform: translateY(-5px);
}

.blog-content-another {
  margin-bottom: 10px;
}

.blog-footer-another {
  display: flex;
  flex-direction: column;
  /* 属性垂直排列 */
  gap: 5px;
  /* 属性之间的间隔 */
  font-size: 12px;
  color: #888;
  /* margin-right: 10px; */
}

.spacing-another {
  margin-right: 10px;
  /* 添加间隔 */
}

.avatar-uploader-label-another {
  display: inline-block;
  width: 100px;
  height: 100px;
  text-align: center;
  line-height: 100px;
  border: 1px dashed #d9d9d9;
  border-radius: 50%;
  /* 圆形样式 */
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader-label-another:hover {
  background-color: #f5f5f5;
}

.avatar-another {
  width: 100px;
  height: 100px;
  border-radius: 50%;
}

.avatar-uploader-icon-another {
  font-size: 28px;
  color: #8c939d;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.user-post-button-another {
  position: fixed;
  bottom: 20px;
  left: 180px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 60px;
  height: 60px;
  background-color: rgb(196, 130, 87);
  color: white;
  border-radius: 50%;
  font-size: 20px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  z-index: 100;
  /* 确保按钮在其他内容之上 */
  text-decoration: none;
}

.user-refresh-button-another {
  position: fixed;
  bottom: 20px;
  left: 20px;
  width: 60px;
  height: 60px;
  background-color: rgb(196, 130, 87);
  color: white;
  border-radius: 50%;
  text-align: center;
  line-height: 60px;
  cursor: pointer;
  font-size: 15px;
}

.back-button-another {
  position: fixed;
  bottom: 20px;
  left: 100px;
  width: 60px;
  height: 60px;
  background-color: rgb(196, 130, 87);
  color: white;
  border-radius: 50%;
  text-align: center;
  line-height: 60px;
  cursor: pointer;
  font-size: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>
