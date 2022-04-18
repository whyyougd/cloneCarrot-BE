package com.hanghea.clonecarrotbe.service;

import com.hanghea.clonecarrotbe.domain.Category;
import com.hanghea.clonecarrotbe.domain.Post;
import com.hanghea.clonecarrotbe.domain.User;
import com.hanghea.clonecarrotbe.dto.PostRequestDto;
import com.hanghea.clonecarrotbe.dto.PostResponseDto;
import com.hanghea.clonecarrotbe.repository.CategoryRepository;
import com.hanghea.clonecarrotbe.repository.ImageRepository;
import com.hanghea.clonecarrotbe.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto,User user) {

        System.out.println("username : "+user.getUsername());

        String categoryName = postRequestDto.getCategoryName();

        System.out.println("categoryName: "+categoryName);

        Category category= categoryRepository.findByCategoryName(categoryName).orElseThrow(
                () -> new NullPointerException("해당 카테고리명이 존재하지 않습니다.")
        );

        Post post = Post.builder()
                .user(user)
//                .imageList(postRequestDto.getImageList())
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
//                .imageList(postRequestDto.getImageList())
                .build();
    }
}
