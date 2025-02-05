package com.Persolute.ElectronicLibrary.service.impl;

import com.Persolute.ElectronicLibrary.entity.po.User;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.Persolute.ElectronicLibrary.exception.CustomerException;
import com.Persolute.ElectronicLibrary.mapper.UserMapper;
import com.Persolute.ElectronicLibrary.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Persolute
 * @version 1.0
 * @description User ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/02/05 15:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /*
     * @author Persolute
     * @version 1.0
     * @description 注册
     * @email 1538520381@qq.com
     * @date 2025/2/5 下午3:09
     */
    @Override
    public R register(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getIsDeleted, false)
                .eq(User::getAccount, user.getAccount());
        if (super.getOne(lambdaQueryWrapper) != null) {
            throw new CustomerException("该账号已存在");
        }

        super.save(user);

        return R.success();
    }
}
