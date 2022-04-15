package com.hanghea.clonecarrotbe;


import com.hanghea.clonecarrotbe.domain.Post;
import com.hanghea.clonecarrotbe.domain.Status;
import com.hanghea.clonecarrotbe.domain.User;
import com.hanghea.clonecarrotbe.repository.PostRepository;
import com.hanghea.clonecarrotbe.repository.StatusRepository;
import com.hanghea.clonecarrotbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class testDataRunner implements ApplicationRunner {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 테스트 User 생성
        User user0= new User("test", "1234");
        user0 = userRepository.save(user0);

        Status status0 = new Status("판매중");
        Status status1 = new Status("예약중");
        Status status2 = new Status("거래완료");

        status0 = statusRepository.save(status0);
        status1 = statusRepository.save(status1);
        status2 = statusRepository.save(status2);


        Post post0 = new Post("판매중입니다",user0,status0);
        Post post1 = new Post("예약중입니다",user0,status1);
        Post post2 = new Post("거래완료됐습니다",user0,status2);

        postRepository.save(post0);
        postRepository.save(post1);
        postRepository.save(post2);
    }
}
