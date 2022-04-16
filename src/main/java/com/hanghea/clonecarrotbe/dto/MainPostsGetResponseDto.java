package com.hanghea.clonecarrotbe.dto;

import lombok.Getter;

@Getter
public class MainPostsGetResponseDto {
    private Long postid;
    private String username;
    private String title;
    private Long price;
//    private String image;
    private int loveCnt;
    private String createdAt;
    private String status;

//    public MainPostsGetResponseDto(Long postid, String username, String title, Long price, String image, int loveCnt, String createdAt, String status) {
//        this.postid = postid;
//        this.username = username;
//        this.title = title;
//        this.price = price;
//        this.image = image;
//        this.loveCnt = loveCnt;
//        this.createdAt = createdAt;
//        this.status = status;
//    }


    // api 테스트용
    // image 기능 완성되면 위의 생성자로 돌려야함
    public MainPostsGetResponseDto(Long postid, String username, String title, Long price, int loveCnt, String createdAt, String status) {
        this.postid = postid;
        this.username = username;
        this.title = title;
        this.price = price;
        this.loveCnt = loveCnt;
        this.createdAt = createdAt;
        this.status = status;
    }
}
