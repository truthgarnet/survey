package org.kong.user.dto;

import lombok.RequiredArgsConstructor;
import org.kong.user.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userEntity.getRole();
            }
        });
        return null;
    }

    @Override
    public String getPassword() {
        return userEntity.getUserPwd();
    }

    @Override
    public String getUsername() {
        return userEntity.getUserName();
    }
}
