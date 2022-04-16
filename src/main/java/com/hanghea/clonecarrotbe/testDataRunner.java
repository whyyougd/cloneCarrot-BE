//package com.hanghea.clonecarrotbe;
//
//
//import com.hanghea.clonecarrotbe.domain.Main;
//import com.hanghea.clonecarrotbe.domain.Status;
//import com.hanghea.clonecarrotbe.domain.User;
//import com.hanghea.clonecarrotbe.repository.MainRepository;
//import com.hanghea.clonecarrotbe.repository.StatusRepository;
//import com.hanghea.clonecarrotbe.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class testDataRunner implements ApplicationRunner {
//
//    @Autowired
//    private MainRepository mainRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private StatusRepository statusRepository;
////    @Autowired
////    private PasswordEncoder passwordEncoder;
////    @Autowired
////    private UserRepository userRepository;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        // 테스트 User 생성
//        User user0= new User("test", "1234");
//        user0 = userRepository.save(user0);
//
//        Status status0 = new Status("판매중");
//        Status status1 = new Status("예약중");
//        Status status2 = new Status("거래완료");
//
//        status0 = statusRepository.save(status0);
//        status1 = statusRepository.save(status1);
//        status2 = statusRepository.save(status2);
//
//
//        Main main0 = new Main("판매중입니다",user0,status0,20000L);
//        Main main1 = new Main("예약중입니다",user0,status1,250000L);
//        Main main2 = new Main("거래완료됐습니다",user0,status2,5000L);
//
//        mainRepository.save(main0);
//        mainRepository.save(main1);
//        mainRepository.save(main2);
//    }
//}
