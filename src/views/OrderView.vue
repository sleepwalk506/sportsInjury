<template>
<div>
    <div>
      <p style="color: #8c939d; font-size: 32px; text-align: center">订单详情</p>
    </div>
    <div style="display: flex; justify-content: center">
      <div style="display: flex;  margin:30px 0;">
        <el-input style="width: 930px; margin-right: 20px; border-radius: 5px" v-model="input" placeholder="请输入订单号" clearable />
        <el-button round :icon="Search" @click="getOrders">搜 索</el-button>
      </div></div>
    <div class="container">
      <el-card v-for="order in orders" :key="order.id" class="card">
        <div  class="cardinter">
          <div class="top">
            <img class="img" :src="goodsList[order.goodsId].picture">
            <p class="title">{{goodsList[order.goodsId].title}}</p>
          </div>
          <div class="bottom">
            <el-button class="button" @click="openDialog(order.numOfGoods)" style="margin-right: 10px">修改订单</el-button>
            <el-button class="button" @click="deleteOrder(order.orderId)" type="danger">删除订单</el-button>
            <p class="num">共{{order.numOfGoods}}件</p>
            <p class="pay">实付款: {{ (goodsList[order.goodsId].price*order.numOfGoods).toFixed(2)}}</p>
          </div>
        </div>
        <el-dialog align-center title="修改购买数量" v-model="visible">
          <el-input-number  v-model="number" :min="1" style="margin-right: 10px"/>
          <el-button type="primary" @click="updateOrder(order.orderId)">确 定</el-button>
        </el-dialog>
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


export default {
  setup(){
    return{
      goodsList
    }
  },
  computed: {
    ...mapState(['userData'])
  },
  data () {
    return {
      visible:false,
      number: 0,
      userId: this.$store.state.userData.userId,
      goodsId: '',
      selectedOrderId: '',
      orders: [],
      input: '',
    }
  },
  mounted(){
    console.log("123")
    this.fetchOrder()
  },
  methods : {
    openDialog(num) {
      this.number = num
      this.visible = true
    },
    async updateOrder(orderId){
      // this.number
      try {
        const apiUrl = 'http://localhost:8082/orders/change'
        const dataToSend = {
          orderId : orderId,
          numOfGoods : this.number
        }
        await axios.post(apiUrl,dataToSend).then(response =>{
          //this.orders = response.data.data
          this.fetchOrder()
          this.visible = false
          //展示orders
        })
      } catch (error) {
        console.error('Error checking orders:', error)
      }
    },
    getOrders(){
      this.fetchOrder_1(this.input)
    },
    async fetchOrder_1 (orderId) {
      try {
        const apiUrl = 'http://localhost:8082/orders/search'
        const dataToSend = {
          orderId : orderId,
        }
        await axios.post(apiUrl,dataToSend).then(response =>{
          console.log(response)
          this.orders = response.data.data
          
          //展示orders
        })
      } catch (error) {
        console.error('Error checking orders:', error)
      }
    },
    async fetchOrder () {
      try {
        const apiUrl = 'http://localhost:8082/orders'
        const dataToSend = {
          userId : this.userId,
        }
        await axios.post(apiUrl,dataToSend).then(response =>{
          console.log(response)
          this.orders = response.data.data
          
          //展示orders
        })
      } catch (error) {
        console.error('Error checking orders:', error)
      }
    },
    async deleteOrder (orderId) {
      try {
        const apiUrl = 'http://localhost:8082/orders/delete'
        const dataToSend = {
          userId : this.userId,
          orderId : orderId
        }
        await axios.post(apiUrl,dataToSend).then(response =>{
          this.orders = response.data.data
          //展示orders
        })
      } catch (error) {
        console.error('Error deleting orders:', error)
      }
    }
  }
}
</script>

<style scoped>
.container {
  display: flex;
  width: 100%;
  flex-wrap: wrap;
  justify-content: center;
}

.card {
  margin: 20px 40px;
}

.cardinter {
  width: 1000px;
}

.top {
  height: 100%;
  width: 100%;
  padding: 20px;
  display: flex;
}
.img {
  width: 150px; /* 设置图像宽度为容器宽度的100% */
  height: 150px; /* 让高度自动适应以保持图像比例 */
  max-width: 100%; /* 可选：限制图像的最大宽度 */
  max-height: 100%; /* 可选：限制图像的最大高度 */
}

.bottom {
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: flex-end;
}
.title {
  margin-left: 20px;
  font-size: 24px;
  text-align: left;
}
.button {

}

.num {
  margin-right: 10px;
  margin-left: 10px;
  font-size: 20px;
}
.pay {
  font-size: 20px;
}
</style>

