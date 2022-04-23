package com.hanghea.clonecarrotbe.service;

import com.hanghea.clonecarrotbe.domain.*;
import com.hanghea.clonecarrotbe.dto.MainPostsGetResponseDto;
import com.hanghea.clonecarrotbe.dto.PostGetResponseDto;
import com.hanghea.clonecarrotbe.dto.PostRequestDto;
import com.hanghea.clonecarrotbe.dto.PostResponseDto;
import com.hanghea.clonecarrotbe.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final StatusRepository statusRepository;
    private final S3Service s3Service;
    private final LoveService loveService;

    // 게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, ArrayList<MultipartFile> files, User user) {


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

        // s3Service에 이미지 저장
        List<String> imageList = s3Service.upload(files);
        postRequestDto.setImageList(imageList);

        // 유효성 검사
        if (postRequestDto.getTitle() == null) {
            throw new IllegalArgumentException("내용을 적어주세요.");
        } else if (postRequestDto.getPrice() < 0) {
            throw new IllegalArgumentException("0원 이상의 가격을 넣어주세요.");
        } else if (postRequestDto.getContent() == null) {
            throw new IllegalArgumentException("상세 설명을 넣어주세요.");
        }


        Post post = new Post(user, postRequestDto, category, status);

        Post savedpost= postRepository.save(post);

        // 이미지 url 저장하기
        saveImage(imageList, savedpost);

        return new PostResponseDto(savedpost, imageList);

    }

    // 메인페이지 조회
    public List<MainPostsGetResponseDto> getMainPosts() {
        List<Post> allSavedPosts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<MainPostsGetResponseDto> mainPostsGetResponseDtoList = new ArrayList<MainPostsGetResponseDto>();

        for(Post savedPost : allSavedPosts){

            // 각 포스트에 저장된 첫번째 이미지만 조회
            String image = "";
            if(savedPost.getImageList().size()!=0){
                image = savedPost.getImageList().get(0).getImageurl();
            }

            // 각 포스트 like 조회
            Long postid = savedPost.getPostId();
            int loveCnt = loveService.getLoveCnt(postid);


            String createdAt = String.valueOf(savedPost.getCreatedAt());

            mainPostsGetResponseDtoList.add(new MainPostsGetResponseDto(savedPost,image,loveCnt,createdAt));
        }
        return mainPostsGetResponseDtoList;
    }


    // 게시글 상세페이지 조회하기
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
        int loveCnt = loveService.getLoveCnt(postid);

        // post에 uid에 해당하는 유저의 아이디가 있는지 찾기
        boolean isLove = loveService.lovePresent(savedPost, username);

        // 생성일
        String createdAt = String.valueOf(savedPost.getCreatedAt());

        return new PostGetResponseDto(savedPost, imageUrls, loveCnt, isLove, createdAt);

    }


        //게시글 수정
        public PostResponseDto updatePost(Long postid, PostRequestDto requestDto, List<MultipartFile> files, User user) {
            //게시글 검사
            Post post = postRepository.findById(postid)
                    .orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));
            //작성자 검사
            Long postUserId = post.getUser().getId();
            System.out.println("postUserId = " + postUserId);
            if (!user.getId().equals(postUserId)){
                throw new IllegalArgumentException("작성자가 아니므로, 해당 게시글을 수정할 수 없습니다.");
            }
            //카테고리 검사
            Category category = categoryRepository.findById(requestDto.getCategoryid())
                    .orElseThrow(() -> new IllegalStateException("해당 카테고리가 없습니다."));

            // 유효성 검사
            if (requestDto.getTitle() == null) {
                throw new IllegalArgumentException("내용을 적어주세요.");
            } else if (requestDto.getPrice() < 0) {
                throw new IllegalArgumentException("0원 이상의 가격을 넣어주세요.");
            } else if (requestDto.getContent() == null) {
                throw new IllegalArgumentException("상세 설명을 넣어주세요.");
            }

            List<String> imagePaths = s3Service.update(postid, files);

            post.update(requestDto, category);

            List<String> images = saveImage(imagePaths, post);

            return new PostResponseDto(post, images);
        }

        // 게시글 수정 (이미지 없이)
        @Transactional
        public PostResponseDto updatePost2(Long postid, PostRequestDto postRequestDto, User user){

            Post post = postRepository.findById(postid)
                    .orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));
            Long postUserId = post.getUser().getId();

            if (!user.getId().equals(postUserId)){
                throw new IllegalArgumentException("작성자가 아니므로, 해당 게시글을 수정할 수 없습니다.");
            }

            Category category = categoryRepository.findById(postRequestDto.getCategoryid())
                    .orElseThrow(() -> new IllegalStateException("해당 카테고리가 없습니다."));

            post.update(postRequestDto, category);

            // 이미지 모두 찾아오기
            List<String> imageUrls = new ArrayList<>();
            List<Image> imageList = imageRepository.findAllByPost_PostId(postid);
            for (Image image : imageList){
                String imageUrl = image.getImageurl();
                imageUrls.add(imageUrl);
            }

            return new PostResponseDto(post,imageUrls);
        }


        // 게시글 삭제
        public void deletePost(Long postid, User user) {
            //item 유효성 검사
            Post post = postRepository.findById(postid)
                    .orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));
            //작성자 검사
            Long postUserId = post.getUser().getId();
            if (!user.getId().equals(postUserId)){
                throw new IllegalArgumentException("작성자가 아니므로, 해당 게시글을 삭제할 수 없습니다.");
            }
            //S3 사진, ImageURl, item 삭제
            s3Service.delete(postid);
            imageRepository.deleteAllByPost_PostId(postid);
            postRepository.deleteById(postid);

        }

    //이미지 저장 메서드
    private List<String> saveImage(List<String> imageList, Post post) {
        List<String> images = new ArrayList<>();
        for(String eachImage : imageList){
            Image image = new Image(eachImage, post);
            imageRepository.save(image);
            images.add(image.getImageurl());
        }
        return images;
    }

}
