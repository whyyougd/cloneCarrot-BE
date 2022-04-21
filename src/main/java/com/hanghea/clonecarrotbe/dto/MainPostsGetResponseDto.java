package com.hanghea.clonecarrotbe.dto;

import com.hanghea.clonecarrotbe.domain.Post;
import lombok.Getter;

@Getter
public class MainPostsGetResponseDto {
    private Long postid;
    private String username;
    private String title;
    private Long price;
    private String image;
    private int loveCnt;
    private String createdAt;
    private String category;
    private String status;

    public MainPostsGetResponseDto(Post savedPost, String image, int loveCnt, String createdAt) {
        this.postid = savedPost.getPostId();
        this.username = savedPost.getUser().getUsername();
        this.title = savedPost.getTitle();
        this.price = savedPost.getPrice();
        this.image = image;
        this.loveCnt = loveCnt;
        this.createdAt = createdAt;
        this.category = savedPost.getCategory().getCategoryName();
        this.status = savedPost.getStatus().getStatus();
    }
}
