package com.hanghea.clonecarrotbe.controller;

import com.hanghea.clonecarrotbe.domain.User;
import com.hanghea.clonecarrotbe.dto.MainPostsGetResponseDto;
import com.hanghea.clonecarrotbe.dto.PostGetResponseDto;
import com.hanghea.clonecarrotbe.dto.PostRequestDto;
import com.hanghea.clonecarrotbe.dto.PostResponseDto;
import com.hanghea.clonecarrotbe.repository.PostRepository;
import com.hanghea.clonecarrotbe.repository.UserRepository;
import com.hanghea.clonecarrotbe.security.UserDetailsImpl;
import com.hanghea.clonecarrotbe.service.PostService;
import com.hanghea.clonecarrotbe.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;
    private final S3Service s3Service;
    private final UserRepository userRepository;

    // 게시글 생성
    @PostMapping(value = "/api/post", headers = ("content-type=multipart/*"))
    public PostResponseDto createPost(@RequestPart("com") PostRequestDto requestDto,
                                      @RequestPart("files") ArrayList<MultipartFile> files,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("/api/post");
        List<String> imgPath = s3Service.upload(files);
        requestDto.setImageList(imgPath);
        System.out.println("imagePath: "+ imgPath);
//        String username = requestDto.getUsername();
//        System.out.println("username: "+username);
//        User user = userRepository.findUserByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));
        return postService.createPost(requestDto, imgPath, userDetails.getUser());
    }

    //게시글 수정

    @PutMapping(value = "/api/post/{postid}", headers = ("content-type=multipart/*"))
    public PostResponseDto updatePost(
            @PathVariable Long postid,
            @RequestPart("com") PostRequestDto postRequestDto,
            @RequestPart("files") List<MultipartFile> files,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(postid, postRequestDto, files, userDetails.getUser());
    }


    // 게시글 삭제
    @DeleteMapping("/api/post/{postid}")
    public ResponseEntity<String> deletePost(@PathVariable Long postid,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("postid: " + postid);
        postService.deletePost(postid,userDetails.getUser());
        return ResponseEntity.ok().body("당근 게시글 삭제 완료");
    }


    // 메인페이지 전체 포스트 불러오기
    @GetMapping("/api/main")
    public List<MainPostsGetResponseDto> getMainPosts(){
        System.out.println("/api/main");
        return postService.getMainPosts();
    }

    // 상세보기
    @GetMapping("/api/post/{postid}")
    public PostGetResponseDto getPost(@PathVariable Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("/api/post/{postid}");
        System.out.println("postid: "+postid);
        System.out.println("userDetails.getUsername(): "+userDetails.getUsername());
        String username = userDetails.getUsername();
        return postService.getPost(postid, username);
    }

}

