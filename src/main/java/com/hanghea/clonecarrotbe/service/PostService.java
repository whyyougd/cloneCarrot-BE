package com.hanghea.clonecarrotbe.service;

import com.hanghea.clonecarrotbe.domain.*;
import com.hanghea.clonecarrotbe.dto.MainPostsGetResponseDto;
import com.hanghea.clonecarrotbe.dto.PostGetResponseDto;
import com.hanghea.clonecarrotbe.dto.PostRequestDto;
import com.hanghea.clonecarrotbe.dto.PostResponseDto;
import com.hanghea.clonecarrotbe.repository.*;
import lombok.RequiredArgsConstructor;
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
    private final LoveRepository loveRepository;
    private final StatusRepository statusRepository;

    // 게시글 작성
//    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, List<String> imageList, User user) {

        String title = postRequestDto.getTitle();
        Long price = postRequestDto.getPrice();
        String content = postRequestDto.getContent();
        Long categoryid = postRequestDto.getCategoryid();

        Category category = categoryRepository.findById(categoryid).orElseThrow(
                () -> new IllegalArgumentException("카테고리 없음")
        );

        Status status = statusRepository.findById(1L).orElseThrow(
                () -> new IllegalArgumentException("상태 없음")
        );

        Post post = new Post(user, title, price, content, category, status);
//        postRepository.save(post);

        Post savedpost= postRepository.save(post);


        List<Image> images = new ArrayList<>();
        for(String eachImage : imageList){
            Image image = new Image(eachImage, savedpost);
            System.out.println("imageurl: "+eachImage);
            imageRepository.save(image);
            images.add(image);
        }

        post.setImageList(images);
//        System.out.println(post);
        return new PostResponseDto(savedpost, imageList);



//        String categoryName = postRequestDto.getCategoryName();
//        Category category= categoryRepository.findByCategoryName(categoryName).orElseThrow(
//                () -> new NullPointerException("해당 카테고리명이 존재하지 않습니다.")
//        );
//
//        Post post = Post.builder()
//                .user(user)
////                .imageList(postRequestDto.getImageList())
//                .title(postRequestDto.getTitle())
//                .content(postRequestDto.getContent())
//                .category(category)
//                .price(postRequestDto.getPrice())
//                .build();
//
//        postRepository.save(post);
//
//        List<String> imagesUrlList = new ArrayList<>();
//        List<Image> images = imageRepository.findAllByPost(post);
//        for(Image eachimage : images){
//            String imageUrl = eachimage.getImageurl();
//            imagesUrlList.add(imageUrl);
//        }
//
//        return PostResponseDto.builder()
//                .username(user.getUsername())
//                .title(postRequestDto.getTitle())
//                .content(postRequestDto.getContent())
//                .categoryName(category.getCategoryName())
//                .price(postRequestDto.getPrice())
//                .imageList(imagesUrlList)
//                .build();
    }

    public List<MainPostsGetResponseDto> getMainPosts() {
        List<Post> allSavedPosts = postRepository.findAll();
        List<MainPostsGetResponseDto> mainPostsGetResponseDtoList = new ArrayList<MainPostsGetResponseDto>();

        for(Post savedPost : allSavedPosts){
            // 각각의 포스트 아이디 가져오기
            Long postid = savedPost.getPostId();
            String username = savedPost.getUser().getUsername();
            String title = savedPost.getTitle();
            Long price = savedPost.getPrice();
            String image = savedPost.getImageList().get(0).getImageurl();
            // 각 포스트 like 조회
            List<Love> loveList = loveRepository.findAllByPost_PostId(postid);
            int loveCnt = loveList.size();
            String createdAt = String.valueOf(savedPost.getCreatedAt());
            String category = savedPost.getCategory().getCategoryName();

            String status = savedPost.getStatus().getStatus();

            mainPostsGetResponseDtoList.add(new MainPostsGetResponseDto(postid,username,title,price,image,loveCnt,createdAt, category, status));
        }

        return mainPostsGetResponseDtoList;
    }

    public PostGetResponseDto getPost(Long postid, String username) {
        Post savedPost = postRepository.findById(postid)
                .orElseThrow(()->new NullPointerException("존재하지 않는 PostId 입니다."));

        // 이미지 모두 찾아오기
        List<String> imageUrls = new ArrayList<>();
        List<Image> imageList = imageRepository.findAllByPost_PostId(postid);
        for (Image image : imageList){
            String imageUrl = image.getImageurl();
            imageUrls.add(imageUrl);
        }


        // 좋아요 찾기
        List<Love> loveList = loveRepository.findAllByPost_PostId(postid);
        int loveCnt = loveList.size();

        // post에 uid에 해당하는 유저의 아이디가 있는지 찾기
        Optional<Love> found = loveRepository.findByPostAndLoveUsername(savedPost, username);

        boolean isLove = found.isPresent();

        System.out.println("isLove: "+isLove);

        // 생성일
        String createdAt = String.valueOf(savedPost.getCreatedAt());


        return new PostGetResponseDto(savedPost, imageUrls, loveCnt, isLove, createdAt);
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

//    // 게시글 수정
//    public PostResponseDto updatePost(Long postid, PostRequestDto requestDto, UserDetailsImpl userDetails) {
//        // 게시글 찾기
//        Post findPost = postRepository.findById(postid).orElseThrow(
//                () -> new NullPointerException("해당 게시글을 찾을 수 없습니다.")
//        );
//
//        // 로그인한 사용자과 게시글 작성자 확인
//        if(!findPost.getUser().getUsername().equals(userDetails.getUsername())){
//            throw new IllegalArgumentException("게시글의 작성자만 수정 할 수 있습니다.");
//        }
//
//        // 수정된 제목과 내용 넣어주기
//        findPost.setTitle(requestDto.getTitle());
//        findPost.setContent(requestDto.getContent());
//
//        // 수정된 게시글 저장
//        Post savePost = postRepository.save(findPost);
//
//
//        // 이미지 다시 빼기
//        List<Image> responseImages = new ArrayList<>();
//        for(int i = 0; i < savePost.getImageList().size(); i++){
//            Image imageFile = savePost.getImageList().get(i);
//            responseImages.add(imageFile);
//        }
//
//        PostResponseDto postResponseDto = new PostResponseDto(savePost);
//
//        postResponseDto.setImageList(responseImages);
//
//        return postResponseDto;
//
//    }


}
