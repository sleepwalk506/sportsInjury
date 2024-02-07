
// 定义一个公共变量，记录url的公共部分
const baseURL = '/api';
const axiosInstance = axios.create({ baseURL })

// 导入axios, npm install axios
import axios from 'axios'

import { ElMessage } from 'element-plus'

// 导入pinia
import { useTokenStore } from '@/store/token.js'

import router from '@/router'
// 添加响应拦截器
axiosInstance.interceptors.response.use(
    result => {
        console.log('aioxs 响应拦截器生效，操作成功');
        // 判断业务状态码
        if (result.data.code === 1)
            return result;

        // 操作失败
        // alert(result.data.message ? result.data.message : '操作失败');
        ElMessage.error(result.data.message ? result.data.message : '服务异常');
        return Promise.reject(result);
    },
    err => {
        console.log('aioxs 响应拦截器生效，操作失败');
        console.log('err: ' + err);
        // 判断响应状态码，如果v结果是401，说明未登录，则跳转到响应状态码
        if (err.response.status === 401) {
            ElMessage.error('请先登录');
            router.push('/login');
        }
        else {
            ElMessage.error('服务异常');
        }
        return Promise.reject(err);
    }
)

// 添加请求拦截器
axiosInstance.interceptors.request.use(
    (config) => {
        // 请求前的回调
        // 定义一个tokenStore
        console.log('axios请求拦截器生效');
        const tokenStore = useTokenStore();
        console.log('token:' + tokenStore.token);
        if (tokenStore.token) {
            config.headers.Authorization = tokenStore.token;
        }
        return config;
    },
    (err) => {
        console.log('axios请求拦截器生效');
        // 请求错误的回调
        Promise.reject(err);
    }
)

export default axiosInstance;