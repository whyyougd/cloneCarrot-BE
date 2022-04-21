package com.hanghea.clonecarrotbe.service;

import com.hanghea.clonecarrotbe.domain.Love;
import com.hanghea.clonecarrotbe.domain.Post;
import com.hanghea.clonecarrotbe.dto.LoveGetResponseDto;
import com.hanghea.clonecarrotbe.repository.LoveRepository;
import com.hanghea.clonecarrotbe.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class LoveService {
    private final LoveRepository loveRepository;
    private final PostRepository PostRepository;

    @Transactional
    public LoveGetResponseDto getLove(Long postid, String username) {
        // postid 유효성 검사
        Post post = PostRepository.findById(postid)
                .orElseThrow( () -> new IllegalStateException("존재하지 않는 PostId 입니다."));

        // post에 uid에 해당하는 유저의 아이디가 있는지 찾기
        Optional<Love> found = loveRepository.findByPostAndLoveUsername(post, username);
        boolean isLove = lovePresent(post, username);

        if(isLove){
            loveRepository.deleteByLoveId(found.get().getLoveId());
            isLove = false;
        }else {
            Love love = new Love(post, username);
            loveRepository.save(love);
            isLove = true;
        }

        // like 조회
        int loveCnt = getLoveCnt(postid);

        return new LoveGetResponseDto(isLove,loveCnt);
    }
    // 좋아요 개수 찾기 메서드
    public int getLoveCnt(Long postid) {
        List<Love> loveList = loveRepository.findAllByPost_PostId(postid);
        return loveList.size();
    }
    // 해당 username이 좋아요 실행했는지 구분
    public boolean lovePresent(Post post, String username){
        Optional<Love> found = loveRepository.findByPostAndLoveUsername(post, username);
        return found.isPresent();
    }
}
