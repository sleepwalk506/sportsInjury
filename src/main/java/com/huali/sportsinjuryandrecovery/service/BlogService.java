package com.huali.sportsinjuryandrecovery.service;

import com.huali.sportsinjuryandrecovery.pojo.Blog;

import java.util.Date;
import java.util.List;

public interface BlogService {
    List<Blog> blogList();
    List<Blog> searchBlogByAuthor(String userId);

    List<Blog> searchBlogByInjury(String injuryType);

    int addNewBlog(String blogId, String userId, String title, String content, Date blogDate, String injuryType);
}
