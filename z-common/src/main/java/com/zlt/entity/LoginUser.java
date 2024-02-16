package com.zlt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Authof: zlt
 * @Date: 2024-2-13 8:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//UserDetails是SpringSecurity官方提供的接口
public class LoginUser implements UserDetails {
    private User user;

    @Override
    //用于返回权限信息。
    //现在我们正在实现‘认证’，‘权限’后面才用的到，所以返回null即可
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    //判断登陆状态是否过期。
    //把这个改成true，表示永不过期
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    //判断账号是否被锁定
    //把这个改成true，表示未锁定
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    //判断登录凭证是否过期
    //把这个改成true，表示永不过期
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    //判断用户是否可用。把这个改成true，表示可用状态
    public boolean isEnabled() {
        return true;
    }
}
