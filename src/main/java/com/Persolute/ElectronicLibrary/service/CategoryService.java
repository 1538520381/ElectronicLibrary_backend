package com.Persolute.ElectronicLibrary.service;

import com.Persolute.ElectronicLibrary.entity.po.Category;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Persolute
 * @version 1.0
 * @description Category Service
 * @email 1538520381@qq.com
 * @date 2025/01/27 13:51
 */
public interface CategoryService extends IService<Category> {
    R add(Category category);
}
