package com.Persolute.ElectronicLibrary.controller;

import com.Persolute.ElectronicLibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description Book Controller
 * @email 1538520381@qq.com
 * @date 2025/01/27 11:46
 */
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
}
