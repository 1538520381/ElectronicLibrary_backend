package com.Persolute.ElectronicLibrary.controller;

import com.Persolute.ElectronicLibrary.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private DocumentService documentService;
}
