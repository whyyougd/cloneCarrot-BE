package com.hanghea.clonecarrotbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequestDto { //회원가입 요청 정보를 받아줄 녀석

    private String username;
    private String password;
    private String passwordcheck;


}
