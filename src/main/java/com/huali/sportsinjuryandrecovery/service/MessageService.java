package com.huali.sportsinjuryandrecovery.service;

import com.huali.sportsinjuryandrecovery.pojo.Message;

import java.util.List;
public interface MessageService {
    List<Message> getMessagesById(String userId);

    int sendMessages(String messageId, String senderId, String receiverId, String messageTime, String content);
}
