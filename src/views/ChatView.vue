<template>
  <div class="chat-page">
    <div class="browse-post-container">
      <div class="header">
        <div class="search-box">
          <input v-model="searchByAuthorQuery" placeholder="search by author..." class="search-input">
          <button @click="searchByAuthor" class="searchButton">search</button>
        </div>
        <div class="search-box">
          <input v-model="searchByInjuryQuery" placeholder="search by injury..." class="search-input">
          <button @click="searchByInjury" class="searchButton">search</button>
        </div>
      </div>
      <div class="space-fk"></div>
      <div v-for="post in posts" :key="post.id" class="post">
        <h2>{{ post.title }}</h2>
        <p>{{ post.content }}</p>
        <div class="post-details">
          <span @click="navigateToAnotherUser(post.userId)" class="hand-cursor">author:{{ post.userId }}</span>
          <span>update date:{{ formatDate(post.blogDate) }}</span>
          <span>injury type:{{ post.injuryType }}</span>
        </div>
      </div>
    </div>
    <router-link to="/userblog" class="post-button">
      <span>+</span>
    </router-link>
    <div class="refresh-button" @click="refreshPage">
      <i class="el-icon-refresh">refresh</i>
    </div>
    <div class="back-to-main-button" @click="navigateToMain">
      <i class="el-icon-arrow-left">main</i>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
import { mapMutations, useStore, mapState, mapActions } from 'vuex'
import { blogSearchByAuthorService, blogSearchByInjuryService, blogGetAllService } from '@/api/blog.js'
import { userGetByIdService } from '@/api/user.js'
// import { ref } from 'vuex'
export default {
  computed: {
    ...mapState(['anotherUserData'])
  },
  data() {
    const posts = []

    return {
      searchByAuthorQuery: '',
      searchQuery: '',
      posts
    }
  },
  created() {
    this.fetchBlogData()
  },
  methods: {
    ...mapMutations(['setAnotherUser']),
    formatDate(dateString) {
      const date = new Date(dateString)
      date.setDate(date.getDate() + 1)
      const formattedDate = date.toISOString().split('T')[0]
      return formattedDate
    },
    refreshPage() {
      this.fetchBlogData()
    },
    navigateToMain() {
      this.$router.push('/main')
    },
    async navigateToAnotherUser(userId) {
      // const apiUrl = 'http://localhost:8082/user/getUserBlog'
      // const anotherUserData = { userId: userId }
      // const response = await axios.post(apiUrl, anotherUserData)
      // const store = useStore()
      // store.commit('setAnotherUser', response.data.data)
      const response = await userGetByIdService(userId);
      this.setAnotherUser(response.data.data)
      this.$router.push('/anotheruser')
    },
    async searchByAuthor() {
      // 调用接口获取用户博客，错误已由拦截器处理
      const dataToSend = { userId: this.searchByAuthorQuery }
      const response = await blogSearchByAuthorService(dataToSend);
      this.posts = response.data.data
    },
    async searchByInjury() {
      // 调用接口获取博客，错误已由拦截器处理
      const dataToSend = this.searchByInjuryQuery;
      const response = await blogSearchByAuthorService(dataToSend);
      this.posts = response.data.data
    },
    async fetchBlogData() {
      // 调用接口获取博客，错误已由拦截器处理
      const response = await blogGetAllService();
      this.posts = response.data.data // 更新组件的数据
    }
  },
  watch: {
    searchQuery: {
      handler: 'getPosts',
      immediate: true
    }
  }
}
</script>
<style>
.chat-page {
  background-image: url('../images/hinata.jpg');
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
}

body {
  margin: 0;
  padding: 0;
  /* background: linear-gradient(to bottom,#ed7104, #df9ba5);
  background-image: url('../images/hinata.jpg'); */
  /* background-size: cover;
  background-position: center;
  background-attachment: fixed; */
}

.browse-post-container {
  /* 圆角边框 */
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  min-height: 100vh;
  box-sizing: border-box;

  display: flex;
  flex-direction: column;
  /* 垂直排列内容 */
  align-items: center;
  /* 垂直居中 */
  /* text-align: center; */
}

.spacer {
  width: 10px;
  /* 调整间距的宽度 */
}

.search-input {
  width: 250px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.search-box {
  display: flex;
  align-items: center;
  margin-right: 10px;
  /* margin-bottom: 30px; */
}

.searchButton {
  padding: 8px 16px;
  background-color: #007BFF;
  color: white;
  border: none;
  cursor: pointer;
}

.hand-cursor {
  cursor: pointer;
}

.header {
  display: flex;
  justify-content: center;
  position: fixed;
  /* 固定在页面上方 */
  top: 0;
  /* 距离页面顶部的距离 */
  left: 0;
  /* 距离页面左侧的距离 */
  width: 100%;
  /* 占据整个宽度 */
  background-color: white;
  /* 背景颜色 */
  padding: 10px 0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.space-fk {
  padding-bottom: 40px;
}

.post-list {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.post-details span {
  margin-right: 10px;
  /* 为作者和更新日期之间添加间距 */
}

.post {
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 10px;
  padding: 20px;
  margin-bottom: 20px;
  width: 80%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  /* margin-top: 40px; */
}

.post-button {
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

.refresh-button {
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

.back-to-main-button {
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
