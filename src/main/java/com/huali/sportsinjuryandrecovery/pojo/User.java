package com.huali.sportsinjuryandrecovery.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;//primary key
    private String password;
    private int gender;
    private Double height;
    private Double weight;
    private int age;
    private String phoneNumber;
    private String Email;
    private Date registrationDate;
}
