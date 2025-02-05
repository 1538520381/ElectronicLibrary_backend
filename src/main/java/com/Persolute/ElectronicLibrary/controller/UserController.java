package com.Persolute.ElectronicLibrary.controller;

import com.Persolute.ElectronicLibrary.entity.po.User;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.Persolute.ElectronicLibrary.exception.CustomerException;
import com.Persolute.ElectronicLibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
