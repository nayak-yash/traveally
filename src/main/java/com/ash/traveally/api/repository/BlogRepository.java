package com.ash.traveally.api.repository;

import com.ash.traveally.api.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
