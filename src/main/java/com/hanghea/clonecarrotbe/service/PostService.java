package com.hanghea.clonecarrotbe.service;

import com.hanghea.clonecarrotbe.domain.Category;
import com.hanghea.clonecarrotbe.domain.Image;
import com.hanghea.clonecarrotbe.domain.Post;
import com.hanghea.clonecarrotbe.domain.User;
import com.hanghea.clonecarrotbe.dto.PostRequestDto;
import com.hanghea.clonecarrotbe.dto.PostResponseDto;
import com.hanghea.clonecarrotbe.repository.CategoryRepository;
import com.hanghea.clonecarrotbe.repository.ImageRepository;
import com.hanghea.clonecarrotbe.repository.PostRepository;
import com.hanghea.clonecarrotbe.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    // 게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto,User user) {

        String categoryName = postRequestDto.getCategoryName();
        Category category= categoryRepository.findByCategoryName(categoryName).orElseThrow(
                () -> new NullPointerException("해당 카테고리명이 존재하지 않습니다.")
        );

        Post post = Post.builder()
                .user(user)
                .imageList(postRequestDto.getImageList())
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .category(category)
                .price(postRequestDto.getPrice())
                .build();

        postRepository.save(post);

        return PostResponseDto.builder()
                .username(user.getUsername())
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .categoryName(category.getCategoryName())
                .price(postRequestDto.getPrice())
                .imageList(postRequestDto.getImageList())
                .build();
    }





//    // 게시글 수정
//    @Transactional
//    public void updatePost(Long postId, PostRequestDto postRequestDto, User user) {
//        Post post = postRepository.findByUserAndPostId(user, postId).orElseThrow(
//                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
//        );
//
//        Category category= categoryRepository.findByCategoryName(postRequestDto.getCategoryName()).orElseThrow(
//                () -> new NullPointerException("해당 카테고리명이 존재하지 않습니다.")
//        );
//        post.update(postRequestDto, category);
//    }

    // 게시글 수정
    public PostResponseDto updatePost(Long postid, PostRequestDto requestDto, UserDetailsImpl userDetails) {
        // 게시글 찾기
        Post findPost = postRepository.findById(postid).orElseThrow(
                () -> new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );

        // 로그인한 사용자과 게시글 작성자 확인
        if(!findPost.getUser().getUsername().equals(userDetails.getUsername())){
            throw new IllegalArgumentException("게시글의 작성자만 수정 할 수 있습니다.");
        }

        // 수정된 제목과 내용 넣어주기
        findPost.setTitle(requestDto.getTitle());
        findPost.setContent(requestDto.getContent());

        // 수정된 게시글 저장
        Post savePost = postRepository.save(findPost);


        // 이미지 다시 빼기
        List<Image> responseImages = new ArrayList<>();
        for(int i = 0; i < savePost.getImageList().size(); i++){
            Image imageFile = savePost.getImageList().get(i);
            responseImages.add(imageFile);
        }

        PostResponseDto postResponseDto = new PostResponseDto(savePost);

        postResponseDto.setImageList(responseImages);

        return postResponseDto;

    }


}
