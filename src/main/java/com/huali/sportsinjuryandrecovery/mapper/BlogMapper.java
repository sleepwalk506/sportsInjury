package com.huali.sportsinjuryandrecovery.mapper;

import com.huali.sportsinjuryandrecovery.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface BlogMapper {
    List<Blog> blogList();

    List<Blog> searchBlogByAuthor(String userId);

    List<Blog> searchBlogByInjury(String injuryType);

    int addNewBlog(String blogId, String userId, String title, String content, Date blogDate,String injuryType);
}
