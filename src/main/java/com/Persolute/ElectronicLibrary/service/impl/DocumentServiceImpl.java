package com.Persolute.ElectronicLibrary.service.impl;

import com.Persolute.ElectronicLibrary.entity.po.Document;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.Persolute.ElectronicLibrary.exception.CustomerException;
import com.Persolute.ElectronicLibrary.mapper.DocumentMapper;
import com.Persolute.ElectronicLibrary.service.DocumentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Persolute
 * @version 1.0
 * @description Document ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/02/02 20:26
 */
@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements DocumentService {
    /*
     * @author Persolute
     * @version 1.0
     * @description 新增文件
     * @email 1538520381@qq.com
     * @date 2025/2/2 下午8:53
     */
    @Override
    public R addDocument(Document document) {
        if (!super.save(document)) {
            throw new CustomerException();
        }

        Document addDocumentVO = new Document();
        addDocumentVO.setId(document.getId());
        addDocumentVO.setOriginalDocumentName(document.getOriginalDocumentName());
        addDocumentVO.setDocumentPathName(document.getDocumentPathName());
        addDocumentVO.setCreateTime(document.getCreateTime());
        addDocumentVO.setUpdateTime(document.getUpdateTime());

        return R.success().put("addDocumentVO", addDocumentVO);
    }
}
