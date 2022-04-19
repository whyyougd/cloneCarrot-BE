package com.hanghea.clonecarrotbe.controller;

import com.hanghea.clonecarrotbe.domain.Post;
import com.hanghea.clonecarrotbe.dto.MainPostsGetResponseDto;
import com.hanghea.clonecarrotbe.dto.PostGetResponseDto;
import com.hanghea.clonecarrotbe.dto.PostResponseDto;
import com.hanghea.clonecarrotbe.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MainController {
    private final MainService mainService;
    @GetMapping("/api/main")
    public List<MainPostsGetResponseDto> getMainPosts(){
        System.out.println("/api/main");
        return mainService.getMainPosts();
    }

    @GetMapping("/api/post/{postid}")
    public PostGetResponseDto getPost(@PathVariable Long postid){
        System.out.println("/api/post/{postid}");
        System.out.println("postid: "+postid);
        return mainService.getPost(postid);
    }
}
