package com.huali.sportsinjuryandrecovery.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SportsRecord {
    private String exerciseId;
    private String userId;
    private String exerciseType;
    private Date exerciseDate;
    private Time exerciseTime;
    private Double kcal;
    private Double maxSpeed;
    private Double minSpeed;
    private Double avgSpeed;
    private Double avgHeartRate;
}
