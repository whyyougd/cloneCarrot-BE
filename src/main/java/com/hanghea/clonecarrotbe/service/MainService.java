package com.hanghea.clonecarrotbe.service;

import com.hanghea.clonecarrotbe.domain.Image;
import com.hanghea.clonecarrotbe.domain.Love;
import com.hanghea.clonecarrotbe.domain.Main;
import com.hanghea.clonecarrotbe.domain.Post;
import com.hanghea.clonecarrotbe.dto.MainPostsGetResponseDto;
import com.hanghea.clonecarrotbe.dto.PostGetResponseDto;
import com.hanghea.clonecarrotbe.dto.PostResponseDto;
import com.hanghea.clonecarrotbe.repository.ImageRepository;
import com.hanghea.clonecarrotbe.repository.LoveRepository;
import com.hanghea.clonecarrotbe.repository.MainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MainService {
    private final MainRepository mainRepository;
    private final LoveRepository loveRepository;
    private final ImageRepository imageRepository;

    public List<MainPostsGetResponseDto> getMainPosts() {
        List<Main> allSavedMains = mainRepository.findAll();
        List<MainPostsGetResponseDto> mainPostsGetResponseDtoList = new ArrayList<MainPostsGetResponseDto>();

        for(Main savedMain : allSavedMains){
            // 각각의 포스트 아이디 가져오기
            Long postid = savedMain.getPostId();
            String username = savedMain.getUser().getUsername();
            String title = savedMain.getTitle();
            Long price = savedMain.getPrice();
//            String image = String.valueOf(savedPost.getImageList().get(0));
            // 각 포스트 like 조회
            List<Love> loveList = loveRepository.findAllByMain_PostId(postid);
            int loveCnt = loveList.size();
            String createdAt = String.valueOf(savedMain.getCreatedAt());

            String status = savedMain.getStatus().getStatus();

//            mainPostsGetResponseDtoList.add(new MainPostsGetResponseDto(postid,username,title,price,image,loveCnt,createdAt,status));
            mainPostsGetResponseDtoList.add(new MainPostsGetResponseDto(postid,username,title,price,loveCnt,createdAt,status));
        }

        return mainPostsGetResponseDtoList;
    }

    public PostGetResponseDto getPost(Long postid) {
        Main savedMain = mainRepository.findById(postid)
                .orElseThrow(()->new NullPointerException("존재하지 않는 PostId 입니다."));

        // 이미지 모두 찾아오기
//        List<String> imageUrls = new ArrayList<>();
//        List<Image> imageList = imageRepository.findAllByMain_PostId(postid);
//        for (Image image : imageList){
//            String imageUrl = image.getImageurl();
//            imageUrls.add(imageUrl);
//        }


        // 좋아요 찾기
        List<Love> loveList = loveRepository.findAllByMain_PostId(postid);
        int loveCnt = loveList.size();

        // 생성일
        String createdAt = String.valueOf(savedMain.getCreatedAt());


//        return new PostGetResponseDto(savedMain, imageUrls, loveCnt, createdAt);
        return new PostGetResponseDto(savedMain, loveCnt, createdAt);
    }
}
