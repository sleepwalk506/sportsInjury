<template>
  <div class="main-view">
    <el-menu
      default-active="user"
      background-color="#333"
      text-color="#fff"
      active-text-color="#409EFF"
    >
      <el-menu-item index="user" @click="navigateTo('user')">User</el-menu-item>
      <el-menu-item index="sports" @click="navigateTo('sportRecord')">Sports Records</el-menu-item>
      <el-menu-item index="injury" @click="navigateTo('injury')">Injury Records</el-menu-item>
      <el-menu-item index="inquiry" @click="navigateTo('inquiry')">Injury Inquiries</el-menu-item>
      <el-menu-item index="chat" @click="navigateTo('chatandblog')">Chat & Blog</el-menu-item>
      <el-menu-item index="wechat" @click="navigateTo('wechat')">Wechat</el-menu-item>
      <el-menu-item index="description" @click="navigateTo('injuryrecords')">Injury description</el-menu-item>
      <el-menu-item index="shop" @click="navigateTo('/shop')">shop</el-menu-item>
    </el-menu>

    <router-view></router-view>
  </div>
</template>

<script>
export default {
  methods: {
    navigateTo (module) {
      // 根据模块名称导航到相应的路由
      this.$router.push({ name: module })
    }
  }
}
</script>

<style scoped>
.main-view {
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

.el-menu {
  margin-bottom: 20px;
}
</style>
