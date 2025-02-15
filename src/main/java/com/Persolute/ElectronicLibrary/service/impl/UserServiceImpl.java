package com.Persolute.ElectronicLibrary.service.impl;

import com.Persolute.ElectronicLibrary.entity.dto.UserQueryPageDto;
import com.Persolute.ElectronicLibrary.entity.po.User;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.Persolute.ElectronicLibrary.exception.CustomerException;
import com.Persolute.ElectronicLibrary.mapper.UserMapper;
import com.Persolute.ElectronicLibrary.service.UserService;
import com.Persolute.ElectronicLibrary.util.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Persolute
 * @version 1.0
 * @description User ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/02/05 15:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    /*
     * @author Persolute
     * @version 1.0
     * @description 登录
     * @email 1538520381@qq.com
     * @date 2025/2/5 下午3:17
     */
    @Override
    public R login(User loginUser) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getIsDeleted, false)
                .eq(User::getAccount, loginUser.getAccount());

        User user = super.getOne(lambdaQueryWrapper);
        if (user == null) {
            throw new CustomerException("账号不存在");
        }

        if (!passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            throw new CustomerException("密码错误");
        }

        if (!user.getStatus()) {
            throw new CustomerException("账号被禁用，请联系管理员");
        }

        redisTemplate.opsForValue().set("login_" + user.getId(), user);

        String token = JWTUtil.createJWT(String.valueOf(user.getId()));

        return R.success("登录成功").put("token", token).put("hasNotLoginFlag", user.getHasNotLoginFlag());
    }

    @Override
    public R loginAdmin(User loginUser) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getIsDeleted, false)
                .eq(User::getAccount, loginUser.getAccount())
                .eq(User::getType, 0);

        User user = super.getOne(lambdaQueryWrapper);
        if (user == null) {
            throw new CustomerException("账号不存在");
        }

        if (!passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            throw new CustomerException("密码错误");
        }

        if (!user.getStatus()) {
            throw new CustomerException("账号被禁用，请联系管理员");
        }

        redisTemplate.opsForValue().set("login_" + user.getId(), user);

        String token = JWTUtil.createJWT(String.valueOf(user.getId()));

        return R.success("登录成功").put("token", token).put("hasNotLoginFlag", user.getHasNotLoginFlag());
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增用户
     * @email 1538520381@qq.com
     * @date 2025/2/11 上午11:53
     */
    @Override
    public R add(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getIsDeleted, false)
                .eq(User::getAccount, user.getAccount());
        if (super.getOne(lambdaQueryWrapper) != null) {
            throw new CustomerException("账号已存在");
        }

        super.save(user);
        return R.success();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 分页查询
     * @email 1538520381@qq.com
     * @date 2025/2/11 下午12:06
     */
    @Override
    public R queryPage(UserQueryPageDto userQueryPageDto) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getIsDeleted, false)
                .eq(User::getType, 1);

        if (userQueryPageDto.getName() != null) {
            lambdaQueryWrapper.like(User::getName, userQueryPageDto.getName());
        }
        if (userQueryPageDto.getPhone() != null) {
            lambdaQueryWrapper.like(User::getPhone, userQueryPageDto.getPhone());
        }
        if (userQueryPageDto.getCompany() != null) {
            lambdaQueryWrapper.like(User::getCompany, userQueryPageDto.getCompany());
        }
        if (userQueryPageDto.getStatus() != null) {
            lambdaQueryWrapper.eq(User::getStatus, userQueryPageDto.getStatus());
        }

        Page<User> userPage = new Page<>(userQueryPageDto.getPage(), userQueryPageDto.getPageSize());
        super.page(userPage, lambdaQueryWrapper);

        return R.success().put("userPage", userPage);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据id删除
     * @email 1538520381@qq.com
     * @date 2025/2/11 下午12:41
     */
    @Override
    public R deleteById(Long id) {
        User user = new User();
        user.setId(id);
        user.setIsDeleted(true);
        super.updateById(user);
        return R.success();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 初始化密码
     * @email 1538520381@qq.com
     * @date 2025/2/11 下午1:54
     */
    @Override
    public R initializePassword(Long id) {
        User user = super.getById(id);

        if (user == null) {
            throw new CustomerException();
        }

        user.setHasNotLoginFlag(true);
        user.setPassword(passwordEncoder.encode(user.getPhone().substring(5)));
        super.updateById(user);
        return R.success();
    }
}
