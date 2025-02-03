package com.Persolute.ElectronicLibrary.controller;

import com.Persolute.ElectronicLibrary.entity.po.Document;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.Persolute.ElectronicLibrary.exception.CustomerException;
import com.Persolute.ElectronicLibrary.service.DocumentService;
import com.alibaba.fastjson.JSON;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
        String suffix = originalDocumentName.substring(originalDocumentName.lastIndexOf(".") + 1);

        Document newDocument = new Document();
        newDocument.setOriginalDocumentName(originalDocumentName);

        String documentPathName = UUID.randomUUID() + "." + suffix;
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

    @PostMapping("/uploadBook")
    public R uploadBook(@RequestBody MultipartFile document) {
        String originalDocumentName = document.getOriginalFilename();
        String suffix = originalDocumentName.substring(originalDocumentName.lastIndexOf(".") + 1);

        if (!suffix.equals("pdf")) {
            return R.error("只支持pdf文件");
        }

        Document newDocument = new Document();
        newDocument.setOriginalDocumentName(originalDocumentName);

        String documentPathNameWithoutSuffix = UUID.randomUUID().toString();
        String documentPathName = documentPathNameWithoutSuffix + '.' + suffix;
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

        R r = documentService.addDocument(newDocument);

        Document coverDocument = new Document();
        try {
            PDDocument doc = PDDocument.load(new File(documentPath + documentPathName));
            PDFRenderer renderer = new PDFRenderer(doc);

            BufferedImage image = renderer.renderImageWithDPI(0, 600);
            String coverDocumentPathName = documentPathNameWithoutSuffix + '-' + "cover" + ".png";
            ImageIO.write(image, "png", new File(documentPath + coverDocumentPathName));

            coverDocument.setOriginalDocumentName(coverDocumentPathName);
            coverDocument.setDocumentPathName(coverDocumentPathName);
            documentService.addDocument(coverDocument);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomerException(e.getMessage());
        }

        return r.put("coverDocument", coverDocument);

//        List<BookPageDocument> pageDocumentList = new ArrayList<>();
//        try {
//            PDDocument doc = PDDocument.load(new File(documentPath + documentPathName));
//            PDFRenderer renderer = new PDFRenderer(doc);
//            int pageCount = doc.getNumberOfPages();
//            for (int i = 0; i < pageCount; i++) {
//                BufferedImage image = renderer.renderImageWithDPI(i, 600);
//                String pageDocumentPathName = documentPathNameWithoutSuffix + '-' + (i + 1) + ".png";
//                ImageIO.write(image, "png", new File(documentPath + pageDocumentPathName));
//
//                Document pageDocument = new Document();
//                pageDocument.setOriginalDocumentName(pageDocumentPathName);
//                pageDocument.setDocumentPathName(pageDocumentPathName);
//                documentService.addDocument(pageDocument);
//
//                BookPageDocument bookPageDocument = new BookPageDocument();
//                bookPageDocument.setPage(i + 1);
//                bookPageDocument.setPageDocumentId(pageDocument.getId());
//                pageDocumentList.add(bookPageDocument);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new CustomerException(e.getMessage());
//        }
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 下载文件
     * @email 1538520381@qq.com
     * @date 2025/2/3 下午3:04
     */
    @GetMapping("/download/{documentId}")
    public void download(HttpServletResponse response, @PathVariable String documentId) {
        Document document = documentService.getById(documentId);

        try {
            ServletOutputStream outputStream = response.getOutputStream();

            if (document == null) {
                byte[] bytes = JSON.toJSONString(R.error("文件不存在")).getBytes();
                outputStream.write(bytes, 0, bytes.length);
                return;
            }

            FileInputStream fileInputStream = new FileInputStream(documentPath + document.getDocumentPathName());

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
            throw new CustomerException(e.getMessage());
        }
    }
}
