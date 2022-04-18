package com.hanghea.clonecarrotbe.controller;

import com.hanghea.clonecarrotbe.domain.User;
import com.hanghea.clonecarrotbe.dto.PostRequestDto;
import com.hanghea.clonecarrotbe.repository.UserRepository;
import com.hanghea.clonecarrotbe.security.UserDetailsImpl;
import com.hanghea.clonecarrotbe.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserRepository userRepository;

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
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto postRequestDto, User user) {
        // test code
        user = new User("testcode","1234");


        postService.createPost(postRequestDto, user);

        return ResponseEntity.ok().body("게시글작성 완료!");
    }
}
