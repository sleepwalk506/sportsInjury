<template>
  <div class="injury-page">
    <div class="injury-list">
      <div v-for="injury in injuries" :key="injury.recordId" class="injury">
        <h2>{{ injury.title }}</h2>
        <p>{{ injury.detailedDescription }}</p>
      </div>
    </div>

    <div class="back-to-main-button-injury" @click="navigateToMain">
      <i class="el-icon-arrow-left">main</i>
    </div>

    <div class="refresh-button-injury" @click="refreshPage">
      <i class="el-icon-refresh">refresh</i>
    </div>

    <router-link to="/addinjury" class="add-injury-button">
      <span>+</span>
    </router-link>
  </div>
</template>

<script>
import axios from 'axios'
import { mapState } from 'vuex'
import store from '@/store'
import { injuryDescriptionGetService } from '@/api/injury.js'

export default {
  // computed: {
  //   ...mapState(['injuries'])
  // },
  data() {
    const injuries = []
    const posts = []
    return {
      searchQuery: '',
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
      injuries
    }
  },
  created() {
    // Fetch initial injury data when the page is loaded
    this.fetchInjuryData()
  },
  methods: {
    refreshPage() {
      this.fetchInjuryData()
    },
    navigateToMain() {
      this.$router.push('/main')
    },
    async fetchInjuryData() {
      // 调用接口获取用户伤病描述，错误已由拦截器处理
      const response = await injuryDescriptionGetService();
      this.injuries = response.data.data
    }
  }
}
</script>

<style>
.injury-page {
  background-image: url('../images/hinata.jpg');
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
  color: white;
  /* 文字颜色 */
  font-family: Arial, sans-serif;
  /* 字体样式 */
  padding: 20px;
  /* 页面内边 ? */
  min-height: 100vh;
  /* 最小高度占满 ? 口 */
}

.header-injury {
  display: flex;
  justify-content: center;
  position: fixed;
  /* ??????? */
  top: 0;
  /* ????????? */
  left: 0;
  /* ????????? */
  width: 100%;
  /* ?????? */
  background-color: white;
  /* ???? */
  padding: 10px 0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.header-injury h1 {
  font-size: 24px;
  /* 标 ? 字体大 ? */
  margin: 0;
  /* 去掉标 ? 的上下外边 ? */
}

.search-box-injury {
  display: flex;
  /* 使用 Flex 布局 */
  justify-content: center;
  /* 水平居中 */
  margin-top: 20px;
  /* 上 ? 边 ? */
}

.search-input-injury {
  width: 250px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
  /* 输入框字体大 ? */
}

.searchButton-injury {
  padding: 8px 16px;
  background-color: #007BFF;
  color: white;
  border: none;
  cursor: pointer;
}

.injury-list {
  flex: 3;
  /* ???????? */
  padding: 20px;
  overflow-y: auto;
  /* ????? */
  box-sizing: border-box;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.injury {
  background-color: rgba(255, 255, 255, 0.7);
  border: 1px solid #ccc;
  border-radius: 10px;
  padding: 20px;
  width: calc(33.33% - 20px);
  /* 计算宽度，一行显 ?3 ? */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  color: #333;
  /* 文字颜色 */
  margin-bottom: 20px;
}

.injury h2 {
  font-size: 20px;
  /* 标 ? 字体大 ? */
  margin-top: 0;
  /* 去掉标 ? 的上 ? 边 ? */
}

.injury p {
  font-size: 16px;
  margin-top: 10px;
  /* 上 ? 边 ? */
}

.injury-details {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  /* 上 ? 边 ? */
}

.injury-details span {
  font-size: 14px;
  /* 详细信息字体大小 */
}

.add-injury-button {
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
  /* ?    ?          ?   */
  text-decoration: none;
}

.back-to-main-button-injury {
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

.refresh-button-injury {
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
}</style>
