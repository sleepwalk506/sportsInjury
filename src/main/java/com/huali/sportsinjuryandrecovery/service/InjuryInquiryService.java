package com.huali.sportsinjuryandrecovery.service;

import com.huali.sportsinjuryandrecovery.pojo.Injury;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface InjuryInquiryService {

    List<Injury> injuryInquiryList(String partName);
}
