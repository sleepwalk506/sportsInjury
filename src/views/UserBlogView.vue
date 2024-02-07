<template>
  <div class="user-profile">
    <div class="user-info">
      <div class="user-info-box">
        <div class="avatar-uploader">
          <label class="avatar-uploader-label" :style="{ backgroundImage: `url(${imageUrl})` }" for="fileInput">
            <input id="fileInput" ref="fileInput" type="file" accept="image/*" style="display: none;"
              @change="handleFileChange" />
          </label>
        </div>
        <div class="info-item">
          <span class="info-label">User ID:</span> {{ userInfo.userId }}
        </div>
        <div class="info-item">
          <span class="info-label">Gender:</span> {{ userInfo.gender }}
        </div>
        <div class="info-item">
          <span class="info-label">Height:</span> {{ userInfo.height }}
        </div>
        <div class="info-item">
          <span class="info-label">Weight:</span> {{ userInfo.weight }}
        </div>
        <div class="info-item">
          <span class="info-label">Age:</span> {{ userInfo.age }}
        </div>
      </div>
    </div>
    <div class="blog-list">
      <div v-for="blog in blogs" :key="blog.id" class="blog-item">
        <div class="blog-content">
          <h3>{{ blog.title }}</h3>
          <p>{{ blog.content }}</p>
        </div>
        <div class="blog-footer">
          <div class="author-info">
            <span class="spacing">Author: {{ blog.userId }}</span>
            <span class="spacing">Update Date: {{ formatDate(blog.blogDate) }}</span>
            <span>Injury Type: {{ blog.injuryType }}</span>
          </div>
        </div>
      </div>
    </div>
    <router-link to="/sendblog" class="user-post-button">
      <span>+</span>
    </router-link>
    <div class="user-refresh-button" @click="userRefreshPage">
      <i class="el-icon-refresh">refresh</i>
    </div>
    <div class="back-button" @click="navigateBack">
      <i class="el-icon-arrow-left">back</i>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { computed } from 'vue'
import { mapState, mapMutations } from 'vuex'
import store from '@/store'
import { userGetUserImageService, userUpdateUserImageService } from '@/api/user.js'
import { blogSearchByAuthorService } from '@/api/blog.js'
// import VueUploadComponent from 'vue-upload-component'
export default {
  // components: {
  //   'vue-upload': VueUploadComponent
  // },
  data() {
    const blogs = []
    return {
      imageUrl: '',
      userInfo: {
        imageUrl: this.imageUrl,
        userId: this.$store.state.userData.userId,
        password: this.$store.state.userData.password,
        gender: null,
        height: this.$store.state.userData.height,
        weight: this.$store.state.userData.weight,
        age: this.$store.state.userData.age,
        phoneNumber: this.$store.state.userData.phoneNumber,
        email: this.$store.state.userData.email
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
      // 调用接口获取用户头像url，错误已由拦截器处理
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
      if (this.$store.state.userData.gender === 1) {
        this.userInfo.gender = 'Male'
      } else {
        this.userInfo.gender = 'Female'
      }
    },

    openFileInput() {
      this.$refs.fileInput.click() // 打开文件输入框
    },
    userRefreshPage() {
      this.fetchBlogData()
    },
    async handleFileChange(event) {
      const selectedFile = event.target.files[0]
      if (selectedFile) {
        // Get the file name
        console.log(selectedFile)
        const fileName = `../images/${selectedFile.name}`

        const userImageData = { userId: this.userInfo.userId, imageUrl: fileName }
        console.log(userImageData)
        try {
          // Send the FormData to the backend
          const response = await userUpdateUserImageService(userImageData);

          if (response.data.code === 1) {
            // Handle success
            console.log('Image uploaded successfully')

            // Update the image URL using the file name
            this.imageUrl = fileName
          } else {
            console.error('Image upload failed')
          }
        } catch (error) {
          console.error('Error uploading image:', error)
        }
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
      // 调用接口根据用户搜索博客，错误已由拦截器处理
      const response = await blogSearchByAuthorService(store.state.userData);
      this.blogs = response.data.data // 更新组件的数据
      console.log(this.blogs)
    }
  }
}
</script>

<style>
body {
  margin: 0;
  padding: 0;
}

.user-profile {
  background-image: url('../images/hinata.jpg');
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
}

.user-profile {
  display: flex;
}

.user-info {
  flex: 1;
  /* 用户信息占比一分 */
  padding: 20px;
}

.user-info-box {
  padding: 15px;
  border-radius: 10px;
  background-color: #f5f5f5;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s, transform 0.3s;
}

.user-info-box:hover {
  background-color: #ffffff;
  transform: translateY(-5px);
}

.info-item {
  margin-bottom: 10px;
}

.info-label {
  font-weight: bold;
  margin-right: 5px;
}

.blog-list {
  flex: 3;
  /* 博客列表占比三分 */
  padding: 20px;
  overflow-y: auto;
  /* 添加滚动条 */
  box-sizing: border-box;
  min-height: 100vh;
}

.blog-item {
  margin-bottom: 20px;
  padding: 15px;
  border-radius: 10px;
  background-color: #f5f5f5;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s, transform 0.3s;
}

.blog-item:hover {
  background-color: #ffffff;
  transform: translateY(-5px);
}

.blog-content {
  margin-bottom: 10px;
}

.blog-footer {
  display: flex;
  flex-direction: column;
  /* 属性垂直排列 */
  gap: 5px;
  /* 属性之间的间隔 */
  font-size: 12px;
  color: #888;
  /* margin-right: 10px; */
}

.spacing {
  margin-right: 10px;
  /* 添加间隔 */
}

.avatar-uploader-label {
  /* background-image: url(imageUrl); */
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

.avatar-uploader-label:hover {
  background-color: #f5f5f5;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.user-post-button {
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

.user-refresh-button {
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

.back-button {
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
