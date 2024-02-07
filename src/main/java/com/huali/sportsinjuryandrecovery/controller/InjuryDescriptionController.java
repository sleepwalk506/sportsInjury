package com.huali.sportsinjuryandrecovery.controller;

import com.huali.sportsinjuryandrecovery.pojo.InjuryDescription;
import com.huali.sportsinjuryandrecovery.pojo.Result;
import com.huali.sportsinjuryandrecovery.pojo.User;
import com.huali.sportsinjuryandrecovery.service.InjuryDescriptionService;
import com.huali.sportsinjuryandrecovery.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author: lj
 * @desc
 * @create: 2024.02.03
 **/
@RestController
@Slf4j
@RequestMapping("/description")
public class InjuryDescriptionController {

    @Autowired
    private InjuryDescriptionService injuryDescriptionService;

    @GetMapping
    public Result getInjuryDescriptionList(){
        Map<String, Object> claims = ThreadLocalUtil.get();
        String userId = (String) claims.get("userId");
        List<InjuryDescription> injuryDescriptionList = injuryDescriptionService.getInjuryDescriptionList(userId);
        if(injuryDescriptionList == null){
            return Result.error("get injury description list failed");
        }
        else{
            return Result.success(injuryDescriptionList);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Result> addInjuryDescription(@RequestBody InjuryDescription injuryDescription){
        // 从Authorization中获取当前用户id
        Map<String, Object> claims = ThreadLocalUtil.get();
        String userId = (String) claims.get("userId");

        injuryDescription.setUserId(userId);
        String title = injuryDescription.getTitle();
        String descriptionId = injuryDescription.getDescriptionId();
        String detailedDescription = injuryDescription.getDetailedDescription();
        int addSuccess = injuryDescriptionService.addInjuryDescription(userId,descriptionId,title,detailedDescription);
        if(addSuccess == 0){
            return ResponseEntity.ok(Result.error("get injury description list failed"));
        }
        else{
            return ResponseEntity.ok(Result.success(addSuccess));
        }
    }
}
