package com.hanghea.clonecarrotbe.dto;

import com.hanghea.clonecarrotbe.domain.Post;
import com.hanghea.clonecarrotbe.domain.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class StatusResponseDto {
    private Long postid;
    private String status;


    public StatusResponseDto(Post post, Status status) {
        this.postid = post.getPostId();
        this.status = status.getStatus();
    }
}
