package com.huali.sportsinjuryandrecovery.controller;

import com.huali.sportsinjuryandrecovery.pojo.Result;
import com.huali.sportsinjuryandrecovery.pojo.User;
import com.huali.sportsinjuryandrecovery.pojo.UserImage;
import com.huali.sportsinjuryandrecovery.service.UserService;
import com.huali.sportsinjuryandrecovery.utils.JwtUtil;
import com.huali.sportsinjuryandrecovery.utils.Md5Util;
import com.huali.sportsinjuryandrecovery.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: lj
 * @desc
 * @create: 2024.02.03
 **/
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/all")
    Result getAllUser(){
        log.info("search all users‘ info");
        //调用service查询用户数据
        List<User> usersList = userService.list();
        return Result.success(usersList);
    }

    // 注册用户
    @PostMapping("/register")
    public Result register(@RequestBody Map<String, Object> params) {
        String userId = (String) params.get("userId");
        String password = (String) params.get("password");
        // 查询用户
        log.info("search user by userId");
        User u = userService.getUserById(userId, password);
        if (u == null) {
            // 没有占用
            // 注册
            userService.addNewUser(userId, password);
            return Result.success();
        } else return Result.error("用户名已被占用");
    }

    // 用户登录
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, Object> params){
        String userId = (String) params.get("userId");
        String password = (String) params.get("password");
        log.info("search user by userId");
        // 根据用户名查询用户
        User user = userService.getUserById(userId, password);
        // 判断该用户是否存在
        if(user == null){
            // 用户不存在
            return Result.error("用户名错误");
        }
        if(password.equals(user.getPassword())){
            // 登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getUserId());

            String token = JwtUtil.genToken(claims);
            // 把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);
            return Result.success(token);
        }
        else return Result.error("密码错误");
    }

    // 修改用户信息
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        log.info("search user by userId");
//        String userId,String password,int gender,Double height,Double weight,String phoneNumber,String Email
        String userId = user.getUserId();
        String password = user.getPassword();
        int gender = user.getGender();
        Double height = user.getHeight();
        Double weight = user.getWeight();
        String phoneNumber = user.getPhoneNumber();
        String Email = user.getEmail();
        userService.editUserInfo(userId,password,gender,height,weight,phoneNumber,Email);
        return Result.success("更新成功");
    }

    @GetMapping("/getUserImage")
    public ResponseEntity<Result> getUserImage(){
        log.info("get user image");
        Map<String, Object> claims = ThreadLocalUtil.get();
        String userId = (String) claims.get("userId");
        UserImage u = userService.getUserImage(userId);
        if(u != null){
            return ResponseEntity.ok(Result.success(u));
        }
        else{
            return ResponseEntity.ok(Result.error("get user image failed"));
        }
    }

    @PutMapping("/updateUserImage")
    public ResponseEntity<Result> updateUserImage(@RequestBody UserImage userImage){
        log.info("update user image");
        String userId = userImage.getUserId();
        String imageUrl = userImage.getImageUrl();
        int i = userService.updateUserImage(userId,imageUrl);
        if(i != 0){
            UserImage userImage1 = userService.getUserImage(userId);
            return ResponseEntity.ok(Result.success(userImage1));
        }
        else{
            return ResponseEntity.ok(Result.error("update user image failed"));
        }
    }

    @GetMapping("/getById")
    public Result getById(@RequestParam("userId") String userId){
        log.info("search user by userId");
        User user = userService.getUserOnlyById(userId);
        return Result.success(user);
    }
}
