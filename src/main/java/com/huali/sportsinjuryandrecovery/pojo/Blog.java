package com.huali.sportsinjuryandrecovery.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    private String blogId;//primary key
    private String userId;//foreign key reference User
    private String title;
    private String content;
    private Date blogDate;
    private String injuryType;
}
