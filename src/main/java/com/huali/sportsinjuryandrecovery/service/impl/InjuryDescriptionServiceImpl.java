package com.huali.sportsinjuryandrecovery.service.impl;

import com.huali.sportsinjuryandrecovery.mapper.InjuryDescriptionMapper;
import com.huali.sportsinjuryandrecovery.pojo.InjuryDescription;
import com.huali.sportsinjuryandrecovery.service.InjuryDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InjuryDescriptionServiceImpl implements InjuryDescriptionService {
    @Autowired
    private InjuryDescriptionMapper injuryDescriptionMapper;
    @Override
    public List<InjuryDescription> getInjuryDescriptionList(String userId) {
        return injuryDescriptionMapper.getInjuryDescriptionList(userId);
    }

    @Override
    public int addInjuryDescription(String userId, String descriptionId, String title, String detailedDescription) {
        return injuryDescriptionMapper.addInjuryDescription(userId,descriptionId,title,detailedDescription);
    }
}
