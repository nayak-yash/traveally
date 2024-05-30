package com.ash.traveally.api.controller;

import com.ash.traveally.api.dto.BlogDto;
import com.ash.traveally.api.dto.PageResponse;
import com.ash.traveally.api.service.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("blogs")
    public ResponseEntity<List<BlogDto>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    @GetMapping("blog")
    public ResponseEntity<PageResponse<BlogDto>> getBlogs(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return ResponseEntity.ok(blogService.getAllBlogs(pageNo, pageSize));
    }

    @GetMapping("blog/{blogId}")
    public ResponseEntity<BlogDto> getBlog(@PathVariable Long blogId) {
        return ResponseEntity.ok(blogService.getBlog(blogId));
    }

    @PostMapping("blog/create")
    public ResponseEntity<BlogDto> createBlog(@RequestBody BlogDto blogDto) {
        return ResponseEntity.ok(blogService.createBlog(blogDto));
    }

    @PutMapping("blog/update")
    public ResponseEntity<BlogDto> updateBlog(@RequestBody BlogDto blogDto) {
        return ResponseEntity.ok(blogService.updateBlog(blogDto));
    }

    @DeleteMapping("blog/delete/{blogId}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long blogId) {
        blogService.deleteBlog(blogId);
        return ResponseEntity.ok("Deleted Successfully");
    }
}
