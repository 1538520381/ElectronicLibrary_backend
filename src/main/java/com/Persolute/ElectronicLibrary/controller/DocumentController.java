package com.Persolute.ElectronicLibrary.controller;

import com.Persolute.ElectronicLibrary.entity.po.Document;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.Persolute.ElectronicLibrary.exception.CustomerException;
import com.Persolute.ElectronicLibrary.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

/**
 * @author Persolute
 * @version 1.0
 * @description Document Controller
 * @email 1538520381@qq.com
 * @date 2025/02/02 20:27
 */
@RestController
@RequestMapping("/document")
public class DocumentController {
    @Value("${ElectronicLibrary.document.path}")
    private String documentPath;

    @Autowired
    private DocumentService documentService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 上传文件
     * @email 1538520381@qq.com
     * @date 2025/2/2 下午8:46
     */
    @PostMapping("/upload")
    public R upload(@RequestBody MultipartFile document) {
        String originalDocumentName = document.getOriginalFilename();
        String suffix = originalDocumentName.substring(originalDocumentName.lastIndexOf("."));

        Document newDocument = new Document();
        newDocument.setOriginalDocumentName(originalDocumentName);

        String documentPathName = UUID.randomUUID() + suffix;
        newDocument.setDocumentPathName(documentPathName);

        File dir = new File(documentPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            document.transferTo(new File(documentPath + documentPathName));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException(e.getMessage());
        }

        return documentService.addDocument(newDocument);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 下载文件
     * @email 1538520381@qq.com
     * @date 2025/2/3 下午3:04
     */
    @GetMapping("/download/{documentId}")
    public R download(HttpServletResponse response, @PathVariable String documentId) {
        Document document = documentService.getById(documentId);
        if (document == null) {
            return R.error("文件不存在");
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(documentPath + document.getDocumentPathName());
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.success();
    }
}
