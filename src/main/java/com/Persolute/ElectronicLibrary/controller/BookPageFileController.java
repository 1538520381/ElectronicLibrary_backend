package com.Persolute.ElectronicLibrary.controller;

import com.Persolute.ElectronicLibrary.service.BookPageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description BookPageFile Controller
 * @email 1538520381@qq.com
 * @date 2025/01/27 11:56
 */
@RestController
@RequestMapping("/bookPageFile")
public class BookPageFileController {
    @Autowired
    private BookPageFileService bookPageFileService;
}
