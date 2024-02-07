package com.huali.sportsinjuryandrecovery.mapper;

import com.huali.sportsinjuryandrecovery.pojo.SportsRecord;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Time;
import java.util.Date;
import java.util.List;


@Mapper
public interface SportsRecordsMapper {

    List<SportsRecord> sportsRecordList(String userId);

    int editSportsRecordsInfo(String exerciseId, String exerciseType, Date exerciseDate, Time exerciseTime, Double kcal, Double avgSpeed, Double avgHeartRate);

    int addSportsRecordsInfo(String userId, String exerciseId, String exerciseType, Date exerciseDate, Time exerciseTime, Double kcal, Double avgSpeed, Double avgHeartRate);
}
