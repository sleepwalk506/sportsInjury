package com.huali.sportsinjuryandrecovery.service;

import com.huali.sportsinjuryandrecovery.pojo.InjuryDescription;

import java.util.List;

public interface InjuryDescriptionService {
    List<InjuryDescription> getInjuryDescriptionList(String userId);
    int addInjuryDescription(String userId,String descriptionId,String title,String detailedDescription);
}
