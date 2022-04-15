package com.hanghea.clonecarrotbe.service;

import com.hanghea.clonecarrotbe.domain.Post;
import com.hanghea.clonecarrotbe.domain.Status;
import com.hanghea.clonecarrotbe.dto.StatusRequestDto;
import com.hanghea.clonecarrotbe.dto.StatusResponseDto;
import com.hanghea.clonecarrotbe.repository.PostRepository;
import com.hanghea.clonecarrotbe.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StatusService {
    private final StatusRepository statusRepository;
    private final PostRepository postRepository;

    public StatusResponseDto updateStatus(Long postid, StatusRequestDto statusRequestDto) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
        );

        // 바꾸고자 하는 상태명을 찾는 중
        Status newStatus = statusRepository.findByStatus(statusRequestDto.getStatus());

        System.out.println("newStatus: "+newStatus.getStatus());

        post.setStatus(newStatus);

        return new StatusResponseDto(postid,newStatus);
    }
}
