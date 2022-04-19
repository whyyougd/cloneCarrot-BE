package com.hanghea.clonecarrotbe.repository;

import com.hanghea.clonecarrotbe.domain.Love;
import com.hanghea.clonecarrotbe.domain.Main;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoveRepository extends JpaRepository<Love, Long> {

    // post 당 love
    Optional<Love> findByMainAndLoveUsername(Main main, String username);
    // 나중에 main이랑 post 리팩토링하고 나서 사용할 jpa문
//    Optional<Love> findByPostAndLoveUsername(Main main, String username);

    // love 취소
    void deleteByLoveId(Long loveId);

    // loveCnt 확인용
    List<Love> findAllByMain_PostId(Long postid);
    // 나중에 main이랑 post 리팩토링하고 나서 사용할 jpa문
//    List<Love> findAllByPost_PostId(Long postid);
}
