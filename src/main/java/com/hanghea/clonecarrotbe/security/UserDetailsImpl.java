package com.hanghea.clonecarrotbe.security;

import com.hanghea.clonecarrotbe.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;

//    public UserDetailsImpl(User user) {
//        this.user = user;
//    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override //계정의 만료여부 리턴 스프링시큐리티의 기능들
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override //계정의 잠금여부를 리턴
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override //계정의 비번이 만료되었는지 리턴
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override //사용가능한계정인지 리턴
    public boolean isEnabled() {
        return true;
    }

    @Override //계정이 가지고 있는 권한 목록들을 리턴
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        UserRoleEnum userRole = user.getRole();
//        String authority = userRole.getAuthority();
//
//        SimpleGrantedAuthority simpleAuthority = new SimpleGrantedAuthority(authority);
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(simpleAuthority);
//        return authorities;
        return null;
    }
}