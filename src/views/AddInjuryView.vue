<template>
  <div class="add-injury-page">
    <div class="header-addinjury">
    </div>

    <div class="injury-form">
      <el-input v-model="title" placeholder="Title" class="injury-input-element"></el-input>
      <el-input v-model="description" type="textarea" placeholder="Injury Description" class="injury-input-element"
        :style="{ height: '70%' }">
        <template #prepend>
          <textarea v-model="description" :style="{ height: '300px' }" placeholder="Injury Description"
            class="custom-textarea"></textarea>
        </template>
      </el-input>
      <el-button class="add-button-addinjury" type="primary" @click="addInjury">Add</el-button>
    </div>

    <div class="back-to-main-button-addinjury" @click="navigateToMain">
      <i class="el-icon-arrow-left">main</i>
    </div>
    <div class="back-button-send-addinjury" @click="navigateBack">
      <i class="el-icon-arrow-left">back</i>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapState } from 'vuex'
import { ElMessageBox } from 'element-plus'
import { injuryRecordsGetService, injuryDescriptionAddService } from '@/api/injury.js'
export default {
  data() {
    return {
      recordId: '',
      currentDate: null,
      userId: this.$store.state.userData.userId,
      title: '',
      injuryType: '',
      description: ''
    }
  },
  created() {
    this.getDescriptionId()
  },
  methods: {
    async getDescriptionId() {
      // 调用获取伤病记录的接口
      const response = await injuryRecordsGetService();
      const descriptionLength = response.data.data.length + 1
      this.recordId = this.userId + '_' + descriptionLength
    },
    navigateToMain() {
      this.$router.push('/main')
    },
    navigateBack() {
      this.$router.push('/injuryrecords')
    },
    async addInjury() {
      const injuryData = {
        userId: this.userId,
        title: this.title,
        descriptionId: this.recordId,
        detailedDescription: this.description
      }
      if (injuryData.type !== '' && injuryData.detailedDescription !== '') {
        // 调用接口，添加伤病描述，错误状态已由拦截器处理
        const response = await injuryDescriptionAddService(injuryData);
        this.$router.push('/injuryrecords');
      } else {
        ElMessageBox.alert('please send record with complete info', {
          confirmButtonText: 'OK',
          type: 'error'
        })
      }
    }
  }
}
</script>

<style scoped>
.add-injury-page {
  background-image: url('../images/hinata.jpg');
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
  color: white;
  font-family: Arial, sans-serif;
  padding: 20px;
  min-height: 90vh;
  display: flex;
  justify-content: center;
}

.header-addinjury {
  text-align: center;
  padding: 20px 0;
  color: black;
}

.header-addinjury h1 {
  font-size: 24px;
  margin: 0;
}

.injury-form {
  width: 60%;
  text-align: center;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f5f5f5;
  margin: 0 auto;
  margin-top: 20px;
}

.injury-input-element {
  margin-bottom: 10px;
}

.add-button-addinjury {
  margin-top: 10px;
}

.back-to-main-button-addinjury {
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

.back-button-send-addinjury {
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
