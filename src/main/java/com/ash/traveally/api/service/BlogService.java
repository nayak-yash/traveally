package com.ash.traveally.api.service;


import com.ash.traveally.api.dto.BlogDto;
import com.ash.traveally.api.dto.PageResponse;

import java.util.List;

public interface BlogService {

    BlogDto createBlog(BlogDto BlogDto);

    List<BlogDto> getAllBlogs();

    PageResponse<BlogDto> getAllBlogs(int pageNo, int pageSize);

    BlogDto getBlog(Long id);

    BlogDto updateBlog(BlogDto BlogDto);

    void deleteBlog(Long id);
}