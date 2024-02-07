package com.huali.sportsinjuryandrecovery.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String messageId; //primary key
    private String senderId;  //foreign key reference users
    private String receiverId;  //foreign key reference users
    private String messageTime;
    private String content;
}
