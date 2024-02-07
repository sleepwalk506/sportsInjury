import request from '@/utils/request.js'

// 咨询
export const injuryInquiryService = (injuryTypeData) => {
    return request.post('/injuryInquiry', injuryTypeData)
}