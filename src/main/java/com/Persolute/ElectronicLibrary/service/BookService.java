package com.Persolute.ElectronicLibrary.service;

import com.Persolute.ElectronicLibrary.entity.dto.BookQueryPageDto;
import com.Persolute.ElectronicLibrary.entity.po.Book;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Book Service
 * @email 1538520381@qq.com
 * @date 2025/01/27 11:45
 */
public interface BookService extends IService<Book> {
    R queryPage(BookQueryPageDto bookQueryPageDto);

    List<Book> getListByCategoryId(Long categoryId);

    R deleteById(Long id);
}
