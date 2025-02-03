package com.Persolute.ElectronicLibrary.service.impl;

import com.Persolute.ElectronicLibrary.entity.po.Book;
import com.Persolute.ElectronicLibrary.entity.po.BookPageDocument;
import com.Persolute.ElectronicLibrary.mapper.BookPageDocumentMapper;
import com.Persolute.ElectronicLibrary.service.BookPageDocumentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Persolute
 * @version 1.0
 * @description BookPageFile ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/01/27 12:02
 */
@Service
public class BookPageDocumentServiceImpl extends ServiceImpl<BookPageDocumentMapper, BookPageDocument> implements BookPageDocumentService {
    /*
     * @author Persolute
     * @version 1.0
     * @description 根据bookId删除
     * @email 1538520381@qq.com
     * @date 2025/2/3 下午10:01
     */
    @Override
    public void deleteByBookId(Long bookId) {
        BookPageDocument bookPageDocument = new BookPageDocument();
        bookPageDocument.setIsDeleted(true);

        LambdaQueryWrapper<BookPageDocument> lambdaQueryWrapper = new LambdaQueryWrapper<BookPageDocument>()
                .eq(BookPageDocument::getIsDeleted, false)
                .eq(BookPageDocument::getBookId, bookId);

        super.update(bookPageDocument, lambdaQueryWrapper);

        return;
    }
}
