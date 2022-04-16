package com.hanghea.clonecarrotbe.dto;

import com.hanghea.clonecarrotbe.domain.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class StatusResponseDto {
    private Long postid;
    private Status status;


    public StatusResponseDto(Long postid, Status status) {
        this.postid = postid;
        this.status = status;
    }
}
