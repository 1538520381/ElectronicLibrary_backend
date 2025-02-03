package com.Persolute.ElectronicLibrary.controller;

import com.Persolute.ElectronicLibrary.entity.po.Category;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.Persolute.ElectronicLibrary.exception.CustomerException;
import com.Persolute.ElectronicLibrary.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description Category Controller
 * @email 1538520381@qq.com
 * @date 2025/01/27 13:52
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增类别
     * @email 1538520381@qq.com
     * @date 2025/2/3 下午4:46
     */
    @PostMapping("/add")
    public R add(@RequestBody Category category) {
        if (category.getName() == null) {
            throw new CustomerException("类别名称不能为空");
        }

        return categoryService.add(category);
    }
}
