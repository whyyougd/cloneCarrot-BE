package com.hanghea.clonecarrotbe.repository;

import com.hanghea.clonecarrotbe.domain.Image;
import com.hanghea.clonecarrotbe.domain.Love;
import com.hanghea.clonecarrotbe.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByPost_PostId(Long postid);

    List<Image> findAllByPost_PostId(Long postid);

    @Transactional
    void deleteAllByPost_PostId(Long postid);
}
