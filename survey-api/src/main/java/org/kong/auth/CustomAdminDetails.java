package org.kong.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.kong.admin.entity.AdminEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAdminDetails implements UserDetails {
    
    private final String adminId;
    
    @JsonIgnore 
    private final String adminPwd;

    private String role;

    public CustomAdminDetails(AdminEntity admin) {
        this.adminId = admin.getAdminId();
        this.adminPwd = admin.getPassword();
        this.role = admin.getRole();
    }

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

    @Override public String getUsername() { return adminId; }
    @Override public String getPassword() { return adminPwd; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    
}