package com.huali.sportsinjuryandrecovery.service.impl;

import com.huali.sportsinjuryandrecovery.mapper.InjuryRecordsMapper;
import com.huali.sportsinjuryandrecovery.pojo.InjuryRecords;
import com.huali.sportsinjuryandrecovery.service.InjuryRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InjuryRecordsServiceImpl implements InjuryRecordsService {
    @Autowired
    private InjuryRecordsMapper injuryRecordsMapper;
    @Override
    public List<InjuryRecords> getAllInjuryRecordsByUser(String userId) {
        return injuryRecordsMapper.getAllInjuryRecordsByUser(userId);
    }

    @Override
    public int addNewInjuryRecord(String recordId, String userId, String injuryType, String injuryPosition, Date dateOfInjury, int recoveryStatus,String injuryDescription) {
        return injuryRecordsMapper.addNewInjuryRecord(recordId,userId,injuryType,injuryPosition,dateOfInjury,recoveryStatus,injuryDescription);
    }

    @Override
    public int deleteInjuryRecord(String recordId) {
        return injuryRecordsMapper.deleteInjuryRecord(recordId);
    }

    @Override
    public int updateInjuryRecord(String recordId, String userId, String injuryType, String injuryPosition, Date dateOfInjury, int recoveryStatus,String injuryDescription) {
        return injuryRecordsMapper.updateInjuryRecord(recordId,userId,injuryType,injuryPosition,dateOfInjury,recoveryStatus,injuryDescription);
    }

    @Override
    public List<InjuryRecords> getInjuryRecordsByInjuryType(String userId, String injuryType) {
        return injuryRecordsMapper.getInjuryRecordsByInjuryType(userId,injuryType);
    }

    @Override
    public List<InjuryRecords> getInjuryRecordsByInjuryPosition(String userId, String injuryPosition) {
        return injuryRecordsMapper.getInjuryRecordsByInjuryPosition(userId,injuryPosition);
    }
}
