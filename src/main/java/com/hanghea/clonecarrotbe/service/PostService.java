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
import org.springframework.web.multipart.MultipartFile;

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
    private final S3Service s3Service;

    // 게시글 작성
    @Transactional
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

            String image = "";
            if(savedPost.getImageList().size()!=0){
                image = savedPost.getImageList().get(0).getImageurl();
            }

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


        //게시글 수정
        public PostResponseDto updatePost(Long postid, PostRequestDto requestDto, List<MultipartFile> files, User user) {
            System.out.println("postid: "+ postid);
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
            if (requestDto.getTitle() == null) {
                throw new IllegalArgumentException("내용을 적어주세요.");
            } else if (requestDto.getPrice() < 0) {
                throw new IllegalArgumentException("0원 이상의 가격을 넣어주세요.");
            } else if (requestDto.getContent() == null) {
                throw new IllegalArgumentException("상세 설명을 넣어주세요.");
            }

            List<String> imagePaths = s3Service.update(postid, files);
            System.out.println("수정된 Image경로들 모아놓은것 :"+ imagePaths);

            post.update(requestDto, category);

            //이미지 URL 저장하기
            List<String> images = new ArrayList<>();
            for(String imageUrl : imagePaths){
                Image image = new Image(imageUrl, post);
                imageRepository.save(image);
                images.add(image.getImageurl());
            }
            return new PostResponseDto(post, images);
        }


        // 게시글 삭제
        public Long deletePost(Long postid, User user) {
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
            System.out.println("당근 게시글 삭제 완료");

            return postid;
        }
}
