package com.Persolute.ElectronicLibrary.controller;

import com.Persolute.ElectronicLibrary.entity.dto.BookQueryPageDto;
import com.Persolute.ElectronicLibrary.entity.po.Book;
import com.Persolute.ElectronicLibrary.entity.po.BookPageDocument;
import com.Persolute.ElectronicLibrary.entity.po.Document;
import com.Persolute.ElectronicLibrary.entity.result.R;
import com.Persolute.ElectronicLibrary.exception.CustomerException;
import com.Persolute.ElectronicLibrary.service.BookPageDocumentService;
import com.Persolute.ElectronicLibrary.service.BookService;
import com.Persolute.ElectronicLibrary.service.DocumentService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Persolute
 * @version 1.0
 * @description Book Controller
 * @email 1538520381@qq.com
 * @date 2025/01/27 11:46
 */
@RestController
@RequestMapping("/book")
@Transactional
public class BookController {
    @Value("${ElectronicLibrary.document.path}")
    private String documentPath;

    @Autowired
    private BookService bookService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private BookPageDocumentService bookPageDocumentService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增书本
     * @email 1538520381@qq.com
     * @date 2025/2/3 下午6:53
     */
    @PostMapping("/add")
    public R add(@RequestBody Book book) {
        if (book.getCategoryId() == null) {
            throw new CustomerException();
        } else if (book.getName() == null) {
            throw new CustomerException();
        } else if (book.getCoverDocumentId() == null) {
            throw new CustomerException();
        } else if (book.getOriginalDocumentId() == null) {
            throw new CustomerException();
        }

        Document bookDocument = documentService.getById(book.getOriginalDocumentId());
        PDDocument doc = null;
        try {
            doc = PDDocument.load(new File(documentPath + bookDocument.getDocumentPathName()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException(e.getMessage());
        }
        int pageCount = doc.getNumberOfPages();
        book.setPage(pageCount);

        book.setHandlingFlag(0);
        bookService.save(book);

//        new Thread(() -> {
//            try {
//                Document bookDocument = documentService.getById(book.getOriginalDocumentId());
//                String bookDocumentPathName = bookDocument.getDocumentPathName();
//                String bookDocumentPathNameWithoutSuffix = bookDocumentPathName.substring(0, bookDocumentPathName.lastIndexOf("."));
//
//                PDDocument doc = PDDocument.load(new File(documentPath + bookDocument.getDocumentPathName()));
//                PDFRenderer renderer = new PDFRenderer(doc);
//                int pageCount = doc.getNumberOfPages();
//                for (int i = 0; i < pageCount; i++) {
//                    BufferedImage image = renderer.renderImageWithDPI(i, 600);
//                    String pageDocumentPathName = bookDocumentPathNameWithoutSuffix + '-' + (i + 1) + ".png";
//                    ImageIO.write(image, "png", new File(documentPath + pageDocumentPathName));
//
//                    Document pageDocument = new Document();
//                    pageDocument.setOriginalDocumentName(pageDocumentPathName);
//                    pageDocument.setDocumentPathName(pageDocumentPathName);
//                    documentService.addDocument(pageDocument);
//
//                    BookPageDocument bookPageDocument = new BookPageDocument();
//                    bookPageDocument.setBookId(book.getId());
//                    bookPageDocument.setPage(i + 1);
//                    bookPageDocument.setPageDocumentId(pageDocument.getId());
//                    bookPageDocumentService.save(bookPageDocument);
//                }
//                book.setHandlingFlag(0);
//                bookService.updateById(book);
//            } catch (Exception e) {
//                book.setHandlingFlag(2);
//                bookService.updateById(book);
//                e.printStackTrace();
//                throw new CustomerException(e.getMessage());
//            }
//        }).start();

        return R.success();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 列表根据id排序
     * @email 1538520381@qq.com
     * @date 2025/2/3 下午8:38
     */
    @GetMapping("/queryPage")
    public R queryPage(BookQueryPageDto bookQueryPageDto) {
        if (bookQueryPageDto.getPage() == null) {
            throw new CustomerException();
        } else if (bookQueryPageDto.getPageSize() == null) {
            throw new CustomerException();
        }
        return bookService.queryPage(bookQueryPageDto);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据id删除
     * @email 1538520381@qq.com
     * @date 2025/2/3 下午10:19
     */
    @DeleteMapping("/deleteById/{id}")
    public R deleteById(@PathVariable Long id) {
        bookPageDocumentService.deleteByBookId(id);
        return bookService.deleteById(id);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 查询列表
     * @email 1538520381@qq.com
     * @date 2025/2/4 下午6:42
     */
    @GetMapping("/queryList")
    public R queryList(Book bookQueryListDto) {
        return bookService.queryList(bookQueryListDto);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据id获取
     * @email 1538520381@qq.com
     * @date 2025/2/16 下午9:26
     */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id) {
        Book book = bookService.getById(id);
        if (book.getPage() == -1) {
            Document bookDocument = documentService.getById(book.getOriginalDocumentId());
            PDDocument doc = null;
            try {
                doc = PDDocument.load(new File(documentPath + bookDocument.getDocumentPathName()));
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomerException(e.getMessage());
            }
            int pageCount = doc.getNumberOfPages();
            book.setPage(pageCount);
            bookService.updateById(book);
        }

        return R.success().put("book", book);
    }
}
