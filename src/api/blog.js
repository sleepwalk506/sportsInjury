// 导入request.js工具
import request from '@/utils/request.js'

// 获取所有博客
export const blogGetAllService = () => {
    return request.get('/blog');
}

// 发布博客
export const blogAddService = (blogData) => {
    return request.post('/blog/add', blogData);
}

// 更新博客
export const blogUpdateService = (blogData) => {
    return request.put('/blog/update', blogData);
}

// 删除博客
export const blogDeleteService = (id) => {
    return request.delete('/blog/delete?blogId=' + id);
}

// 根据作者搜索博客
export const blogSearchByAuthorService = (authorData) => {
    return request.post('/blog/searchByAuthor', authorData);
}

// 根据伤病类型搜索博客
export const blogSearchByInjuryService = (injuryData) => {
    return request.post('/blog/searchByInjury', injuryData);
}