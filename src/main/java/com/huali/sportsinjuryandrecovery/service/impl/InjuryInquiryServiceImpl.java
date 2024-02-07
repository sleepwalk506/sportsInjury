package com.huali.sportsinjuryandrecovery.service.impl;
import com.huali.sportsinjuryandrecovery.mapper.InjuryInquiryMapper;
import com.huali.sportsinjuryandrecovery.pojo.Injury;
import com.huali.sportsinjuryandrecovery.service.InjuryInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InjuryInquiryServiceImpl implements InjuryInquiryService{
    @Autowired
    private InjuryInquiryMapper injuryInquiryMapper;
    @Override
    public List<Injury> injuryInquiryList(String injuryType) {
        return injuryInquiryMapper.injuryInquiryList(injuryType);
    }
}
