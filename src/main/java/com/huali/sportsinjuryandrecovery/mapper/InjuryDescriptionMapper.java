package com.huali.sportsinjuryandrecovery.mapper;

import com.huali.sportsinjuryandrecovery.pojo.InjuryDescription;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InjuryDescriptionMapper {
    List<InjuryDescription> getInjuryDescriptionList(String userId);
    int addInjuryDescription(String userId,String descriptionId,String title,String detailedDescription);
}
