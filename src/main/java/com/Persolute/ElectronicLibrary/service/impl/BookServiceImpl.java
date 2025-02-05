package com.Persolute.ElectronicLibrary.service.impl;

import com.Persolute.ElectronicLibrary.entity.dto.BookQueryPageDto;
import com.Persolute.ElectronicLibrary.entity.po.Book;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.Persolute.ElectronicLibrary.mapper.BookMapper;
import com.Persolute.ElectronicLibrary.service.BookService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Book ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/01/27 11:45
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    /*
     * @author Persolute
     * @version 1.0
     * @description 查询分页
     * @email 1538520381@qq.com
     * @date 2025/2/3 下午8:35
     */
    @Override
    public R queryPage(BookQueryPageDto bookQueryPageDto) {
        Page<Book> bookPage = new Page<>(bookQueryPageDto.getPage(), bookQueryPageDto.getPageSize());

        LambdaQueryWrapper<Book> lambdaQueryWrapper = new LambdaQueryWrapper<Book>()
                .eq(Book::getIsDeleted, false);

        if (bookQueryPageDto.getCategoryId() != null) {
            lambdaQueryWrapper.eq(Book::getCategoryId, bookQueryPageDto.getCategoryId());
        }
        if (bookQueryPageDto.getName() != null) {
            lambdaQueryWrapper.like(Book::getName, bookQueryPageDto.getName());
        }

        super.page(bookPage, lambdaQueryWrapper);
        return R.success().put("bookPage", bookPage);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取列表通过categoryId
     * @email 1538520381@qq.com
     * @date 2025/2/3 下午9:55
     */
    @Override
    public List<Book> getListByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Book> lambdaQueryWrapper = new LambdaQueryWrapper<Book>()
                .eq(Book::getIsDeleted, false)
                .eq(Book::getCategoryId, categoryId);
        return super.list(lambdaQueryWrapper);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据id删除
     * @email 1538520381@qq.com
     * @date 2025/2/3 下午10:00
     */
    @Override
    public R deleteById(Long id) {
        Book book = new Book();
        book.setId(id);
        book.setIsDeleted(true);
        super.updateById(book);
        return R.success();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 查询分页
     * @email 1538520381@qq.com
     * @date 2025/2/4 下午8:56
     */
    @Override
    public R queryList(Book bookQueryListDto) {
        LambdaQueryWrapper<Book> lambdaQueryWrapper = new LambdaQueryWrapper<Book>()
                .eq(Book::getIsDeleted, false);

        if (bookQueryListDto.getCategoryId() != null) {
            lambdaQueryWrapper.eq(Book::getCategoryId, bookQueryListDto.getCategoryId());
        }
        if (bookQueryListDto.getName() != null) {
            lambdaQueryWrapper.like(Book::getName, bookQueryListDto.getName());
        }

        List<Book> bookList = super.list(lambdaQueryWrapper);

        return R.success().put("bookList", bookList);
    }
}
