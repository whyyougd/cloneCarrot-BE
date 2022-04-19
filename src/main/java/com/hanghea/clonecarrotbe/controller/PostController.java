package com.hanghea.clonecarrotbe.controller;

import com.hanghea.clonecarrotbe.domain.User;
import com.hanghea.clonecarrotbe.dto.PostRequestDto;
import com.hanghea.clonecarrotbe.repository.PostRepository;
import com.hanghea.clonecarrotbe.security.UserDetailsImpl;
import com.hanghea.clonecarrotbe.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    // 글 작성
//    @PostMapping("/api/post")
//    public ResponseEntity<String> createPost(@RequestBody PostRequestDto postRequestDto,
//                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        postService.createPost(postRequestDto, userDetails.getUser());
//
//        return ResponseEntity.ok().body("게시글작성 완료!");
//    }
    @PostMapping("/api/post")
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        System.out.println("username: "+userDetails.getUsername());
        postService.createPost(postRequestDto, userDetails.getUser());

        return ResponseEntity.ok().body("게시글작성 완료!");
    }

    // 게시글 수정
    @PutMapping("/api/post/{postid}")
    public void updatePost(@PathVariable Long postid,
                           @RequestBody PostRequestDto postRequestDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postService.updatePost(postid, postRequestDto, userDetails.getUser());
    }

    // 게시글 삭제
    @DeleteMapping("/api/post/{postid}")
    public String deletePost(@PathVariable Long postid) {
        postRepository.deleteById(postid);
        return "삭제 완료!";
    }
}
