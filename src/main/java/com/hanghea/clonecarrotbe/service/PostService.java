package com.hanghea.clonecarrotbe.service;

import com.hanghea.clonecarrotbe.domain.Love;
import com.hanghea.clonecarrotbe.domain.Post;
import com.hanghea.clonecarrotbe.dto.MainPostsGetResponseDto;
import com.hanghea.clonecarrotbe.repository.LoveRepository;
import com.hanghea.clonecarrotbe.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final LoveRepository loveRepository;
    public List<MainPostsGetResponseDto> getMainPosts() {
        List<Post> allSavedPosts = postRepository.findAll();
        List<MainPostsGetResponseDto> mainPostsGetResponseDtoList = new ArrayList<MainPostsGetResponseDto>();
        List<String> lovedUsers = new ArrayList<>();

        for(Post savedPost : allSavedPosts){
            // 각각의 포스트 아이디 가져오기
            Long postid = savedPost.getPostId();
            String username = savedPost.getUser().getUsername();
            String title = savedPost.getTitle();
            Long price = savedPost.getPrice();
//            String image = String.valueOf(savedPost.getImageList().get(0));
            // 각 포스트 like 조회
            List<Love> loveList = loveRepository.findAllByPost_PostId(postid);
            for (Love eachLove: loveList){
                String loveUsername = eachLove.getLoveUsername();
                lovedUsers.add(loveUsername);
            }
            int loveCnt = loveList.size();
            String createdAt = String.valueOf(savedPost.getCreatedAt());

            String status = savedPost.getStatus().getStatus();

//            mainPostsGetResponseDtoList.add(new MainPostsGetResponseDto(postid,username,title,price,image,loveCnt,createdAt,status));
            mainPostsGetResponseDtoList.add(new MainPostsGetResponseDto(postid,username,title,price,loveCnt,createdAt,status));
        }

        return mainPostsGetResponseDtoList;
    }

}
