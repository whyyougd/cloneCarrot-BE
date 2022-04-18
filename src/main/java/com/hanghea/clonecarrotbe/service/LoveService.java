//package com.hanghea.clonecarrotbe.service;
//
//import com.hanghea.clonecarrotbe.domain.Love;
//import com.hanghea.clonecarrotbe.domain.Main;
//import com.hanghea.clonecarrotbe.dto.LoveGetResponseDto;
//import com.hanghea.clonecarrotbe.repository.LoveRepository;
//import com.hanghea.clonecarrotbe.repository.MainRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Service
//
//public class LoveService {
//    private final LoveRepository loveRepository;
//    private final MainRepository mainRepository;
//
//    @Transactional
//    public LoveGetResponseDto getLove(Long postid, String username) {
//
//        Main Main = mainRepository.findById(postid).get();
//
//        // post에 uid에 해당하는 유저의 아이디가 있는지 찾기
//        Optional<Love> found = loveRepository.findByPostAndLoveUsername(Main, username);
//
//        boolean isLove;
//
//        // 있을 때와 없을 때 구분하여 토글 실행
//        if (found.isPresent()) {
//            loveRepository.deleteByLoveId(found.get().getLoveId());
//            isLove = false;
//        } else {
//            Love love = new Love(Main, username);
//            loveRepository.save(love);
//            isLove = true;
//        }
//
//        System.out.println("isLove: "+isLove);
//
//        // like 조회
//        List<String> lovedUsers = new ArrayList<>();
//        List<Love> loveList = loveRepository.findAllByPost_PostId(postid);
//        for (Love eachLikes: loveList){
//            String loveUsername = eachLikes.getLoveUsername();
//            lovedUsers.add(loveUsername);
//        }
//
//        return new LoveGetResponseDto(isLove,loveList.size(),lovedUsers);
//    }
//}
