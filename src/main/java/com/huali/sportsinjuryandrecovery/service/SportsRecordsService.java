package com.huali.sportsinjuryandrecovery.service;

import com.huali.sportsinjuryandrecovery.pojo.SportsRecord;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface SportsRecordsService {
    List<SportsRecord> sportsRecordsList(String userId);
    int editSportsRecordsInfo(String exerciseId, String exerciseType, Date exerciseDate, Time exerciseTime, Double kcal, Double avgSpeed, Double avgHeartRate);
    int addSportdRecordsInfo(String userId, String exerciseId, String exerciseType, Date exerciseDate, Time exerciseTime, Double kcal, Double avgSpeed, Double avgHeartRate);
}
