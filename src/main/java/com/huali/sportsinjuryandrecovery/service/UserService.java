package com.huali.sportsinjuryandrecovery.service;

import com.huali.sportsinjuryandrecovery.pojo.User;
import com.huali.sportsinjuryandrecovery.pojo.UserImage;

import java.util.List;

public interface UserService {

    List<User> list();
    User getUserById(String userId,String password);
    int editUserInfo(String userId,String password,int gender,Double height,Double weight,String phoneNumber,String Email);
    User getUserOnlyById(String userId);
    int addNewUser(String userId,String password);
    UserImage getUserImage(String userId);
    int updateUserImage(String userId,String imageUrl);
    List<User> getFriends(String userId);
}
