package com.huali.sportsinjuryandrecovery.controller;

import com.huali.sportsinjuryandrecovery.pojo.InjuryRecords;
import com.huali.sportsinjuryandrecovery.pojo.Result;
import com.huali.sportsinjuryandrecovery.service.InjuryRecordsService;
import com.huali.sportsinjuryandrecovery.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: lj
 * @desc
 * @create: 2024.02.03
 **/
@Slf4j
@RestController
@RequestMapping("/injuryRecords")
public class InjuryRecordsController {

    @Autowired
    private InjuryRecordsService injuryRecordsService;

    @GetMapping
    public Result getAllInjuryRecordsByUser(){
        // 从Authorization中获取当前用户id
        Map<String, Object> claims = ThreadLocalUtil.get();
        String userId = (String) claims.get("userId");

        List<InjuryRecords> injuryRecordsList = injuryRecordsService.getAllInjuryRecordsByUser(userId);
        if(injuryRecordsList == null){
            return Result.error("get injury records failed");
        }
        else {
            return Result.success(injuryRecordsList);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Result> updateInjuryRecord(@RequestBody InjuryRecords injuryRecords){
        // 从Authorization中获取当前用户id
        Map<String, Object> claims = ThreadLocalUtil.get();
        String userId = (String) claims.get("userId");

        String recordId = injuryRecords.getRecordId();
        String injuryType = injuryRecords.getInjuryType();
        String injuryPosition = injuryRecords.getInjuryPosition();
        Date dateOfInjury = injuryRecords.getDateOfInjury();
        int recoveryStatus = injuryRecords.getRecoveryStatus();
        String injuryDescription = injuryRecords.getInjuryDescription();
        int updateSuccess = injuryRecordsService.updateInjuryRecord(recordId,userId,injuryType,injuryPosition,dateOfInjury,recoveryStatus,injuryDescription);
        if(updateSuccess == 0){
            return ResponseEntity.ok(Result.error("update injury records failed"));
        }
        else{
            return ResponseEntity.ok(Result.success(updateSuccess));
        }
    }

    @GetMapping("/searchByPosition")
    public Result getInjuryRecordsByInjuryPosition(String injuryPosition){
        // 从Authorization中获取当前用户id
        Map<String, Object> claims = ThreadLocalUtil.get();
        String userId = (String) claims.get("userId");

        List<InjuryRecords> injuryRecordsList = injuryRecordsService.getInjuryRecordsByInjuryPosition(userId,injuryPosition);
        if(injuryRecordsList == null){
            return Result.error("cant find injury record with this type");
        }
        else{
            return Result.success(injuryRecordsList);
        }
    }

    @GetMapping("/searchByType")
    public Result getInjuryRecordsByInjuryType(String injuryType){
        // 从Authorization中获取当前用户id
        Map<String, Object> claims = ThreadLocalUtil.get();
        String userId = (String) claims.get("userId");

        List<InjuryRecords> injuryRecordsList = injuryRecordsService.getInjuryRecordsByInjuryType(userId, injuryType);
        if(injuryRecordsList == null){
            return Result.error("cant find injury record with this type");
        }
        else{
            return Result.success(injuryRecordsList);
        }
    }

    @PostMapping("/add")
    public Result addNewInjuryRecord(@RequestBody InjuryRecords injuryRecords){
        String recordId = injuryRecords.getRecordId();
        // 从Authorization中获取当前用户id
        Map<String, Object> claims = ThreadLocalUtil.get();
        String userId = (String) claims.get("userId");

        String injuryType = injuryRecords.getInjuryType();
        String injuryPosition = injuryRecords.getInjuryPosition();
        Date dateOfInjury = injuryRecords.getDateOfInjury();
        int recoveryStatus = injuryRecords.getRecoveryStatus();
        String injuryDescription = injuryRecords.getInjuryDescription();
        int addSuccess = injuryRecordsService.addNewInjuryRecord(recordId,userId,injuryType,injuryPosition,dateOfInjury,recoveryStatus,injuryDescription);
        if(addSuccess == 0){
            return Result.error("add injury record failed");
        }
        else{
            return Result.success(addSuccess);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Result> deleteInjuryRecord(@RequestParam("recordId") String recordId){
        int deleteSuccess = injuryRecordsService.deleteInjuryRecord(recordId);
        if(deleteSuccess == 0){
            return ResponseEntity.ok(Result.error("delete injury record failed"));
        }
        else{
            return ResponseEntity.ok(Result.success(deleteSuccess));
        }
    }
}
