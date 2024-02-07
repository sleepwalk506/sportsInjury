package com.huali.sportsinjuryandrecovery.mapper;

import com.huali.sportsinjuryandrecovery.pojo.Injury;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InjuryInquiryMapper {
    List<Injury> injuryInquiryList(String injuryType);
}
