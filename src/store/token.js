// 定义store
import { defineStore } from 'pinia'
import { ref } from 'vue'

/*
    第一个参数：名字，唯一性
    第二个参数：函数，函数内部可以定义状态的所有内容
    返回值：函数
*/
export const useTokenStore = defineStore(
    "token",
    () => {
        // 定义状态的内容

        // 1.响应式变量
        const token = ref('');
        // 2.定义一个函数，修改token的值
        const setToken = (newToken) => {
            token.value = newToken;
        };
        // 3.函数，移除token的值
        const removeToken = () => {
            token.value = '';
        };
        return {
            token, setToken, removeToken
        }
    },
    {
        // pinia持久化存储
        // 1. 安装persist npm install pinia-persistedstate-plugin
        // 2. 在pinia中使用persist，即在main.js中引入pinia-persistedstate-plugin，然后创建实例，最后pinia.use(persist)
        // 3. 定义状态Store时指定持久化配置参数，如下
        persist: true
    }

);
