package com.huali.sportsinjuryandrecovery.controller;

import com.huali.sportsinjuryandrecovery.pojo.Result;
import com.huali.sportsinjuryandrecovery.pojo.SportsRecord;
import com.huali.sportsinjuryandrecovery.service.SportsRecordsService;
import com.huali.sportsinjuryandrecovery.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
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
@RequestMapping("/sportsRecords")
public class SportsRecordsController {

    @Autowired
    private SportsRecordsService sportsRecordsService;

    @GetMapping
    public Result sportsRecordsList(){
        log.info("search sports records' info by user");
        // 从Authorization中获取当前用户id
        Map<String, Object> claims = ThreadLocalUtil.get();
        String userId = (String) claims.get("userId");
        List<SportsRecord> sportsRecord = sportsRecordsService.sportsRecordsList(userId);
        return Result.success(sportsRecord);
    }

    @PutMapping("/update")
    public Result editSportsRecordsInfo(@RequestBody SportsRecord sportsRecord){
        log.info("update sports records");
        String exerciseId = sportsRecord.getExerciseId();
        String exerciseType = sportsRecord.getExerciseType();
        Date exerciseDate = sportsRecord.getExerciseDate();
        Time exerciseTime = sportsRecord.getExerciseTime();
        Double kcal = sportsRecord.getKcal();
        Double avgSpeed = sportsRecord.getAvgSpeed();
        Double avgHeartRate = sportsRecord.getAvgHeartRate();
//        System.out.println(exerciseId+exerciseType);
        int updatedSportsRecords = sportsRecordsService.editSportsRecordsInfo(exerciseId,exerciseType,exerciseDate,exerciseTime,kcal,avgSpeed,avgHeartRate);
        if(updatedSportsRecords != 0){
            return Result.success(updatedSportsRecords);
        }
        else{
            return Result.error("update failed");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Result> addSportsRecordsInfo(@RequestBody SportsRecord sportsRecord){
        log.info("add new sports records");
        // 从Authorization中获取当前用户id
        Map<String, Object> claims = ThreadLocalUtil.get();
        String userId = (String) claims.get("userId");

        String exerciseId = sportsRecord.getExerciseId();
        String exerciseType = sportsRecord.getExerciseType();
        Date exerciseDate = sportsRecord.getExerciseDate();
        Time exerciseTime = sportsRecord.getExerciseTime();
        Double kcal = sportsRecord.getKcal();
        Double avgSpeed = sportsRecord.getAvgSpeed();
        Double avgHeartRate = sportsRecord.getAvgHeartRate();

        int addSportsRecord = sportsRecordsService.addSportdRecordsInfo(userId,exerciseId,exerciseType,exerciseDate,exerciseTime,kcal,avgSpeed,avgHeartRate);
        if(addSportsRecord != 0){
            return ResponseEntity.ok(Result.success(addSportsRecord));
        }
        else{
            return ResponseEntity.ok(Result.error("add new sports record failed"));
        }
    }
}
