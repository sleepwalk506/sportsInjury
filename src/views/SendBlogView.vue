<template>
  <div class="blog-container">
    <div class="editor-container">
      <el-input v-model="title" placeholder="Title" class="input-element"></el-input>
      <el-input v-model="injuryType" placeholder="Injury Type" class="input-element"></el-input>
      <el-input v-model="content" type="textarea" placeholder="Content" class="input-element" :style="{ height: '70%' }">
        <template #prepend>
          <textarea :style="{ height: '300px' }" v-model="content" placeholder="Content"
            class="custom-textarea"></textarea>
        </template></el-input>
      <el-button class="send-button" type="primary" @click="sendBlog">Send</el-button>
    </div>
    <div class="back-button-send" @click="navigateBack">
      <i class="el-icon-arrow-left">back</i>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapState, mapMutations } from 'vuex'
import { ElMessageBox } from 'element-plus'
import store from '@/store'
import { blogSearchByAuthorService, blogAddService } from '@/api/blog.js'

export default {
  // computed: {
  //   ...mapState(['userData'])
  // },
  data() {
    return {
      blogId: '',
      currentDate: null,
      userId: this.$store.state.userData.userId,
      title: '',
      injuryType: '',
      content: ''
    }
  },
  created() {
    this.currentDate = new Date()
    this.getBlogId()
  },
  methods: {
    async getBlogId() {
      // 调用接口查询用户博客，错误已由拦截器处理
      const dataToSend = { userId: this.userId }
      const response = await blogSearchByAuthorService(dataToSend);
      const blogLength = response.data.data.length + 1
      this.blogId = this.userId + '_' + blogLength
    },
    navigateBack() {
      this.$router.push('/userblog')
    },
    async sendBlog() {
      const blogData = {
        blogId: this.blogId,
        userId: this.userId,
        title: this.title,
        content: this.content,
        blogDate: this.currentDate,
        injuryType: this.injuryType
      }
      if (blogData.title !== '' && blogData.content !== '' && blogData.injuryType !== '') {
        // 调用接口发送博客，错误已由拦截器处理
        const response = await blogAddService(blogData);
        if (response.data.code === 1) {
          this.$router.push('/userblog')
        } else {
          ElMessageBox.alert('send blog failed', {
            confirmButtonText: 'OK',
            type: 'error'
          })
        }
      } else {
        ElMessageBox.alert('please send blog with complete info', {
          confirmButtonText: 'OK',
          type: 'error'
        })
      }
    }
  }
}
</script>

<style scoped>
.blog-container {
  background-image: url('../images/hinata.jpg');
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
}

.blog-container {
  display: flex;
  justify-content: center;
  /* align-items: center; */
  height: 90vh;
}

.editor-container {
  width: 60%;
  text-align: center;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f5f5f5;
}

.input-element {
  margin-bottom: 10px;
}

.send-button {
  margin-top: 10px;
}

.back-button-send {
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
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>
