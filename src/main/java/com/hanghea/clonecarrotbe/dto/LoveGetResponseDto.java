package com.hanghea.clonecarrotbe.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
public class LoveGetResponseDto {
    private boolean isLove;
    private int loveCnt;
    private List<String> lovedUsers;

    public LoveGetResponseDto(boolean isLove, int loveCnt, List<String> lovedUsers) {
        this.isLove = isLove;
        this.loveCnt = loveCnt;
        this.lovedUsers = lovedUsers;
    }
}
