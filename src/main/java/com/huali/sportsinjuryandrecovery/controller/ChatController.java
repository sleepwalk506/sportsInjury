package com.huali.sportsinjuryandrecovery.controller;

import com.huali.sportsinjuryandrecovery.pojo.Message;
import com.huali.sportsinjuryandrecovery.pojo.Result;
import com.huali.sportsinjuryandrecovery.pojo.User;
import com.huali.sportsinjuryandrecovery.service.MessageService;
import com.huali.sportsinjuryandrecovery.service.UserService;
import com.huali.sportsinjuryandrecovery.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Result messageList(){
        log.info("get all messages");
        // 从Authorization中获取当前用户id
        Map<String, Object> claims = ThreadLocalUtil.get();
        String userId = (String) claims.get("userId");

        List<Message> messageList = messageService.getMessagesById(userId);
        if(messageList != null){
            return Result.success(messageList);
        }
        else{
            return Result.error("get messages failed");
        }
    }

    @PostMapping("/send")
    public Result sendMessages(@RequestBody Message message){
        log.info("send your messages");
        String messageId = message.getMessageId();
        String senderId = message.getSenderId();
        String receiverId = message.getReceiverId();
        String content = message.getContent();
        String messageTime = message.getMessageTime();
//        System.out.println(messageId+content);
        int updatedMessages = messageService.sendMessages(messageId, senderId, receiverId, messageTime, content);
        if(updatedMessages != 0){
            return Result.success(updatedMessages);
        }
        else{
            return Result.error("update msg failed");
        }
    }

    @GetMapping("/friends")
    public Result getFriends(){
        log.info("get friends");
        // 从Authorization中获取当前用户id
        Map<String, Object> claims = ThreadLocalUtil.get();
        String userId = (String) claims.get("userId");

        List<User> friends = userService.getFriends(userId);
        return Result.success(friends);
    }
}
