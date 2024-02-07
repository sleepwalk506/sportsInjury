<template>
  <div>
    <div>
      <p style="color: #8c939d; font-size: 32px; text-align: center; margin: 30px 0">运动商城</p>
    </div>
    <el-button class="button" @click="goToOrder" size="large" :icon="Memo">订 单</el-button>
    <div class="container">
      <el-card v-for="item in goodsList" :key="item.id" class="card">
        <div>
          <img class="img" :src="item.picture">
          <div class="text">
            <div class="price">
              <p>¥ </p>
              <p class="pricenum">{{item.price}}</p>
            </div>
            <p class="title">{{item.title}}</p>
          </div>
        </div>
        <div class="bottom">
          <el-input-number class="inputnum" v-model="item.number" :min="1" :max="10" @change="handleChange($event,item.number)" />
          <el-button @click="sendOrder(item.goodsId)" type="danger">购 买</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapState, mapMutations } from 'vuex'
import { ElMessageBox } from 'element-plus'
import { DateTime } from 'luxon'
import goodsList from '@/data/shop'
import {
  Memo
} from '@element-plus/icons-vue'

export default {
  setup() {
    return { Memo, goodsList }
  },
  computed: {
    ...mapState(['userData'])
  },
  data () {
    return {
      userId: this.$store.state.userData.userId,
      goodsId: '',
      orders: [],
      numOfGoods: 1
    }
  },
  methods : {
    goToOrder() {
      this.$router.push('/order');
    },
    async sendOrder (idOfGoods) {
      try {
        const Url = 'http://localhost:8082/orders'
        const dataToSend = {
          userId : this.userId,
        }
        await axios.post(Url,dataToSend).then(response =>{
          this.orders = response.data.data
        })
      } catch (error) {
        console.error('Error fetching orders:', error)
      }
      
      try {
        const lenOfOrder = this.orders.length+1
        console.log(lenOfOrder)
        const apiUrl = 'http://localhost:8082/shop'
        const newOrder = {
          orderId : this.userId + '_' + lenOfOrder,
          goodsId : idOfGoods,
          userId : this.userId,
          numOfGoods : this.numOfGoods
        }
        await axios.post(apiUrl,newOrder).then(response =>{
          this.orders = response.data.data
          ElMessageBox.alert('购买成功', {
            confirmButtonText: 'OK',
            type: 'info'
          })
        })
      } catch (error) {
        console.error('Error buying goods:', error)
      }
    },
    handleChange (val,num) {
      this.numOfGoods = num
    }
  }
}
</script>

<style scoped>
.container {
  display: flex;
  position: relative;
  justify-content: center;
  flex-wrap: wrap;
}

.button {
  position: fixed;
  right: 30px;
  top: 40px;
}

.card {
  padding: 10px;
  margin: 10px;
  width: 500px;
  height: 600px;
}

.img {
  width: 100%; /* 设置图像宽度为容器宽度的100% */
  height: auto; /* 让高度自动适应以保持图像比例 */
  max-width: 100%; /* 可选：限制图像的最大宽度 */
  max-height: 100%; /* 可选：限制图像的最大高度 */
}

.text {
  display: flex;
  align-items: center;
}

.price {
  color: crimson;
  font-size: 32px;
  width: 150px;
  display: flex;
}

.title {
  width: auto;
}

.bottom {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.inputnum {
  margin-right: 10px;
}
</style>
