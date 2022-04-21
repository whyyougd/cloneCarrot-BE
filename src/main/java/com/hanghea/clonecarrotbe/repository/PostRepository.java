package com.hanghea.clonecarrotbe.repository;

import com.hanghea.clonecarrotbe.domain.Post;
import com.hanghea.clonecarrotbe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
}
