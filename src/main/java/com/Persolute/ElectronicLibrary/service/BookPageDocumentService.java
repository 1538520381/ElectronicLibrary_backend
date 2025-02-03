package com.Persolute.ElectronicLibrary.service;

import com.Persolute.ElectronicLibrary.entity.po.BookPageDocument;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Persolute
 * @version 1.0
 * @description BookPageDocument Service
 * @email 1538520381@qq.com
 * @date 2025/01/27 12:01
 */
public interface BookPageDocumentService extends IService<BookPageDocument> {
    void deleteByBookId(Long bookId);
}
