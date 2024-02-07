package com.huali.sportsinjuryandrecovery.service.impl;

import com.huali.sportsinjuryandrecovery.mapper.SportsRecordsMapper;
import com.huali.sportsinjuryandrecovery.pojo.SportsRecord;
import com.huali.sportsinjuryandrecovery.service.SportsRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;
@Service
public class SportsRecordsServiceImpl implements SportsRecordsService {
    @Autowired
    private SportsRecordsMapper sportsRecordsMapper;

    @Override
    public List<SportsRecord> sportsRecordsList(String userId) {
        return sportsRecordsMapper.sportsRecordList(userId);
    }

    @Override
    public int editSportsRecordsInfo(String exerciseId, String exerciseType, Date exerciseDate, Time exerciseTime, Double kcal, Double avgSpeed, Double avgHeartRate) {
        return sportsRecordsMapper.editSportsRecordsInfo(exerciseId,exerciseType,exerciseDate,exerciseTime,kcal,avgSpeed,avgHeartRate);
    }

    @Override
    public int addSportdRecordsInfo(String userId, String exerciseId, String exerciseType, Date exerciseDate, Time exerciseTime, Double kcal, Double avgSpeed, Double avgHeartRate) {
        return sportsRecordsMapper.addSportsRecordsInfo(userId,exerciseId,exerciseType,exerciseDate,exerciseTime,kcal,avgSpeed,avgHeartRate);
    }
}
