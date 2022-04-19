package com.hanghea.clonecarrotbe.dto;

import com.hanghea.clonecarrotbe.domain.Category;
import com.hanghea.clonecarrotbe.domain.Image;
import com.hanghea.clonecarrotbe.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Builder
public class PostResponseDto {
    private Long postid;
    private String username;
    private String title;
    private String content;

    private Long price;
    private List<String> imageList;
    private String categoryName;
    private String status;

    public PostResponseDto(Post post) {
        this.postid = post.getPostId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.price = post.getPrice();
        this.content = post.getContent();
        this.categoryName = post.getCategory().getCategoryName();
    }

    public PostResponseDto(Post savedPost, List<String> imageList) {
        this.postid = savedPost.getPostId();
        this.username = savedPost.getUser().getUsername();
        this.title = savedPost.getTitle();
        this.price = savedPost.getPrice();
        this.content = savedPost.getContent();
        this.categoryName = savedPost.getCategory().getCategoryName();
        this.imageList = imageList;
        this.status = savedPost.getStatus().getStatus();
    }

    public PostResponseDto(Long postid, Post post, List<String> imageList) {
        this.postid = postid;
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.price = post.getPrice();
        this.content = post.getContent();
        this.categoryName = post.getCategory().getCategoryName();
        this.imageList = imageList;
    }
}
