import request from '@/utils/request.js'

// 获取用户所有伤病记录
export const injuryRecordsGetService = () => {
    return request.get('/injuryRecords');
}

// 添加伤病记录
export const injuryRecordsAddService = (injuryRecordData) => {
    return request.post('/injuryRecords/add', injuryRecordData);
}

// 更新伤病记录
export const injuryRecordsUpdateService = (injuryRecordData) => {
    return request.put('/injuryRecords/update', injuryRecordData);
}

// 删除伤病记录
export const injuryRecordsDeleteService = (id) => {
    return request.delete('/injuryRecords/delete?recordId=' + id);
}

// 根据伤病类型查找
export const injuryRecordsSearchByTypeService = (injuryType) => {
    return request.get('/injuryRecords/searchByType?injuryType=' + injuryType);
}

// 根据伤病位置查找
export const injuryRecordsSearchByPositionService = (injuryPosition) => {
    return request.get('/injuryRecords/searchByPosition?injuryPosition=' + injuryPosition);
}

// 获取用户所有伤病描述
export const injuryDescriptionGetService = () => {
    return request.get('description');
}

// 添加伤病描述
export const injuryDescriptionAddService = (descriptionData) => {
    return request.post('description/add', descriptionData);
}