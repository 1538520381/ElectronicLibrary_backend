package com.Persolute.ElectronicLibrary.entity.dto;

import com.Persolute.ElectronicLibrary.entity.po.Book;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2025/02/03 20:52
 */
@Data
public class BookQueryPageDto extends Book {
    // 页码
    private Integer page;

    // 页大小
    private Integer pageSize;
}
