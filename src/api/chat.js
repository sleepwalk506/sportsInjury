import request from '@/utils/request'

// 获取用户所有消息
export const chatGetAllMessageService = () => {
    return request.get('/chat');
}

// 发送消息
export const chatSendMessageService = (messageData) => {
    return request.post('/chat/send', messageData);
}

// 获取好友列表
export const chatGetFriendsService = () => {
    return request.get('/chat/friends');
}