// 导入request.js工具
import request from '@/utils/request.js'

// 提供调用注册接口的函数
export const userRegisterService = function (registerData) {
    return request.post('/user/register', registerData);
}

// 提供调用登录接口的函数
export const userLoginService = function (loginData) {
    return request.post('/user/login', loginData);
}

// 获取所有用户详细信息
export const userInfoService = () => {
    return request.get('/user/all');
}

// 修改用户信息
export const userInfoUpdateService = (userInfoData) => {
    return request.put('/user/update', userInfoData);
}

// 获取用户头像
export const userGetUserImageService = () => {
    return request.get('/user/getUserImage');
}

// 修改用户头像
export const userUpdateUserImageService = (userImageData) => {
    return request.put('/user/updateUserImage', userImageData);
}

// 根据id搜索用户
export const userGetByIdService = (userId) => {
    return request.get('/user/getById?userId=' + userId);
}
