package com.hanghea.clonecarrotbe.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final HeaderTokenExtractor headerTokenExtractor;

    public WebSecurityConfiguration(
            HeaderTokenExtractor headerTokenExtractor
    ) {
        this.headerTokenExtractor = headerTokenExtractor;
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests() // 권한요청 처리 설정 메서드
                .antMatchers("/h2-console/**").permitAll()// 누구나 h2-console 접속 허용
                .and() // ...
                .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .disable(); // GET메소드는 문제가 없는데 POST메소드만 안되서 CSRF 비활성화 시킴
    }

    private JwtAuthFilter jwtFilter() throws Exception {
        List<String> skipPathList = new ArrayList<>();

        // 회원 관리 API 허용
//        skipPathList.add("GET,/user/**");
//        skipPathList.add("POST,/user/**");
//        skipPathList.add("GET,/oauth/**");
//
//        skipPathList.add("GET,/image/**");
//        skipPathList.add("GET,/");

        skipPathList.add("GET,/**");
        skipPathList.add("POST,/**");
        skipPathList.add("PUT,/**");

        FilterSkipMatcher matcher = new FilterSkipMatcher(
                skipPathList,
                "/**"
        );

        JwtAuthFilter filter = new JwtAuthFilter(
                matcher,
                headerTokenExtractor
        );
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }
}

