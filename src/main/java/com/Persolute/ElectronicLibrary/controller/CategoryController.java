package com.Persolute.ElectronicLibrary.controller;

import com.Persolute.ElectronicLibrary.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
