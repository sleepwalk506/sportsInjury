package com.huali.sportsinjuryandrecovery.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Injury {
    String injuryId;//primary key
    String injuryType;
    String injurySubType;
    String injuryDescription;//long text
}
