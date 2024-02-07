package com.huali.sportsinjuryandrecovery.mapper;

import com.huali.sportsinjuryandrecovery.pojo.InjuryRecords;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface InjuryRecordsMapper {
    List<InjuryRecords> getAllInjuryRecordsByUser(String userId);
    int addNewInjuryRecord(String recordId, String userId, String injuryType, String injuryPosition, Date dateOfInjury, int recoveryStatus,String injuryDescription);
    int deleteInjuryRecord(String recordId);
    int updateInjuryRecord(String recordId, String userId, String injuryType, String injuryPosition, Date dateOfInjury, int recoveryStatus,String injuryDescription);
    List<InjuryRecords> getInjuryRecordsByInjuryType(String userId,String injuryType);
    List<InjuryRecords> getInjuryRecordsByInjuryPosition(String userId,String injuryPosition);
}
