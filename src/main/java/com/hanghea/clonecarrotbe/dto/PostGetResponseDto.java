package com.hanghea.clonecarrotbe.dto;

import com.hanghea.clonecarrotbe.domain.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class PostGetResponseDto {
    private Long postid;
    private String username;
    private String title;
    private String content;
    private Long price;
    private List<String> imageList; // List<Image>로 바꿔야하는지 확인해야함
    private String categoryName;
    private int loveCnt;
    private String createdAt;
    private String status;


    // 개발용 image 제외한 생성자
    public PostGetResponseDto(Post savedPost,List<String> imageUrls, int loveCnt, String createdAt) {
        this.postid = savedPost.getPostId();
        this.username = savedPost.getUser().getUsername();
        this.title = savedPost.getTitle();
        this.content = savedPost.getContent();
        this.price = savedPost.getPrice();
        this.categoryName = savedPost.getCategory().getCategoryName();
        this.imageList = imageUrls;
        this.loveCnt = loveCnt;
        this.createdAt = createdAt;
        this.status = savedPost.getStatus().getStatus();
    }
}
