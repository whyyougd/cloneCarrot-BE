//package com.hanghea.clonecarrotbe;
//
//
//import com.hanghea.clonecarrotbe.domain.Category;
//import com.hanghea.clonecarrotbe.domain.Status;
//import com.hanghea.clonecarrotbe.repository.CategoryRepository;
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
//    private UserRepository userRepository;
//    @Autowired
//    private StatusRepository statusRepository;
//    @Autowired
//    private CategoryRepository categoryRepository;
////    @Autowired
////    private PasswordEncoder passwordEncoder;
////    @Autowired
////    private UserRepository userRepository;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        // 테스트 User 생성
////        User user0= new User("test", "1234");
////        user0 = userRepository.save(user0);
//
////        Status status0 = new Status("판매중");
////        Status status1 = new Status("예약중");
////        Status status2 = new Status("거래완료");
////
////        status0 = statusRepository.save(status0);
////        status1 = statusRepository.save(status1);
////        status2 = statusRepository.save(status2);
//
//
////        Category category0 = new Category("생활가전");
////        Category category1 = new Category("여성의류");
////        Category category2 = new Category("남성패션/잡화");
//
////        categoryRepository.save(category0);
////        categoryRepository.save(category1);
////        categoryRepository.save(category2);
//
//
////
////        Main main0 = new Main(user0, "판매중입니다.", "중고거래0합니다", 30000L,category0,status0);
////        Main main1 = new Main(user0, "예약중입니다.", "중고거래1합니다", 250000L,category1,status1);
////        Main main2 = new Main(user0, "거래완료됐습니다.", "중고거래2합니다", 5000L,category2,status2);
////
////        mainRepository.save(main0);
////        mainRepository.save(main1);
////        mainRepository.save(main2);
//
//    }
//}
