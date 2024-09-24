package com.project.LMS_plus.security;


import com.project.LMS_plus.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한 정보를 반환 (여기서는 기본적으로 빈 리스트로 설정)
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getStudentId();  // 사용자 ID로 학번을 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정이 만료되지 않았는지 여부 (true로 설정)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠겨 있지 않은지 여부 (true로 설정)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 자격 증명이 만료되지 않았는지 여부 (true로 설정)
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정이 활성화되었는지 여부 (true로 설정)
        return true;
    }
}
