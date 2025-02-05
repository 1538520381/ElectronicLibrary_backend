package com.Persolute.ElectronicLibrary.controller;

import com.Persolute.ElectronicLibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
