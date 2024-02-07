package com.huali.sportsinjuryandrecovery.mapper;

import com.huali.sportsinjuryandrecovery.pojo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    List<Message> getMessagesById(String userId);
    int sendMessages(String messageId, String senderId, String receiverId, String messageTime, String content);

}
