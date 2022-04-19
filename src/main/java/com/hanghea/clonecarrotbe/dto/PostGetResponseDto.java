package com.hanghea.clonecarrotbe.dto;

import com.hanghea.clonecarrotbe.domain.Image;
import com.hanghea.clonecarrotbe.domain.Main;
import lombok.Getter;

import java.util.List;

@Getter
public class PostGetResponseDto {
    private Long postid;
    private String username;
    private String title;
    private String content;
    private Long price;
//    private List<String> imageList; // List<Image>로 바꿔야하는지 확인해야함
    private String categoryName;
    private int loveCnt;
    private String createdAt;
    private String status;

    public PostGetResponseDto(Main savedMain, List<String> imageUrls, int loveCnt, String createdAt) {
        this.postid = savedMain.getPostId();
        this.username = savedMain.getUser().getUsername();
        this.title = savedMain.getTitle();
        this.content = savedMain.getContent();
        this.price = savedMain.getPrice();
//        this.imageList = imageUrls;
        this.categoryName = savedMain.getCategory().getCategoryName();
        this.loveCnt = loveCnt;
        this.createdAt = createdAt;
        this.status = savedMain.getStatus().getStatus();

    }


    // 개발용 image 제외한 생성자
    public PostGetResponseDto(Main savedMain, int loveCnt, String createdAt) {
        this.postid = savedMain.getPostId();
        this.username = savedMain.getUser().getUsername();
        this.title = savedMain.getTitle();
        this.content = savedMain.getContent();
        this.price = savedMain.getPrice();
        this.categoryName = savedMain.getCategory().getCategoryName();
        this.loveCnt = loveCnt;
        this.createdAt = createdAt;
        this.status = savedMain.getStatus().getStatus();
    }
}
