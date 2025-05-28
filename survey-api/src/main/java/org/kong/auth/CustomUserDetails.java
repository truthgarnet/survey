package org.kong.auth;

import lombok.RequiredArgsConstructor;

import org.kong.user.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final String userId;

    @JsonIgnore
    private final String userPwd;

    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role;
            }
        });
        return null;
    }

    @Override
    public String getPassword() {
        return userPwd;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    public CustomUserDetails(UserEntity user) {
        this.userId = user.getUserId();
        this.userPwd = user.getPassword();
        this.role = user.getRole();
    }
}
