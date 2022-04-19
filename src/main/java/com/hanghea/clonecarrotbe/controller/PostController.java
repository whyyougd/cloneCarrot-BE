package com.hanghea.clonecarrotbe.controller;

import com.hanghea.clonecarrotbe.domain.Image;
import com.hanghea.clonecarrotbe.dto.PostRequestDto;
import com.hanghea.clonecarrotbe.dto.PostResponseDto;
import com.hanghea.clonecarrotbe.repository.PostRepository;
import com.hanghea.clonecarrotbe.security.UserDetailsImpl;
import com.hanghea.clonecarrotbe.service.PostService;
import com.hanghea.clonecarrotbe.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;
    private final S3Service s3Service;

//    // 글 작성
//    @PostMapping("/api/post")
//    public ResponseEntity<String> createPost(@RequestBody PostRequestDto postRequestDto,
//                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        postService.createPost(postRequestDto, userDetails.getUser());
//
//        return ResponseEntity.ok().body("게시글작성 완료!");
//    }

    // 게시글 생성
    @PostMapping("/api/post")
    public PostResponseDto createPost(@RequestPart PostRequestDto requestDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @RequestPart MultipartFile file) {
        List<String> imgPath = new ArrayList<>(s3Service.upload(file));
        requestDto.setImageList(imgPath);
        System.out.println("image들어갔나");
        return postService.createPost(requestDto, userDetails);
    }

    // 게시글 수정
    @PutMapping("/api/post/{postid}")
    public void updatePost(@PathVariable Long postid,
                           @RequestPart PostRequestDto postRequestDto,
                           @RequestPart List<MultipartFile> file) {

        List<Image> imagePath = s3Service.upload(file,postRequestDto.getImageList());
        postRequestDto.setImageList(imagePath);
        return postService.updatePost(postid, postRequestDto);
    }



    // 게시글 삭제
    @DeleteMapping("/api/post/{postid}")
    public String deletePost(@PathVariable Long postid) {
        postRepository.deleteById(postid);
        return "삭제 완료!";
    }

//    // 게시글 이미지 업로드
//    @PostMapping("/api/posting/image")
//    public String uploadImages( @RequestParam(value = "image", required = false) MultipartFile multipartFile) throws IOException {
//        String imgUrl = null;
//        if(!multipartFile.isEmpty()){
//            imgUrl = s3Uploader.upload(multipartFile, "static");
//        }
//        return imgUrl;
//    }
}

