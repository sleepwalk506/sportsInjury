package com.huali.sportsinjuryandrecovery.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InjuryRecords {
    String recordId;
    String userId;
    String injuryType;
    String injuryPosition;
    Date dateOfInjury;
    int recoveryStatus;
    String injuryDescription;
}