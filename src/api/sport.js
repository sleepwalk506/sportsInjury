import request from '@/utils/request.js'

// 获取用户所有运动记录
export const sportsRecordsGetService = () => {
    return request.get('/sportsRecords');
}

// 添加运动记录
export const sportsRecordsAddService = (injuryRecordData) => {
    return request.post('/sportsRecords/add', injuryRecordData);
}

// 更新运动记录
export const sportsRecordsUpdateService = (injuryRecordData) => {
    return request.put('/sportsRecords/update', injuryRecordData);
}

// 删除运动记录
export const sportsRecordsDeleteService = (id) => {
    return request.delete('/sportsRecords/delete?recordId=' + id);
}