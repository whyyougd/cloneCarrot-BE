package com.hanghea.clonecarrotbe.controller;

import com.hanghea.clonecarrotbe.dto.MainPostsGetResponseDto;
import com.hanghea.clonecarrotbe.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;
    @GetMapping("/api/main")
    public List<MainPostsGetResponseDto> getMainPosts(){
        System.out.println("/api/main");
        return postService.getMainPosts();
    }
}
