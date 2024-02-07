import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import MainView from '../views/MainView.vue'
import LoginView from '../views/LoginView.vue'
import UserView from '../views/UserView.vue'
import SportsView from '../views/SportsView.vue'
import ChatView from '../views/ChatView.vue'
import UserBlogView from '../views/UserBlogView.vue'
import AnotherUserView from '../views/AnotherUserView.vue'
import SendBlogView from '../views/SendBlogView.vue'
import WechatView from '../views/WechatView.vue'
import InjuryView from '../views/InjuryView.vue'
import InquiryView from '../views/InquiryView.vue'
import InjuryRecordsView from '../views/InjuryRecordsView.vue'
import AddInjuryView from '../views/AddInjuryView.vue'
import ShopView from '../views/ShopView.vue'
import OrdersView from '../views/OrderView.vue'
import AdminView from '../views/AdminView.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/main',
    name: 'main',
    component: MainView
  },
  {
    path: '/user',
    name: 'user',
    component: UserView
  },
  {
    path: '/sportRecord',
    name: 'sportRecord',
    component: SportsView
  },
  {
    path: '/chatAndBlog',
    name: 'chatandblog',
    component: ChatView
  },
  {
    path: '/userblog',
    name: 'userblog',
    component: UserBlogView
  },
  {
    path: '/anotheruser',
    name: 'anotheruser',
    component: AnotherUserView
  },
  {
    path: '/sendblog',
    name: 'sendblog',
    component: SendBlogView
  },
  {
    path: '/wechat',
    name: 'wechat',
    component: WechatView
  },
  {
    path: '/injury',
    name: 'injury',
    component: InjuryView
  },
  {
    path: '/inquiry',
    name: 'inquiry',
    component: InquiryView
  },
  {
    path: '/injuryrecords',
    name: 'injuryrecords',
    component: InjuryRecordsView
  },
  {
    path: '/addinjury',
    name: '/addinjury',
    component: AddInjuryView
  },
  {
    path:'/shop',
    name:'/shop',
    component:ShopView
  }
  ,
  {
    path:'/order',
    name:'/order',
    component:OrdersView
  },
  {
    path:'/admin',
    name:'admin',
    component: AdminView
  }

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
