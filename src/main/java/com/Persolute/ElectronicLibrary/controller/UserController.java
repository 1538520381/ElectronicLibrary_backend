package com.Persolute.ElectronicLibrary.controller;

import com.Persolute.ElectronicLibrary.entity.po.User;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.Persolute.ElectronicLibrary.exception.CustomerException;
import com.Persolute.ElectronicLibrary.service.UserService;
import com.Persolute.ElectronicLibrary.util.JWTUtil;
import com.Persolute.ElectronicLibrary.util.WebUtil;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Persolute
 * @version 1.0
 * @description User Controller
 * @email 1538520381@qq.com
 * @date 2025/02/05 15:03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * @author Persolute
     * @version 1.0
     * @description 注册
     * @email 1538520381@qq.com
     * @date 2025/2/5 下午3:11
     */
    @PostMapping("/register")
    public R register(@RequestBody User user) {
        if (user.getAccount() == null) {
            throw new CustomerException("账号不能为空");
        } else if (user.getPassword() == null) {
            throw new CustomerException("密码不能为空");
        } else if (user.getType() == null) {
            throw new CustomerException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userService.register(user);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 登录
     * @email 1538520381@qq.com
     * @date 2025/2/5 下午3:22
     */
    @PostMapping("/login")
    public R login(@RequestBody User user) {
        if (user.getAccount() == null) {
            throw new CustomerException("账号不能为空");
        } else if (user.getPassword() == null) {
            throw new CustomerException("密码不能为空");
        }

        return userService.login(user);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据token获取用户
     * @email 1538520381@qq.com
     * @date 2025/2/6 下午4:19
     */
    @GetMapping("/getUserByToken")
    public R getUserByToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");

        if (token == null) {
            return R.error("用户未登录");
        }

        String userId;
        try {
            Claims claims = JWTUtil.paresJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            throw new CustomerException("非法token");
        }

        Object user = redisTemplate.opsForValue().get("login_" + userId);
        if (user == null) {
            return R.error("用户未登录");
        }

        return R.success().put("user", user);
    }
}
