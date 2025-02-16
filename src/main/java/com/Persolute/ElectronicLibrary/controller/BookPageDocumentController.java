package com.Persolute.ElectronicLibrary.controller;

import com.Persolute.ElectronicLibrary.entity.po.Book;
import com.Persolute.ElectronicLibrary.entity.po.BookPageDocument;
import com.Persolute.ElectronicLibrary.entity.po.Document;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.Persolute.ElectronicLibrary.exception.CustomerException;
import com.Persolute.ElectronicLibrary.service.BookPageDocumentService;
import com.Persolute.ElectronicLibrary.service.BookService;
import com.Persolute.ElectronicLibrary.service.DocumentService;
import com.alibaba.fastjson.JSON;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Persolute
 * @version 1.0
 * @description BookPageDocument Controller
 * @email 1538520381@qq.com
 * @date 2025/01/27 11:56
 */
@RestController
@RequestMapping("/bookPageDocument")
@Transactional
public class BookPageDocumentController {
    @Value("${ElectronicLibrary.document.path}")
    private String documentPath;

    @Autowired
    private BookPageDocumentService bookPageDocumentService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private BookService bookService;

    @GetMapping("/downloadByBookIdAndPage/{bookId}/{page}")
    public void downloadByBookIdAndPage(HttpServletResponse response, @PathVariable Long bookId, @PathVariable Integer page) {
        BookPageDocument bookPageDocument = bookPageDocumentService.getByBookIdAndPage(bookId, page);
        if (bookPageDocument == null) {
            Book book = bookService.getById(bookId);
            try {
                Document bookDocument = documentService.getById(book.getOriginalDocumentId());
                String bookDocumentPathName = bookDocument.getDocumentPathName();
                String bookDocumentPathNameWithoutSuffix = bookDocumentPathName.substring(0, bookDocumentPathName.lastIndexOf("."));

                PDDocument doc = PDDocument.load(new File(documentPath + bookDocument.getDocumentPathName()));
                PDFRenderer renderer = new PDFRenderer(doc);
                BufferedImage image = renderer.renderImageWithDPI(page - 1, 600);
                String pageDocumentPathName = bookDocumentPathNameWithoutSuffix + '-' + page + ".png";
                ImageIO.write(image, "png", new File(documentPath + pageDocumentPathName));

                Document pageDocument = new Document();
                pageDocument.setOriginalDocumentName(pageDocumentPathName);
                pageDocument.setDocumentPathName(pageDocumentPathName);
                documentService.addDocument(pageDocument);

                bookPageDocument = new BookPageDocument();
                bookPageDocument.setBookId(book.getId());
                bookPageDocument.setPage(page);
                bookPageDocument.setPageDocumentId(pageDocument.getId());
                bookPageDocumentService.save(bookPageDocument);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomerException(e.getMessage());
            }
        }

        Document document = documentService.getById(bookPageDocument.getPageDocumentId());

        try {
            ServletOutputStream outputStream = response.getOutputStream();

            if (document == null) {
                response.setContentType("text/html;charset=utf-8");
                byte[] bytes = JSON.toJSONString(R.error("文件不存在")).getBytes(StandardCharsets.UTF_8);
                outputStream.write(bytes, 0, bytes.length);
                return;
            }

            FileInputStream fileInputStream = new FileInputStream(documentPath + document.getDocumentPathName());

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(document.getOriginalDocumentName(), "UTF-8") + "\"");
            int len;
            byte[] bytes = new byte[1024 * 1024 * 3];
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
