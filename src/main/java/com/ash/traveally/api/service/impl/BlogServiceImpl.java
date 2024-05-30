package com.ash.traveally.api.service.impl;

import com.ash.traveally.api.dto.PageResponse;
import com.ash.traveally.api.dto.BlogDto;
import com.ash.traveally.api.exceptions.BlogNotFoundException;
import com.ash.traveally.api.models.Blog;
import com.ash.traveally.api.repository.BlogRepository;
import com.ash.traveally.api.repository.UserRepository;
import com.ash.traveally.api.security.CustomUserDetailsService;
import com.ash.traveally.api.service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BlogDto createBlog(BlogDto blogDto) {
        Blog blog = mapToEntity(blogDto);
        Blog newBlog = blogRepository.save(blog);
        return mapToDto(newBlog);
    }

    @Override
    public List<BlogDto> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        return blogs.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public PageResponse<BlogDto> getAllBlogs(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Blog> page = blogRepository.findAll(pageable);
        List<Blog> blogs = page.getContent();
        List<BlogDto> content = blogs.stream().map(this::mapToDto).collect(Collectors.toList());
        PageResponse<BlogDto> blogResponse = new PageResponse<>();
        blogResponse.setContent(content);
        blogResponse.setPageNo(page.getNumber());
        blogResponse.setPageSize(page.getSize());
        blogResponse.setTotalPages(page.getTotalPages());
        blogResponse.setTotalElements(page.getTotalElements());
        blogResponse.setLast(page.isLast());
        return blogResponse;
    }

    @Override
    public BlogDto getBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() ->
                new BlogNotFoundException("Blog cannot be found")
        );
        return mapToDto(blog);
    }

    @Override
    public BlogDto updateBlog(BlogDto blogDto) {
        if (!blogRepository.existsById(blogDto.getId())) {
            throw new BlogNotFoundException("Blog cannot be updated");
        }
        if (!blogDto.getAuthorId().equals(userRepository.findByEmail(CustomUserDetailsService.getUserEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found")).getId())) {
            throw new BlogNotFoundException("Blog cannot be updated");
        }
        Blog updatedBlog = mapToEntity(blogDto);
        Blog newBlog = blogRepository.save(updatedBlog);
        return mapToDto(newBlog);
    }

    @Override
    public void deleteBlog(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new BlogNotFoundException("Blog cannot be deleted");
        }
        if (!blogRepository.findById(id).get().getAuthor().getId().equals(userRepository.findByEmail(CustomUserDetailsService.getUserEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found")).getId())) {
            throw new BlogNotFoundException("Blog cannot be deleted");
        }
        blogRepository.deleteById(id);
    }

    public Long getUserId() {
        return userRepository.findIdFromEmail(CustomUserDetailsService.getUserEmail()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
    }

    private BlogDto mapToDto(Blog blog) {
        BlogDto blogDto = new BlogDto();
        blogDto.setId(blog.getId());
        blogDto.setCity(blog.getCity());
        blogDto.setCountry(blog.getCountry());
        blogDto.setTitle(blog.getTitle());
        blogDto.setIntroduction(blog.getIntroduction());
        blogDto.setDescription(blog.getDescription());
        blogDto.setPlacePhoto(blog.getPlacePhoto());
        blogDto.setTime(blog.getTime());
        blogDto.setAuthorId(blog.getAuthor().getId());
        blogDto.setIsFavourite(blog.getLikedIDs().contains(getUserId()));
        blogDto.setLikes(blog.getLikedIDs().size());
        return blogDto;
    }

    private Blog mapToEntity(BlogDto blogDto)  {
        Blog blog = new Blog();
        if (blogDto.getId() != null) blog.setId(blogDto.getId());
        blog.setCity(blogDto.getCity());
        blog.setCountry(blogDto.getCountry());
        blog.setTitle(blogDto.getTitle());
        blog.setIntroduction(blogDto.getIntroduction());
        blog.setDescription(blogDto.getDescription());
        blog.setPlacePhoto(blogDto.getPlacePhoto());
        blog.setTime(blogDto.getTime());
        blog.setAuthor(userRepository.findByEmail(CustomUserDetailsService.getUserEmail()).orElseThrow(() -> new UsernameNotFoundException("Host doesn't exist")));
        if (blogDto.getId() != null) {
            Blog temp = blogRepository.findById(blogDto.getId()).get();
            Set<Long> likedIds = temp.getLikedIDs();
            if (blogDto.getIsFavourite()) likedIds.add(getUserId());
            else likedIds.remove(getUserId());
            blog.setLikedIDs(likedIds);
        }
        return blog;
    }
}
