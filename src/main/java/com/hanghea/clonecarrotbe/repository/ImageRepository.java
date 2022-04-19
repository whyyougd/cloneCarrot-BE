package com.hanghea.clonecarrotbe.repository;

import com.hanghea.clonecarrotbe.domain.Image;
import com.hanghea.clonecarrotbe.domain.Love;
import com.hanghea.clonecarrotbe.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    // getPost에서 이미지 리스트 찾기
//    List<Image> findAllByMain_PostId(Long postid);
    List<Image> findAllByPost(Post post);

}
