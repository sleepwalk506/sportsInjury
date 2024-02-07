package com.huali.sportsinjuryandrecovery.controller;

import com.huali.sportsinjuryandrecovery.pojo.Injury;
import com.huali.sportsinjuryandrecovery.pojo.Result;
import com.huali.sportsinjuryandrecovery.service.InjuryInquiryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class InquiryController {
    @Autowired
    private InjuryInquiryService injuryInquiryService;

    @PostMapping("/injuryInquiry")
    public Result InjuryInquiry(@RequestBody Injury injury) {
        log.info("injury inquiry");
        String injuryType = injury.getInjuryType();
        List<Injury> injuryAns = injuryInquiryService.injuryInquiryList(injuryType);

        if(injuryAns!=null){
            return Result.success(injuryAns);
        }
        else{
            return Result.error("get injury failed");
        }
    }
}
