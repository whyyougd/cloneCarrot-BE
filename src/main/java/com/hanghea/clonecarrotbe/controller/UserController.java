package com.hanghea.clonecarrotbe.controller;


import com.hanghea.clonecarrotbe.dto.UserRequestDto;
import com.hanghea.clonecarrotbe.security.UserDetailsImpl;
import com.hanghea.clonecarrotbe.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequestDto requestDto) {
        userService.registerUser(requestDto);
        log.info("회원가입 완료");
        return ResponseEntity.ok()
                .body("회원가입 완료");
    }

    @PostMapping("/isLogin")
    public ResponseEntity<String> getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info(userDetails.getUsername());
        return ResponseEntity.ok().body(userDetails.getUsername());
    }

}
//ResponseEntity 클래스를 사용하면, 결과값! 상태코드! 헤더값!을 모두 프론트에 넘겨줄 수 있고, 에러코드 또한 섬세하게 설정해서 보내줄 수 있다는 장점이 있다!
