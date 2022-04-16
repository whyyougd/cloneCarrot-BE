package com.hanghea.clonecarrotbe.service;

import com.hanghea.clonecarrotbe.domain.Love;
import com.hanghea.clonecarrotbe.domain.Main;
import com.hanghea.clonecarrotbe.dto.MainPostsGetResponseDto;
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
    public List<MainPostsGetResponseDto> getMainPosts() {
        List<Main> allSavedMains = mainRepository.findAll();
        List<MainPostsGetResponseDto> mainPostsGetResponseDtoList = new ArrayList<MainPostsGetResponseDto>();
        List<String> lovedUsers = new ArrayList<>();

        for(Main savedMain : allSavedMains){
            // 각각의 포스트 아이디 가져오기
            Long postid = savedMain.getPostId();
            String username = savedMain.getUser().getUsername();
            String title = savedMain.getTitle();
            Long price = savedMain.getPrice();
//            String image = String.valueOf(savedPost.getImageList().get(0));
            // 각 포스트 like 조회
            List<Love> loveList = loveRepository.findAllByPost_PostId(postid);
            for (Love eachLove: loveList){
                String loveUsername = eachLove.getLoveUsername();
                lovedUsers.add(loveUsername);
            }
            int loveCnt = loveList.size();
            String createdAt = String.valueOf(savedMain.getCreatedAt());

            String status = savedMain.getStatus().getStatus();

//            mainPostsGetResponseDtoList.add(new MainPostsGetResponseDto(postid,username,title,price,image,loveCnt,createdAt,status));
            mainPostsGetResponseDtoList.add(new MainPostsGetResponseDto(postid,username,title,price,loveCnt,createdAt,status));
        }

        return mainPostsGetResponseDtoList;
    }

}
