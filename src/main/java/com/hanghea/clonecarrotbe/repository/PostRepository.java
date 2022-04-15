package com.hanghea.clonecarrotbe.repository;

import com.hanghea.clonecarrotbe.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    
}
