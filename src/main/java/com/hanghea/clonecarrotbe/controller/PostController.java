package com.hanghea.clonecarrotbe.controller;

import com.hanghea.clonecarrotbe.domain.User;
import com.hanghea.clonecarrotbe.dto.PostRequestDto;
import com.hanghea.clonecarrotbe.dto.PostResponseDto;
import com.hanghea.clonecarrotbe.repository.PostRepository;
import com.hanghea.clonecarrotbe.repository.UserRepository;
import com.hanghea.clonecarrotbe.service.PostService;
import com.hanghea.clonecarrotbe.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;
    private final S3Service s3Service;
    private final UserRepository userRepository;


//     글 작성
//    @PostMapping("/api/post")
//    public ResponseEntity<String> createPost(@RequestBody PostRequestDto postRequestDto,
//                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        postService.createPost(postRequestDto, userDetails.getUser());
//
//        return ResponseEntity.ok().body("게시글작성 완료!");
//    }


    // 게시글 생성
//    @PostMapping("/api/post")
//    public PostResponseDto createPost(@RequestPart("com") PostRequestDto requestDto,
//                                      @RequestPart("files") List<MultipartFile> files,
//                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        List<String> imgPath = s3Service.upload(files);
//        requestDto.setImageList(imgPath);
//        System.out.println("image들어갔나"+ imgPath);
//        return postService.createPost(requestDto, imgPath, userDetails.getUser());
//    }
    @PostMapping("/api/post")
    public PostResponseDto createPost(@RequestPart("com") PostRequestDto requestDto,
                                      @RequestPart("files") List<MultipartFile> files) {
        System.out.println("/api/post");
//        System.out.println("requestDto.getCategoryName(): "+requestDto.getCategoryName());
        List<String> imgPath = s3Service.upload(files);
        requestDto.setImageList(imgPath);
        System.out.println("image들어갔나"+ imgPath);
        String username = requestDto.getUsername();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));
        return postService.createPost(requestDto, imgPath, user);
    }
//
//    // 게시글 수정
//    @PutMapping("/api/post/{postid}")
//    public void updatePost(@PathVariable Long postid,
//                           @RequestPart PostRequestDto postRequestDto,
//                           @RequestPart List<MultipartFile> file) {
//
//        List<Image> imagePath = s3Service.upload(file,postRequestDto.getImageList());
//        postRequestDto.setImageList(imagePath);
//        return postService.updatePost(postid, postRequestDto);
//    }



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

