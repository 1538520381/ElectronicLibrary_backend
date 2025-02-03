package com.Persolute.ElectronicLibrary.service.impl;

import com.Persolute.ElectronicLibrary.entity.po.Category;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.Persolute.ElectronicLibrary.exception.CustomerException;
import com.Persolute.ElectronicLibrary.mapper.CategoryMapper;
import com.Persolute.ElectronicLibrary.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Category ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/01/27 13:51
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    /*
     * @author Persolute
     * @version 1.0
     * @description 新增类别
     * @email 1538520381@qq.com
     * @date 2025/2/3 下午4:48
     */
    @Override
    public R add(Category category) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper1 = new LambdaQueryWrapper<Category>()
                .eq(Category::getIsDeleted, false)
                .eq(Category::getName, category.getName());
        if (super.getOne(lambdaQueryWrapper1) != null) {
            return R.error("该类别已存在");
        }

        LambdaQueryWrapper<Category> lambdaQueryWrapper2 = new LambdaQueryWrapper<Category>()
                .eq(Category::getIsDeleted, false);
        int count = super.count(lambdaQueryWrapper2);
        category.setSort(count + 1);

        if (!super.save(category)) {
            throw new CustomerException();
        }

        return R.success();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取列表根据sort排序
     * @email 1538520381@qq.com
     * @date 2025/2/3 下午5:04
     */
    @Override
    public R getListSortBySort() {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<Category>()
                .eq(Category::getIsDeleted, false)
                .orderByAsc(Category::getSort);
        List<Category> categoryList = super.list(lambdaQueryWrapper);
        return R.success().put("categoryList", categoryList);
    }
}
