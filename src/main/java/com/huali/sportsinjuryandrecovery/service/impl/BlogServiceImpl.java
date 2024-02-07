package com.huali.sportsinjuryandrecovery.service.impl;

import com.huali.sportsinjuryandrecovery.mapper.BlogMapper;
import com.huali.sportsinjuryandrecovery.pojo.Blog;
import com.huali.sportsinjuryandrecovery.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<Blog> blogList() {
        return blogMapper.blogList();
    }

    @Override
    public List<Blog> searchBlogByAuthor(String userId) {
        return blogMapper.searchBlogByAuthor(userId);
    }

    @Override
    public List<Blog> searchBlogByInjury(String injuryType) {
        return blogMapper.searchBlogByInjury(injuryType);
    }

    @Override
    public int addNewBlog(String blogId, String userId, String title, String content, Date blogDate, String injuryType) {
        return blogMapper.addNewBlog(blogId,userId,title,content,blogDate,injuryType);
    }
}
