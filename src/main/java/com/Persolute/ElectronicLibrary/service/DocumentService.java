package com.Persolute.ElectronicLibrary.service;

import com.Persolute.ElectronicLibrary.entity.po.Document;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Persolute
 * @version 1.0
 * @description Document Service
 * @email 1538520381@qq.com
 * @date 2025/02/02 20:26
 */
public interface DocumentService extends IService<Document> {
    R addDocument(Document document);
}
