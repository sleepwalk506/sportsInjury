package com.huali.sportsinjuryandrecovery.controller;

import com.huali.sportsinjuryandrecovery.pojo.Blog;
import com.huali.sportsinjuryandrecovery.pojo.Injury;
import com.huali.sportsinjuryandrecovery.pojo.Result;
import com.huali.sportsinjuryandrecovery.pojo.User;
import com.huali.sportsinjuryandrecovery.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author: lj
 * @desc
 * @create: 2024.02.03
 **/
@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping
    public Result blogList(){
        log.info("get all blogs");
        List<Blog> blogList = blogService.blogList();
        if(blogList != null){
            return Result.success(blogList);
        }
        else{
            return Result.error("get blogs failed");
        }
    }

    @PostMapping("/searchByAuthor")
    public Result searchBlogByAuthor(@RequestBody User user){
        String userId = user.getUserId();
//        System.out.println(userId);
        log.info("search blogs by author");
        List<Blog> blogList = blogService.searchBlogByAuthor(userId);
        if(blogList != null){
            return Result.success(blogList);
        }
        else{
            return Result.error("get blogs failed");
        }
    }

    @PostMapping("/searchByInjury")
    public Result searchBlogByInjury(@RequestBody Injury injury){
        String injuryType = injury.getInjuryType();
        log.info("search blogs by injury");
        List<Blog> blogList = blogService.searchBlogByInjury(injuryType);
        if(blogList != null){
            return Result.success(blogList);
        }
        else{
            return Result.error("get blogs failed");
        }
    }

    @PostMapping("/sendBlog")
    public Result sendBlog(@RequestBody Blog blog){
        log.info("add new blog");
        String blogId = blog.getBlogId();
        String userId = blog.getUserId();
        String title = blog.getTitle();
        String content = blog.getContent();
        Date blogDate = blog.getBlogDate();
        String injuryType = blog.getInjuryType();
        int i = blogService.addNewBlog(blogId,userId,title,content,blogDate,injuryType);
        if(i != 0){
            return Result.success(i);
        }
        else{
            return Result.error("add new blog failed");
        }
    }
}
