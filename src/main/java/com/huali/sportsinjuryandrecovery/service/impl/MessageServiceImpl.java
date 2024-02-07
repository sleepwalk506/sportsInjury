package com.huali.sportsinjuryandrecovery.service.impl;

import com.huali.sportsinjuryandrecovery.mapper.MessageMapper;
import com.huali.sportsinjuryandrecovery.pojo.Message;
import com.huali.sportsinjuryandrecovery.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Message> getMessagesById(String userId){
        return messageMapper.getMessagesById(userId);
    }

    @Override
    public int sendMessages(String messageId, String senderId, String receiverId, String messageTime, String content){
        return messageMapper.sendMessages(messageId, senderId, receiverId, messageTime, content);
    }


}
