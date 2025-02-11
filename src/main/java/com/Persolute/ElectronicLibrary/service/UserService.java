package com.Persolute.ElectronicLibrary.service;

import com.Persolute.ElectronicLibrary.entity.dto.UserQueryPageDto;
import com.Persolute.ElectronicLibrary.entity.po.User;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Persolute
 * @version 1.0
 * @description User Service
 * @email 1538520381@qq.com
 * @date 2025/02/05 15:01
 */
public interface UserService extends IService<User> {
    R register(User user);

    R login(User user);

    R add(User user);

    R queryPage(UserQueryPageDto userQueryPageDto);

    R deleteById(Long id);

    R initializePassword(Long id);
}
