package com.Persolute.ElectronicLibrary.entity.dto;

import com.Persolute.ElectronicLibrary.entity.po.Category;
import lombok.Data;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Category UpdateList Dto
 * @email 1538520381@qq.com
 * @date 2025/02/03 17:25
 */
@Data
public class CategoryUpdateListDto {
    // 类别列表
    List<Category> categoryList;
}
