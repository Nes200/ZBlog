package com.zlt.service.impl;

import com.zlt.Utils.BeanCopyUtils;
import com.zlt.Utils.JwtUtil;
import com.zlt.Utils.RedisCache;
import com.zlt.entity.LoginUser;
import com.zlt.entity.ResponseResult;
import com.zlt.entity.User;
import com.zlt.service.BlogLoginService;
import com.zlt.vo.BlogUserLoginVo;
import com.zlt.vo.UserInfoVo;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ObjectStreamClass;
import java.util.Objects;

/**
 * @Authof: zlt
 * @Date: 2024-2-13 8:30
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    //AuthenticationManager是security官方提供的接口
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //封装登陆的用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //在下一行之前，封装的数据会先走UserDetailsServiceImpl实现类
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //上面那一行会得到所有的认证用户信息authenticate。
        //然后下一行需要判断用户认证是否通过，如果authenticate的值是null，就说明认证没有通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //把这个userid通过我们写的JwtUtil工具类转换成密文，这个密文就是token值
        String jwt = JwtUtil.createJWT(userId);

        //第二个参数：我们写的是loginUser，里面有权限信息，后面会用到
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);

        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        Integer userid = loginUser.getUser().getId();

        //根据key删除redis中对应的value值
        redisCache.deleteObject("bloglogin:"+userid);

        return ResponseResult.okResult();
    }
}
