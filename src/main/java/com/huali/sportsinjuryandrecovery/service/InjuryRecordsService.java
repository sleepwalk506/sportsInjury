package com.huali.sportsinjuryandrecovery.service;

import com.huali.sportsinjuryandrecovery.pojo.InjuryRecords;

import java.util.Date;
import java.util.List;

public interface InjuryRecordsService {
    List<InjuryRecords> getAllInjuryRecordsByUser(String userId);
    int addNewInjuryRecord(String recordId, String userId, String injuryType, String injuryPosition, Date dateOfInjury, int recoveryStatus,String injuryDescription);
    int deleteInjuryRecord(String recordId);
    int updateInjuryRecord(String recordId, String userId, String injuryType, String injuryPosition, Date dateOfInjury, int recoveryStatus,String injuryDescription);
    List<InjuryRecords> getInjuryRecordsByInjuryType(String userId,String injuryType);
    List<InjuryRecords> getInjuryRecordsByInjuryPosition(String userId,String injuryPosition);
}
