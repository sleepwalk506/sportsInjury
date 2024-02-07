package com.huali.sportsinjuryandrecovery.service.impl;

import com.huali.sportsinjuryandrecovery.mapper.UserMapper;
import com.huali.sportsinjuryandrecovery.pojo.User;
import com.huali.sportsinjuryandrecovery.pojo.UserImage;
import com.huali.sportsinjuryandrecovery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public User getUserById(String userId, String password) {
        return userMapper.getUserById(userId,password);
    }

    @Override
    public int editUserInfo(String userId,String password,int gender,Double height,Double weight,String phoneNumber,String Email) {
        return userMapper.editUserInfo(userId,password,gender,height,weight,phoneNumber,Email);
    }

    @Override
    public User getUserOnlyById(String userId) {
        return userMapper.getUserOnlyById(userId);
    }

    @Override
    public int addNewUser(String userId, String password) {
        return userMapper.addNewUser(userId,password);
    }

    @Override
    public UserImage getUserImage(String userId) {
        return userMapper.getUserImage(userId);
    }

    @Override
    public int updateUserImage(String userId, String imageUrl) {
        return userMapper.updateUserImage(userId,imageUrl);
    }

    @Override
    public List<User> getFriends(String userId) {
        return userMapper.getFriends(userId);
    }
}
